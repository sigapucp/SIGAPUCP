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
@Table("PromocionCantidades")
public class PromocionCantidad extends Model{
    public void asignar_atributos(String codigo,String idPadre, Integer compro, Integer llevo, String flag_categoria,String codTipCat, String idTipCat ){
        this.set("promocion_cod", codigo );
        int id = (idPadre.equals("")) ? 0 : Integer.parseInt(idPadre);
        this.set("promocion_cod", id );  
        this.set("nr_comprar",compro);
        this.set("nr_obtener",llevo);
        this.set("es_categoria_obtener",flag_categoria);
        if (flag_categoria.equals("S")){
            this.set("categoria_code",codTipCat);
            this.set("categoria_id",idTipCat);
        } else{
            this.set("tipo_cod",codTipCat);
            this.set("tipo_id",idTipCat);
        }
    }
}
