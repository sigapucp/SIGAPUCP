/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.NullDrawing;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.ProductoSimulacion;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.SelectableGrid;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.TupleProductos;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Celda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Estado;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.ProductoNodo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Punto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Ruta;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaDetallada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaGeneral;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.TestRouting;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaXY;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaZ;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Simulacion;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class SimulacionesController extends Controller{

    @FXML
    private AnchorPane producton_container;
    @FXML
    private TextField BuscarCodigo;
    @FXML
    private TableView<OrdenSalida> tablaOrdenesSalida;
    @FXML
    private TableColumn<OrdenSalida, String> ColumnaCodigo;
    @FXML
    private TableColumn<OrdenSalida, String> ColumnaCliente;
    @FXML
    private TableColumn<OrdenSalida, String> ColumnaTipo;
    @FXML
    private ComboBox<String> BuscarTipo;
    @FXML
    private TextField BuscarCliente;
    @FXML
    private AnchorPane tipo_producto_formulario;
    @FXML
    private AnchorPane formulario_general;
    @FXML
    private TextField VerCodigoOrden;
    @FXML
    private TextField VerClienteOrden;
    @FXML
    private DatePicker VerFechaOrden;
    @FXML
    private TextField VerTipoOrden;
    private TextField VerNrCargadores;
    @FXML
    private TextField VerCapacidadCarrito;
    @FXML
    private AnchorPane formulario_productos;
    @FXML
    private TableView<Producto> TablaProductosUnicos;
    @FXML
    private TableColumn<Producto, String> ColumnaCantidadUnicos;
    @FXML
    private TableColumn<Producto, String> ColumnaUnicosAlmacen;
    @FXML
    private TableColumn<Producto, String> ColumnaCodRacksUnicos;
    @FXML
    private TableColumn<Producto, String> ColumnaPosicionUnicos;
    @FXML
    private TableColumn<Producto, String> ColumnaNivelUnicos;
    @FXML
    private TableColumn<Producto, String> ColumnaCapacidadRestante;
    @FXML
    private GridPane formulario_simulacion;
    @FXML
    private ComboBox<Integer> VerNrSimulacion;
    @FXML
    private ComboBox<Integer> VerNrRuta;
    @FXML
    private Label VerCostoRuta;
    @FXML
    private AnchorPane mapa_pane;
    @FXML
    private TextField VerAcopioX;
    @FXML
    private TextField VerAcopioY;
    @FXML
    private TextField VerLargoAlmacen;
    @FXML
    private TextField VerAnchoAlmacen;
    
    
    private SelectableGrid grid;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private LazyList<Rack> racksActuales;
    private List<Producto> productos;
    private RutaDetallada rutaDetallada;
    private RutaGeneral rutaGeneral;
    private Celda.TIPO[][] mapa;
    private List<TupleProductos> rutasProductos;
    private int height;
    private int width;
    private double[][] distancias;
    private List<Double> pesos; 
    private List<Simulacion> simulaciones;
    private Boolean distanciaCalculada;
    private Simulacion simulacionActual;    
    private List<ProductoSimulacion> productosSimulacion;
    private Boolean nextReset = false;
    private Punto acopioAnterior = null;
    @FXML
    private CheckBox recarcularRutasCheck;
    @FXML
    private TitledPane tittle_pane;
 
    
    public SimulacionesController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();      
        rutaDetallada = new RutaDetallada();
        rutasProductos = new ArrayList<>();
        pesos = new ArrayList<>();
        simulaciones = new ArrayList<>();
        distanciaCalculada = false;
        simulacionActual = null;
    }
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO                              
        
        racksActuales = Rack.where("estado = ?",Rack.ESTADO.ACTIVO.name());
        TestRouting test = new TestRouting();
        try {
            //test.runTest();
        } catch (Exception ex) {
            Logger.getLogger(SimulacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @Override
    public void postInitialize(String gFxmlPath)
    {        
        fxmlPath = gFxmlPath;
        Almacen almacenCentral = Almacen.findFirst("es_central = ?", "T");
        int largo = almacenCentral.getInteger("largo");
        int ancho = almacenCentral.getInteger("ancho");        
        int area = almacenCentral.getInteger("longitud_area");
        
      
        Double realWidth = mapa_pane.getPrefWidth();
        Double realHeight = mapa_pane.getPrefHeight();
        
        grid = new SelectableGrid(ancho, largo, area, new NullDrawing(),realWidth.intValue(),realHeight.intValue());                        
        
        height = largo/area;
        width = ancho/area;
        
        mapa = new Celda.TIPO[height][width];
        
        for(int i = 0;i<width;i++)
        {
            for(int j = 0;j<height;j++)
            {
                mapa[j][i] = Celda.TIPO.LIBRE;
            }
        }
           
        List<Almacen> almacenes = Almacen.where("es_central = ?", "F");
        List<Rack> racksAll = Rack.findAll();
        for(Almacen almacen:almacenes)            
        {
            int relative_x = almacen.getInteger("x_relativo_central");
            int relative_y = almacen.getInteger("y_relativo_central");
            
            List<Rack> racks = racksAll.stream().filter(x -> Objects.equals(x.getInteger("almacen_id"), almacen.getInteger("almacen_id"))).collect(Collectors.toList());
            for(Rack rack:racks)
            {
                int anchorX1 = rack.getInteger("x_ancla1") + relative_x;
                int anchorY1 = rack.getInteger("y_ancla1") + relative_y;
                
                int anchorX2 = rack.getInteger("x_ancla2") + relative_x;     
                int anchorY2 = rack.getInteger("y_ancla2") + relative_y;                               
                
                if(rack.getString("tipo").equals(Rack.TIPO.HORIZONTAL.name()))
                {
                    int start = Integer.min(anchorX1, anchorX2);
                    int finish = Integer.max(anchorX1, anchorX2);
                    
                    for(;start<=finish;start++)
                    {
                        mapa[anchorY1][start] = Celda.TIPO.RACK;                                                
                        grid.pintarTile(start,anchorY1, Color.RED);
                    }                    
                }else
                {
                    int start = Integer.min(anchorY1, anchorY2);
                    int finish = Integer.max(anchorY1, anchorY2);
                    
                    for(;start<=finish;start++)
                    {
                        mapa[start][anchorX1] = Celda.TIPO.RACK;       
                        grid.pintarTile(anchorX1,start, Color.RED);
                    }
                }
            }                        
        }           
        mapa_pane.getChildren().setAll(grid);                                         
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Simulaciones;
    }

    @FXML
    private void visualizar_simulacion(ActionEvent event) {
    }

    @FXML
    private void buscar_simulacion(ActionEvent event) {
    }
        
    @FXML
    private void comenzarRuta(ActionEvent event) {
        grid.clearUserTiles();    
        simulacionActual.setNodoActual(0);
        tittle_pane.setText("Inicio de Ruta");
    }

    @FXML
    private void siguientePaso(ActionEvent event) {    
        
        if(nextReset)
        {
            comenzarRuta(null);  
            nextReset = false;
            return;            
        }
        ProductoNodo nodoActual = simulacionActual.getNodoActual();
        if(nodoActual.sig.EsDeposito())
        {
            tittle_pane.setText("Llendo al punto de acopio");
        }else
        {
            tittle_pane.setText("Recogiendo Producto: " + productosSimulacion.get(nodoActual.sig.GetLlave()).getProducto().getString("producto_cod"));            
        }
        
        TupleProductos tupla = rutasProductos.stream().filter(x -> x.esPar(productosSimulacion.get(nodoActual.GetLlave()), productosSimulacion.get(nodoActual.sig.GetLlave()))).findFirst().get();           
        dibujarMapa(tupla.getEstado(),tupla.getColor());
        grid.pintarTile(tupla.getProductoUno().getPunto().x, tupla.getProductoUno().getPunto().y, tupla.getProductoUno().getColor());
        grid.pintarTile(tupla.getProductoDos().getPunto().x, tupla.getProductoDos().getPunto().y, tupla.getProductoDos().getColor());    
        
        nextReset = simulacionActual.moverEnRuta();        
    }


    @FXML
    private void generarSimulacion(ActionEvent event) {
        try {
        grid.clearUserTiles();
        String capacidadCarrito = VerCapacidadCarrito.getText();         
        String puntoAcopioX = VerAcopioX.getText();
        String puntoAcopioY = VerAcopioY.getText();
        
        if(!validator.isNumeric(capacidadCarrito)||!validator.isNumeric(puntoAcopioX)||!validator.isNumeric(puntoAcopioX))
        {
            infoController.show("Los datos de entrada contienen errores");
            return;
        }
        
        Double capacidadCarro = Double.valueOf(capacidadCarrito);
        int acopioX = (int)Double.parseDouble(puntoAcopioX);
        int acopioY = (int)Double.parseDouble(puntoAcopioY);
        
        Punto acopio = new Punto(acopioX, acopioY);
        
        if(!acopio.esValido(height, width))
        {
            infoController.show("El punto de acopio no es valido");
            return;            
        }
        
        productos = Producto.where("ubicado = ?", "S");                                    
        
        if(!distanciaCalculada||recarcularRutasCheck.isSelected()) calcularDistancias(capacidadCarro, acopio);
        
        if(acopioAnterior != null && !acopioAnterior.isEqual(acopio))
        {
            // Actualizar el punto
            productosSimulacion.get(0).setPunto(acopio);
            for(int i = 1;i<productosSimulacion.size();i++)
            {              
                Celda.TIPO[][] mapaActual = copiarMapa(mapa,height,width);                                           
                Estado ruta = rutaDetallada.generarRutaGreedy(mapaActual, height, width, acopio, productosSimulacion.get(i).getPunto());               
                distancias[0][i] = ruta.getCost();
                distancias[i][0] = ruta.getCost();  
                replaceRuta(productosSimulacion.get(0),productosSimulacion.get(i),ruta);
            }         
        }
        
        acopioAnterior = acopio;
        rutaGeneral = new RutaGeneral(productosSimulacion.size()-1, distancias, pesos, capacidadCarro);
        rutaGeneral.CorrerAlgoritmo();               
        Simulacion nuevaSimulacion = new Simulacion(rutaGeneral, acopio, capacidadCarro);        
        simulacionActual = nuevaSimulacion;
        
        
        impimirRuta(nuevaSimulacion.getRutaActual());        
        } catch (Exception ex) {
            Logger.getLogger(SimulacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }              
                
    }
    
    public Boolean calcularDistancias(Double capacidadCarro,Punto acopio)
    {        
        productosSimulacion = new ArrayList<>();        
        productosSimulacion.add(new ProductoSimulacion(acopio));
        for(Producto producto:productos)
        {
            ProductoSimulacion pSimulacion = new ProductoSimulacion(producto);
            if(!pSimulacion.getPunto().esValido(height, width))
            {
                infoController.show("El producto " + producto.getString("producto_cod") + " posee una posicion invalida");
                return false;
            }
            
            productosSimulacion.add(pSimulacion);            
            double peso = TipoProducto.findById(producto.get("tipo_id")).getDouble("peso");
            if(peso > capacidadCarro)
            {
                infoController.show("El producto " + producto.getString("producto_cod") + " posee un peso mayor a la capacidad del carrito");
                return false;
            }
            pesos.add(peso);
            
        }                        
        Integer nrProductos = productosSimulacion.size();        
        distancias = new double[nrProductos+1][nrProductos+1]; 
                
        for(int i = 0;i<nrProductos+1;i++)
        {
            for(int j = i+1;j<nrProductos;j++)
            {
                TupleProductos tupla = new TupleProductos(productosSimulacion.get(i), productosSimulacion.get(j));
                rutasProductos.add(tupla);
                Celda.TIPO[][] mapaActual = copiarMapa(mapa,height,width);                                           
                Estado ruta = rutaDetallada.obtenerRutaOptima(mapaActual, height, width, tupla.getProductoUno().getPunto(), tupla.getProductoDos().getPunto());               
                distancias[i][j] = ruta.getCost();
                distancias[j][i] = ruta.getCost();
                tupla.setRuta(ruta);                              
            }
        }             
        distanciaCalculada = true;
        return true;
    }
    
    private void replaceRuta(ProductoSimulacion pr1,ProductoSimulacion pr2,Estado estado)
    {
        TupleProductos tupla = rutasProductos.stream().filter(x -> x.esPar(pr1, pr2)).findFirst().get();
        tupla.setRuta(estado);        
    }
    
    private void impimirRuta(List<ProductoNodo> rutaList)
    {           
        for(ProductoNodo producto:rutaList)
        {
            TupleProductos tupla = rutasProductos.stream().filter(x -> x.esPar(productosSimulacion.get(producto.GetLlave()), productosSimulacion.get(producto.sig.GetLlave()))).findFirst().get();           
            dibujarMapa(tupla.getEstado(),tupla.getColor());
            grid.pintarTile(tupla.getProductoUno().getPunto().x, tupla.getProductoUno().getPunto().y, tupla.getProductoUno().getColor());
            grid.pintarTile(tupla.getProductoDos().getPunto().x, tupla.getProductoDos().getPunto().y, tupla.getProductoDos().getColor());
            if(producto.sig.EsDeposito()) break;
        }
    }
    
    public void dibujarMapa(Estado solucion,Color color)
    {
        for(Punto punto:solucion.ruta)
        {                       
            grid.pintarTileDeletable(punto.x, punto.y, color);
        }            
    }
    
    private Celda.TIPO[][] copiarMapa(Celda.TIPO[][] mapa,int altura,int ancho)
    {
        Celda.TIPO[][] nuevoMapa = new Celda.TIPO[altura][ancho];
        
        for(int i = 0;i<ancho;i++)
        {
            for(int j = 0;j<altura;j++)
            {
                nuevoMapa[j][i] = mapa[j][i];
            }
        }                
        return nuevoMapa;
    }           

    @FXML
    private void agregarSimulacion(ActionEvent event) {
    }

}
