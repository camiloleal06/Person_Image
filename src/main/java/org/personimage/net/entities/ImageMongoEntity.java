package org.personimage.net.entities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document(collection = "images")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ImageMongoEntity {
    @Id
    private String id;
    private String name;
    private String img;
    private long size;
    private String contentType;
}
