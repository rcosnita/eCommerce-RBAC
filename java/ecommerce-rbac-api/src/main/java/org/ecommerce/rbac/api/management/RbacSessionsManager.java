package org.ecommerce.rbac.api.management;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.ecommerce.rbac.dto.Permissions;
import org.ecommerce.rbac.dto.Roles;
import org.ecommerce.rbac.dto.Sessions;
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
 * This is the rbac sessions manager. Here you can find all required methods
 * that make this implementation fully compliant with RBAC 2.0 standard (from sessions
 * perspective). Based on this interface you can generate a rest client really easy.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.10.2011
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki/Rest-api
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RbacSessionsManager {
	/**
	 * Method used to load a user sessions.
	 * 
	 * @param userId User unique identifier.
	 * @param onlyActive (Optional) returns only active sessions.
	 * @return
	 */
	@Path("/{userId}")
	@GET
	public Sessions loadAllSessionsByUser(
			@PathParam("userId") Integer userId,
			@QueryParam("onlyActive") boolean onlyActive);
	
	/**
	 * Method used to load all activated roles from a session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param onlyActive Make sure the session is active. If not an exception is thrown.
	 * @return
	 */
	@Path("/{sessionId}/roles")
	@GET
	public Roles loadSessionRoles(
			@PathParam("sessionId") Integer sessionId,
			@QueryParam("onlyActive") boolean onlyActive);	
	
	/**
	 * Method used to load all activated permissions from the specified session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param onlyActive Make sure that session is active. If not an exception is thrown.
	 * @return
	 */
	@Path("/{sessionId}/permissions")
	@GET
	public Permissions loadSessionPermissions(
			@PathParam("sessionId") Integer sessionId,
			@QueryParam("onlyActive") boolean onlyActive);
	
	/**
	 * Method used to check if a permission is enabled within a specified session. 
	 * 
	 * @param sessionId Session unique identifier.
	 * @param permissionId Permission unique identifier.
	 * @param onlyActive Make sure the session is active. If not an exception is thrown.
	 * @return
	 */
	@Path("/{sessionId}/permissions/{permissionId}")
	@GET
	public Boolean checkSessionPermissionEnabled(
			@PathParam("sessionId") Integer sessionId,
			@PathParam("permissionId") Integer permissionId,
			@QueryParam("onlyActive") boolean onlyActive);
	
	/**
	 * Method used to start a new session for a specified user.
	 * 
	 * @param userId User unique identifier.
	 * @param activateRoles (Optional) flag used to force RBAC to activate by default all non conflicting
	 * 		roles. Not a standard feature of RBAC.
	 * @param remoteSession This is the remote session identifier. In most cases it will be a hash probably.
	 * @return The newly started session for the user.
	 */
	@Path("/{userId}")
	@POST
	public Integer startUserSession(
			@PathParam("userId") Integer userId,
			@QueryParam("activateRoles") boolean activateRoles,
			@QueryParam("remoteSession") String remoteSession);
	
	/**
	 * Method used to activate a role within a specified session. It also
	 * activates all descendants. Really cool for working with hierarchical RBAC.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param roleId Role unique identifier.
	 * @param useInheritance Flag used to activate roles all from hierarchy of roleId.
	 * @return
	 */
	@Path("/{sessionId}/roles/{roleId}")
	@PUT
	public void activateSessionRole(
			@PathParam("sessionId") Integer sessionId,
			@PathParam("roleId") Integer roleId,
			@QueryParam("useInheritance") boolean useInheritance);
	
	/**
	 * Method used to stop a specified session.
	 * 
	 * @param sessionId Session unique identifier.
	 */
	@Path("/{sessionId}")
	@DELETE
	public void stopSession(@PathParam("sessionId") Integer sessionId);
	
	/**
	 * Method used to deactivate an active role from session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param roleId Role unique identifier.
	 */
	@Path("/{sessionId}/roles/{roleId}")
	@DELETE
	public void deactivateSessionRole(
			@PathParam("sessionId") Integer sessionId,
			@PathParam("roleId") Integer roleId);
}