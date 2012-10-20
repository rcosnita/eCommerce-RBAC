package org.ecommerce.rbac.persistence.entities;

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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

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
 * Class used to implement RBAC role component.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="Roles")
@NamedQueries({
	@NamedQuery(name="Roles.loadAll", query="SELECT obj FROM Role obj ORDER BY name"),
	@NamedQuery(name="Roles.loadByName", query="SELECT obj FROM Role obj WHERE obj.name = :roleName"),
	@NamedQuery(name="Roles.loadForPermission", query="SELECT obj FROM Role obj LEFT JOIN obj.permissions perm WHERE perm.id = :permissionId ORDER BY obj.name"),
	@NamedQuery(name="Roles.loadNonConflictingRolesForUser",
			query="SELECT obj FROM Role obj INNER JOIN obj.assignedUsers user " +
				"WHERE user.id = :userId AND length(obj.dynamicSeparations) = 0 " +
				"ORDER BY obj.name"),
	@NamedQuery(name="Roles.loadNonConflictingDescendants",
			query="SELECT role FROM Role role INNER JOIN role.descendants role_desc " + 
				"WHERE role_desc.dynamicSeparations IS EMPTY AND " +
					"role.id = :roleId")
})
@NamedNativeQueries({
	@NamedNativeQuery(name="Roles.removeAllPermissions", query="DELETE FROM AssignedPermissions WHERE role_id = ?1", resultClass=Role.class),
	@NamedNativeQuery(name="Roles.removeAllUsers", query="DELETE FROM AssignedUsers WHERE role_id = ?1", resultClass=Role.class)
})
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
	private Set<User> assignedUsers;
	
	/**
	 * These are all direct descendants of this role.
	 */
	@ManyToMany
	@JoinTable(name="RolesInheritance",
			joinColumns={@JoinColumn(name="role_parent", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_child", referencedColumnName="id")})
	private Set<Role> descendants;

	/**
	 * These are all direct ascendants of this role.
	 */
	@ManyToMany
	@JoinTable(name="RolesInheritance",
			joinColumns={@JoinColumn(name="role_child", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_parent", referencedColumnName="id")})
	private Set<Role> ascendants;
	
	/**
	 * Here we can access all DSD in which this role appears.
	 */
	@ManyToMany(mappedBy="roles")
	private Set<DynamicSeparationDuty> dynamicSeparations;
	
	@ManyToMany
	@JoinTable(name="AssignedPermissions", 
			joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="permission_id", referencedColumnName="id")})
	private Set<Permission> permissions;

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
	
	public Set<User> getAssignedUsers() {
		return assignedUsers;
	}

	public void setAssignedUsers(Set<User> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}	
	
	public Set<DynamicSeparationDuty> getDynamicSeparations() {
		return dynamicSeparations;
	}

	public void setDynamicSeparations(Set<DynamicSeparationDuty> dynamicSeparations) {
		this.dynamicSeparations = dynamicSeparations;
	}
	
	public Set<Role> getDescendants() {
		return descendants;
	}

	public void setDescendants(Set<Role> descendants) {
		this.descendants = descendants;
	}

	public Set<Role> getAscendants() {
		return ascendants;
	}

	public void setAscendants(Set<Role> ascendants) {
		this.ascendants = ascendants;
	}
	
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}	
	
	/**
	 * Method used to transform the current entity to a transferable
	 * object.
	 * 
	 * @return
	 */
	public org.ecommerce.rbac.dto.Role toRoleDTO() {
		org.ecommerce.rbac.dto.Role role = new org.ecommerce.rbac.dto.Role();
		
		role.setId(this.getId());
		role.setName(this.getName());
		
		return role;
	}
	
	/**
	 * Method used to transform the specified dto to an entity.
	 * 
	 * @param role Role dto instance.
	 * @return
	 */
	public static Role valueOf(org.ecommerce.rbac.dto.Role role) {
		Role ret = new Role();
		
		ret.setId(role.getId());
		ret.setName(role.getName());
		
		return ret;
	}
	
	/**
	 * A role is equal with another role only by equality of name
	 * or primary key.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Role) {
			Role role = Role.class.cast(obj);
			
			return role.getId() == this.getId() ||
				this.getName().equalsIgnoreCase(role.getName());
		}
		
		return super.equals(obj);
	}
}