CREATE SCHEMA `ecommerce_rbac` DEFAULT CHARACTER SET utf8 ;

-- Create schema structure
CREATE TABLE Users (id int(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));
CREATE TABLE Roles (id int(10) NOT NULL AUTO_INCREMENT, name varchar(100), PRIMARY KEY (id));
CREATE TABLE Objects (id int(10) NOT NULL AUTO_INCREMENT, name varchar(100) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Operations (id int(10) NOT NULL AUTO_INCREMENT, name varchar(100) NOT NULL, PRIMARY KEY (id));
CREATE TABLE AssignedUsers (user_id int(10) NOT NULL, role_id int(10) NOT NULL, PRIMARY KEY (user_id, role_id));
CREATE TABLE Sessions (id int(10) NOT NULL AUTO_INCREMENT, active tinyint NOT NULL, start_date timestamp NOT NULL, end_date timestamp, PRIMARY KEY (id));
CREATE TABLE UserSessions (user_id int(10) NOT NULL, session_id int(10) NOT NULL, PRIMARY KEY (user_id, session_id));
CREATE TABLE SessionRoles (session_id int(10) NOT NULL, role_id int(10) NOT NULL, PRIMARY KEY (session_id, role_id));
CREATE TABLE Permissions (operation_id int(10) NOT NULL, object_id int(10) NOT NULL, id int(10) NOT NULL AUTO_INCREMENT, name varchar(100) NOT NULL, PRIMARY KEY (id), UNIQUE INDEX (operation_id), UNIQUE INDEX (object_id));
CREATE TABLE AssignedPermissions (permission_id int(10) NOT NULL, role_id int(10) NOT NULL, PRIMARY KEY (permission_id, role_id));
CREATE TABLE RolesInheritance (roles_child int(10) NOT NULL, roles_parent int(10) NOT NULL, PRIMARY KEY (roles_child, roles_parent));
CREATE TABLE DynamicSeparationDutySet (id int(10) NOT NULL AUTO_INCREMENT, name varchar(100) NOT NULL, cardinality int(10) NOT NULL, PRIMARY KEY (id));
CREATE TABLE DynamicSeparationDutySetRoles (dsd_id int(10) NOT NULL, role_id int(10) NOT NULL, PRIMARY KEY (dsd_id, role_id));

-- Add constraints
ALTER TABLE AssignedUsers ADD INDEX fk_assignedusers_user (user_id), ADD CONSTRAINT fk_assignedusers_user FOREIGN KEY (user_id) REFERENCES Users (id);
ALTER TABLE AssignedUsers ADD INDEX fk_assignedusers_role (role_id), ADD CONSTRAINT fk_assignedusers_role FOREIGN KEY (role_id) REFERENCES Roles (id);
ALTER TABLE UserSessions ADD INDEX fk_usersessions_user (user_id), ADD CONSTRAINT fk_usersessions_user FOREIGN KEY (user_id) REFERENCES Users (id);
ALTER TABLE UserSessions ADD INDEX fk_usersessions_session (session_id), ADD CONSTRAINT fk_usersessions_session FOREIGN KEY (session_id) REFERENCES Sessions (id);
ALTER TABLE SessionRoles ADD INDEX fk_sessionroles_session (session_id), ADD CONSTRAINT fk_sessionroles_session FOREIGN KEY (session_id) REFERENCES Sessions (id);
ALTER TABLE SessionRoles ADD INDEX fk_sessionroles_role (role_id), ADD CONSTRAINT fk_sessionroles_role FOREIGN KEY (role_id) REFERENCES Roles (id);
ALTER TABLE Permissions ADD INDEX fk_permission_operation (operation_id), ADD CONSTRAINT fk_permission_operation FOREIGN KEY (operation_id) REFERENCES Operations (id);
ALTER TABLE Permissions ADD INDEX fk_permissions_object (object_id), ADD CONSTRAINT fk_permissions_object FOREIGN KEY (object_id) REFERENCES Objects (id);
ALTER TABLE AssignedPermissions ADD INDEX fk_assignedpermissions_permission (permission_id), ADD CONSTRAINT fk_assignedpermissions_permission FOREIGN KEY (permission_id) REFERENCES Permissions (id);
ALTER TABLE AssignedPermissions ADD INDEX fk_assignedpermissions_role (role_id), ADD CONSTRAINT fk_assignedpermissions_role FOREIGN KEY (role_id) REFERENCES Roles (id);
ALTER TABLE RolesInheritance ADD INDEX fk_rolesinheritance_child (roles_child), ADD CONSTRAINT fk_rolesinheritance_child FOREIGN KEY (roles_child) REFERENCES Roles (id);
ALTER TABLE RolesInheritance ADD INDEX fk_rolesinheritance_parent (roles_parent), ADD CONSTRAINT fk_rolesinheritance_parent FOREIGN KEY (roles_parent) REFERENCES Roles (id);
ALTER TABLE DynamicSeparationDutySetRoles ADD INDEX fk_dsdroles_dsd (dsd_id), ADD CONSTRAINT fk_dsdroles_dsd FOREIGN KEY (dsd_id) REFERENCES DynamicSeparationDutySet (id);
ALTER TABLE DynamicSeparationDutySetRoles ADD INDEX fk_dsdroles_role (role_id), ADD CONSTRAINT fk_dsdroles_role FOREIGN KEY (role_id) REFERENCES Roles (id);
