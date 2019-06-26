package com.controllers;

import com.Beans.Database;
import com.Beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 11/24/2018.
 */
@MultipartConfig
@Controller
public class ManageUser {

    private Database db = new Database();
    /* It provides list of employees in model object */
    @RequestMapping("/ViewUser")
    public ModelAndView viewAllUsers(HttpSession session) {
        List<User> users = new ArrayList<User>();
        User user = (User) session.getAttribute("userdata");
        if(session.getAttribute("usertype").equals("admin")) {
        users = db.getUserList();
        //return new ModelAndView("UserManagementPages/UserManagement/ViewUsers", "userlist", users);//UserManagementPages/UserManagement
        return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
    }
        if(session.getAttribute("usertype").equals("citizen"))
        {
            return new ModelAndView("CitizenDashBoardNew");
        } else if(session.getAttribute("usertype").equals("organization"))
        {
            users = db.getUserListOrganization(user.getOrg_id());
            return new ModelAndView("OrganizationLandingPage");
        }else if(session.getAttribute("usertype").equals("custome"))
        {
            return new ModelAndView("CustomeLandingPage");
        }else if(session.getAttribute("usertype").equals("admin"))
        {
            //return new ModelAndView("UserManagementPages/UserManagement/ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement

            //return new ModelAndView("AdminLandingPageNew");
        }else {
            return new ModelAndView("");
        }

    }

    @RequestMapping("/cruduser")
    public ModelAndView cruduser(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
            List<User> users = db.getUserList();
            return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement

    }

    @RequestMapping(value = "/SearchedUser")
    public ModelAndView searchUser(@RequestParam("userName") String userName) {
        User user = db.getUser(userName);
        System.out.println("userController: SearchUser: "+user.getPhone_no());
        return new ModelAndView("SearchedUser", "user", user);
    }

    @RequestMapping(value = "/blockuserviewform")
    public ModelAndView blockuserview(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<User> users = new ArrayList<User>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isBlock_users()) {
            boolean block = false;
                users = db.getUserList();
                return new ModelAndView("UserBlockView", "userlist", users);//UserManagementPages/UserManagement
            } else {
            if(session.getAttribute("usertype").equals("citizen"))
            {
                return new ModelAndView("CitizenDashBoardNew");
            } else if(session.getAttribute("usertype").equals("organization"))
            {
                return new ModelAndView("OrganizationLandingPage");
            }else if(session.getAttribute("usertype").equals("custome"))
            {
                return new ModelAndView("CustomeLandingPage");
            }else if(session.getAttribute("usertype").equals("admin"))
            {
                return new ModelAndView("AdminLandingPageNew");
            }else {
                return new ModelAndView("");
            }
        }
    }

    @RequestMapping(value = "/approveduserviewform")
    public ModelAndView approveduserview(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<User> users = new ArrayList<User>();
        if(session.getAttribute("usertype").equals("admin")) {
            boolean block = false;
                users = db.getUserList();
                return new ModelAndView("UserApprovedView", "userlist", users);//UserManagementPages/UserManagement
            } else {
            if(session.getAttribute("usertype").equals("citizen"))
            {
                return new ModelAndView("CitizenDashBoardNew");
            } else if(session.getAttribute("usertype").equals("organization"))
            {
                return new ModelAndView("OrganizationLandingPage");
            }else if(session.getAttribute("usertype").equals("custome"))
            {
                return new ModelAndView("CustomeLandingPage");
            }else if(session.getAttribute("usertype").equals("admin"))
            {
                return new ModelAndView("AdminLandingPageNew");
            }else {
                return new ModelAndView("");
            }
        }
    }

    @RequestMapping(value = "/BlockUser/{username}")
    public ModelAndView blockUser(@PathVariable String username,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isBlock_users()) {
            boolean block = false;
            List<User> users = new ArrayList<User>();
            block = db.block_user(username);
            users = db.getUserList();
            if (block == true) {
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            } else {
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            }
        }else
        {
            return new ModelAndView("CustomeLandingPage");
        }
   }

    @RequestMapping(value = "/UnBlockUser/{username}")
    public ModelAndView unBlockUser(@PathVariable String username,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isBlock_users()) {
            boolean block = false;
            List<User> users = new ArrayList<User>();
            block = db.unblock_user(username);
            users = db.getUserList();
            if (block == true) {
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            } else {
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            }
        }else
        {
            return new ModelAndView("CustomeLandingPage");
        }
    }

    @RequestMapping(value = "/BlockUsernew/{username}")
    public ModelAndView BlockUsernew(@PathVariable String username,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isBlock_users()) {
            boolean block = false;
            List<User> users = new ArrayList<User>();
            block = db.block_user(username);
            if (block == true) {
                users = db.getUserList();
                return new ModelAndView("UserBlockView", "userlist", users);//UserManagementPages/UserManagement
            } else {
                if(session.getAttribute("usertype").equals("citizen"))
                {
                    return new ModelAndView("CitizenDashBoardNew");
                } else if(session.getAttribute("usertype").equals("organization"))
                {
                    return new ModelAndView("OrganizationLandingPage");
                }else if(session.getAttribute("usertype").equals("custome"))
                {
                    return new ModelAndView("CustomeLandingPage");
                }else {
                    return new ModelAndView("/");
                }
            }
        }else
        {
            return new ModelAndView("CustomeLandingPage");
        }
   }

    @RequestMapping(value = "/UnBlockUsernew/{username}")
    public ModelAndView UnBlockUsernew(@PathVariable String username,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isBlock_users()) {
            boolean block = false;
            List<User> users = new ArrayList<User>();
            block = db.unblock_user(username);
            if (block == true) {
                users = db.getUserList();
                return new ModelAndView("UserBlockView", "userlist", users);//UserManagementPages/UserManagement
            } else {
                if(session.getAttribute("usertype").equals("citizen"))
                {
                    return new ModelAndView("CitizenDashBoardNew");
                } else if(session.getAttribute("usertype").equals("organization"))
                {
                    return new ModelAndView("OrganizationLandingPage");
                }else if(session.getAttribute("usertype").equals("custome"))
                {
                    return new ModelAndView("CustomeLandingPage");
                }else {
                    return new ModelAndView("");
                }
            }
        }else
        {
            return new ModelAndView("CustomeLandingPage");
        }
    }

    @RequestMapping(value = "/ApprovedUsernew/{username}")
    public ModelAndView ApprovedUsernew(@PathVariable String username,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        System.out.println("Approved user: "+session.getAttribute("usertype"));
        if(session.getAttribute("usertype").equals("admin")) {

            System.out.println("Approved user: "+session.getAttribute("usertype"));
            boolean block = false;
            List<User> users = new ArrayList<User>();
            block = db.approved_user(username);
            if (block == true) {
                users = db.getUserList();
                return new ModelAndView("UserApprovedView", "userlist", users);//UserManagementPages/UserManagement
            } else {
                if(session.getAttribute("usertype").equals("citizen"))
                {
                    return new ModelAndView("CitizenDashBoardNew");
                } else if(session.getAttribute("usertype").equals("organization"))
                {
                    return new ModelAndView("OrganizationLandingPage");
                }else if(session.getAttribute("usertype").equals("custome"))
                {
                    return new ModelAndView("CustomeLandingPage");
                }else if(session.getAttribute("usertype").equals("admin"))
                {
                    return new ModelAndView("AdminLandingPageNew");
                }else {
                    return new ModelAndView("");
                }
            }
        }else
        {
            return new ModelAndView("CustomeLandingPage");
        }
    }

    @RequestMapping(value = "/ApprovedUser/{username}")
    public ModelAndView ApprovedUser(@PathVariable String username,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        //if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().is()) {
            boolean approved = false;
            List<User> users = new ArrayList<User>();
        approved = db.approved_user(username);
            if (approved == true) {
                users = db.getUserList();
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            } else {
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            }
        /*}else
        {
            return new ModelAndView("CustomeLandingPage");
        }*/
    }

    @RequestMapping(value = "/DeleteUser/{username}")
    public ModelAndView DeleteUser(@PathVariable String username,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        System.out.println("DeleteUser: "+db.getRoleById(user.getRole().getRole_id()).getAccessRights().isDelete_users());
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isDelete_users()) {
            System.out.println("DeleteUser");
            boolean approved = false;
            List<User> users = new ArrayList<User>();
            users = db.getUserList();
            approved = db.delete_user(username);
        if (approved == true) {
                users = db.getUserList();
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            } else {
                return new ModelAndView("ViewUsers", "userlist", users);//UserManagementPages/UserManagement
            }
        }else
        {
            return new ModelAndView("CustomeLandingPage");
        }
    }

}
