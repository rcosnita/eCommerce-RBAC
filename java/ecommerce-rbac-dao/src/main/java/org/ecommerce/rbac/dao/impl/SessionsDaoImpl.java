package org.ecommerce.rbac.dao.impl;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ecommerce.rbac.dao.SessionsDao;
import org.ecommerce.rbac.persistence.entities.Permission;
import org.ecommerce.rbac.persistence.entities.Role;
import org.ecommerce.rbac.persistence.entities.Session;
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
 * Sessions DAO official implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 05.10.2011
 */

@Repository("sessionsDaoBean")
public class SessionsDaoImpl implements SessionsDao {
	private final static Logger logger = Logger.getLogger(SessionsDaoImpl.class.getName());
	
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
	public List<Session> loadSessionsByUser(Integer userId, Boolean onlyActive) {
		logger.info(String.format("Loading all sessions for user %s --- onlyActive: %s",
						userId, onlyActive));
		
		TypedQuery<Session> query = 
			getEntityManager().createNamedQuery("Sessions.loadSessionsByUser", Session.class);
		query.setParameter("userId", userId);
		query.setParameter("active", onlyActive);
		
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Role> loadSessionRoles(Integer sessionId, Boolean onlyActive) {
		logger.info(String.format("Loading session %s roles.", sessionId));
		
		Session session = getEntityManager().find(Session.class, sessionId);
		
		if(session == null) {
			throw new NoResultException(String.format("Session %s does not exist.", sessionId));
		}
		
		if(onlyActive && !session.isActive()) {
			throw new UnsupportedOperationException(
					String.format("Session %s is not active.", sessionId));
		}
		
		return session.getSessionRoles();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Permission> loadSessionPermissions(Integer sessionId,
			Boolean onlyActive) {
		logger.info(String.format("Loading session %s permissions.", sessionId));
		
		Set<Role> roles = this.loadSessionRoles(sessionId, onlyActive);
		
		List<Permission> permissions = new ArrayList<Permission>();
		
		for(Role role : roles) {
			permissions.addAll(role.getPermissions());
		}
		
		return permissions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkSessionPermissionEnabled(Integer sessionId,
			Integer permissionId, boolean onlyActive) {
		logger.info(String.format("Checking permission %s enabled into session %s.", 
						permissionId, sessionId));
		
		List<Permission> permissions = this.loadSessionPermissions(sessionId, onlyActive);
		
		Permission permission = new Permission();
		permission.setId(permissionId);
		
		return permissions.contains(permission);
	}

	/**
	 * Method used to obtain all descendants that are don't have any DSD assigned.
	 * 
	 * @param parentRole
	 * @param descendants This is the set that collect all non conflicting roles keeping track
	 * 		of inheritance ierarchy
	 */
	private void obtainAllNonConflictingDescendants(Role parentRole, Set<Role> descendants) {
		Query query = getEntityManager().createNamedQuery("Roles.loadNonConflictingDescendants");
		query.setParameter("roleId", parentRole.getId());
		
		List<Role> roles = query.getResultList();
		
		descendants.add(parentRole);
		
		for(Role role : roles) {
			obtainAllNonConflictingDescendants(role, descendants);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Integer createUserSession(Integer userId, Boolean activateRoles,
			String remoteSession) {
		logger.info(String.format("JPA create new session for user %s. Activate roles? %s",
				userId, activateRoles));
	
		User user = new User();
		user.setId(userId);
		
		Session session = new Session();
		session.setUser(user);
		session.setActive(true);
		session.setStartDate((new GregorianCalendar()).getTime());
		session.setRemoteSession(remoteSession);
		
		if(activateRoles) {
			TypedQuery<Role> query = 
				getEntityManager().createNamedQuery("Roles.loadNonConflictingRolesForUser", Role.class);
			query.setParameter("userId", userId);
			
			List<Role> roles = query.getResultList();
			
			Set<Role> rolesSet = new HashSet<Role>();
			rolesSet.addAll(roles);
			
			for(Role role : roles) {
				obtainAllNonConflictingDescendants(role, rolesSet);
			}
			
			session.setSessionRoles(rolesSet);
		}
		
		getEntityManager().persist(session);
		
		return session.getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void activateSessionRole(Integer sessionId, Integer roleId, 
			boolean useInheritance) {
		logger.info(String.format("Activate role %s within session %s.",
				sessionId, roleId));
		
		Session session = getEntityManager().find(Session.class, sessionId);
		
		if(session == null || !session.isActive()) {
			throw new NoResultException(String.format("Session %s does not exist.", sessionId));
		}
		
		Role role = new Role();
		role.setId(roleId);

		session.getSessionRoles().add(role);
		
		if(useInheritance) {
			Set<Role> nonConflictingDescendants = new HashSet<Role>();
			
			obtainAllNonConflictingDescendants(role, nonConflictingDescendants);
			
			session.getSessionRoles().addAll(nonConflictingDescendants);
		}
		
		getEntityManager().merge(session);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void stopSession(Integer sessionId) {
		logger.info(String.format("JPA stopping session %s.", sessionId));
		
		Session session = getEntityManager().find(Session.class, sessionId);
		
		if(session == null) {
			throw new NoResultException(String.format("Session %s does not exist.", sessionId));
		}
		
		session.setActive(false);
		getEntityManager().merge(session);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deactivateSessionRole(Integer sessionId, Integer roleId) {
		logger.info(String.format("Activate role %s within session %s.",
				sessionId, roleId));
		
		Session session = getEntityManager().find(Session.class, sessionId);
		
		if(session == null || !session.isActive()) {
			throw new NoResultException(String.format("Session %s does not exist.", sessionId));
		}
		
		Role role = new Role();
		role.setId(roleId);
		
		session.getSessionRoles().remove(role);
		
		getEntityManager().merge(session);		
	}
}
