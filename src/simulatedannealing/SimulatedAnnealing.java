/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Jauma
 */
public class SimulatedAnnealing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        int nProd;
        List<Double> pesos;
        double [][] distancias;
        
        try(BufferedReader br = new BufferedReader(new FileReader("route.txt"))) {
            StringBuilder sb = new StringBuilder();
            
            String line = br.readLine();            
            nProd = Integer.valueOf(line);
            
            line = br.readLine();
            
            List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
            pesos = items.stream().map(Double::parseDouble).collect(Collectors.toList());
            
            distancias = new double[nProd+1][nProd+1];
            
            for(int i = 0; i < nProd; i++)
            {
                line = br.readLine();
                items = Arrays.asList(line.split("\\s*,\\s*"));
                
                int j = i + 1;
                double valor = 0.0;
                for(String item : items)
                {
                     valor = Double.valueOf(item);
                     distancias[i][j] = valor;
                     distancias[j][i] = valor;                    
                    j++;
                }
            }
                        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
        }                
        
        AlgorithmSA simulatedA = new AlgorithmSA(4, 3, distancias,pesos, 10);
                
        simulatedA.CorrerAlgoritmo();
        
        int estadoSize = simulatedA.estadoProblema.size();
        for (Ruta ruta : simulatedA.rutas)
        {
            ruta.RecalcularDistancia();
            ruta.ImprimirRuta();
            ruta.ImprimirCosto();
            System.out.println();
        }
        
        for(int i = 0;i<nProd + 1;i++)
        {
            for(int j = 0;j<nProd + 1;j++)
            {
                System.out.print(distancias[i][j] + " ");
            }
            System.out.println();
        }
    }    
}
