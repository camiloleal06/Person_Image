package org.personimage.net.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.personimage.net.entities.ImageEntity;
import org.personimage.net.exceptions.ImageNotFoundException;
import org.personimage.net.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public ImageEntity saveImage(MultipartFile file) throws IOException {
	ImageEntity img = new ImageEntity();
	return procesarImagen(file, img);
    }

    @Override
    public List<ImageEntity> listAll() {
	return imageRepository.findAll();
    }

    @Override
    public ImageEntity findById(Integer id) throws ImageNotFoundException {
	return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
    }

    @Override
    public boolean existsById(Integer id) {
	return imageRepository.existsById(id);
    }

//
    @Override
    public void deleteImage(Integer id) throws ImageNotFoundException {
	ImageEntity img = imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
	imageRepository.deleteById(img.getId());
    }

    @Override
    public ImageEntity updateImage(Integer id, MultipartFile file) throws ImageNotFoundException, IOException {
	ImageEntity img = imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
	return procesarImagen(file, img);

    }

    public ImageEntity procesarImagen(MultipartFile file, ImageEntity img) throws IOException {
	byte[] imageByte = file.getBytes();
	img.setName(file.getOriginalFilename());
	img.setImg(Base64.getEncoder().encodeToString(imageByte));
	img.setContentType(file.getContentType());
	img.setSize(file.getSize());
	return imageRepository.save(img);
    }

}
