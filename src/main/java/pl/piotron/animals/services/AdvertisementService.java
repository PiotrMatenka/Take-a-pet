package pl.piotron.animals.services;

import org.springframework.stereotype.Service;
import pl.piotron.animals.exceptions.AdvertisementNotFoundException;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.exceptions.ImageNotFoundException;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.model.dto.AdvertisementDto;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.model.dto.UserAdvertisementDto;
import pl.piotron.animals.model.mapper.AdvertisementMapper;
import pl.piotron.animals.model.mapper.ImageAdvertisementMapper;
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
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final ImageAdvertisementMapper imageAdvertisementMapper;
    private final AdvertisementMapper advertisementMapper;
    private final ImageRepository imageRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository, ImageAdvertisementMapper imageAdvertisementMapper, AdvertisementMapper advertisementMapper, ImageRepository imageRepository) {
        this.advertisementRepository = advertisementRepository;
        this.imageAdvertisementMapper = imageAdvertisementMapper;
        this.advertisementMapper = advertisementMapper;
        this.imageRepository = imageRepository;
    }

    public Optional<AdvertisementDto> findById(Long id)
    {
        return advertisementRepository.findById(id).map(advertisementMapper::toDto);
    }

    public List<ImageAdvertisementDto> getAll()
    {
        return advertisementRepository.findAll()
                .stream().map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    public AdvertisementDto createAdvertisement(AdvertisementDto dto)
    {
        Optional<Advertisement> activeAdvertisement = advertisementRepository.findByTitleAndUser_Id(dto.getTitle(), dto.getUserId());
        activeAdvertisement.ifPresent(a ->{
            throw new AppException("Posiadasz już ogłoszenie z identycznym tytułem");
        });
        return mapAndSave(dto);
    }

    public Optional<UserAdvertisementDto> getViewById (Long adverId)
    {
        return advertisementRepository.findById(adverId)
                .map(UserAdvertisementMapper::toDto);
    }

    public List<ImageAdvertisementDto> getAllByCategory(String category)
    {
        return advertisementRepository.findAllByCategoryName(category)
                .stream()
                .filter(a -> a.getEnd() == null)
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<ImageAdvertisementDto> getAllByCity (String city)
    {
        return advertisementRepository.findAllByCity(city)
                .stream()
                .filter(a -> a.getEnd() == null)
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<ImageAdvertisementDto> getAllByTitle (String title)
    {
        return advertisementRepository.findAllByTitle(title).stream()
                .filter(a -> a.getEnd() == null)
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public LocalDateTime finishAdvertisement(Long adverId)
    {
        Optional<Advertisement>advertisement = advertisementRepository.findById(adverId);
        Advertisement adverEntity = advertisement.orElseThrow(AdvertisementNotFoundException::new);
        if (adverEntity.getEnd()!=null)
            throw new AppException("Ogłoszenie jest już zakonczone");
        else {
                adverEntity.setEnd(LocalDateTime.now());
                removeImages(adverId);}

        return adverEntity.getEnd();
    }

    public AdvertisementDto updateAdvertisement(AdvertisementDto dto)
    {
        Optional<Advertisement> adverById = advertisementRepository.findById(dto.getId());
        Advertisement advertisement = adverById.orElseThrow(AdvertisementNotFoundException::new);
        return mapAndSave(dto);
    }
    public List<String> getImagesById (Long id)
    {
        Optional<Advertisement> adById = advertisementRepository.findById(id);
        if (!adById.isPresent())
            throw new AdvertisementNotFoundException();
        else
            return imageRepository.findAllByAdvertisement_Id(id)
                .stream()
                .map(ImageStorage::getUploadUrl)
                .collect(Collectors.toList());
    }


    private AdvertisementDto mapAndSave (AdvertisementDto dto)
    {
        Advertisement entity = advertisementMapper.toEntity(dto);
        Advertisement savedAdvertisement = advertisementRepository.save(entity);
        return advertisementMapper.toDto(savedAdvertisement);
    }

    private void removeImages(Long adverId)
    {
        List<ImageStorage> images  = imageRepository.findAllByAdvertisement_Id(adverId);
        if (!images.isEmpty())
        {
            for (ImageStorage i:images)
            {
                try{
                    Files.delete(Paths.get(i.getDeleteUrl()));
                }catch (IOException e)
                {
                    e.getMessage();
                }
            }
            imageRepository.deleteAll(images);
        }else throw new ImageNotFoundException();

    }

}
