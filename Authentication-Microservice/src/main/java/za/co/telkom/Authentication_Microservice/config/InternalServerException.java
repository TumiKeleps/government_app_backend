package za.co.telkom.Authentication_Microservice.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException{

    public InternalServerException(String errorMessage){
        super(errorMessage);
    }


}