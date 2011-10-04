package org.ecommerce.rbac.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ecommerce.rbac.dao.UsersDao;
import org.ecommerce.rbac.persistence.entities.Session;
import org.ecommerce.rbac.persistence.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
Copyright (C) 2001 by Radu Viorel Cosnita

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
 * Users data access object layer implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 03.10.2011
 */

@Repository("usersDaoBean")
public class UsersDaoImpl implements UsersDao {
	private final static Logger logger = Logger.getLogger(UsersDaoImpl.class.getName());
	
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
	public List<User> loadAllUsers() {
		logger.info("JPA loading all RBAC users.");
				
		Query query = getEntityManager().createNamedQuery("Users.loadAll");
		
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User loadUserById(Integer id) {
		logger.info(String.format("JPA loading RBAC user %s.", id));
		
		return getEntityManager().find(User.class, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void createNewUser(User user) {
		logger.info(String.format("JPA creating adding new user %s.", user.getId()));
		
		getEntityManager().persist(user);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method is not supported (no need as no user attributes are currently 
	 * implemented).
	 */
	@Override
	@Transactional
	public void updateUser(User user) {
		logger.info(String.format("JPA updating user %s.", user.getId()));
		
		throw new UnsupportedOperationException("This is not supported yet.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void stopUserSessions(Integer id) {
		logger.info(String.format("JPA stop all active sessions for user %s.", id));
		
		Query query = getEntityManager().createNamedQuery("Sessions.loadActiveSessionsByUser");
		query.setParameter("userId", id);
		
		List<Session> sessions = query.getResultList();
		
		if(sessions.isEmpty()) {
			logger.info(String.format("JPA No active sessions for user %s.", id));
		}
		
		for(Session session : sessions) {
			session.setActive(false);
			getEntityManager().merge(session);
		}
		
		logger.info(String.format("JPA stopped %s active sessions for user %s.", id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteUser(Integer userId) {
		logger.info(String.format("JPA removing user %s.", userId));
		
		User user = getEntityManager().find(User.class, userId);
		
		if(user == null) {
			throw new NoResultException(String.format("User %s not found.", userId));
		}
		
		getEntityManager().remove(user);
	}
}