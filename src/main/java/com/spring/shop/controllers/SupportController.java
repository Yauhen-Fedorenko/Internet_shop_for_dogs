package com.spring.shop.controllers;

import com.spring.shop.models.MyUser;
import com.spring.shop.models.Support;
import com.spring.shop.repository.SupportRepository;
import com.spring.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.Set;

@Controller
public class SupportController {
    @Autowired
    private SupportRepository supportRepository;


    @GetMapping("/support")
    public String getPageSupport(@AuthenticationPrincipal MyUser myUser,
            Model model) {
        model.addAttribute("user", myUser);
        return "support";
    }
    @GetMapping("/support/ask")
    public String getQuestion(@AuthenticationPrincipal MyUser user,
                              @RequestParam String question
                              ) {
        if (question.isEmpty()) return "redirect:/support";
        Support support = new Support(question, "", user);
        supportRepository.save(support);
        return "redirect:/support";
    }

}
