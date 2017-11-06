/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Jauma
 */
public class EstadoProblema {
    
    private ProductoNodo nodoInicial;
    private ProductoNodo nodoFinal;
    private int nrNodos;
    
    public EstadoProblema()
    {
        nodoInicial = null;
        nodoFinal = null;
        nrNodos = 0;
    }       
    
    public ProductoNodo GetNodoInicial()
    {
        return nodoInicial;
    }
    
    public void SetNodoInicial(ProductoNodo nodo)
    {
        nodoInicial = nodo;        
    }
    
    public ProductoNodo GetNodoFinal()
    {
        return nodoFinal;
    }
    
    public void SetNodoFinal(ProductoNodo nodo)
    {
        nodoFinal = nodo;
    }
    
     public void addAtStart(ProductoNodo nodo)
    {
        if(nrNodos == 0){
            nodoInicial = nodo;
            nodoFinal = nodo;            
        }else{
            nodo.sig = nodoInicial;
            nodoInicial.ant = nodo;
            nodoInicial = nodo;
        }
        nrNodos++;        
    }
    
    public void addAtEnd(ProductoNodo nodo)
    {
        if(nrNodos == 0)
        {
            nodoInicial = nodo;
            nodoFinal = nodo;
        }else{
            nodoFinal.sig = nodo;
            nodo.ant = nodoFinal;
            nodoFinal = nodo;
        }
        nrNodos++;
    }
    
    public void addAfter(ProductoNodo anterior,ProductoNodo nuevo)
    {
        if(anterior==nodoFinal) 
        {
            addAtEnd(nuevo);
            return;
        }
        
        nuevo.sig = anterior.sig;
        nuevo.ant = anterior;
        
        if(nuevo.sig!=null) nuevo.sig.ant = nuevo;
        anterior.sig = nuevo;     
        nrNodos++;
    }
    
    public void addBefore(ProductoNodo siguiente,ProductoNodo nuevo)
    {
        if(siguiente==nodoInicial) 
        {
            addAtStart(nuevo);
            return;
        }
        
        nuevo.sig = siguiente;
        nuevo.ant = siguiente.ant;
        
        if(nuevo.ant!=null) nuevo.ant.sig = nuevo;
        siguiente.ant = nuevo; 
        nrNodos++;
    }
    
    public void deleteFromStart()
    {
        if(nrNodos != 0)
        {
            nodoInicial = nodoInicial.sig;
            nrNodos--;            
        }        
    }
    
    public void deleteFromEnd()
    {
        if(nrNodos !=0)
        {
            if(nrNodos == 1)
            {
                deleteFromStart();
            }else
            {
                ProductoNodo nodoTemp = nodoFinal.ant;
                nodoFinal = nodoTemp;
                nodoFinal.sig = null;
                nrNodos--;                
            }            
        }
    }
    
     
     public int size()
    {
        return nrNodos;
    }
    
    public ProductoNodo get(int index)
    {
        if(index<0||index>=nrNodos) return null;
        ProductoNodo temp = nodoInicial;        
        for(int i = 0;i<index;i++)
        {
            temp = temp.sig;            
        }
        return temp;
    }
    
    public int indexOf(ProductoNodo nodo)
    {
        int index = 0;
        ProductoNodo temp = nodoInicial;
        while(temp != null && temp != nodo)
        {
            temp = temp.sig;
            index++;
        }
        
        if(temp == null) return -1;
        return index;        
    }
    
    public void insert(int index, ProductoNodo nodo)
    {       
        if(index >= nrNodos) index = nrNodos - 1;        
        if(index == 0)
        {
            addAtStart(nodo);
            return;        
        }                
        if(index == (nrNodos - 1))
        {
            addAtEnd(nodo);
            return;
        }
                       
        ProductoNodo temp = nodoInicial;
        ProductoNodo antNodo = nodoInicial;
        
        for(int i = 1;i<index;i++)
        {
            antNodo = temp;
            temp = temp.sig;
        }       
        antNodo.sig = nodo;
        nodo.sig = temp;
        temp.ant = nodo;                
    }
    
    public void deleteAt(int index)
    {
        if(index >= nrNodos) index = nrNodos - 1;        
        if(index == 0)
        {
            deleteFromStart();
            return;        
        }                
        if(index == (nrNodos - 1))
        {
            deleteFromEnd();
            return;
        }
                       
        ProductoNodo temp = nodoInicial;
        ProductoNodo antNodo = nodoInicial;
        
        for(int i = 1;i<index;i++)
        {
            antNodo = temp;
            temp = temp.sig;
        }       
        
        antNodo.sig = temp;        
        temp.ant = antNodo;                        
    }
    
    public void deleteNode(ProductoNodo node)
    {
        ProductoNodo prevNode = node.ant;
        ProductoNodo nextNode = node.sig;
        
        if(prevNode == null)
        {
            nodoInicial = null;
            nodoInicial = nextNode;
            nextNode.ant = null;            
        }else if(nextNode == null)
        {
            nodoFinal = null;
            nodoFinal = prevNode;
            prevNode.sig = null;
            return;
        }else 
        {
            node = null;
            prevNode.sig = nextNode;
            nextNode.ant = prevNode;
        }
        nrNodos--;                        
    }
    
    public void Swap(ProductoNodo nodo1, ProductoNodo nodo2) {
        if (nodo1 == nodo2) {
            return;
        }

        if (nodo1.sig == nodo2) {
            nodo1.sig = nodo2.sig;
            nodo2.ant = nodo1.ant;

            if (nodo1.sig != null) {
                nodo1.sig.ant = nodo1;
            }

            if (nodo2.ant != null) {
                nodo2.ant.sig = nodo2;
            }

            nodo2.sig = nodo1;
            nodo1.ant = nodo2;
        } else {
            ProductoNodo tempAnt = nodo2.ant;
            ProductoNodo tempSig = nodo2.sig;

            nodo2.ant = nodo1.ant;
            nodo2.sig = nodo1.sig;

            nodo1.ant = tempAnt;
            nodo1.sig = tempSig;

            if (nodo2.sig != null) {
                nodo2.sig.ant = nodo2;
            }
            if (nodo2.ant != null) {
                nodo2.ant.sig = nodo2;
            }

            if (nodo1.sig != null) {
                nodo1.sig.ant = nodo1;
            }
            if (nodo1.ant != null) {
                nodo1.ant.sig = nodo1;
            }
        }
    }         
     
    public void TwoOpt(ProductoNodo nodo1, ProductoNodo nodo2, ArrayList<Ruta> rutas,int ruta1Ind,int ruta2Ind) throws Exception
    {             
        if (nodo1 == nodo2) {
            return;
        }                                   
        ProductoNodo temp1 = nodo1;
        ProductoNodo temp2 = nodo2;
        ProductoNodo next1;
        ProductoNodo next2;             
          
        int nRuta = 0;
        try {                       
            do {            
                next1 = temp1.sig;
                next2 = temp2.ant;                         
                Swap(temp1, temp2);              
                if(temp2.sig == temp1) break;
                temp1 = next1;
                temp2 = next2;                  
            } while (temp1 != temp2);
                        
            if(ruta1Ind == ruta2Ind) return;          
            ProductoNodo temp = nodoInicial.sig;                                           
            Ruta ruta = rutas.get(nRuta);
            Ruta sigRuta = (++nRuta < rutas.size()) ? rutas.get(nRuta) : null;
            while(temp!=nodoFinal)
            {                                
                if(temp.EsDeposito())
                {
                    if(ruta!= null)
                    {                       
                         ruta.SetNodoActual(temp);
                    }else
                    {
                        throw new Exception();                        
                    }
                    
                    if(sigRuta != null) sigRuta.SetNodoInicial(temp);                    
                    ruta = sigRuta;
                    sigRuta = (++nRuta < rutas.size()) ? rutas.get(nRuta) : null;
                }         
                temp = temp.sig;
            }                            
        } catch (Exception e) {   
            throw new Exception("TwoOpt: " + e.getMessage());
        }        
    }
    
    public Boolean CheckConsistency(ArrayList<Ruta> rutas)
    {
        Boolean a = false;
        try {
            int prodCount = 0;
            for(Ruta ruta : rutas)
            {
                if(!ruta.GetNodoInicial().EsDeposito())
                {
                    a = true;
                }

                if(!ruta.GetNodoActual().EsDeposito())
                {
                    a = true;
                }

                ProductoNodo temp = ruta.GetNodoInicial();
                while(temp!=ruta.GetNodoActual())
                {
                     if(!temp.EsDeposito()) prodCount++;      
                    temp = temp.sig;                                 
                }                    
                if(ruta.GetNrPuntos()<=1)
                {
                    a = true;
                }
            }  
            
            if(prodCount!=5)
            {
                a = true;
            }
            ProductoNodo linkIni = rutas.get(0)._estadoProblema.GetNodoInicial();
            ProductoNodo linkFin = rutas.get(0)._estadoProblema.GetNodoFinal();
            
            
            if(linkIni.ant!=null&&!linkIni.EsDeposito())
            {
               a = true;
            }
            
            if(linkFin.sig != null&&!linkFin.EsDeposito())
            {
                a = true;
            }
            
        } catch (Exception e) {
            a = true;
        }        
        return a;
    }
    
    
    public void clone(EstadoProblema newList,HashMap<ProductoNodo,Integer> depotAIndex) throws Exception
    {                
        ProductoNodo ant = null;
        ProductoNodo temp = nodoInicial;
        ProductoNodo tempNew = new ProductoNodo(nodoInicial);                
        int i = 0;
        try {
            while(temp != null)
            {                           
                if(ant!= null) ant.sig = tempNew;
                tempNew.ant = ant;
                newList.addAtEnd(tempNew);
                if(temp.EsDeposito()) depotAIndex.put(temp, i);
                i++;
                ant = tempNew;
                temp = temp.sig;
                if(temp != null) tempNew = new ProductoNodo(temp);            
            }                    
        } catch (Exception e) {
            throw new Exception("ClonarEstadoProblema: " + e.getMessage());
        }           
    }
    
    public void ImprimirEstado(EstadoProblema estado)
    {
        ProductoNodo temp = estado.GetNodoInicial();
        while(temp!=null)
        {
            temp.Imprimir();
            temp = temp.sig;
        }
        System.out.println("");                   
    }
}


