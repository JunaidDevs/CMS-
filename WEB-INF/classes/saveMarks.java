import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.html.StyleSheet;


public class saveMarks extends HttpServlet {
  
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    response.setContentType("text/html");
	// get PrintWriter object
	PrintWriter out = response.getWriter();

  //get data from addform
  String courses=request.getParameter("courses");
  String signup_id=request.getParameter("signup_id");//rooll
  String oquiz=request.getParameter("oquiz");
  String oassignment=request.getParameter("oassignment");
  String oproject=request.getParameter("oproject");
  String omid=request.getParameter("omid");
  String ofinal=request.getParameter("ofinal");
  

  
   
  out.println("<html>");
  out.println("<head><title>ADD MARKS</title><link rel='stylesheet' href='css/bootstrap.min.css'/><link rel='stylesheet' href='css/style.css'/></head>");
  out.println("<body id='img2'>");
   

    // clear cache prevent backbuuttton
    // response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");

    HttpSession session=request.getSession(false);  

    String status = (String) session.getAttribute("session_status");

    if   ((session == null || status == null || !status.equals("teacher")))  
    // if(session == null)
    {
      out.println("<h1 class='text-center mt-3'>YOU ARE NOT SIGNED IN/concerned person OR YOUR SESSION HAS BEEN EXPIRED</h1>");
      out.println("<BR/><br/><a href='signin.html'><button class='btn btn-primary ms-3'>SIGN IN</button></a>");    
    }else
{
       RequestDispatcher rd = request.getRequestDispatcher("/teacher_header.html");
       rd.include(request,response);
   
  try
  {
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://127.0.0.1/project";
    Connection con=DriverManager.getConnection(url, "root", "root");
    Statement st=con.createStatement();

    String name=(String)session.getAttribute("session_name");  //username
    String id=(String)session.getAttribute("session_id");//teacher id
    String teachers=(String)session.getAttribute("session_teachers");  	//teacchers name 

     boolean check1=true;
    //  boolean check2=true;
     boolean check3=true;

    String query1 ="Select * from add_course where courses='"+courses+"' AND teachers='"+teachers+"' AND signup_id='"+signup_id+"'";
    ResultSet rs1 = st.executeQuery( query1 );
  
   if(!(rs1.next()))
   {  
    out.println("<h1 class='text-center mt-3'>None of the students has enrolled in "+courses+" Yet</h1><br/>");
    check1=false;
      }

    // String query2 ="Select * from add_Course where signup_id='"+signup_id+"' ";
    // ResultSet rs2 = st.executeQuery( query2 );

    // if(!(rs2.next()))
    // { 
    //  out.println("<h1  class='text-center mt-3'>Roll number: "+signup_id+" has not enrolled  </h1>");
    //  check2=false;
    // }

    String query3 ="Select * from marks where signup_id='"+signup_id+"' AND courses='"+courses+"' AND teachers = '"+teachers+"' ";
    ResultSet rs3 = st.executeQuery( query3 );

    if(rs3.next())
    { 
     out.println("<h1  class='text-center mt-3'>Roll number: "+signup_id+" has already got marks <br/> You can Update his marks.</h1>");
     out.println("<a style='padding-left:40px' href='updateMarks.jsp'><button  class='btn btn-primary'>Update Marks</button></a>");
     check3=false;
    }
 
    if(check1==true  && check3==true)
    {
      String query = "INSERT INTO marks(signup_id,oquiz,oassignment,oproject,omid,ofinal,courses,teachers)VALUES('"+ signup_id + "','"+ oquiz + "','"+ oassignment + "','"+ oproject + "','"+ omid + "','"+ ofinal + "','"+ courses + "','"+ teachers + "')";
      int rs = st.executeUpdate( query );  
      if(rs==1)
      {    
      out.println("<h1  class='text-center mt-3'>Roll number: "+signup_id+" marks added succesfully  </h1>");
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
