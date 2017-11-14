/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import static edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra.ESTADO.values;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("guiasremision")
@IdName("guia_id")
public class GuiaRemision extends Model{
    static{
        validatePresenceOf("client_id", "placa_vehiculo", "marca_vehiculo", "punto_partida", "punto_llegada", "fecha_inicio_traslado");
    }        
 
    public enum ESTADO
    {
        CANCELADA,
        ENPROCESO,
        COMPLETA;
        private static GuiaRemision.ESTADO[] vals = values();
        public GuiaRemision.ESTADO next()
        {
            return vals[(this.ordinal()+1) % vals.length];
        }        
    }        
}
