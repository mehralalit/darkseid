package edu.immune.dropwizard.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

import edu.immune.dropwizard.util.TokenManager;
import edu.immune.dropwizard.util.UserRecordManager;
import edu.immune.dropwizard.view.IndexView;
import edu.immune.dropwizard.view.LoginView;

/**
 * View Resource for html display
 * 
 * @author lalit
 *
 */
@Path("rest-service")
@Produces(MediaType.TEXT_HTML)
public class ViewResource {

	private static Logger logger = LoggerFactory.getLogger(ViewResource.class);
	private static final String successMessage = "Authentication Successful! Your Authorization token is: ";
	private static final String failureMessage = "Authentication Failure! User does not Exist";

	/**
	 * fetches initial index page
	 * 
	 * @return {@link IndexView}
	 */
	@GET
	@Path("index")
	@Timed
	public IndexView getIndex() {
		return new IndexView();
	}

	/**
	 * services the token generation request
	 * 
	 * @param phone
	 *            phone number of the user for login
	 * @return {@link LoginView}
	 */
	@POST
	@Path("login")
	public LoginView login(@FormParam(value = "phone") String phone) {
		String message;
		try {
			String token = TokenManager.getToken(phone);
			UserRecordManager.updateToken(phone, token);
			message = new String(successMessage + token);
			message.concat(token);
			logger.info("Authentication Successful. Phone# {}", phone);
		} catch (Exception e) {
			message = new String(failureMessage);
			logger.error("Authorization Failed. User does not Exist", e);
		}
		return new LoginView(message);
	}

}
