/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("OrdenesSalida")
@IdName("salida_id")
@Many2Many(other = SimulacionBD.class, join = "SimulacionesxDespachos", sourceFKName = "salida_id", targetFKName = "simulacion_id")
public class OrdenSalida extends Model{
    static{
        validatePresenceOf("salida_cod", "tipo", "estado");
    }
    
    public enum TIPO
    {
        Venta,
        Rotura,
        Otras
    }
    
    public enum ESTADO
    {
        PENDIENTE,
        ENPROCESO,
        COMPLETA;
        private static OrdenSalida.ESTADO[] vals = values();
        public OrdenSalida.ESTADO next()
        {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }    
}
