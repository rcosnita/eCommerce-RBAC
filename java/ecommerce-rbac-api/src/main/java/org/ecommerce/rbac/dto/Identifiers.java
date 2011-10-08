package org.ecommerce.rbac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="identifiers-array")
public class Identifiers implements Serializable {
	private List<Integer> identifiers = new ArrayList<Integer>();
	
	@XmlElement(name="id")
	public List<Integer> getIdentifiers() {
		return this.identifiers;
	}
}
