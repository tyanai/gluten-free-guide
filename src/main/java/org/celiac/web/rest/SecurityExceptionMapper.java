package org.celiac.web.rest;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.celiac.web.rest.NotAuthorizedException;

@Provider
public class SecurityExceptionMapper implements ExceptionMapper<NotAuthorizedException>
{

  public Response toResponse(NotAuthorizedException exception)
  {
	System.out.println(exception.getMessage());
	int status = Response.Status.UNAUTHORIZED.getStatusCode();
	
	if ("USER_OR_PASS_INCORRECT".equals(exception.getMessage())) status = 701;
	else if ("USER_NOT_EXIST".equals(exception.getMessage())) status = 702;
	else if ("USER_NEED_TO_ACKNOWLEDGE_TOU".equals(exception.getMessage())) status = 703;
	else if ("USER_EXPIRED".equals(exception.getMessage())) status = 704;
	
	
	
    return Response.status(status).build();
  }

}