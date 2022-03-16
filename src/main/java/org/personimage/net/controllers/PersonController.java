package org.personimage.net.controllers;

import java.io.IOException;
import java.util.List;

import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.PersonEntity;
import org.personimage.net.exceptions.PersonBadRequestException;
import org.personimage.net.exceptions.PersonNotFoundException;
import org.personimage.net.services.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
@Api(value = "API REST PERSON-IMAGE")
public class PersonController {

    private PersonServiceImpl personServiceImpl;

    public PersonController(PersonServiceImpl personServiceImpl) {
	this.personServiceImpl = personServiceImpl;
    }

    private static final String PERSON = "person";
    private static final String PERSON_MONGO = "personmongo";
    private static final String ID = "/{id}";

    @ApiOperation(notes = "Se obtiene el Listado de todos las Personas", value = "Listado de Personas", tags = {
	    "Person" })
    @GetMapping(PERSON)
    public ResponseEntity<List<PersonEntity>> getPeople() {
	return new ResponseEntity<>(personServiceImpl.listAll(), HttpStatus.OK);
    }

    @ApiOperation(notes = "Se obtiene una Persona por ID", value = "Obtener Persona por ID", tags = { "Person" })
    @GetMapping(PERSON + ID)
    public ResponseEntity<PersonEntity> getPersonById(@PathVariable Integer id) throws PersonNotFoundException {
	return new ResponseEntity<>(personServiceImpl.getById(id).orElse(null), HttpStatus.OK);
    }

    @ApiOperation(notes = "Se obtiene una persona por su ID y su Imagen en MONGO DB", value = "Obtener Persona por ID en MySQL e Imagen en MongoDB", tags = {
	    "Person" })
    @GetMapping(PERSON_MONGO + ID)
    public ResponseEntity<PersonImageMongoDTO> getPersonImageByDni(@PathVariable int id) {
	return new ResponseEntity<>(personServiceImpl.getPersonImageMongo(id), HttpStatus.OK);
    }

    @ApiOperation(notes = "Se crea una Persona nueva", value = "Crear Persona", tags = { "Person" })
    @PostMapping(PERSON)
    public ResponseEntity<PersonEntity> savePersonWithImageMysql(@ModelAttribute PersonDTO personDTO,
	    @RequestParam("image") MultipartFile file) throws PersonBadRequestException, IOException {
	return new ResponseEntity<>(personServiceImpl.savePerson(personDTO, file), HttpStatus.CREATED);
    }

    @ApiOperation(notes = "Se crea una Persona nueva con Imagen en Mongo", value = "Crear Persona", tags = { "Person" })
    @PostMapping(PERSON_MONGO)
    public ResponseEntity<PersonEntity> savePersonWithImageMongo(@ModelAttribute PersonDTO personDTO,
	    @RequestParam("imageMongo") MultipartFile file) throws PersonBadRequestException, IOException {
	return new ResponseEntity<>(personServiceImpl.savePersonWithImageMongo(personDTO, file), HttpStatus.CREATED);
    }

    @ApiOperation(notes = "Se actualizan datos de una Persona", value = "Actualizar Persona", tags = { "Person" })
    @PutMapping(PERSON + ID)
    public ResponseEntity<PersonEntity> updatePerson(@PathVariable Integer id, @ModelAttribute PersonDTO personDTO,
	    MultipartFile file) throws PersonNotFoundException, IOException {
	return new ResponseEntity<>(personServiceImpl.updatePerson(id, personDTO), HttpStatus.OK);
    }

    @ApiOperation(notes = "Se elimina una Persona por ID", value = "Elminar Persona", tags = { "Person" })
    @DeleteMapping(PERSON + ID)
    public ResponseEntity<String> deletePersonById(@PathVariable Integer id) throws PersonNotFoundException {
	personServiceImpl.deletePerson(id);
	return new ResponseEntity<>("Person Deleted", HttpStatus.OK);
    }

}
