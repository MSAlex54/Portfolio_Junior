import entities.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MainServlet")
public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        Person person = (Person) session.getAttribute("person");

        if (person == null) {
            person = new Person();
            person.setFirst_name(req.getParameter("name"));
            person.setLast_name(req.getParameter("lname"));
        }
        Integer count = (Integer) session.getAttribute("counter");
        if (count == null) {
            count = 1;
        } else {
            ++count;
        }
        session.setAttribute("counter", count);
        String name = req.getParameter("name");
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<h1>Hello Worlod</h1>");
        pw.println("<h2>Hello " + name + "</h1>");
        pw.println("<p>" + session.getAttribute("counter") + "</p>");
        pw.println("<p>" + person.toString() + "</p>");
        pw.println("</html>");
//        RequestDispatcher disp = req.getRequestDispatcher("/test");
//        disp.forward(req,resp);
//        resp.sendRedirect("/test?name="+ name);
    }
}
