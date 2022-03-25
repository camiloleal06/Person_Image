package org.personimage.net.repositories;

import java.util.List;

import org.personimage.net.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    List<PersonEntity> findByAgeGreaterThan(int age);

}
