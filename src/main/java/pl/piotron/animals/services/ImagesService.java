package pl.piotron.animals.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import pl.piotron.animals.exceptions.DuplicateImageException;
import pl.piotron.animals.exceptions.FileExtensionException;
import pl.piotron.animals.exceptions.ImageNotFoundException;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.repositories.AdvertisementRepository;
import pl.piotron.animals.repositories.ImageRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImagesService {
    private final ImageRepository imageRepository;
    private String uploadUrl;
    private final AdvertisementRepository advertisementRepository;
    private final String UPLOAD_DIR = "/JAVA/Take a pet/src/main/resources/static/upload-dir/";

    public ImagesService(ImageRepository imageRepository, AdvertisementRepository advertisementRepository) {
        this.imageRepository = imageRepository;
        this.advertisementRepository = advertisementRepository;
    }

    public void storeImage(MultipartFile file, Long advertisementId) throws DuplicateImageException
    {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        if(fileName.contains("..")) {
            throw new FileExtensionException();
        }
        if (!fileExtension.equals("jpg") && !fileExtension.equals("png")){
            throw new FileExtensionException();
        }
        uploadUrl = "/upload-dir/"+advertisementId+"/"+fileName;
        Optional<ImageStorage> imageByUrl = imageRepository.findByUploadUrl(uploadUrl);
        if (imageByUrl.isPresent())
            throw new DuplicateImageException();
        ImageStorage imageStorage = mapToEntity(file, advertisementId);
        uploadImage(file, advertisementId);
        imageRepository.save(imageStorage);
    }

    public List<ImageStorage> getAll ()
    {
        return new ArrayList<>(imageRepository.findAll());
    }

    public Optional<ImageStorage> getImage(Long imageId)
    {
        return imageRepository.findById(imageId);
    }

    public List<ImageStorage> getAllByAdvertisementId(Long advertisementId)
    {
        return imageRepository.findAllByAdvertisement_Id(advertisementId);

    }

    public ImageStorage getMainImage(Long adverId)
    {
        return imageRepository.findFirstByAdvertisement_Id(adverId);

    }

    public void removeSingleImage (Long imageId)
    {
        Optional<ImageStorage> image = imageRepository.findById(imageId);
        image.ifPresentOrElse(i -> {
            try {
                Files.delete(Paths.get(i.getDeleteUrl()));
                imageRepository.delete(i);
            }catch (IOException e)
            {
                e.getMessage();
            }

        }, () -> {
            throw new ImageNotFoundException();
        });
    }

    private void uploadImage(MultipartFile file, Long advertisementId)
    {
        File uploadDirectory = new File(UPLOAD_DIR+"/"+advertisementId);
        uploadDirectory.mkdirs();

        try {
            File oFile = new File(UPLOAD_DIR+advertisementId+"/"+file.getOriginalFilename());
            OutputStream os = new FileOutputStream(oFile);
            InputStream inputStream = file.getInputStream();

            IOUtils.copy(inputStream, os);

            os.close();
            inputStream.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private ImageStorage mapToEntity(MultipartFile file, Long advertisementId)
    {
        ImageStorage imageStorage = new ImageStorage();
        imageStorage.setTitle(StringUtils.cleanPath(file.getOriginalFilename()));
        imageStorage.setUploadUrl(uploadUrl);
        imageStorage.setDeleteUrl(UPLOAD_DIR+advertisementId+"/"+file.getOriginalFilename());
        Optional<Advertisement>advertisement = advertisementRepository.findById(advertisementId);
        advertisement.ifPresent(imageStorage::setAdvertisement);
        return imageStorage;
    }

}
