/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jauma
 */
public class Ruta {
    
    public final EstadoProblema _estadoProblema;
    private final double [][] _distancias;
    private final String _rutaNombre;
    private final int _rutaCodigo;
    private double _distancia;
    private double _peso;    
    public ProductoNodo _nodoInicial;
    public ProductoNodo _nodoActual;
    public int _nrNodos;
    private Boolean _estadoSucio;    
   
    public Ruta(EstadoProblema estadoProblema,double [][] distancias, 
            String nombre,int codigo,ProductoNodo nodoInicial)
    {
        _rutaNombre = nombre;
        _rutaCodigo = codigo;
        _peso = 0.0;                
        _distancia = 0.0;
        _estadoProblema = estadoProblema;
        _distancias = distancias;
        _nrNodos = 0;
        
        _nodoInicial = nodoInicial;       
        
        _nodoActual = nodoInicial;            
    }
    
    public Ruta(EstadoProblema estadoProblema,double [][] distancias, 
            String nombre,int codigo,double distancia, double peso,
            ProductoNodo nodoInicial,ProductoNodo nodoActual)
    {
        _rutaNombre = nombre;
        _rutaCodigo = codigo;
        _estadoProblema = estadoProblema;
        _distancias = distancias;
        _distancia = distancia;
        _peso = peso;
        _estadoSucio = true;
        
        try {
            SetNodoInicial(nodoInicial);
            SetNodoActual(nodoActual);
        } catch (Exception ex) {
            Logger.getLogger(Ruta.class.getName()).log(Level.SEVERE, null, ex);
        }              
    }
        
    public String GetNombre() { return _rutaNombre; }
    public int GetCodigo() { return _rutaCodigo; }
    public double GetPeso() { return _peso; }    
    public double GetDistancia() { return _distancia; }          
    public void IncrementarPeso(double peso) { _peso += peso; }  
    public Boolean EsEstadoSucio() { return _estadoSucio; };
    
    public void AgregarPunto(ProductoNodo nodo) 
    { 
        // HEY
        _distancia += _distancias[_nodoActual.GetLlave()][nodo.GetLlave()];     
        _estadoProblema.addAtEnd(nodo);   
        IncrementarPeso(nodo.GetPeso());        
        _nodoActual = nodo;
    }
    
    public void AgregarEn(ProductoNodo nodo,int index)
    {
        if(index<0) index = 0;
        int  i = 0;
        ProductoNodo temp = _nodoActual;
        
        while(temp!=_nodoActual&&i<index)
        {
            temp = temp.sig;
            i++;
        }        
        if(temp == _nodoActual) 
        {
            _distancia -= _distancias[_nodoActual.GetLlave()][_nodoActual.ant.GetLlave()];     
            _estadoProblema.addBefore(_nodoActual, nodo);
            
        }else
        {
            _distancia -= _distancias[temp.GetLlave()][temp.sig.GetLlave()];     
            _estadoProblema.addAfter(temp, nodo);
        }                
        if(temp.sig!=null) _distancia += _distancias[temp.GetLlave()][temp.sig.GetLlave()];
        if(temp.ant!=null) _distancia += _distancias[temp.GetLlave()][temp.ant.GetLlave()];                        
    }
    
    public void RemoverNodo(ProductoNodo nodo)
    {
        if(nodo.sig!=null) _distancia -= _distancias[nodo.GetLlave()][nodo.sig.GetLlave()];
        if(nodo.ant!=null) _distancia -= _distancias[nodo.GetLlave()][nodo.ant.GetLlave()];
        _estadoProblema.deleteNode(nodo);        
    }
    
    public ProductoNodo GetPunto(int index)
    {        
        ProductoNodo temp = _nodoInicial;
        int i = 0;
        while(temp != _nodoActual && i<index)
        {
            temp = temp.sig;
            i++;            
        }
        return temp;               
    }
    
    public int GetNrPuntos()
    {        
        if(!_estadoSucio) return _nrNodos;
        ProductoNodo temp = _nodoInicial;
        int i = 0;
        try {
            while(temp != _nodoActual)
            {
                temp = temp.sig;
                i++;            
            }            
        } catch (Exception e) {
            System.out.println("Error en Set Nodo Get Nr Puntos");
        }        
        return  i + 1;                 
    }
    
    public ProductoNodo GetNodoInicial()
    {
        return _nodoInicial;
    }
    
    public void SetNodoInicial(ProductoNodo nodo) throws Exception
    {
        try {
            if(nodo.EsDeposito()||_nodoActual == nodo)
            {
                _nodoInicial = nodo;
                _estadoSucio = true;
            }
            
        } catch (Exception e) {
            System.out.println("Error en Set Nodo Inicial");
        }        
    }        
     
    public ProductoNodo GetNodoActual()
    {
        return _nodoActual;
    }       
     
    public void SetNodoActual(ProductoNodo nodo) 
    {
        try {
            if(nodo.EsDeposito()||_nodoInicial == nodo)
            {
                _nodoActual = nodo;   
                _estadoSucio = true;
            }            
        } catch (Exception e) {
            System.out.println("Error en Set Nodo Actual");
        }        
    }         
         
    public Ruta Clonar(EstadoProblema nuevoEstado,HashMap<ProductoNodo,Integer> depotAIndex)
    {       
        try {
             return new Ruta(nuevoEstado, _distancias, _rutaNombre, _rutaCodigo, _distancia,_peso,
                nuevoEstado.get(depotAIndex.get(_nodoInicial)),nuevoEstado.get(depotAIndex.get(_nodoActual)));           
        } catch (Exception e) {
            System.out.println("Error en Clonar Ruta");
        }
        return null;
       
    }
    
    public void RecalcularEstado()
    {                        
        ProductoNodo nodoAnt = _nodoInicial;
        ProductoNodo nodo = null;        
        _distancia = 0;
        try {
            do {            
            nodo = nodoAnt.sig;
            _distancia += _distancias[nodoAnt.GetLlave()][nodo.GetLlave()]; 
            _peso += nodo.GetPeso();
            nodoAnt = nodo;                 
        } while (nodo != _nodoActual);   
        _estadoSucio = false;
        } catch (Exception e) {
            System.out.println("Error en Recalcular Distancia");
        }        
    }
    
    public void ImprimirRuta()
    {                
        ProductoNodo temp = _nodoInicial;
        do {      
            temp.Imprimir();
            temp = temp.sig;            
        } while (!temp.EsDeposito());
        temp.ImprimirLn();                        
    }
    
    
    public void ImprimirCosto()
    { 
        System.out.println("Distancia Recorrida: " +  new DecimalFormat("#.##").format(_distancia));
    }
}
