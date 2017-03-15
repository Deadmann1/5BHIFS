/*
  Created by IntelliJ IDEA.
  Author: Manuel Sammer
  Copyright © 2017 by Manuel Sammer
  All rights reserved.
  No part of this publication may be reproduced, distributed, or transmitted in any form or by any means,
  including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the publisher,
  except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
  For permission requests, write to the publisher.
*/
package pkgServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(asyncSupported = false, name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    private HttpSession session = null;
    private HttpServletResponse response = null;
    private PrintWriter writer = null;
    private String sessionMessage = "";
    private String username = "";
    private String password = "";
    private boolean isAdmin = false;
    private boolean isLoggedIn = false;
    private int hits;

    public LoginServlet() {
        hits = 0;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initSession(request);
        checkInput(request);


        session.setAttribute("isAdmin", isAdmin);
        session.setAttribute("isLoggedIn", isLoggedIn);


        callAppropriateJSP(request, response);
    }

    private void callAppropriateJSP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "";
        if (request.getParameter("btnLogin") != null) {
            url = response.encodeRedirectURL(request.getContextPath());
        } else if (request.getParameter("btnListBooks") != null) {
            url = (response.encodeRedirectURL(request.getContextPath())+ "/bookList.jsp");
            sessionMessage = "type in bookid a/o author";
        } else if (request.getParameter("btnNewBook") != null) {
        } else if (request.getParameter("btnNewUser") != null) {
        } else if (request.getParameter("btnNewDelivery") != null) {
        } else if (request.getParameter("btnAllDeliveries") != null) {
        }
            session.setAttribute("sessionMessage", sessionMessage + " (hits: " + hits + ")");
            response.sendRedirect(url);
    }

    private void initSession(HttpServletRequest request) {
        session = request.getSession();
        hits++;
        session.setAttribute("hits", hits);
    }

    private void checkInput(HttpServletRequest request) {
        if (request.getParameter("btnLogin") != null) {
            checkLoginCredentials(request);
        }
    }

    private void checkLoginCredentials(HttpServletRequest request) {
        username = request.getParameter("user").toString();
        password = request.getParameter("password").toString();

        if (username == null || username.equals("") || password == null || password.equals("")) {
            sessionMessage = "type in username & password";
            isLoggedIn = false;
        } else {
            if (username.equals("manuel") && password.equals("manuel")) {
                isLoggedIn = true;
                sessionMessage = "login correct";
            } else {
                isLoggedIn = false;
                sessionMessage = "username a/o password not correct";
            }
        }
    }

}
