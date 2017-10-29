/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Auditoria.Auditoria;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
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
        validatePresenceOf("nombre", "tipo_cod");
        validateNumericalityOf("longitud");
        validateNumericalityOf("ancho");
        validateNumericalityOf("alto");
    }    

    public void asignar_atributos(String usuario,float peso, String nombre, char perecible, String descripcion, 
            float longitud, float ancho,float alto, Integer unidad_peso_id,Integer unidad_medida_id) {       
        set("peso",peso);
        set("nombre",nombre);
        set("pericible",perecible);
        set("descripcion",descripcion);
        set("estado","activo");
        set("longitud",longitud);
        set("ancho",ancho);
        set("alto",alto);
        set("last_user_change",usuario);
        set("unidad_peso_id", unidad_peso_id);
        set("unidad_tamano_id", unidad_medida_id);
    }
    
    public enum ESTADO
    {
        ACTIVO,
        INACTIVO
    }
}

