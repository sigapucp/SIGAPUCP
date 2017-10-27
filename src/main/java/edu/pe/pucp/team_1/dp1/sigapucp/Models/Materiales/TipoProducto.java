/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Auditoria.Auditoria;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("tiposproducto")
@IdName("tipo_id")
public class TipoProducto extends Model{
        
    
    
    public TipoProducto() {}

    public TipoProducto(StringProperty tipo_cod, Integer tipo_id, float peso, StringProperty nombre, char perecible, String descripcion, 
            String LastUserChange, Date LastDateChange, char FlagLastOperation, String estado, float longitud, float ancho,
            Integer unidad_id) {
        set("tipo_cod",tipo_cod);
        set("tipo_id",tipo_id);
        set("peso",peso);
        set("nombre",nombre);
        set("perecible",perecible);
        set("descripcion",descripcion);
        set("last_user_change",LastUserChange);
        set("last_date_change",LastDateChange);
        set("flag_last_operation",FlagLastOperation);
        set("estado",estado);
        set("longitud",longitud);
        set("ancho",ancho);
        set("unidad_id",unidad_id);
    }
    /*public static List<TipoProducto> inicializarProductos (){
        Base.open();
        TipoProducto productosLista = TipoProducto.where("tipo_cod = 'lapipilot'");
        System.out.println(productosLista);


        //
        //Boolean hayProductos = false;
        /*try{
            Base.open();
            productosLista = TipoProducto.where("tipo_cod = 'lapipilot'");

            Base.close();
        }
        catch (Exception e){
            System.out.println(e);
        }*
        return productosLista;
    }*/
    
 
   /* 
    public static List<TipoProducto> recuperarRegistros(){
        
        List<TipoProducto> registros = TipoProducto.findAll();
        return registros;
    }*/
    public static String hallarNombreTipoProducto(){       
        Base.open();
        TipoProducto tipo = TipoProducto.findById(1);
        String nombre = tipo.getTipoProd();     
        return nombre;
    }
    public static String hallarCodigoTipoProducto(){       
        Base.open();
        TipoProducto tipo = TipoProducto.findById(1);
        String codigo = tipo.getTipoCod();     
        return codigo;
    }
    public String getTipoProd(){     
        return getString("nombre");
    }
       
    public String getTipoCod(){        
        return getString("tipo_cod");
    }
    

}
