import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.html.StyleSheet;


public class role extends HttpServlet {
        
        //Process the HTTP POST request
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html");
        // response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String role=request.getParameter("role");
        
        out.println("<html>");
     
    out.println("<html>");
    out.println("<head><title>ROLE</title><link rel='stylesheet' href='css/bootstrap.min.css'/><link rel='stylesheet' href='css/style.css'/></head>");
    out.println("<body id='img2'>");
    try
    {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1/project";
        Connection con=DriverManager.getConnection(url, "root", "root");
        Statement st=con.createStatement();

     if(role.equals("S"))
     {
        response.sendRedirect("signup.html");
     }
        
     else
     { 
        response.sendRedirect("teacher_signup.html");
     }
     
     st.close();
     con.close();
    }
    catch(Exception e)
    {
      out.println(e);
    }
    out.println("</body></html>");
}
}