package org.personimage.net.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonImageMongoDTO {
    private String dni;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String city;
    private String imageMongo;

}
