package edu.immune.dropwizard.view;

import io.dropwizard.views.View;

/**
 * Login Page View
 * @author lalit
 *
 */
public class LoginView extends View {
	
	private String message;
	
	public LoginView(String message) {
		super("login.ftl");
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
