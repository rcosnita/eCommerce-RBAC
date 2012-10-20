package org.ecommerce.rbac.impl.rest;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacRolesManager;
import org.ecommerce.rbac.dao.RolesDao;
import org.ecommerce.rbac.dto.Identifiers;
import org.ecommerce.rbac.dto.Operations;
import org.ecommerce.rbac.dto.Permissions;
import org.ecommerce.rbac.dto.Role;
import org.ecommerce.rbac.dto.Roles;
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
 * Roles manager official implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 08.10.2011
 */

@Service("rbacRolesManagerService")
public class RbacRolesManagerImpl implements RbacRolesManager {
	private final static Logger logger = Logger.getLogger(RbacRolesManagerImpl.class.getName());
	
	private RolesDao rolesDAO;
	
	@Autowired
	public RbacRolesManagerImpl(RolesDao rolesDAO) {
		this.rolesDAO = rolesDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Roles loadAllRoles() {
		logger.info("REST loading all RBAC roles defined.");
		
		List<org.ecommerce.rbac.persistence.entities.Role> roles =
			rolesDAO.loadAllRoles();
		
		Roles ret = new Roles();
		
		for(org.ecommerce.rbac.persistence.entities.Role role : roles) {
			ret.getRoles().add(role.toRoleDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role loadRoleById(Integer roleId) {
		logger.info(String.format("REST loading RBAC role %s.", roleId));
		
		return rolesDAO.loadRoleById(roleId).toRoleDTO();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role loadRoleByName(String roleName) {
		logger.info(String.format("REST loading RBAC role %s.", roleName));
		
		return rolesDAO.loadRoleByName(roleName).toRoleDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Users loadRoleUsers(Integer roleId) {
		logger.info(String.format("REST loading RBAC users for role %s.", roleId));
		
		Set<org.ecommerce.rbac.persistence.entities.User> users = 
			rolesDAO.loadRoleById(roleId).getAssignedUsers();
		
		Users ret = new Users();
		
		for(org.ecommerce.rbac.persistence.entities.User user : users) {
			ret.getUsers().add(user.toUserDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Permissions loadRolePermissions(Integer roleId) {
		logger.info(String.format("REST loading RBAC permissions for role %s.", roleId));
		
		Set<org.ecommerce.rbac.persistence.entities.Permission> permissions =
			rolesDAO.loadRoleById(roleId).getPermissions();
		
		Permissions ret = new Permissions();
		
		for(org.ecommerce.rbac.persistence.entities.Permission perm : permissions) {
			ret.getPermissions().add(perm.toPermissionDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Operations loadRoleAllowedOperations(Integer roleId, Integer objectId) {
		logger.info(String.format("REST loading RBAC operations for role %s allowed on object %s.", 
						roleId, objectId));
		
		List<org.ecommerce.rbac.persistence.entities.Operation> operations =
			rolesDAO.loadRoleOperationsAllowedForObject(roleId, objectId);
		
		Operations ret = new Operations();
		
		for(org.ecommerce.rbac.persistence.entities.Operation operation : operations) {
			ret.getOperations().add(operation.toOperationDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer createNewRole(Role role) {
		logger.info(String.format("REST creating new RBAC role %s.", role.getName()));
		
		if(role.getId() != null) {
			throw new UnsupportedOperationException("You must not specify id.");
		}
		
		return rolesDAO.createNewRole(org.ecommerce.rbac.persistence.entities.Role.valueOf(role));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateExistingRole(Integer roleId, Role role) {
		logger.info(String.format("REST updating RBAC role %s.", role.getName()));
		
		if(roleId == null) {
			throw new UnsupportedOperationException("You must specify id.");
		}
		
		role.setId(roleId);
		
		rolesDAO.updateRole(org.ecommerce.rbac.persistence.entities.Role.valueOf(role));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void assignUsersToRole(Integer roleId, Identifiers users) {
		List<Integer> ids = users.getIdentifiers();
		
		logger.info(String.format("REST assigning %s users to role %s.", ids.size(), roleId));
		
		rolesDAO.assignUsersToRole(roleId, ids);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void assignPermissionsToRole(Integer roleId,
			Identifiers permissions) {
		List<Integer> ids = permissions.getIdentifiers();
		
		logger.info(String.format("REST assigning %s permissions to role %s.", 
						ids.size(), roleId));
		
		rolesDAO.assignPermissionsToRole(roleId, ids);		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeRole(Integer roleId) {
		logger.info(String.format("REST removing RBAC role %s.", roleId));
		
		rolesDAO.removeRole(roleId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeUsersFromRole(Integer roleId, List<Integer> users) {
		logger.info(String.format("REST removing %s users from role %s.",
				users.size(), roleId));
		
		rolesDAO.removeUsersFromRole(roleId, users);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePermissionsFromRole(Integer roleId,
			List<Integer> permissions) {
		logger.info(String.format("REST removing %s permissions from role %s.",
						permissions.size(), roleId));
		
		rolesDAO.removePermissionsFromRole(roleId, permissions);
	}
}