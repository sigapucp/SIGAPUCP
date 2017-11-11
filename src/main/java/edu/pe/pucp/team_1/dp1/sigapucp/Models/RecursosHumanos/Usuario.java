/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos;

import java.util.List;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("Usuarios")
@IdName("usuario_id")
public class Usuario extends Model{
    static {
        dateFormat("dd/MM/yyyy", "last_date_change");
    }    

    static{
        validatePresenceOf("name","contact_name","provuder_ruc","phone_number");
        validateNumericalityOf("provuder_ruc");
        validateNumericalityOf("phone_number");
        validateRegexpOf("provuder_ruc", "\\d{1,11}");
    }
    
    public static boolean autenticacion (String correo_usuario, String contrasenha){
        Usuario usuario;
        Boolean autenticado = false;
        try{          
            usuario = Usuario.findFirst("email = ? and contrasena_encriptada = ?", correo_usuario, contrasenha);            
            autenticado = usuario != null;            
        }
        catch (Exception e){
            System.out.println(e);            
        }
        return autenticado;
    }
    
    public static boolean enviaCorreo(String correo_usuario){
        Usuario usuario;
        Boolean exito = false;
        try{            
            usuario = Usuario.findFirst("email = ?", correo_usuario);            
            exito = usuario != null;
        }
        catch (Exception e){
            System.out.println(e);
        }
        return exito;
    }
    
    public static boolean tienePermiso(List<AccionxRol> permisos,Menu.MENU menu,Accion.ACCION accion) {
        return (permisos.stream().anyMatch(x->x.getInteger("menu_id").equals(Menu.MENU.getId(menu)) && x.getInteger("accion_id").equals(Accion.ACCION.getId(accion))));        
    }
      
    public Rol getRol()
    {
        return Rol.findFirst("rol_id = ?", getInteger("rol_id"));        
    }
    
    public enum ESTADO
    {
        ACTIVO,
        INACTIVO
    }
}
