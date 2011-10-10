package org.ecommerce.rbac.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.ecommerce.rbac.dao.OperationsDao;
import org.ecommerce.rbac.persistence.entities.Operation;
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
 * Operations DAO implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 05.10.2011
 */

@Repository("operationsDaoBean")
public class OperationsDaoImpl implements OperationsDao {
	private final static Logger logger = Logger.getLogger(OperationsDaoImpl.class.getName());
	
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
	public List<Operation> loadAllOperations() {
		logger.info("JPA loading all operations.");
		
		TypedQuery<Operation> query = getEntityManager().createNamedQuery("Operations.loadAll",
											Operation.class);
		
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operation loadOperationById(Integer operationId) {
		logger.info(String.format("JPA Loading operation %s.", operationId));
		
		Operation operation = getEntityManager().find(Operation.class, operationId);
		
		if(operation == null) {
			throw new NoResultException(String.format("Operation %s does not exist.", operationId));
		}
		
		return operation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void createNewOperation(Operation operation) {
		logger.info(String.format("JPA creating operation %s.", operation.getName()));
		
		if(operation.getId() != null) {
			throw new UnsupportedOperationException("You must not specify operation id.");
		}
		
		getEntityManager().persist(operation);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updateOperation(Operation operation) {
		logger.info(String.format("JPA updating operation %s.", operation.getName()));
		
		if(operation.getId() == null) {
			throw new UnsupportedOperationException("You must specify operation id.");
		}
		
		Operation operationEntity = this.loadOperationById(operation.getId());
		operationEntity.setName(operation.getName());
		
		getEntityManager().merge(operationEntity);	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removeOperation(Integer operationId) {
		logger.info(String.format("JPA deleting operation %s.", operationId));
		
		Operation operation = getEntityManager().find(Operation.class, operationId);
		
		if(operation == null) {
			throw new NoResultException(String.format("Operation %s does not exist."));
		}
		
		getEntityManager().remove(operation);
	}
}
