/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("OrdenesSalida")
@IdName("salida_id")
public class OrdenSalida extends Model{
    public enum TIPO
    {
        Compra,
        Devolucion,
        Encuentro,
        Otras
    }
}
