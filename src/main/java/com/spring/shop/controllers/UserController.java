package com.spring.shop.controllers;


import com.spring.shop.models.MyUser;
import com.spring.shop.models.Role;
import com.spring.shop.random.RandomPass;
import com.spring.shop.repository.UserRepository;
import com.spring.shop.sendEmail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

import static com.spring.shop.models.Role.*;
import static com.spring.shop.models.Role.USER;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;
    private RandomPass randomPass = new RandomPass();

    @GetMapping("/login")
    public String signIn(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error!=null) model.addAttribute("error", "Login or password isn't correct");
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model, @RequestParam(name = "error", defaultValue = "", required = false) String error) {
        if (error.equals("username")) model.addAttribute("error", "This login already exists");
        if (error.equals("passwordEquals")) model.addAttribute("error", "Password isn't equals");
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String passwordCheck,
                          Model model) {
        if (userRepository.findByUsername(username) != null) return "redirect:/registration?error=username";
        if (!password.equals(passwordCheck)) return "redirect:/registration?error=passwordEquals";
        MyUser user = new MyUser(username, passwordEncoder.encode(password), email, Collections.singleton(Role.USER),
                true);
        user.setActivationCode(UUID.randomUUID().toString());
//        Set<Role> roles = new HashSet<>();
//        roles.add(USER);
//        roles.add(REDACTOR);
//        roles.add(ADMIN);
//        user.setRole(roles);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String getUserCabinet() {
        return "user";
    }

    @GetMapping("/user/update")
    public String updateUser(Principal principal, Model model,
                             @RequestParam(name = "error", defaultValue = "", required = false)String error) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //MyUser user = userRepository.findByUsername(authentication.getName());
        if (error.equals("username")) model.addAttribute("error", "This login already exists");
        if (error.equals("passwordEquals")) model.addAttribute("error", "Password isn't equals");
        MyUser user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "update-user";
    }
    @PostMapping("/user/update")
    public String updateUs(
            Principal principal,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String passwordCheck
    ) {
        if (userRepository.findByUsername(username) != null&&!username.equals(principal.getName())) return "redirect:/user/update?error=username";
        if (!password.equals(passwordCheck)) return "redirect:/user/update?error=passwordEquals";
        MyUser myUser = userRepository.findByUsername(principal.getName());
        myUser.setUsername(username);
        myUser.setEmail(email);
        myUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(myUser);
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/forgot_pass")
    public String forgotPass(@RequestParam (name = "error", defaultValue = "", required = false) String error,
                             Model model) {
        if (error.equals("1"))
            model.addAttribute("error", "Пользователя с таким логином не существует");
        if (error.equals("2"))
            model.addAttribute("error", "Необходимо перейти по ссылке из письма");
        if (error.equals("3"))
            model.addAttribute("error", "Инструкции по смене пароля отправлены Вам на почту");
        if (error.equals("4"))
            model.addAttribute("error", "Пароль успешно изменен, новый пароль направлен Вам на почту. \n " +
                    "Перейдите на ссылку авторизации и авторизируйтесь используя новый пароль.");
        return "forgot_pass";
    }

    @PostMapping("/forgot_pass/login")
    public String recoveryPass(@RequestParam String username, Model model) {
        MyUser myUser = userRepository.findByUsername(username);
        if (myUser==null) {return "redirect:/forgot_pass?error=1";}
        myUser.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(myUser);
        String message = String.format("Здравствуйте, %s! \n    для подтверждения смены пароля пройдите по ссылке link:http://localhost:8080/forgot_pass/%s \n" +
                "после перехода по ссылке ваш пароль будет изменен. Если вы не отправляли запрос на смену пароля, просто проигнорируйте это сообщение.",
                myUser.getUsername(), myUser.getActivationCode());
        mailSender.send(myUser.getEmail(), "Password", message);
        return "redirect:/forgot_pass?error=3";
    }
    @GetMapping("/forgot_pass/{code}")
    public String changePass(Model model, @PathVariable String code) {
        MyUser user = userRepository.findByActivationCode(code);

        if (user == null) return "redirect:/forgot_pass?error=2";

        user.setActivationCode(null);
        String newPass = randomPass.getNewPassword();
        mailSender.send(user.getEmail(), "Новый пароль", "Ваш пароль был успешно изменен на пароль: "+newPass);
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return "redirect:/forgot_pass?error=4";
    }
}
