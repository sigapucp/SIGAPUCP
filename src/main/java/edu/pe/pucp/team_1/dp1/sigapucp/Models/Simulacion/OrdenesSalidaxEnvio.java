/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Gustavo
 */
@Table("ordenessalidaxenvio")
@CompositePK({"orden_compra_id","orden_compra_cod","client_id", "salida_cod", "salida_id", "envio_id", "envio_cod"})

public class OrdenesSalidaxEnvio extends Model{
    
}
