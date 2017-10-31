/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;


import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Precio;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
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
    static{
        validatePresenceOf("nombre", "tipo_cod");
        validateNumericalityOf("longitud");
        validateNumericalityOf("ancho");
        validateNumericalityOf("alto");
        validateNumericalityOf("peso");
    }    

    public void asignar_atributos(String usuario,float peso, String nombre, char perecible, String descripcion, 
            float longitud, float ancho,float alto, Integer unidad_peso_id,Integer unidad_medida_id) {       
        set("peso",peso);
        set("nombre",nombre);
        set("pericible",perecible);
        set("descripcion",descripcion);
        set("estado",ESTADO.ACTIVO.name());
        set("longitud",longitud);
        set("ancho",ancho);
        set("alto",alto);
        set("last_user_change",usuario);
        set("unidad_peso_id", unidad_peso_id);
        set("unidad_tamano_id", unidad_medida_id);
    }
    
    public double getPrecioActual() throws Exception
    {
        List<Precio> precios = Precio.where("tipo_id = ?",getId());
        
        Date now = Date.valueOf(LocalDate.now());
        Double precioValor = 0.0;
        
        for(Precio precio:precios)
        {
            Date fIni = precio.getDate("fecha_inicio");
            Date fFin = precio.getDate("fecha_fin");
            
            if(now.after(fFin)&&now.before(fFin))
            {
                precioValor = precio.getDouble("precio");
                return precioValor;                
            }            
        }
        
        precioValor = precios.stream().findFirst().get().getDouble("precio");
        return precioValor;
    }
    
    public enum ESTADO
    {
        ACTIVO,
        INACTIVO
    }
}

