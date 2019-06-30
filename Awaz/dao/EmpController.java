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

}
