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
    private String codigo_promo;
    private String id_promo;
    
    /**
     * @param codigo_promo the nombre_modulo to set
     */
    public void setCodigoPromo(String codigo_promo) {
        this.codigo_promo =  codigo_promo;        
    }
    
    /**
     * @param id_promo the nombre_modulo to set
     */
    public void setPromoId(String id_promo) {
        this.id_promo =  id_promo;        
    }
}
