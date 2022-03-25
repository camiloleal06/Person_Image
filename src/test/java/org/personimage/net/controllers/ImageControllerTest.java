package org.personimage.net.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.personimage.net.entities.ImageMongo;
import org.personimage.net.services.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ImageController.class)
class ImageControllerTest {
    @Autowired
    MockMvc mockito;

    @MockBean
    private ImageServiceImpl imageServiceImpl;

    ImageMongo imageMongo = new ImageMongo("1", "C/foto.jpg", "base64", 1580, "image/jpeg");
    List<ImageMongo> listImage = Arrays.asList(imageMongo);

    @Test
    void shouldReturnGetAllImagesMongo() throws Exception {
	when(imageServiceImpl.listAll()).thenReturn(listImage);
	mockito.perform(get("/api/v1/image/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(listImage.size()))).andDo(print());

    }

    @Test
    void shouldReturnGetImageMongoById() throws Exception {
	when(imageServiceImpl.findById(anyString())).thenReturn(imageMongo);
	mockito.perform(get("/api/v1/image/{id}", "1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
    }

    @Test
    void testDeleteImageById() throws Exception {
	doNothing().when(imageServiceImpl).deleteImagen("1");
	mockito.perform(delete("/api/v1/image/{id}", "1"))
		.andExpect(jsonPath("$.messagge", is("Image has been deleted"))).andExpect(status().isOk())
		.andDo(print());
	verify(imageServiceImpl, times(1)).deleteImagen("1");
    }

}
