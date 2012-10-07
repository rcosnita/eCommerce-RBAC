package org.ecommerce.rbac.dao;

import java.util.List;

import org.ecommerce.rbac.persistence.entities.Permission;
import org.ecommerce.rbac.persistence.entities.Role;

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
 * API for permissions data access object layer.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 03.10.2011
 */
public interface PermissionsDao {
	/**
	 * Method used to load all defined permissions.
	 * 
	 * @return
	 */
	public List<Permission> loadAllPermissions();
	
	/**
	 * Method used to load specified permission.
	 * 
	 * @param permissionId Permission unique identifier.
	 * @return
	 */
	public Permission loadPermissionById(Integer permissionId);
	
	/**
	 * Method used to load all roles that hold a given permission.
	 * 
	 * @param permissionId Permission unique identifier.
	 * @return
	 */
	public List<Role> loadRolesForPermission(Integer permissionId);
	
	/**
	 * Method used to create a new permission.
	 * 
	 * @param operationId Operation unique identifier.
	 * @param objectId Object unique identifier.
	 * @param permission Permission instance.
	 */
	public int createNewPermission(
			Integer operationId,
			Integer objectId, 			
			Permission permission);
	
	/**
	 * Update an existing permission.
	 * 
	 * @param permission A permission instance.
	 */
	public void updatePermission(Permission permission);
	
	/**
	 * Remove an existing permission.
	 * 
	 * @param permissionId Permission unique identifier.
	 */
	public void removePermission(Integer permissionId);
	
	/**
	 * Removes a given permission from all roles who are granted to use it.
	 * 
	 * @param permissionId Permission unique identifier.
	 */
	public void removePermissionFromRoles(Integer permissionId);
}