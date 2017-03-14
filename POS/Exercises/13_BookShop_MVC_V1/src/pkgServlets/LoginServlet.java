package pkgServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Manuel Sammer on 24.02.2017.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        InitSession(request); // Move to after successful login part of code
        getDatabase(request);
        checkInput();
        getLoginInformation();
        callAppropriateJSP(request, response);

    }

    private void callAppropriateJSP(HttpServletRequest request, HttpServletResponse response) {
    }

    private void getLoginInformation() {
    }

    private void checkInput() {
    }

    private void getDatabase(HttpServletRequest request) {
    }

    
}
