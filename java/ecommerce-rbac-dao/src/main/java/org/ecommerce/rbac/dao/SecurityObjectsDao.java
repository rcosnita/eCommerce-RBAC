package org.ecommerce.rbac.dao;

import java.util.List;

import org.ecommerce.rbac.persistence.entities.SecurityObject;

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
 * API for security objects data access object layer.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */
public interface SecurityObjectsDao {
	/**
	 * Method used to retrieve all security objects from database.
	 * 
	 * @return
	 */
	public List<SecurityObject> loadAllObjects();
	
	/**
	 * Method used to retrieve a slice of objects from database.
	 * 
	 * @param startRecord The record from which we start retrieving objects.
	 * @param pageSize The number of objects we want to retrieve.
	 * @param searchQuery The text used for matching object name against.
	 * 
	 * @return
	 */
	public List<SecurityObject> loadAllObjects(int startRecord, int pageSize, String searchQuery);	
	
	/**
	 * Method used to load a specified object.
	 * 
	 * @param objectId Object unique identifier.
	 * @return
	 */
	public SecurityObject loadObjectById(Integer objectId);
	
	/**
	 * Method used to create a new security object.
	 * 
	 * @param object SecurityObject instance.
	 * @return The newly created object identifier.
	 */
	public int createNewObject(SecurityObject object);
	
	/**
	 * Method used to update a security object. 
	 * 
	 * @param object SecurityObject instance.
	 */
	public void updateObject(SecurityObject object);
	
	/**
	 * Method used to remove a specified security object.
	 * 
	 * @param objectId SecurityObject unique identifier.
	 */
	public void removeObject(Integer objectId);
}
