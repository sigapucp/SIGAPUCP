alter table ordenesentrada alter column provuder_ruc drop not null;
alter table ordenesentrada alter column proveedor_id drop not null;
alter table ordenesentrada alter column client_id drop not null;


alter table ordenescompra alter column cotizacion_id drop not null;

alter table fletes alter column tipo_id drop not null;
alter table fletes alter column categoria_id drop not null;
alter table fletes alter column moneda_id drop not null;

alter table promociones alter column tipo_id drop not null;
alter table promociones alter column categoria_id drop not null;
alter table promocionbonificaciones alter column categoria_id drop not null;
alter table promocionbonificaciones alter column tipo_id drop not null;
alter table promocionporcentajes alter column valor_condition drop not null;
alter table promocionporcentajes alter column concepto_condition drop not null;
alter table promocionporcentajes alter column relacion_condition drop not null;


alter table productos alter column almacen_xy_cod drop not null;
alter table productos alter column almacen_xy_id drop not null;

alter table productos alter column almacen_z_id drop not null;
alter table productos alter column almacen_z_cod drop not null;

alter table productos alter column rack_cod drop not null;
alter table productos alter column rack_id drop not null;

alter table productos alter column lote_id drop not null;
alter table productos alter column lote_cod drop not null;

alter table productos alter column almacen_cod drop not null;
alter table productos alter column almacen_id drop not null;

