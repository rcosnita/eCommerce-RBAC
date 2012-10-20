package org.ecommerce.rbac.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ecommerce.rbac.dao.RolesDao;
import org.ecommerce.rbac.persistence.entities.Operation;
import org.ecommerce.rbac.persistence.entities.Permission;
import org.ecommerce.rbac.persistence.entities.Role;
import org.ecommerce.rbac.persistence.entities.User;
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
 * Roles data access object layer official implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 03.10.2011
 */

@Repository("rolesDaoBean")
public class RolesDaoImpl implements RolesDao {
	private final static Logger logger = Logger.getLogger(RolesDaoImpl.class.getName());
	
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
	public List<Role> loadAllRoles() {
		logger.info("JPA loading all rbac roles.");
		
		TypedQuery<Role> query = getEntityManager().createNamedQuery("Roles.loadAll", Role.class);
		
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role loadRoleById(Integer roleId) {
		logger.info(String.format("JPA loading role %s.", roleId));
		
		return getEntityManager().find(Role.class, roleId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role loadRoleByName(String roleName) {
		logger.info(String.format("JPA loading role %s.", roleName));
		
		TypedQuery<Role> q = getEntityManager().createNamedQuery("Roles.loadByName", Role.class);
		q.setParameter("roleName", roleName);
			
		return q.getSingleResult();
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Integer createNewRole(Role role) {
		logger.info(String.format("JPA create role %s.", role.getName()));
		
		if(role.getId() != null) {
			throw new UnsupportedOperationException("You can not specify role id for this operation.");
		}
		
		getEntityManager().persist(role);
		
		return role.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updateRole(Role role) {
		logger.info(String.format("JPA updating role %s.", role.getName()));
		
		if(role.getId() == null) {
			throw new UnsupportedOperationException("You must specify an existing role.");
		}
		
		Role roleEntity = this.loadRoleById(role.getId());
		roleEntity.setName(role.getName());
		
		getEntityManager().merge(roleEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void assignUsersToRole(Integer roleId, List<Integer> users) {
		logger.info(String.format("JPA assign %s users to role %s.", users.size(), roleId));
		
		Role role = getEntityManager().find(Role.class, roleId);
		
		if(role == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		for(Integer user : users) {
			User usrEntity = new User();
			usrEntity.setId(user);
			
			role.getAssignedUsers().add(usrEntity);
		}
		
		getEntityManager().merge(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void assignPermissionsToRole(Integer roleId,
			List<Integer> permissions) {
		logger.info(String.format("JPA assign %s permissions to role %s.", 
						permissions.size(), roleId));
		
		Role role = getEntityManager().find(Role.class, roleId);

		if(role == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}		
		
		for(Integer permId : permissions) {
			Permission perm = new Permission();
			perm.setId(permId);
			
			role.getPermissions().add(perm);
		}
		
		getEntityManager().merge(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removeRole(Integer roleId) {
		logger.info(String.format("JPA removing role %s.", roleId));
		
		Role role = getEntityManager().find(Role.class, roleId);
		
		if(role == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		getEntityManager().remove(role);
	}

	/**
	 * Method used to remove all users from a given role.
	 * 
	 * @param roleId Role unique identifier.
	 */
	@Transactional
	private void removeAllUsersFromRole(Integer roleId) {
		Query query = getEntityManager().createNamedQuery("Roles.removeAllUsers");
		query.setParameter(1, roleId);
		
		query.executeUpdate();
	}
	
	/**
	 * Method used to remove all permissions from a given role.
	 * 
	 * @param roleId Role unique identifier.
	 */
	@Transactional
	private void removeAllPermissionsFromRole(Integer roleId) {
		Query query = getEntityManager().createNamedQuery("Roles.removeAllPermissions");
		query.setParameter(1, roleId);
		
		query.executeUpdate();
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removeUsersFromRole(Integer roleId, List<Integer> users) {
		logger.info(String.format("JPA removing %s users from role %s.",
						users.size(), roleId));		
		
		Role role = getEntityManager().find(Role.class, roleId);
		
		if(role == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		if(users.isEmpty()) {
			this.removeAllUsersFromRole(roleId);
			
			return;
		}
		
		for(Integer userId : users) {
			User user = new User();
			user.setId(userId);
			
			role.getAssignedUsers().remove(user);
		}
		
		getEntityManager().merge(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removePermissionsFromRole(Integer roleId,
			List<Integer> permissions) {
		logger.info(String.format("JPA removing %s permissions from role %s.",
				permissions.size(), roleId));

		Role role = getEntityManager().find(Role.class, roleId);
		
		if(role == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		if(permissions.isEmpty()) {
			this.removeAllPermissionsFromRole(roleId);
			
			return;
		}		
		
		for(Integer permId : permissions) {
			Permission perm = new Permission();
			perm.setId(permId);
			
			role.getPermissions().remove(perm);
		}
		
		getEntityManager().merge(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Operation> loadRoleOperationsAllowedForObject(Integer roleId,
			Integer objectId) {
		logger.info(String.format("JPA loading allowed operations for object %s under role %s.",
						objectId, roleId));
		
		TypedQuery<Operation> query = 
			getEntityManager().createNamedQuery("Operations.loadAllowedForRoleObject", Operation.class);
		
		query.setParameter("roleId", roleId);
		query.setParameter("objectId", objectId);
		
		return query.getResultList();
	}
}
