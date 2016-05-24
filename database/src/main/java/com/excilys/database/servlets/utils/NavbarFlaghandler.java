package com.excilys.database.servlets.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public class NavbarFlaghandler {

    public static void setFlag(HttpServletRequest request, String level, String header, String body) {
        // Setting a success feedback navbar
        request.setAttribute("postMessage", "true");
        request.setAttribute("messageLevel", level);
        request.setAttribute("messageHeader", header);
        request.setAttribute("messageBody", body);
    }

    public static void setFlag(ModelMap request, String level, String header, String body) {
        // Setting a success feedback navbar
        request.addAttribute("postMessage", true);
        request.addAttribute("messageLevel", level);
        request.addAttribute("messageHeader", header);
        request.addAttribute("messageBody", body);
    }
}
