package org.ecommerce.rbac.persistence.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
 * Class that defines the data model for a RBAC permission.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="Permissions")
@NamedQueries({
	@NamedQuery(name="Permissions.loadAll", 
			query="SELECT perm FROM Permission perm ORDER BY perm.name"),
	@NamedQuery(name="Permissions.loadUserPermissions",
			query="SELECT perm FROM Permission perm INNER JOIN perm.roles role " +
				"INNER JOIN role.assignedUsers user " +
				"WHERE user.id = :userId")
})
@NamedNativeQueries({
	@NamedNativeQuery(name="Permission.removeFromRoles", 
			query="DELETE FROM AssignedPermissions WHERE permission_id = ?1", 
			resultClass=Permission.class)
})
public class Permission {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="operation_id", referencedColumnName="id")
	private Operation operation;
	
	@ManyToOne
	@JoinColumn(name="object_id", referencedColumnName="id")
	private SecurityObject object;
	
	@ManyToMany(cascade={CascadeType.ALL}, mappedBy="permissions")
	private Set<Role> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public SecurityObject getObject() {
		return object;
	}

	public void setObject(SecurityObject object) {
		this.object = object;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}	
	
	/**
	 * Method used to transform the current entity to a transferable
	 * permission.
	 * 
	 * @return
	 */
	public org.ecommerce.rbac.dto.Permission toPermissionDTO() {
		org.ecommerce.rbac.dto.Permission ret = new org.ecommerce.rbac.dto.Permission();
		
		ret.setId(this.getId());
		ret.setName(this.getName());
		ret.setObject(this.getObject().toSecurityObjectDTO());
		ret.setOperation(this.getOperation().toOperationDTO());
		
		return ret;
	}	
	
	/**
	 * Method used to convert a transferable permission to an entity.
	 */
	public static Permission valueOf(org.ecommerce.rbac.dto.Permission perm) {
		Permission ret = new Permission();
		
		ret.setId(perm.getId());
		ret.setName(perm.getName());
		
		if(perm.getObject() != null) {
			ret.setObject(new SecurityObject());
			ret.getObject().setId(perm.getObject().getId());
		}
		
		if(perm.getOperation() != null) {
			ret.setOperation(new Operation());
			ret.getOperation().setId(perm.getOperation().getId());
		}
		
		return ret;
	}
	
	/**
	 * Permissions equality is determined based on name or permision i.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Permission) {
			Permission perms = Permission.class.cast(obj);
			
			return perms.getId() == this.getId() || 
				perms.getName().equalsIgnoreCase(this.getName());
		}
		
		return super.equals(obj);
	}
}