/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;

import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author Jauma
 */
@Table("AlmacenAreaZs")
@CompositePK({ "almacen_z_id", "almacen_xy_id" })
public class AlmacenAreaZ extends Model{    
}
