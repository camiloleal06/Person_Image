package org.personimage.net.repositories;

import org.personimage.net.entities.ImageMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMongoRepository extends MongoRepository<ImageMongoEntity, String> {

}
