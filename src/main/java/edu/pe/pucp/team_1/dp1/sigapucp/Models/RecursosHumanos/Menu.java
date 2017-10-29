/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("Menus")
@IdName("menu_id")
@Many2Many(other = Accion.class, join = "AccionesxMenus", sourceFKName = "menu_id", targetFKName = "accion_id")
public class Menu extends Model{
    public enum MENU
    {
        NullMenu,
        Dashboard,
        Auditoria,
        ReportedeErrorres,
        Almacenes,
        Categorias,
        Productos,
        Racks,
        OrdendeEntrada,
        OrdendeDespacho,
        Clientes,
        Proformas,
        Pedidos,
        Proveedores,
        Promociones,
        Fletes,
        DocumentosdeVenta,
        NotasdeCredito,
        Usuarios,
        Roles,
        Login,
        Simulacion;

        public static int getId(MENU menu)
        {
            return menu.ordinal() + 1;
        }
        
        public static MENU getMenu(int id)
        {
            for(MENU menu: values())
            {
                if(getId(menu) == id) return menu;
            }
            return NullMenu;
        }
    }
}
