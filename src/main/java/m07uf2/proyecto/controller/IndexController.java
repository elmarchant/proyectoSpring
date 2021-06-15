package m07uf2.proyecto.controller;

import m07uf2.proyecto.model.Message;
import m07uf2.proyecto.model.Usuario;
import m07uf2.proyecto.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class IndexController {
    @Autowired
    private UserRepo repository;

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/main")
    public String main(Principal principal, Model model){
        Usuario user = repository.findByUsername(principal.getName());
        if(!repository.existsByUsername(principal.getName())){
            Message message = new Message(
                    "Error",
                    "El usuario no existe.",
                    "/login"
            );

            model.addAttribute("message", message);

            return "user/status";
        }
        model.addAttribute("userData", user);

        return "main";
    }
}
