import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;


public class update extends HttpServlet {

   //Process the HTTP POST request
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
     
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
 
     String courses=request.getParameter("courses");
     String newcourses=request.getParameter("newcourses");
     String newteachers=request.getParameter("newteachers");
     String newtime=request.getParameter("newtime");     
     String newday=request.getParameter("newday");
   
   
     out.println("<html>");
     out.println("<head><title>UPDATE COURSES</title><link rel='stylesheet' href='css/bootstrap.min.css'/><link rel='stylesheet' href='css/style.css'/></head>");
     out.println("<body id='img2'>");

    HttpSession session=request.getSession(false);  

    String status = (String) session.getAttribute("session_status");

    if   ((session == null || status == null || !status.equals("student"))) 
      // if(session == null)
     {
      out.println("<h1 class='text-center mt-3'>YOU ARE NOT SIGNED IN/Concerned Person OR YOUR SESSION HAS BEEN EXPIRED</h1>");
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
  
     String id=(String)session.getAttribute("session_id");  	  

     boolean check1 = true; 
     boolean check2 = true; 

     String query1 ="Select * from add_course  where (signup_id = '"+ id + "' AND courses='"+courses+"')";
     ResultSet rs1 = st.executeQuery( query1 );
     if(rs1.next())
     {

        String query2 ="Select * from add_course  where (signup_id = '"+ id + "' AND courses='"+newcourses+"')";
        ResultSet rs2 = st.executeQuery( query2 );
   
        if(rs2.next())
        {
           String course = rs2.getString("courses");
           out.println("<h1  class=' display-5 text-center mt-3'>Course : "+course+" is already Enrolled </h1>");
           check1 = false;
         }
   
        String query3 ="Select * from add_course  where (signup_id = '"+ id + "' AND time = '"+ newtime +"' AND day='"+newday+"' )";
        ResultSet rs3 = st.executeQuery( query3 );
         
         if(rs3.next())
           {
           String d = rs3.getString("day");
           String t = rs3.getString("time");
           out.println("<h1  class=' display-5 text-center mt-3'>Slot :"+ t +","+d+" is already Reserved </h1>");   
           check2 = false;
            }

         if(check1 == true && check2 == true)
         {
           String query4="UPDATE add_course SET courses='"+newcourses+"' , teachers='"+newteachers+"' , time='"+newtime+"' , day='"+newday+"' where courses='"+courses+"' AND signup_id='"+id+"'";
           int rs = st.executeUpdate(query4);  
          if(rs==1)
          {
             out.println("<h1>Course Updated Successfully</h1>"); 
           }
          else 
          {
             out.println("<h1>Course not   updated Successfully</h1>"); 
          }
        
         }
        
         if(check1 == true && check2 == true)
         {
          String query5 ="Select * from history  where (signup_id = '"+ id + "' AND courses='"+newcourses+"')";
          ResultSet rs5 = st.executeQuery( query5 );
           
           if(rs5.next())
           {
            String query6="UPDATE history SET status='ENROLLED', teachers='"+newteachers+"' , time='"+newtime+"' , day='"+newday+"' where courses='"+newcourses+"' AND signup_id='"+id+"'";
            int rs6 = st.executeUpdate(query6);   
           }
           else
           {
            String query7 = "INSERT INTO history(signup_id,courses,teachers,time,day,status)VALUES('"+ id + "','"+ newcourses + "','"+ newteachers + "','"+ newtime + "','"+ newday + "','ENROLLED')";
            int rs7 = st.executeUpdate( query7 );  
           }
           
           String query8="UPDATE history SET status='DROPPED-UPDATED' where courses='"+courses+"' AND signup_id='"+id+"'";
           int rs8 = st.executeUpdate(query8);
         }         
      }
      else
      {
       out.println("<h1 class='text-center'>Course Doesn't Exist</h1>");
      }
   
      st.close();
      con.close();
    
    } catch(Exception e)
   {
      out.println(e);
   }

  }//ELSE END

  out.println("</body></html>");
}
}
