package org.ecommerce.rbac.api.management;

import java.util.List;

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

import org.ecommerce.rbac.dto.Identifiers;
import org.ecommerce.rbac.dto.Operations;
import org.ecommerce.rbac.dto.Permissions;
import org.ecommerce.rbac.dto.Role;
import org.ecommerce.rbac.dto.Roles;
import org.ecommerce.rbac.dto.Users;

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
 * This is the rbac roles manager. Here you can find all required methods
 * that make this implementation fully compliant with RBAC 2.0 standard (from roles
 * perspective). Based on this interface you can generate a rest client really easy.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.10.2011
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki/Rest-api
 */

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RbacRolesManager {
	/**
	 * Method used to load all roles currently defined in RBAC system.
	 * 
	 * @return
	 */
	@Path("/")
	@GET
	public Roles loadAllRoles();
	
	/**
	 * Method used to load a specified role.
	 * 
	 * @param roleId Role unique identifier.
	 * @return
	 */
	@Path("/{roleId}")
	@GET
	public Role loadRoleById(@PathParam("roleId") Integer roleId);
	
	/**
	 * Method used to load a role from RBAC system starting from role name.
	 * 
	 * @param roleName Role name.
	 * @return
	 */
	@Path("/byname/{roleName}")
	@GET
	public Role loadRoleByName(@PathParam("roleName") String roleName);
	
	/**
	 * Return all users belonging to a specified role.
	 * 
	 * @param roleId
	 * @return
	 */
	@Path("/{roleId}/users")
	@GET
	public Users loadRoleUsers(@PathParam("roleId") Integer roleId);
	
	/**
	 * Method used to load all permissions belonging to a specified role.
	 * 
	 * @param roleId Role unique iedntifier.
	 * @return
	 */
	@Path("/{roleId}/permissions")
	@GET
	public Permissions loadRolePermissions(@PathParam("roleId") Integer roleId);
	
	/**
	 * Method used to load all operations that can be executed onto an object
	 * under a specified role.
	 *  
	 * @param roleId Role unique identifier.
	 * @param objectId Object unique identifier/
	 * @return
	 */
	@Path("/{roleId}/operations/{objectId}")
	@GET
	public Operations loadRoleAllowedOperations(
			@PathParam("roleId") Integer roleId,
			@PathParam("objectId") Integer objectId);
	
	/**
	 * Method used to create a new role.
	 * 
	 * @param role An instance of role object we want to create.
	 */
	@Path("/")
	@POST
	public Integer createNewRole(Role role);
	
	/**
	 * Method used to update an existing role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param role An instance of role object.
	 */
	@Path("/{roleId}")
	@PUT
	public void updateExistingRole(
			@PathParam("roleId") Integer roleId, 
			Role role);
	
	/**
	 * Method used to assign a list of users to a role. Only
	 * users that are not already part of this group will be added.
	 * 
	 * @param roleId Role unique identifier.
	 * @param users The list of users we want to assign.
	 */
	@Path("/{roleId}/bulk/users")
	@PUT
	public void assignUsersToRole(
			@PathParam("roleId") Integer roleId,
			Identifiers users
	);
	
	/**
	 * Method used to assign a list of permissions to a role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param permissions The list of permissions we want to add.
	 */
	@Path("/{roleId}/bulk/permissions")
	@PUT
	public void assignPermissionsToRole(
			@PathParam("roleId") Integer roleId,
			Identifiers permissions
	);
	
	/**
	 * Method used to remove a specified role from RBAC system.
	 * 
	 * @param roleId
	 */
	@Path("/{roleId}")
	@DELETE
	public void removeRole(@PathParam("roleId") Integer roleId);
	
	/**
	 * Method used to remove a list of users from a specified role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param users A list of users.
	 */
	@Path("/{roleId}/bulk/users")
	@DELETE
	public void removeUsersFromRole(
			@PathParam("roleId") Integer roleId,
			@QueryParam("userId") List<Integer> users
	);
	
	/**
	 * Method used to remove a list of permissions from the specified role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param permissions A list of permissions.
	 */
	@Path("/{roleId}/bulk/permissions")
	@DELETE
	public void removePermissionsFromRole(
			@PathParam("roleId") Integer roleId,
			@QueryParam("permissionId") List<Integer> permissions);
}