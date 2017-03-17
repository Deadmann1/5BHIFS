package pkgServlets;

import pkgData.CustomerBean;
import pkgData.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/*Created by IntelliJ IDEA.
  Author: sammerm
  Copyright © 2017 by sammerm
  All rights reserved. 
  No part of this publication may be reproduced, distributed, or transmitted in any form or by any means, 
  including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the publisher, 
  except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
  For permission requests, write to the publisher.
*/
@WebServlet(name = "MenuServlet")
public class MenuServlet extends HttpServlet {
    private HttpSession session = null;
    private PrintWriter writer = null;
    private String sessionMessage = "";
    private int hits;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        verifySession(request, response);
        initSession(request, response);
        checkInput(request, response);
        callAppropriateJSP(request, response);
    }

    private void verifySession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        session = request.getSession(false);
        if (session == null || !session.getAttribute("sessionID").equals(session.getId())) {
            try {
                sessionMessage = "type in customername & password";
                session.setAttribute("sessionMessage", sessionMessage + " (hits: " + hits + ")");
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
            } catch (IOException e) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/error.jsp");
            }
        }
    }

    private void initSession(HttpServletRequest request, HttpServletResponse response) {
        try {
            hits = Integer.parseInt(session.getAttribute("hits").toString());
        } catch (Exception ex) {
            hits = 0;
        }
        hits++;
        session.setAttribute("hits", hits);
    }

    private void callAppropriateJSP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = (response.encodeRedirectURL(request.getContextPath()));
        session.setAttribute("sessionMessage", sessionMessage + " (hits: " + hits + ")");
        if (request.getParameter("btnOamtc") != null) {
            try {
                response.sendRedirect(response.encodeRedirectURL("http://www.oeamtc.at"));
            } catch (IOException e) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/error.jsp");
            }
        } else if (request.getParameter("btnBuy") != null) {
            try {
                response.sendRedirect(url + "/buy.jsp");
            } catch (IOException e) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/error.jsp");
            }
        }else if (request.getParameter("btnBought") != null) {
            try {
                response.sendRedirect(url + "/bought.jsp");
            } catch (IOException e) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/error.jsp");
            }
        }else if (request.getParameter("btnBack") != null) {
            try {
                response.sendRedirect(url + "/login.jsp");
            } catch (IOException e) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/error.jsp");
            }
        }
    }

    private void checkInput(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("btnBuy") != null) {
            try {
                session.setAttribute("carList", Database.getInstance().getCarsWithoutOwner());
                sessionMessage = "select car(s) you want to buy";
            } catch (Exception e) {
                sessionMessage = "SQL Exception : ( " + e.getMessage() + " )";
            }
        } else if (request.getParameter("btnBought") != null) {
            try {
                CustomerBean user = (CustomerBean) session.getAttribute("sessionCustomer");
                session.setAttribute("boughtCarList", Database.getInstance().getCarsWithOwner(user));
                sessionMessage = "these are your car(s)";
            } catch (Exception e) {
                sessionMessage = "SQL Exception : ( " + e.getMessage() + " )";
            }
        } else if (request.getParameter("btnBack") != null) {
            sessionMessage = "type in your username and password";
        }
    }
}
