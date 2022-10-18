package com.spring.shop.controllers;

import com.spring.shop.models.Item;
import com.spring.shop.models.MyUser;
import com.spring.shop.repository.ItemRepository;
import com.spring.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/item/add")
    public String add() {
        return "add-item";
    }

    @PostMapping("/item/add")
    public String store(@AuthenticationPrincipal MyUser myUser,
                        @RequestParam String title,
                        @RequestParam String image,
                        @RequestParam String price,
                        @RequestParam String info,
                        @RequestParam String phoneNumber) {
        Item item = new Item(title, info, image, Float.parseFloat(price), myUser, phoneNumber);
        itemRepository.save(item);
        return "redirect:/";
    }
    @GetMapping("/item/{id}")
    public String showItem(@AuthenticationPrincipal MyUser myUser,
            @PathVariable(value = "id") long id, Model model) {
        Item item = itemRepository.findById(id).orElse(new Item());
        model.addAttribute("item", item);
        if (myUser == null) model.addAttribute("bool", false);
        else model.addAttribute("bool", myUser.equals(item.getUser()));
        return "show-item";
    }
    @GetMapping("/item/{id}/update")
    public String updateItem(@PathVariable(value = "id") long id, Model model) {
        Item item = itemRepository.findById(id).orElse(new Item());
        model.addAttribute("item", item);
        return "update-item";
    }
    @PostMapping("/item/{id}/update")
    public String updateStore(@PathVariable(value = "id") long id,
                              @RequestParam String title,
                              @RequestParam String image,
                              @RequestParam String price,
                              @RequestParam String phoneNumber,
                              @RequestParam String info){
        Item item = itemRepository.findById(id).orElse(new Item());
        item.setTitle(title);
        item.setImage(image);
        item.setPrice(Float.parseFloat(price));
        item.setInfo(info);
        item.setPhoneNumber(phoneNumber);
        //itemRepository.deleteById(id);
        itemRepository.save(item);
        return "redirect:/item/"+id;
    }
    @PostMapping("/item/{id}/delete")
    public String deleteItem(@PathVariable(value = "id") long id) {
        Item item = itemRepository.findById(id).orElse(new Item());
        itemRepository.delete(item);
        return "redirect:/";
    }
    @GetMapping("/item_user")
    public String showUserItem(@AuthenticationPrincipal MyUser user,
                               Model model) {
        if (user!=null)
            {model.addAttribute("items", user.getItems());
            return "item-user";}
        else return "redirect:/";
    }
}
