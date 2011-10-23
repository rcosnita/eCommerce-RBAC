package org.ecommerce.rbac.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

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
 * This is the permission entity as defined by RBAC standard.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.11.2011
 */

@XmlRootElement(name="permission")
public class Permission implements Serializable {
	private Integer id;
	private String name;
	private SecurityObject object;
	private Operation operation;
	
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
	public SecurityObject getObject() {
		return object;
	}
	public void setObject(SecurityObject object) {
		this.object = object;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
		
	@Override
	public String toString() {
		return String.format("RBAC permission %s:%s.", this.getId(), this.getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Integer) {
			return this.getId() == Integer.class.cast(obj);
		}
		else if(obj instanceof String) {
			return this.getName().equalsIgnoreCase(obj.toString());
		}
		else if(obj instanceof Permission) {
			Permission perm = Permission.class.cast(obj);
			
			return perm.getId() == this.getId() ||
				this.getName().equalsIgnoreCase(perm.getName());
		}
		
		return super.equals(obj);
	}
}