import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Manuel Sammer on 18.01.2017.
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {
    //Used to present the error in these kind of variables
    private static int notThreadSafeCounter = 0;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request,response);
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Return current counter of Visitor/Requester, sample code from the internet
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            notThreadSafeCounter++;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Elaboration_Servlet_2_Test</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>You are " + notThreadSafeCounter + ". Requestor in the Queue</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
