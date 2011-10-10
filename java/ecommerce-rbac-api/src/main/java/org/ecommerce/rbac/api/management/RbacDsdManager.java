package org.ecommerce.rbac.api.management;

import java.util.List;

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

import org.ecommerce.rbac.dto.DynamicSeparationRule;
import org.ecommerce.rbac.dto.DynamicSeparationRules;
import org.ecommerce.rbac.dto.Identifiers;
import org.ecommerce.rbac.dto.Roles;

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
 * This is the rbac Dynamic Separation of Duty manager. Here you can find all required methods
 * that make this implementation fully compliant with RBAC 2.0 standard (from dynamic
 * separation of duty perspective).
 * 
 * @author Radu Viorel Cosnita
 * @version 1.0
 * @since 01.10.2011
 * @see https://github.com/rcosnita/eCommerce-RBAC/wiki
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RbacDsdManager {
	/**
	 * Method used to load all defined dynamic separation of duty rules.
	 * 
	 * @return
	 */
	@Path("/")
	@GET
	public DynamicSeparationRules loadAllDsd();
	
	/**
	 * Method used to load a dynamic separation of duty rule by id.
	 * 
	 * @param dsdId DSD unique identifier.
	 * @return
	 */
	@Path("/{dsdId}")
	@GET
	public DynamicSeparationRule loadDsdById(@PathParam("dsdId") Integer dsdId);
	
	/**
	 * Method used to load all roles belonging to a dsd.
	 * 
	 * @param dsdId
	 * @return
	 */
	@Path("/{dsdId}/roles")
	@GET
	public Roles loadDsdRoles(@PathParam("dsdId") Integer dsdId);
	
	/**
	 * Method used to create a new dsd rule.
	 * 
	 * @param dsdName DSD name.
	 * @param cardinality DSD allowed cardinality. >= 2.
	 * @param roles A list of roles identifiers we want to assign to dsd.
	 */
	@Path("/{dsdName}/{cardinality}")
	@POST
	public void createNewDsd(
			@PathParam("dsdName") String dsdName,
			@PathParam("cardinality") Integer cardinality,
			Identifiers roles
	);
	
	/**
	 * Method used to add new roles into the current DSD.
	 * 
	 * @param dsdId DSD unique identifier.
	 * @param A list of roles identifier.
	 */
	@Path("/{dsdId}/roles")
	@POST
	public void includeRolesInDsd(
			@PathParam("dsdId") Integer dsdId,
			List<Integer> roles);
	
	/**
	 * Method used to update an existing DSD.
	 * 
	 * @param dsdId DSD unique identifier.
	 * @param dsd DSD instance.
	 */
	@Path("/{dsdId}")
	@PUT
	public void updateExistingDsd(
			@PathParam("dsdId") Integer dsdId,
			DynamicSeparationRule dsd);
	
	/**
	 * Method used to exclude a list of roles from the specified DSD.
	 * 
	 * @param dsdId DSD unique identifier.
	 * @param roles A list of roles id we want to exclude.
	 */
	@Path("/{dsdId}/roles")
	@DELETE
	public void excludeRolesFromDsd(
			@PathParam("dsdId") Integer dsdId,
			@QueryParam("roleId") List<Integer> roles
	);
	
	/**
	 * Method used to delete an existing DSD.
	 * 
	 * @param dsdId DSD unique identifier.
	 */
	@Path("/{dsdId}")
	@DELETE
	public void removeDsd(@PathParam("dsdId") Integer dsdId);
}