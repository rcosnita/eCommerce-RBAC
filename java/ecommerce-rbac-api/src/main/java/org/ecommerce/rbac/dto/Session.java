package org.ecommerce.rbac.dto;

import java.io.Serializable;
import java.util.Date;

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
 * This is the session resource from RBAC standard. In here we also support
 * some basic audit properties like: active session, start time, end time.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.11.2011
 */

@XmlRootElement(name="session")
public class Session implements Serializable {
	private Integer id;
	private boolean active;
	private Date startTime;
	private Date endTime;
	
	/**
	 * This attribute is nowhere mentioned in RBAC standard. It is used
	 * for integrating with web frameworks that supports server side
	 * session management.
	 */
	private String remoteSession;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getRemoteSession() {
		return remoteSession;
	}
	public void setRemoteSession(String remoteSession) {
		this.remoteSession = remoteSession;
	}
	@Override
	public String toString() {
		return String.format("Session %s started on %s.", this.getId(), this.getStartTime());
	}
}
