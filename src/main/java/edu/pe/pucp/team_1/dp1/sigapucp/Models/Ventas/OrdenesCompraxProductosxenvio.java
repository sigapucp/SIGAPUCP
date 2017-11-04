/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author alulab14
 */
@Table("ordenescompraxproductosxenvio")
//@IdName("client_id")
public class OrdenesCompraxProductosxenvio extends Model{
    public void asignar_atributos(OrdenCompraxProducto producto){
        this.set("orden_compra_cod", producto.getString("orden_compra_cod"));
        this.set("orden_compra_id", producto.getInteger("orden_compra_id"));
        this.set("client_id", producto.getInteger("client_id"));
        this.set("tipo_cod", producto.getString("tipo_cod"));
        this.set("tipo_id", producto.getInteger("tipo_id"));
    }
}
