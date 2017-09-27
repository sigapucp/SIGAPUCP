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
public class ProductoNodo {
    
    private final int _llave;
    private final String _nombre;
    private final double _peso;
    private final Boolean _esDeposito;
    private Boolean _visitado;         
    public ProductoNodo ant;
    public ProductoNodo sig;
        
    public ProductoNodo(int llave,String nombre,double peso, Boolean esDeposito)
    {
        _llave = llave;
        if (nombre == null)
        {
            _nombre = "Prod" + llave;
        }else
        {
            _nombre = nombre;
        }                
        _visitado = false;
        _peso = peso;
        _esDeposito = esDeposito;        
        ant = null;
        sig = null;
    }
    
     public ProductoNodo(ProductoNodo nodo)
    {                
        _llave = nodo._llave;
        if (nodo._nombre == null)
        {
            _nombre = "Prod" + nodo._llave;
        }else
        {
            _nombre = nodo._nombre;
        }                
        _visitado = nodo._visitado;
        _peso = nodo._peso;
        _esDeposito = nodo._visitado;        
        ant = null;
        sig = null;
    }
    
    public Boolean GetVisitado() { return _visitado; }
    public void SetVisitado(Boolean valor) { _visitado = valor; }    
    public int GetLlave() { return _llave; }
    public Boolean EsDeposito() { return _esDeposito; }
    public double GetPeso() { return _peso ; }
    
        
    public void Imprimir()
    {
        System.out.print(_nombre + " ");
    }
    
    public void ImprimirLn()
    {
        System.out.println(_nombre);
    }
}
