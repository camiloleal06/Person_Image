package org.personimage.net.exceptions;

import java.time.LocalDateTime;

import lombok.*;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor

public class ExceptionMessage {
    @NonNull
    private HttpStatus status;
    @NonNull
    private Integer code;
    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime date = LocalDateTime.now();
    @NonNull
    private String message;

}