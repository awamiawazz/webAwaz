package com.dao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Hp on 11/21/2018.
 */
@Controller
@RequestMapping("/myImage")
public class ImageController {

    // @Resource(name = "itemService")
   // private ItemService itemService;

    @RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
    public void showImage(@RequestParam("id") Integer itemId, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {


     //   Report item = itemService.get(itemId);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, video/mp4");
      //  response.getOutputStream().write(item.getItemImage());


        response.getOutputStream().close();

    }

}
