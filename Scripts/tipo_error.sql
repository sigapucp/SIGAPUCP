
insert into tiposerror values ('UNK500',default,'Error desconocido');
insert into tiposerror values ('VAL400',default,'Error de validacion tipo de dato ingresados');
insert into tiposerror values ('VAL401',default,'Error de validacion valor de dato ingresados');
insert into tiposerror values ('VAL402',default,'Error de repeticion de Codigo');
insert into tiposerror values ('VAL403',default,'Error de autentificacion');
insert into tiposerror values ('SEG490',default,'Error de seguridad - Generico');
insert into tiposerror values ('SEG491',default,'Error de seguridad - Privilegios Insuficientes');
insert into tiposerror values ('SEG492',default,'Error de seguridad - Operation Time out');
insert into tiposerror values ('EXP501',default,'Excepcion dentro de aplicacion - Generico');
insert into tiposerror values ('EXP502',default,'Excepcion dentro de aplicacion - Null Pointer');
insert into tiposerror values ('EXP503',default,'Excepcion dentro de aplicacion - DataBase Exception');
insert into tiposerror values ('DBM501',default,'Error en Conexion');
insert into tiposerror values ('DBM502',default,'Error en Transaccion - Rollback realizado');

select * from tiposerror;
