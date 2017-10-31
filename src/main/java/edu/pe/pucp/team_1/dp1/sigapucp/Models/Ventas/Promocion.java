/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

import java.sql.Date;
import java.time.LocalDate;
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
    static{
        dateFormat("dd/MM/yyyy","fecha_inicio");
        dateFormat("dd/MM/yyyy","fecha_fin");
    }
    
    public void asignar_atributos(String codigo,String fechaIni,String fechaFin,String prioridad,String es_categoria,String estado,String tipoPromo,String codTipCat, String idTipCat){
        this.set("promocion_cod", codigo );
        this.setDate("fecha_inicio", fechaIni);
        this.setDate("fecha_fin", fechaFin);
        int prioridad1 = (prioridad.equals("")) ? 1 : Integer.parseInt(prioridad);
        this.set("prioridad", prioridad1);
        this.set("es_categoria", es_categoria);
        this.set("estado", estado);
        this.set("tipo", tipoPromo);
        int auxidTipCat = (idTipCat.equals("")) ? 1 : Integer.parseInt(idTipCat);
        if (es_categoria.equals("S")){
            this.set("categoria_code",codTipCat);            
            this.set("categoria_id",auxidTipCat);
        } else{            
            this.set("tipo_cod",codTipCat);
            this.set("tipo_id",auxidTipCat);
        }
    }
    
    public enum TIPO{
        CANTIDAD,
        BONIFICACIÓN,
        PORCENTAJE
    }
    
    public enum ESTADO{
        ACTIVO,
        INACTIVO
    }
}
