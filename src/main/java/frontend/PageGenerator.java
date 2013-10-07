package frontend;

import java.io.File;
 // $codepro.audit.disable unnecessaryImport
import java.io.IOException;
 // $codepro.audit.disable unnecessaryImport
import java.io.StringWriter;
import java.io.Writer;
 // $codepro.audit.disable unnecessaryImport
import java.util.HashMap;
 // $codepro.audit.disable unnecessaryImport
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


import org.json.simple.JSONObject;

import utils.SessionStatus;
import utils.UserSession;

public class PageGenerator 
{
	Writer out = null;
    Configuration cfg = new Configuration();
    
    public PageGenerator()
    {
    	try {
    		//String currDir = System.getProperty("user.dir");
    		//System.out.println("Current dir: " + currDir);
			cfg.setDirectoryForTemplateLoading(new File("./")); //Static/Templates
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    public String getMainPage(UserSession userSession)
    {
        try {
            //Load template from source folder
            Template template = cfg.getTemplate("index.html");
             
            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
        	

            if(userSession != null)
            {
            	if(userSession.eSessionStatus.get() == SessionStatus.eAuthorized)
            	{
            		data.put("userName", userSession.userName);
            		data.put("authorized", true);
            	}
            	else
            	{
                    data.put("userName", "Войдите");
                	data.put("authorized", false);
            	}
            }
            else
            {
                data.put("userName", "Войдите");
            	data.put("authorized", false);
            }
            
            // String output
            Writer out = new StringWriter();
            template.process(data, out);
 
            return out.toString();
             
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
        	e.printStackTrace();
        }
        
		return "Error: could not generate page";  
    }
    
	public String parseUserSession(UserSession userSession)
	{
		  JSONObject obj=new JSONObject();
		  obj.put("userName", userSession.userName.get());
		  obj.put("userId", userSession.userId.get());
		  
		  String status = new String("");
		  if(userSession.eSessionStatus.get() == SessionStatus.eAuthorized)
			  status = "authorized";
		  else if(userSession.eSessionStatus.get() == SessionStatus.eInProcess)
			  status = "inProcess";
		  else if(userSession.eSessionStatus.get() == SessionStatus.eWrongLogin)
			  status = "wrongLogin";
		  else if(userSession.eSessionStatus.get() == SessionStatus.eWrongPassword)
			  status = "wrongPassword";
		  else if(userSession.eSessionStatus.get() == SessionStatus.eUserExists)
			  status = "userExists";
		  else if(userSession.eSessionStatus.get() == SessionStatus.eRegistered)
			  status = "registered";
		  
		  obj.put("status", status);
		  
		  return obj.toString();
	}
	
}
