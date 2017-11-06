--Usuarios

CREATE FUNCTION Usuarios_audit() RETURNS trigger AS $T_Usuarios_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Usuarios_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Usuarios_BIU BEFORE INSERT OR UPDATE ON Usuarios
FOR EACH ROW EXECUTE PROCEDURE Usuarios_audit();

--Roles

CREATE FUNCTION Roles_audit() RETURNS trigger AS $T_Roles_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Roles_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Roles_BIU BEFORE INSERT OR UPDATE ON Roles
FOR EACH ROW EXECUTE PROCEDURE Roles_audit();

--Almacenes

CREATE FUNCTION Almacenes_audit() RETURNS trigger AS $T_Almacenes_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Almacenes_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Almacenes_BIU BEFORE INSERT OR UPDATE ON Almacenes
FOR EACH ROW EXECUTE PROCEDURE Almacenes_audit();

--Clientes

CREATE FUNCTION Clientes_audit() RETURNS trigger AS $T_Clientes_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Clientes_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Clientes_BIU BEFORE INSERT OR UPDATE ON Clientes
FOR EACH ROW EXECUTE PROCEDURE Clientes_audit();

--TiposProducto

CREATE FUNCTION TiposProducto_audit() RETURNS trigger AS $T_TiposProducto_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_TiposProducto_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_TiposProducto_BIU BEFORE INSERT OR UPDATE ON TiposProducto
FOR EACH ROW EXECUTE PROCEDURE TiposProducto_audit();

--OrdenesEntrada

CREATE FUNCTION OrdenesEntrada_audit() RETURNS trigger AS $T_OrdenesEntrada_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_OrdenesEntrada_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_OrdenesEntrada_BIU BEFORE INSERT OR UPDATE ON OrdenesEntrada
FOR EACH ROW EXECUTE PROCEDURE OrdenesEntrada_audit();

--Cotizaciones

CREATE FUNCTION Cotizaciones_audit() RETURNS trigger AS $T_Cotizaciones_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Cotizaciones_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Cotizaciones_BIU BEFORE INSERT OR UPDATE ON Cotizaciones
FOR EACH ROW EXECUTE PROCEDURE Cotizaciones_audit();

--NotasCredito

CREATE FUNCTION NotasCredito_audit() RETURNS trigger AS $T_NotasCredito_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_NotasCredito_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_NotasCredito_BIU BEFORE INSERT OR UPDATE ON NotasCredito
FOR EACH ROW EXECUTE PROCEDURE NotasCredito_audit();

--OrdenesCompra

CREATE FUNCTION OrdenesCompra_audit() RETURNS trigger AS $T_OrdenesCompra_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_OrdenesCompra_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_OrdenesCompra_BIU BEFORE INSERT OR UPDATE ON OrdenesCompra
FOR EACH ROW EXECUTE PROCEDURE OrdenesCompra_audit();

--OrdenesSalida

CREATE FUNCTION OrdenesSalida_audit() RETURNS trigger AS $T_OrdenesSalida_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_OrdenesSalida_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_OrdenesSalida_BIU BEFORE INSERT OR UPDATE ON OrdenesSalida
FOR EACH ROW EXECUTE PROCEDURE OrdenesSalida_audit();

--DocVentas

CREATE FUNCTION DocVentas_audit() RETURNS trigger AS $T_DocVentas_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_DocVentas_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_DocVentas_BIU BEFORE INSERT OR UPDATE ON DocVentas
FOR EACH ROW EXECUTE PROCEDURE DocVentas_audit();

--CategoriasProducto

CREATE FUNCTION CategoriasProducto_audit() RETURNS trigger AS $T_CategoriasProducto_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_CategoriasProducto_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_CategoriasProducto_BIU BEFORE INSERT OR UPDATE ON CategoriasProducto
FOR EACH ROW EXECUTE PROCEDURE CategoriasProducto_audit();

--Proveedores

CREATE FUNCTION Proveedores_audit() RETURNS trigger AS $T_Proveedores_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Proveedores_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Proveedores_BIU BEFORE INSERT OR UPDATE ON Proveedores
FOR EACH ROW EXECUTE PROCEDURE Proveedores_audit();

--Unidades

CREATE FUNCTION Unidades_audit() RETURNS trigger AS $T_Unidades_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Unidades_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Unidades_BIU BEFORE INSERT OR UPDATE ON Unidades
FOR EACH ROW EXECUTE PROCEDURE Unidades_audit();

--Monedas

CREATE FUNCTION Monedas_audit() RETURNS trigger AS $T_Monedas_BIU$
BEGIN
  NEW.last_date_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Monedas_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Monedas_BIU BEFORE INSERT OR UPDATE ON Monedas
FOR EACH ROW EXECUTE PROCEDURE Monedas_audit();

--Envios

CREATE FUNCTION Envios_audit() RETURNS trigger AS $T_Envios_BIU$
BEGIN
  NEW.date_last_change = now();
  IF (TG_OP = 'INSERT') THEN
      NEW.flag_last_operation = '1';
      RETURN NEW;
  ELSIF (TG_OP = 'UPDATE') THEN
      NEW.flag_last_operation = '2';
      RETURN NEW;
  END IF;
  RETURN NULL;
END;
$T_Envios_BIU$ LANGUAGE plpgsql;

CREATE TRIGGER T_Envios_BIU BEFORE INSERT OR UPDATE ON Envios
FOR EACH ROW EXECUTE PROCEDURE Envios_audit();
