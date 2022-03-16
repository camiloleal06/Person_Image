package org.personimage.net.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.ImageEntity;
import org.personimage.net.entities.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageMongo", ignore = true)
    PersonEntity toEntity(PersonDTO personDTO, ImageEntity image);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    PersonEntity toEntity(PersonDTO personDTO, String imageMongo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "imageMongo", ignore = true)
    PersonEntity toEntity(PersonDTO personDTO);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "imageMongo", ignore = true)
    PersonEntity toEntity(PersonDTO personDTO, Integer id);

    PersonDTO toDTO(PersonEntity person);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "old", ignore = true)
    @Mapping(target = "image", ignore = true)
    PersonEntity toEntity(PersonImageMongoDTO personImageMongoDTO);

    PersonImageMongoDTO toGetPersonDTO(PersonEntity personEntity);

}