package org.ecommerce.rbac.api.management;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
 * This is the rbac inhertiance manager. Here you can find all required methods
 * that make this implementation fully compliant with RBAC 2.0 standard (from roles
 * general inheritance perspective).
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki/Rest-api
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki/Roles-hierarchy-rbac-support
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RbacInheritanceManager {
	/**
	 * Method used to add an inheritance relation between roleId and childId.
	 * 
	 * @param roleId Role unique identifier. From here the permissions are inherited.
	 * @param childId Child role unique identifier. Role specified by <code>roleId</code>
	 * 		inherits all users from child role.
	 */
	@Path("/{roleId}/{childId}")
	@POST
	public void addInheritance(
			@PathParam("roleId") Integer roleId,
			@PathParam("childId") Integer childId
	);

	/**
	 * Method used to delete an inheritance relation.
	 * 
	 * @param roleId Role unique identifier.
	 * @param childId Child role unique identifier.
	 */
	@Path("/{roleId}/{childId}")
	@DELETE
	public void deleteInheritance(
			@PathParam("roleId") Integer roleId,
			@PathParam("childId") Integer childId
	);
	
	/**
	 * Method used to add a first level ascendant to the specified role.
	 * All inheritance relations are rebuilt if necessary. This is fully compliant
	 * with RBAC 2.0 standard.
	 * 
	 * @param roleId Role unique identifier.
	 * @param ascendantId Ascendant role unique identifier.
	 */
	@Path("/{roleId}/ascendant/{ascendantId}")
	@POST
	public void addAscendant(
			@PathParam("roleId") Integer roleId,
			@PathParam("childId") Integer ascendantId
	);
	
	/**
	 * Method used to add a first level descendant to the specified role.
	 * All inheritance relations are rebuilt if necessary. This is fully compliant
	 * with RBAC 2.0 standard.
	 * 
	 * @param roleId
	 * @param descendantId
	 */
	@Path("/{roleId}/descendant/{descendantId}")
	@POST
	public void addDescendant(
			@PathParam("roleId") Integer roleId,
			@PathParam("descendantId") Integer descendantId 
	);
}