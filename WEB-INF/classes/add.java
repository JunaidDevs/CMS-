import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.html.StyleSheet;


public class add extends HttpServlet {
  
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    response.setContentType("text/html");
	// get PrintWriter object
	PrintWriter out = response.getWriter();

  //get data from addform
  String courses=request.getParameter("courses");
  String teachers=request.getParameter("teachers");
  String time=request.getParameter("time");
  String day=request.getParameter("day");

  
    out.println("<html>");
    out.println("<head><title>ADD COURSE</title><link rel='stylesheet' href='css/bootstrap.min.css'/><link rel='stylesheet' href='css/style.css'/></head>");
    out.println("<body id='img2'>");

    // clear cache prevent backbuuttton
    // response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
    HttpSession session=request.getSession(false);  

    String status = (String) session.getAttribute("session_status");


    if   ((session == null || status == null || !status.equals("student"))) 
    
    // if(session == null)
    {
      out.println("<h1 class='text-center mt-3'>YOU ARE NOT SIGNED IN OR YOUR SESSION HAS BEEN EXPIRED</h1>");
      out.println("<BR/><br/><a href='signin.html'><button class='btn btn-primary ms-3'>SIGN IN</button></a>");    
    }else

    {
       RequestDispatcher rd = request.getRequestDispatcher("/header.html");
       rd.include(request,response);
   
  try
  {
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://127.0.0.1/project";
    Connection con=DriverManager.getConnection(url, "root", "root");
    Statement st=con.createStatement();

    String name=(String)session.getAttribute("session_name");  
    String id=(String)session.getAttribute("session_id");  	

        
    boolean check1 = true; 
    boolean check2 = true; 

    String query1 ="Select * from add_course  where (signup_id = '"+ id + "' AND name = '"+ name +"' AND day='"+day+"' AND time='"+ time +"')";
    ResultSet rs1 = st.executeQuery( query1 );
    if(rs1.next())
    {
           String d = rs1.getString("day");
           String t = rs1.getString("time");
           out.println("<h1  class=' display-5 text-center mt-3'>Slot :"+ t +","+d+" is already Reserved </h1>");
       
    check1 = false;
     
    }

    String query2 ="Select * from add_course  where (signup_id = '"+ id + "' AND name = '"+ name +"' AND courses='"+courses+"' )";
    ResultSet rs2 = st.executeQuery( query2 );
     if(rs2.next())
     {
      String course = rs2.getString("courses");
      out.println("<h1  class=' display-5 text-center mt-3'>Course : "+course+" is already Enrolled </h1>");
       
      check2 = false;
     }
     
        if(check1 == true&& check2 == true)
      {
	
        String query = "INSERT INTO add_course(signup_id,name,courses,teachers,time,day)VALUES('"+ id + "','"+ name + "','"+ courses + "','"+ teachers + "','"+ time + "','"+ day + "')";
        int rs = st.executeUpdate( query );
        // JOptionPane.showMessageDialog(null,"Student Details Inserted Successfully");
    
         if(rs==1)
         {	
          out.println("<h1 class='display-3 text-center'>COURSE Added Successfully</h1>");
         }
         else
         {	
         out.println("<h1 class='display-3 text-center'>Course Could not be added Successfully</h1>");
         }

      } 

        if(check1 == true && check2 == true)
       {
    
          String query3 ="Select * from history  where (signup_id = '"+ id + "'  AND courses='"+courses+"')";
          ResultSet rs3 = st.executeQuery( query3);
          if(rs3.next())
          {
            String query4="UPDATE history SET status='ENROLLED',teachers='"+teachers+"' , time='"+time+"' , day='"+day+"' where courses='"+courses+"' AND signup_id='"+id+"'";
            int rs4 = st.executeUpdate(query4);
          }else
          {
            String query = "INSERT INTO history(signup_id,courses,teachers,time,day,status)VALUES('"+ id + "','"+ courses + "','"+ teachers + "','"+ time + "','"+ day + "','ENROLLED')";
            int rs = st.executeUpdate( query );  
          }
                  
        }
     st.close();
     con.close();

  }
  catch(Exception e)
  {
    out.println(e);
  }
}//else closed
out.println("</body></html>");
}
}
