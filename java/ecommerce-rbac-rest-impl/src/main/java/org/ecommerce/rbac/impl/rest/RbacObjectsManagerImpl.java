package org.ecommerce.rbac.impl.rest;

import java.util.List;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacObjectsManager;
import org.ecommerce.rbac.dao.SecurityObjectsDao;
import org.ecommerce.rbac.dto.SecurityObject;
import org.ecommerce.rbac.dto.SecurityObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
 * Rbac objects manager implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 06.10.2011
 */
@Service("rbacObjectsService")
public class RbacObjectsManagerImpl implements RbacObjectsManager {
	private final static Logger logger = Logger.getLogger(RbacObjectsManagerImpl.class.getName());
	
	private SecurityObjectsDao objectsDAO;
	
	@Autowired
	public RbacObjectsManagerImpl(SecurityObjectsDao objectsDAO) {
		this.objectsDAO = objectsDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SecurityObjects loadAllObjects() {
		logger.info("REST loading all defined RBAC objects.");
		
		List<org.ecommerce.rbac.persistence.entities.SecurityObject> objects = 
			objectsDAO.loadAllObjects();
		
		SecurityObjects ret = new SecurityObjects();
		
		for(org.ecommerce.rbac.persistence.entities.SecurityObject object : objects) {
			ret.getObjects().add(object.toSecurityObjectDTO());
		}
		
		return ret;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SecurityObjects loadAllObjects(int startRecord, int pageSize, String searchQuery) {
		logger.info(String.format("REST loading %s RBAC objects starting from %s", pageSize, startRecord));
		
		List<org.ecommerce.rbac.persistence.entities.SecurityObject> objects = 
				objectsDAO.loadAllObjects(startRecord, pageSize, searchQuery);
		
		SecurityObjects ret = new SecurityObjects();
		
		for(org.ecommerce.rbac.persistence.entities.SecurityObject object : objects) {
			ret.getObjects().add(object.toSecurityObjectDTO());
		}
		
		return ret;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SecurityObject loadObjectById(Integer objectId) {
		logger.info(String.format("REST loading RBAC object %s.", objectId));
		
		return objectsDAO.loadObjectById(objectId).toSecurityObjectDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public int createNewObject(SecurityObject object) {
		logger.info(String.format("REST creating RBAC object %s.", object.getName()));
		
		return objectsDAO.createNewObject(org.ecommerce.rbac.persistence.entities.SecurityObject.valueOf(object));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void updateExistingObject(Integer objectId, SecurityObject object) {
		logger.info(String.format("REST updating RBAC object %s.", object.getName()));
		
		object.setId(objectId);
		objectsDAO.updateObject(org.ecommerce.rbac.persistence.entities.SecurityObject.valueOf(object));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removeObject(Integer objectId) {
		logger.info(String.format("REST removing RBAC object %s.", objectId));
		
		objectsDAO.removeObject(objectId);
	}
}