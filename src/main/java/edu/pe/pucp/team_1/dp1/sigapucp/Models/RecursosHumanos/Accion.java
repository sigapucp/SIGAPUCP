/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("acciones")
@IdName("accion_id")
public class Accion extends Model{    
    public enum ACCION
    {        
        NUL,
        LOG,
        VIW,
        CRE,
        MOD,
        DES,
        CSV,
        ROL,
        EST,
        MOV,
        MIV,
        MIC,
        MID,
        MIE,
        SIM,
        PRO,
        FLT,
        TRS;
        
        public static int getId(ACCION accion)
        {
            return accion.ordinal() + 1;
        }
    }
  
}
