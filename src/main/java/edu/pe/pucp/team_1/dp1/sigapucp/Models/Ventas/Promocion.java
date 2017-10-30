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
 * @author Alberto
 */
@Table("Promociones")
@IdName("promocion_id")
public class Promocion extends Model{
    public void asignar_atributos(String codigo,String fechaIni,String fechaFin,String prioridad,String es_categoria,String estado,String tipoPromo,String codTipCat){
        this.set("promocion_cod", codigo );
        this.set("fecha_inicio", fechaIni);
        this.set("fecha_fin", fechaFin);
        int prioridad1 = (prioridad.equals("")) ? 1 : Integer.parseInt(prioridad);
        this.set("prioridad", prioridad1);
        this.set("es_categoria", es_categoria);
        this.set("estado", estado);
        this.set("tipo", tipoPromo);
        if (es_categoria.equals("S")){
            this.set("categoria_code",codTipCat);
        } else{
            this.set("tipo_cod",codTipCat);
        }
    }
}
