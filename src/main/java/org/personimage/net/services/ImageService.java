package org.personimage.net.services;

import java.io.IOException;
import java.util.List;

import org.personimage.net.entities.ImageEntity;
import org.personimage.net.exceptions.ImageNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public List<ImageEntity> listAll();

    public ImageEntity saveImage(MultipartFile imagefile) throws IOException;

    public ImageEntity findById(Integer id) throws ImageNotFoundException;

    public boolean existsById(Integer id);

    public void deleteImage(Integer id) throws ImageNotFoundException;

    public ImageEntity updateImage(Integer id, MultipartFile imageFile) throws ImageNotFoundException, IOException;

}
