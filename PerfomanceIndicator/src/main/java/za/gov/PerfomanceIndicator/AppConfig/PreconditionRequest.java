package za.gov.PerfomanceIndicator.AppConfig;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreconditionRequest extends RuntimeException{

    public PreconditionRequest(String errorMessage){
        super(errorMessage);
    }

}

