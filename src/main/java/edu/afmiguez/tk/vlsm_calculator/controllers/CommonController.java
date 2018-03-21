package edu.afmiguez.tk.vlsm_calculator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getIndex(Model model){
        model.addAttribute("title","Home");
        return "index";
    }
}
