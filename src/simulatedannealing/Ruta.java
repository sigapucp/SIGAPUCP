/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
    private double _capacidad;
    public ProductoNodo _nodoInicial;
    public ProductoNodo _nodoActual;
    public int _nrNodos;
    private Boolean _estadoSucio;    
   
    public Ruta(EstadoProblema estadoProblema,double [][] distancias, 
            String nombre,int codigo,ProductoNodo nodoInicial,double capacidad)
    {
        _rutaNombre = nombre;
        _rutaCodigo = codigo;
        _peso = 0.0;                
        _distancia = 0.0;
        _estadoProblema = estadoProblema;
        _distancias = distancias;
        _nrNodos = 0;
        _capacidad = capacidad;
        
        _nodoInicial = nodoInicial;       
        
        _nodoActual = nodoInicial;            
    }
    
    public Ruta(EstadoProblema estadoProblema,double [][] distancias, 
            String nombre,int codigo,double distancia, double peso,
            ProductoNodo nodoInicial,ProductoNodo nodoActual,double capacidad)
    {
        _rutaNombre = nombre;
        _rutaCodigo = codigo;
        _estadoProblema = estadoProblema;
        _distancias = distancias;
        _distancia = distancia;
        _peso = peso;
        _estadoSucio = true;
        _capacidad = capacidad;
        
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
    
    public void AgregarEn(ProductoNodo nodo,int index) throws Exception
    {
        if(index<0) index = 0;
        int  i = 0;
        ProductoNodo temp = _nodoActual;
        try {              
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
            
        } catch (Exception e) {
            throw new Exception("AgregarEn: " + e.getMessage());
        }                                
    }
    
    public void RemoverNodo(ProductoNodo nodo) throws Exception
    {
        try {
            if(nodo.sig!=null) _distancia -= _distancias[nodo.GetLlave()][nodo.sig.GetLlave()];
            if(nodo.ant!=null) _distancia -= _distancias[nodo.GetLlave()][nodo.ant.GetLlave()];
            _estadoProblema.deleteNode(nodo);                    
        } catch (Exception e) {
            throw new Exception("RemoverNodo: " + e.getMessage());
        }        
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
    
    public int GetNrPuntos() throws Exception

    {              
        ProductoNodo temp = _nodoInicial;
        int i = 0;
        try {
            while(temp != _nodoActual)
            {
                temp = temp.sig;
                i++;            
            }            
        } catch (Exception e) {
            throw new Exception("GetNrPuntos: " + e.getMessage());
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
            }else
            {
                if(!nodo.EsDeposito())
                {
                    throw new Exception("Set no un depot");
                }                
            }            
        } catch (Exception e) {
           throw new Exception("Set Nodo Inicial: " + e.getMessage());
        }        
    }        
     
    public ProductoNodo GetNodoActual()
    {
        return _nodoActual;
    }       
     
    public void SetNodoActual(ProductoNodo nodo) throws Exception
    {
        try {
            if(nodo.EsDeposito()||_nodoInicial == nodo)
            {
                _nodoActual = nodo;   
                _estadoSucio = true;
            }
            else          
            {
                if(!nodo.EsDeposito())
                {
                    throw new Exception("Set no un depot");
                }                
            }            
        }catch (Exception e) {
           throw new Exception("Set Nodo Inicial: " + e.getMessage());
        }        
    }         
         
    public Ruta Clonar(EstadoProblema nuevoEstado,HashMap<ProductoNodo,Integer> depotAIndex) throws Exception
    {       
        try {
             return new Ruta(nuevoEstado, _distancias, _rutaNombre, _rutaCodigo, _distancia,_peso,
                nuevoEstado.get(depotAIndex.get(_nodoInicial)),nuevoEstado.get(depotAIndex.get(_nodoActual)),_capacidad);           
        } catch (Exception e) {
            throw new Exception("Clonar Ruta: " + e.getMessage());
        }
    }
    
    public void ClonarListaTwoOpt(ArrayList<ProductoNodo> lista) throws Exception
    {
        try {
            if(lista == null) return;
            ProductoNodo temp = _nodoInicial.sig;        
            while(temp!=null&&temp!=_nodoActual.sig)
            {
                lista.add(temp);
                temp = temp.sig;
            }                    
        } catch (Exception e) {
            throw new Exception("ClonarListTwoOpt: " + e.getMessage());
        }        
    }
    
    public Boolean RecalcularEstado() throws Exception
    {                        
        ProductoNodo nodoAnt = _nodoInicial;
        ProductoNodo nodo = null;        
        _distancia = 0;
        _peso = 0;
        try {
            do {            
            nodo = nodoAnt.sig;
            _distancia += _distancias[nodoAnt.GetLlave()][nodo.GetLlave()]; 
            _peso += nodo.GetPeso();
            nodoAnt = nodo;                 
        } while (nodo != _nodoActual);   
        _estadoSucio = false;
        if(_peso>_capacidad) throw new Exception("Ruta " + _rutaNombre + ": Peso Inconsistente.");
        return true;
        } catch (Exception e) {
            throw new Exception("RecalcularEstado: " + e.getMessage());            
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
