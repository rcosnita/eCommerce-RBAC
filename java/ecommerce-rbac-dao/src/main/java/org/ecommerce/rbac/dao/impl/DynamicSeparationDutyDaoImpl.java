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