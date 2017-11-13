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
 * @author Jauma
 */
@Table("cotizaciones")
@IdName("cotizacion_id")
public class Cotizacion extends Model{

    static{
        validatePresenceOf("nombre", "direccion_despacho", "direccion_facturacion");
        validateNumericalityOf("telef_contacto");
        validateNumericalityOf("dni");
        validateNumericalityOf("ruc");
    }
    
    static {
        dateFormat("dd/MM/yyyy", "fecha_emision");
    }     
    
    public enum ESTADO
    {
        SINPEDIDO,
        CONPEDIDO
    }
}

