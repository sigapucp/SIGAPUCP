CREATE TABLE Simulaciones
(
 simulacion_id   SERIAL NOT NULL ,
 capacidad_carro DECIMAL(10,2) NOT NULL ,
 nr_productos    INT NOT NULL ,
 distancia_total DECIMAL(10,2) NOT NULL ,
 punto_acopio_x  INT NOT NULL ,
 punto_acopio_y  INT NOT NULL ,

 CONSTRAINT PK_SolucionesDespacho PRIMARY KEY  (simulacion_id )
);




--************************************** SimulacionesxDespachos

CREATE TABLE SimulacionesxDespachos
(
 salida_cod    NVARCHAR(30) NOT NULL ,
 salida_id     SERIAL NOT NULL ,
 simulacion_id SERIAL NOT NULL ,

 CONSTRAINT pk_753 PRIMARY KEY  (salida_cod , salida_id , simulacion_id ),
 CONSTRAINT fk_2009 FOREIGN KEY (simulacion_id)
  REFERENCES Simulaciones(simulacion_id),
 CONSTRAINT fk_2013 FOREIGN KEY (salida_cod, salida_id)
  REFERENCES OrdenesSalida(salida_cod, salida_id)
);



--SKIP Index: fkIdx_2009

--SKIP Index: fkIdx_2013


--************************************** RutasDespacho

CREATE TABLE RutasDespacho
(
 ruta_despacho_id    SERIAL NOT NULL ,
 ruta_orden          TEXT NOT NULL ,
 distancia_recorrida DECIMAL(10,2) NOT NULL ,
 simulacion_id       SERIAL NOT NULL ,

 CONSTRAINT pk_734 PRIMARY KEY  (ruta_despacho_id ),
 CONSTRAINT fk_2005 FOREIGN KEY (simulacion_id)
  REFERENCES Simulaciones(simulacion_id)
);