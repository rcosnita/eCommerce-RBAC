# ECommerce RBAC

This project intend to implement a reusable security system for ecommerce applications that is compliant with RBAC standard.
RBAC standard current version is widely use by many companies but the goal of this project is to provide an implementation
that can be easily used by ecommerce application. Because many ecommerce applications are web oriented this implementation
will use technologies easily to use in web environments.

# What RBAC features will be supported

RBAC standard current version describe four major components.

* Core components (as defined by standard)
* Roles hierarchy (as defined by standard)
* Services that allows integration over http / https
* API that allows native integration with java applications
* Common security templates that can be used for different size ecommerce applications
* Persistence layer for RBAC security features
* Database scripts for different RDBMS systems (MySQL and Postgres are supported) 

## Optional features that will be supported

* Static separation of duty relations (SSD as defined by standard)
* Dynamic separation of dutiy relations (DSOD as defined by standard)

# Technology used

* Java 1.6 SE
* Spring 3.0.5
* Apache CXF JAXRS
* Apache CXF WS
* JPA 2.0
* MySql 5 / Postgres 8
