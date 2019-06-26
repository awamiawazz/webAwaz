package com.controllers;

import com.Beans.Database;
import com.Beans.Report;
import com.Beans.User;
import com.Beans.apiModel.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 11/25/2018.
 */
@MultipartConfig
@Controller
public class ReportController {

        private Database db = new Database();

        @Autowired
        @RequestMapping("/AddReport")
        public ModelAndView showAddReportForm() {
            return new ModelAndView("AddReport", "command", new Report());
        }

        @RequestMapping("/googlemap")
        public ModelAndView googleMap() {
            return new ModelAndView("GoogleMap", "command", new Report());
        }


        @RequestMapping("/allreport")
        public ModelAndView viewAllReport(HttpSession session) {
            User user = (User) session.getAttribute("userdata");
            List<Report> reportList = new ArrayList<Report>();
            if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
                int user_id = user.getUser_id();
                // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();
                if (user != null) {
                    reportList = db.getReports();//=dao.getEmployees();
                }
                //return new ModelAndView("AllReport", "list", reportList);
                System.out.println("All Reportss");
                if(session.getAttribute("usertype").equals("citizen"))
                {
                    return new ModelAndView("AllReportCitizenView", "list", reportList);
                } else if(session.getAttribute("usertype").equals("organization"))
                {
                    return new ModelAndView("AllReport", "list", reportList);
                }else if(session.getAttribute("usertype").equals("custome"))
                {
                    return new ModelAndView("AllReport", "list", reportList);
                }else {
                    return new ModelAndView("/");
                }
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
                }else {
                    return new ModelAndView("/");
                }
            }
    }

        @RequestMapping("/myreport")
        public ModelAndView myreport(HttpSession session) {
            User user = (User) session.getAttribute("userdata");
            List<Report> reportList = new ArrayList<Report>();
            if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
                int user_id = user.getUser_id();
                reportList = db.getReportsByUserId(user_id);
                // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();
                if (user != null) {
                    reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();

                }
                //return new ModelAndView("AllReport", "list", reportList);
                System.out.println("All Reportss");
                if(session.getAttribute("usertype").equals("citizen"))
                {
                    return new ModelAndView("AllReportCitizenView", "list", reportList);
                } else if(session.getAttribute("usertype").equals("organization"))
                {
                    return new ModelAndView("AllReport", "list", reportList);
                }else if(session.getAttribute("usertype").equals("custome"))
                {
                    return new ModelAndView("AllReport", "list", reportList);
                }else {
                    return new ModelAndView("/");
                }
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
                }else {
                    return new ModelAndView("/");
                }
            }
    }
    @RequestMapping("/citizenpandingreport")
    public ModelAndView citizenpandingreport(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
            int user_id = user.getUser_id();
            reportList = db.getReportsByUserIdByStatus(user_id, "pending");
            // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();

            //return new ModelAndView("AllReport", "list", reportList);
            System.out.println("All Reportss");
            if(session.getAttribute("usertype").equals("citizen"))
            {
                return new ModelAndView("AllReportCitizenView", "list", reportList);
            } else if(session.getAttribute("usertype").equals("organization"))
            {
                reportList = db.getReports();
                return new ModelAndView("AllReport", "list", reportList);
            }else if(session.getAttribute("usertype").equals("custome"))
            {
                return new ModelAndView("AllReport", "list", reportList);
            }else {
                return new ModelAndView("/");
            }
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
            }else {
                return new ModelAndView("/");
            }
        }
    }
    @RequestMapping("/citizensolvedreport")
    public ModelAndView citizensolvedreport(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
            int user_id = user.getUser_id();
            reportList = db.getReportsByUserIdByStatus(user_id, "solved");
            // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();

            //return new ModelAndView("AllReport", "list", reportList);
            System.out.println("All Reportss");
            if(session.getAttribute("usertype").equals("citizen"))
            {
                return new ModelAndView("AllReportCitizenView", "list", reportList);
            } else if(session.getAttribute("usertype").equals("organization"))
            {
                reportList = db.getReports();
                return new ModelAndView("AllReport", "list", reportList);
            }else if(session.getAttribute("usertype").equals("custome"))
            {
                return new ModelAndView("AllReport", "list", reportList);
            }else {
                return new ModelAndView("/");
            }
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
            }else {
                return new ModelAndView("/");
            }
        }
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST) //used for citizen signup
    public ModelAndView Addcomment(@RequestParam("data") String[] data, HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isAdd_comment()) {
            Feedback feedback = new Feedback();
            feedback.setComment(data[0]);
            feedback.setRanking(Double.parseDouble(data[2]));
            feedback.setUser_id(user.getUser_id());
            feedback.setR_id(Integer.parseInt(data[1])); // in data 3 report id stored type is hidden

            int seccess = db.addComment(feedback);

            ;//= citizenDbHandler.RegisterCitizen(user);
            if (seccess > 0) {
                if(session.getAttribute("usertype").equals("citizen"))
                {
                    return new ModelAndView("CitizenDashBoardNew");
                } else if(session.getAttribute("usertype").equals("organization"))
                {
                    return new ModelAndView("OrganizationLandingPage");
                }else if(session.getAttribute("usertype").equals("custome"))
                {
                    return new ModelAndView("CustomeLandingPage");
                }else{
                    return new ModelAndView("/");
                }
            }
        }else {
                if(session.getAttribute("usertype").equals("citizen"))
                {
                    return new ModelAndView("CitizenDashBoardNew");
                } else if(session.getAttribute("usertype").equals("organization"))
                {
                    return new ModelAndView("OrganizationLandingPage");
                }else if(session.getAttribute("usertype").equals("custome"))
                {
                    return new ModelAndView("CustomeLandingPage");
                }else{
                    return new ModelAndView("/");
                }
            }

        return new ModelAndView("/");
    }

    /* It deletes record for the given id in URL and redirects to AllReport */
        @RequestMapping(value = "/DeleteReport/{id}", method = RequestMethod.GET)
        public ModelAndView deleteReport(@PathVariable int id,HttpSession session) {
            User user =(User) session.getAttribute("userdata");
            if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isDelete_report()) {
                //dao.delete(id);
                Database db = new Database();
                db.deleteReport(id);
                return new ModelAndView("redirect:/AllReport");
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
                }else {
                    return new ModelAndView("/");
                }
            }
    }


    @RequestMapping(value = "/changeReportStatuss/{username}")
    public ModelAndView changeReportStatuss(@PathVariable String username,@PathVariable String status,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isUpdate_report()) {
            boolean changestatus = false;
            changestatus = db.changeReportStatus(username, status);
            reportList = db.getReports();
            if (changestatus == true) {
                reportList = db.getReports();
                return new ModelAndView("AllReport", "list", reportList);
            }
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
            }else {
                return new ModelAndView("/");
            }
        }
            return new ModelAndView("/");

    }

    @RequestMapping(value = "/changeReportStatus")
    public ModelAndView changeReportStatus(@RequestParam("dataa") String[] data,HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<Report> reportList = new ArrayList<Report>();
        System.out.println("length: "+ data.length);
        System.out.println("Change REport status: "+ data[0]);
        System.out.println("Change REport status: "+ data[1]);
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isUpdate_report()) {
            boolean changestatus = false;

            System.out.println("Status Changed REport status: "+ data[1]);
            changestatus = db.changeReportStatuss(data);
            reportList = db.getReports();
            if (changestatus == true) {
                reportList = db.getinboxReport(user.getUser_name());
                return new ModelAndView("InboxReport", "list", reportList);
            }
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
            }else {
                return new ModelAndView("/");
            }
        }
            return new ModelAndView("/");

    }

    /*It saves object into database. The @ModelAttribute puts request data
     *  into model object. You need to mention RequestMethod.POST method
     *  because default request is GET*/

        @RequestMapping(value = "/uploadRepor", method = RequestMethod.POST)
        public ModelAndView uploadReport(@RequestParam("data") String[] names,@RequestParam("filedata") MultipartFile[] files,HttpSession session) {

            User user = (User) session.getAttribute("userdata");
            InputStream imagee =null, videoo=null;
            byte[] image = null, video=null;
            String message = "";
            System.out.println("Kaleem Pakistatn: "+files[0]);
            MultipartFile img = files[0];
            MultipartFile vid = files[1];
            Report report = new Report();
            report.setTitle(names[0]);
            message = message + "You successfully uploaded file=" + names[0]
                    + "<br />";
            report.setSeverity(names[1]);
            report.setDescription(names[2]);
            report.setAddress(names[3]);
            report.setLat(Double.parseDouble(names[4]));
            report.setLog(Double.parseDouble(names[5]));
            message = message + "You successfully uploaded file=" + names[1]
                    + "<br />";

            message = message + "You successfully uploaded file=" + names[2]
                    + "<br />";
            message = message + "You successfully uploaded file=" + names[3]
                    + "<br />";
            try {
                image = img.getBytes();
                imagee = new ByteArrayInputStream(image);
                message = message + "You successfully uploaded file=" + " image 1 "
                        + "<br />";
                video = vid.getBytes();
                videoo = new ByteArrayInputStream(video);
                message = message + "You successfully uploaded file=" + " video 1"
                        + "<br />";

            } catch (Exception e) {
                //return "You failed to upload file " + " => " + e.getMessage();
                System.out.print("You failed to upload file " + " => " + e.getMessage());
            }
            report.setImagee(image);
            report.setVideoo(video);
            report.setUser_id(user.getUser_id());
            report.setStatus("send");
            report.setP_id(1);
            db.insertReportNewInDB(report);
            //String title = db.getReportNew(report.getTitle()).getTitle();
            //return message;
            return new ModelAndView("CitizenDashBoardNew");
        }


    @RequestMapping("/inboxreport")
    public ModelAndView inboxreport(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        System.out.print("org_name: "+user.getFull_name());
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
            int user_id = user.getUser_id();
            // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();
            if (user != null) {
                reportList = db.getinboxReport(user.getUser_name());//=dao.getEmployees();
            }
            //return new ModelAndView("AllReport", "list", reportList);
            System.out.println("All Reportss");
            return new ModelAndView("InboxReport", "list", reportList);
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
            }else {
                return new ModelAndView("/");
            }
        }
    }

    @RequestMapping("/inboxreportsubuser")
    public ModelAndView inboxreportsubuser(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
            int user_id = user.getUser_id();
            // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();

            reportList = db.getinboxReport(user.getUser_name());
            //return new ModelAndView("AllReport", "list", reportList);
            System.out.println("All Reportsssssssssssssss");
            return new ModelAndView("InboxReport", "list", reportList);
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
            }else {
                return new ModelAndView("/");
            }
        }
    }


    @RequestMapping("/pandingreport")
    public ModelAndView pandingreport(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
            int user_id = user.getUser_id();
            // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();
            if (user != null) {
                reportList = db.getPendingReport(user.getUser_name(), "pending");//=dao.getEmployees();
            }
            //return new ModelAndView("AllReport", "list", reportList);
            System.out.println("All Reportss");
            return new ModelAndView("PendingReport", "list", reportList);
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
            }else {
                return new ModelAndView("/");
            }
        }
    }

    @RequestMapping("/pandingreportsubuser")
    public ModelAndView pandingreportsubuser(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
            int user_id = user.getUser_id();
            // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();
            if (user != null) {
                reportList = db.getPendingReport(user.getUser_name(), "pending");//=dao.getEmployees();
            }
            //return new ModelAndView("AllReport", "list", reportList);
            System.out.println("All Reportss");
            return new ModelAndView("PendingReport", "list", reportList);
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
            }else {
                return new ModelAndView("/");
            }
        }
    }

    @RequestMapping("/solvedreport")
    public ModelAndView solvedreport(HttpSession session) {
        User user = (User) session.getAttribute("userdata");
        System.out.print("org_name: "+user.getFull_name());
        List<Report> reportList = new ArrayList<Report>();
        if(db.getRoleById(user.getRole().getRole_id()).getAccessRights().isView_report()) {
            int user_id = user.getUser_id();
            // List<Report> reportList = db.getReportsByUserId(user_id);//=dao.getEmployees();
            if (user != null) {
                reportList = db.getSolvedReport(user.getUser_name(), "solved");//=dao.getEmployees();
            }
            //return new ModelAndView("AllReport", "list", reportList);
            System.out.println("All Reportss");
            return new ModelAndView("SolvedReport", "list", reportList);
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
            }else {
                return new ModelAndView("/");
            }
        }
    }



    /* It displays object data into form for the given id.
         * The @PathVariable puts URL data into variable.*/

    /*
    @RequestMapping(value = "/EditRepor", method = RequestMethod.POST)
    public ModelAndView editReport(@RequestParam("data") String[] data, @RequestParam("file") MultipartFile[] files) {

        System.out.print("editReportHandler ");
        InputStream imagee, videoo;
        String old_title = null;
        byte[] image = null, video = null;
        String message = "";
        MultipartFile img = files[0];
        MultipartFile vid = files[1];
        Report report = new Report();
        report.setTitle(data[0]);
        report.setDescription(data[1]);
        report.setLocation(data[2]);
        report.setSeverity(data[3]);
        try {
            image = img.getBytes();
            imagee = new ByteArrayInputStream(image);
            message = message + "You successfully uploaded file=" + " image 1 "
                    + "<br />";
            video = img.getBytes();
            videoo = new ByteArrayInputStream(video);
            message = message + "You successfully uploaded file=" + " video 1"
                    + "<br />";
            old_title = data[4];
            System.out.println("old Bambenoo Balolo:" + old_title);


        } catch (Exception e) {
            //return "You failed to upload file "  + " => " + e.getMessage();
            System.out.print("You failed to upload file " + " => " + e.getMessage());
        }
        report.setImagee(image);
        report.setVideoo(video);
        report.setUser_id(2);

        report = db.editReport(report, old_title);
        System.out.println("New Bambenoo Balolo:" + report.getTitle());
        //return message;
        return new ModelAndView("AllReport");
    }
*/


}
