truncate table acciones RESTART IDENTITY CASCADE;

insert into acciones values ('NUL',default,'NullAccion','Accion Nula');
insert into acciones values ('LOG',default,'Login','Se intenta logear a la aplicacion');
insert into acciones values ('VIW',default,'Ver','Se logra ingresar y ver el menu');
insert into acciones values ('CRE',default,'Crear','Crea una nueva instancia del elemento');
insert into acciones values ('MOD',default,'Modificar','Modifica atributos del elemento');
insert into acciones values ('DES',default,'Deshabilitar','Inhabilita el uso del elemento. Eliminacion logica');
insert into acciones values ('CSV',default,'Cargar CVS','Carga data de forma masiva con archivos CSV');
insert into acciones values ('ROL',default,'Asignar Rol a usuario','Le otorga los permisos especificados por el rol al usuario');
insert into acciones values ('EST',default,'Modificar de estado a elemento','Pasa al elemento a su siguiente estado del proceso');
insert into acciones values ('MOV',default,'Despacho','Completa una orden de despacho');
insert into acciones values ('MIV',default,'Entrada generica','Entdada de productos generica');
insert into acciones values ('MIC',default,'Entrada por Compra','Completa una orden de entrada por Compra a Proveedor');
insert into acciones values ('MID',default,'Entrada por devolucion','Completa una orden de entrada por devolucion');
insert into acciones values ('MIE',default,'Entrada por encontrar','Completa una orden de entrada por Encuentro');
insert into acciones values ('SIM',default,'Uso de la herramienta de simulacion','Se usa la herramienta para simular el despacho');
insert into acciones values ('PRO',default,'Modificar Promocion','Maneja el estado de una promocion frente un tipo o categoria');
insert into acciones values ('FLT',default,'Modificar Flete','Maneja el estado de un flete frente a un tipo o categoria');
insert into acciones values ('TRS',default,'Transferencia de productos','Modifica la posicion de un producto');

select * from acciones;


