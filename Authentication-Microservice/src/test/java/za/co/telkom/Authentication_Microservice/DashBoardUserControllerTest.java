// package za.co.telkom.Authentication_Microservice;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import za.co.telkom.Authentication_Microservice.config.ConflictException;
// import za.co.telkom.Authentication_Microservice.config.ForbiddenException;
// import za.co.telkom.Authentication_Microservice.config.NotFoundException;
// import za.co.telkom.Authentication_Microservice.controller.DashBoardUserController;
// import za.co.telkom.Authentication_Microservice.model.DashBoardUsers;
// import za.co.telkom.Authentication_Microservice.service.DashBoardUserService;

// @WebMvcTest(DashBoardUserController.class)
// class DashBoardUserControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private DashBoardUserService userService;

//     private DashBoardUsers user;

//     @BeforeEach
//     void setUp() {
//         // Initialize a sample user
//         user = new DashBoardUsers();
//         user.setId(1L);
//         user.setFirstName("John");
//         user.setLastName("Doe");
//         user.setEmail("john.doe@example.com");
//         user.setPassword("password123");
//     }

//     private String asJsonString(final Object obj) {
//         try {
//             return new ObjectMapper().writeValueAsString(obj);
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//     }

//     // Test for successful sign-up
//     @Test
//     void testSignUp_Success() throws Exception {
//         when(userService.signUp(any(DashBoardUsers.class))).thenReturn(user);

//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(asJsonString(user)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.message").value("User signed up successfully!"));
//     }

//     // Test for sign-up with existing email
//     @Test
//     void testSignUp_EmailAlreadyExists() throws Exception {
//         when(userService.signUp(any(DashBoardUsers.class))).thenThrow(new ConflictException("USER ALREADY EXISTS"));

//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(asJsonString(user)))
//                 .andExpect(status().isConflict())
//                 .andExpect(jsonPath("$.errorCode").value(409))
//                 .andExpect(jsonPath("$.message").value("USER ALREADY EXISTS"));
//     }

//     // Test for sign-up with invalid data
//     @Test
//     void testSignUp_InvalidData() throws Exception {
//         // Set invalid email
//         user.setEmail("invalid-email");

//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(asJsonString(user)))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.email").value("Email should be valid"));
//     }

//     // Test for successful login
//     @Test
//     void testLogin_Success() throws Exception {
//         when(userService.login(user.getEmail(), user.getPassword())).thenReturn(user);

//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
//                 .param("username", user.getEmail())
//                 .param("password", user.getPassword()))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value(user.getFirstName()))
//                 .andExpect(jsonPath("$.surname").value(user.getLastName()))
//                 .andExpect(jsonPath("$.email").value(user.getEmail()));
//     }

//     // Test for login with incorrect credentials
//     @Test
//     void testLogin_IncorrectCredentials() throws Exception {
//         when(userService.login(user.getEmail(), "wrongpassword"))
//                 .thenThrow(new ForbiddenException("PASSWORD OR EMAIL INCORRECT"));

//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
//                 .param("username", user.getEmail())
//                 .param("password", "wrongpassword"))
//                 .andExpect(status().isForbidden())
//                 .andExpect(jsonPath("$.errorCode").value(403))
//                 .andExpect(jsonPath("$.message").value("PASSWORD OR EMAIL INCORRECT"));
//     }

//     // Test for login with missing parameters
//     @Test
//     void testLogin_MissingParameters() throws Exception {
//         mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
//                 .param("username", user.getEmail()))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.erroCode").value("400"))
//                 .andExpect(jsonPath("$.message").value("BAD REQUEST"));
//     }

//     // Test for successful update
//     @Test
//     void testUpdateUser_Success() throws Exception {
//         DashBoardUsers updatedUser = new DashBoardUsers();
//         updatedUser.setId(1L);
//         updatedUser.setFirstName("Jane");
//         updatedUser.setLastName("Smith");
//         updatedUser.setEmail("jane.smith@example.com");
//         updatedUser.setPassword("newpassword");

//         when(userService.updateUser(eq(1L), any(DashBoardUsers.class))).thenReturn(updatedUser);

//         mockMvc.perform(MockMvcRequestBuilders.put("/api/auth/users/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(asJsonString(updatedUser)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.firstName").value("Jane"))
//                 .andExpect(jsonPath("$.lastName").value("Smith"))
//                 .andExpect(jsonPath("$.email").value("jane.smith@example.com"));
//     }

//     // Test for updating a non-existent user
//     @Test
//     void testUpdateUser_UserNotFound() throws Exception {
//         when(userService.updateUser(eq(1L), any(DashBoardUsers.class)))
//                 .thenThrow(new NotFoundException("User not found"));

//         mockMvc.perform(MockMvcRequestBuilders.put("/api/auth/users/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(asJsonString(user)))
//                 .andExpect(status().isNotFound())
//                 .andExpect(jsonPath("$.errorCode").value(404))
//                 .andExpect(jsonPath("$.message").value("USER NOT FOUND"));
//     }

//     // Test for successful deletion
//     @Test
//     void testDeleteUser_Success() throws Exception {
//         doNothing().when(userService).deleteUser(1L);

//         mockMvc.perform(MockMvcRequestBuilders.delete("/api/auth/users/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.message").value("User deleted successfully"));
//     }

//     // Test for retrieving an existing user
//     @Test
//     void testGetUserById_Success() throws Exception {
//         when(userService.getUserById(1L)).thenReturn(Optional.of(user));

//         mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/users/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.email").value(user.getEmail()))
//                 .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
//                 .andExpect(jsonPath("$.lastName").value(user.getLastName()));
//     }

//     // Test for retrieving a non-existent user
//     @Test
//     void testGetUserById_UserNotFound() throws Exception {
//         when(userService.getUserById(1L)).thenReturn(Optional.empty());

//         mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/users/1"))
//                 .andExpect(status().isNotFound());
//     }
// }
