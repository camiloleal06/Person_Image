package org.personimage.net.services;

import java.io.IOException;
import java.util.List;

import org.personimage.net.entities.ImageMongo;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public List<ImageMongo> listAll();

    public boolean existsById(String id);

    ImageMongo findById(String id);

    ImageMongo saveImagen(MultipartFile file, ImageMongo img) throws IOException;

    void deleteImagen(String id);

}
