package com.dao;

import com.Beans.Database;
import com.Beans.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;

@MultipartConfig
@Controller
public class EmpController {

    private Database db = new Database();



    @Autowired

    @RequestMapping("/AddReport")
    public ModelAndView showAddReportForm() {
        return new ModelAndView("AddReport", "command", new Report());
    }

    /*It saves object into database. The @ModelAttribute puts request data
     *  into model object. You need to mention RequestMethod.POST method
     *  because default request is GET*/


    /* It deletes record for the given id in URL and redirects to AllReport */
    @RequestMapping(value = "/DeleteReport/{id}", method = RequestMethod.GET)
    public ModelAndView deleteReport(@PathVariable int id) {
        //dao.delete(id);
        Database db = new Database();
        db.deleteReport(id);
        return new ModelAndView("redirect:/AllReport");
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        //dao.delete(id);
        return new ModelAndView("index");
    }


























        /*
        @RequestMapping(value = "/EditReportt/{title}")
        public ModelAndView editReport(@PathVariable String title) {
            Report reportt = db.getReport(title);
            Report report = db.editReport(reportt, title);
            return new ModelAndView("AllReport", "report", report);
        }
    */

}