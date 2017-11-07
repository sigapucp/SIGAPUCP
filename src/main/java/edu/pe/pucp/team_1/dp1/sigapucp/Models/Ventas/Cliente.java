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
@Table("Clientes")
@IdName("client_id")
public class Cliente extends Model{
    
    static{
        validatePresenceOf("nombre", "direccion_despacho", "direccion_facturacion");
        validateNumericalityOf("telef_contacto");
        validateNumericalityOf("dni");
        validateNumericalityOf("ruc");
    }
    
    public void asignar_atributos(String nombre, String nombre_contacto, String telf_contacto, String ruc, String dni, String tipo_cliente, String direccion_despacho, String direccion_facturacion){
        this.set("nombre", nombre );
        this.set("nombre_contacto", nombre_contacto);
        int telef_contacto = (telf_contacto.equals("")) ? 0 : Integer.parseInt(telf_contacto);
        this.set("telef_contacto",  telef_contacto);
        this.set("ruc", ruc);
        this.set("dni", dni);
        this.set("tipo_cliente", tipo_cliente);
        this.set("direccion_despacho", direccion_despacho);
        this.set("direccion_facturacion", direccion_facturacion);
        this.set("estado", "activo");
        this.set("last_user_change","user");
    }
    
    public boolean is_valid(){
        boolean match = true;
        if (!this.get("ruc").equals("")){
            match = (this.get("ruc").toString().length() == 11 );
        }
        if (!this.get("dni").equals("")){
            match = (this.get("dni").toString().length() == 8 );
        }
                
        return match;
    }
    
      public enum TIPO
    {
        PersonaNatural,
        PersonaJuridica
    }
      
    public enum ESTADO
    {
        ACTIVO,
        INACTIVO
    }      
}
