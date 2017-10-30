/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;


import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
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
        validateNumericalityOf("peso");
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

