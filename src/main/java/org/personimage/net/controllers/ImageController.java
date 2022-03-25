package org.personimage.net.controllers;

import java.util.List;

import org.personimage.net.entities.ImageMongo;
import org.personimage.net.services.ImageServiceImpl;
import org.personimage.net.util.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ImageController {
    private ImageServiceImpl imageServiceImpl;

    public ImageController(ImageServiceImpl imageServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
    }

    private static final String IMAGE = "image";
    private static final String ID = "/{id}";

    @GetMapping(IMAGE)
    public ResponseEntity<List<ImageMongo>> getAllImagesMongo() {
        return new ResponseEntity<>(imageServiceImpl.listAll(), HttpStatus.OK);
    }

    @GetMapping(IMAGE + ID)
    public ResponseEntity<ImageMongo> getImageMongoById(@PathVariable String id) {
        return new ResponseEntity<>(imageServiceImpl.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(IMAGE + ID)
    public ResponseEntity<Msg> deleteImageById(@PathVariable String id) {
        imageServiceImpl.deleteImagen(id);
        return new ResponseEntity<>(new Msg("Image has been deleted"), HttpStatus.OK);
    }

}
