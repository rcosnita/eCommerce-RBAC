package org.ecommerce.rbac.api.management;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.ecommerce.rbac.dto.SecurityObject;
import org.ecommerce.rbac.dto.SecurityObjects;

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
 * This is the rbac objects manager. Here you can find all required methods
 * that make this implementation fully compliant with RBAC 2.0 standard (from objects
 * perspective). Based on this interface you can generate a rest client really easy.
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.10.2011
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki/Rest-api
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RbacObjectsManager {
	/**
	 * Method used to obtain all objects defined in RBAC system.
	 * 
	 * @return
	 */
	@Path("/")
	@GET
	public SecurityObjects loadAllObjects();
	
	/**
	 * Method used to obtain all objects defined in the RBAC system.
	 * 
	 * @param startRecord the record from which we want to start retrieving objects.
	 * @param pageSize the number of objects we want to retrieve.
	 * @return
	 */
	@Path("/{startRecord}/{pageSize}/")
	@GET
	public SecurityObjects loadAllObjects(
		@PathParam("startRecord") int startRecord,
		@PathParam("pageSize") int pageSize,
		@QueryParam("searchQuery") String searchCriteria);
	
	/**
	 * Method used to load a specified object from RBAC.
	 * 
	 * @param objectId Object unique identifier.
	 * @return
	 */
	@Path("/{objectId}")
	@GET
	public SecurityObject loadObjectById(@PathParam("objectId") Integer objectId);
	
	/**
	 * Method used to create a new RBAC object.
	 * 
	 * @param object An instance of SecurityObject.
	 * @return The newly created object identifier.
	 */
	@Path("/")
	@POST
	public int createNewObject(SecurityObject object);
	
	/**
	 * Method used to update an existing RBAC object.
	 * 
	 * @param objectId
	 * @param object
	 */
	@Path("/{objectId}")
	@PUT
	public void updateExistingObject(
			@PathParam("objectId") Integer objectId,
			SecurityObject object);	
	
	/**
	 * Method used to remove an object from the RBAC system.
	 *  
	 * @param objectId Object unique identifier.
	 */
	@Path("/{objectId}")
	@DELETE
	public void removeObject(@PathParam("objectId") Integer objectId);
}