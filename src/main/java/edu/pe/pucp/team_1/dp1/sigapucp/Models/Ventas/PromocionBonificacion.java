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
@Table("PromocionBonificaciones")
public class PromocionBonificacion extends Model{
    public void asignar_atributos(String codigo, Integer compro, Integer llevo ){
        this.set("nombre", codigo );
        this.set("nr_comprar",compro);
        this.set("nr_obtener",llevo);
    }
}
