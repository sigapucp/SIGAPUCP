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
@Table("Proveedores")
@IdName("proveedor_id")
public class Proveedor extends Model{
    
    static{
        validatePresenceOf("nombre");
        validateNumericalityOf("provuder_ruc");
    }
    
    public void asignar_atributos(String nombre, String nombre_contacto, String telf_contacto, String ruc, String comentarios){
        this.set("name", nombre );
        this.set("contact_name", nombre_contacto);
        int telef_contacto = (telf_contacto.equals("")) ? 0 : Integer.parseInt(telf_contacto);
        this.set("phone_number",  telef_contacto);
        this.set("provuder_ruc", ruc);
        this.set("status", "activo");
        this.set("annotation", comentarios);
    }    
}
