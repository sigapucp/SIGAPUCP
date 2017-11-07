/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("Almacenes")
@IdName("almacen_id")
public class Almacen extends Model{
    static {
        dateFormat("dd/MM/yyyy", "last_date_change");
    }
    
    public void asignarAtributosAlmacenLogico(int almacen_largo, 
            int almacen_ancho, 
            int almacen_x, 
            int almacen_y, 
            int longitud_area, 
            String almacen_nombre,
            char almacen_central, 
            Usuario usuario_actual,
            ObservableList<Rack> racks)
    {
        set("nombre", almacen_nombre);
        set("largo", almacen_largo);
        set("ancho", almacen_ancho);
        set("es_central", almacen_central);
        set("longitud_area", (double) longitud_area);
        set("x_relativo_central", almacen_x);
        set("y_relativo_central", almacen_y);
        set("last_user_change", usuario_actual.get("email"));
        set("flag_last_operation", 'T');
        setDate("last_date_change", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    
    public void asignarAtributosAlmacenCentral(String almacen_nombre,
            String almacenCod,
            int almacen_largo,
            int almacen_ancho,
            double longitud_area,
            char almacen_central,
            Usuario usuario_actual) 
    {
        set("nombre", almacen_nombre);
        set("largo", almacen_largo);
        set("ancho", almacen_ancho);
        set("es_central", almacen_central);
        set("almacen_cod", almacenCod);
        set("longitud_area", longitud_area);
        
        set("x_relativo_central", 0);
        set("y_relativo_central", 0);
        set("last_user_change", usuario_actual.get("email"));
        set("flag_last_operation", 'T');
        setDate("last_date_change", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    
    public Boolean esValido(String almacen_nombre,
            String almacen_largo,
            String almacen_ancho,
            String almacen_longitud_area,
            char almacen_central,
            ObservableList<Almacen> almacenes_logicos,
            ObservableList<Rack> racks)
    {
        boolean condition = !almacen_nombre.equals("") && !almacen_largo.equals("") && !almacen_ancho.equals("") && !almacen_longitud_area.equals("") ;

        switch(almacen_central) {
            case 'T':
                System.out.println("Almacen Central");
                return condition && almacenes_logicos.size() > 0;
            case 'F':
                System.out.println("Almacen Logico");
                return condition && racks.size() > 0;
            default:
                return false;
        }
    }
    
    public void updateAtributosAlmacenLogico(String almacen_nombre,
            int almacen_largo,
            int almacen_ancho,
            double almacen_longitud_area,
            char almacen_central,
            Usuario usuario_actual,
            ObservableList<Rack> racks)
    {
        set("nombre", almacen_nombre);
        set("largo", almacen_largo);
        set("ancho", almacen_ancho);
        set("longitud_area", almacen_longitud_area);
        set("es_central", almacen_central);
        set("last_user_change", usuario_actual.get("email"));
        setDate("last_date_change", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        if(racks.size() > 0) {
            racks.forEach((rack) -> {
                if(rack.isNew()) {
                    int almacenId = Integer.valueOf(String.valueOf(get("almacen_id")));
                    String almacenCod = String.valueOf(get("almacen_cod"));

                    rack.set("rack_cod", rack.generarCodigoRack(almacenCod));
                    rack.set("almacen_id", almacenId);
                    rack.set("almacen_cod", almacenCod);
                }
                add(rack);
            });
        }
    }          
}
