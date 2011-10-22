package org.ecommerce.rbac.integration.springdm.rest;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.ecommerce.rbac.api.Rbac;
import org.ecommerce.rbac.api.RbacManager;
import org.ecommerce.rbac.api.management.RbacDsdManager;
import org.ecommerce.rbac.api.management.RbacInheritanceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rbac implementation for integrating REST api to an application.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 17.10.2011
 */

@Component("rbacClient")
public class RbacImpl implements Rbac {
	private final static Logger logger = Logger.getLogger(RbacImpl.class.getName());

	private RbacManager rbacManager;
	private RbacDsdManager dsdManager;
	private RbacInheritanceManager inheritanceManager;
	
	@Autowired
	public RbacImpl(RbacManager rbacManager, RbacDsdManager dsdManager,
			RbacInheritanceManager inheritanceManager) {
		this.rbacManager = rbacManager;
		this.dsdManager = dsdManager;
		this.inheritanceManager = inheritanceManager;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RbacManager getRbacManager() {
		return this.rbacManager;
	}

	@Override
	public RbacDsdManager getDsdManager() {
		return dsdManager;
	}

	@Override
	public RbacInheritanceManager getInheritanceManager() {
		return inheritanceManager;
	}
	
	@PostConstruct
	private void start() {
		logger.info("RBAC Client started");
	}
	
	@PreDestroy
	private void stop() {
		logger.info("RBAC Client stopped");
	}	
}
