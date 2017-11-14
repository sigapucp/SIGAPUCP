insertTemplate.put("#accionesxroles", "INSERT INTO public.accionesxroles(\n" +
        "            menu_id, accion_cod, rol_cod, rol_id, accion_id, solo_admin)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?);");
        insertTemplate.put("#almacenareaxys", "INSERT INTO public.almacenareaxys(\n" +
        "            almacen_xy_id, alto, estado, tipo, rack_cod, rack_id, almacen_id, \n" +
        "            almacen_cod, x, y)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
        insertTemplate.put("#almacenareazs", "INSERT INTO public.almacenareazs(\n" +
        "            almacen_z_id, almacen_xy_id, capacidad_restante, level, state, \n" +
        "            capacity)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#almacenes", "INSERT INTO public.almacenes(\n" +
        "            almacen_cod, almacen_id, nombre, es_central, largo, ancho, longitud_area, \n" +
        "            last_user_change, last_date_change, flag_last_operation, x_relativo_central, \n" +
        "            y_relativo_central)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#categoriasproducto", "INSERT INTO public.categoriasproducto(\n" +
        "            categoria_code, categoria_id, nombre, descripcion, last_user_change, \n" +
        "            last_date_change, flag_last_operation, estado)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
        insertTemplate.put("#categoriasxtipos", "INSERT INTO public.categoriasxtipos(\n" +
        "            tipo_id, categoria_code, categoria_id, tipo_cod)\n" +
        "    VALUES (?, ?, ?, ?);");
        insertTemplate.put("#clientes", "INSERT INTO public.clientes(\n" +
        "            client_id, nombre, nombre_contacto, telef_contacto, ruc, dni, \n" +
        "            last_user_change, last_date_change, flag_last_operation, estado, \n" +
        "            tipo_cliente, direccion_despacho, direccion_facturacion, departamento)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?);");       
        insertTemplate.put("#cotizaciones", "INSERT INTO public.cotizaciones(\n" +
        "            cotizacion_cod, client_id, cotizacion_id, fecha_emision, estado, \n" +
        "            total, last_user_change, last_date_change, flag_last_operation, \n" +
        "            moneda_id, igv)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#cotizacionesxproductosxfletes", "INSERT INTO public.cotizacionesxproductosxfletes(\n" +
        "            tipo_id, client_id, cotizacion_cod, cotizacion_id, flete_id, \n" +
        "            tipo_cod, flete_code, valor_flete)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
          insertTemplate.put("#cotizacionesxproductosxpromocioness", "INSERT INTO public.cotizacionesxproductosxpromociones(\n" +
        "            promocion_cod, promocion_id, tipo_id, client_id, cotizacion_cod, \n" +
        "            cotizacion_id, tipo_cod, valor_descuento)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
        insertTemplate.put("#cotizacionxproductos", "INSERT INTO public.cotizacionxproductos(\n" +
        "            tipo_id, client_id, cotizacion_cod, cotizacion_id, tipo_cod, \n" +
        "            cantidad, subtotal_previo, subtotal_final, precio_unitario, descuento, \n" +
        "            flete, cantidad_descuento_disponible)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#docventas", "INSERT INTO public.docventas(\n" +
        "            doc_venta_cod, client_id, doc_venta_id, estado, last_user_change, \n" +
        "            flag_last_operation, fecha_emision, guia_id, guia_cod, tipo, \n" +
        "            last_date_change)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#errorlog", "INSERT INTO public.errorlog(\n" +
        "            error_cod, error_type_id, error_id, usuario_cod, usuario_id, \n" +
        "            descripcion, menu_id, accion_cod, rol_cod, rol_id, accion_id, \n" +
        "            tiempo)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#fletes", "INSERT INTO public.fletes(\n" +
        "            flete_id, flete_code, fecha_inicio, fecha_fin, prioridad, es_categoria, \n" +
        "            tipo, estado, tipo_id, categoria_code, categoria_id, tipo_cod, \n" +
        "            moneda_id, valor)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#lotes", "INSERT INTO public.lotes(\n" +
        "            lote_cod, lote_id, fecha_entrada, proveedor_ruc, proveedor_id)\n" +
        "    VALUES (?, ?, ?, ?, ?);");
        insertTemplate.put("#notascredito", "INSERT INTO public.notascredito(\n" +
        "            credit_note_cod, client_id, credit_note_id, last_user_change, \n" +
        "            last_date_change, flag_last_operation, explicacion, fecha_emision, \n" +
        "            orden_entrada_id, orden_entrada_cod)\n" +
        "    VALUES (?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#ordenescompra", "INSERT INTO public.ordenescompra(\n" +
        "            orden_compra_cod, client_id, orden_compra_id, fecha_emision, \n" +
        "            total, last_user_change, last_date_change, flag_last_operation, \n" +
        "            usuario_cod, usuario_id, moneda_id, cotizacion_cod, cotizacion_id, \n" +
        "            igv, estado, direccion_despacho, direccion_facturacion, departamento, \n" +
        "            tipo)\n" +
        "    VALUES (?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#ordenescompraxproductos", "INSERT INTO public.ordenescompraxproductos(\n" +
        "            tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod, \n" +
        "            cantidad, subtotal_previo, subtotal_final, precio_unitario, descuento, \n" +
        "            flete, cantidad_descuento_disponible, reservado, cantidad_en_envios)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?);");
        insertTemplate.put("#ordenescompraxproductosxenvio", "INSERT INTO public.ordenescompraxproductosxenvio(\n" +
        "            orden_compra_cod, client_id, orden_compra_id, tipo_id, tipo_cod, \n" +
        "            envio_id, envio_cod, cantidad)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
        insertTemplate.put("#ordenescompraxproductosxfletes", "INSERT INTO public.ordenescompraxproductosxfletes(\n" +
        "            tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod, \n" +
        "            flete_id, flete_code, valor_flete)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
        insertTemplate.put("#ordenescompraxproductosxpromociones", "INSERT INTO public.ordenescompraxproductosxpromociones(\n" +
        "            tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod, \n" +
        "            promocion_cod, promocion_id, valor_desc)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
        insertTemplate.put("#ordenesentrada", "INSERT INTO public.ordenesentrada(\n" +
        "            orden_entrada_cod, orden_entrada_id, fecha_emision, last_user_change, \n" +
        "            last_date_change, flag_last_operation, estado, descripcion, tipo, \n" +
        "            provuder_ruc, proveedor_id, doc_venta_cod, client_id, doc_venta_id)\n" +
        "    VALUES (?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?);");
        insertTemplate.put("#ordenesentradaxproductos", "INSERT INTO public.ordenesentradaxproductos(\n" +
        "            orden_entrada_id, orden_entrada_cod, tipo_cod, tipo_id, estado, \n" +
        "            cantidad, ingresado)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#ordenessalida", "INSERT INTO public.ordenessalida(\n" +
        "            salida_cod, salida_id, estado, last_user_change, last_data_change, \n" +
        "            flag_last_operation, ruta_orden, nr_asignados, descripcion, tipo)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?);");
        insertTemplate.put("#ordenessalidaxenvio", "INSERT INTO public.ordenessalidaxenvio(\n" +
        "            orden_compra_cod, client_id, orden_compra_id, salida_cod, salida_id, \n" +
        "            envio_id, envio_cod)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#ordenessalidaxproductos", "INSERT INTO public.ordenessalidaxproductos(\n" +
        "            salida_cod, salida_id, tipo_cod, tipo_id, cantidad, despachado, \n" +
        "            tipo_salida)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#ordenessalidaxproductosfinal", "INSERT INTO public.ordenessalidaxproductosfinal(\n" +
        "            producto_cod, producto_id, tipo_cod, orden_entrada_cod, orden_entrada_id, \n" +
        "            tipo_id, salida_cod, salida_id, estado)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?);");
        insertTemplate.put("#precios", "INSERT INTO public.precios(\n" +
        "            tipo_id, tipo_cod, precio_id, precio, fecha_inicio, fecha_fin, \n" +
        "            es_default, moneda_id)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#productos", "INSERT INTO public.productos(\n" +
        "            producto_cod, producto_id, tipo_cod, orden_entrada_cod, orden_entrada_id, \n" +
        "            tipo_id, fecha_entrada, fecha_caducidad, fecha_adquirida, almacen_z_id, \n" +
        "            rack_cod, rack_id, lote_cod, lote_id, almacen_cod, almacen_id, \n" +
        "            ubicado, tipo_posicion, almacen_xy_id, estado, posicion_rack)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?);");
        insertTemplate.put("#promocionbonificaciones", "INSERT INTO public.promocionbonificaciones(\n" +
        "            promocion_cod, promocion_id, nr_comprar, nr_obtener, es_categoria_comprar, \n" +
        "            categoria_code, categoria_id, tipo_id, tipo_cod)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?);");
        insertTemplate.put("#promociones", "INSERT INTO public.promociones(\n" +
        "            promocion_cod, promocion_id, fecha_inicio, fecha_fin, prioridad, \n" +
        "            es_categoria, estado, tipo, tipo_id, categoria_code, categoria_id, \n" +
        "            tipo_cod)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#promocionporcentajes", "INSERT INTO public.promocionporcentajes(\n" +
        "            promocion_cod, promocion_id, valor_desc, concepto_desc, valor_condition, \n" +
        "            concepto_condition, relacion_condition)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#proveedores", "INSERT INTO public.proveedores(\n" +
        "            provuder_ruc, proveedor_id, name, contact_name, phone_number, \n" +
        "            last_user_change, last_date_change, flag_last_operation, status, \n" +
        "            annotation)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, \n" +
        "            ?);");
        insertTemplate.put("#racks", "INSERT INTO public.racks(\n" +
        "            rack_cod, almacen_id, rack_id, almacen_cod, longitud, altura, \n" +
        "            es_uniforme, x_ancla1, y_ancla1, x_ancla2, y_ancla2, estado, \n" +
        "            tipo, capacidad)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#accionesxroles", "INSERT INTO public.rutasdespacho(\n" +
        "            ruta_despacho_id, ruta_orden, distancia_recorrida, simulacion_id)\n" +
        "    VALUES (?, ?, ?, ?);");
        insertTemplate.put("#simulaciones", "INSERT INTO public.simulaciones(\n" +
        "            simulacion_id, capacidad_carro, nr_productos, distancia_total, \n" +
        "            punto_acopio_x, punto_acopio_y)\n" +
        "    VALUES (?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#simulacionesxdespachos", "INSERT INTO public.simulacionesxdespachos(\n" +
        "            salida_cod, salida_id, simulacion_id)\n" +
        "    VALUES (?, ?, ?);");
        insertTemplate.put("#stocks", "INSERT INTO public.stocks(\n" +
        "            tipo_id, tipo_cod, stock_real, stock_logico)\n" +
        "    VALUES (?, ?, ?, ?);");
        insertTemplate.put("#tiposerror", "IINSERT INTO public.tiposerror(\n" +
        "            error_cod, error_type_id, descripcion)\n" +
        "    VALUES (?, ?, ?);");
        insertTemplate.put("#tiposproducto", "INSERT INTO public.tiposproducto(\n" +
        "            tipo_cod, tipo_id, peso, nombre, pericible, descripcion, last_user_change, \n" +
        "            last_date_change, flag_last_operation, estado, longitud, ancho, \n" +
        "            unidad_peso_id, alto, unidad_tamano_id)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?);");
        insertTemplate.put("#roles", "INSERT INTO public.roles(\n" +
        "            rol_cod, rol_id, nombre, descripcion, last_user_change, last_date_change, \n" +
        "            flag_last_operation, estado)\n" +
        "    VALUES (?, ?, ?, ?, ?, ?, \n" +
        "            ?, ?);");
        insertTemplate.put("#usuarios", "INSERT INTO public.usuarios(\n" +
        "            usuario_cod, usuario_id, contrasena_encriptada, email, nombre, \n" +
        "            apellido, telefono, es_admin, last_user_change, last_date_change, \n" +
        "            estado, contrasena_nullable, rol_cod, rol_id, flag_last_operation)\n" +
        "    VALUES (?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?, \n" +
        "            ?, ?, ?, ?, ?);");