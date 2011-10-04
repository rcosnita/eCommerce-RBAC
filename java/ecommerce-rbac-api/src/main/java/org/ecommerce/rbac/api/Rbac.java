package org.ecommerce.rbac.api;

import org.ecommerce.rbac.api.management.RbacDsdManager;
import org.ecommerce.rbac.api.management.RbacInheritanceManager;

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
 * This is the main entry point of ecommerce rbac project. This provides
 * all methods to interact with the rbac implementation in a uniform way.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.10.2011
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki
 */

public interface Rbac {
	/**
	 * Method used to an instance of rbac core features manager.
	 * 
	 * @return
	 */
	public RbacManager getRbacManager();
	
	/**
	 * Method used to obtain a dynamic separation of duty manager.
	 * 
	 * @return
	 */
	public RbacDsdManager getDsdManager();
	
	/**
	 * Method used to obtain an rbac inheritance manager.. 
	 * 
	 * @return
	 */
	public RbacInheritanceManager getInheritanceManager();
}
