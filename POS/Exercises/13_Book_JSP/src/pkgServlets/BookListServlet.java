package pkgServlets;

import pkgData.BookBean;
import pkgData.Database;
import pkgData.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
@WebServlet(name = "BookListServlet")
public class BookListServlet extends HttpServlet {
    private HttpSession session = null;
    private PrintWriter writer = null;
    private String sessionMessage = "";
    private int hits;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        verifySession(request,response);
        initSession(request,response);
        checkInput(request, response);
        callAppropriateJSP(request, response);
    }

    private void verifySession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        session = request.getSession(false);
        if (session == null || !session.getAttribute("sessionID").equals(session.getId())) {
            try {
                sessionMessage = "type in username & password";
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
        String url = (response.encodeRedirectURL(request.getContextPath()) + "/listBooks.jsp");
        if (request.getParameter("btnSearch") != null) {
            url = (response.encodeRedirectURL(request.getContextPath()) + "/bookDetail.jsp");
        } else if (request.getParameter("btnBack") != null) {
            url = response.encodeRedirectURL(request.getContextPath());
            sessionMessage = "type in bookid a/o author";
        }
        session.setAttribute("sessionMessage", sessionMessage + " (hits: " + hits + ")");
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath()) + "/error.jsp");
        }
    }

    private void checkInput(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("btnSearch") != null) {
            int id = 0;
            String str = request.getParameter("bookId");
            if (str != null && !str.isEmpty()) {
                id = Integer.parseInt(str);
            }
            String author = request.getParameter("author");
            BookBean book = new BookBean(id, "", author, -1);
            session.setAttribute("sessionBook", book);
            try {
                session.setAttribute("bookList", Database.getInstance().getBooksWithUser((UserBean)session.getAttribute("sessionUser"),book));
            } catch (Exception e) {
                sessionMessage = "SQL Exception : ( " + e.getMessage() + " )";
            }
        }
    }
}
