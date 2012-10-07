package org.ecommerce.rbac.impl.rest;

import java.util.List;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacPermissionsManager;
import org.ecommerce.rbac.dao.PermissionsDao;
import org.ecommerce.rbac.dto.Operation;
import org.ecommerce.rbac.dto.Permission;
import org.ecommerce.rbac.dto.Permissions;
import org.ecommerce.rbac.dto.Roles;
import org.ecommerce.rbac.dto.SecurityObject;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

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
 * RBAC permissions manager implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 06.10.2011
 */

@Service("rbacPermissionsService")
public class RbacPermissionsManagerImpl implements RbacPermissionsManager {
	private final static Logger logger = Logger.getLogger(RbacPermissionsManagerImpl.class.getName());
	
	private PermissionsDao permissionsDAO;
	
	@Autowired
	public RbacPermissionsManagerImpl(PermissionsDao permissionsDAO) {
		this.permissionsDAO = permissionsDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Permissions loadAllPermissions() {
		logger.info("REST loading all RBAC permissions.");
		
		List<org.ecommerce.rbac.persistence.entities.Permission> permissions =
			permissionsDAO.loadAllPermissions();
		
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
	public Permission loadPermissionById(Integer permissionId) {
		logger.info(String.format("REST loading RBAC permission %s.", permissionId));
		
		return permissionsDAO.loadPermissionById(permissionId).toPermissionDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Roles loadRolesForPermission(Integer permissionId) {
		logger.info(String.format("REST loading RBAC roles for permission %s.", permissionId));
		
		List<org.ecommerce.rbac.persistence.entities.Role> rolesEntities = permissionsDAO.loadRolesForPermission(permissionId);
		
		Roles roles = new Roles();
		
		for(org.ecommerce.rbac.persistence.entities.Role role : rolesEntities) {
			roles.getRoles().add(role.toRoleDTO());
		}
		
		return roles;
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operation loadPermissionOperation(Integer permissionId) {
		logger.info(String.format("REST loading RBAC permission %s operation.", permissionId));
		
		return permissionsDAO.loadPermissionById(permissionId).getOperation().toOperationDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SecurityObject loadPermissionObject(Integer permissionId) {
		logger.info(String.format("REST loading RBAC permission %s object.", permissionId));
		
		return permissionsDAO.loadPermissionById(permissionId).getObject().toSecurityObjectDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int createNewPermissions(Integer operationId, Integer objectId,
			Permission permission) {
		logger.info(String.format("REST creating RBAC permission from operation %s and object %s.",
				operationId, objectId));
		
		org.ecommerce.rbac.persistence.entities.Permission permEntity = 
			org.ecommerce.rbac.persistence.entities.Permission.valueOf(permission);
		
		int permissionId = permissionsDAO.createNewPermission(operationId, objectId, permEntity);
		
		return permissionId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateExistingPermission(Integer permissionId,
			Permission permission) {
		logger.info(String.format("REST updating RBAC permission %s.",
				permissionId));
		
		permission.setId(permissionId);
		
		permissionsDAO.updatePermission(org.ecommerce.rbac.persistence.entities.Permission.valueOf(permission));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePermission(Integer permissionId) {
		logger.info(String.format("REST removing RBAC permission %s.",
				permissionId));
		
		permissionsDAO.removePermission(permissionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removePermissionFromRoles(Integer permissionId) {
		logger.info(String.format("REST removing RBAC permission %s from all roles.",permissionId));
		
		permissionsDAO.removePermissionFromRoles(permissionId);
	}
}
