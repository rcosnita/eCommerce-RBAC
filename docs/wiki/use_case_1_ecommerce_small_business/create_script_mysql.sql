-- make sure security structure is not initialized
DELETE FROM DynamicSeparationDutySetRoles;
DELETE FROM DynamicSeparationDutySet;
DELETE FROM Sessions;
DELETE FROM AssignedUsers;
DELETE FROM Users;
DELETE FROM RolesInheritance;
DELETE FROM Roles;
DELETE FROM Permissions;
DELETE FROM Objects;
DELETE FROM Operations;

-- create operations
INSERT INTO Operations(name)
VALUES('view');

INSERT INTO Operations(name)
VALUES('create');

INSERT INTO Operations(name)
VALUES('edit');

INSERT INTO Operations(name)
VALUES('delete');



-- create objects
INSERT INTO Objects(name)
VALUES('pages');

INSERT INTO Objects(name)
VALUES('products');

INSERT INTO Objects(name)
VALUES('reports');

INSERT INTO Objects(name)
VALUES('accounts');

INSERT INTO Objects(name)
VALUES('discounts');

INSERT INTO Objects(name)
VALUES('account');



-- create permissions
INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'view pages', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'view') oper, 
     (SELECT id FROM Objects WHERE name = 'pages') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'view accounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'view') oper, 
     (SELECT id FROM Objects WHERE name = 'accounts') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'view account', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'view') oper, 
     (SELECT id FROM Objects WHERE name = 'account') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'view reports', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'view') oper, 
     (SELECT id FROM Objects WHERE name = 'reports') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'view products', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'view') oper, 
     (SELECT id FROM Objects WHERE name = 'products') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'view discounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'view') oper, 
     (SELECT id FROM Objects WHERE name = 'discounts') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'edit pages', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'edit') oper, 
     (SELECT id FROM Objects WHERE name = 'pages') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'edit accounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'edit') oper, 
     (SELECT id FROM Objects WHERE name = 'accounts') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'edit account', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'edit') oper, 
     (SELECT id FROM Objects WHERE name = 'account') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'edit reports', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'edit') oper, 
     (SELECT id FROM Objects WHERE name = 'reports') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'edit products', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'edit') oper, 
     (SELECT id FROM Objects WHERE name = 'products') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'edit discounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'edit') oper, 
     (SELECT id FROM Objects WHERE name = 'discounts') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'delete pages', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'delete') oper, 
     (SELECT id FROM Objects WHERE name = 'pages') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'delete accounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'delete') oper, 
     (SELECT id FROM Objects WHERE name = 'accounts') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'delete account', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'delete') oper, 
     (SELECT id FROM Objects WHERE name = 'account') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'delete reports', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'delete') oper, 
     (SELECT id FROM Objects WHERE name = 'reports') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'delete products', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'delete') oper, 
     (SELECT id FROM Objects WHERE name = 'products') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'delete discounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'delete') oper, 
     (SELECT id FROM Objects WHERE name = 'discounts') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'create pages', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'create') oper, 
     (SELECT id FROM Objects WHERE name = 'pages') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'create accounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'create') oper, 
     (SELECT id FROM Objects WHERE name = 'accounts') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'create account', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'create') oper, 
     (SELECT id FROM Objects WHERE name = 'account') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'create reports', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'create') oper, 
     (SELECT id FROM Objects WHERE name = 'reports') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'create products', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'create') oper, 
     (SELECT id FROM Objects WHERE name = 'products') obj;

INSERT INTO Permissions(name, operation_id, object_id)
SELECT 'create discounts', oper.id, obj.id
FROM (SELECT id FROM Operations WHERE name = 'create') oper, 
     (SELECT id FROM Objects WHERE name = 'discounts') obj;



-- create roles

INSERT INTO Roles(name)
VALUES('Manager');

INSERT INTO Roles(name)
VALUES('Inactive customers');

INSERT INTO Roles(name)
VALUES('Customer support');

INSERT INTO Roles(name)
VALUES('Marketing');

INSERT INTO Roles(name)
VALUES('Products admin');

INSERT INTO Roles(name)
VALUES('Customers');

INSERT INTO Roles(name)
VALUES('Reporting');

INSERT INTO Roles(name)
VALUES('Portal admin');

INSERT INTO Roles(name)
VALUES('Visitors');


-- create roles inheritance
INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') parent,
     (SELECT id FROM Roles WHERE name = 'Inactive customers') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') parent,
     (SELECT id FROM Roles WHERE name = 'Customer support') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') parent,
     (SELECT id FROM Roles WHERE name = 'Marketing') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') parent,
     (SELECT id FROM Roles WHERE name = 'Products admin') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') parent,
     (SELECT id FROM Roles WHERE name = 'Customers') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') parent,
     (SELECT id FROM Roles WHERE name = 'Reporting') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') parent,
     (SELECT id FROM Roles WHERE name = 'Portal admin') child;


INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Inactive customers') parent,
     (SELECT id FROM Roles WHERE name = 'Manager') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Customer support') parent,
     (SELECT id FROM Roles WHERE name = 'Manager') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Marketing') parent,
     (SELECT id FROM Roles WHERE name = 'Manager') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Products admin') parent,
     (SELECT id FROM Roles WHERE name = 'Manager') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Customers') parent,
     (SELECT id FROM Roles WHERE name = 'Manager') child;

INSERT INTO RolesInheritance(role_parent, role_child)
SELECT parent.id, child.id
FROM (SELECT id FROM Roles WHERE name = 'Reporting') parent,
     (SELECT id FROM Roles WHERE name = 'Manager') child;



-- Permissions assignment 
INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') role,
     (SELECT id FROM Permissions WHERE name = 'view products') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') role,
     (SELECT id FROM Permissions WHERE name = 'view discounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Visitors') role,
     (SELECT id FROM Permissions WHERE name = 'create account') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Inactive customers') role,
     (SELECT id FROM Permissions WHERE name = 'view products') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Inactive customers') role,
     (SELECT id FROM Permissions WHERE name = 'view discounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Customer support') role,
     (SELECT id FROM Permissions WHERE name = 'view accounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Customer support') role,
     (SELECT id FROM Permissions WHERE name = 'view pages') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Marketing') role,
     (SELECT id FROM Permissions WHERE name = 'view reports') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Products admin') role,
     (SELECT id FROM Permissions WHERE name = 'edit products') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Products admin') role,
     (SELECT id FROM Permissions WHERE name = 'delete products') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Products admin') role,
     (SELECT id FROM Permissions WHERE name = 'create products') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Products admin') role,
     (SELECT id FROM Permissions WHERE name = 'edit discounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Products admin') role,
     (SELECT id FROM Permissions WHERE name = 'delete discounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Products admin') role,
     (SELECT id FROM Permissions WHERE name = 'create discounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Customers') role,
     (SELECT id FROM Permissions WHERE name = 'view account') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Customers') role,
     (SELECT id FROM Permissions WHERE name = 'edit account') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Customers') role,
     (SELECT id FROM Permissions WHERE name = 'delete account') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Reporting') role,
     (SELECT id FROM Permissions WHERE name = 'view reports') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Reporting') role,
     (SELECT id FROM Permissions WHERE name = 'create reports') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Reporting') role,
     (SELECT id FROM Permissions WHERE name = 'delete reports') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Reporting') role,
     (SELECT id FROM Permissions WHERE name = 'edit reports') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Portal admin') role,
     (SELECT id FROM Permissions WHERE name = 'view pages') perm;


INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Portal admin') role,
     (SELECT id FROM Permissions WHERE name = 'edit pages') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Portal admin') role,
     (SELECT id FROM Permissions WHERE name = 'create pages') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Portal admin') role,
     (SELECT id FROM Permissions WHERE name = 'delete pages') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Portal admin') role,
     (SELECT id FROM Permissions WHERE name = 'view accounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Portal admin') role,
     (SELECT id FROM Permissions WHERE name = 'edit accounts') perm;

INSERT INTO AssignedPermissions(role_id, permission_id)
SELECT role.id, perm.id
FROM (SELECT id FROM Roles WHERE name = 'Portal admin') role,
     (SELECT id FROM Permissions WHERE name = 'delete accounts') perm;