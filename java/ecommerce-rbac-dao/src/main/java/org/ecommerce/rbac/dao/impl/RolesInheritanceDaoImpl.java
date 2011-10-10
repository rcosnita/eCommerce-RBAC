package org.ecommerce.rbac.dao.impl;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.ecommerce.rbac.dao.RolesInheritanceDao;
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
 * Roles inheritance RBAC implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 10.10.2011
 */

@Repository("rolesInheritanceDao")
public class RolesInheritanceDaoImpl implements RolesInheritanceDao {
	private final static Logger logger = Logger.getLogger(RolesInheritanceDaoImpl.class.getName());
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Method used to check if a role is descendant of another role.
	 * 
	 * @param parent Parent role.
	 * @param child Child role.
	 * 
	 * @return The role from descendants ierarchy where child can be found.
	 */
	private Role isDescendant(Role parent, Role child) {
		for(Role descendant : parent.getDescendants()) {
			if(descendant == child) {
				return parent;
			}
			
			for(Role directDescendant : descendant.getDescendants()) {
				return isDescendant(directDescendant, child);
			}
		}
		
		return null;
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void createInheritance(Integer roleId, Integer childId) {
		logger.info(String.format("JPA RBAC role %s inherits permissions from role %s.", roleId, childId));
		
		Role parentRole = getEntityManager().find(Role.class, roleId);
		Role childRole = getEntityManager().find(Role.class, childId);
		
		if(parentRole == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		if(childRole == null) {
			throw new NoResultException(String.format("Role %s does not exist.", childId));
		}
	
		if(isDescendant(parentRole, childRole) == null) {
			throw new UnsupportedOperationException(String.format("Role %s already inherits permission from role %s.",
								roleId, childId));
		}
		
		parentRole.getDescendants().add(childRole);
		getEntityManager().merge(parentRole);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void removeInheritance(Integer roleId, Integer childId) {
		logger.info(String.format("JPA remove RBAC roles inheritance between %s and %s.", roleId, childId));
		
		Role parentRole = getEntityManager().find(Role.class, roleId);
		Role childRole = getEntityManager().find(Role.class, childId);
		
		if(parentRole == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		if(childRole == null) {
			throw new NoResultException(String.format("Role %s does not exist.", childId));
		}
		
		Role ierarchyTreeNode = isDescendant(parentRole, childRole);
		
		if(ierarchyTreeNode == null) {
			throw new UnsupportedOperationException(String.format("Role %s does not inherit permissions from role %s.",
							roleId, childId));
		}
		
		ierarchyTreeNode.getDescendants().remove(childRole);
		getEntityManager().merge(ierarchyTreeNode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void addAscendant(Integer roleId, Integer ascendantId) {
		logger.info(String.format("JPA add RBAC direct ascendant %s for role %s: Users inheritance."));
		
		Role role = getEntityManager().find(Role.class, roleId);
		Role ascendantRole = getEntityManager().find(Role.class, ascendantId);
		
		if(role == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		if(ascendantRole == null) {
			throw new NoResultException(String.format("Role %s does not exist.", ascendantId));
		}
		
		Role ierarchyTreeNode = isDescendant(role, ascendantRole);
		
		if(ierarchyTreeNode != null) {
			throw new UnsupportedOperationException(String.format("RBAC role %s is an descendant of %s.",
							ascendantId, roleId));
		}
		
		ierarchyTreeNode = isDescendant(ascendantRole, role);

		if(ierarchyTreeNode != null) {
			throw new UnsupportedOperationException(String.format("RBAC role %s is an ascendant of %s.",
							ascendantId, roleId));
		}
		
		// moving the first level ascendants one level up: they will inherit ascendant role.
		for(Role currAscendant : role.getAscendants()) {
			currAscendant.getDescendants().remove(role);
			currAscendant.getDescendants().add(ascendantRole);
			
			getEntityManager().merge(currAscendant);
		}
		
		// adding the direct ascendant
		role.getAscendants().add(ascendantRole);
		getEntityManager().merge(role);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void addDescendant(Integer roleId, Integer descendantId) {
		logger.info(String.format("JPA add RBAC direct %s descendant to role %s: Users inheritance.",
						descendantId, roleId));
		
		Role role = getEntityManager().find(Role.class, roleId);
		Role descendantRole = getEntityManager().find(Role.class, descendantId);
		
		if(role == null) {
			throw new NoResultException(String.format("Role %s does not exist.", roleId));
		}
		
		if(descendantRole == null) {
			throw new NoResultException(String.format("Role %s does not exist.", descendantId));
		}
		
		Role ierarchyTreeNode = isDescendant(role, descendantRole);
		
		if(ierarchyTreeNode != null) {
			throw new UnsupportedOperationException(
					String.format("Role %s is already a descendant of %s.", descendantId, roleId));
		}
		
		ierarchyTreeNode = isDescendant(descendantRole, role);
		
		if(ierarchyTreeNode != null) {
			throw new UnsupportedOperationException(
					String.format("Role %s is ascendant of %s.", roleId, descendantId));
		}
		
		// moving the first level of descendants down one level: they will be direct descendants of descendantRole
		for(Role currDescendant : role.getDescendants()) {
			currDescendant.getAscendants().remove(role);
			currDescendant.getAscendants().add(descendantRole);
			
			getEntityManager().merge(currDescendant);
		}
		
		// adding descendantRole as first level descendant.
		role.getDescendants().add(descendantRole);
		getEntityManager().merge(role);
	}
}