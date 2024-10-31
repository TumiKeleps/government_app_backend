// package za.co.telkom.Authentication_Microservice;


// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.*;
// import za.co.telkom.Authentication_Microservice.config.ConflictException;
// import za.co.telkom.Authentication_Microservice.config.ForbiddenException;
// import za.co.telkom.Authentication_Microservice.config.NotFoundException;
// import za.co.telkom.Authentication_Microservice.model.DashBoardUsers;
// import za.co.telkom.Authentication_Microservice.repo.DashBoardUserRepo;
// import za.co.telkom.Authentication_Microservice.service.DashBoardUserService;

// class DashBoardUserServiceTest {

//     @InjectMocks
//     private DashBoardUserService userService;

//     @Mock
//     private DashBoardUserRepo userRepository;

//     private DashBoardUsers user;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // Initialize a sample user
//         user = new DashBoardUsers();
//         user.setId(1L);
//         user.setFirstName("John");
//         user.setLastName("Doe");
//         user.setEmail("john.doe@example.com");
//         user.setPassword("password123");
//     }

//     // Test for successful sign-up
//     @Test
//     void testSignUp_Success() {
//         // Mock repository behavior
//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
//         when(userRepository.save(user)).thenReturn(user);

//         DashBoardUsers result = userService.signUp(user);

//         assertNotNull(result);
//         assertEquals(user.getEmail(), result.getEmail());

//         // Verify that save was called
//         verify(userRepository, times(1)).save(user);
//     }

//     // Test for sign-up with existing email
//     @Test
//     void testSignUp_EmailAlreadyExists() {
//         // Mock repository behavior
//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

//         assertThrows(ConflictException.class, () -> {
//             userService.signUp(user);
//         });

//         // Verify that save was not called
//         verify(userRepository, never()).save(any(DashBoardUsers.class));
//     }

//     // Test for successful login
//     @Test
//     void testLogin_Success() {
//         // Mock repository behavior
//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

//         DashBoardUsers result = userService.login(user.getEmail(), user.getPassword());

//         assertNotNull(result);
//         assertEquals(user.getEmail(), result.getEmail());

//         // Verify that findByEmail was called
//         verify(userRepository, times(1)).findByEmail(user.getEmail());
//     }

//     // Test for login with non-existent email
//     @Test
//     void testLogin_UserNotFound() {
//         // Mock repository behavior
//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

//         assertThrows(NotFoundException.class, () -> {
//             userService.login(user.getEmail(), user.getPassword());
//         });

//         // Verify that findByEmail was called
//         verify(userRepository, times(1)).findByEmail(user.getEmail());
//     }

//     // Test for login with incorrect password
//     @Test
//     void testLogin_IncorrectPassword() {
//         // Mock repository behavior
//         when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

//         assertThrows(ForbiddenException.class, () -> {
//             userService.login(user.getEmail(), "wrongpassword");
//         });

//         // Verify that findByEmail was called
//         verify(userRepository, times(1)).findByEmail(user.getEmail());
//     }

//     // Test for successful update
//     @Test
//     void testUpdateUser_Success() {
//         DashBoardUsers updatedUserDetails = new DashBoardUsers();
//         updatedUserDetails.setFirstName("Jane");
//         updatedUserDetails.setLastName("Smith");
//         updatedUserDetails.setEmail("jane.smith@example.com");
//         updatedUserDetails.setPassword("newpassword");

//         // Mock repository behavior
//         when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//         when(userRepository.save(any(DashBoardUsers.class))).thenReturn(updatedUserDetails);

//         DashBoardUsers result = userService.updateUser(user.getId(), updatedUserDetails);

//         assertNotNull(result);
//         assertEquals("Jane", result.getFirstName());
//         assertEquals("Smith", result.getLastName());
//         assertEquals("jane.smith@example.com", result.getEmail());

//         // Verify interactions
//         verify(userRepository, times(1)).findById(user.getId());
//         verify(userRepository, times(1)).save(any(DashBoardUsers.class));
//     }

//     // Test for updating a non-existent user
//     @Test
//     void testUpdateUser_UserNotFound() {
//         DashBoardUsers updatedUserDetails = new DashBoardUsers();
//         updatedUserDetails.setFirstName("Jane");
//         updatedUserDetails.setLastName("Smith");
//         updatedUserDetails.setEmail("jane.smith@example.com");
//         updatedUserDetails.setPassword("newpassword");

//         // Mock repository behavior
//         when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

//         assertThrows(NotFoundException.class, () -> {
//             userService.updateUser(user.getId(), updatedUserDetails);
//         });

//         // Verify interactions
//         verify(userRepository, times(1)).findById(user.getId());
//         verify(userRepository, never()).save(any(DashBoardUsers.class));
//     }

//     // Test for successful deletion
//     @Test
//     void testDeleteUser() {
//         // No exception should be thrown
//         doNothing().when(userRepository).deleteById(user.getId());

//         userService.deleteUser(user.getId());

//         // Verify that deleteById was called
//         verify(userRepository, times(1)).deleteById(user.getId());
//     }

//     // Test for retrieving an existing user
//     @Test
//     void testGetUserById_Success() {
//         // Mock repository behavior
//         when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

//         Optional<DashBoardUsers> result = userService.getUserById(user.getId());

//         assertTrue(result.isPresent());
//         assertEquals(user.getEmail(), result.get().getEmail());

//         // Verify that findById was called
//         verify(userRepository, times(1)).findById(user.getId());
//     }

//     // Test for retrieving a non-existent user
//     @Test
//     void testGetUserById_UserNotFound() {
//         // Mock repository behavior
//         when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

//         Optional<DashBoardUsers> result = userService.getUserById(user.getId());

//         assertFalse(result.isPresent());

//         // Verify that findById was called
//         verify(userRepository, times(1)).findById(user.getId());
//     }
// }
