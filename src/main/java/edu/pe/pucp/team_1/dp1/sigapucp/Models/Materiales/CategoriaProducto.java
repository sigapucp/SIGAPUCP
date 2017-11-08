/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("categoriasproducto")
@IdName("categoria_id")
@Many2Many(other = TipoProducto.class, join = "categoriasxtipos", sourceFKName = "categoria_id", targetFKName = "tipo_id")
public class CategoriaProducto extends Model{
    static{
        validatePresenceOf("nombre","categoria_code");
    }
    public void asignar_atributos(String usuario,String codigo, String nombre, String descripcion){
        this.set("last_user_change",usuario);
        this.set("categoria_code",codigo);
        this.set("nombre", nombre );
        this.set("descripcion", descripcion);
        this.set("estado",ESTADO.ACTIVO.name());
        
    }
    public enum ESTADO
    {
        ACTIVO,
        INACTIVO
    }
    
}
