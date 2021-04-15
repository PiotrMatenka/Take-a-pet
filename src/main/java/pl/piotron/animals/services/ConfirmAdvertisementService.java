package pl.piotron.animals.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import pl.piotron.animals.exceptions.AdvertisementNotFoundException;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.exceptions.ImageNotFoundException;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.EmailResponse;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.model.dto.UserAdvertisementDto;
import pl.piotron.animals.model.mapper.UserAdvertisementMapper;
import pl.piotron.animals.repositories.AdvertisementRepository;
import pl.piotron.animals.repositories.ImageRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfirmAdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final ImageRepository imageRepository;
    private final UserAdvertisementMapper userAdvertisementMapper;
    private final EmailSenderService emailSenderService;

    public ConfirmAdvertisementService(AdvertisementRepository advertisementRepository, ImageRepository imageRepository,
                                       UserAdvertisementMapper userAdvertisementMapper, EmailSenderService emailSenderService) {
        this.advertisementRepository = advertisementRepository;
        this.imageRepository = imageRepository;
        this.userAdvertisementMapper = userAdvertisementMapper;
        this.emailSenderService = emailSenderService;
    }

    public List<UserAdvertisementDto> getAllAcceptedByUser()
    {
        return advertisementRepository.findAllAcceptedByUser()
                .stream().map(userAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean acceptAdverByUser(Long id)
    {
        Advertisement adverEntity = checkById(id);
        if (adverEntity.getEnd()!=null)
            throw new AppException("Ogłoszenie jest już zakończone");
        else {
            adverEntity.setAcceptedByUser(true);
        }
        return adverEntity.isAcceptedByUser();
    }

    @Transactional
    public boolean acceptAdverByAdmin(Long id)
    {
        Advertisement adverEntity = checkById(id);
        if (adverEntity.getEnd()!=null)
            throw new AppException("Ogłoszenie jest już zakończone");
        else {
            adverEntity.setAcceptedByAdmin(true);
            sendAcceptEmail(adverEntity);
        }
        return adverEntity.isAcceptedByAdmin();
    }

    @Transactional
    public LocalDateTime finishAdvertisement(Long adverId)
    {
        Advertisement adverEntity = checkById(adverId);
        if (adverEntity.getEnd()!=null)
            throw new AppException("Ogłoszenie jest już zakończone");
        else {
            adverEntity.setEnd(LocalDateTime.now());
            removeImages(adverId);}

        return adverEntity.getEnd();
    }

    @Transactional
    public EmailResponse sendToCorrectionEmail(Long id, String response)
    {
        Advertisement advertisement = checkById(id);
        advertisement.setAcceptedByUser(false);
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setContent(response);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(advertisement.getUser().getEmail());
        mailMessage.setSubject("Ogłoszenie do poprawy");
        mailMessage.setFrom("takeapet.pl@gmail.com");
        mailMessage.setText("Cześć "+advertisement.getUser().getUserDetails().getFirstName() +",\n\n "
                +"Twoje ogłoszenie: " +advertisement.getTitle()+", wymaga poprawy, poniżej szczegóły:"+"\n\n"
                +response+"\n\n"
                +"Pozdrawiamy, zespół Take A Pet");

        emailSenderService.sendEmail(mailMessage);
        return emailResponse;
    }

    void sendAcceptEmail(Advertisement advertisement)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(advertisement.getUser().getEmail());
        mailMessage.setSubject("Potwierdzenie wystawienia ogłoszenia.");
        mailMessage.setFrom("takeapet.pl@gmail.com");
        mailMessage.setText("Cześć "+advertisement.getUser().getUserDetails().getFirstName() +",\n\n "
                +"Potwierdzamy wystawienia ogłoszenia: " +advertisement.getTitle()+"\n\n"
                +"Pozdrawiamy, zespół Take A Pet");

        emailSenderService.sendEmail(mailMessage);
    }

     Advertisement checkById(Long id)
    {
        Optional<Advertisement> advertisement = advertisementRepository.findById(id);
        return  advertisement.orElseThrow(AdvertisementNotFoundException::new);
    }

     void removeImages(Long adverId)
    {
        List<ImageStorage> images  = imageRepository.findAllByAdvertisement_Id(adverId);

        for (ImageStorage i:images)
        {
            try{
                Files.delete(Paths.get(i.getDeleteUrl()));
            }catch (IOException | ImageNotFoundException e)
            {
                e.getMessage();
            }
        }
        imageRepository.deleteAll(images);
    }
}
