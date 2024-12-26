package tn.iteam.springpersofinance.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.iteam.springpersofinance.dto.UserRegisterDto;
import tn.iteam.springpersofinance.entities.User;
import tn.iteam.springpersofinance.exceptions.DuplicateEmailException;
import tn.iteam.springpersofinance.repositories.UserRepository;
import tn.iteam.springpersofinance.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/get-login-page")
    public String logging(Model model) {
        model.addAttribute("content", "users_views/login");
        return "layouts/layouts";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        try {
            // Call the UserService to handle the login process
            String loggedInUser = userService.login(email, password, session);

            // Add success message and redirect
            model.addAttribute("message", "Login successful!");
            return "redirect:/get-user-profile"; // Redirect to the user profile or dashboard

        } catch (IllegalArgumentException ex) {
            // If login fails, add the error message to the model
            model.addAttribute("error", ex.getMessage());
            return "redirect:/get-login-page"; // Redirect back to the login page with an error
        }
    }
/*    @GetMapping("get-register-page")
    public String register(Model model) {
        model.addAttribute("content", "content");
        return "users_views/register";
    }*/
@GetMapping("/get-register-page")
public String showRegisterForm(Model model) {
    // Set the content for the main section dynamically
    model.addAttribute("content", "users_views/register");
    return "layouts/layouts"; // Return the layout template
}

    /*@GetMapping("/get-user-profile")
    public String showUserprofile(Model model) {

        // Set the content for the main section dynamically
        model.addAttribute("content", "users_views/user_profile");
        return "layouts/layouts"; // Return the layout template
    }*/

/*    @GetMapping("/get-user-profile")
    public String getUserProfile(HttpSession session, Model model) {
        // Retrieve the logged-in user's info from the session
        String loggedInUserName = (String) session.getAttribute("loggedInUserName");
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        // Add the user info to the model
        model.addAttribute("loggedInUserName", loggedInUserName);
        model.addAttribute("loggedInUserId", loggedInUserId);
        model.addAttribute("content", "users_views/user_profile");

        // You can also fetch other user data from the database if needed
        User user = userRepository.findById(loggedInUserId).orElse(null);
        model.addAttribute("user", user);

        return "layouts/layouts"; // Return the profile view
    }*/
@GetMapping("/get-user-profile")
public String getUserProfile(HttpSession session, Model model) {
    // Retrieve the logged-in user's info from the session
    String loggedInUserName = (String) session.getAttribute("loggedInUserName");
    Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

    // Add the user info to the model
    model.addAttribute("loggedInUserName", loggedInUserName);
    model.addAttribute("loggedInUserId", loggedInUserId);
    model.addAttribute("content", "users_views/user_profile");

    // You can also fetch other user data from the database if needed
    User user = userRepository.findById(loggedInUserId).orElse(null);
    model.addAttribute("user", user);

    return "layouts/layouts"; // Return the profile view
}

    @PostMapping("/register")
   public String registerUser(@ModelAttribute UserRegisterDto userDTO, Model model) {
        try {
            userService.register(userDTO);
            model.addAttribute("successMessage", "Registration successful!");
            return "redirect:/get-login-page";
        } catch (DuplicateEmailException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "layouts/layouts";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error during registration: " + e.getMessage());
            return "layouts/layouts";
        }
    }

/*public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userDTO) {
    try {
        User user = userService.register(userDTO);
        return ResponseEntity.ok("Registration successful!");
    } catch (IllegalArgumentException e) {
        throw e; // This will be caught by the exception handler
    }
}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("There was an error processing your request. Please try again.");
    }*/
}
