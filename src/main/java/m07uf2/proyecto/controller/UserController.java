package m07uf2.proyecto.controller;

import m07uf2.proyecto.model.Message;
import m07uf2.proyecto.model.Usuario;
import m07uf2.proyecto.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Controller
public class UserController {
    @Autowired
    private UserRepo repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/user/register")
    public String register(){
        return "/user/register";
    }

    @PostMapping("/user/register/save")
    public String save(
            @RequestParam(required = true) String nombre,
            @RequestParam(required = true) String apellido,
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password,
            @RequestParam(required = true) String repassword,
            Model model
    ){
        Message message = new Message("Éxito", "Se ha registrado correctamente.", "/");

        if(password.equals(repassword)){
            Usuario user = new Usuario(nombre, apellido, username, encoder.encode(password));

            if (repository.existsByUsername(username)){
                message.setAllMessage(
                        "Error",
                        "El nombre de usuario ya existe.",
                        "/user/register"
                );
            }else{
                repository.save(user);
            }
        }else{
            message.setAllMessage(
                    "Error",
                    "Las contraseñas no coinciden.",
                    "/user/register"
            );
        }

        model.addAttribute("message", message);

        return "/user/status";
    }

    @GetMapping("/user/profile")
    public String profile(Model model, Principal principal){
        Usuario user = repository.findByUsername(principal.getName());
        model.addAttribute("profile", user);
        return "user/profile";
    }

    @GetMapping("/user/profile/update")
    public String update(Model model, Principal principal){
        Usuario user = repository.findByUsername(principal.getName());
        model.addAttribute("profile", user);
        return "user/update";
    }

    @PostMapping("/user/profile/update/nombre")
    public String updateNombre(@RequestParam(required = true) String nombre, Principal principal){
        Usuario user = repository.findByUsername(principal.getName());

        user.setNombre(nombre);
        repository.save(user);

        return "redirect:/user/profile";
    }

    @PostMapping("/user/profile/update/apellido")
    public String updateApellido(@RequestParam(required = true) String apellido, Principal principal){
        Usuario user = repository.findByUsername(principal.getName());

        user.setApellido(apellido);
        repository.save(user);

        return "redirect:/user/profile";
    }

    @PostMapping("/user/profile/update/password")
    public String updatePassword(
            @RequestParam(required = true) String password,
            @RequestParam(required = true) String repassword,
            Model model,
            Principal principal
    ){
        Message message = new Message(
                "Éxito",
                "Se ha guardado la nueva contraseña.",
                "/logout"
        );

        Usuario user = repository.findByUsername(principal.getName());

        if(password.equals(repassword)){
            user.setPassword(encoder.encode(password));
            repository.save(user);
        }else{
            message.setAllMessage(
                    "Error",
                    "Las contraseñas no coinciden. No se ha podido guardar.",
                    "/user/profile/update"
            );
        }

        model.addAttribute("message", message);

        return "user/status";
    }
}
