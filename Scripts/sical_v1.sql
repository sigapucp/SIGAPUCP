CREATE TABLE TiposOrdenEntrada
(
 tipo_entrada_id  SERIAL NOT NULL ,
 tipo_entdada_cod VARCHAR(30) NOT NULL ,
 nombre           VARCHAR(50) NOT NULL ,
 descripcion      VARCHAR(150) NOT NULL ,

 CONSTRAINT pk_1596 PRIMARY KEY  (tipo_entrada_id , tipo_entdada_cod )
);




--************************************** TiposError

CREATE TABLE TiposError
(
 error_cod     VARCHAR(20) NOT NULL ,
 error_type_id SERIAL NOT NULL ,
 descripcion   VARCHAR(100) NOT NULL ,

 CONSTRAINT pk_1072 PRIMARY KEY  (error_cod , error_type_id )
);




--************************************** CategoriasProducto

CREATE TABLE CategoriasProducto
(
 categoria_code      VARCHAR(20) NOT NULL ,
 categoria_id        SERIAL NOT NULL ,
 nombre              VARCHAR(50) NOT NULL ,
 descripcion         VARCHAR(100) NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 estado              VARCHAR(20) NOT NULL ,

 CONSTRAINT pk_853 PRIMARY KEY  (categoria_code , categoria_id )
);




--************************************** NotaCreditoEstados

CREATE TABLE NotaCreditoEstados
(
 nota_credito_estado_id SERIAL NOT NULL ,
 mombre                 VARCHAR(30) NOT NULL ,
 descripcion            VARCHAR(100) NOT NULL ,
 ant_estado             INT NULL ,
 sig_estado             INT NULL ,

 CONSTRAINT pk_805 PRIMARY KEY  (nota_credito_estado_id )
);




--************************************** RutasDespacho

CREATE TABLE RutasDespacho
(
 ruta_despacho_id SERIAL NOT NULL ,
 ruta_orden       TEXT NOT NULL ,

 CONSTRAINT pk_734 PRIMARY KEY  (ruta_despacho_id )
);




--************************************** DocVentaEstados

CREATE TABLE DocVentaEstados
(
 doc_venta_estado_id SERIAL NOT NULL ,
 nombre              VARCHAR(30) NOT NULL ,
 descripcion         VARCHAR(100) NOT NULL ,
 ant_estado          INT NULL ,
 sig_estado          INT NULL ,

 CONSTRAINT pk_654 PRIMARY KEY  (doc_venta_estado_id )
);




--************************************** ParametrosSistema

CREATE TABLE ParametrosSistema
(
 parametro_id INT NOT NULL ,
 nombre       VARCHAR(30) NOT NULL ,
 valor        VARCHAR(50) NOT NULL ,
 tipo         VARCHAR(50) NOT NULL ,

 CONSTRAINT pk_617 PRIMARY KEY  (parametro_id )
);




--************************************** OrdenCompraEstados

CREATE TABLE OrdenCompraEstados
(
 order_compra_estado_id SERIAL NOT NULL ,
 nombre                 VARCHAR(50) NOT NULL ,
 descripcion            VARCHAR(100) NOT NULL ,
 ant_estado             INT NULL ,
 sig_estado             INT NULL ,

 CONSTRAINT pk_599 PRIMARY KEY  (order_compra_estado_id )
);




--************************************** Monedas

CREATE TABLE Monedas
(
 moneda_id           SERIAL NOT NULL ,
 nombre              VARCHAR(30) NOT NULL ,
 abrv                VARCHAR(10) NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 estado              VARCHAR(20) NOT NULL ,

 CONSTRAINT pk_430 PRIMARY KEY  (moneda_id )
);




--************************************** Proveedores

CREATE TABLE Proveedores
(
 provuder_ruc        VARCHAR(20) NOT NULL ,
 proveedor_id        SERIAL NOT NULL ,
 name                VARCHAR(50) NOT NULL ,
 contact_name        VARCHAR(50) NOT NULL ,
 phone_number        VARCHAR(20) NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_date_chage     DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 status              VARCHAR(50) NOT NULL ,
 annotation          VARCHAR(200) NOT NULL ,

 CONSTRAINT pk_346 PRIMARY KEY  (provuder_ruc , proveedor_id )
);




--************************************** EstadosProducto

CREATE TABLE EstadosProducto
(
 estado_id   SERIAL NOT NULL ,
 nombre      VARCHAR(50) NOT NULL ,
 descripcion VARCHAR(100) NOT NULL ,
 ant_estado  INT NULL ,
 sig_estado  INT NULL ,

 CONSTRAINT pk_276 PRIMARY KEY  (estado_id )
);




--************************************** Unidades

CREATE TABLE Unidades
(
 unidad_id           SERIAL NOT NULL ,
 abreviacion         VARCHAR(10) NOT NULL ,
 descripcion         VARCHAR(100) NOT NULL ,
 nombre              VARCHAR(50) NOT NULL ,
 last_user_change    VARCHAR(50) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 es_discreto         CHAR(1) NOT NULL ,
 estado              VARCHAR(20) NOT NULL ,

 CONSTRAINT pk_217 PRIMARY KEY  (unidad_id )
);




--************************************** TiposArea

CREATE TABLE TiposArea
(
 tipo_area_id SERIAL NOT NULL ,
 nombre       VARCHAR(50) NOT NULL ,
 descripcion  VARCHAR(100) NOT NULL ,

 CONSTRAINT pk_204 PRIMARY KEY  (tipo_area_id )
);




--************************************** Almacenes

CREATE TABLE Almacenes
(
 almacen_cod         VARCHAR(50) NOT NULL ,
 almacen_id          SERIAL NOT NULL ,
 nombre              VARCHAR(50) NOT NULL ,
 es_central          CHAR(1) NOT NULL ,
 largo               INT NOT NULL ,
 ancho               INT NOT NULL ,
 longitud_area       DECIMAL(10,2) NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 x_relativo_central  INT NOT NULL ,
 y_relativo_central  INT NOT NULL ,

 CONSTRAINT pk_182 PRIMARY KEY  (almacen_cod , almacen_id )
);




--************************************** Roles

CREATE TABLE Roles
(
 rol_cod             VARCHAR(30) NOT NULL ,
 rol_id              SERIAL NOT NULL ,
 nombre              VARCHAR(50) NOT NULL ,
 descripcion         VARCHAR(100) NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 estado              VARCHAR(20) NOT NULL ,

 CONSTRAINT pk_143 PRIMARY KEY  (rol_cod , rol_id )
);




--************************************** Acciones

CREATE TABLE Acciones
(
 accion_cod  VARCHAR(30) NOT NULL ,
 accion_id   SERIAL NOT NULL ,
 nombre      VARCHAR(50) NOT NULL ,
 descripcion VARCHAR(100) NOT NULL ,

 CONSTRAINT pk_114 PRIMARY KEY  (accion_cod , accion_id )
);




--************************************** Menus

CREATE TABLE Menus
(
 menu_id SERIAL NOT NULL ,
 nombre  VARCHAR(50) NOT NULL ,

 CONSTRAINT pk_108 PRIMARY KEY  (menu_id )
);




--************************************** Clientes

CREATE TABLE Clientes
(
 client_id             SERIAL NOT NULL ,
 nombre                VARCHAR(50) NULL ,
 nombre_contacto       VARCHAR(100) NULL ,
 telef_contacto        INT NULL ,
 ruc                   VARCHAR(20) NULL ,
 dni                   VARCHAR(20) NULL ,
 last_user_change      VARCHAR(20) NOT NULL ,
 last_date_change      DATE NOT NULL ,
 flag_last_operation   CHAR(1) NOT NULL ,
 estado                VARCHAR(50) NOT NULL ,
 tipo_cliente          VARCHAR(20) NOT NULL ,
 direccion_despacho    VARCHAR(150) NOT NULL ,
 direccion_facturacion VARCHAR(150) NOT NULL ,

 CONSTRAINT pk_83 PRIMARY KEY  (client_id )
);




--************************************** AccionesxMenus

CREATE TABLE AccionesxMenus
(
 menu_id     SERIAL NOT NULL ,
 accion_cod  VARCHAR(30) NOT NULL ,
 accion_id   SERIAL NOT NULL ,
 descripcion VARCHAR(200) NOT NULL ,

 CONSTRAINT pk_1624 PRIMARY KEY  (menu_id , accion_cod , accion_id ),
 CONSTRAINT fk_1621 FOREIGN KEY (menu_id)
  REFERENCES Menus(menu_id),
 CONSTRAINT fk_1626 FOREIGN KEY (accion_cod, accion_id)
  REFERENCES Acciones(accion_cod, accion_id)
);



--SKIP Index: fkIdx_1621

--SKIP Index: fkIdx_1626


--************************************** CambioMonedas

CREATE TABLE CambioMonedas
(
 moneda1_id SERIAL NOT NULL ,
 moneda2_id SERIAL NOT NULL ,
 factor     DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_446 PRIMARY KEY  (moneda1_id , moneda2_id ),
 CONSTRAINT fk_443 FOREIGN KEY (moneda1_id)
  REFERENCES Monedas(moneda_id),
 CONSTRAINT fk_448 FOREIGN KEY (moneda2_id)
  REFERENCES Monedas(moneda_id)
);



--SKIP Index: fkIdx_443

--SKIP Index: fkIdx_448


--************************************** Lotes

CREATE TABLE Lotes
(
 lote_cod      VARCHAR(10) NOT NULL ,
 lote_id       SERIAL NOT NULL ,
 fecha_entrada DATE NOT NULL ,
 proveedor_ruc VARCHAR(20) NOT NULL ,
 proveedor_id  SERIAL NOT NULL ,

 CONSTRAINT pk_331 PRIMARY KEY  (lote_cod , lote_id ),
 CONSTRAINT fk_354 FOREIGN KEY (proveedor_ruc, proveedor_id)
  REFERENCES Proveedores(provuder_ruc, proveedor_id)
);



--SKIP Index: fkIdx_354


--************************************** TiposProducto

CREATE TABLE TiposProducto
(
 tipo_cod            VARCHAR(20) NOT NULL ,
 tipo_id             SERIAL NOT NULL ,
 peso                DECIMAL(10,2) NOT NULL ,
 nombre              VARCHAR(50) NOT NULL ,
 pericible           CHAR(1) NOT NULL ,
 descripcion         VARCHAR(100) NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 estado              VARCHAR(20) NOT NULL ,
 longitud            DECIMAL(10,2) NOT NULL ,
 ancho               DECIMAL(10,2) NOT NULL ,
 unidad_id           SERIAL NOT NULL ,

 CONSTRAINT pk_289 PRIMARY KEY  (tipo_cod , tipo_id ),
 CONSTRAINT fk_295 FOREIGN KEY (unidad_id)
  REFERENCES Unidades(unidad_id)
);



--SKIP Index: fkIdx_295


--************************************** Racks

CREATE TABLE Racks
(
 rack_cod    VARCHAR(50) NOT NULL ,
 almacen_id  SERIAL NOT NULL ,
 rack_id     SERIAL NOT NULL ,
 almacen_cod VARCHAR(50) NOT NULL ,
 longitud    INT NOT NULL ,
 altura      INT NOT NULL ,
 es_uniforme CHAR(1) NOT NULL ,
 x_ancla1    INT NOT NULL ,
 y_ancla1    INT NOT NULL ,
 x_ancla2    INT NOT NULL ,
 y_ancla2    INT NOT NULL ,
 estado      VARCHAR(20) NOT NULL ,

 CONSTRAINT pk_249 PRIMARY KEY  (rack_cod , almacen_id , rack_id , almacen_cod ),
 CONSTRAINT fk_319 FOREIGN KEY (almacen_cod, almacen_id)
  REFERENCES Almacenes(almacen_cod, almacen_id)
);



--SKIP Index: fkIdx_319


--************************************** AlmacenAreaXYs

CREATE TABLE AlmacenAreaXYs
(
 almacen_xy_cod VARCHAR(50) NOT NULL ,
 almacen_id     SERIAL NOT NULL ,
 almacen_xy_id  SERIAL NOT NULL ,
 almacen_cod    VARCHAR(50) NOT NULL ,
 alto           INT NULL ,
 estado         CHAR(1) NOT NULL ,
 tipo_area_id   SERIAL NOT NULL ,

 CONSTRAINT pk_190 PRIMARY KEY  (almacen_xy_cod , almacen_id , almacen_xy_id , almacen_cod ),
 CONSTRAINT fk_197 FOREIGN KEY (almacen_cod, almacen_id)
  REFERENCES Almacenes(almacen_cod, almacen_id),
 CONSTRAINT fk_209 FOREIGN KEY (tipo_area_id)
  REFERENCES TiposArea(tipo_area_id)
);



--SKIP Index: fkIdx_197

--SKIP Index: fkIdx_209


--************************************** Usuarios

CREATE TABLE Usuarios
(
 usuario_cod           VARCHAR(20) NOT NULL ,
 usuario_id            SERIAL NOT NULL ,
 contrasena_encriptada VARCHAR(100) NOT NULL ,
 email                 VARCHAR(100) NULL ,
 nombre                VARCHAR(50) NULL ,
 apellido              VARCHAR(50) NULL ,
 telefono              VARCHAR(20) NULL ,
 es_admin              CHAR(1) NULL ,
 last_user_change      VARCHAR(20) NOT NULL ,
 last_date_change      DATE NOT NULL ,
 estado                VARCHAR(50) NOT NULL ,
 contrasena_nullable   VARCHAR(100) NULL ,
 rol_cod               VARCHAR(30) NOT NULL ,
 rol_id                SERIAL NOT NULL ,
 flag_last_operation   CHAR(1) NOT NULL ,

 CONSTRAINT pk_96 PRIMARY KEY  (usuario_cod , usuario_id ),
 CONSTRAINT fk_1101 FOREIGN KEY (rol_cod, rol_id)
  REFERENCES Roles(rol_cod, rol_id)
);



--SKIP Index: fkIdx_1101


--************************************** Stocks

CREATE TABLE Stocks
(
 tipo_id      SERIAL NOT NULL ,
 tipo_cod     VARCHAR(20) NOT NULL ,
 stock_real   INT NOT NULL ,
 stock_logico INT NOT NULL ,

 CONSTRAINT pk_1049 PRIMARY KEY  (tipo_id , tipo_cod ),
 CONSTRAINT fk_1046 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id)
);



--SKIP Index: fkIdx_1046


--************************************** Precios

CREATE TABLE Precios
(
 tipo_id      SERIAL NOT NULL ,
 tipo_cod     VARCHAR(20) NOT NULL ,
 precio       DECIMAL(10,4) NOT NULL ,
 fecha_inicio DATE NOT NULL ,
 fecha_fin    DATE NOT NULL ,
 es_default   CHAR(1) NOT NULL ,
 moneda_id    SERIAL NOT NULL ,

 CONSTRAINT pk_1034 PRIMARY KEY  (tipo_id , tipo_cod ),
 CONSTRAINT fk_1031 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id),
 CONSTRAINT fk_1037 FOREIGN KEY (moneda_id)
  REFERENCES Monedas(moneda_id)
);



--SKIP Index: fkIdx_1031

--SKIP Index: fkIdx_1037


--************************************** Fletes

CREATE TABLE Fletes
(
 flete_id       SERIAL NOT NULL ,
 flete_code     VARCHAR(30) NOT NULL ,
 fecha_inicio   DATE NOT NULL ,
 fecha_fin      DATE NULL ,
 prioridad      INT NOT NULL ,
 es_categoria   CHAR(1) NOT NULL ,
 tipo           VARCHAR(50) NOT NULL ,
 estado         VARCHAR(20) NOT NULL ,
 tipo_id        SERIAL NOT NULL ,
 categoria_code VARCHAR(20) NULL ,
 categoria_id   SERIAL NOT NULL ,
 tipo_cod       VARCHAR(20) NULL ,

 CONSTRAINT pk_933 PRIMARY KEY  (flete_id , flete_code ),
 CONSTRAINT fk_978 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id),
 CONSTRAINT fk_1359 FOREIGN KEY (categoria_code, categoria_id)
  REFERENCES CategoriasProducto(categoria_code, categoria_id)
);



--SKIP Index: fkIdx_978

--SKIP Index: fkIdx_1359


--************************************** Promociones

CREATE TABLE Promociones
(
 promocion_cod  VARCHAR(30) NOT NULL ,
 promocion_id   SERIAL NOT NULL ,
 fecha_inicio   DATE NOT NULL ,
 fecha_fin      DATE NULL ,
 prioridad      INT NOT NULL ,
 es_categoria   CHAR(1) NOT NULL ,
 estado         VARCHAR(20) NOT NULL ,
 tipo           VARCHAR(20) NOT NULL ,
 tipo_id        SERIAL NOT NULL ,
 categoria_code VARCHAR(20) NULL ,
 categoria_id   SERIAL NOT NULL ,
 tipo_cod       VARCHAR(20) NOT NULL ,

 CONSTRAINT pk_888 PRIMARY KEY  (promocion_cod , promocion_id ),
 CONSTRAINT fk_951 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id),
 CONSTRAINT fk_955 FOREIGN KEY (categoria_code, categoria_id)
  REFERENCES CategoriasProducto(categoria_code, categoria_id)
);



--SKIP Index: fkIdx_951

--SKIP Index: fkIdx_955


--************************************** CategoriasxTipos

CREATE TABLE CategoriasxTipos
(
 tipo_id             SERIAL NOT NULL ,
 categoria_code      VARCHAR(20) NOT NULL ,
 categoria_id        SERIAL NOT NULL ,
 tipo_cod            VARCHAR(20) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,

 CONSTRAINT pk_865 PRIMARY KEY  (tipo_id , categoria_code , categoria_id , tipo_cod ),
 CONSTRAINT fk_862 FOREIGN KEY (categoria_code, categoria_id)
  REFERENCES CategoriasProducto(categoria_code, categoria_id),
 CONSTRAINT fk_867 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id)
);



--SKIP Index: fkIdx_862

--SKIP Index: fkIdx_867


--************************************** OrdenesEntrada

CREATE TABLE OrdenesEntrada
(
 orden_entrada_cod   VARCHAR(30) NOT NULL ,
 orden_entrada_id    SERIAL NOT NULL ,
 fecha_emision       DATE NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_date_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 estado              VARCHAR(20) NOT NULL ,
 descripcion         VARCHAR(200) NOT NULL ,
 usuario_cod         VARCHAR(20) NOT NULL ,
 usuario_id          SERIAL NOT NULL ,
 tipo_entrada_id     SERIAL NOT NULL ,
 tipo_entdada_cod    VARCHAR(30) NOT NULL ,

 CONSTRAINT pk_767 PRIMARY KEY  (orden_entrada_cod , orden_entrada_id ),
 CONSTRAINT fk_792 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id),
 CONSTRAINT fk_1611 FOREIGN KEY (tipo_entrada_id, tipo_entdada_cod)
  REFERENCES TiposOrdenEntrada(tipo_entrada_id, tipo_entdada_cod)
);



--SKIP Index: fkIdx_792

--SKIP Index: fkIdx_1611


--************************************** Cotizaciones

CREATE TABLE Cotizaciones
(
 cotizacion_cod       VARCHAR(20) NOT NULL ,
 client_id            SERIAL NOT NULL ,
 cotizacion_id        SERIAL NOT NULL ,
 fecha_emision        DATE NOT NULL ,
 estado               VARCHAR(20) NOT NULL ,
 total                DECIMAL(10,2) NOT NULL ,
 last_user_change     VARCHAR(20) NOT NULL ,
 last_date_change     DATE NOT NULL ,
 flag_last_operation  CHAR(1) NOT NULL ,
 usuario_cod          VARCHAR(20) NOT NULL ,
 usuario_id           SERIAL NOT NULL ,
 moneda_id            SERIAL NOT NULL ,
 promocion_automatica CHAR(1) NOT NULL ,

 CONSTRAINT pk_414 PRIMARY KEY  (cotizacion_cod , client_id , cotizacion_id ),
 CONSTRAINT fk_417 FOREIGN KEY (client_id)
  REFERENCES Clientes(client_id),
 CONSTRAINT fk_421 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id),
 CONSTRAINT fk_823 FOREIGN KEY (moneda_id)
  REFERENCES Monedas(moneda_id)
);



--SKIP Index: fkIdx_417

--SKIP Index: fkIdx_421

--SKIP Index: fkIdx_823


--************************************** RutasRacks

CREATE TABLE RutasRacks
(
 rack1_id     SERIAL NOT NULL ,
 almacen1_id  SERIAL NOT NULL ,
 rack1_cod    VARCHAR(50) NOT NULL ,
 almacen1_cod VARCHAR(50) NOT NULL ,
 rack2_id     SERIAL NOT NULL ,
 almacen2_id  SERIAL NOT NULL ,
 rack2_cod    VARCHAR(50) NOT NULL ,
 almacen2_cod VARCHAR(50) NOT NULL ,
 distancia1   DECIMAL(10,2) NOT NULL ,
 distancia2   DECIMAL(10,2) NOT NULL ,
 nr_pasos1    INT NOT NULL ,
 nr_pasos2    INT NOT NULL ,
 rack1_ancla1 CHAR(1) NOT NULL ,
 rack2_ancla1 CHAR(1) NOT NULL ,
 rack1_ancla2 CHAR(1) NOT NULL ,
 rack2_ancla2 CHAR(1) NOT NULL ,
 ruta1        TEXT NOT NULL ,
 ruta2        TEXT NOT NULL ,

 CONSTRAINT pk_365 PRIMARY KEY  (rack1_id , almacen1_id , rack1_cod , almacen1_cod , rack2_id , almacen2_id , rack2_cod , almacen2_cod ),
 CONSTRAINT fk_1401 FOREIGN KEY (rack1_cod, almacen1_id, rack1_id, almacen1_cod)
  REFERENCES Racks(rack_cod, almacen_id, rack_id, almacen_cod),
 CONSTRAINT fk_1413 FOREIGN KEY (rack2_cod, almacen2_id, rack2_id, almacen2_cod)
  REFERENCES Racks(rack_cod, almacen_id, rack_id, almacen_cod)
);



--SKIP Index: fkIdx_1401

--SKIP Index: fkIdx_1413


--************************************** AlmacenAreaZs

CREATE TABLE AlmacenAreaZs
(
 almacen_z_cod  VARCHAR(50) NOT NULL ,
 almacen_id     SERIAL NOT NULL ,
 almacen_xy_cod VARCHAR(50) NOT NULL ,
 almacen_xy_id  SERIAL NOT NULL ,
 almacen_z_id   SERIAL NOT NULL ,
 almacen_cod    VARCHAR(50) NOT NULL ,
 capacity       DECIMAL(10,2) NOT NULL ,
 level          INT NOT NULL ,
 state          CHAR(1) NOT NULL ,

 CONSTRAINT pk_195 PRIMARY KEY  (almacen_z_cod , almacen_id , almacen_xy_cod , almacen_xy_id , almacen_z_id , almacen_cod ),
 CONSTRAINT fk_190 FOREIGN KEY (almacen_xy_cod, almacen_id, almacen_xy_id, almacen_cod)
  REFERENCES AlmacenAreaXYs(almacen_xy_cod, almacen_id, almacen_xy_id, almacen_cod)
);



--SKIP Index: fkIdx_191


--************************************** AccionesxRoles

CREATE TABLE AccionesxRoles
(
 menu_id    SERIAL NOT NULL ,
 accion_cod VARCHAR(30) NOT NULL ,
 rol_cod    VARCHAR(30) NOT NULL ,
 rol_id     SERIAL NOT NULL ,
 accion_id  SERIAL NOT NULL ,
 solo_admin CHAR(1) NOT NULL ,

 CONSTRAINT pk_126 PRIMARY KEY  (menu_id , accion_cod , rol_cod , rol_id , accion_id ),
 CONSTRAINT fk_1632 FOREIGN KEY (menu_id, accion_cod, accion_id)
  REFERENCES AccionesxMenus(menu_id, accion_cod, accion_id),
 CONSTRAINT fk_1644 FOREIGN KEY (rol_cod, rol_id)
  REFERENCES Roles(rol_cod, rol_id)
);



--SKIP Index: fkIdx_1632

--SKIP Index: fkIdx_1644


--************************************** ErrorLog

CREATE TABLE ErrorLog
(
 error_cod     VARCHAR(20) NOT NULL ,
 error_type_id SERIAL NOT NULL ,
 error_id      SERIAL NOT NULL ,
 usuario_cod   VARCHAR(20) NOT NULL ,
 usuario_id    SERIAL NOT NULL ,
 descripcion   VARCHAR(300) NOT NULL ,
 menu_id       SERIAL NOT NULL ,
 accion_cod    VARCHAR(30) NOT NULL ,
 rol_cod       VARCHAR(30) NOT NULL ,
 rol_id        SERIAL NOT NULL ,
 accion_id     SERIAL NOT NULL ,

 CONSTRAINT pk_1079 PRIMARY KEY  (error_cod , error_type_id , error_id ),
 CONSTRAINT fk_1076 FOREIGN KEY (error_cod, error_type_id)
  REFERENCES TiposError(error_cod, error_type_id),
 CONSTRAINT fk_1081 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id),
 CONSTRAINT fk_1541 FOREIGN KEY (menu_id, accion_cod, rol_cod, rol_id, accion_id)
  REFERENCES AccionesxRoles(menu_id, accion_cod, rol_cod, rol_id, accion_id)
);



--SKIP Index: fkIdx_1076

--SKIP Index: fkIdx_1081

--SKIP Index: fkIdx_1541


--************************************** AccionLog

CREATE TABLE AccionLog
(
 menu_id     SERIAL NOT NULL ,
 accion_cod  VARCHAR(30) NOT NULL ,
 rol_cod     VARCHAR(30) NOT NULL ,
 rol_id      SERIAL NOT NULL ,
 accion_id   SERIAL NOT NULL ,
 tiempo      DATE NOT NULL ,
 usuario_cod VARCHAR(20) NOT NULL ,
 usuario_id  SERIAL NOT NULL ,

 CONSTRAINT pk_1059 PRIMARY KEY  (menu_id , accion_cod , rol_cod , rol_id , accion_id ),
 CONSTRAINT fk_1056 FOREIGN KEY (menu_id, accion_cod, rol_cod, rol_id, accion_id)
  REFERENCES AccionesxRoles(menu_id, accion_cod, rol_cod, rol_id, accion_id),
 CONSTRAINT fk_1064 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id)
);



--SKIP Index: fkIdx_1056

--SKIP Index: fkIdx_1064


--************************************** PorcentajeFletes

CREATE TABLE PorcentajeFletes
(
 flete_id   SERIAL NOT NULL ,
 flete_code VARCHAR(30) NOT NULL ,
 percent    DECIMAL(10,4) NOT NULL ,

 CONSTRAINT pk_1001 PRIMARY KEY  (flete_id , flete_code ),
 CONSTRAINT fk_998 FOREIGN KEY (flete_id, flete_code)
  REFERENCES Fletes(flete_id, flete_code)
);



--SKIP Index: fkIdx_998


--************************************** VolumenFletes

CREATE TABLE VolumenFletes
(
 flete_id           SERIAL NOT NULL ,
 flete_code         VARCHAR(30) NOT NULL ,
 cost_unidad_medida DECIMAL(10,4) NOT NULL ,

 CONSTRAINT pk_996 PRIMARY KEY  (flete_id , flete_code ),
 CONSTRAINT fk_993 FOREIGN KEY (flete_id, flete_code)
  REFERENCES Fletes(flete_id, flete_code)
);



--SKIP Index: fkIdx_993


--************************************** PesoFletes

CREATE TABLE PesoFletes
(
 flete_id          SERIAL NOT NULL ,
 flete_code        VARCHAR(30) NOT NULL ,
 costo_unidad_peso DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_991 PRIMARY KEY  (flete_id , flete_code ),
 CONSTRAINT fk_988 FOREIGN KEY (flete_id, flete_code)
  REFERENCES Fletes(flete_id, flete_code)
);



--SKIP Index: fkIdx_988


--************************************** PromocionBonificaciones

CREATE TABLE PromocionBonificaciones
(
 promocion_cod VARCHAR(30) NOT NULL ,
 promocion_id  SERIAL NOT NULL ,
 nr_comprar    DECIMAL(10,2) NOT NULL ,
 nr_obtener    DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_878 PRIMARY KEY  (promocion_cod , promocion_id ),
 CONSTRAINT fk_901 FOREIGN KEY (promocion_cod, promocion_id)
  REFERENCES Promociones(promocion_cod, promocion_id)
);



--SKIP Index: fkIdx_901


--************************************** NotasCredito

CREATE TABLE NotasCredito
(
 credit_note_cod        VARCHAR(20) NOT NULL ,
 client_id              SERIAL NOT NULL ,
 credit_note_id         SERIAL NOT NULL ,
 last_user_change       VARCHAR(20) NOT NULL ,
 last_date_change       DATE NOT NULL ,
 flag_last_operation    CHAR(1) NOT NULL ,
 explicacion            VARCHAR(150) NOT NULL ,
 fecha_emision          DATE NOT NULL ,
 usuario_cod            VARCHAR(20) NOT NULL ,
 usuario_id             SERIAL NOT NULL ,
 nota_credito_estado_id SERIAL NOT NULL ,
 orden_entrada_id       SERIAL NOT NULL ,
 orden_entrada_cod      VARCHAR(30) NOT NULL ,

 CONSTRAINT pk_629 PRIMARY KEY  (credit_note_cod , client_id , credit_note_id ),
 CONSTRAINT fk_688 FOREIGN KEY (client_id)
  REFERENCES Clientes(client_id),
 CONSTRAINT fk_692 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id),
 CONSTRAINT fk_811 FOREIGN KEY (nota_credito_estado_id)
  REFERENCES NotaCreditoEstados(nota_credito_estado_id),
 CONSTRAINT fk_1106 FOREIGN KEY (orden_entrada_cod, orden_entrada_id)
  REFERENCES OrdenesEntrada(orden_entrada_cod, orden_entrada_id)
);



--SKIP Index: fkIdx_688

--SKIP Index: fkIdx_692

--SKIP Index: fkIdx_811

--SKIP Index: fkIdx_1106


--************************************** PromocionCantidades

CREATE TABLE PromocionCantidades
(
 promocion_cod        VARCHAR(30) NOT NULL ,
 promocion_id         SERIAL NOT NULL ,
 nr_comprar           DECIMAL(10,2) NOT NULL ,
 nr_obtener           DECIMAL(10,2) NOT NULL ,
 es_categoria_obtener NCHAR(1) NOT NULL ,
 categoria_code       VARCHAR(20) NULL ,
 categoria_id         SERIAL NOT NULL ,
 tipo_id              SERIAL NOT NULL ,
 tipo_cod             VARCHAR(20) NULL ,

 CONSTRAINT pk_535 PRIMARY KEY  (promocion_cod , promocion_id ),
 CONSTRAINT fk_1318 FOREIGN KEY (promocion_cod, promocion_id)
  REFERENCES Promociones(promocion_cod, promocion_id),
 CONSTRAINT fk_1323 FOREIGN KEY (categoria_code, categoria_id)
  REFERENCES CategoriasProducto(categoria_code, categoria_id),
 CONSTRAINT fk_1328 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id)
);



--SKIP Index: fkIdx_1318

--SKIP Index: fkIdx_1323

--SKIP Index: fkIdx_1328


--************************************** PromocionPorcentajes

CREATE TABLE PromocionPorcentajes
(
 promocion_cod      VARCHAR(30) NOT NULL ,
 promocion_id       SERIAL NOT NULL ,
 valor_desc         DECIMAL(10,4) NOT NULL ,
 concepto_desc      VARCHAR(30) NOT NULL ,
 valor_condition    VARCHAR(20) NOT NULL ,
 concepto_condition VARCHAR(30) NOT NULL ,
 relacion_condition CHAR(1) NOT NULL ,

 CONSTRAINT pk_519 PRIMARY KEY  (promocion_cod , promocion_id ),
 CONSTRAINT fk_904 FOREIGN KEY (promocion_cod, promocion_id)
  REFERENCES Promociones(promocion_cod, promocion_id)
);



--SKIP Index: fkIdx_904


--************************************** OrdenesCompra

CREATE TABLE OrdenesCompra
(
 orden_compra_cod       VARCHAR(20) NOT NULL ,
 client_id              SERIAL NOT NULL ,
 orden_compra_id        SERIAL NOT NULL ,
 fecha_emision          DATE NOT NULL ,
 subtotal               DECIMAL NOT NULL ,
 last_user_change       VARCHAR(20) NOT NULL ,
 last_date_change       DATE NOT NULL ,
 flag_last_operation    CHAR(1) NOT NULL ,
 usuario_cod            VARCHAR(20) NOT NULL ,
 usuario_id             SERIAL NOT NULL ,
 moneda_id              SERIAL NOT NULL ,
 cotizacion_cod         VARCHAR(20) NULL ,
 cotizacion_id          SERIAL NOT NULL ,
 order_compra_estado_id SERIAL NOT NULL ,

 CONSTRAINT pk_469 PRIMARY KEY  (orden_compra_cod , client_id , orden_compra_id ),
 CONSTRAINT fk_471 FOREIGN KEY (client_id)
  REFERENCES Clientes(client_id),
 CONSTRAINT fk_475 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id),
 CONSTRAINT fk_580 FOREIGN KEY (cotizacion_cod, client_id, cotizacion_id)
  REFERENCES Cotizaciones(cotizacion_cod, client_id, cotizacion_id),
 CONSTRAINT fk_603 FOREIGN KEY (order_compra_estado_id)
  REFERENCES OrdenCompraEstados(order_compra_estado_id),
 CONSTRAINT fk_815 FOREIGN KEY (moneda_id)
  REFERENCES Monedas(moneda_id)
);



--SKIP Index: fkIdx_471

--SKIP Index: fkIdx_475

--SKIP Index: fkIdx_580

--SKIP Index: fkIdx_603

--SKIP Index: fkIdx_815


--************************************** CotizacionxProductos

CREATE TABLE CotizacionxProductos
(
 tipo_id         SERIAL NOT NULL ,
 client_id       SERIAL NOT NULL ,
 cotizacion_cod  VARCHAR(20) NOT NULL ,
 cotizacion_id   SERIAL NOT NULL ,
 tipo_cod        VARCHAR(20) NOT NULL ,
 cantidad        DECIMAL(10,2) NOT NULL ,
 subtotal_previo DECIMAL(10,2) NOT NULL ,
 subtotal_final  DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_456 PRIMARY KEY  (tipo_id , client_id , cotizacion_cod , cotizacion_id , tipo_cod ),
 CONSTRAINT fk_453 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id),
 CONSTRAINT fk_458 FOREIGN KEY (cotizacion_cod, client_id, cotizacion_id)
  REFERENCES Cotizaciones(cotizacion_cod, client_id, cotizacion_id)
);



--SKIP Index: fkIdx_453

--SKIP Index: fkIdx_458


--************************************** Productos

CREATE TABLE Productos
(
 producto_cod    VARCHAR(30) NOT NULL ,
 producto_id     SERIAL NOT NULL ,
 tipo_id         SERIAL NOT NULL ,
 tipo_cod        VARCHAR(20) NOT NULL ,
 fecha_entrada   DATE NULL ,
 fecha_caducidad DATE NULL ,
 fecha_adquirida DATE NOT NULL ,
 estado_id       SERIAL NOT NULL ,
 almacen_id      SERIAL NOT NULL ,
 almacen_xy_cod  VARCHAR(50) NOT NULL ,
 almacen_xy_id   SERIAL NOT NULL ,
 almacen_z_cod   VARCHAR(50) NOT NULL ,
 almacen_z_id    SERIAL NOT NULL ,
 rack_cod        VARCHAR(50) NOT NULL ,
 rack_id         SERIAL NOT NULL ,
 lote_cod        VARCHAR(10) NULL ,
 lote_id         SERIAL NOT NULL ,
 almacen_cod     VARCHAR(50) NOT NULL ,

 CONSTRAINT pk_221 PRIMARY KEY  (producto_cod , producto_id , tipo_id , tipo_cod ),
 CONSTRAINT fk_265 FOREIGN KEY (rack_cod, almacen_id, rack_id, almacen_cod)
  REFERENCES Racks(rack_cod, almacen_id, rack_id, almacen_cod),
 CONSTRAINT fk_269 FOREIGN KEY (almacen_z_cod, almacen_id, almacen_xy_cod, almacen_xy_id, almacen_z_id, almacen_cod)
  REFERENCES AlmacenAreaZs(almacen_z_cod, almacen_id, almacen_xy_cod, almacen_xy_id, almacen_z_id, almacen_cod),
 CONSTRAINT fk_282 FOREIGN KEY (estado_id)
  REFERENCES EstadosProducto(estado_id),
 CONSTRAINT fk_291 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id),
 CONSTRAINT fk_338 FOREIGN KEY (lote_cod, lote_id)
  REFERENCES Lotes(lote_cod, lote_id)
);



--SKIP Index: fkIdx_265

--SKIP Index: fkIdx_269

--SKIP Index: fkIdx_282

--SKIP Index: fkIdx_291

--SKIP Index: fkIdx_338


--************************************** CotizacionesxProductosxFletes

CREATE TABLE CotizacionesxProductosxFletes
(
 tipo_id        SERIAL NOT NULL ,
 client_id      SERIAL NOT NULL ,
 cotizacion_cod VARCHAR(20) NOT NULL ,
 cotizacion_id  SERIAL NOT NULL ,
 flete_id       SERIAL NOT NULL ,
 tipo_cod       VARCHAR(20) NOT NULL ,
 flete_code     VARCHAR(30) NOT NULL ,
 valor_flete    DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_1384 PRIMARY KEY  (tipo_id , client_id , cotizacion_cod , cotizacion_id , flete_id , tipo_cod , flete_code ),
 CONSTRAINT fk_1388 FOREIGN KEY (tipo_id, client_id, cotizacion_cod, cotizacion_id, tipo_cod)
  REFERENCES CotizacionxProductos(tipo_id, client_id, cotizacion_cod, cotizacion_id, tipo_cod),
 CONSTRAINT fk_1396 FOREIGN KEY (flete_id, flete_code)
  REFERENCES Fletes(flete_id, flete_code)
);



--SKIP Index: fkIdx_1388

--SKIP Index: fkIdx_1396


--************************************** CotizacionesxProductosxPromociones

CREATE TABLE CotizacionesxProductosxPromociones
(
 promocion_cod   VARCHAR(30) NOT NULL ,
 promocion_id    SERIAL NOT NULL ,
 tipo_id         SERIAL NOT NULL ,
 client_id       SERIAL NOT NULL ,
 cotizacion_cod  VARCHAR(20) NOT NULL ,
 cotizacion_id   SERIAL NOT NULL ,
 tipo_cod        VARCHAR(20) NOT NULL ,
 valor_descuento DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_1369 PRIMARY KEY  (promocion_cod , promocion_id , tipo_id , client_id , cotizacion_cod , cotizacion_id , tipo_cod ),
 CONSTRAINT fk_1366 FOREIGN KEY (promocion_cod, promocion_id)
  REFERENCES Promociones(promocion_cod, promocion_id),
 CONSTRAINT fk_1372 FOREIGN KEY (tipo_id, client_id, cotizacion_cod, cotizacion_id, tipo_cod)
  REFERENCES CotizacionxProductos(tipo_id, client_id, cotizacion_cod, cotizacion_id, tipo_cod)
);



--SKIP Index: fkIdx_1366

--SKIP Index: fkIdx_1372


--************************************** OrdenesEntradaxProductos

CREATE TABLE OrdenesEntradaxProductos
(
 producto_cod      VARCHAR(30) NOT NULL ,
 producto_id       SERIAL NOT NULL ,
 tipo_id           SERIAL NOT NULL ,
 tipo_cod          VARCHAR(20) NOT NULL ,
 orden_entrada_id  SERIAL NOT NULL ,
 orden_entrada_cod VARCHAR(30) NOT NULL ,
 provider_ruc      VARCHAR(20) NOT NULL ,
 estado            VARCHAR(20) NOT NULL ,
 proveedor_id      SERIAL NOT NULL ,

 CONSTRAINT pk_778 PRIMARY KEY  (producto_cod , producto_id , tipo_id , tipo_cod , orden_entrada_id , orden_entrada_cod ),
 CONSTRAINT fk_775 FOREIGN KEY (producto_cod, producto_id, tipo_id, tipo_cod)
  REFERENCES Productos(producto_cod, producto_id, tipo_id, tipo_cod),
 CONSTRAINT fk_783 FOREIGN KEY (orden_entrada_cod, orden_entrada_id)
  REFERENCES OrdenesEntrada(orden_entrada_cod, orden_entrada_id),
 CONSTRAINT fk_787 FOREIGN KEY (provider_ruc, proveedor_id)
  REFERENCES Proveedores(provuder_ruc, proveedor_id)
);



--SKIP Index: fkIdx_775

--SKIP Index: fkIdx_783

--SKIP Index: fkIdx_787


--************************************** ProductosxNotasCredito

CREATE TABLE ProductosxNotasCredito
(
 client_id       SERIAL NOT NULL ,
 credit_note_cod VARCHAR(20) NOT NULL ,
 credit_note_id  SERIAL NOT NULL ,
 tipo_cod        VARCHAR(20) NOT NULL ,
 tipo_id         SERIAL NOT NULL ,
 valor_credito   DECIMAL(10,2) NOT NULL ,
 cantidad        INT NOT NULL ,

 CONSTRAINT pk_667 PRIMARY KEY  (client_id , credit_note_cod , credit_note_id , tipo_cod , tipo_id ),
 CONSTRAINT fk_673 FOREIGN KEY (credit_note_cod, client_id, credit_note_id)
  REFERENCES NotasCredito(credit_note_cod, client_id, credit_note_id),
 CONSTRAINT fk_1574 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id)
);



--SKIP Index: fkIdx_673

--SKIP Index: fkIdx_1574


--************************************** DocVentas

CREATE TABLE DocVentas
(
 doc_venta_cod        VARCHAR(30) NOT NULL ,
 client_id            SERIAL NOT NULL ,
 doc_venta_id         SERIAL NOT NULL ,
 orden_venta_completa CHAR(1) NOT NULL ,
 last_user_change     VARCHAR(20) NOT NULL ,
 last_date_change     CHAR(1) NOT NULL ,
 flag_last_operation  CHAR(1) NOT NULL ,
 fecha_emision        DATE NOT NULL ,
 usuario_cod          VARCHAR(20) NOT NULL ,
 usuario_id           SERIAL NOT NULL ,
 moneda_id            SERIAL NOT NULL ,
 orden_compra_id      SERIAL NOT NULL ,
 orden_compra_cod     VARCHAR(20) NOT NULL ,
 doc_venta_estado_id  SERIAL NOT NULL ,

 CONSTRAINT pk_624 PRIMARY KEY  (doc_venta_cod , client_id , doc_venta_id ),
 CONSTRAINT fk_631 FOREIGN KEY (orden_compra_cod, client_id, orden_compra_id)
  REFERENCES OrdenesCompra(orden_compra_cod, client_id, orden_compra_id),
 CONSTRAINT fk_658 FOREIGN KEY (doc_venta_estado_id)
  REFERENCES DocVentaEstados(doc_venta_estado_id),
 CONSTRAINT fk_677 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id),
 CONSTRAINT fk_681 FOREIGN KEY (client_id)
  REFERENCES Clientes(client_id),
 CONSTRAINT fk_819 FOREIGN KEY (moneda_id)
  REFERENCES Monedas(moneda_id)
);



--SKIP Index: fkIdx_631

--SKIP Index: fkIdx_658

--SKIP Index: fkIdx_677

--SKIP Index: fkIdx_681

--SKIP Index: fkIdx_819


--************************************** OrdenesCompraxProductos

CREATE TABLE OrdenesCompraxProductos
(
 tipo_id          SERIAL NOT NULL ,
 client_id        SERIAL NOT NULL ,
 orden_compra_id  SERIAL NOT NULL ,
 orden_compra_cod VARCHAR(20) NOT NULL ,
 tipo_cod         VARCHAR(20) NOT NULL ,
 quantity         DECIMAL(10,2) NOT NULL ,
 subtotal         DECIMAL(10,2) NOT NULL ,
 delivery_date    DATE NOT NULL ,

 CONSTRAINT pk_577 PRIMARY KEY  (tipo_id , client_id , orden_compra_id , orden_compra_cod , tipo_cod ),
 CONSTRAINT fk_574 FOREIGN KEY (orden_compra_cod, client_id, orden_compra_id)
  REFERENCES OrdenesCompra(orden_compra_cod, client_id, orden_compra_id),
 CONSTRAINT fk_584 FOREIGN KEY (tipo_cod, tipo_id)
  REFERENCES TiposProducto(tipo_cod, tipo_id)
);



--SKIP Index: fkIdx_574

--SKIP Index: fkIdx_584


--************************************** OrdenesCompraxProductosxFletes

CREATE TABLE OrdenesCompraxProductosxFletes
(
 tipo_id          SERIAL NOT NULL ,
 client_id        SERIAL NOT NULL ,
 orden_compra_id  SERIAL NOT NULL ,
 orden_compra_cod VARCHAR(20) NOT NULL ,
 tipo_cod         VARCHAR(20) NOT NULL ,
 flete_id         SERIAL NOT NULL ,
 flete_code       VARCHAR(30) NOT NULL ,
 valor_flete      DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_1518 PRIMARY KEY  (tipo_id , client_id , orden_compra_id , orden_compra_cod , tipo_cod , flete_id , flete_code ),
 CONSTRAINT fk_1515 FOREIGN KEY (tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod)
  REFERENCES OrdenesCompraxProductos(tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod),
 CONSTRAINT fk_1524 FOREIGN KEY (flete_id, flete_code)
  REFERENCES Fletes(flete_id, flete_code)
);



--SKIP Index: fkIdx_1515

--SKIP Index: fkIdx_1524


--************************************** OrdenesCompraxProductosxPromociones

CREATE TABLE OrdenesCompraxProductosxPromociones
(
 tipo_id          SERIAL NOT NULL ,
 client_id        SERIAL NOT NULL ,
 orden_compra_id  SERIAL NOT NULL ,
 orden_compra_cod VARCHAR(20) NOT NULL ,
 tipo_cod         VARCHAR(20) NOT NULL ,
 promocion_cod    VARCHAR(30) NOT NULL ,
 promocion_id     SERIAL NOT NULL ,
 valor_desc       DECIMAL(10,2) NOT NULL ,

 CONSTRAINT pk_1501 PRIMARY KEY  (tipo_id , client_id , orden_compra_id , orden_compra_cod , tipo_cod , promocion_cod , promocion_id ),
 CONSTRAINT fk_1498 FOREIGN KEY (tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod)
  REFERENCES OrdenesCompraxProductos(tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod),
 CONSTRAINT fk_1507 FOREIGN KEY (promocion_cod, promocion_id)
  REFERENCES Promociones(promocion_cod, promocion_id)
);



--SKIP Index: fkIdx_1498

--SKIP Index: fkIdx_1507


--************************************** OrdenesDespacho

CREATE TABLE OrdenesDespacho
(
 despacho_cod        VARCHAR(30) NOT NULL ,
 despacho_id         SERIAL NOT NULL ,
 client_id           SERIAL NOT NULL ,
 doc_venta_cod       VARCHAR(30) NOT NULL ,
 doc_venta_id        SERIAL NOT NULL ,
 estado              VARCHAR(20) NOT NULL ,
 last_user_change    VARCHAR(20) NOT NULL ,
 last_data_change    DATE NOT NULL ,
 flag_last_operation CHAR(1) NOT NULL ,
 ruta_orden          TEXT NOT NULL ,
 nr_asignados        INT NOT NULL ,

 CONSTRAINT pk_702 PRIMARY KEY  (despacho_cod , despacho_id , client_id , doc_venta_cod , doc_venta_id ),
 CONSTRAINT fk_1563 FOREIGN KEY (doc_venta_cod, client_id, doc_venta_id)
  REFERENCES DocVentas(doc_venta_cod, client_id, doc_venta_id)
);



--SKIP Index: fkIdx_1563


--************************************** DocVentasxProductos

CREATE TABLE DocVentasxProductos
(
 tipo_id          SERIAL NOT NULL ,
 client_id        SERIAL NOT NULL ,
 orden_compra_id  SERIAL NOT NULL ,
 doc_venta_cod    VARCHAR(30) NOT NULL ,
 orden_compra_cod VARCHAR(20) NOT NULL ,
 doc_venta_id     SERIAL NOT NULL ,
 tipo_cod         VARCHAR(20) NOT NULL ,
 estado           VARCHAR(30) NOT NULL ,

 CONSTRAINT pk_642 PRIMARY KEY  (tipo_id , client_id , orden_compra_id , doc_venta_cod , orden_compra_cod , doc_venta_id , tipo_cod ),
 CONSTRAINT fk_639 FOREIGN KEY (tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod)
  REFERENCES OrdenesCompraxProductos(tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod),
 CONSTRAINT fk_646 FOREIGN KEY (doc_venta_cod, client_id, doc_venta_id)
  REFERENCES DocVentas(doc_venta_cod, client_id, doc_venta_id)
);



--SKIP Index: fkIdx_639

--SKIP Index: fkIdx_646


--************************************** OrdenesDespachoxRutas

CREATE TABLE OrdenesDespachoxRutas
(
 despacho_cod     VARCHAR(30) NOT NULL ,
 despacho_id      SERIAL NOT NULL ,
 ruta_despacho_id SERIAL NOT NULL ,
 client_id        SERIAL NOT NULL ,
 doc_venta_cod    VARCHAR(30) NOT NULL ,
 doc_venta_id     SERIAL NOT NULL ,
 creation_date    DATE NOT NULL ,
 usuario_cod      VARCHAR(20) NOT NULL ,
 usuario_id       SERIAL NOT NULL ,

 CONSTRAINT pk_753 PRIMARY KEY  (despacho_cod , despacho_id , ruta_despacho_id , client_id , doc_venta_cod , doc_venta_id ),
 CONSTRAINT fk_750 FOREIGN KEY (despacho_cod, despacho_id, client_id, doc_venta_cod, doc_venta_id)
  REFERENCES OrdenesDespacho(despacho_cod, despacho_id, client_id, doc_venta_cod, doc_venta_id),
 CONSTRAINT fk_757 FOREIGN KEY (ruta_despacho_id)
  REFERENCES RutasDespacho(ruta_despacho_id),
 CONSTRAINT fk_761 FOREIGN KEY (usuario_cod, usuario_id)
  REFERENCES Usuarios(usuario_cod, usuario_id)
);



--SKIP Index: fkIdx_750

--SKIP Index: fkIdx_757

--SKIP Index: fkIdx_761


--************************************** OrdenesDespachoxProductosFinal

CREATE TABLE OrdenesDespachoxProductosFinal
(
 producto_cod  VARCHAR(30) NOT NULL ,
 producto_id   SERIAL NOT NULL ,
 tipo_id       SERIAL NOT NULL ,
 client_id     SERIAL NOT NULL ,
 despacho_cod  VARCHAR(30) NOT NULL ,
 despacho_id   SERIAL NOT NULL ,
 tipo_cod      VARCHAR(20) NOT NULL ,
 doc_venta_cod VARCHAR(30) NOT NULL ,
 doc_venta_id  SERIAL NOT NULL ,
 estado        VARCHAR(30) NOT NULL ,

 CONSTRAINT pk_719 PRIMARY KEY  (producto_cod , producto_id , tipo_id , client_id , despacho_cod , despacho_id , tipo_cod , doc_venta_cod , doc_venta_id ),
 CONSTRAINT fk_723 FOREIGN KEY (despacho_cod, despacho_id, client_id, doc_venta_cod, doc_venta_id)
  REFERENCES OrdenesDespacho(despacho_cod, despacho_id, client_id, doc_venta_cod, doc_venta_id),
 CONSTRAINT fk_727 FOREIGN KEY (producto_cod, producto_id, tipo_id, tipo_cod)
  REFERENCES Productos(producto_cod, producto_id, tipo_id, tipo_cod)
);



--SKIP Index: fkIdx_723

--SKIP Index: fkIdx_727


