/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("OrdenesCompraxProductos")
@CompositePK({ "orden_entrada_id", "orden_entrada_cod","client_id","tipo_id","tipo_cod" })
public class OrdenCompraxProducto extends Model{    
}
