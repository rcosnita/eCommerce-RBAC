package org.ecommerce.rbac.api;

import org.ecommerce.rbac.api.management.RbacObjectsManager;
import org.ecommerce.rbac.api.management.RbacOperationsManager;
import org.ecommerce.rbac.api.management.RbacPermissionsManager;
import org.ecommerce.rbac.api.management.RbacRolesManager;
import org.ecommerce.rbac.api.management.RbacSessionsManager;
import org.ecommerce.rbac.api.management.RbacUsersManager;

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
 * This is the rbac core features manager. Here you can find all required methods
 * that make this implementation fully compliant with RBAC 2.0 standard (from core
 * components perspective).
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.10.2011
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki
 */

public interface RbacManager {
	/**
	 * Method used to return an instance of  RbacUserManager object. Using
	 * this object you can interact with users from rbac system. 
	 * 
	 * @return
	 */
	public RbacUsersManager getRbacUsersManager();
	
	/**
	 * Method used to return an instance of RbacRolesManager object. Using this
	 * object you can interact with roles from rbac system.
	 * 
	 * @return
	 */
	public RbacRolesManager getRbacRolesManager();
	
	/**
	 * Method used to return an instance of RbacObjectsManager object. Using
	 * this object you can interact with objects from rbac system.
	 * 
	 * @return
	 */
	public RbacObjectsManager getRbacObjectsManager();

	/**
	 * Method used to return an instance of RbacOperationsManager object. Using
	 * this object you can interact with operations from RBAC system.
	 * 
	 * @return
	 */
	public RbacOperationsManager getRbacOperationsManager();
	
	/**
	 * Method used to return an instance of RbacPermissionsManager object. Using
	 * this object you can interact with permissions from rbac system.
	 * 
	 * @return
	 */	
	public RbacPermissionsManager getRbacPermissionsManager();
	
	/**
	 * Method used to return an instance of RbacSessionsManager object. Using
	 * this object you can interact with sessions from RBAC system.
	 * @return
	 */
	public RbacSessionsManager getRbacSessionsManager();
}