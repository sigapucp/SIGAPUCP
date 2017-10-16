/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.spring.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author herbert
 */
@Configuration
public class AppJavaConfig {
    
    @Bean(name = "stringPrintWriter") @Scope("prototype")
    
    public PrintWriter printWriter(){
        // useful when dumping stack trace to file
        return new PrintWriter(new StringWriter());
    }
}
