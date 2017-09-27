/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatedannealing;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Jauma
 */
public class Ruta {
    
    public final LinkedStateList _estadoProblema;
    private final double [][] _distancias;
    private final String _rutaNombre;
    private final int _rutaCodigo;
    private double _distancia;
    private int _nrPuntos;
    private double _peso;
    public ProductoNodo NodoInicial;
    public ProductoNodo NodoActual;
    private int _posInicial;
    private int _posActual;
        
    public Ruta(LinkedStateList estadoProblema,double [][] distancias, 
            String nombre,int codigo,ProductoNodo nodoInicial)
    {
        _rutaNombre = nombre;
        _rutaCodigo = codigo;
        _peso = 0.0;                
        _distancia = 0.0;
        _nrPuntos = 0;
        _estadoProblema = estadoProblema;
        _distancias = distancias;
        
        NodoInicial = nodoInicial;
        _posInicial = _estadoProblema.indexOf(NodoInicial);
        
        NodoActual = nodoInicial;       
        _posActual = _posInicial;       
    }
    
    public Ruta(LinkedStateList estadoProblema,double [][] distancias, 
            String nombre,int codigo,double distancia, int nrPuntos,  double peso,
            ProductoNodo nodoInicial,ProductoNodo nodoActual, int posInicial,int posActual)
    {
        _rutaNombre = nombre;
        _rutaCodigo = codigo;
        _estadoProblema = estadoProblema;
        _distancias = distancias;
        _distancia = distancia;
        _nrPuntos = nrPuntos;
        _peso = peso;
        
        NodoInicial = nodoInicial;
        NodoActual = nodoActual;
        _posInicial = posInicial;
        _posActual = posActual;                
    }
    
    
    public String GetNombre() { return _rutaNombre; }
    public int GetCodigo() { return _rutaCodigo; }
    public double GetPeso() { return _peso; }    
    public double GetDistancia() { return _distancia; }
    public int GetNrPuntos()  { return _nrPuntos; }
    
    public void IncrementarPeso(double peso) { _peso += peso; }  
    
    public void AgregarPunto(ProductoNodo nodo) 
    { 
        _nrPuntos++; 
        _distancia += _distancias[NodoActual.GetLlave()][nodo.GetLlave()];
        _posActual = _estadoProblema.size();
        _estadoProblema.addAtEnd(nodo);        
        NodoActual = nodo;
    }
    
    public Ruta Clonar(LinkedStateList nuevoEstado)
    {         
        return new Ruta(nuevoEstado, _distancias, _rutaNombre, _rutaCodigo, _distancia,_nrPuntos,_peso,
                nuevoEstado.get(_posInicial),nuevoEstado.get(_posActual),_posInicial,_posActual);
    }
    
    public void RecalcularDistancia()
    {                        
        ProductoNodo nodoAnt = NodoInicial;
        ProductoNodo nodo = null;
        _distancia = 0;
        do {            
            nodo = nodoAnt.sig;
            _distancia += _distancias[nodoAnt.GetLlave()][nodo.GetLlave()]; 
            nodoAnt = nodo;            
        } while (nodo != NodoActual);                                                
    }
    
    public void ImprimirRuta()
    {                
        ProductoNodo temp = NodoInicial;
        do {      
            temp.Imprimir();
            temp = temp.sig;            
        } while (!temp.EsDeposito());
        temp.ImprimirLn();                        
    }
    
    public void IntraRouteTwoOpt()
    {
        if(_nrPuntos == 3) return;
        if(_nrPuntos == 4) 
        {
            _estadoProblema.Swap(NodoInicial.sig, NodoActual.ant);
            return;
        }
        
        int point1 = ThreadLocalRandom.current().nextInt(0, _nrPuntos);
        int point2 = ThreadLocalRandom.current().nextInt(0, _nrPuntos);
    }
    
    public void ImprimirCosto()
    { 
        System.out.println("Distancia Recorrida: " +  new DecimalFormat("#.##").format(_distancia));
    }
}
