insertTemplate.put("#accionesxroles", "INSERT INTO public.accionesxroles(" +
        "            menu_id, accion_cod, rol_cod, rol_id, accion_id, solo_admin)" +
        "    VALUES (?, ?, ?, ?, ?, ?);");
        insertTemplate.put("#almacenareaxys", "INSERT INTO public.almacenareaxys(" +
        "            almacen_xy_id, alto, estado, tipo, rack_cod, rack_id, almacen_id, " +
        "            almacen_cod, x, y)" +
        "    VALUES (?, ?, ?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
        insertTemplate.put("#almacenareazs", "INSERT INTO public.almacenareazs(" +
        "            almacen_z_id, almacen_xy_id, capacidad_restante, level, state, " +
        "            capacity)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#almacenes", "INSERT INTO public.almacenes(" +
        "            almacen_cod, almacen_id, nombre, es_central, largo, ancho, longitud_area, " +
        "            last_user_change, last_date_change, flag_last_operation, x_relativo_central, " +
        "            y_relativo_central)" +
        "    VALUES (?, ?, ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#categoriasproducto", "INSERT INTO public.categoriasproducto(" +
        "            categoria_code, categoria_id, nombre, descripcion, last_user_change, " +
        "            last_date_change, flag_last_operation, estado)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
        insertTemplate.put("#categoriasxtipos", "INSERT INTO public.categoriasxtipos(" +
        "            tipo_id, categoria_code, categoria_id, tipo_cod)" +
        "    VALUES (?, ?, ?, ?);");
        insertTemplate.put("#clientes", "INSERT INTO public.clientes(" +
        "            client_id, nombre, nombre_contacto, telef_contacto, ruc, dni, " +
        "            last_user_change, last_date_change, flag_last_operation, estado, " +
        "            tipo_cliente, direccion_despacho, direccion_facturacion, departamento)" +
        "    VALUES (?, ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, " +
        "            ?, ?, ?, ?);");       
        insertTemplate.put("#cotizaciones", "INSERT INTO public.cotizaciones(" +
        "            cotizacion_cod, client_id, cotizacion_id, fecha_emision, estado, " +
        "            total, last_user_change, last_date_change, flag_last_operation, " +
        "            moneda_id, igv)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#cotizacionesxproductosxfletes", "INSERT INTO public.cotizacionesxproductosxfletes(" +
        "            tipo_id, client_id, cotizacion_cod, cotizacion_id, flete_id, " +
        "            tipo_cod, flete_code, valor_flete)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
          insertTemplate.put("#cotizacionesxproductosxpromocioness", "INSERT INTO public.cotizacionesxproductosxpromociones(" +
        "            promocion_cod, promocion_id, tipo_id, client_id, cotizacion_cod, " +
        "            cotizacion_id, tipo_cod, valor_descuento)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
        insertTemplate.put("#cotizacionxproductos", "INSERT INTO public.cotizacionxproductos(" +
        "            tipo_id, client_id, cotizacion_cod, cotizacion_id, tipo_cod, " +
        "            cantidad, subtotal_previo, subtotal_final, precio_unitario, descuento, " +
        "            flete, cantidad_descuento_disponible)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#docventas", "INSERT INTO public.docventas(" +
        "            doc_venta_cod, client_id, doc_venta_id, estado, last_user_change, " +
        "            flag_last_operation, fecha_emision, guia_id, guia_cod, tipo, " +
        "            last_date_change)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#errorlog", "INSERT INTO public.errorlog(" +
        "            error_cod, error_type_id, error_id, usuario_cod, usuario_id, " +
        "            descripcion, menu_id, accion_cod, rol_cod, rol_id, accion_id, " +
        "            tiempo)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#fletes", "INSERT INTO public.fletes(" +
        "            flete_id, flete_code, fecha_inicio, fecha_fin, prioridad, es_categoria, " +
        "            tipo, estado, tipo_id, categoria_code, categoria_id, tipo_cod, " +
        "            moneda_id, valor)" +
        "    VALUES (?, ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#lotes", "INSERT INTO public.lotes(" +
        "            lote_cod, lote_id, fecha_entrada, proveedor_ruc, proveedor_id)" +
        "    VALUES (?, ?, ?, ?, ?);");
        insertTemplate.put("#notascredito", "INSERT INTO public.notascredito(" +
        "            credit_note_cod, client_id, credit_note_id, last_user_change, " +
        "            last_date_change, flag_last_operation, explicacion, fecha_emision, " +
        "            orden_entrada_id, orden_entrada_cod)" +
        "    VALUES (?, ?, ?, ?, " +
        "            ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#ordenescompra", "INSERT INTO public.ordenescompra(" +
        "            orden_compra_cod, client_id, orden_compra_id, fecha_emision, " +
        "            total, last_user_change, last_date_change, flag_last_operation, " +
        "            usuario_cod, usuario_id, moneda_id, cotizacion_cod, cotizacion_id, " +
        "            igv, estado, direccion_despacho, direccion_facturacion, departamento, " +
        "            tipo)" +
        "    VALUES (?, ?, ?, ?, " +
        "            ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#ordenescompraxproductos", "INSERT INTO public.ordenescompraxproductos(" +
        "            tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod, " +
        "            cantidad, subtotal_previo, subtotal_final, precio_unitario, descuento, " +
        "            flete, cantidad_descuento_disponible, reservado, cantidad_en_envios)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?);");
        insertTemplate.put("#ordenescompraxproductosxenvio", "INSERT INTO public.ordenescompraxproductosxenvio(" +
        "            orden_compra_cod, client_id, orden_compra_id, tipo_id, tipo_cod, " +
        "            envio_id, envio_cod, cantidad)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
        insertTemplate.put("#ordenescompraxproductosxfletes", "INSERT INTO public.ordenescompraxproductosxfletes(" +
        "            tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod, " +
        "            flete_id, flete_code, valor_flete)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
        insertTemplate.put("#ordenescompraxproductosxpromociones", "INSERT INTO public.ordenescompraxproductosxpromociones(" +
        "            tipo_id, client_id, orden_compra_id, orden_compra_cod, tipo_cod, " +
        "            promocion_cod, promocion_id, valor_desc)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
        insertTemplate.put("#ordenesentrada", "INSERT INTO public.ordenesentrada(" +
        "            orden_entrada_cod, orden_entrada_id, fecha_emision, last_user_change, " +
        "            last_date_change, flag_last_operation, estado, descripcion, tipo, " +
        "            provuder_ruc, proveedor_id, doc_venta_cod, client_id, doc_venta_id)" +
        "    VALUES (?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?);");
        insertTemplate.put("#ordenesentradaxproductos", "INSERT INTO public.ordenesentradaxproductos(" +
        "            orden_entrada_id, orden_entrada_cod, tipo_cod, tipo_id, estado, " +
        "            cantidad, ingresado)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#ordenessalida", "INSERT INTO public.ordenessalida(" +
        "            salida_cod, salida_id, estado, last_user_change, last_data_change, " +
        "            flag_last_operation, ruta_orden, nr_asignados, descripcion, tipo)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?);");
        insertTemplate.put("#ordenessalidaxenvio", "INSERT INTO public.ordenessalidaxenvio(" +
        "            orden_compra_cod, client_id, orden_compra_id, salida_cod, salida_id, " +
        "            envio_id, envio_cod)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#ordenessalidaxproductos", "INSERT INTO public.ordenessalidaxproductos(" +
        "            salida_cod, salida_id, tipo_cod, tipo_id, cantidad, despachado, " +
        "            tipo_salida)" +
        "    VALUES (?, ?, ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#ordenessalidaxproductosfinal", "INSERT INTO public.ordenessalidaxproductosfinal(" +
        "            producto_cod, producto_id, tipo_cod, orden_entrada_cod, orden_entrada_id, " +
        "            tipo_id, salida_cod, salida_id, estado)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?);");
        insertTemplate.put("#precios", "INSERT INTO public.precios(" +
        "            tipo_id, tipo_cod, precio_id, precio, fecha_inicio, fecha_fin, " +
        "            es_default, moneda_id)" +
        "    VALUES (?, ?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#productos", "INSERT INTO public.productos(" +
        "            producto_cod, producto_id, tipo_cod, orden_entrada_cod, orden_entrada_id, " +
        "            tipo_id, fecha_entrada, fecha_caducidad, fecha_adquirida, almacen_z_id, " +
        "            rack_cod, rack_id, lote_cod, lote_id, almacen_cod, almacen_id, " +
        "            ubicado, tipo_posicion, almacen_xy_id, estado, posicion_rack)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?);");
        insertTemplate.put("#promocionbonificaciones", "INSERT INTO public.promocionbonificaciones(" +
        "            promocion_cod, promocion_id, nr_comprar, nr_obtener, es_categoria_comprar, " +
        "            categoria_code, categoria_id, tipo_id, tipo_cod)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?);");
        insertTemplate.put("#promociones", "INSERT INTO public.promociones(" +
        "            promocion_cod, promocion_id, fecha_inicio, fecha_fin, prioridad, " +
        "            es_categoria, estado, tipo, tipo_id, categoria_code, categoria_id, " +
        "            tipo_cod)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#promocionporcentajes", "INSERT INTO public.promocionporcentajes(" +
        "            promocion_cod, promocion_id, valor_desc, concepto_desc, valor_condition, " +
        "            concepto_condition, relacion_condition)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#proveedores", "INSERT INTO public.proveedores(" +
        "            provuder_ruc, proveedor_id, name, contact_name, phone_number, " +
        "            last_user_change, last_date_change, flag_last_operation, status, " +
        "            annotation)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, " +
        "            ?);");
        insertTemplate.put("#racks", "INSERT INTO public.racks(" +
        "            rack_cod, almacen_id, rack_id, almacen_cod, longitud, altura, " +
        "            es_uniforme, x_ancla1, y_ancla1, x_ancla2, y_ancla2, estado, " +
        "            tipo, capacidad)" +
        "    VALUES (?, ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#accionesxroles", "INSERT INTO public.rutasdespacho(" +
        "            ruta_despacho_id, ruta_orden, distancia_recorrida, simulacion_id)" +
        "    VALUES (?, ?, ?, ?);");
        insertTemplate.put("#simulaciones", "INSERT INTO public.simulaciones(" +
        "            simulacion_id, capacidad_carro, nr_productos, distancia_total, " +
        "            punto_acopio_x, punto_acopio_y)" +
        "    VALUES (?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#simulacionesxdespachos", "INSERT INTO public.simulacionesxdespachos(" +
        "            salida_cod, salida_id, simulacion_id)" +
        "    VALUES (?, ?, ?);");
        insertTemplate.put("#stocks", "INSERT INTO public.stocks(" +
        "            tipo_id, tipo_cod, stock_real, stock_logico)" +
        "    VALUES (?, ?, ?, ?);");
        insertTemplate.put("#tiposerror", "IINSERT INTO public.tiposerror(" +
        "            error_cod, error_type_id, descripcion)" +
        "    VALUES (?, ?, ?);");
        insertTemplate.put("#tiposproducto", "INSERT INTO public.tiposproducto(" +
        "            tipo_cod, tipo_id, peso, nombre, pericible, descripcion, last_user_change, " +
        "            last_date_change, flag_last_operation, estado, longitud, ancho, " +
        "            unidad_peso_id, alto, unidad_tamano_id)" +
        "    VALUES (?, ?, ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?, ?, ?);");
        insertTemplate.put("#roles", "INSERT INTO public.roles(" +
        "            rol_cod, rol_id, nombre, descripcion, last_user_change, last_date_change, " +
        "            flag_last_operation, estado)" +
        "    VALUES (?, ?, ?, ?, ?, ?, " +
        "            ?, ?);");
        insertTemplate.put("#usuarios", "INSERT INTO public.usuarios(" +
        "            usuario_cod, usuario_id, contrasena_encriptada, email, nombre, " +
        "            apellido, telefono, es_admin, last_user_change, last_date_change, " +
        "            estado, contrasena_nullable, rol_cod, rol_id, flag_last_operation)" +
        "    VALUES (?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?, " +
        "            ?, ?, ?, ?, ?);");