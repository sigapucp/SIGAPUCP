/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

import static edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra.ESTADO.values;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("docventas")
@IdName("doc_venta_id")
public class DocVenta extends Model{
    public enum ESTADO
    {
        CANCELADA,
        PENDIENTE,
        COMPLETA;
        
        private static DocVenta.ESTADO[] vals = values();
        public DocVenta.ESTADO next()
        {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }    
    
    public enum TIPO
    {
        BOLETA,
        FACTURA
       
    }
}
