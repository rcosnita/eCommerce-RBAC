package org.ecommerce.rbac.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
 * Class used to implement RBAC role component.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="Roles")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	/**
	 * These are the members assined to this role. Initially this would be an 
	 * empty collection.
	 */
	@ManyToMany
	@JoinTable(name="AssignedUsers",
			joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")}
	)
	private List<User> assignedUsers;
	
	/**
	 * These are all direct descendants of this role.
	 */
	@ManyToMany
	@JoinTable(name="RolesInheritance",
			joinColumns={@JoinColumn(name="role_parent", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_child", referencedColumnName="id")})
	private List<Role> descendants;

	/**
	 * These are all direct ascendants of this role.
	 */
	@ManyToMany
	@JoinTable(name="RolesInheritance",
			joinColumns={@JoinColumn(name="role_child", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_parent", referencedColumnName="id")})
	private List<Role> ascendants;
	
	/**
	 * Here we can access all DSD in which this role appears.
	 */
	@ManyToMany(mappedBy="roles")
	private List<DynamicSeparationDuty> dynamicSeparations;
	
	@ManyToMany
	@JoinTable(name="AssignedPermissions",
			joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="permission_id", referencedColumnName="id")})
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
	
	public List<User> getAssignedUsers() {
		return assignedUsers;
	}

	public void setAssignedUsers(List<User> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}	
	
	public List<DynamicSeparationDuty> getDynamicSeparations() {
		return dynamicSeparations;
	}

	public void setDynamicSeparations(List<DynamicSeparationDuty> dynamicSeparations) {
		this.dynamicSeparations = dynamicSeparations;
	}
	
	public List<Role> getDescendants() {
		return descendants;
	}

	public void setDescendants(List<Role> descendants) {
		this.descendants = descendants;
	}

	public List<Role> getAscendants() {
		return ascendants;
	}

	public void setAscendants(List<Role> ascendants) {
		this.ascendants = ascendants;
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}	
}