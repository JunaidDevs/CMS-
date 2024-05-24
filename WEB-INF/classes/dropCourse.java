import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;

public class dropCourse extends HttpServlet {

  //Process the HTTP POST request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    response.setContentType("text/html");
     PrintWriter out = response.getWriter();
     String courses=request.getParameter("courses"); 
     String signup_id=request.getParameter("signup_id"); 
      
     
    out.println("<html>");
    out.println("<head><title>drop course</title><link rel='stylesheet' href='css/bootstrap.min.css'/><link rel='stylesheet' href='css/style.css'/></head>");
    out.println("<body id='img2'>");
      HttpSession session=request.getSession(false);  

      String status = (String) session.getAttribute("session_status");
  
      if   ((session == null || status == null || !status.equals("teacher"))) 
    
    // if(session == null)
    {
      out.println("<h1 class='text-center mt-3'>YOU ARE NOT SIGNED IN OR YOUR SESSION HAS BEEN EXPIRED</h1>");
      out.println("<BR/><br/><a href='signin.html'><button class='btn btn-primary ms-3'>SIGN IN</button></a>");    
    }else
  {
    RequestDispatcher rd = request.getRequestDispatcher("/teacher_header.html");
    rd.include(request,response);
try
{
     Class.forName("com.mysql.jdbc.Driver");
     String url = "jdbc:mysql://127.0.0.1/project";
     Connection con=DriverManager.getConnection(url,"root","root");
     Statement st=con.createStatement();

     String name=(String)session.getAttribute("session_name");  
     String id=(String)session.getAttribute("session_id");  	              
     String teachers=(String)session.getAttribute("session_teachers"); 

     String query="Delete from add_course where (signup_id='"+signup_id+"' AND courses='"+courses+"'AND teachers='"+teachers+"')  ";
     int rs = st.executeUpdate(query);
     if(rs==1)
     {
       out.println("<h1 class='text-center'>COURSE Droped Successfully</h1>"); 
       String query1="UPDATE history SET status='DROPPED' where courses='"+courses+"' AND signup_id= '"+signup_id+"'AND teachers='"+teachers+"'";
       int rs2 = st.executeUpdate(query1);
     }
      else
      {
        out.println("<h1 class='text-center'>Student is not Enrolled Yet in this course</h1>");
      }
      String query3="Delete from marks where (signup_id='"+signup_id+"' AND courses='"+courses+"' AND teachers='"+teachers+"')  ";
     int rs3 = st.executeUpdate(query3);

      st.close();
      con.close();
    }
    catch(Exception e)
    {
    out.println(e);
    }
  }//ELSE END
out.println("</body></html>");
}
}