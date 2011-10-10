package org.ecommerce.rbac.persistence.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.ecommerce.rbac.dto.DynamicSeparationRule;

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
 * Class used to model dynamic separation of duty data model.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="DynamicSeparationDutySet")
@NamedQueries({
	@NamedQuery(name="DSD.loadAll", 
			query="SELECT obj FROM DynamicSeparationDuty obj ORDER BY obj.name")
})
public class DynamicSeparationDuty {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="cardinality")
	private int cardinality;

	/**
	 * These are the roles included into this dynamic separation of duty
	 * rule.
	 */
	@ManyToMany
	@JoinTable(name="DynamicSeparationDutySetRoles",
			joinColumns={@JoinColumn(name="dsd_id", referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
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

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * Method used to transform this entity to a transferable object.
	 * 
	 * @return
	 */
	public DynamicSeparationRule toDynamicSeparationDTO() {
		DynamicSeparationRule rule = new DynamicSeparationRule();
		
		rule.setId(this.getId());
		rule.setName(this.getName());
		rule.setCardinality(this.getCardinality());
		
		return rule;
	}
	
	/**
	 * Method used to obtain an entity from a DTO object.
	 * 
	 * @param rule
	 * @return
	 */
	public static DynamicSeparationDuty valueOf(DynamicSeparationRule rule) {
		DynamicSeparationDuty ret = new DynamicSeparationDuty();
		
		ret.setId(rule.getId());
		ret.setName(rule.getName());
		ret.setCardinality(rule.getCardinality());
		
		return ret;
	}
}