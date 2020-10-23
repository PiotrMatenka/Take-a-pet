package pl.piotron.animals.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.piotron.animals.exceptions.FileExtensionException;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.ImageStorage;
import pl.piotron.animals.repositories.AdvertisementRepository;
import pl.piotron.animals.repositories.ImageRepository;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImagesService {
    private final ImageRepository imageRepository;
    private String uploadUrl;
    private final AdvertisementRepository advertisementRepository;
    private final String UPLOAD_DIR = "/JAVA/animals/src/main/resources/static/upload-dir/";

    public ImagesService(ImageRepository imageRepository, AdvertisementRepository advertisementRepository) {
        this.imageRepository = imageRepository;
        this.advertisementRepository = advertisementRepository;
    }
    public void storeImage(MultipartFile file, Long advertisementId)
    {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        if(fileName.contains("..")) {
            throw new FileExtensionException();
        }
        if (!fileExtension.equals("jpg") && !fileExtension.equals("png")){
            throw new FileExtensionException();
        }
        uploadUrl = "../upload-dir/"+advertisementId+"/"+fileName;
        ImageStorage imageStorage = mapToEntity(file, advertisementId);
        uploadImage(file, advertisementId);
        imageRepository.save(imageStorage);
    }
    public List<ImageStorage> getAll ()
    {
        return new ArrayList<>(imageRepository.findAll());

    }
    public List<String> getAllByAdvertisementId(Long advertisementId)
    {
        return imageRepository.findAllByAdvertisement_Id(advertisementId)
                .stream()
                .map(ImageStorage::getUploadUrl)
                .collect(Collectors.toList());
    }

    public ImageStorage getMainImage(Long adverId)
    {
        return imageRepository.findAllByAdvertisement_Id(adverId)
                .get(0);

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
