package org.personimage.net.controllers;

import java.io.IOException;
import java.util.List;

import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.PersonEntity;
import org.personimage.net.exceptions.PersonBadRequestException;
import org.personimage.net.services.PersonServiceImpl;
import org.personimage.net.util.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

@RestController
@RequestMapping
@Api(value = "API REST PERSON-IMAGE")
public class PersonController {

    private PersonServiceImpl personServiceImpl;

    public PersonController(PersonServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    private static final String PERSON = "person";
    private static final String IMAGE = "/image";
    private static final String AGE = "/age";
    private static final String ID = "/{id}";

    @GetMapping(PERSON)
    public ResponseEntity<List<PersonImageMongoDTO>> getAllPeople() {
        return new ResponseEntity<>(personServiceImpl.listAllDTo(), HttpStatus.OK);
    }

    @GetMapping(PERSON + AGE)
    public ResponseEntity<List<PersonEntity>> getAllPeopleByAge(@RequestParam int age) {
        return new ResponseEntity<>(personServiceImpl.listAllAgeGreaterTo(age), HttpStatus.OK);
    }

    @PostMapping(PERSON)
    public ResponseEntity<PersonEntity> savePersonWithImageMongo(@ModelAttribute PersonDTO personDTO,
            MultipartFile file) throws PersonBadRequestException, IOException {
        return new ResponseEntity<>(personServiceImpl.savePersonWithImageMongo(personDTO, file), HttpStatus.CREATED);
    }

    @GetMapping(PERSON + ID)
    public ResponseEntity<PersonImageMongoDTO> getPersonImageById(@PathVariable int id) {
        return new ResponseEntity<>(personServiceImpl.getPersonImageMongo(id), HttpStatus.OK);
    }

    @DeleteMapping(PERSON + ID)
    public ResponseEntity<Msg> deletePersonById(@PathVariable Integer id) {
        personServiceImpl.deletePerson(id);
        return new ResponseEntity<>(new Msg("Person has been Deleted"), HttpStatus.OK);
    }

    @PutMapping(PERSON + ID)
    public ResponseEntity<PersonEntity> updateDataToPerson(@PathVariable Integer id, @RequestBody PersonDTO personDTO) {
        return new ResponseEntity<>(personServiceImpl.updateDataToPerson(id, personDTO), HttpStatus.OK);
    }

    @PutMapping(PERSON + IMAGE + ID)
    public ResponseEntity<PersonEntity> updateImageToPerson(@PathVariable Integer id, MultipartFile file)
            throws IOException {
        return new ResponseEntity<>(personServiceImpl.updateImageToPerson(id, file), HttpStatus.OK);
    }

}
