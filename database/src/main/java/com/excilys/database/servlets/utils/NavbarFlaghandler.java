package com.excilys.database.servlets.utils;

import javax.servlet.http.HttpServletRequest;

public class NavbarFlaghandler {

    public static void setFlag(HttpServletRequest request, String level, String header, String body) {
        // Setting a success feedback navbar
        request.setAttribute("postMessage", "true");
        request.setAttribute("messageLevel", level);
        request.setAttribute("messageHeader", header);
        request.setAttribute("messageBody", body);
    }
}
