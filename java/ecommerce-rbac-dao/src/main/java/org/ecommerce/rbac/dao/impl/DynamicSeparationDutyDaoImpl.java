package org.ecommerce.rbac.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.ecommerce.rbac.dao.DynamicSeparationDutyDao;
import org.ecommerce.rbac.persistence.entities.DynamicSeparationDuty;
import org.ecommerce.rbac.persistence.entities.Role;
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
 * Official dsd implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.10.2011
 */

@Repository("dynamicSeparationDutyDao")
public class DynamicSeparationDutyDaoImpl implements DynamicSeparationDutyDao {
	private final static Logger logger = Logger.getLogger(DynamicSeparationDutyDaoImpl.class.getName());
	
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
	public List<DynamicSeparationDuty> loadAllDsd() {
		logger.info("JPA loading all RBAC dsd.");
		
		TypedQuery<DynamicSeparationDuty> query =
			getEntityManager().createNamedQuery("DSD.loadAll", DynamicSeparationDuty.class);
 		
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DynamicSeparationDuty loadDsdById(Integer dsdId) {
		logger.info(String.format("JPA load RBAC dsd %s.", dsdId));
		
		DynamicSeparationDuty dsd = getEntityManager().find(DynamicSeparationDuty.class, dsdId);
		
		if(dsd == null) {
			throw new NoResultException(String.format("RBAC dsd %s does not exist.", dsdId));
		}
		
		return dsd;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void createNewDsd(DynamicSeparationDuty dsd,
			List<Integer> roles) {
		logger.info(String.format("JPA create new RBAC dsd %s from %s roles.", 
				dsd.getName(), roles.size()));
		
		if(dsd.getId() != null) {
			throw new UnsupportedOperationException("You must not specify id.");
		}
		
		if(roles.size() < 2) {
			throw new UnsupportedOperationException("A DSD must contain at least two roles.");
		}
		
		Set<Role> rolesEntity = new HashSet<Role>();
		
		for(Integer roleId : roles) {
			Role role = new Role();
			role.setId(roleId);
			
			rolesEntity.add(role);
		}
		
		dsd.setRoles(rolesEntity);
				
		getEntityManager().persist(dsd);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void assignNewRolesToDsd(Integer dsdId, List<Integer> roles) {
		logger.info(String.format("JPA assigning %s roles to dsd %s.", 
						roles.size(), dsdId));
		
		DynamicSeparationDuty dsd = getEntityManager().find(DynamicSeparationDuty.class, dsdId);
		
		if(dsd == null) {
			throw new NoResultException(String.format("RBAC dsd %s does not exist.", dsdId));
		}
		
		for(Integer roleId : roles) {
			Role role = new Role();
			role.setId(roleId);
			
			dsd.getRoles().add(role);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updateDsd(DynamicSeparationDuty dsd) {
		logger.info(String.format("JPA updating dsd %s.", dsd.getId()));
		
		if(dsd.getId() == null) {
			throw new UnsupportedOperationException("You must specify a dsd identifier.");
		}
		
		DynamicSeparationDuty dsdEntity = this.loadDsdById(dsd.getId());
		dsdEntity.setName(dsd.getName());
		dsdEntity.setCardinality(dsd.getCardinality());
		
		getEntityManager().merge(dsdEntity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removeDsd(Integer dsdId) {
		logger.info(String.format("JPA removing dsd %s.", dsdId));
		
		DynamicSeparationDuty dsd = getEntityManager().find(DynamicSeparationDuty.class, dsdId);
		
		if(dsd == null) {
			throw new NoResultException(String.format("JPA RBAC dsd %s does not exist.", dsdId));
		}
		
		getEntityManager().remove(dsd);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removeRolesFromDsd(Integer dsdId, List<Integer> roles) {
		logger.info(String.format("JPA removing %s roles from dsd %s.", 
						roles.size(), dsdId));
		
		DynamicSeparationDuty dsd = getEntityManager().find(DynamicSeparationDuty.class, dsdId);
		
		if(dsd == null) {
			throw new NoResultException(String.format("JPA RBAC dsd %s does not exist.", dsdId));
		}		
		
		for(Integer roleId : roles) {
			Role role = new Role();
			role.setId(roleId);
			
			dsd.getRoles().remove(role);
		}
		
		if(dsd.getRoles().size() < 2) {
			throw new UnsupportedOperationException("A DSD must containt at least two roles.");
		}
		
		getEntityManager().merge(dsd);
	}
}