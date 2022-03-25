package org.personimage.net.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.entities.ImageMongo;
import org.personimage.net.entities.PersonEntity;
import org.personimage.net.exceptions.PersonNotFoundException;
import org.personimage.net.mapper.PersonMapper;
import org.personimage.net.repositories.PersonRepository;
import org.springframework.mock.web.MockMultipartFile;

class PersonServiceImplTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personServiceImpl;

    PersonEntity person;
    List<PersonEntity> lista;
    PersonDTO personDTO;
    MockMultipartFile file;
    PersonMapper personMapper;
    ImageMongo img;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        person = new PersonEntity(1, "73207639", "Camilo", "Leal", "camilo.leal@pragma.com.co", "3225996394", 30,
                "Cartagena", "idmongo");

        lista = Arrays.asList(person);

        personDTO = new PersonDTO("73207639", "Camilo", "Leal", "camilo.leal@pragma.com.co", "3225996394", 30,
                "Cartagena");

        file = new MockMultipartFile("data", "file.jpg", "image/jpeg", "Imagen".getBytes());

        personMapper = mock(PersonMapper.class);
    }

    @Test
    void shouldGetlistAllPeople() {
        when(personRepository.findAll()).thenReturn(lista);
        //
        // When
        //
        List<PersonEntity> listPerson = personServiceImpl.listAll();
        //
        // Then
        //
        verify(personRepository, times(1)).findAll();
        assertFalse(listPerson.isEmpty());
        assertThat(listPerson).isNotNull();
    }

    @Test
    void shouldGetPersonaByIdAndReturnPersonNotFoundException() {
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        //
        // When
        //
        String email = personServiceImpl.getById(1).orElseThrow().getEmail();
        //
        // Then
        //
        verify(personRepository, times(2)).findById(1);
        assertThat(email).isEqualTo("camilo.leal@pragma.com.co");
        assertThrows(PersonNotFoundException.class, () -> personServiceImpl.getById(2));
    }

    @Test
    void shouldReturnTrueById() {
        when(personRepository.existsById(1)).thenReturn(true);
        assertTrue(personServiceImpl.existsById(1));
        verify(personRepository).existsById(anyInt());
    }

    @Test
    void shouldReturnListPersonGreatheThanAge() {
        when(personRepository.findByAgeGreaterThan(1)).thenReturn(lista);
        assertFalse(personServiceImpl.listAllAgeGreaterTo(1).isEmpty());
        verify(personRepository).findByAgeGreaterThan(1);
    }

    @Test
    void shouldReturnListPersonGreatheThanAgeOrPersonBadRequest() {
        when(personRepository.findByAgeGreaterThan(1)).thenReturn(lista);
        assertFalse(personServiceImpl.listAllAgeGreaterTo(1).isEmpty());
        verify(personRepository).findByAgeGreaterThan(1);
    }

    @Test
    void shouldSavePersonAndImage() throws IOException {

        assertNotNull(personServiceImpl.setImagenMongo(file));

    }

    @Test
    void shouldUpdateImageToPerson() throws IOException {
        PersonEntity persona = new PersonEntity(1, "73207639", "Camilo", "Leal", "camilo.leal@pragma.com.co",
                "3225996394", 30, "Cartagena", "idmongo");
        when(personRepository.findById(persona.getId())).thenReturn(Optional.of(person));
        Optional<PersonEntity> personUpdate = personRepository.findById(1);
        int id = personUpdate.get().getId();
        assertNotNull(personServiceImpl.updateImageToPerson(id, file));
        verify(personRepository).save(person);
    }

}
