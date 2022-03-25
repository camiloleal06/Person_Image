package org.personimage.net.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.ImageMongo;
import org.personimage.net.entities.PersonEntity;
import org.personimage.net.exceptions.ImageNotFoundException;
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
    public List<PersonImageMongoDTO> listAllDTo() {
	return mapper.toListDTO(personRepository.findAll());
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
    public PersonEntity updateImageToPerson(Integer id, MultipartFile file)
	    throws PersonNotFoundException, IOException {
	PersonEntity person = this.getById(id).orElseThrow(() -> new PersonNotFoundException(id));
	person.setImageMongo(this.setImagenMongo(file).getId());
	return personRepository.save(person);
    }

    @Override
    public PersonEntity savePersonWithImageMongo(PersonDTO personDTO, MultipartFile file) throws IOException {
	return personRepository.save(mapper.toEntity(personDTO, this.setImagenMongo(file).getId()));
    }

    @Override
    public PersonImageMongoDTO getPersonImageMongo(int id) {
	PersonEntity person = this.getById(id).orElseThrow(() -> new PersonNotFoundException(id));
	final String IMG = person.getImageMongo();
	ImageMongo imageMongo = imageMongoRepository.findById(IMG).orElseThrow(() -> new ImageNotFoundException(IMG));
	return mapper.toPersonImageMongoDTO(person, imageMongo.getImg());
    }

    public ImageMongo setImagenMongo(MultipartFile file) throws IOException {
	ImageMongo img = new ImageMongo();
	byte[] imageByte = file.getBytes();
	img.setName(file.getOriginalFilename());
	img.setImg(Base64.getEncoder().encodeToString(imageByte));
	img.setSize(file.getSize());
	img.setContentType(file.getContentType());
	return imageMongoRepository.save(img);
    }

    @Override
    public PersonEntity updateDataToPerson(Integer id, PersonDTO personDTO) {
	PersonEntity p = this.getById(id).orElseThrow(() -> new PersonNotFoundException(id));
	return personRepository.save(mapper.toEntityUpdate(p, personDTO));

    }

    @Override
    public List<PersonEntity> listAllAgeGreaterTo(int age) {
	return personRepository.findByAgeGreaterThan(age);
    }

}
