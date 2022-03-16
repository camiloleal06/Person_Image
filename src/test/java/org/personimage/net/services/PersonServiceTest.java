package org.personimage.net.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.entities.ImageEntity;
import org.personimage.net.entities.ImageMongoEntity;
import org.personimage.net.entities.PersonEntity;
import org.personimage.net.exceptions.PersonNotContentException;
import org.personimage.net.exceptions.PersonNotFoundException;
import org.personimage.net.repositories.PersonRepository;
import org.springframework.mock.web.MockMultipartFile;

class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonServiceImpl personServiceImpl;

    PersonEntity person;
    ImageEntity image;
    ImageMongoEntity imageMongo;
    List<PersonEntity> lista;
    PersonDTO personDTO;
    MockMultipartFile multipartFile;

    @BeforeEach
    void setUp() {

	MockitoAnnotations.openMocks(this);

	image = new ImageEntity(1, "C/foto.jpg", "base64", 1580, "image/jpg");
	imageMongo = new ImageMongoEntity("1", "C/foto.jpg", "base64", 1580, "image/jpg");
	person = new PersonEntity(1, "73207639", "Camilo", "Leal", "camilo.leal@pragma.com.co", "3225996394", 30,
		"Cartagena", image, imageMongo.getId());

	lista = Arrays.asList(new PersonEntity(5, "73207639", "Camilo", "Leal", "camilo.leal@pragma.com.co",
		"3225996394", 30, "Cartagena", image, imageMongo.getId()));

	personDTO = new PersonDTO("73207639", "Camilo", "Leal", "camilo.leal@pragma.com.co", "3225996394", 30,
		"Cartagena");

	multipartFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

    }

    @Test
    void listAll() throws PersonNotContentException {
	when(personRepository.findAll()).thenReturn(lista);
	assertTrue(!personServiceImpl.listAll().isEmpty());
	assertNotNull(personServiceImpl.listAll());
	verify(personRepository, times(2)).findAll();

    }

    @Test
    void testPersonaByIdExiste() throws PersonNotFoundException {
	when(personRepository.findById(1)).thenReturn(Optional.of(person));
	assertTrue(personServiceImpl.getById(1).isPresent());
	assertThat(personServiceImpl.getById(1).orElseThrow().getEmail()).isEqualTo("camilo.leal@pragma.com.co");
	verify(personRepository, times(4)).findById(1);
    }

}
