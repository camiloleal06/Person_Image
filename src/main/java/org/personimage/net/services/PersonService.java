package org.personimage.net.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.PersonEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {

    List<PersonEntity> listAll();

    List<PersonEntity> listAllAgeGreaterTo(int age);

    Optional<PersonEntity> getById(Integer id);

    boolean existsById(Integer id);

    void deletePerson(Integer id);

    PersonEntity savePersonWithImageMongo(PersonDTO personDTO, MultipartFile file) throws IOException;

    PersonEntity updateImageToPerson(Integer id, MultipartFile file) throws IOException;

    PersonEntity updateDataToPerson(Integer id, PersonDTO personDTO) throws IOException;

    PersonImageMongoDTO getPersonImageMongo(int id);

    List<PersonImageMongoDTO> listAllDTo();

}
