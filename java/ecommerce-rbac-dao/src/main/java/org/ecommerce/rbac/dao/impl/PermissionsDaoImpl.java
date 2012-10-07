package org.ecommerce.rbac.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ecommerce.rbac.dao.PermissionsDao;
import org.ecommerce.rbac.persistence.entities.Operation;
import org.ecommerce.rbac.persistence.entities.Permission;
import org.ecommerce.rbac.persistence.entities.Role;
import org.ecommerce.rbac.persistence.entities.SecurityObject;
import org.springframework.stereotype.Repository;
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
 * Roles DAO official implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 05.10.2011
 */

@Repository("permissionsDaoBean")
public class PermissionsDaoImpl implements PermissionsDao {
	private final static Logger logger = Logger.getLogger(PermissionsDaoImpl.class.getName());
	
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> loadAllPermissions() {
		logger.info("JPA loading all permissions.");
		
		TypedQuery<Permission> query = getEntityManager().createNamedQuery("Permissions.loadAll",
									Permission.class);
		
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Permission loadPermissionById(Integer permissionId) {
		logger.info(String.format("JPA loading permission %s.", permissionId));
		
		Permission perm = getEntityManager().find(Permission.class, permissionId);
		
		if(perm == null) {
			throw new NoResultException(String.format("Permission %s does not exist.", permissionId));
		}
		
		return perm;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Role> loadRolesForPermission(Integer permissionId) {
		logger.info(String.format("JPA loading all roles granted to %s permission.", permissionId));
		
		TypedQuery<Role> query = getEntityManager().createNamedQuery("Roles.loadForPermission", Role.class);
		query.setParameter("permissionId", permissionId);
		
		return query.getResultList();
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public int createNewPermission(Integer operationId, Integer objectId,
			Permission permission) {
		logger.info(String.format("JPA create new permission connecting operation %s and object %s.",
				operationId, objectId));
		
		if(permission.getId() != null) {
			throw new UnsupportedOperationException("You mustn't specify permission id.");
		}
		
		Operation operation = new Operation();
		operation.setId(operationId);
		
		SecurityObject object = new SecurityObject();
		object.setId(objectId);
		
		permission.setOperation(operation);
		permission.setObject(object);
		
		getEntityManager().persist(permission);
		
		return permission.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updatePermission(Permission permission) {
		logger.info(String.format("JPA updating permission %s.", permission.getId()));
		
		if(permission.getId() == null) {
			throw new UnsupportedOperationException("You must specify permission id.");
		}
		
		Permission permEntity = this.loadPermissionById(permission.getId());
		permEntity.setName(permission.getName());
		
		getEntityManager().merge(permEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removePermission(Integer permissionId) {
		logger.info(String.format("JPA removing permission %s.", permissionId));
		
		Permission perm = getEntityManager().find(Permission.class, permissionId);
		
		if(perm == null) {
			throw new NoResultException(String.format("Permission %s not found.", permissionId));
		}
		
		this.removePermissionFromRoles(permissionId);
		
		getEntityManager().remove(perm);
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public void removePermissionFromRoles(Integer permissionId) {
		logger.info(String.format("JPA removing permission %s from all roles.", permissionId));
		
		Query query = getEntityManager().createNamedQuery("Permission.removeFromRoles");
		query.setParameter(1, permissionId);
		
		query.executeUpdate();		
	}
}
