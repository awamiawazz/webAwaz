package com.controllers;

/**
 * Created by Asad on 3/24/2019.
 */

import com.Beans.*;
import com.Beans.apiModel.Feedback;
import com.Beans.apiModel.MyItems;
import com.Beans.apiModel.RestMethods;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Configuration
@ComponentScan("com.Beans.apiModel.*")
public class ApiController {

    protected RestMethods restMethods = new RestMethods();
    protected Database db = new Database();

    @RequestMapping(value = "/addreport", method = RequestMethod.POST)
    public ResponseEntity addreport(@RequestBody Report report) {
        System.out.println("in addReport method");
        Report reportt = db.addReportapi(report);
        return new ResponseEntity(reportt, HttpStatus.OK);
    }

    //Register Citizen                                                                             //Register Citizen
    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody User user){
        User user1 = db.RegisterCitizen(user);
        if (user1 == null) {
            return new ResponseEntity("Sorry Failed To Registered " , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user1, HttpStatus.OK);
    }
    // Show user Report by userid
    @RequestMapping(value = "/reports/{user_id}", method = RequestMethod.GET)                      // Show user Report by userid
    public ResponseEntity allReportsUser(@PathVariable("user_id") int user_id) {

        List<Report> reportList = db.allReportsUserapi(user_id);
        if (reportList == null) {
            return new ResponseEntity("No Report found for user_ID " + user_id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reportList, HttpStatus.OK);
    }
    //Show all reports in database
    @RequestMapping(value = "/reports", method = RequestMethod.GET)                               // show all reports in database
    public ResponseEntity allREports() {

        List<Report> reportList = db.allReportsapi();
        if (reportList == null) {
            return new ResponseEntity("No Report found " , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reportList, HttpStatus.OK);
    }
    // login the citizen by username and password
    @RequestMapping(value = "/loginuser/{user_username}/{password}", method = RequestMethod.GET)  // login the citizen by username and password
    public ResponseEntity loginUser(@PathVariable("user_username") String user_username,@PathVariable("password") String password) {
        System.out.println("UserName: "+user_username);
        System.out.println("Password: "+password);
        boolean success=false;
        String message = "";
        User user = db.checkLoginApi(user_username, password);
        if(user!=null) {
            success=true;
            message= "login Successfully";
            return new ResponseEntity(user, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity("Email and password ", HttpStatus.OK);
        }
    }
    // Show latitude and loggitude and report organization name
    @RequestMapping(value = "/reportsLatLog", method = RequestMethod.GET)                         // Show latitude and loggitude and report organization name
    public ResponseEntity latlogs() {

        List<MyItems> latlogs  = db.reportLatLogapi();
        if (latlogs == null) {
            return new ResponseEntity("No Lat Log found " , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(latlogs, HttpStatus.OK);
    }
    // Show list of organizations                                                                 // Show list of organizations
    @RequestMapping(value = "/registeredOrganization", method = RequestMethod.GET)
    public ResponseEntity showRegisteredOganization() {

        List<User> orglist  = db.getOrganizationListapi();
        if (orglist == null) {
            return new ResponseEntity("No Organization found " , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orglist, HttpStatus.OK);
    }
    // Show list of problems solved by organization                                               // Show list of problems solved by organization
    @RequestMapping(value = "/organizationproblems/{user_id}", method = RequestMethod.GET)
    public ResponseEntity showOrganizationProblems(@PathVariable("user_id") int user_id) {
        List<Problemm> orglist  = db.getOrganizationProblemsapi(user_id);
        if (orglist == null) {
            return new ResponseEntity("No Organization found " , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orglist, HttpStatus.OK);
    }

    @RequestMapping(value = "/commentapi", method = RequestMethod.POST)                           // add comment to report
    public ResponseEntity comment(@RequestBody Feedback feedback) {
        Feedback seccess = db.addCommentapi(feedback);
        ;//= citizenDbHandler.RegisterCitizen(user);
        if (seccess !=null) {
            return new ResponseEntity(seccess , HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity("Sorry to add comment", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getcommentsapi/{id}", method = RequestMethod.GET)                   // get all comment of a report y report id
    public ResponseEntity getComment(@PathVariable("id") int id) {
        List<Feedback> feedbacks = db.getCommentsApi(id);
        if (feedbacks == null) {
            return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(feedbacks, HttpStatus.OK);
    }

    // Show user Report by userid
    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)                      // Show user Report by userid
    public ResponseEntity getUser(@PathVariable("user_id") int user_id) {

        System.out.println("GetUser api") ;
        User user = db.getUserById(user_id);
        if (user == null) {
            return new ResponseEntity("No Report found for user_ID " + user_id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user, HttpStatus.OK);
    }



}