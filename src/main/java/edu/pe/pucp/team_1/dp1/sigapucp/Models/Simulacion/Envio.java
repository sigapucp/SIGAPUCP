/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author alulab14
 */
@Table("envios")
//@IdName("envio_id")
@CompositePK({ "envio_id", "envio_cod", "orden_compra_cod", "client_id", "orden_compra_id"})
public class Envio extends Model{
    
    public enum ESTADO
    {
        PENDIENTE,
        ENPROCESO,
        COMPLETA
    }
}
