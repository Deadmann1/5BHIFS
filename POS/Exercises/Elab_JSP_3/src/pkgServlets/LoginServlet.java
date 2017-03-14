package pkgServlets;

import pkgModels.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Manuel Sammer on 01.03.2017.
 */
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = new User();
        user.setUsername("Test, Hallo 123");
        session.setAttribute("user",user);
        RequestDispatcher req = request.getRequestDispatcher("display.jsp");
        req.forward(request, response);
    }
}
