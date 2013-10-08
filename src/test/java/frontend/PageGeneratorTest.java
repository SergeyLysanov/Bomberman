package frontend;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import utils.SessionStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import utils.UserSession;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.json.simple.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;


public class PageGeneratorTest 
{
	
	Configuration cfg = new Configuration();
	
	public PageGeneratorTest() throws IOException
	{
		//Load template from source folder
		cfg.setDirectoryForTemplateLoading(new File("./"));
	}
	
	@Test
	public void testPageGenerator()
	{
		PageGenerator result = new PageGenerator();
		assertNotNull(result);
	}

	@Test
	public void testGetMainPageWithNullSession() throws IOException, TemplateException
	{
		PageGenerator generator = new PageGenerator();

		UserSession userSession = null;
		String result = generator.getMainPage(userSession);
		
        Template template = cfg.getTemplate("index.html");
         
        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("userName", "Войдите");
    	data.put("authorized", false);
    	
        // String output
        Writer out = new StringWriter();
        template.process(data, out);
        String expected = out.toString();
        
        
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetMainPageForAuthorizedUser() throws IOException, TemplateException
	{
		PageGenerator generator = new PageGenerator();

		UserSession userSession = new UserSession("name", "sessionId", "pass");
		userSession.eSessionStatus.set(SessionStatus.eAuthorized);
		String result = generator.getMainPage(userSession);
		
        Template template = cfg.getTemplate("index.html");
         
        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
		data.put("userName", userSession.userName);
		data.put("authorized", true);
    	
        // String output
        Writer out = new StringWriter();
        template.process(data, out);
        String expected = out.toString();
        
        
		assertEquals(expected, result);
	}

	@Test
	public void testGetMainPageForNotAuthorizedUser() throws IOException, TemplateException
	{
		PageGenerator generator = new PageGenerator();

		UserSession userSession = new UserSession("name", "sessionId", "pass");
		userSession.eSessionStatus.set(SessionStatus.eWrongLogin);
		String result = generator.getMainPage(userSession);
		
        Template template = cfg.getTemplate("index.html");
         
        // Build the data-model
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("userName", "Войдите");
    	data.put("authorized", false);
    	
        // String output
        Writer out = new StringWriter();
        template.process(data, out);
        String expected = out.toString();
        
		assertEquals(expected, result);
	}


	@Test
	public void testParseRegisteredUserSession()
	{
		PageGenerator generator = new PageGenerator();

		UserSession userSession = new UserSession("name", "sessionId", "pass");
		userSession.eSessionStatus.set(SessionStatus.eRegistered);

		String result = generator.parseUserSession(userSession);

		JSONObject expectedResult = new JSONObject();
		expectedResult.put("userName", userSession.userName.get());
		expectedResult.put("userId", userSession.userId.get());
		expectedResult.put("status", "registered");
		  
		assertEquals(expectedResult.toString(), result);
	}
	
	@Test
	public void testParseWrongPasswordUserSession()
	{
		PageGenerator generator = new PageGenerator();

		UserSession userSession = new UserSession("name", "sessionId", "pass");
		userSession.eSessionStatus.set(SessionStatus.eWrongPassword);

		String result = generator.parseUserSession(userSession);

		JSONObject expectedResult = new JSONObject();
		expectedResult.put("userName", userSession.userName.get());
		expectedResult.put("userId", userSession.userId.get());
		expectedResult.put("status", "wrongPassword");
		  
		assertEquals(expectedResult.toString(), result);
	}
	
	@Test
	public void testParseWrongLoginUserSession()
	{
		PageGenerator generator = new PageGenerator();

		UserSession userSession = new UserSession("name", "sessionId", "pass");
		userSession.eSessionStatus.set(SessionStatus.eWrongLogin);

		String result = generator.parseUserSession(userSession);

		JSONObject expectedResult = new JSONObject();
		expectedResult.put("userName", userSession.userName.get());
		expectedResult.put("userId", userSession.userId.get());
		expectedResult.put("status", "wrongLogin");
		  
		assertEquals(expectedResult.toString(), result);
	}

	@Test
	public void testParseAuthorizedUserSession()
	{
		PageGenerator generator = new PageGenerator();

		UserSession userSession = new UserSession("name", "sessionId", "pass");
		userSession.eSessionStatus.set(SessionStatus.eAuthorized);

		String result = generator.parseUserSession(userSession);

		JSONObject expectedResult = new JSONObject();
		expectedResult.put("userName", userSession.userName.get());
		expectedResult.put("userId", userSession.userId.get());
		expectedResult.put("status", "authorized");
		  
		assertEquals(expectedResult.toString(), result);
	}
	
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(PageGeneratorTest.class);
	}
}