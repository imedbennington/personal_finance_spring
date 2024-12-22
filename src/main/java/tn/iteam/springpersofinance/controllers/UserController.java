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
import tn.iteam.springpersofinance.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-login-page")
    public String logging(Model model) {
        model.addAttribute("content", "users_views/login");
        return "layouts/layouts";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        try {
            String loggedInUser = userService.login(email, password, session);
            model.addAttribute("message", "Login successful!");
            return "redirect:/get-user-profile"; // Redirect to the user profile or dashboard
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/get-login-page"; // Return to login page with error
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

    @GetMapping("/get-user-profile")
    public String showUserprofile(Model model) {
        // Set the content for the main section dynamically
        model.addAttribute("content", "users_views/user_profile");
        return "layouts/layouts"; // Return the layout template
    }
    /*public String userProfile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");

        if (userId == null) {
            return "redirect:/get-login-page"; // Redirect to login if no user is logged in
        }
        model.addAttribute("content", "users_views/user_profile");
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        return "layouts/layouts"; // View for user profile
    }*/
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
