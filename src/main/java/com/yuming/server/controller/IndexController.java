package com.yuming.server.controller;

import com.yuming.server.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chen jia hao
 */
@Controller
public class IndexController {
    @Autowired
    RedisService redisService;
    @RequestMapping(value = "1")
    public ModelAndView toIndex(){
//        redisService.set("hi","hello");
//        System.out.println(redisService.get("hi"));
        return new ModelAndView("index");
    }

    @RequestMapping(value = "gszc")
    public ModelAndView toGSZC(){
        return new ModelAndView("gszc");
    }

    @RequestMapping(value = "dljz")
    public ModelAndView toDLJZ(){
        return new ModelAndView("dljz");
    }

    @RequestMapping(value = "nsch")
    public ModelAndView toNSCH(){
        return new ModelAndView("nsch");
    }

    @RequestMapping(value = "sbzx")
    public ModelAndView toSBZX(){
        return new ModelAndView("sbzx");
    }

    @RequestMapping(value = "rzch")
    public ModelAndView toRZCH(){
        return new ModelAndView("rzch");
    }

    @RequestMapping(value = "about")
    public ModelAndView toABOUT(){
        return new ModelAndView("about");
    }

    @RequestMapping(value = "test")
    public void test(HttpServletRequest request, HttpServletResponse response){

        System.out.println("kkk.....");
        try {
            response.getWriter().print("ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
