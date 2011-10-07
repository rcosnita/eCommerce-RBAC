package org.ecommerce.rbac.impl.rest.exceptions;

import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

/**
Copyright (C) 2011 by Radu Viorel Cosnita

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/

/**
 * Class used to map a ConstraintViolationException to the correct http error code.
 * Currently this is implemented only for supporting MySql error codes. 
 * Unfortunately spring does not support jpa exception handling in a such
 * granular way as it does for hibernate. 
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 08.10.2011
 */

@Provider
@Component("constraintViolationExceptionBean")
public class JpaSystemExceptionMapper implements ExceptionMapper<JpaSystemException> {
	/**
	 * Here comes an ugly part were we determine what kind of error is it:
	 *   - duplicate entry
	 *   - foreign key violation
	 *   - other case might come.
	 */
	@Override
	public Response toResponse(JpaSystemException exception) {
		SQLException rootCause = SQLException.class.cast(exception.getMostSpecificCause());
		
		Status status = Status.INTERNAL_SERVER_ERROR;
		
		switch(rootCause.getErrorCode()) {
			case 1062: 
			case 1452:
				status = Status.FORBIDDEN;
				break;
		}
		
		return Response.status(status).entity(rootCause.getMessage()).build();
	}
}