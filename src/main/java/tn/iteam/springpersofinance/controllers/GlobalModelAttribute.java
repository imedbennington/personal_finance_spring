package tn.iteam.springpersofinance.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
@ControllerAdvice
public class GlobalModelAttribute {
    @Autowired
    private HttpSession session;

    @ModelAttribute
    public void addUserToModel(Model model) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");

        // If the user is logged in, add their ID to the model for every view
        if (loggedInUserId != null) {
            model.addAttribute("loggedInUserId", loggedInUserId);
        }
    }
}
