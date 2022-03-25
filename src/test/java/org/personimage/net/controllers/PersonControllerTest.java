package org.personimage.net.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.PersonEntity;
import org.personimage.net.mapper.PersonMapper;
import org.personimage.net.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    MockMvc mockito;

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private PersonServiceImpl personService;

    @MockBean
    private PersonMapper mapper;

    final int id = 1;

    MockMultipartFile file = new MockMultipartFile("data", "file.jpg", "image/jpeg", "Imagen".getBytes());

    PersonEntity person = new PersonEntity(1, "73207639", "Camilo", "Leal", "camilo.leal@pragma.com.co", "3225996394",
            30, "Cartagena", "6h5f5sd4gd6");

    @Test
    void shouldGetPersonByIdAndReturnOk() throws Exception {
        when(personService.getById(anyInt())).thenReturn(Optional.of(person));
        mockito.perform(get("/api/v1/person/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void shoulFindPersonByIdAndReturnBadRequestException() throws Exception {
        when(personService.getById(1)).thenReturn(Optional.of(person));
        mockito.perform(get("/api/v1/person/{id}", "O").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.code", is(400)));
    }

    @Test
    void shouldGetAllPeopleAndReturnOk() throws Exception {
        List<PersonImageMongoDTO> personLista = mapper.toListDTO(Arrays.asList(person));
        given(personService.listAll()).willReturn(Arrays.asList(person));
        mockito.perform(get("/api/v1/person").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(personLista.size())));
    }

    @Test
    void shouldDeletePersonAndReturnOk() throws Exception {
        doNothing().when(personService).deletePerson(id);
        mockito.perform(delete("/api/v1/person/{id}", id))
                .andExpect(jsonPath("$.message", is("Person has been Deleted"))).andExpect(status().isOk())
                .andDo(print());
        verify(personService, times(1)).deletePerson(id);

    }

    @Test
    void shouldSavePersonAndReturnOk() throws Exception {
        given(personService.savePersonWithImageMongo(mapper.toDTO(person), file)).willReturn(person);
        mockito.perform(multipart("/api/v1/person").file(file).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateImageToPersonAndReturnNoSupported() throws Exception {
        given(personService.updateImageToPerson(person.getId(), file)).willReturn(person);
        mockito.perform(put("/api/v1/person").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andDo(print());
    }

    @Test
    void shouldUpdateDataToPersonAndReturnOK() throws Exception {
        given(personService.updateDataToPerson(person.getId(), mapper.toDTO(person))).willReturn(person);
        mockito.perform(put("/api/v1/person/{id}", 1).content(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
