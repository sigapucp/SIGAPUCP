/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Jauma
 */
public class AccionLogger {
    
    public void logAccion(Base connection,Accion.ACCION accion,Usuario usuario)
    {
        try{        
        Base.openTransaction();
        AccionLog entrada = new AccionLog();
//        entrada.set("first_name", "John");
//        entrada.set("last_name", "Doe");      
        entrada.saveIt();
        Base.commitTransaction();
        }
        catch(Exception e){
           Base.rollbackTransaction();
        }
    }
}
