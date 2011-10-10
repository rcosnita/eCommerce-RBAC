package org.ecommerce.rbac.impl.rest;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacDsdManager;
import org.ecommerce.rbac.dao.DynamicSeparationDutyDao;
import org.ecommerce.rbac.dto.DynamicSeparationRule;
import org.ecommerce.rbac.dto.DynamicSeparationRules;
import org.ecommerce.rbac.dto.Identifiers;
import org.ecommerce.rbac.dto.Roles;
import org.ecommerce.rbac.persistence.entities.DynamicSeparationDuty;
import org.ecommerce.rbac.persistence.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
 * Dynamic Separation of Duty official service implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 08.10.2011
 */

@Service("rbacDsdService")
public class RbacDsdManagerImpl implements RbacDsdManager {
	private final static Logger logger = Logger.getLogger(RbacDsdManagerImpl.class.getName());

	private DynamicSeparationDutyDao dsdDAO;
	
	@Autowired
	public RbacDsdManagerImpl(DynamicSeparationDutyDao dsdDAO) {
		this.dsdDAO = dsdDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DynamicSeparationRules loadAllDsd() {
		logger.info("REST load all RBAC dsd.");
		
		List<org.ecommerce.rbac.persistence.entities.DynamicSeparationDuty> dsds = 
			dsdDAO.loadAllDsd();
		
		DynamicSeparationRules rules = new DynamicSeparationRules();
		
		for(org.ecommerce.rbac.persistence.entities.DynamicSeparationDuty dsd : dsds) {
			rules.getDsdRules().add(dsd.toDynamicSeparationDTO());
		}
		
		return rules;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DynamicSeparationRule loadDsdById(Integer dsdId) {
		logger.info(String.format("REST load dsd by id %s.", dsdId));
				
		return dsdDAO.loadDsdById(dsdId).toDynamicSeparationDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public Roles loadDsdRoles(Integer dsdId) {
		logger.info(String.format("REST load RBAC dsd %s roles.", dsdId));
				
		Roles roles = new Roles();
		
		org.ecommerce.rbac.persistence.entities.DynamicSeparationDuty dsd = 
			dsdDAO.loadDsdById(dsdId);
		
		for(org.ecommerce.rbac.persistence.entities.Role role : dsd.getRoles()) {
			roles.getRoles().add(role.toRoleDTO());
		}
		
		return roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewDsd(String dsdName, Integer cardinality, Identifiers roles) {
		logger.info(String.format("REST creating new dsd %s.", dsdName));
		
		DynamicSeparationDuty dsd = new DynamicSeparationDuty();
		dsd.setName(dsdName);
		dsd.setCardinality(cardinality);
		dsd.setRoles(new HashSet<Role>());
		
		dsdDAO.createNewDsd(dsd, roles.getIdentifiers());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void includeRolesInDsd(Integer dsdId, List<Integer> roles) {
		logger.info(String.format("REST assign %s roles to dsd.", roles.size()));
		
		dsdDAO.assignNewRolesToDsd(dsdId, roles);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateExistingDsd(Integer dsdId, DynamicSeparationRule dsd) {
		logger.info(String.format("REST updating dsd %s.", dsdId));
		
		dsd.setId(dsdId);
		
		dsdDAO.updateDsd(DynamicSeparationDuty.valueOf(dsd));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void excludeRolesFromDsd(Integer dsdId, List<Integer> roles) {
		logger.info(String.format("REST removing %s roles from dsd %s.",
						roles.size(), dsdId));
		
		dsdDAO.removeRolesFromDsd(dsdId, roles);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDsd(Integer dsdId) {
		logger.info(String.format("REST removing dsd %s.", dsdId));
		
		dsdDAO.removeDsd(dsdId);
	}
}