package com.controllers;

/**
 * Created by Asad on 1/30/2019.
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "account")
public class AccountController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "indexx";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            ModelMap modelMap) {
        if(username.equalsIgnoreCase("awan") && password.equalsIgnoreCase("0000")) {
            session.setAttribute("username", username);
            return "success";
        } else {
            modelMap.put("error", "Invalid Account");
            return "indexx";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:../account";
    }

}
