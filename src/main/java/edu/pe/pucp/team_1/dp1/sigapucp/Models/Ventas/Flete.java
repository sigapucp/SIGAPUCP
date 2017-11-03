/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("Fletes")
@IdName("flete_id")
public class Flete extends Model{
    
    public enum ZONA
    {
        Amazonas,
        Ancash,
        Apurimac,
        Arequipa,
        Ayacucho,
        Cajamarca,
        Callao,
        Cuzco,
        Huancavelica,
        Huanuco,
        Ica,
        Junin,
        LaLibertad,
        Lambayeque,
        Lima,
        Loreto,
        MadredeDios,
        Moquegua,
        Pasco,
        Piura,
        Puno,
        SanMartin,
        Tacna,
        Tumbes,
        Ucayali;
    }
    
    public enum TIPO
    {
        VOLUMEN,
        PESO,
        PORCENTAJE        
    }
    
    public enum ESTADO
    {
        ACTIVO,
        INACTIVO
    }
    
}
