/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import static edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada.ESTADO.values;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("OrdenesCompra")
@IdName("orden_compra_id")
public class OrdenCompra extends Model{
    static {
        dateFormat("dd/MM/yyyy", "fecha_emision");
    }   
    
    public enum ESTADO
    {
        PENDIENTE,
        ENDESPACHO,
        COMPLETA;
        
        private static OrdenCompra.ESTADO[] vals = values();
        public OrdenCompra.ESTADO next()
        {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }
}
