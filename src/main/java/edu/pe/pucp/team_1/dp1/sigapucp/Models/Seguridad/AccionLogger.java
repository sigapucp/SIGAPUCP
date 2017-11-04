/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Jauma
 */
public class AccionLogger {
    
    public void logAccion(Accion.ACCION accion,Menu.MENU menu,Usuario usuario)
    {   
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        try{        
        Base.openTransaction();
        AccionLog entrada = new AccionLog();
        entrada.asignar_atributos(accion.toString(),accion.ordinal()+1,menu.ordinal()+1,usuario.getRol().getString("rol_cod") , usuario.getRol().getInteger("rol_id"), usuario.getString("usuario_cod"),usuario.getInteger("usuario_id"));
        entrada.saveIt();
        Base.commitTransaction();
        }
        catch(Exception e){
           System.out.println(e);
           Base.rollbackTransaction();
        }
    }

}
