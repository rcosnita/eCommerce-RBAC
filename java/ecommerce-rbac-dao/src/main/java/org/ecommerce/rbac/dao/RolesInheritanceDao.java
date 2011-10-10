package org.ecommerce.rbac.dao;

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
 * API for roles inheritance data access object. This DAO is managing
 * the inheritance relations between roles. Keep in mind that inheritance
 * from RBAC 2.0 standard is done on multiple levels.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.10.2011
 */

public interface RolesInheritanceDao {
	/**
	 * Method used to create an inheritance relation.
	 * 
	 * @param roleId Role unique identifier.
	 * @param childId Child role unique identifier.
	 */
	public void createInheritance(Integer roleId, Integer childId);
	
	/**
	 * Method used to remove the inheritance relation between the roles specified.
	 * 
	 * @param roleId Role unique identifier.
	 * @param childId Child role unique identifier.
	 */
	public void removeInheritance(Integer roleId, Integer childId);
	
	/**
	 * Method used to add a direct ascendant of the specified role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param ascendantId Ascendant role unique identifier.
	 */
	public void addAscendant(Integer roleId, Integer ascendantId);
	
	/**
	 * Method used to add a direct descendant for the specified role.
	 * 
	 * @param roleId Role unique identifier.
	 * @param descendantId Descendant unique identifier.
	 */
	public void addDescendant(Integer roleId, Integer descendantId);
}