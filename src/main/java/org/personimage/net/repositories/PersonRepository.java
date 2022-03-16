package org.personimage.net.repositories;

import org.personimage.net.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    /*
     * @Query("SELECT new org.personimage.net.dtos.GetPersonDTO(" +
     * "p.firstName, p.lastName, p.email, p.phone, p.city, i.img)" +
     * " FROM PersonEntity p JOIN p.image i where p.dni=?1") // public GetPersonDTO
     * findPersonImageByDni(String dni);
     */

}
