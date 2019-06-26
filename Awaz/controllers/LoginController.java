package com.controllers;

import com.Beans.Database;
import com.Beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Asad on 4/10/2019.
 */
@Controller
@SessionAttributes("user")
public class LoginController {

    @ModelAttribute("user")
    public User setUpUserForm() {
        return new User();
    }
/*
    @PostMapping("/dologin")
    public String doLogin(@ModelAttribute("user") User user, Model model) {

        // Implement your business logic
        if (user.getEmail().equals("sunil@example.com") && user.getPassword().equals("abc@123")) {
            // Set user dummy data
            user.setFname("Sunil");
            user.setMname("Singh");
            user.setLname("Bora");
            user.setAge(28);
        } else {
            model.addAttribute("message", "Login failed. Try again.");
            return "index";
        }
        return "success";
    }
*/
Database db = new Database();
    @RequestMapping("/citizenloginn")
    public ModelAndView citizennloginn(@ModelAttribute("user") User user) {
        //session = request.getSession();

        ModelAndView modelAndView = new ModelAndView();
        try {
            User userValidate = db.checkLogin(user.getUser_name(), user.getPassword());
            System.out.println(userValidate + " UseValidate userController");
            if (userValidate.equals("citizen")) {

                System.out.println("Citizens Home");
                user = db.getUser(user.getUser_name());

                //modelAndView.addObject("usernamee",user_name);
                modelAndView.setViewName("CitizenDashBoardNew");
                //return new ModelAndView("UserManagementPages/CitizenDashBoard");
                return modelAndView;
            } else if (userValidate.equals("organization")) {
                //session = request.getSession();
                //session.setAttribute("organization", user_name);
                //request.setAttribute("userName", user_name);

                //request.getRequestDispatcher("OrganizationDashboard.jsp").forward(request, response);
            } else if (userValidate.equals("admin")) {
            }
            else if(userValidate.equals("block_user") )
            {

               // model.addAttribute("message", "The user is Blocked by admin .");
                return new ModelAndView("UserManagementPages/BlockUser");
            }
        }
        //catch (IOException e1)
        //{
        //e1.printStackTrace();
        //}
        catch (Exception e2) {
            e2.printStackTrace();
        }
        //model.addAttribute("message", "Login failed. Try again.");
        return new ModelAndView("citizenSigninPage");
    }

}
