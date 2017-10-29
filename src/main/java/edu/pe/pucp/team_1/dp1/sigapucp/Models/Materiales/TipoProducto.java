/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Auditoria.Auditoria;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("tiposproducto")
@IdName("tipo_id")
public class TipoProducto extends Model{
    
    static{
        validatePresenceOf("nombre", "tipo_cod", "unidad_id");
        validateNumericalityOf("longitud");
        validateNumericalityOf("ancho");
    }    

    public void asignar_atributos(String usuario, String tipo_cod, float peso, String nombre, char perecible, String descripcion, 
            float longitud, float ancho, String unidad_id) {
        set("tipo_cod",tipo_cod);
        set("peso",peso);
        set("nombre",nombre);
        set("pericible",perecible);
        set("descripcion",descripcion);
        set("estado","activo");
        set("longitud",longitud);
        set("ancho",ancho);
        set("last_user_change",usuario);
        set("unidad_id", Integer.parseInt(unidad_id));
    }
}
