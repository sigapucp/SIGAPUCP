/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos;

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
    
    public static boolean autenticacion (String correo_usuario, String contrasenha){
        System.out.println("query");
        Usuario usuario;
        Boolean autenticado = false;
        try{
            Base.open();
            usuario = Usuario.findFirst("email = ? and contrasena_encriptada = ?", correo_usuario, contrasenha);
            System.out.println(usuario.exists());
            autenticado = usuario.exists();
        }
        catch (Exception e){
            System.out.println(e);
            //Base.rollbackTransaction();
        }
        return autenticado;
    }
    
    public static boolean enviaCorreo(String correo_usuario){
        Usuario usuario;
        Boolean exito = false;
        try{
            Base.open();
            usuario = Usuario.findFirst("email = ?", correo_usuario);
            System.out.println(usuario.exists());
            exito = usuario.exists();
        }
        catch (Exception e){
            System.out.println(e);
            //Base.rollbackTransaction();
        }
        return exito;
    }
}
