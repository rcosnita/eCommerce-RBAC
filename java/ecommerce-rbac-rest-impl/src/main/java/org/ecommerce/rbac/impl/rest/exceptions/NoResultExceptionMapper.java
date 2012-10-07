package org.ecommerce.rbac.impl.rest.exceptions;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

/**
 * Class used to implement a mechanism that transform NoResultException
 * into a rest exception.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 */
@Component("noResultExceptionMapperBean")
@Provider
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response toResponse(NoResultException exception) {
		Response response = 
			Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
		
		return response;
	}
}