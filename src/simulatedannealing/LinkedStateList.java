/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;

/**
 *
 * @author Jauma
 */
public class LinkedStateList {
    
    private ProductoNodo nodoInicial;
    private ProductoNodo nodoFinal;
    private int nrNodos;
    
    public LinkedStateList()
    {
        nodoInicial = null;
        nodoFinal = null;
        nrNodos = 0;
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
    
    public void Swap(ProductoNodo nodo1, ProductoNodo nodo2)
    {
        if(nodo1 == nodo2) return;
        
        if(nodo1.sig == nodo2)
        {
            nodo1.sig = nodo2.sig;
            nodo2.ant = nodo1.ant;
            
            if(nodo1.sig != null)
                nodo1.sig.ant = nodo1;
            
            if(nodo2.ant != null)
                nodo2.ant.sig = nodo2;
            
            nodo2.sig = nodo1;
            nodo1.ant = nodo2;
        }else
        {
            ProductoNodo tempAnt = nodo2.ant;
            ProductoNodo tempSig = nodo2.sig;
            
            nodo2.ant = nodo1.ant;
            nodo2.sig = nodo1.sig;
            
            nodo1.ant = tempAnt;
            nodo1.sig = tempSig;
            
            if(nodo2.sig != null)
                nodo2.sig.ant = nodo2;
            if(nodo2.ant != null)
                nodo2.ant.sig = nodo2;
            
            if(nodo1.sig != null)
                nodo1.sig.ant = nodo1;
            if(nodo1.ant != null)
                nodo1.ant.sig = nodo1;                            
        }
    }
    
    public void reverseRange(ProductoNodo nodo1, ProductoNodo nodo2)
    {
        
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
    
    public LinkedStateList clone()
    {
        LinkedStateList newList = new LinkedStateList();
        
        ProductoNodo ant = null;
        ProductoNodo temp = nodoInicial;
        ProductoNodo tempNew = new ProductoNodo(nodoInicial);        
        
        while(temp != null)
        {                           
            if(ant!= null) ant.sig = tempNew;
            tempNew.ant = ant;
            newList.addAtEnd(tempNew);
            ant = tempNew;
            temp = temp.sig;
            if(temp != null) tempNew = new ProductoNodo(temp);            
        }
        return newList;        
    }
}
