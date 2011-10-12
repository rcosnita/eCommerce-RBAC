package org.ecommerce.rbac.impl.rest;

import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacInheritanceManager;
import org.ecommerce.rbac.dao.RolesInheritanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * Inheritance manager official implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.10.2011
 */

@Service("rbacInheritanceService")
public class RbacInheritanceManagerImpl implements RbacInheritanceManager {
	private final static Logger logger = Logger.getLogger(RbacInheritanceManagerImpl.class.getName());
	
	private RolesInheritanceDao inheritanceDAO;
	
	@Autowired
	public RbacInheritanceManagerImpl(RolesInheritanceDao inheritanceDAO) {
		this.inheritanceDAO = inheritanceDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInheritance(Integer roleId, Integer childId) {
		logger.info(String.format("REST RBAC role %s inherits permissions from role %s.",
					roleId, childId));
		
		inheritanceDAO.createInheritance(roleId, childId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteInheritance(Integer roleId, Integer childId) {
		logger.info(String.format("REST RBAC remove inheritance between %s and %s.",
						roleId, childId));
		
		inheritanceDAO.removeInheritance(roleId, childId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAscendant(Integer roleId, Integer ascendantId) {
		logger.info(String.format("REST RBAC add %s role as direct ascendant of %s.",
				ascendantId, roleId));
		
		inheritanceDAO.addAscendant(roleId, ascendantId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDescendant(Integer roleId, Integer descendantId) {
		logger.info(String.format("REST RBAC add %s role as direct descendant of %s.",
				descendantId, roleId));
		
		inheritanceDAO.addDescendant(roleId, descendantId);
	}
}