package org.ecommerce.rbac.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.ecommerce.rbac.persistence.entities.Operation;
import org.ecommerce.rbac.persistence.entities.Permission;
import org.ecommerce.rbac.persistence.entities.User;

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
 * API for data access object that allows users management.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */
public interface UsersDao {
	/**
	 * Method used to load all users from database.
	 * 
	 * @return
	 */
	public List<User> loadAllUsers();
	
	/**
	 * Method used to obtain from database a slice of users.
	 *  
	 * @param pageSize
	 * @param startRecord
	 * @return
	 */
	public List<User> loadAllUsers(int pageSize, int startRecord);
	
	/**
	 * Method used to load a specified user.
	 * 
	 * @param id User unique identifier.
	 * @return
	 */
	public User loadUserById(Integer id);
	
	/**
	 * Method used to load all permissions for a specified user.
	 * 
	 * @param userId User unique identifier.
	 * @return
	 */
	public List<Permission> loadUserPermissions(Integer userId);
	
	/**
	 * Method used to load all operations for an object assigned to an user.
	 * 
	 * @param userId User unique identifier.
	 * @param objectId Object unique identifier.
	 * @return
	 */
	public List<Operation> loadUserOperationForObject(Integer userId, Integer objectId);
	
	/**
	 * Method used to create a new user.
	 * 
	 * @param user An user instance.
	 * 
	 * @throws UnsupportedOperationException if user id is not specified.
	 */
	public void createNewUser(User user);
	
	/**
	 * Method used to update an existing user.
	 * 
	 * @param user An user instance.
	 * 
	 * @throws NoResultException if user can not be found. 
	 */
	public void updateUser(User user);
	
	/**
	 * Method used to remove an existing user.
	 * 
	 * @param userId User unique identifier.
	 */
	public void deleteUser(Integer userId);
	
	/**
	 * Method used to remove all roles from a given user.
	 * 
	 * @param userId User unique identifier.
	 */
	public void clearUserRoles(Integer userId);
	
	/**
	 * Method used to stop all user active sessions using a single transaction.
	 *  
	 * @param id User unique identifier.
	 * 
	 * @throws NoResultException if user can not be found.
	 */
	public void stopUserSessions(Integer id);
}
