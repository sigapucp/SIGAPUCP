truncate table accionesxmenus RESTART IDENTITY;

insert into accionesxmenus values (17,'LOG',2,'Usuario intenta loguearse a la aplicacion');

insert into accionesxmenus values (5,'CRE',3,'Se crea un nuevo almacen');
insert into accionesxmenus values (6,'CRE',3,'Se crea una nueva categoria de producto');
insert into accionesxmenus values (7,'CRE',3,'Se crea un nuevo tipo de producto');
insert into accionesxmenus values (8,'CRE',3,'Se crea un nuevo Rack en un almacen');
insert into accionesxmenus values (9,'CRE',3,'Se crea una nueva Orden de Entrada');
insert into accionesxmenus values (10,'CRE',3,'Se crea una nueva Orden de Despacho');
insert into accionesxmenus values (11,'CRE',3,'Se crea un nuevo Cliente');
insert into accionesxmenus values (12,'CRE',3,'Se crea una nueva Proforma');
insert into accionesxmenus values (13,'CRE',3,'Se crea un nuevo Pedido');
insert into accionesxmenus values (14,'CRE',3,'Se crea un nuevo Proveedor');
insert into accionesxmenus values (15,'CRE',3,'Se crea una nueva Promocion');
insert into accionesxmenus values (16,'CRE',3,'Se crea un nuevo Flete');
insert into accionesxmenus values (17,'CRE',3,'Se crea un nuevo Documento de Venta');
insert into accionesxmenus values (18,'CRE',3,'Se crea una nueva Nota de Credito');
insert into accionesxmenus values (19,'CRE',3,'Se crea un nuevo Usuario');
insert into accionesxmenus values (20,'CRE',3,'Se crea un nuevo Rol');

insert into accionesxmenus values (5,'MOD',4,'Se modifica un almacen');
insert into accionesxmenus values (6,'MOD',4,'Se modifica una categoria de producto');
insert into accionesxmenus values (7,'MOD',4,'Se modifica un tipo de producto');
insert into accionesxmenus values (8,'MOD',4,'Se modifica un Rack en un almacen');
insert into accionesxmenus values (9,'MOD',4,'Se modifica una Orden de Entrada');
insert into accionesxmenus values (10,'MOD',4,'Se modifica una Orden de Despacho');
insert into accionesxmenus values (11,'MOD',4,'Se modifica un Cliente');
insert into accionesxmenus values (12,'MOD',4,'Se modifica una Proforma');
insert into accionesxmenus values (13,'MOD',4,'Se modifica un Pedido');
insert into accionesxmenus values (14,'MOD',4,'Se modifica un Proveedor');
insert into accionesxmenus values (15,'MOD',4,'Se modifica una Promocion');
insert into accionesxmenus values (16,'MOD',4,'Se modifica un Flete');
insert into accionesxmenus values (17,'MOD',4,'Se modifica un Documento de Venta');
insert into accionesxmenus values (18,'MOD',4,'Se modifica una Nota de Credito');
insert into accionesxmenus values (19,'MOD',4,'Se modifica un Usuario');
insert into accionesxmenus values (20,'MOD',4,'Se modifica un Rol');

insert into accionesxmenus values (5,'DES',5,'Se deshabilita un almacen');
insert into accionesxmenus values (6,'DES',5,'Se deshabilita una categoria de producto');
insert into accionesxmenus values (7,'DES',5,'Se deshabilita un tipo de producto');
insert into accionesxmenus values (8,'DES',5,'Se deshabilita un Rack en un almacen');
insert into accionesxmenus values (9,'DES',5,'Se deshabilita una Orden de Entrada');
insert into accionesxmenus values (10,'DES',5,'Se deshabilita una Orden de Despacho');
insert into accionesxmenus values (11,'DES',5,'Se deshabilita un Cliente');
insert into accionesxmenus values (12,'DES',5,'Se deshabilita una Proforma');
insert into accionesxmenus values (13,'DES',5,'Se deshabilita un Pedido');
insert into accionesxmenus values (14,'DES',5,'Se deshabilita un Proveedor');
insert into accionesxmenus values (15,'DES',5,'Se deshabilita una Promocion');
insert into accionesxmenus values (16,'DES',5,'Se deshabilita un Flete');
insert into accionesxmenus values (17,'DES',5,'Se deshabilita un Documento de Venta');
insert into accionesxmenus values (18,'DES',5,'Se deshabilita una Nota de Credito');
insert into accionesxmenus values (19,'DES',5,'Se deshabilita un Usuario');
insert into accionesxmenus values (20,'DES',5,'Se deshabilita un Rol');


insert into accionesxmenus values (5,'CSV',6,'Se con archivo con formato especifico un Almacen');
insert into accionesxmenus values (6,'CSV',6,'Se carga de forma masiva Categorias de producto');
insert into accionesxmenus values (7,'CSV',6,'Se carga de forma masiva tipos de producto');
insert into accionesxmenus values (8,'CSV',6,'Se con archivo con formato especifico un Rack en almacen');
insert into accionesxmenus values (11,'CSV',6,'Se carga de forma masiva Clientes');
insert into accionesxmenus values (14,'CSV',6,'Se carga de forma masiva Proveedores');
insert into accionesxmenus values (15,'CSV',6,'Se carga de forma masiva Promociones');
insert into accionesxmenus values (16,'CSV',6,'Se carga de forma masiva Fletes');
insert into accionesxmenus values (19,'CSV',6,'Se carga de forma masiva Usuario');

insert into accionesxmenus values (20,'ROL',7,'Le otorga los permisos especificados por el rol al usuario');


-- Pending
insert into accionesxmenus values (9,'EST',8,'Se cambia al siguiente estado del proceso a una Orden de Entrada');
insert into accionesxmenus values (10,'EST',8,'Se cambia al siguiente estado del proceso a una Orden de Despacho');
insert into accionesxmenus values (12,'EST',8,'Se cambia al siguiente estado del proceso a una Proforma');
insert into accionesxmenus values (13,'EST',8,'Se cambia al siguiente estado del proceso a un Pedido');
insert into accionesxmenus values (17,'EST',8,'Se cambia al siguiente estado del proceso a un Documento de Venta');
insert into accionesxmenus values (18,'EST',8,'Se cambia al siguiente estado del proceso a una Nota de Credito');

insert into accionesxmenus values (10,'MOV',9,'Completa una orden de despacho');

insert into accionesxmenus values (9,'MIV',10,'Completa una orden de entrada por razones varias');
insert into accionesxmenus values (9,'MIC',11,'Completa una orden de entrada por Compra a Proveedor');
insert into accionesxmenus values (9,'MID',12,'Completa una orden de entrada por devolucion');
insert into accionesxmenus values (9,'MIE',13,'Completa una orden de entrada por Encuentro');

insert into accionesxmenus values (22,'SIM',14,'Se usa la herramienta para simular el despacho');

insert into accionesxmenus values (15,'PRO',15,'Maneja el estado de una Promocion frente un tipo o categoria');
insert into accionesxmenus values (16,'FLT',16,'Maneja el estado de un Flete frente a un tipo o categoria');

/* insert into accionesxmenus values ('TRS',default,'Transferencia de productos','Modifica la posicion de un producto'); */

select * from accionesxmenus;