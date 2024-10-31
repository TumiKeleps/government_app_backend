package za.co.telkom.Authentication_Microservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "dashboard_users", indexes = {
    @Index(name = "idx_dashboard_id", columnList = "id"),
    @Index(name = "idx_dashboard_email", columnList = "email")

})
@SequenceGenerator(sequenceName="dashboard_users_seq", name="dashboard_users_seq", allocationSize=1,initialValue = 100000000)
@Data
public class DashBoardUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "dashboard_users_seq")
    private Long id;

    @NotBlank(message = "Name is required")
    private String firstName;

    @NotBlank(message = "Surname is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
