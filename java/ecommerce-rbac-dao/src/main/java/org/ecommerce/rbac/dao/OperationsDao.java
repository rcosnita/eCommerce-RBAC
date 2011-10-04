package org.ecommerce.rbac.dao;

import java.util.List;

import org.ecommerce.rbac.persistence.entities.Operation;

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
 * API for operations data access object layer.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 1.0
 */
public interface OperationsDao {
	/**
	 * Method used to load all defined operations.
	 * 
	 * @return
	 */
	public List<Operation> loadAllOperations();
	
	/**
	 * Method used to load a specified operation.
	 * 
	 * @param operationId Operation unique identifier.
	 * @return
	 */
	public Operation loadOperationById(Integer operationId);
	
	/**
	 * Method used to create a new operation.
	 * 
	 * @param operation Operation instance we want to add to database.
	 */
	public void createNewOperation(Operation operation);
	
	/**
	 * Method used to update an existing operation.
	 * 
	 * @param operation Operation instance.
	 */
	public void updateOperation(Operation operation);
	
	/**
	 * Method used to remove a specified operation.
	 * 
	 * @param operationId Operation unique identifier.
	 */
	public void removeOperation(Integer operationId);
}