package com.mailjet.send;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.Resource;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Email;
import com.mailjet.client.resource.Template;

@SuppressWarnings("serial")
public class Send_exampleServlet extends HttpServlet {
	private static final String html = "<h3>Dear passenger, welcome to Mailjet!</h3><br />May the delivery force be with you!";
	static String apiKey = "";
	static String apiSecret = "";
	
	static MailjetClient client = new MailjetClient(apiKey, apiSecret);
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		

		JSONArray rec = new JSONArray();
		JSONObject jose = new JSONObject();
		jose.put("Email", "jose@insightos.com");
		rec.put(jose);
		rec.toString();
//		MailjetRequest request = new MailjetRequest(Contact.resource);
		MailjetRequest request = new MailjetRequest(getEmailResource());
		request.property(Email.FROMEMAIL, "rene@insightos.com");
		request.property(Email.FROMNAME, "José & René");
		request.property(Email.SUBJECT, "BurgerOfHamburg");
		request.property(Email.TEXTPART, "ben is cool");
		request.property(Email.HTMLPART, html);
		request.property(Email.RECIPIENTS, rec.toString());
		request.property(Email.MJCAMPAIGN, "HelloWorld");
		request.property(Email.MJDEDUPLICATECAMPAIGN, "1");
		
		MailjetResponse response = null;
		try {
			response = client.post(request);
		} catch (MailjetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MailjetSocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		System.out.println(gson.toJson(response));
		System.out.println(response.toString());
		System.out.println(response.getData());
		System.out.println(response.getStatus());
//		resp.setContentType("text/plain");
//		resp.getWriter().println(response.toString());
	}
	
	private Resource getEmailResource() {
		Resource res = Email.resource;
		return res;
	}

	public static void main(String[] args) throws MailjetException, MailjetSocketTimeoutException {
//		try {
//			new Send_exampleServlet().doGet(null, null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String htmmmml = getTemplate();
		send(htmmmml);
	}

	private static String getTemplate() throws MailjetException, MailjetSocketTimeoutException {
		 
	     MailjetRequest request;
	     MailjetResponse response;
	     request = new MailjetRequest(Template.resource).property(Template.ID, "36977");
	     response = client.get(request);
	     
	     
	     System.out.println(response.getStatus());
	     System.out.println(response.getData());
		return response.getData().toString();
		
	}

	private static void send(String newHtml) throws MailjetException, MailjetSocketTimeoutException {
	     MailjetRequest request;
	     MailjetResponse response;
	     request = new MailjetRequest(Email.resource)
	                       .property(Email.FROMEMAIL, "rene@insightos.com")
	                       .property(Email.FROMNAME, "Mailjet Pilot")
	                       .property(Email.SUBJECT, "Your email flight plan!")
	                       .property(Email.TEXTPART, "Dear passenger, welcome to Mailjet! May the delivery force be with you!")
	                       .property(Email.HTMLPART, newHtml)
	                       .property(Email.RECIPIENTS, new JSONArray()
	               .put(new JSONObject()
	                   .put("Email", "rene@insightos.com")))
	                       .property(Email.MJCAMPAIGN, "SendAPI_campaign")
	                       .property(Email.MJDEDUPLICATECAMPAIGN, "1");
	     response = client.post(request);
	     
	     
	     System.out.println(response.getStatus());
	     System.out.println(response.getData());
		
	}
}
