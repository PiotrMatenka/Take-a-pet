package pl.piotron.animals.services;

import org.springframework.stereotype.Service;
import pl.piotron.animals.exceptions.AdvertisementNotFoundException;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.model.dto.AdvertisementDto;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.model.dto.ViewAdvertisementDto;
import pl.piotron.animals.model.mapper.AdvertisementMapper;
import pl.piotron.animals.model.mapper.ImageAdvertisementMapper;
import pl.piotron.animals.model.mapper.ViewAdvertisementMapper;
import pl.piotron.animals.repositories.AdvertisementRepository;
import pl.piotron.animals.repositories.ImageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final ImageAdvertisementMapper imageAdvertisementMapper;
    private final AdvertisementMapper advertisementMapper;
    private final ImageRepository imageRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository, ImageAdvertisementMapper imageAdvertisementMapper,
                                AdvertisementMapper advertisementMapper, ImageRepository imageRepository) {
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

    public Optional<ViewAdvertisementDto> getViewById (Long adverId)
    {
        return advertisementRepository.findById(adverId)
                .map(ViewAdvertisementMapper::toDto);
    }

    public List<ImageAdvertisementDto> getAllByCategory(String category)
    {
        return advertisementRepository.findAllByCategoryName(category)
                .stream()
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<ImageAdvertisementDto> getAllByCity (String city)
    {
        return advertisementRepository.findAllByCity(city)
                .stream()
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<ImageAdvertisementDto> getAllByTitle (String title)
    {
        return advertisementRepository.findAllByTitle(title).stream()
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    public AdvertisementDto updateAdvertisement(AdvertisementDto dto)
    {
        Advertisement advertisement =  checkById(dto.getId());
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

    private Advertisement checkById(Long id)
    {
        Optional<Advertisement>advertisement = advertisementRepository.findById(id);
        return  advertisement.orElseThrow(AdvertisementNotFoundException::new);
    }

    private AdvertisementDto mapAndSave (AdvertisementDto dto)
    {
        Advertisement entity = advertisementMapper.toEntity(dto);
        Advertisement savedAdvertisement = advertisementRepository.save(entity);
        return advertisementMapper.toDto(savedAdvertisement);
    }
}
