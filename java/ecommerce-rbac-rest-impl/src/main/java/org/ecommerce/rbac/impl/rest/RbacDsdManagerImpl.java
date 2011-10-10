package org.ecommerce.rbac.impl.rest;

import java.util.List;
import java.util.logging.Logger;

import org.ecommerce.rbac.api.management.RbacDsdManager;
import org.ecommerce.rbac.dao.DynamicSeparationDutyDao;
import org.ecommerce.rbac.dto.DynamicSeparationRule;
import org.ecommerce.rbac.dto.DynamicSeparationRules;
import org.ecommerce.rbac.dto.Roles;
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
 * Dynamic Separation of Duty official service implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 08.10.2011
 */

@Service("rbacDsdService")
public class RbacDsdManagerImpl implements RbacDsdManager {
	private final static Logger logger = Logger.getLogger(RbacDsdManagerImpl.class.getName());

	private DynamicSeparationDutyDao dsdDAO;
	
	@Autowired
	public RbacDsdManagerImpl(DynamicSeparationDutyDao dsdDAO) {
		this.dsdDAO = dsdDAO;
	}
	
	@Override
	public DynamicSeparationRules loadAllDsd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DynamicSeparationRule loadDsdById(Integer dsdId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Roles loadDsdRoles(Integer dsdId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNewDsd(String dsdName, Integer cardinality) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void includeRolesInDsd(Integer dsdId, List<Integer> roles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateExistingDsd(Integer dsdId, DynamicSeparationRule dsd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excludeRolesFromDsd(Integer dsdId, List<Integer> roles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDsd(Integer dsdId) {
		// TODO Auto-generated method stub
		
	}	
}
