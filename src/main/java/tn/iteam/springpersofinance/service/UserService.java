package tn.iteam.springpersofinance.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.iteam.springpersofinance.dto.UserRegisterDto;
import tn.iteam.springpersofinance.entities.User;
import tn.iteam.springpersofinance.exceptions.DuplicateEmailException;
import tn.iteam.springpersofinance.repositories.UserRepository;
@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserRegisterDto userDTO) throws DuplicateEmailException {
        // Check if a user with the provided email already exists
        if (userRepository.findByemail(userDTO.getEmail()) != null) {
            throw new DuplicateEmailException("Email " + userDTO.getEmail() + " is already in use.");
        }

        // Create a new user and set the properties
        User user = new User();
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


    /*public User login(String email, String password, HttpSession session) {
        User user = userRepository.findByemail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Store user info in the session
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());

        return user;
    }*/

    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        try {
            User user = userRepository.findByemail(email);
            session.setAttribute("loggedInUserName", user.getFirstName());
            session.setAttribute("loggedInUserId", user.getId());
            return "redirect:/get-user-profile"; // Redirect to the dashboard or home page
        } catch (IllegalArgumentException ex) {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/login";
    }

}
