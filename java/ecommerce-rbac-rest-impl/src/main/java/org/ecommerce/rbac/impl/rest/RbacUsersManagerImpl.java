package org.ecommerce.rbac.impl.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacUsersManager;
import org.ecommerce.rbac.dao.RolesDao;
import org.ecommerce.rbac.dao.UsersDao;
import org.ecommerce.rbac.dto.Operations;
import org.ecommerce.rbac.dto.Permissions;
import org.ecommerce.rbac.dto.Roles;
import org.ecommerce.rbac.dto.User;
import org.ecommerce.rbac.dto.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * Users manager official implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 08.10.2011
 */

@Service("rbacUsersService")
public class RbacUsersManagerImpl implements RbacUsersManager {
	private final static Logger logger = Logger.getLogger(RbacUsersManagerImpl.class.getName());
	
	private UsersDao usersDAO;
	
	@Autowired
	public RbacUsersManagerImpl(UsersDao usersDAO) {
		this.usersDAO = usersDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Users loadAllUsers() {
		logger.info("REST loading all defined RBAC users.");
		
		Users ret = new Users();
		
		List<org.ecommerce.rbac.persistence.entities.User> users = 
			usersDAO.loadAllUsers();
		
		for(org.ecommerce.rbac.persistence.entities.User user : users) {
			ret.getUsers().add(user.toUserDTO());
		}
		
		return ret;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Users loadAllUsers(int pageSize, int startRecord) {
		logger.info(String.format("REST loading all defined RBAC users: (%d, %d)", pageSize, startRecord));
		
		Users ret = new Users();
		
		List<org.ecommerce.rbac.persistence.entities.User> users =
				usersDAO.loadAllUsers(pageSize, startRecord);
			
		for(org.ecommerce.rbac.persistence.entities.User user : users) {
			ret.getUsers().add(user.toUserDTO());
		}
			
		return ret;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User loadUserById(Integer userId) {
		logger.info(String.format("REST loading RBAC user %s.", userId));
		
		return usersDAO.loadUserById(userId).toUserDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Roles loadUserRoles(Integer userId) {
		logger.info(String.format("REST loading RBAC user %s roles.", userId));
		
		org.ecommerce.rbac.persistence.entities.User user = 
				usersDAO.loadUserById(userId);
		
		Roles ret = new Roles();
		
		for(org.ecommerce.rbac.persistence.entities.Role role : user.getRoles()) {
			ret.getRoles().add(role.toRoleDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Permissions loadUserPermissions(Integer userId) {
		logger.info(String.format("REST loading permissions for RBAC user %s.", userId));
		
		Permissions ret = new Permissions();
		
		for(org.ecommerce.rbac.persistence.entities.Permission perm : usersDAO.loadUserPermissions(userId)) {
			ret.getPermissions().add(perm.toPermissionDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operations loadUserOperationsForObject(Integer userId,
			Integer objectId) {
		logger.info(String.format("REST loading all RBAC operations for object %s belonging to %s.",
				objectId, userId));
		
		Operations ret = new Operations();
		
		for(org.ecommerce.rbac.persistence.entities.Operation operation 
				: usersDAO.loadUserOperationForObject(userId, objectId)) {
			ret.getOperations().add(operation.toOperationDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void createUser(Integer userId, User user) {
		logger.info(String.format("REST creating new RBAC user %s.", userId));
		
		user.setId(userId);
		usersDAO.createNewUser(org.ecommerce.rbac.persistence.entities.User.valueOf(user));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updateUser(Integer userId, User user) {
		logger.info(String.format("REST updating RBAC user %s.", userId));
		
		user.setId(userId);
		usersDAO.updateUser(org.ecommerce.rbac.persistence.entities.User.valueOf(user));		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteUser(Integer userId) {
		logger.info(String.format("REST removing RBAC user %s.", userId));
		
		usersDAO.deleteUser(userId);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteUserFromAllRoles(Integer userId) {
		logger.info(String.format("REST removing user %s from all roles.", userId));
		
		usersDAO.clearUserRoles(userId);
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteUserSessions(Integer userId) {
		logger.info(String.format("REST stopping all sessions of user %s.", userId));
		
		usersDAO.stopUserSessions(userId);
	}
}