package org.personimage.net.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.ImageEntity;
import org.personimage.net.entities.ImageMongoEntity;
import org.personimage.net.entities.PersonEntity;
import org.personimage.net.exceptions.PersonNotFoundException;
import org.personimage.net.mapper.PersonMapper;
import org.personimage.net.repositories.ImageMongoRepository;
import org.personimage.net.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ImageMongoRepository imageMongoRepository;

    private final PersonMapper mapper;

    @Override
    public List<PersonEntity> listAll() {
	return personRepository.findAll();
    }

    @Override
    public PersonEntity savePerson(PersonDTO personDTO) {
	return personRepository.save(mapper.toEntity(personDTO));
    }

    @Override
    public Optional<PersonEntity> getById(Integer id) throws PersonNotFoundException {
	PersonEntity person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	return personRepository.findById(person.getId());
    }

    @Override
    public boolean existsById(Integer id) {
	return personRepository.existsById(id);
    }

    @Override
    public void deletePerson(Integer id) throws PersonNotFoundException {
	PersonEntity person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	personRepository.deleteById(person.getId());

    }

    @Override
    public PersonEntity updatePerson(Integer id, PersonDTO personDTO) throws PersonNotFoundException, IOException {
	if (!this.existsById(id))
	    throw new PersonNotFoundException(id);
	return personRepository.save(mapper.toEntity(personDTO, id));
    }

    @Override
    public PersonEntity savePerson(PersonDTO personDTO, MultipartFile file) throws IOException {
	return personRepository.save(mapper.toEntity(personDTO, this.setImage(file)));
    }

    @Override
    public PersonEntity savePersonWithImageMongo(PersonDTO personDTO, MultipartFile file) throws IOException {
	return personRepository.save(mapper.toEntity(personDTO, this.setImagenMongo(file).getId()));
    }

    public PersonImageMongoDTO getPersonImageMongo(int id) {
	PersonEntity person = this.getById(id).orElseThrow(() -> new PersonNotFoundException(id));
	ImageMongoEntity imageMongo = imageMongoRepository.findById(person.getImageMongo()).get();
	person.setImageMongo(imageMongo.getName());
	return mapper.toGetPersonDTO(person);
    }

    private ImageEntity setImage(MultipartFile file) throws IOException {
	ImageEntity img = new ImageEntity();
	byte[] imageByte = file.getBytes();
	img.setName(file.getOriginalFilename());
	img.setImg(Base64.getEncoder().encodeToString(imageByte));
	img.setContentType(file.getContentType());
	img.setSize(file.getSize());
	return img;
    }

    public ImageMongoEntity setImagenMongo(MultipartFile file) throws IOException {
	ImageMongoEntity img = new ImageMongoEntity();
	byte[] imageByte = file.getBytes();
	img.setName(file.getOriginalFilename());
	img.setImg(Base64.getEncoder().encodeToString(imageByte));
	img.setSize(file.getSize());
	img.setContentType(file.getContentType());
	return imageMongoRepository.save(img);
    }

}
