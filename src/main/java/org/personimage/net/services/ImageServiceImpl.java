package org.personimage.net.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.personimage.net.entities.ImageMongo;
import org.personimage.net.exceptions.ImageNotFoundException;
import org.personimage.net.repositories.ImageMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageMongoRepository imageRepository;

    @Override
    public ImageMongo saveImagen(MultipartFile file, ImageMongo img) throws IOException {
	byte[] imageByte = file.getBytes();
	img.setName(file.getOriginalFilename());
	img.setImg(Base64.getEncoder().encodeToString(imageByte));
	img.setContentType(file.getContentType());
	img.setSize(file.getSize());
	return imageRepository.save(img);
    }

    @Override
    public List<ImageMongo> listAll() {
	return imageRepository.findAll();
    }

    @Override
    public boolean existsById(String id) {
	return imageRepository.existsById(id);
    }

    @Override
    public ImageMongo findById(String id) {
	return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
    }

    @Override
    public void deleteImagen(String id) {
	ImageMongo img = imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
	imageRepository.deleteById(img.getId());
    }

    public ImageMongo updateImageMongo(String id, MultipartFile file) throws IOException {
	ImageMongo img = imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
	img.setId(id);
	byte[] imageByte = file.getBytes();
	img.setImg(Base64.getEncoder().encodeToString(imageByte));
	return imageRepository.save(img);

    }

}
