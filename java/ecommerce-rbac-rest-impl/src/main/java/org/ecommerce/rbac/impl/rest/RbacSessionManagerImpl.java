package org.ecommerce.rbac.impl.rest;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacSessionsManager;
import org.ecommerce.rbac.dao.SessionsDao;
import org.ecommerce.rbac.dto.Permissions;
import org.ecommerce.rbac.dto.Roles;
import org.ecommerce.rbac.dto.Sessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Session manager official implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 12.10.2011
 */

@Service("rbacSessionsService")
public class RbacSessionManagerImpl implements RbacSessionsManager {
	private final static Logger logger = Logger.getLogger(RbacSessionManagerImpl.class.getName());
	
	private SessionsDao sessionsDAO;
	
	@Autowired
	public RbacSessionManagerImpl(SessionsDao sessionsDAO) {
		this.sessionsDAO = sessionsDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sessions loadAllSessionsByUser(Integer userId, boolean onlyActive) {
		logger.info(String.format("REST load sessions for user %s. Only active: %s",
						userId, onlyActive));
		
		List<org.ecommerce.rbac.persistence.entities.Session> sessions =
			sessionsDAO.loadSessionsByUser(userId, onlyActive);
		
		Sessions sessionsDTO = new Sessions();
		
		for(org.ecommerce.rbac.persistence.entities.Session session : sessions) {
			sessionsDTO.getSessions().add(session.toSessionDTO());
		}
		
		return sessionsDTO;
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public Roles loadSessionRoles(Integer sessionId, boolean onlyActive) {
		logger.info(String.format("REST load session %s. Only active: %s.", 
						sessionId, onlyActive));
		
		Roles roles = new Roles();
		
		Set<org.ecommerce.rbac.persistence.entities.Role> roleEntities = 
			sessionsDAO.loadSessionRoles(sessionId, onlyActive);
		
		for(org.ecommerce.rbac.persistence.entities.Role role : roleEntities) {
			roles.getRoles().add(role.toRoleDTO());
		}
		
		return roles;
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public Permissions loadSessionPermissions(Integer sessionId,
			boolean onlyActive) {
		logger.info(String.format("REST loading session permission %s. Only active: %s.",
						sessionId, onlyActive));
		
		List<org.ecommerce.rbac.persistence.entities.Permission> perms = 
			sessionsDAO.loadSessionPermissions(sessionId, onlyActive);
		
		Permissions permsDTO = new Permissions();
		
		for(org.ecommerce.rbac.persistence.entities.Permission perm : perms) {
			permsDTO.getPermissions().add(perm.toPermissionDTO());
		}
		
		return permsDTO;
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public Boolean checkSessionPermissionEnabled(Integer sessionId,
			Integer permissionId, boolean onlyActive) {
		logger.info(String.format("REST checking permission %s in session %s. Only active: %s.", 
						sessionId, permissionId, onlyActive));
		
		return sessionsDAO.checkSessionPermissionEnabled(sessionId, permissionId, onlyActive);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public Integer startUserSession(Integer userId, boolean activateRoles, 
			String remoteSession) {
		logger.info(String.format("REST start user %s session with remote session %s. Only active: %s.", 
						userId, remoteSession, activateRoles));
		
		return sessionsDAO.createUserSession(userId, activateRoles, remoteSession);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public void activateSessionRole(Integer sessionId, Integer roleId, 
					boolean useInheritance) {
		logger.info(String.format("REST activate role %s in session %s. Activate descendant roles: ",
						roleId, sessionId, useInheritance));
		
		sessionsDAO.activateSessionRole(sessionId, roleId, useInheritance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stopSession(Integer sessionId) {
		logger.info(String.format("REST stop session %s.", sessionId));
		
		sessionsDAO.stopSession(sessionId);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public void deactivateSessionRole(Integer sessionId, Integer roleId) {
		logger.info(String.format("REST deactivate role %s from session %s.", 
						sessionId, roleId));
		
		sessionsDAO.deactivateSessionRole(sessionId, roleId);
	}
}