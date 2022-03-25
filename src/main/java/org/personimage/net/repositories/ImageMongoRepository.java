package org.personimage.net.repositories;

import org.personimage.net.entities.ImageMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMongoRepository extends MongoRepository<ImageMongo, String> {

}
