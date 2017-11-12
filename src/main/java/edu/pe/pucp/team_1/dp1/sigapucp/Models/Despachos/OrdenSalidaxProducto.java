/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author a20111628
 */
@Table("OrdenesSalidaxProductos")
@CompositePK({"salida_cod", "salida_id", "tipo_id", "tipo_cod"})
public class OrdenSalidaxProducto extends Model{
    
}
