package org.ecommerce.rbac.dao;

import java.util.List;
import java.util.Set;

import org.ecommerce.rbac.persistence.entities.Permission;
import org.ecommerce.rbac.persistence.entities.Role;
import org.ecommerce.rbac.persistence.entities.Session;

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
 * API for sessions data access object layer.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 1.0
 */
public interface SessionsDao {
	/**
	 * Load all sessions belonging to a specified user.
	 * 
	 * @param userId User unique identifier.
	 * @param onlyActive Only active flag.
	 * @return
	 */
	public List<Session> loadSessionsByUser(Integer userId, Boolean onlyActive);
	
	/**
	 * Method used to load all roles belonging to a specified session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param onlyActive Only active flag.
	 * @return
	 */
	public Set<Role> loadSessionRoles(Integer sessionId, Boolean onlyActive);
	
	/**
	 * Method used to load all permissions belonging to a session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param onlyActive Only active flag.
	 * @return
	 */
	public List<Permission> loadSessionPermissions(Integer sessionId, Boolean onlyActive);
	
	/**
	 * Method used to check if a permission is enabled within a session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param permissionId Permission unique identifier.
	 * @param onlyActive
	 * @return
	 */
	public Boolean checkSessionPermissionEnabled(
			Integer sessionId,
			Integer permissionId,
			boolean onlyActive);	
	
	/**
	 * Method used to create a new user session. It enables all non conflicting roles 
	 * if flag activateRoles is enabled.
	 * 
	 * @param userId User unique identifier.
	 * @param activateRoles Flag that signal activation of non conflicting roles.
	 * @param remoteSession This is a remote session unique identifier (jsessionid possibly).
	 * @return
	 */
	public Integer createUserSession(Integer userId, Boolean activateRoles,
			String remoteSession);
	
	/**
	 * Method used to activate a new role within the current session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param roleId Role unique identifier.
	 * @param useInheritance Flag used to determine if activation of descendants is desired 
	 * 		or not.
	 */
	public void activateSessionRole(Integer sessionId, Integer roleId,
			boolean useInheritance);
	
	/**
	 * Method used to stop a specified session.
	 * 
	 * @param sessionId Session unique identifier.
	 */
	public void stopSession(Integer sessionId);
	
	/**
	 * Method used to deactivate a specified role from a session.
	 * 
	 * @param sessionId Session unique identifier.
	 * @param roleId Role unique identifier.
	 */
	public void deactivateSessionRole(Integer sessionId, Integer roleId);
}
