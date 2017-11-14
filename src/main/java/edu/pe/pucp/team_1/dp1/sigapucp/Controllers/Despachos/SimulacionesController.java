/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarOrdenesSalidaController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.NullDrawing;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.ProductoSimulacion;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.SelectableGrid;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.TupleProductos;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Celda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Estado;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalidaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalidaxProductoFinal;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.ProductoNodo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Punto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.Ruta;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaDespacho;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaDetallada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaGeneral;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.RutaSaved;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.SimulacionBD;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.SimulacionSaved;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.SimulacionesxDespachos;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.TestRouting;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaXY;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaZ;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Simulacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenSalidaArgs;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

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
    private TableColumn<OrdenSalida, String> ColumnaDescripcion;
    @FXML
    private TableColumn<OrdenSalida, String> ColumnaTipo;
    @FXML
    private ComboBox<String> BuscarTipo;
    @FXML
    private AnchorPane tipo_producto_formulario;
    @FXML
    private AnchorPane formulario_general;
    @FXML
    private TextField VerCodigoOrden;    
    @FXML
    private TextArea VerDescripcion;
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
    private TableView<ProductoSimulacion> TablaProductosUnicos;

    @FXML
    private TableColumn<ProductoSimulacion, String> ColumnaProdCodigo;

    @FXML
    private TableColumn<ProductoSimulacion, String> ColumnaAlmacen;

    @FXML
    private TableColumn<ProductoSimulacion, String> ColumnaRack;
    

    @FXML
    private TableColumn<ProductoSimulacion, String> ColumnaPunto;

    @FXML
    private TableColumn<ProductoSimulacion, Color> ColumnaColor;

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
    @FXML
    private CheckBox recarcularRutasCheck;
    @FXML
    private TitledPane tittle_pane;
    @FXML
    private Label VerCostoSimulacion;
    @FXML
    private GridPane formulario_simulacion1;
    @FXML
    private ComboBox<Integer> VerNrSimulacionSaved;
    @FXML
    private ComboBox<Integer> VerNrRutaSaved;
    @FXML
    private Label VerCostoRutaSaved;
    @FXML
    private Label VerCostoSimulacionSaved;   
    @FXML
    private AnchorPane mapa_paneSaved;
    
    
    private SelectableGrid grid;
    private SelectableGrid gridSaved;
    
    private WarningAlertController warningController;
    private ConfirmationAlertController confirmationController;
    private InformationAlertController infoController;
    
    private LazyList<Rack> racksActuales;
    private List<Producto> productos;
    private RutaDetallada rutaDetallada;
    private RutaGeneral rutaGeneral;
    private Celda.TIPO[][] mapa;   
    private int height;
    private int width;
    private double[][] distancias;
    private List<Double> pesos; 
    
    private List<Simulacion> simulaciones;
    private List<SimulacionSaved> simulacionesSaved;
    private Simulacion simulacionActual;    
    private SimulacionSaved simulacionActualSaved;    
    
    private Boolean distanciaCalculada;
    private List<ProductoSimulacion> productosSimulacion;
    private Boolean nextReset = false;
    private Punto acopioAnterior = null;    
    private Stage modal_stage;
    private OrdenSalida ordenSalidaActual;
    private Color colorActual = null;
    private Color colorActualSaved = null;
    
    ObservableList<Integer> nrRutas = FXCollections.observableArrayList();                           
    ObservableList<Integer> nrSimulaciones = FXCollections.observableArrayList();           
    
    ObservableList<Integer> nrRutasSaved = FXCollections.observableArrayList();                           
    ObservableList<Integer> nrSimulacionesSaved = FXCollections.observableArrayList();           
        
    ObservableList<ProductoSimulacion> productosObs = FXCollections.observableArrayList(); 
    ObservableList<OrdenSalida> ordenesSalida = FXCollections.observableArrayList();      
    
    public SimulacionesController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();      
        confirmationController = new ConfirmationAlertController();
        rutaDetallada = new RutaDetallada();     
        pesos = new ArrayList<>();
        simulaciones = new ArrayList<>();
        simulacionesSaved = new ArrayList<>();
        distanciaCalculada = false;       
        simulacionActual = null;      
    }
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {                        
        try {
            
            VerNrSimulacion.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {                
                if(newValue == null) return;
                if(newValue<0||newValue>simulaciones.size()) return;
                simulacionActual = simulaciones.get(newValue-1);
                grid.clearUserTilesColor(colorActual);    
                impimirRuta(simulacionActual.getRutaActual(),simulacionActual.getRutasProductos());
                fillListUpTo(nrRutas, simulacionActual.getNrRutas());
                VerCostoSimulacion.setText(String.valueOf(simulacionActual.getCosto()));                
            }          
        });
            
            VerNrRuta.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if(simulacionActual == null) return;
                if(newValue == null) return;
                if(newValue<0||newValue>simulacionActual.getNrRutas()) return;
                simulacionActual.setRutaActual(newValue-1);
                grid.clearUserTilesColor(colorActual);
                impimirRuta(simulacionActual.getRutaActual(),simulacionActual.getRutasProductos());
                VerCostoRuta.setText(String.valueOf(simulacionActual.getCostoRutaActual()));                           
            }          
        });
            
            
        VerNrSimulacionSaved.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {                
                if(newValue == null) return;
                if(newValue<0||newValue>simulacionesSaved.size()) return;
                simulacionActualSaved = simulacionesSaved.get(newValue-1);
                gridSaved.clearUserTiles();    
                imprimirRutaSaved(simulacionActualSaved.getRutaActual().getRuta());
                fillListUpTo(nrRutasSaved, simulacionActualSaved.getNrRutas());
                VerCostoSimulacionSaved.setText(String.valueOf(simulacionActualSaved.getCosto()));                
            }          
        });
            
            VerNrRutaSaved.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if(simulacionActualSaved == null) return;
                if(newValue == null) return;
                if(newValue<0||newValue>simulacionActualSaved.getNrRutas()) return;
                simulacionActualSaved.setRutaActual(newValue-1);
                gridSaved.clearUserTilesColor(colorActualSaved);
                imprimirRutaSaved(simulacionActualSaved.getRutaActual().getRuta());
                VerCostoRutaSaved.setText(String.valueOf(simulacionActualSaved.getCostoRutaActual()));                        
            }          
        });                        
            
        ColumnaProdCodigo.setCellValueFactory((CellDataFeatures<ProductoSimulacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getProductoNombre()));       
        ColumnaAlmacen.setCellValueFactory((CellDataFeatures<ProductoSimulacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getAlmacenName()));       
        ColumnaRack.setCellValueFactory((CellDataFeatures<ProductoSimulacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getRackCode()));       
        ColumnaPunto.setCellValueFactory((CellDataFeatures<ProductoSimulacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getPunto().toString()));       
        ColumnaColor.setCellValueFactory((CellDataFeatures<ProductoSimulacion, Color> p) -> new ReadOnlyObjectWrapper(p.getValue().getColor()));       
        
        ColumnaColor.setCellFactory(column -> {
            return new TableCell<ProductoSimulacion, Color>() {
            @Override
            protected void updateItem(Color item, boolean empty) {      
                if(item == null) return;
                String a = "-fx-background-color: rgb(" + (int)(item.getRed()*255) + "," + (int)(item.getGreen()*255) + "," + (int)(item.getBlue()*255) + ")";
                setStyle(a);
            }
        };}
        );
            
            
        ColumnaCodigo.setCellValueFactory((CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("salida_cod")));   
        ColumnaDescripcion.setCellValueFactory((CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("descripcion")));   
        ColumnaTipo.setCellValueFactory((CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("tipo")));           
        
        ordenesSalida.addAll(OrdenSalida.findAll());         
        
        VerNrRuta.setItems(nrRutas);
        VerNrRutaSaved.setItems(nrRutasSaved);
        VerNrSimulacion.setItems(nrSimulaciones);  
        VerNrSimulacionSaved.setItems(nrSimulacionesSaved);
        
        TablaProductosUnicos.setItems(productosObs);
        tablaOrdenesSalida.setItems(ordenesSalida);
        
        inhabilitar_formulario();             
        
        } catch (Exception ex) {
            infoController.show("Error al cargar las simulaciones: " + ex.getMessage());
            Logger.getLogger(SimulacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void fillListUpTo(ObservableList<Integer> lista,int n)
    {
        lista.clear();
        for(int i = 0;i<n;i++)
        {
            lista.add(i+1);
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
        gridSaved = new SelectableGrid(ancho, largo, area, new NullDrawing(),realWidth.intValue(),realHeight.intValue());
        
        height = largo/area;
        width = ancho/area;
        
        VerLargoAlmacen.setText(String.valueOf(height));
        VerAnchoAlmacen.setText(String.valueOf(width));
        
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
                        gridSaved.pintarTile(start,anchorY1, Color.RED);
                    }                    
                }else
                {
                    int start = Integer.min(anchorY1, anchorY2);
                    int finish = Integer.max(anchorY1, anchorY2);
                    
                    for(;start<=finish;start++)
                    {
                        mapa[start][anchorX1] = Celda.TIPO.RACK;       
                        grid.pintarTile(anchorX1,start, Color.RED);
                        gridSaved.pintarTile(anchorX1,start, Color.RED);
                    }
                }
            }                        
        }           
        mapa_pane.getChildren().setAll(grid);       
        mapa_paneSaved.getChildren().setAll(gridSaved);        
        /////////////// Change
        
    }
    
    private void inhabilitar_formulario()
    {
        formulario_general.setDisable(true);
        formulario_productos.setDisable(true);
        formulario_simulacion.setDisable(true);
        formulario_simulacion1.setDisable(true);
    }
    
    private void habilitar_formulario()
    {
        formulario_general.setDisable(false);
        formulario_productos.setDisable(false);
        formulario_simulacion.setDisable(false);        
        formulario_simulacion1.setDisable(false);
    }
    
    private void limpiar_formulario()
    {
        ordenSalidaActual = null;
        distanciaCalculada = false;
        nextReset = false;
        VerAcopioX.setText("0");
        VerAcopioY.setText("0");
        VerCapacidadCarrito.clear();      
        VerDescripcion.clear();
        if(productos != null) productos.clear();     
        if(simulaciones != null) simulaciones.clear();
        if(simulacionesSaved != null) simulacionesSaved.clear();
        productosObs.clear();
        nrRutas.clear();
        nrSimulaciones.clear();
        nrRutasSaved.clear();
        nrSimulacionesSaved.clear();
        VerCostoRuta.setText("--");
        VerCostoSimulacion.setText("--");
    }     
    
    @Override
    public void guardar()
    {
        if(productosSimulacion == null)
        {
            infoController.show("No ha seleccionado ninguna Orden");
            return;
        }
        
        try {
            Base.openTransaction();
            for(Simulacion simulacion:simulaciones)
            {
                SimulacionBD simulacionBD = new SimulacionBD();
                
                simulacionBD.set("capacidad_carro",simulacion.getCapcacidadCarro());
                simulacionBD.set("nr_productos",simulacion.getNrProductos());
                simulacionBD.set("distancia_total",simulacion.getCosto());
                simulacionBD.set("punto_acopio_x",simulacion.getPuntoAcopio().x);
                simulacionBD.set("punto_acopio_y",simulacion.getPuntoAcopio().y);
                
                simulacionBD.saveIt();
                for(int i = 0;i<simulacion.getNrRutas();i++)
                {
                    RutaDespacho rutaDespacho = new RutaDespacho();
                    
                    rutaDespacho.set("distancia_recorrida",simulacion.getRutas().get(i).GetDistancia());
                    rutaDespacho.set("simulacion_id",simulacionBD.getId());
                    rutaDespacho.set("ruta_orden",simulacion.getRutaString(i, productosSimulacion));                     
                    rutaDespacho.saveIt();                                        
                }              
                
                SimulacionesxDespachos simxdes = new SimulacionesxDespachos();
                simxdes.set("salida_cod",ordenSalidaActual.get("salida_cod"));
                simxdes.set("salida_id",ordenSalidaActual.get("salida_id"));
                simxdes.set("simulacion_id",simulacionBD.get("simulacion_id"));
                simxdes.saveIt();
            }
            
            Base.commitTransaction();
            infoController.show("La simulaciones se han guardado satisfactoriamente");
        } catch (Exception e) {
            infoController.show("Error al guardar Simulaciones: " + e.getMessage());
            Base.rollbackTransaction();
        }
        inhabilitar_formulario();
        limpiar_formulario();        
    }
    
     private void setAgregarProductos() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarOrdenesSalida.fxml"));
        AgregarOrdenesSalidaController controller = new AgregarOrdenesSalidaController();
        loader.setController(controller);                      
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage.setScene(modal_content_scene);
        if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    

        controller.devolverOrdenEvent.addHandler((Object sender, agregarOrdenSalidaArgs args) -> {
            ordenSalidaActual = args.getOrdenSalida();
        });             
    }

    @FXML
    private void visualizar_simulacion(ActionEvent event) {
        habilitar_formulario();
        limpiar_formulario();
        
        ordenSalidaActual = tablaOrdenesSalida.getSelectionModel().getSelectedItem();
        if(ordenSalidaActual == null)
        {
            infoController.show("No ha seleccionado ninguna Orden Valida");
            return;
        }
        
        String tipo = ordenSalidaActual.getString("tipo");
        
        if(!tipo.equals(OrdenSalida.TIPO.Venta.name()))
        {
            if(!confirmationController.show("La orden de salida es del tipo: " + tipo + ".", "¿Seguro que desea continuar?"))
            {
                inhabilitar_formulario();
                ordenSalidaActual = null;
                return;
            }                                
        }
        
        grid.clearUserTilesColor(colorActual);
        gridSaved.clearUserTilesColor(colorActualSaved);
        List<Producto> productosOrden =  new ArrayList<>();
        List<OrdenSalidaxProductoFinal> productosFinal = OrdenSalidaxProductoFinal.where("salida_id = ?", ordenSalidaActual.getId());
        
        for(OrdenSalidaxProductoFinal productoFinal:productosFinal)
        {
            productosOrden.add(Producto.findFirst("producto_id = ?", productoFinal.get("producto_id")));
        }
        
        productos = productosOrden.stream().filter(x -> x.getString("ubicado").equals("S")).collect(Collectors.toList());
        if(productos.isEmpty())
        {
            infoController.show("No hay con una ubicacion en esta orden");
            limpiar_formulario();
            inhabilitar_formulario();
            return;            
        }
        
        List<Producto> noUbicados = productosOrden.stream().filter(x -> x.getString("ubicado").equals("N")).collect(Collectors.toList());
        
        if(!noUbicados.isEmpty())
        {
            String msg = "Los siguientes productos no han sido ubicados por lo que no se tomaran cuenta en la simulación:\n\n";
            for(Producto producto:noUbicados)
            {
                msg += producto.getString("producto_id") + "\n";
            }
            infoController.show(msg);
        }

        VerCodigoOrden.setText(ordenSalidaActual.getString("salida_cod"));
        VerTipoOrden.setText(ordenSalidaActual.getString("tipo"));
        VerDescripcion.setText(ordenSalidaActual.getString("descripcion"));
        VerFechaOrden.setValue(ordenSalidaActual.getDate("last_data_change").toLocalDate());
                
        productosSimulacion = new ArrayList<>();        
        productosSimulacion.add(new ProductoSimulacion());
        for(Producto producto:productos)
        {
            ProductoSimulacion pSimulacion = new ProductoSimulacion(producto);
            if(!pSimulacion.getPunto().esValido(height, width))
            {
                infoController.show("El producto " + producto.getString("producto_cod") + " posee una posicion invalida");
                return;
            }            
            productosSimulacion.add(pSimulacion);                                  
        }                   
        
        Integer nrProductos = productosSimulacion.size();        
        distancias = new double[nrProductos+1][nrProductos+1];                           
        RefrescarTablaProductos(productosSimulacion);
        
        setProductosGuardados(ordenSalidaActual);
    }
    
    private void setProductosGuardados(OrdenSalida ordenSalida)
    {        
        try {            
                        
            List<SimulacionBD> simulacionesGuardadas = ordenSalida.getAll(SimulacionBD.class);
            for(SimulacionBD simulacion:simulacionesGuardadas)
            {
                SimulacionSaved nuevaSimulacionSaved = new SimulacionSaved(simulacion.getDouble("distancia_total"),simulacion.getDouble("capacidad_carro"),
                        simulacion.getInteger("nr_productos"),simulacion.getInteger("punto_acopio_x"),simulacion.getInteger("punto_acopio_y"));
                
                simulacionesSaved.add(nuevaSimulacionSaved);
                
                List<RutaDespacho> rutas = RutaDespacho.where("simulacion_id = ?", simulacion.getId());
                nrSimulacionesSaved.add(simulacionesSaved.size());
                
                for(RutaDespacho ruta:rutas)
                {
                    RutaSaved rutaSaved = new RutaSaved(ruta.getString("ruta_orden"),ruta.getDouble("distancia_recorrida"));                    
                    nuevaSimulacionSaved.addRuta(rutaSaved);
                }                                
            }
            
        } catch (Exception e) {
            infoController.show("No se ha podido cargar las simulaciones guardadas: " + e.getMessage());
        }
    }

    @FXML
    private void buscar_simulacion(ActionEvent event) {
        String codigo = BuscarCodigo.getText();      
        String tipo = BuscarTipo.getSelectionModel().getSelectedItem();        
                
        List<OrdenSalida> tempOrdenes = OrdenSalida.where("estado = ?",OrdenSalida.ESTADO.ENPROCESO.name());
        
        if(codigo!=null&&!codigo.isEmpty())
        {            
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("producto_cod").equals(codigo)).collect(Collectors.toList());
        }
      
        if(tipo!=null&&!tipo.isEmpty())
        {
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("tipo").equals(tipo)).collect(Collectors.toList());
        }
      
        RefrescarTabla(tempOrdenes);
        try {                        
        } catch (Exception e) {
            infoController.show("La Orden contiene errores : " + e);                    
        }
    }
    
    private void RefrescarTabla(List<OrdenSalida> ordenesRefresh)
    {        
        try {
            ordenesSalida.removeAll(ordenesSalida);                 
            if(ordenesRefresh == null) return;
            for (OrdenSalida orden : ordenesRefresh) {
                ordenesSalida.add(orden);
            }               
            tablaOrdenesSalida.getColumns().get(0).setVisible(false);
            tablaOrdenesSalida.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Las ordenes contienen errores : " + e.getMessage());      
        }                                  
    }
    
    private void RefrescarTablaProductos(List<ProductoSimulacion> productosRefresh)
    {        
        try {
            productosObs.removeAll(productosObs);                 
            if(productosRefresh == null) return;
            for (ProductoSimulacion producto : productosRefresh) {
                productosObs.add(producto);
            }               
            TablaProductosUnicos.getColumns().get(0).setVisible(false);
            TablaProductosUnicos.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Los productos contienen errores : " + e.getMessage());      
        }                                  
    }
    
    
    @FXML
    private void agregarSimulacion(ActionEvent event) {       
        if(simulacionActual == null)
        {
            infoController.show("No ha seleccionado ninguna Simulacion");
            return;
        }               
        simulaciones.add(simulacionActual);
        nrSimulaciones.add(simulaciones.size());
    }

        
    @FXML
    private void comenzarRuta(ActionEvent event) {
        grid.clearUserTilesColor(colorActual);
        simulacionActual.setNodoActual(0);
        tittle_pane.setText("Inicio de Ruta");
        
        for(ProductoNodo producto:simulacionActual.getRutaActual())
        {
            TupleProductos tupla = simulacionActual.getRutasProductos().stream().filter(x -> x.esPar(productosSimulacion.get(producto.GetLlave()), productosSimulacion.get(producto.sig.GetLlave()))).findFirst().get();                                   
            grid.pintarTileDeletable(tupla.getProductoUno().getPunto().x, tupla.getProductoUno().getPunto().y, tupla.getProductoUno().getColor());
            grid.pintarTileDeletable(tupla.getProductoDos().getPunto().x, tupla.getProductoDos().getPunto().y, tupla.getProductoDos().getColor());
            if(producto.sig.EsDeposito()) break;            
        }
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
        
        TupleProductos tupla = simulacionActual.getRutasProductos().stream().filter(x -> x.esPar(productosSimulacion.get(nodoActual.GetLlave()), productosSimulacion.get(nodoActual.sig.GetLlave()))).findFirst().get();           
        dibujarMapa(tupla.getEstado(),tupla.getColor());
        grid.pintarTileDeletable(tupla.getProductoUno().getPunto().x, tupla.getProductoUno().getPunto().y, tupla.getProductoUno().getColor());
        grid.pintarTileDeletable(tupla.getProductoDos().getPunto().x, tupla.getProductoDos().getPunto().y, tupla.getProductoDos().getColor());    
        
        nextReset = simulacionActual.moverEnRuta();        
    }


    @FXML
    private void generarSimulacion(ActionEvent event) {
        try {
        grid.clearUserTilesColor(colorActual);
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
        
        if(!acopio.esValido(height, width)||grid.isTileActive(acopio.x, acopio.y))
        {
            infoController.show("El punto de acopio no es valido");
            return;            
        }                                            
        
        List<TupleProductos> rutasProductos;
        if(!distanciaCalculada||recarcularRutasCheck.isSelected()) 
        {
            rutasProductos = new ArrayList<>();
            calcularDistancias(capacidadCarro, acopio,rutasProductos);            
        }else
        {
            rutasProductos = simulacionActual.getRutasProductos();            
        }
        
        if(acopioAnterior != null && !acopioAnterior.isEqual(acopio))
        {
            // Actualizar el punto
            productosSimulacion.get(0).setPunto(acopio);
            for(int i = 1;i<productosSimulacion.size();i++)
            {              
                Celda.TIPO[][] mapaActual = copiarMapa(mapa,height,width);                                           
                Estado ruta = rutaDetallada.obtenerRutaOptima(mapaActual, height, width, acopio, productosSimulacion.get(i).getPunto());               
                distancias[0][i] = ruta.getCost();
                distancias[i][0] = ruta.getCost();  
                rutasProductos = copiarProductosRutas(rutasProductos);
                replaceRuta(productosSimulacion.get(0),productosSimulacion.get(i),ruta,rutasProductos);
            }         
        }
        
        acopioAnterior = acopio;
        rutaGeneral = new RutaGeneral(productosSimulacion.size()-1, distancias, pesos, capacidadCarro);
        rutaGeneral.CorrerAlgoritmo();               
        Simulacion nuevaSimulacion = new Simulacion(rutaGeneral,rutasProductos, acopio, capacidadCarro,productosSimulacion.size()-1);        
        simulacionActual = nuevaSimulacion;
        
        VerCostoRuta.setText(String.valueOf(simulacionActual.getCostoRutaActual()));
        VerCostoSimulacion.setText(String.valueOf(simulacionActual.getCosto()));                     
        grid.clearUserTilesColor(colorActual);
        impimirRuta(simulacionActual.getRutaActual(),rutasProductos);
        fillListUpTo(nrRutas, simulacionActual.getNrRutas());                  
        } catch (Exception ex) {
            Logger.getLogger(SimulacionesController.class.getName()).log(Level.SEVERE, null, ex);
        }                              
    }
    
    private List<TupleProductos> copiarProductosRutas(List<TupleProductos> rutas)
    {
        List<TupleProductos> nuevasRutas = new ArrayList<>();        
        for(TupleProductos tuple: rutas)
        {
            nuevasRutas.add(new TupleProductos(tuple));
        }
        return  nuevasRutas;
    }
    
    public Boolean calcularDistancias(Double capacidadCarro,Punto acopio,List<TupleProductos> rutasProductos)
    {               
        productosSimulacion.get(0).setPunto(acopio);
        pesos.clear();
        for(Producto producto:productos)
        {
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
    
    private void replaceRuta(ProductoSimulacion pr1,ProductoSimulacion pr2,Estado estado,List<TupleProductos> rutasProductos)
    {
        TupleProductos tupla = rutasProductos.stream().filter(x -> x.esPar(pr1, pr2)).findFirst().get();
        tupla.setRuta(estado);        
    }
    
    private void impimirRuta(List<ProductoNodo> rutaList,List<TupleProductos> rutasProductos)
    {           
        for(ProductoNodo producto:rutaList)
        {
            TupleProductos tupla = rutasProductos.stream().filter(x -> x.esPar(productosSimulacion.get(producto.GetLlave()), productosSimulacion.get(producto.sig.GetLlave()))).findFirst().get();                       
            dibujarMapa(tupla.getEstado(),tupla.getColor());           
            grid.pintarTileDeletable(tupla.getProductoUno().getPunto().x, tupla.getProductoUno().getPunto().y, tupla.getProductoUno().getColor());
            grid.pintarTileDeletable(tupla.getProductoDos().getPunto().x, tupla.getProductoDos().getPunto().y, tupla.getProductoDos().getColor());
            if(producto.sig.EsDeposito()) break;
            
        }
    }
    
    private void imprimirRutaSaved(String ruta)
    {
        List<String> elements = Arrays.asList(ruta.split(","));
                
        Punto actual = new Punto(elements.get(0));
        Color colorActual = Color.BLACK;
        
        int nrElemeents = elements.size();
        
        for(int i = 1;i<nrElemeents;i++)
        {
            Color color = getColor(elements.get(i++));
            gridSaved.pintarTileDeletable(actual.x, actual.y,color);            
            
            colorActual = getColor(elements.get(i++));
            
            for(int j = 0;j<elements.get(i).length();j++)
            {
                char c = elements.get(i).charAt(j);
                actual.moverA(c);        
                gridSaved.pintarTileDeletable(actual.x, actual.y, colorActual);               
            }
        }                
    }
    
    public Color getColor(String clr)
    {
        List<String> punto = Arrays.asList(clr.split("-"));
        Integer r = Integer.valueOf(punto.get(0));
        Integer g = Integer.valueOf(punto.get(1));        
        Integer b = Integer.valueOf(punto.get(2));     
        
        return Color.rgb(r, g, b);
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

    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Simulaciones;
    }

    @FXML
    private void cambiarFondo(ActionEvent event) {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();                      
        colorActual = Color.color(r, g, b);
        grid.changeNullToColor(colorActual);
    }

    @FXML
    private void cambiarFondoSaved(ActionEvent event) {
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();  
        colorActualSaved = Color.color(r, g, b);
        gridSaved.changeNullToColor(colorActualSaved);         
    }

}
