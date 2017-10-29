/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Alberto
 */
@Table("PromocionPorcentajes")
public class PromocionPorcentaje extends Model{
    public void asignar_atributos(String codigo, Integer cantidadPorc, String concepto){
        this.set("nombre", codigo );
        this.set("valor_desc",  cantidadPorc);
        this.set("concepto_desc", concepto);
    }
}
