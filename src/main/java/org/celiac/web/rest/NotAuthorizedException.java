package org.celiac.web.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotAuthorizedException extends WebApplicationException {
    
	private static final long serialVersionUID = 6120042259425053801L;

	private String eMessage;
	
	public NotAuthorizedException() {
		super(Response.status(Response.Status.UNAUTHORIZED).build());
	}
	
	
	public NotAuthorizedException(String message) {
        super(Response.status(Response.Status.UNAUTHORIZED).entity(message).type("text/plain").build());
        eMessage = message;
    }
	
	public String getMessage(){
		return eMessage;		
	}
}
