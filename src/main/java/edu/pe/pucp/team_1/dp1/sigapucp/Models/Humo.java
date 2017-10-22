/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
/**
 *
 * @author Gustavo
 */
public class Humo {
    
    public static void prueba_humo(){
        
        Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146:5432/sigapucp_db_admin?currentSchema=PUBLIC", "sigapucp", "sigapucp");
        //Person.findAll().dump();
        /*System.out.println("================================= 1");
        LazyList<Department> depts = Department.findAll();
        depts.dump();
        //System.out.println(employees.get(1).getString("dept"));
        //employees.dump();
        //List<Department> deptos =  Department.findAll("select * from departments");
        //Base.close();
        */
        Department.findAll();
        

    }
}
