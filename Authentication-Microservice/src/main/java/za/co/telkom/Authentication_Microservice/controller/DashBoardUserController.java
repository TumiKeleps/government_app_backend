
package za.co.telkom.Authentication_Microservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import za.co.telkom.Authentication_Microservice.config.BadRequest;
import za.co.telkom.Authentication_Microservice.config.ConflictException;
import za.co.telkom.Authentication_Microservice.config.ForbiddenException;
import za.co.telkom.Authentication_Microservice.config.InternalServerException;
import za.co.telkom.Authentication_Microservice.config.NotFoundException;
import za.co.telkom.Authentication_Microservice.model.DashBoardUsers;
import za.co.telkom.Authentication_Microservice.model.SignIn;
import za.co.telkom.Authentication_Microservice.service.DashBoardUserService;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*", allowCredentials = "true")
//@CrossOrigin(origins = "*")

@CrossOrigin(
    origins = "*",
    allowedHeaders = {"x-api-key", "Content-Type"},
    methods = {RequestMethod.GET, RequestMethod.POST}
)

@Slf4j
public class DashBoardUserController {

    @Autowired
    private DashBoardUserService userService;

   
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody DashBoardUsers user) {
        try {
            userService.signUp(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User signed up successfully!");
            return ResponseEntity.ok(response);
        } catch (ConflictException e) {
            return handleConflictException(e);
            
        }
    }
    // Login endpoint
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody SignIn request) {
        try {
            DashBoardUsers user = userService.login(request.getUsername(), request.getPassword());
            Map<String, Object> response = new HashMap<>();
            response.put("name", user.getFirstName());
            response.put("surname", user.getLastName());
            response.put("email", user.getEmail());
            response.put("id" , user.getId());
            return ResponseEntity.ok(response);
        } catch (ForbiddenException e) {
            return handleForbiddenException(e);
        } catch (NotFoundException e) {
            return handleNotFoundException(e);
        }
        catch(BadRequest e)
        {
            return handleBadRequest(e);
        }
         catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(500, e.getMessage()));
        }
    }

    // CRUD operations
    @PutMapping("/users/{id}")
    public ResponseEntity<DashBoardUsers> updateUser(@PathVariable Long id, @RequestBody DashBoardUsers userDetails) {
        DashBoardUsers updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<DashBoardUsers> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(userService.getUserById(id));
    }

    

    // Exception Handlers
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbiddenException(ForbiddenException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(buildErrorResponse(403, "PASSWORD OR EMAIL INCORRECT"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(buildErrorResponse(404, "USER NOT FOUND"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerException(InternalServerException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(buildErrorResponse(500, "INTERNAL SERVER ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<Map<String,Object>> handleBadRequest(BadRequest ex)
    {
        log.error(ex.getMessage());
        return new ResponseEntity<>(buildErrorResponse(400, "BAD REQUEST"), HttpStatus.BAD_REQUEST);
        
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflictException(ConflictException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(buildErrorResponse(409, "USER ALREADY EXISTS"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMissingParametersExceptions(MissingServletRequestParameterException ex) {
        
        return new ResponseEntity<>(Map.of("erroCode","400","message","BAD REQUEST"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    // Helper method to build error response
    private Map<String, Object> buildErrorResponse(int errorCode, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", errorCode);
        response.put("message", message);
        return response;
    }
}
