package kea.wishlist.controller;

import jakarta.servlet.http.HttpSession;
import kea.wishlist.dto.UserDTO;
import kea.wishlist.model.User;
import kea.wishlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registerForm";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) throws SQLException {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute("user") UserDTO userDTO, HttpSession httpSession,
                               Model model) throws SQLException {
        User authenticatedUser = userService.authenticate(userDTO);
        if(authenticatedUser != null){
            int userId = authenticatedUser.getId();
            httpSession.setAttribute("userId", userId);
            model.addAttribute("userId", userId);
            return "redirect:/" + userId + "/wishlist";
        }else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

}
