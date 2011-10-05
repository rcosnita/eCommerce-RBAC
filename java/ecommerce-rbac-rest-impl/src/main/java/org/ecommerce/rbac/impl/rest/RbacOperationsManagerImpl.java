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
	public void createNewOperation(Integer operationId, Operation operation) {
		logger.info(String.format("Creating operation %s.", operationId));
		
		operation.setId(operationId);
		
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
