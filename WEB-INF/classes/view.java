import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class view extends HttpServlet {

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head><title>VIEW COURSES</title><link rel='stylesheet' href='css/bootstrap.min.css'/><link rel='stylesheet' href='css/style.css'/></head>");
    out.println("<body id='img2'>");
    
    HttpSession session=request.getSession(false);  

    String status = (String) session.getAttribute("session_status");


    if   ((session == null || status == null || !status.equals("student"))) 
   
    // if(session == null)
    {
      out.println("<h1 class='text-center mt-3'>YOU ARE NOT SIGNED IN/Allowed to visit this page OR YOUR SESSION HAS BEEN EXPIRED</h1>");
      out.println("<BR/><br/><a href='signin.html'><button class='btn btn-primary ms-3'>SIGN IN</button></a>");    
    }
   else
  {
      RequestDispatcher rd = request.getRequestDispatcher("/header.html");
      rd.include(request,response);
      
    try
    {   
      Class.forName("com.mysql.jdbc.Driver");
      String url = "jdbc:mysql://127.0.0.1/project";
      Connection con=DriverManager.getConnection(url,"root","root");
      Statement st=con.createStatement();
	    
      String name=(String)session.getAttribute("session_name");  
	    String id=(String)session.getAttribute("session_id");  	
    
      String query = "SELECT * FROM add_course WHERE (signup_id = '"+ id + "' AND name = '"+ name + "') ";      
     	ResultSet rs = st.executeQuery( query );

      if(rs.next())
      {	
          out.println("<div class='container'>");
          out.println("<h1 class='mt-3 text-center'> Enrolled Courses </h1>");
          out.println("<table class='table table-bordered border-dark'>"); 
          out.println("<thead>");
          out.println("<tr class='table-dark'>");
          out.println("<th>Course Name</th>");
          out.println("<th>Teacher Name</th>");
          out.println("<th>Day</th>");
          out.println("<th>Time Slot</th>");
           out.println("</tr>");
          out.println("</thead>");

          out.println("<tbody>");
          do {
            out.println("<tr>");
            out.println("<td>" + rs.getString("courses") + "</td>");
            out.println("<td>" + rs.getString("teachers") + "</td>");
            out.println("<td>" + rs.getString("day") + "</td>");
            out.println("<td>" + rs.getString("time") + "</td>");
            out.println("</tr>");
        } while (rs.next());
    
          
          out.println("</tbody></table></div>");
        }
       else
       {
        out.println("<h1 style='color:red; 'class='mt-3 text-center display-5'>Note</h1>");
        out.println("<h1 class='mt-3 text-center'>You Are NOT Enrolled IN ANY Course</h1>");
        out.println("<h1 class='mt-3 text-center'>First Add Courses</h1>");
      }
     st.close();
     con.close();
    }
    catch(Exception e)
    {
      out.println(e);
    }
  } //ELSE END
    out.println("</body></html>");
}
}