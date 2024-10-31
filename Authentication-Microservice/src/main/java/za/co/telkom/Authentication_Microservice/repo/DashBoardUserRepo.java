package za.co.telkom.Authentication_Microservice.repo;
import za.co.telkom.Authentication_Microservice.model.DashBoardUsers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DashBoardUserRepo extends JpaRepository<DashBoardUsers, Long> 
{
    
    Optional<DashBoardUsers> findByEmail(String email);
}
