/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("accionlog")
@CompositePK({"menu_id","accion_cod","rol_cod","rol_id","accion_id"})
public class AccionLog extends Model{    
    
    public void asignar_atributos(String accion_code,int accion_id, int menu_id, String rol_cod, int rol_id, String usuario_code,int usuario_id) {
        
        LocalDate hoy_aux = LocalDate.now();
        Date hoy = Date.from(hoy_aux.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.setDate("tiempo",hoy);
        this.set("accion_cod",accion_code);
        this.set("accion_id",accion_id);
        this.set("menu_id", menu_id);
        this.set("rol_cod", rol_cod);
        this.set("rol_id",rol_id);
        this.set("usuario_cod",usuario_code);
        this.set("usuario_id",usuario_id);                      
    }
}
