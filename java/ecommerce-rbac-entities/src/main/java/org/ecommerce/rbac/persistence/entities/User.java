package org.ecommerce.rbac.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
 * Class used to implement a RBAC user. Currently we hold only the 
 * unique identifier of the user. This unique identifier will not 
 * be even set by the RBAC system but instead by the system that integrates
 * with this RBAC implementation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="Users")
@NamedQueries({
	@NamedQuery(name="Users.loadAll", query="SELECT obj FROM User obj")
})
@NamedNativeQueries({
	@NamedNativeQuery(name="Users.removeFromAllRoles", query="DELETE FROM AssignedUsers WHERE user_id = ?1", resultClass=User.class)
})
public class User {
	@Id
	@Column(name="id")
	private Integer id;
	
	@ManyToMany(mappedBy="assignedUsers")
	private List<Role> roles = new ArrayList<Role>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * Method used to transform the current entity to a transferable
	 * object.
	 * 
	 * @return
	 */
	public org.ecommerce.rbac.dto.User toUserDTO() {
		org.ecommerce.rbac.dto.User user = new org.ecommerce.rbac.dto.User();
		
		user.setId(this.getId());
		
		return user;
	}
	
	/**
	 * Method used to transform a transferable user to an entity.
	 * 
	 * @param user A dto user instance.
	 * @return
	 */
	public static User valueOf(org.ecommerce.rbac.dto.User user) {
		User ret = new User();
		
		ret.setId(user.getId());
		
		return ret;
	}
	
	/**
	 * Users equality is determined based on primary key.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User usr = User.class.cast(obj);
			return usr.getId() == this.getId();
		}
		
		return super.equals(obj);
	}
}