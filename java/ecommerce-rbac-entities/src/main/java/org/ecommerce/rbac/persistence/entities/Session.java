package org.ecommerce.rbac.persistence.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
 * Class used to model a RBAC session component.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="Sessions")
@NamedQueries({
	@NamedQuery(name="Sessions.loadActiveSessionsByUser", 
			query="SELECT obj FROM Session obj " +
				"WHERE obj.active = 1 AND obj.user.id = :userId"),
	@NamedQuery(name="Sessions.loadSessionsByUser",
			query="SELECT obj FROM Session obj " +
				"WHERE obj.user.id = :userId AND (obj.active = :active OR :active IS NULL)")
})
public class Session {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="remote_session")
	private String remoteSession;

	/**
	 * These are all roles activated within the current session.
	 */
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="SessionRoles",
			joinColumns={@JoinColumn(name="session_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	private Set<Role> sessionRoles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemoteSession() {
		return remoteSession;
	}

	public void setRemoteSession(String remoteSession) {
		this.remoteSession = remoteSession;
	}
	
	public Set<Role> getSessionRoles() {
		return sessionRoles;
	}

	public void setSessionRoles(Set<Role> sessionRoles) {
		this.sessionRoles = sessionRoles;
	}
	
	/**
	 * Method used to transform the entity to a transferable object.
	 * @return
	 */
	public org.ecommerce.rbac.dto.Session toSessionDTO() {
		org.ecommerce.rbac.dto.Session session = new org.ecommerce.rbac.dto.Session();
		session.setId(this.getId());
		session.setStartTime(this.getStartDate());
		session.setEndTime(this.getEndDate());
		session.setActive(this.isActive());
		session.setRemoteSession(this.getRemoteSession());
		
		return session;
	}
}