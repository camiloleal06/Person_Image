package org.personimage.net.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.entities.PersonEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {

    List<PersonEntity> listAll();

    PersonEntity savePerson(PersonDTO personDTO);

    Optional<PersonEntity> getById(Integer id);

    boolean existsById(Integer id);

    void deletePerson(Integer id);

    PersonEntity savePerson(PersonDTO personDTO, MultipartFile file) throws IOException;

    PersonEntity updatePerson(Integer id, PersonDTO personDTO) throws IOException;

    PersonEntity savePersonWithImageMongo(PersonDTO personDTO, MultipartFile file) throws IOException;

}
