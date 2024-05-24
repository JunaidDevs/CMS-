function validation_signin()
{  
  var uname=document.signin_form.name.value;  
  var password=document.signin_form.password.value;
 
  if (uname.length<4)
  {  
    alert("User Name must be 4 characters long");  
    return false;  
  }
  if(password.length<6)
  {  
    alert("Password must be at least 6 characters long.");  
    return false;  
   }
      return true;
}


////////
function validation_teacher()

    { 

        var name=document.getElementById('selectName').value;
        var uname=document.signup_form.username.value;  
        var age=document.signup_form.age.value;
        var email=document.signup_form.email.value; 
        var password=document.signup_form.password.value;
        var confirm=document.signup_form.confirm_password.value;
        // var name=document.getElementById('selectTeacher').value;
       
   
        if (uname.length<4)
          {  
            alert("User Name must be 4 characters long");  
            return false;  
          }
          if (age=="")
          {  
            alert("Age can't be blank");  
            return false;  
          }
          if (age<=0)
          {  
            alert("Age Should be greater than 0");  
            return false;  
          }

          if (email=="")
            {  
              alert("Email can't be blank");  
              return false;  
            }
            else 
            {
              if(email.indexOf("@") == 0)
              {
                alert("Format Not Correct\n'@' can't occur at start");
              return false;
              }
              if (email.indexOf("@") == -1)
              {
                alert("Format Not Correct\n Email Should contain  '@'");
              return false;
              }
              if (email.lastIndexOf("@") != email.indexOf("@")) 
              {
                alert("Format Not Correct\n'@' can use atmost one time in an email");
              return false;
              }
        
              if (email.indexOf(".") == -1) 
              {
                alert("Format Not Correct\n Email Should contain at least one '.'");
              return false;
              }
        
              if (email.lastIndexOf(".") < email.indexOf("@")) 
              {
                alert("Format Not Correct\nFormat \"abc@gmail.com\"");
              return false;
              }
        
              if (email.lastIndexOf(".") + 1 == email.length)
              {
                alert("Format Not Correct\nEmail should not end with'.'");
              return false;
              }
        
              if(email[email.indexOf("@") + 1] == '.')
              {
                alert("Format Not Correct\nThere should be at least one character between '@' and '.' ");
              return false;
              }
            }
        
      if (name=="Open")
        {  
          alert("Select an Option From NAME Field");  
          return false;  
        }
        if(password.length<6)
        {  
          alert("Password must be at least 6 characters long.");  
          return false;  
        }
        if (password !=confirm )
        {
          alert("Password Did not Match");
          return false;
        }
        return true;
        }  
