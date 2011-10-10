package org.ecommerce.rbac.dao;

import java.util.List;

import org.ecommerce.rbac.persistence.entities.DynamicSeparationDuty;

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
 * Data access layer API for dynamic separation of duty (dsd).
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 08.10.2011
 */

public interface DynamicSeparationDutyDao {
	/**
	 * Method used to load all defined dsds.
	 * 
	 * @return
	 */
	public List<DynamicSeparationDuty> loadAllDsd();
	
	/**
	 * Method used to obtain a specified dsd. 
	 * 
	 * @param dsdId DSD unique identifier.
	 * @return
	 */
	public DynamicSeparationDuty loadDsdById(Integer dsdId);
	
	/**
	 * Method used to create a new dynamic separation of duty from the 
	 * specified roles.
	 * 
	 * @param dsd DSD instance.
	 * @param roles A list of roles unique identifiers.
	 */
	public void createNewDsd(DynamicSeparationDuty dsd, List<Integer> roles);
	
	/**
	 * Method used to assign new roles to a specified dsd.
	 * 
	 * @param dsdId DSD unique identifier.
	 * @param roles A list of roles to add.
	 */
	public void assignNewRolesToDsd(Integer dsdId, List<Integer> roles);
	
	/**
	 * Method used to update an existing dsd.
	 * 
	 * @param dsd DSD instance.
	 */
	public void updateDsd(DynamicSeparationDuty dsd);
	
	/**
	 * Method used to remove a specified dsd.
	 * 
	 * @param dsdId DSD unique identifier.
	 */
	public void removeDsd(Integer dsdId);
		
	/**
	 * Method used to remove roles from a dsd.
	 * 
	 * @param dsdId DSD unique identifier.
	 * @param roles A list of roles to remove.
	 */
	public void removeRolesFromDsd(Integer dsdId, List<Integer> roles);
}