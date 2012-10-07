package org.ecommerce.rbac.api.management;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ecommerce.rbac.dto.Operation;
import org.ecommerce.rbac.dto.Permission;
import org.ecommerce.rbac.dto.Permissions;
import org.ecommerce.rbac.dto.Roles;
import org.ecommerce.rbac.dto.SecurityObject;

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
 * This is the rbac operations manager. Here you can find all required methods
 * that make this implementation fully compliant with RBAC 2.0 standard (from permissions
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
public interface RbacPermissionsManager {
	/**
	 * Method used to load all permissions currently defined in RBAC system.
	 * 
	 * @return
	 */
	@Path("/")
	@GET
	public Permissions loadAllPermissions();
	
	/**
	 * Method used to load a specified permission.
	 * 
	 * @param permissionId Permission unique identifier.
	 * @return
	 */
	@Path("/{permissionId}")
	@GET
	public Permission loadPermissionById(@PathParam("permissionId") Integer permissionId);
	
	/**
	 * Method used to load all roles that are granted with the specified permission.
	 *  
	 * @param permissionId Permission unique identifier.
	 * @return
	 */
	@Path("/{permissionId}/roles")
	@GET
	public Roles loadRolesForPermission(@PathParam("permissionId") Integer permissionId);
	
	/**
	 * Method used to load operation belonging to a permission. 
	 * 
	 * @param permissionId
	 * @return
	 */
	@Path("/{permissionId}/operation")
	@GET
	public Operation loadPermissionOperation(@PathParam("permissionId") Integer permissionId);
	
	/**
	 * Method used to load object belonging to a permission. 
	 * 
	 * @param permissionId
	 * @return
	 */
	@Path("/{permissionId}/object")
	@GET
	public SecurityObject loadPermissionObject(@PathParam("permissionId") Integer permissionId);
	
	/**
	 * Method used to create a new permission from a specified operation 
	 * and a specified object.
	 * 
	 * @param operationId Operation unique identifier.
	 * @param objectId Object unique identifier.
	 * @param permission Permission instance.
	 */
	@Path("{operationId}/{objectId}")
	@POST
	public int createNewPermissions(
			@PathParam("operationId") Integer operationId, 
			@PathParam("objectId") Integer objectId,
			Permission permission);	
	
	/**
	 * Method used to update a specified permission.
	 * 
	 * @param permissionId Permission unique identifier.
	 * @param permission Permission instance.
	 */
	@Path("/{permissionId}")
	@PUT
	public void updateExistingPermission(
			@PathParam("permissionId") Integer permissionId,
			Permission permission);	
	
	/**
	 * Method used to remove the specified permission.
	 * 
	 * @param permissionId Permission unique identifier.
	 */
	@Path("/{permissionId}")
	@DELETE
	public void removePermission(@PathParam("permissionId") Integer permissionId);
	
	/**
	 * Method used to remove a given permission from all roles which are granted to use it.
	 * 
	 * @param permissionId Permission unique identifier.
	 */
	@Path("/{permissionId}/roles")
	@DELETE
	public void removePermissionFromRoles(@PathParam("permissionId") Integer permissionId);
}