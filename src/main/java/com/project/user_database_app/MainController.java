package com.project.user_database_app;

import com.project.application.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/database")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser(@RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String accountType,
                                           @RequestParam String login, @RequestParam String password,
                                           @RequestParam String email, @RequestParam Integer deleteCode){
        User n = new User();
        n.setFirstName(firstName);
        n.setLastName(lastName);
        n.setLogin(login);
        n.setPassword(password);
        n.setAccountType(UserTypes.valueOf(accountType));
        n.setEmail(email);
        n.setDeleteCode(deleteCode);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path="/search")
    public @ResponseBody Iterable<User> searchUsers(@RequestParam String lastName){
        return userRepository.findByLastName(lastName);
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody void deleteUser(@RequestParam int id){
        userRepository.deleteById(id);
    }
}
