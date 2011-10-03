package org.ecommerce.rbac.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ecommerce.rbac.dao.RolesDao;
import org.ecommerce.rbac.persistence.entities.Role;
import org.springframework.stereotype.Repository;

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

	@Override
	public List<Role> loadAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role loadRoleById(Integer roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNewRole(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRole(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assingUsersToRole(Integer roleId, List<Integer> users) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignPermissionsToRole(Integer roleId,
			List<Integer> permissions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRole(Integer roleId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUsersFromRole(Integer roleId, List<Integer> users) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePermissionsFromRole(Integer roleId,
			List<Integer> permissions) {
		// TODO Auto-generated method stub
		
	}
	
	
}
