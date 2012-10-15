package org.ecommerce.rbac.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.ecommerce.rbac.persistence.entities.Operation;
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
 * API for roles data access object layer.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */
public interface RolesDao {
	/**
	 * Method used to load all defined roles from database.
	 * 
	 * @return
	 */
	public List<Role> loadAllRoles();
	
	/**
	 * Method used to load a specified role.
	 * 
	 * @param roleId Role unique identiier.
	 * @return
	 * @throws NoResultException Thrown when role does not exist.
	 */
	public Role loadRoleById(Integer roleId);
	
	/**
	 * Method used to load a role by name.
	 * 
	 * @param roleName Role name.
	 * @return
	 */
	public Role loadRoleByName(String roleName);
	
	/**
	 * Method used to load all roles operations allowed for a specified
	 * object.
	 * 
	 * @param roleId Role unique identifier.
	 * @param objectId RBAC object unique identifier.
	 * @return
	 */
	public List<Operation> loadRoleOperationsAllowedForObject(Integer roleId, 
				Integer objectId);
	
	/**
	 * Method used to create a new role.
	 * 
	 * @param role Role instance.
	 * @return The newly created role unique identifier.
	 * 
	 * @throws EntityExistsException Thrown when the role already exist.
	 */
	public Integer createNewRole(Role role);
	
	/**
	 * Method used to update an existing role.
	 * 
	 * @param role Role instance.
	 * 
	 * @throws NoResultException Thrown when role does not exist.
	 */
	public void updateRole(Role role);

	/**
	 * Method used to assign a list of users to a specified role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param users A list of users.
	 */
	public void assignUsersToRole(Integer roleId, List<Integer> users);
	
	/**
	 * Method used to assign a list of permissions to a specified role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param permissions A list of permissions.
	 */
	public void assignPermissionsToRole(Integer roleId, List<Integer> permissions);
	
	/**
	 * Method used to delete a role from database. 
	 * 
	 * @param roleId Role unique identifier.
	 */
	public void removeRole(Integer roleId);
	
	/**
	 * Method used to remove users from a role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param users A list of users.
	 */
	public void removeUsersFromRole(Integer roleId, List<Integer> users);
	
	/**
	 * Method used to remove permissions from a role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param permissions A list of permissions.
	 */
	public void removePermissionsFromRole(Integer roleId, List<Integer> permissions);
}
