package org.ecommerce.rbac.impl.rest;

import java.util.List;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacOperationsManager;
import org.ecommerce.rbac.dao.OperationsDao;
import org.ecommerce.rbac.dto.Operation;
import org.ecommerce.rbac.dto.Operations;
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
 * Rbac REST operations service implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 05.10.2011
 */

@Service("rbacOperationsService")
public class RbacOperationsManagerImpl implements RbacOperationsManager {
	private final static Logger logger = Logger.getLogger(RbacOperationsManagerImpl.class.getName());
	
	private OperationsDao operationsDAO;
	
	/**
	 * Constructor that initialize all required dependencies.
	 * 
	 * @param operationsDao
	 */
	@Autowired
	public RbacOperationsManagerImpl(OperationsDao operationsDao) {
		this.operationsDAO = operationsDao;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operations loadAllOperations() {
		logger.info("REST loading all RBAC operations.");
		
		List<org.ecommerce.rbac.persistence.entities.Operation> operations = 
			operationsDAO.loadAllOperations();
		
		Operations ret = new Operations();
		
		for(org.ecommerce.rbac.persistence.entities.Operation operation : operations) {
			ret.getOperations().add(operation.toOperationDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operation loadOperationById(Integer operationId) {
		logger.info(String.format("JPA loading RBAC operation %s.", operationId));
		
		org.ecommerce.rbac.persistence.entities.Operation operation = 
			operationsDAO.loadOperationById(operationId);
		
		return operation.toOperationDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewOperation(Operation operation) {
		logger.info(String.format("Creating operation %s.", operation.getName()));
		
		operationsDAO.createNewOperation(
				org.ecommerce.rbac.persistence.entities.Operation.valueOf(operation));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateExistingOperation(Integer operationId, Operation operation) {
		logger.info(String.format("Updating operation %s.", operationId));
		
		operation.setId(operationId);
		
		operationsDAO.createNewOperation(
				org.ecommerce.rbac.persistence.entities.Operation.valueOf(operation));		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeOperation(Integer operationId) {
		logger.info(String.format("Removing operation %s.", operationId));
		
		operationsDAO.removeOperation(operationId);
	}
}
