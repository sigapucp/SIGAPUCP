/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad;

/**
 *
 * @author Jauma
 */
public class AccionLoggerSingleton {
    
    private static AccionLogger instance = null;
    
    public static AccionLogger getInstance()
    {
        if(instance == null)
        {
            instance = new AccionLogger();            
        }
        return instance;                            
    }        
}
