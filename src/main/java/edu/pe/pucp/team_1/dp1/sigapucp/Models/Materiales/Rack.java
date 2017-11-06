/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("Racks")
@IdName("rack_id")
@BelongsTo(parent = Almacen.class, foreignKeyName="almacen_id")
public class Rack extends Model{
    static {
        dateFormat("dd/MM/yyyy", "last_date_change");
    }
    
    public void asignarAtributosRack(String rackCod,
            int rackLongitud,
            char rackEsUniforme,
            int rackXAncla1,
            int rackYAncla1,
            int rackXAncla2,
            int rackYAncla2,
            String rackEstado)
    {
        set("rack_cod", rackCod);
        set("longitud", rackLongitud);
        set("es_uniforme", rackEsUniforme);
        set("x_ancla1", rackXAncla1);
        set("y_ancla1", rackYAncla1);
        set("x_ancla2", rackXAncla2);
        set("y_ancla2", rackYAncla2);
        set("estado", rackEstado);
        
        if(rackXAncla1==rackXAncla2)
        {
            set("tipo",TIPO.VERTICAL.name());
        }else
        {
            set("tipo",TIPO.HORIZONTAL.name());
        }
    }
    
    public String generarCodigoRack(String almacenCod) {
        String cod = almacenCod;
        int rackX1 = Integer.valueOf(String.valueOf(get("x_ancla1")));
        int rackX2 = Integer.valueOf(String.valueOf(get("x_ancla2")));
        int rackY1 = Integer.valueOf(String.valueOf(get("y_ancla1")));
        int rackY2 = Integer.valueOf(String.valueOf(get("y_ancla2")));
        
        cod = cod.concat(String.format("-X%dY%d-X%dY%d", rackX1, rackY1, rackX2, rackY2));
        return cod;
    }
    
    public enum TIPO
    {
        VERTICAL,
        HORIZONTAL
    }
}
