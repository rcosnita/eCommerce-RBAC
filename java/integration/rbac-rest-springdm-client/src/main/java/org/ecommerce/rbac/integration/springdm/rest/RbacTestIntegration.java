package org.ecommerce.rbac.integration.springdm.rest;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RbacTestIntegration {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = 
			new ClassPathXmlApplicationContext("/META-INF/spring/rbac-client-context.xml");
		
		JAXRSClientFactoryBean bean = null;
	}
}
