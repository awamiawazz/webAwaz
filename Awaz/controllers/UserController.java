package com.controllers;

/**
 * Created by Hp on 11/22/2018.
 */

import com.Beans.AccessRights;
import com.Beans.Database;
import com.Beans.Role;
import com.Beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.web.bind.annotation.SessionAttribute;


@MultipartConfig
@Controller
@SessionAttributes("usernamee")
public class UserController {

    private Database db = new Database();
    private HttpSession httpSession;


    @Autowired
    @RequestMapping("/CitizenLogin")
    public ModelAndView showCitizenLoginForm() {
        return new ModelAndView("UserManagementPages/CitizenLogin", "command", new User());
    }


    @RequestMapping("/customelogin")
    @ModelAttribute("user")
    public ModelAndView customelogin(@RequestParam("user_username") String user_name,@RequestParam("password") String password,HttpSession session,
                                      ModelMap modelMap) {
        //session = request.getSession();
        User userValidate ;// = db.getUser(user_name);
        //System.out.println("user approved: "+userValidate.isApproved_status());
        ModelAndView modelAndView = new ModelAndView();
        try {
             userValidate = db.checkLogin(user_name, password);
            System.out.println(userValidate + " UseValidate userController");
            System.out.println(userValidate.isApproved_status() + " UseValidate userController");
            System.out.println(userValidate.isBlock_status() + " UseValidate userController");
            System.out.println(userValidate + " UseValidate userController");

            if (userValidate.isApproved_status() && !userValidate.isBlock_status()) {
                System.out.println("loginsuccess");
                //session.setAttribute("user", user);
                session.setAttribute("userdata", userValidate);
                if(db.getRoleById(userValidate.getRole().getRole_id()).getRole_name().equals("citizen"))
                {
                    session.setAttribute("usertype", "citizen");
                    System.out.println("CitizenDashBoardNew Home");
                    modelAndView.setViewName("CitizenDashBoardNew");

                }else if(db.getRoleById(userValidate.getRole().getRole_id()).getRole_name().equals("organization"))
                {
                    session.setAttribute("usertype", "organization");
                    System.out.println("Organization Home");
                    modelAndView.setViewName("OrganizationLandingPage");

                }else if(db.getRoleById(userValidate.getRole().getRole_id()).getRole_name().equals("admin"))
                {
                    session.setAttribute("usertype", "admin");
                    System.out.println("AdminLandingPageNew Home");
                    modelAndView.setViewName("AdminLandingPageNew");
                }else if(db.getRoleById(userValidate.getRole().getRole_id()).getRole_name().equals("subuser"))
                {
                    session.setAttribute("usertype", "subuser");
                    System.out.println("custom Home");
                    modelAndView.setViewName("OrganizationSubuserLandingPage");
                }else if(db.getRoleById(userValidate.getRole().getRole_id()).getRole_name().length()>0)
                {
                    session.setAttribute("usertype", "custome");
                    System.out.println("custom Home");
                    modelAndView.setViewName("CustomeLandingPage");
                }
                //return new ModelAndView("UserManagementPages/CitizenDashBoard");
            }
            else {
                System.out.println("Not loginsuccess");
                System.out.print("org_id: "+ userValidate.getOrg_id());
                if(userValidate.getOrg_id()> 0)
                {
                    System.out.print("org_id: "+ userValidate.getOrg_id());
                    modelAndView.setViewName("OrganizationSubuserLandingPage");
                }
                System.out.println("Approved Status: "+userValidate.isApproved_status());
                if(!userValidate.isApproved_status())
                {
                    modelAndView.setViewName("UserManagementPages/UserManagement/ApprovedUser");
                }
                if(userValidate.equals(null))
                {
                    modelAndView.setViewName("UserManagementPages/UserManagement/ErrorPage");
                }
                else if(userValidate.isBlock_status())
                {
                    modelAndView.setViewName("UserManagementPages/BlockUser");
                }
                else
                {
                    modelAndView.setViewName("CustomeSigin");
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }

        return modelAndView;
    }

    @RequestMapping("/citizenhome")
    public ModelAndView citizenhome(HttpSession session) {
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
    @RequestMapping("/customehome")
    public ModelAndView customehome(HttpSession session) {
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
        }else if(session.getAttribute("usertype").equals("subuser"))
        {
            return new ModelAndView("OrganizationSubuserLandingPage");
        }else {
            return new ModelAndView("/");
        }
    }

    @RequestMapping(value = "/citizenlogout")
     public ModelAndView citizenlogout(HttpSession session) {
        session.removeAttribute("userdata");
        session.removeAttribute("usertype");

        return new ModelAndView("/CitizenSigninNeww");
    }
    @RequestMapping(value = "/customelogout")
     public ModelAndView customelogout(HttpSession session) {
        session.removeAttribute("userdata");
        session.removeAttribute("usertype");
        return new ModelAndView("/CustomeSigin");
    }
    @RequestMapping(value = "/viewprofile")
    public ModelAndView viewprofile(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        return new ModelAndView("ViewProfileCustome", "user", user );//, "Role", role);
    }

    @RequestMapping(value = "/organizationlogout")
     public ModelAndView organizationlogout(HttpSession session) {
        session.removeAttribute("userdata");
        session.removeAttribute("usertype");
        return new ModelAndView("/OrganizationSignin");
    }

    @RequestMapping("/organizationlogin")
    @ModelAttribute("user")
    public ModelAndView organizationlogin(@RequestParam("user_username") String user_name,@RequestParam("password") String password,HttpSession session,
            ModelMap modelMap) {
        //session = request.getSession();
        User user;// = db.getUser(user_name);
        ModelAndView modelAndView = new ModelAndView();
        try {
            User userValidate = db.checkLogin(user_name, password);
            System.out.println(userValidate + " UseValidate userController");
            if (userValidate.equals("organization")) {
                //session.setAttribute("user", user);
                user = db.getUser(user_name);
                session.setAttribute("userdata",user);

                System.out.println("Organization Home organization id: "+user.getOrg_id());
                //session.setAttribute("user", request.getParameter("user_name")); //setting session attribute
                //request.setAttribute("user", user_name);
                //System.out.println("Session k ander ha "+ session.getAttribute("user") );
                //request.getRequestDispatcher("citizenDashboard.jsp").forward(request, response);

                //modelAndView.addObject("usernamee",user_name);
                modelAndView.setViewName("OrganizationLandingPage");
                //return new ModelAndView("UserManagementPages/CitizenDashBoard");
                return modelAndView;
            }
            else if(userValidate.equals("block_user") )
            {
                return new ModelAndView("UserManagementPages/BlockUser");
            }
        }
        //catch (IOException e1)
        //{
        //e1.printStackTrace();
        //}s
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return new ModelAndView("OrganizationSignin");
    }

    @RequestMapping("/organizationhome")
    public ModelAndView organizationhome(HttpSession session) {
        if(session.getAttribute("userdata").equals(null))
        {
            return new ModelAndView("OrganizationSignin");
        }else {
            System.out.println("Session_id: "+session.getId());
            return new ModelAndView("OrganizationLandingPage");
        }
    }

    @RequestMapping(value = "/addEmployeform")
    public ModelAndView RegisterSubUser(HttpSession session) {
        if(session.getAttribute("userdata").equals(null)){
            return new ModelAndView("CustomeSogin");
        }else
        {
            User user =(User) session.getAttribute("userdata");
            System.out.println("organization id: "+user.getOrg_id());
            return new ModelAndView("/addOrganizationEmploye");
        }

    }

    @RequestMapping(value = "/addemploye", method = RequestMethod.POST) //used for citizen signup
    public ModelAndView addSubUser(@RequestParam("dataa") String[] data, HttpSession session) {
        Role role = db.getDatabaseRole("organization");
        User user = new User();
        User user1 = (User) session.getAttribute("userdata");
        user.setFull_name(data[0]);
        user.setUser_name(data[1]);
        user.setEmail(data[2]);
        user.setPassword(data[4]);
        user.setCnic(data[6]);//org_id
        user.setOrg_id(user1.getOrg_id());//org_id
        user.setPhone_no(data[7]);
        user.setAddress(data[8]);
        user.setRole(role);
        User u = db.getUser(user.getUser_name());
        int uid = u.getUser_id();
//      int emp_id = Integer.parseInt(data[3]);
        String emp_id = data[3];
        user.setEmp_id(data[3]);
        System.out.println("organization Id: " + user1.getOrg_id());
        User user11 = db.addEmploye(user);
        ;//= citizenDbHandler.RegisterCitizen(user);
        if (user11.equals(null)) {
            //return new ModelAndView("CitizenSigninNeww");
            return new ModelAndView("OrganizationLandingPage");
            //session.setAttribute("user",request.getParameter("user_name"));
            //response.sendRedirect("citizenDashboard.jsp");
        } else {
            return new ModelAndView("OrganizationLandingPage");
        }

    }

    @RequestMapping("/allemploye")
    public ModelAndView viewAllReport(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<User> userList = new ArrayList<User>();
        if (user != null) {
            userList = db.getEmploye(user.getOrg_id());
        }
        System.out.println("All Employe");
        return new ModelAndView("allEmploye", "list", userList);
    }

    @RequestMapping("/employedetail/{title}")
    public ModelAndView employedetail(@PathVariable int emp_id) {
        int user_id = 2;
        User user = db.getEmployeOfOrganization(emp_id);//=dao.getEmployees();
        return new ModelAndView("employeDetail", "user", user);
    }

    @RequestMapping("/CitizenDashboard")
    //yaha check kr k user ka role kon sa ha or role k mutabiq dashboard show ho jye
    public ModelAndView showDashBoard() {
        //if(httpSession.getAttribute("user_name") != null) {
        //System.out.println("User_name"+httpSession.getAttribute("user_name"));
        return new ModelAndView("UserManagementPages/CitizenDashBoard", "command", new User());
        // }
        //return new ModelAndView("UserManagementPages/CitizenLogin");
    }

    @RequestMapping("/adminlogin")
    @ModelAttribute("user")
    public ModelAndView adminlogin(@RequestParam("user_username") String user_name, @RequestParam("password") String password) {
       if(user_name.equals("awamiawazadmin") && password.equals("awamiawazadmin"))
       {
           return new ModelAndView("AdminLandingPageNew");
       }
        return new ModelAndView("AdminSignin");
    }

    @RequestMapping("/AdminLandingPage")
    public ModelAndView showAdminLanding() {
        List<Role> roleList = new ArrayList<Role>();
        roleList = db.getRoles();
        return new ModelAndView("UserManagementPages/AdminLandingPage");
    }
    @RequestMapping("/ManageRole")
    public ModelAndView showManageRolePage(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_users()) {
            List<Role> roleList = new ArrayList<Role>();
            System.out.println("UserController ManageRole:");
            roleList = db.getRoles();
            return new ModelAndView("ManageRoleView", "listRole", roleList);
        }
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
    @RequestMapping("/ManageUser")
    public ModelAndView showManageUserPage() {
        List<Role> roleList = db.getRoles();//=dao.getEmployees();
        return new ModelAndView("UserManagementPages/ManageUser", "listRole", roleList);
    }
    @RequestMapping("/OrganizationLandingPage")
    public ModelAndView showOrganizationLanding() {
        List<Role> roleList = new ArrayList<Role>();
        roleList = db.getRoles();
        return new ModelAndView("UserManagementPages/OrganizationHandleReport/OrganizationLandingPage");
    }

    @RequestMapping("/AddRole")
    public ModelAndView showAddRoleForm(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isAdd_role()) {
            List<Role> roleList = db.getRoles();
            return new ModelAndView("AddRole");
        }else
        {
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
    @RequestMapping(value = "/AddedRolee", method = RequestMethod.POST)
    public ModelAndView AddedRole(@RequestParam("role_name") String role_name, @RequestParam("access_right") int[] access_right,HttpSession session) {
        AccessRights accessRights = new AccessRights();
        for(int i=0; i<access_right.length; i++)
        {
            System.out.println("access Righttttttttt: "+i+" "+access_right[i]);
                switch(access_right[i])
                {
                    case 1:
                        accessRights.setCan_report(true);
                        break;
                    case 2:
                        accessRights.setView_report(true);
                        break;
                    case 3:
                        accessRights.setDelete_report(true);
                        break;
                    case 4:
                        accessRights.setUpdate_report(true);
                        break;
                    case 5:
                        accessRights.setBlock_users(true);
                        break;
                    case 6:
                        accessRights.setDelete_users(true);
                        break;
                    case 7:
                        accessRights.setView_users(true);
                        break;
                    case 8:
                        accessRights.setGenerate_visual(true);
                        break;
                    case 9:
                        accessRights.setGenerate_data_reports(true);
                        break;
                    case 10:
                        accessRights.setAdd_comment(true);
                        break;
                }
        }
        Role role = new Role();
        int access_right_id = db.getAccessRight_id(); // last inserted Access_right
        access_right_id++;
        role.setAccessRights(accessRights);
        role.setRole_name(role_name);
        role.setRole_id(access_right_id);
        System.out.println("befor addrole");
        db.addRole(role);
        System.out.println("ater addrole");

        List<Role> listRole = db.getRoles();

        //return new ModelAndView("UserManagementPages/ManageRoles/ManageRole","listRole",listRole);
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
    @RequestMapping("/DeleteRole/{role_id}")
    public ModelAndView showDeleteRoleForm(@PathVariable int role_id,HttpSession session) {
        List<Role> listRole = db.getRoles();
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isDelete_role()) {
            boolean status = db.deleteRole(role_id);
            listRole = db.getRoles();
             if(status == true)
        {
                System.out.print("Role is deleted: UserController ");
                listRole = db.getRoles();
                return new ModelAndView("ManageRoleView","listRole",listRole);
                //return new ModelAndView("UserManagementPages/ManageRoles/ManageRole","roleList",roleList);
                //return "SpringMvcExample/ManageRole";

        }       }
        else
        {
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

        return new ModelAndView("");
    }

    @RequestMapping(value = "/EditRole/{role_name}")
    public ModelAndView showEditRoleForm(@PathVariable String role_name) {
        String role_title = "citizen";
        Role role = db.getDatabaseRole(role_name);
        System.out.println("EditRole: accessright id " + role.getAccessRights().getA_id());
        return new ModelAndView("EditRole", "role", role);//, "Role", role);
    }
    @RequestMapping(value = "/EditedRole", method = RequestMethod.POST)
    public ModelAndView editRol(@RequestParam("role_name") String role_name, @RequestParam("access_right") String[] access_rights, @RequestParam("old_role_id") int role_id){

        List<Role> listRole = db.getRoles();
        System.out.print("Edited Role User Controller ");
        Role role = new Role();
        role.setRole_name(role_name);
        role.setAccessRights(this.setAccessRight(access_rights)); // set AccessRights to Boolean
        role = db.editRole(role, role_id);;
        //return new ModelAndView("UserManagementPages/ManageRoles/ManageRole");

        listRole = db.getRoles();
        return new ModelAndView("ManageRoleView","listRole",listRole);

    }
    private AccessRights setAccessRight(String access_right[]) {
        AccessRights accessRights = new AccessRights();
        int option = 0;

        for (int i = 0; i < access_right.length; i++) {
            if (access_right[i].equals("can_report")) {
                option = 1;
            } else if (access_right[i].equals("view_report")) {
                option = 2;
            } else if (access_right[i].equals("delete_report")) {
                option = 3;
            } else if (access_right[i].equals("update_report")) {
                option = 4;
            } else if (access_right[i].equals("block_user")) {
                option = 5;
            } else if (access_right[i].equals("delete_user")) {
                option = 6;
            } else if (access_right[i].equals("view_user")) {
                option = 7;
            } else if (access_right[i].equals("generate_visual")) {
                option = 8;
            } else {
                option = 9;
            }
            switch (option) {
                case 1:
                    accessRights.setCan_report(true);
                    System.out.println("CanReport: " + accessRights.isCan_report());
                    break;
                case 2:
                    accessRights.setView_report(true);
                    break;
                case 3:
                    accessRights.setDelete_users(true);
                    break;
                case 4:
                    accessRights.setUpdate_report(true);
                    break;
                case 5:
                    accessRights.setBlock_users(true);
                    break;
                case 6:
                    accessRights.setDelete_users(true);
                    break;
                case 7:
                    accessRights.setView_users(true);
                    break;
                case 8:
                    accessRights.setGenerate_visual(true);
                    break;
                case 9:
                    accessRights.setGenerate_data_reports(true);
                    break;
            }

        }
        return accessRights;
    }

    /*
    @RequestMapping("/login")
    public ModelAndView login(Model model, @ModelAttribute("user") User user, @RequestParam("user_username") String user_name, @RequestParam("password") String password) {

        ModelAndView modelAndView = new ModelAndView();
        //modelAndView.addObject("thought", user_name);
        model.addAttribute("user_name",user_name);
        if (user != null) {
            model.addAttribute("user_name", user);
        } else {
            model.addAttribute("user_name", new User());
        }
        try {
            String userValidate = db.checkLogin(user_name, password);
            System.out.println(userValidate + " UseValidate userController");
//            httpSession.setAttribute("user_name",user_name);
            if (userValidate.equals("citizen")) {
                System.out.println("Citizens Home");
                modelAndView.addObject("thought", user_name);
                modelAndView.setViewName("UserManagementPages/CitizenDashBoard");

                //session.setAttribute("user", request.getParameter("user_name")); //setting session attribute
                //request.setAttribute("user", user_name);
                //System.out.println("Session k ander ha "+ session.getAttribute("user") );
                //request.getRequestDispatcher("citizenDashboard.jsp").forward(request, response);
            } else if (userValidate.equals("organization")) {
                System.out.println("organization's Home");
                //session = request.getSession();
                //session.setAttribute("organization", user_name);
                //request.setAttribute("userName", user_name);

                //request.getRequestDispatcher("OrganizationDashboard.jsp").forward(request, response);
            } else if (userValidate.equals("admin")) {
                System.out.println("Admins's Home");
                //session = request.getSession();
                //session.setMaxInactiveInterval(10*60);
                //session.setAttribute("Admin", user_name);
                //request.setAttribute("userName", user_name);

                //request.getRequestDispatcher("AdminDashboard.jsp.jsp").forward(request, response);
            }
            else if(userValidate.equals("block_user") )
            {

                modelAndView.setViewName("UserManagementPages/BlockUser");
                //return new ModelAndView("UserManagementPages/BlockUser");
                return modelAndView;
            }

        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        //modelAndView.setViewName("UserManagementPages/CitizenShip");
        return modelAndView;
        //return new ModelAndView("UserManagementPages/CitizenSignUp");
    }

    */

}
