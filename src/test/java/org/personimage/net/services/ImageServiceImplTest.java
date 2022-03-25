package org.personimage.net.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.personimage.net.entities.ImageMongo;
import org.personimage.net.repositories.ImageMongoRepository;
import org.springframework.mock.web.MockMultipartFile;

class ImageServiceImplTest {
    @Mock
    ImageMongoRepository imageRepository;

    @InjectMocks
    ImageServiceImpl imageServiceImpl;

    ImageMongo imageMongo;
    MockMultipartFile file;
    List<ImageMongo> lista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageMongo = new ImageMongo("1", "C/foto.jpg", "base64", 1580, "image/jpeg");
        file = new MockMultipartFile("data", "file.jpg", imageMongo.getContentType(), "Imagen".getBytes());
        lista = java.util.Arrays.asList(imageMongo);
    }

    @Test
    void testProcesarImagen() throws IOException {
        when(imageServiceImpl.saveImagen(file, imageMongo)).thenReturn(imageMongo);
        assertEquals("image/jpeg", imageServiceImpl.saveImagen(file, imageMongo).getContentType());
        verify(imageRepository).save(imageMongo);
    }

    @Test
    void testListAll() {
        when(imageRepository.findAll()).thenReturn(lista);
        assertFalse(imageServiceImpl.listAll().isEmpty());
        verify(imageRepository).findAll();
    }

    @Test
    void testFindById() {
        when(imageRepository.findById(anyString())).thenReturn(Optional.of(imageMongo));
        assertNotNull(imageServiceImpl.findById(anyString()));
        verify(imageRepository, times(1)).findById(anyString());
    }

    @Test
    void testExistsById() {
        when(imageRepository.existsById(anyString())).thenReturn(true);
        assertTrue(imageServiceImpl.existsById(anyString()));
        verify(imageRepository).existsById(anyString());
    }

    @Test
    void testUpdateById() throws IOException {
        when(imageRepository.save(imageMongo)).thenReturn(imageMongo);
        imageServiceImpl.updateImageMongo(imageMongo.getId(), file);
        verify(imageRepository).save(imageMongo);
    }

}
