package org.personimage.net.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.personimage.net.dtos.PersonDTO;
import org.personimage.net.dtos.PersonImageMongoDTO;
import org.personimage.net.entities.PersonEntity;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);


    PersonEntity toEntity(PersonDTO personDTO);

    PersonDTO toDTO(PersonEntity personEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageMongo", ignore = true)

    PersonEntity toEntityUpdate(@MappingTarget PersonEntity person, PersonDTO personDTO);

    PersonImageMongoDTO toPersonImageDTO(PersonEntity personEntity);

    @Mapping(target = "id", ignore = true)
    PersonEntity toEntity(PersonDTO personDTO, String imageMongo);

    List<PersonImageMongoDTO> toListDTO(List<PersonEntity> listPersonImagenDTO);

    PersonImageMongoDTO toPersonImageMongoDTO(PersonEntity person, String img);

}