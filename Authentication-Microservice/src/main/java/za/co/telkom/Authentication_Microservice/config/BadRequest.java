package za.co.telkom.Authentication_Microservice.config;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException{

    public BadRequest(String errorMessage){
        super(errorMessage);
    }

}


