package com.spring.shop.controllers;


import com.spring.shop.models.MyUser;
import com.spring.shop.models.Role;
import com.spring.shop.models.Support;
import com.spring.shop.repository.ItemRepository;
import com.spring.shop.repository.SupportRepository;
import com.spring.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    SupportRepository supportRepository;

    @GetMapping("/panel_admin")
    public String getPanel(Model model){
        Iterable<MyUser> myUsers = userRepository.findAll();
        model.addAttribute("myUsers", myUsers);
        return "panel-admin";
    }
    @GetMapping("/panel_admin/items/{id}")
    public String getItemsOfUser(@PathVariable(value = "id") long id, Model model){
            MyUser myUser = userRepository.findById(id).orElse(new MyUser());
            model.addAttribute("user", myUser);
        return "itemsOfUser";
    }
    @GetMapping("/panel_admin/change_roles/{id}")
    public String changeRoles(@PathVariable(value = "id") long id, Model model) {
        MyUser myUser = userRepository.findById(id).orElse(new MyUser());
        model.addAttribute("user", myUser);
        return "admin-change-roles";
    }
    @PostMapping( "/panel_admin/change/{id}/update")
    public String updateChanges(@PathVariable(value = "id") long id,
                                @RequestParam(value = "admin", required = false, defaultValue = "no") String admin,
                                @RequestParam(value = "redactor", required = false, defaultValue = "no") String redactor) {
        MyUser user = userRepository.findById(id).orElse(new MyUser());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (redactor.equals("yes")) roles.add(Role.REDACTOR);
        if (admin.equals("yes")) {roles.add(Role.ADMIN);roles.add(Role.REDACTOR);}
        user.setRole(roles);
        userRepository.save(user);
        return "redirect:/panel_admin";
    }
    @GetMapping("/panel_admin/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id,
                             @AuthenticationPrincipal MyUser myUser) {
        MyUser user = userRepository.findById(id).orElse(new MyUser());
        if (user.getId()== myUser.getId()||user.getUsername().equals("Admin")) return "redirect:/panel_admin";
            else userRepository.delete(user);
        return "redirect:/panel_admin";
    }
    @GetMapping("/answer_support")
    public String answerSup(Model model){
        Iterable<Support> supports = supportRepository.findAll();
        model.addAttribute("supports", supports);
        return "answer_support";
    }
    @PostMapping("answer_support/{id}")
    public String addAnswer(@PathVariable(value = "id") long id,
                            @RequestParam String answer){
        Support support = supportRepository.findById(id).orElse(new Support());
        support.setAnswer(answer);
        supportRepository.save(support);
        return "redirect:/answer_support";
    }

}
