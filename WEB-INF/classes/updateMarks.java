import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.html.StyleSheet;


public class updateMarks extends HttpServlet {
  
  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    response.setContentType("text/html");
	// get PrintWriter object
	PrintWriter out = response.getWriter();

  //get data from addform
  String courses=request.getParameter("courses");
  String signup_id=request.getParameter("signup_id");
  String oquiz=request.getParameter("oquiz");
  String oassignment=request.getParameter("oassignment");
  String oproject=request.getParameter("oproject");
  String omid=request.getParameter("omid");
  String ofinal=request.getParameter("ofinal");
  

  
    
  out.println("<html>");
  out.println("<head><title>UPDATE MARKS</title><link rel='stylesheet' href='css/bootstrap.min.css'/><link rel='stylesheet' href='css/style.css'/></head>");
  out.println("<body id='img2'>");
   

    // clear cache prevent backbuuttton
    // response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");

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
    Connection con=DriverManager.getConnection(url, "root", "root");
    Statement st=con.createStatement();

    String name=(String)session.getAttribute("session_name");  
    String id=(String)session.getAttribute("session_id");  	
    String teachers=(String)session.getAttribute("session_teachers"); 

     boolean check1=true;
     boolean check2=true;
  
    String query1="Select * from marks where signup_id='"+signup_id+"' AND  courses='"+courses+"' ";
    ResultSet rs1 = st.executeQuery( query1 );

    if(!(rs1.next()))
    { 
     out.println("<h1  class='text-center mt-3'>Roll number: "+signup_id+" has not got marks yet.<br>First Add marks then you can update.</h1>");
     check1=false;
    }

    
    String query2="Select * from add_course where courses='"+courses+"' AND signup_id='"+signup_id+"' AND teachers ='"+teachers+"'";
    ResultSet rs2 = st.executeQuery( query2 );

    if(!(rs2.next()))
    { 
     out.println("<h1  class='text-center mt-3'>Roll number: "+signup_id+" has not enrolled in "+courses+" yet</h1>");
     check2=false;
    }


  if(check1==true && check2==true )
    {
      String query = "UPDATE marks SET oquiz='"+oquiz+"',   oassignment='"+  oassignment +"' , oproject='"+oproject+"', omid='"+omid+"', ofinal='"+ofinal+"' where courses='"+courses+"' AND signup_id='"+signup_id+"' AND  teachers ='"+teachers+"'";
      int rs = st.executeUpdate(query);    
      if(rs==1)
      {    
      out.println("<h1  class='text-center mt-3'>Roll number: "+signup_id+" marks updated succesfully  </h1>");
      }  
      else{
        out.println("<h1  class='text-center mt-3'>Roll number: "+signup_id+" marks could not be updated </h1>");
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
