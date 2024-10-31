
package za.co.telkom.Authentication_Microservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.telkom.Authentication_Microservice.config.ConflictException;
import za.co.telkom.Authentication_Microservice.config.ForbiddenException;
import za.co.telkom.Authentication_Microservice.config.NotFoundException;
import za.co.telkom.Authentication_Microservice.model.DashBoardUsers;
import za.co.telkom.Authentication_Microservice.repo.DashBoardUserRepo;

@Service
public class DashBoardUserService {

    @Autowired
    private DashBoardUserRepo userRepository;

    // Sign-up
    public DashBoardUsers signUp(DashBoardUsers user)  {
        Optional<DashBoardUsers> existingUserByEmail = userRepository.findByEmail(user.getEmail());

        if (existingUserByEmail.isPresent()) {
            throw new ConflictException("USER ALREADY EXISTS");
        }
   
        return userRepository.save(user);  // Save user in DB
    }

    // Login
    public DashBoardUsers login(String email, String password) throws ForbiddenException, NotFoundException {
        Optional<DashBoardUsers> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user.get();  // Successful login
            } else {
                throw new ForbiddenException("PASSWORD OR EMAIL INCORRECT");
            }
        } else {
            throw new NotFoundException("USER DOESNT EXIST");
            
        }
    }

    // CRUD operations
    public DashBoardUsers updateUser(Long id, DashBoardUsers userDetails) {
        DashBoardUsers user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<DashBoardUsers> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
