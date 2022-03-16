package org.personimage.net.controllers;

import java.io.IOException;
import java.util.List;

import org.personimage.net.entities.ImageEntity;
import org.personimage.net.exceptions.ImageNotFoundException;
import org.personimage.net.services.ImageServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class ImageController {

    private ImageServiceImpl imageServiceImpl;

    private static final String IMAGEN = "imagen";
//    private static final String IMAGEN_MONGO = "imagenmongo";
    private static final String ID = "/{id}";

    @ApiOperation(notes = "Se obtiene el Listado de todas las Imagenes", value = "Listado de Imagenes", tags = {
	    "Imagen" })
    @GetMapping(IMAGEN)
    public ResponseEntity<List<ImageEntity>> getImagenes() {
	return new ResponseEntity<>(imageServiceImpl.listAll(), HttpStatus.OK);
    }

    @ApiOperation(notes = "Se obtiene una Imagen por ID", value = "ObtenerImagen por ID", tags = { "Imagen" })
    @GetMapping(IMAGEN + ID)
    public ResponseEntity<ImageEntity> getImageById(@PathVariable Integer id) throws ImageNotFoundException {
	return new ResponseEntity<>(imageServiceImpl.findById(id), HttpStatus.OK);
    }

    @ApiOperation(notes = "Se crea una Imagen nueva en BASE64", value = "Crear Imagen", tags = { "Imagen" })
    @PostMapping(IMAGEN)
    public ResponseEntity<ImageEntity> saveImage(MultipartFile imageFile) throws IOException {
	return new ResponseEntity<>(imageServiceImpl.saveImage(imageFile), HttpStatus.CREATED);
    }

    @ApiOperation(notes = "Se actualizan datos de una Imagen", value = "Actualizar Imagen", tags = { "Imagen" })
    @PutMapping(IMAGEN + ID)
    public ResponseEntity<ImageEntity> updateImage(@PathVariable Integer id, MultipartFile file)
	    throws ImageNotFoundException, IOException {
	return new ResponseEntity<>(imageServiceImpl.updateImage(id, file), HttpStatus.OK);
    }

    @ApiOperation(notes = "Se elimina una Imagen por ID", value = "Eliminar Imagen", tags = { "Imagen" })
    @DeleteMapping(IMAGEN + ID)
    public ResponseEntity<String> deleteImageById(@PathVariable Integer id) throws ImageNotFoundException {
	imageServiceImpl.deleteImage(id);
	return new ResponseEntity<>("Image Deleted", HttpStatus.OK);
    }

    /*
     * @ApiOperation(notes =
     * "Se obtiene el Listado de todas las Imagenes en MongoDB", value =
     * "Listado de Imagenes MongoDB", tags = { "Imagen" })
     * 
     * @GetMapping(IMAGEN_MONGO) public ResponseEntity<List<ImagenMongo>>
     * getImagenesMongo() { return new
     * ResponseEntity<>(imageServiceImplMysql.listAllMongo(), HttpStatus.OK); }
     */

    /*
     * @ApiOperation(notes = "Se crea una Imagen en MongoDB nueva en BASE64", value
     * = "Crear Imagen Mongo", tags = { "Imagen" })
     * 
     * @PostMapping(IMAGEN_MONGO) public ResponseEntity<ImagenMongo>
     * saveImageMongo(MultipartFile imageFile) throws IOException { return new
     * ResponseEntity<>(imageServiceImpl.saveImagenMongo(imageFile),
     * HttpStatus.CREATED); }
     */

}
