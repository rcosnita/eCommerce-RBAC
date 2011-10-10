package org.ecommerce.rbac.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
 * Class that defines the data model for a RBAC operation.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="Operations")
@NamedQueries({
	@NamedQuery(name="Operations.loadAll", query="SELECT obj FROM Operation obj ORDER BY obj.name"),
	@NamedQuery(name="Operations.loadAllowedForRoleObject",
			query="SELECT oper FROM Operation oper " +
				"INNER JOIN oper.permissions perm " +
				"INNER JOIN perm.roles role " +
				"INNER JOIN perm.object obj " +
				"WHERE obj.id = :objectId and role.id = :roleId"),
	@NamedQuery(name="Operations.loadUserOperationsForObject",
			query="SELECT oper FROM Operation oper " +
				"INNER JOIN oper.permissions perm " +
				"INNER JOIN perm.object obj " +
				"INNER JOIN perm.roles role " +
				"INNER JOIN role.assignedUsers user " +
				"WHERE user.id = :userId AND obj.id = :objectId")
})
public class Operation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="operation")
	private List<Permission> permissions;

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

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}	
	
	/**
	 * Method used to transform the current object into a transferable one.
	 * 
	 * @return
	 */
	public org.ecommerce.rbac.dto.Operation toOperationDTO() {
		org.ecommerce.rbac.dto.Operation ret = new org.ecommerce.rbac.dto.Operation();

		ret.setId(this.getId());
		ret.setName(this.getName());
		
		return ret;
	}
	
	/**
	 * Method used to format a transferable object to an entity.
	 * @param operation An operation instance.
	 * @return
	 */
	public static Operation valueOf(org.ecommerce.rbac.dto.Operation operation) {
		Operation ret = new Operation();
		
		ret.setId(operation.getId());
		ret.setName(operation.getName());
		
		return ret;
	}
}