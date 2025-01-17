package tn.iteam.springpersofinance.service;

import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tn.iteam.springpersofinance.dto.UserRegisterDto;
import tn.iteam.springpersofinance.entities.User;
import tn.iteam.springpersofinance.exceptions.DuplicateEmailException;
import tn.iteam.springpersofinance.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserRegisterDto userDTO) throws DuplicateEmailException, IOException {
        // Check if a user with the provided email already exists
        if (userRepository.findByemail(userDTO.getEmail()) != null) {
            throw new DuplicateEmailException("Email " + userDTO.getEmail() + " is already in use.");
        }

        // Initialize a new user
        User user = new User();

        // Handle profile picture upload
/*        if (userDTO.getProfilePicture() != null && !userDTO.getProfilePicture().isEmpty()) {
            MultipartFile profilePicture = userDTO.getProfilePicture();
            String fileName = UUID.randomUUID() + "_" + profilePicture.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads/profile_pictures");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Create directories if they don't exist
            }
            Files.copy(profilePicture.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // Save relative file path in the user object
            //user.setProfilePicture("profile-pictures/" + fileName);
            user.setProfile_picture(fileName);
        }*/

        // Set other user properties
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setCountry(userDTO.getCountry());
        user.setCity(userDTO.getCity());
        user.setAddress(userDTO.getAddress());
        user.setZip_code(userDTO.getZipCode());
        user.setDate_of_birth(userDTO.getDateOfBirth());
        user.setPassword(userDTO.getPassword());

        // Save the new user to the database
        return userRepository.save(user);
    }


    // Login method that handles authentication and session management
    public String login(String email, String password, HttpSession session) {
        // Fetch user from the database
        User user = userRepository.findByemail(email);

        // If no user is found or the password is incorrect, throw an exception
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Store user information in the session
        session.setAttribute("loggedInUserName", user.getFirstName());
        session.setAttribute("loggedInUserId", user.getId());

        // Return the logged-in user's first name or any other data you want
        return user.getFirstName();
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/login";
    }


}
