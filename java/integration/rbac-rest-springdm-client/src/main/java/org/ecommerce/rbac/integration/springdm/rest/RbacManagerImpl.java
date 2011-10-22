package org.ecommerce.rbac.integration.springdm.rest;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ecommerce.rbac.api.RbacManager;
import org.ecommerce.rbac.api.management.RbacObjectsManager;
import org.ecommerce.rbac.api.management.RbacOperationsManager;
import org.ecommerce.rbac.api.management.RbacPermissionsManager;
import org.ecommerce.rbac.api.management.RbacRolesManager;
import org.ecommerce.rbac.api.management.RbacSessionsManager;
import org.ecommerce.rbac.api.management.RbacUsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RBAC Manager bean that integrates with rest api.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 17.10.2011
 */

@Component("rbacManagerImpl")
public class RbacManagerImpl implements RbacManager {
	private final static Logger logger = Logger.getLogger(RbacManagerImpl.class.getName());
	
	private RbacUsersManager usersManager;
	private RbacRolesManager rolesManager;
	private RbacObjectsManager objectsManager;
	private RbacOperationsManager operationsManager;
	private RbacPermissionsManager permissionsManager;
	private RbacSessionsManager sessionsManager;
	
	@Autowired
	public RbacManagerImpl(RbacUsersManager usersManager, RbacRolesManager rolesManager,
			RbacObjectsManager objectsManager, RbacOperationsManager operationsManager,
			RbacPermissionsManager permissionsManager, RbacSessionsManager sessionsManager) {
		this.usersManager = usersManager;
		this.rolesManager = rolesManager;
		this.objectsManager = objectsManager;
		this.operationsManager = operationsManager;
		this.permissionsManager = permissionsManager;
		this.sessionsManager = sessionsManager;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RbacUsersManager getRbacUsersManager() {
		return this.usersManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RbacRolesManager getRbacRolesManager() {
		return this.rolesManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RbacObjectsManager getRbacObjectsManager() {
		return this.objectsManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RbacOperationsManager getRbacOperationsManager() {
		return this.operationsManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RbacPermissionsManager getRbacPermissionsManager() {
		return this.permissionsManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RbacSessionsManager getRbacSessionsManager() {
		return this.sessionsManager;
	}	
	
	@PostConstruct
	private void start() {
		logger.info("RBAC Manager Client started");
	}
	
	@PreDestroy
	private void stop() {
		logger.info("RBAC Manager Client stopped");
	}	
}