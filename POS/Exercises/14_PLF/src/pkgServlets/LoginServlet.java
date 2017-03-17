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

import pkgData.Database;
import pkgData.CustomerBean;

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
    private PrintWriter writer = null;
    private String sessionMessage = "";
    private int hits;
    private String username = "";
    private String password = "";
    private boolean isLoggedIn = false;

    public LoginServlet() {
        hits = 0;
        sessionMessage = "type in your name and passwd";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initSession(request);
        checkInput(request);
        callAppropriateJSP(request, response);
    }

    private void callAppropriateJSP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = "";
        if (request.getParameter("btnLogin") != null) {
            if(isLoggedIn) {
                url = response.encodeRedirectURL(request.getContextPath() + "/menu.jsp");
            }
            else {
                url = response.encodeRedirectURL(request.getContextPath());
            }
        }
        else if (request.getParameter("btnMenu") != null) {
            url = (response.encodeRedirectURL(request.getContextPath()) + "/menu.jsp");
            sessionMessage = "login ok";
        }
        /*else if (request.getParameter("btnNewBook") != null) {
            url = (response.encodeRedirectURL(request.getContextPath()) + "/newBook.jsp");
            sessionMessage = "fill in all fields";
        } else if (request.getParameter("btnNewUser") != null) {
            url = (response.encodeRedirectURL(request.getContextPath()) + "/newUser.jsp");
            sessionMessage = "fill in all fields";
        } else if (request.getParameter("btnNewDelivery") != null) {
            url = (response.encodeRedirectURL(request.getContextPath()) + "/listOrders.jsp");
            sessionMessage = "fill in all fields";
        } else if (request.getParameter("btnAllDeliveries") != null) {
            url = (response.encodeRedirectURL(request.getContextPath()) + "/listDeliveries.jsp");
            sessionMessage = "all deliveries";
        }
        */
        session.setAttribute("sessionMessage", sessionMessage + " (hits: " + hits + ")");
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/error.jsp");
        }
    }

    private void initSession(HttpServletRequest request) {
        session = request.getSession();
        try {
            hits = Integer.parseInt(session.getAttribute("hits").toString());
        } catch (Exception ex) {
            hits = 0;
        }
        hits++;
        session.setAttribute("hits", hits);
    }

    private void checkInput(HttpServletRequest request) {
        if (request.getParameter("btnLogin") != null) {
            checkLoginCredentials(request);
        } else if (request.getParameter("btnBuy") != null) {
            try {
                session.setAttribute("carList", Database.getInstance().getCarsWithoutOwner());
            } catch (Exception e) {
                sessionMessage = "SQL Exception : ( " + e.getMessage() + " )";
            }
        }
    }

    private void checkLoginCredentials(HttpServletRequest request) {
        username = request.getParameter("customername").toString();
        password = request.getParameter("password").toString();
        if (username == null || username.equals("") || password == null || password.equals("")) {
            sessionMessage = "type in customername & password";
            isLoggedIn = false;
        } else {
            try {
                CustomerBean user = new CustomerBean(username, password);
                if (Database.getInstance().isUserValid(user)) {
                    isLoggedIn = true;
                    session.setAttribute("sessionCustomer", user);
                    session.setAttribute("sessionID", session.getId());
                    sessionMessage = "login ok";
                } else {
                    isLoggedIn = false;
                    sessionMessage = "customername a/o password not correct";
                }
            } catch (Exception e) {
                isLoggedIn = false;
                sessionMessage = "SQL Exception : ( " + e.getMessage() + " )";
            }
        }
        session.setAttribute("isLoggedIn", isLoggedIn);
    }

}
