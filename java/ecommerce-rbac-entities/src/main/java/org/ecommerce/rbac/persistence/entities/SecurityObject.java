package org.ecommerce.rbac.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * Class that defines the data model for a RBAC object.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 02.10.2011
 */

@Entity
@Table(name="Objects")
@NamedQueries({
	@NamedQuery(name="SecurityObject.loadAll", 
			query="SELECT obj FROM SecurityObject obj ORDER BY obj.name"),
	@NamedQuery(name="SecurityObject.loadAllPaginated",
			query="SELECT obj FROM SecurityObject obj WHERE obj.name LIKE CONCAT('%', :searchQuery, '%') ORDER BY obj.name")
})
public class SecurityObject {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;

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
	
	/**
	 * Method used to transform this entity to a transferable object. 
	 * 
	 * @return
	 */
	public org.ecommerce.rbac.dto.SecurityObject toSecurityObjectDTO() {
		org.ecommerce.rbac.dto.SecurityObject ret = new org.ecommerce.rbac.dto.SecurityObject();
		
		ret.setId(this.getId());
		ret.setName(this.getName());
		
		return ret;
	}
	
	/**
	 * Method used to transform a transferable object into an entity.
	 * 
	 * @param object SecurityObject dto instance.
	 * @return
	 */
	public static SecurityObject valueOf(org.ecommerce.rbac.dto.SecurityObject object) {
		SecurityObject ret = new SecurityObject();
		
		ret.setId(object.getId());
		ret.setName(object.getName());
		
		return ret;
	}
}