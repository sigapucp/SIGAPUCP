/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Navegacion;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;

/**
 *
 * @author Alberto Chang Lopez
 */
public class abrirModalPromoArgs extends EventArgs{
    private String codigo;
    private String id;
    
    /**
     * @param codigo the nombre_modulo to set
     */
    public void setCodigo(String codigo) {
        this.codigo =  codigo;        
    }
    
    /**
     * @param id the nombre_modulo to set
     */
    public void setId(String id) {
        this.id =  id;        
    }
    
    /**
     * @return the codigo
     */
    public String getCodigo() { 
        return codigo;
    }
    
    /**
     * @return the id
     */
    public String getId() { 
        return id;
    }
}
