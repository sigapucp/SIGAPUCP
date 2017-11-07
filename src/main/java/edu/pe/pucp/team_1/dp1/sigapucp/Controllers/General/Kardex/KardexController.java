/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Kardex;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenesSalidaxEnvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Precio;
import java.io.IOException;
import java.util.stream.Collectors;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Hugo
 */
public class KardexController extends Controller {
    
    @FXML
    private AnchorPane kardexProducto;
    //Búsqueda
    @FXML
    private TextField tipoProductoBuscar;  
    @FXML
    private TextField codigoProductoBuscar;  
    @FXML
    private TableView<TipoProducto> tablaProductos; 
    @FXML
    private TableColumn<TipoProducto,String> ColumnaCodigoProducto;
    @FXML
    private TableColumn<TipoProducto,String> ColumnaTipoProducto;
    
    //Formulario    
    @FXML
    private TextField producto_nombre;
    @FXML
    private TextField producto_codigo;
    @FXML
    private TableView<Kardex> tablaKardex;
    @FXML
    private TableColumn<Kardex, String> columna_fecha;
    @FXML
    private TableColumn<Kardex, String> columna_detalle;
    @FXML
    private TableColumn<Kardex, String> columna_ent_cant;
    @FXML
    private TableColumn<Kardex, String> columna_ent_costo;
    @FXML
    private TableColumn<Kardex, String> columna_sal_cant;
    @FXML
    private TableColumn<Kardex, String> columna_sal_costo;
    @FXML
    private TableColumn<Kardex, String> columna_exi_cant;
    @FXML
    private TableColumn<Kardex, String> columna_exi_costo;
    
    
    //Adicionales
    private List<TipoProducto> tipos;
    private final ObservableList<TipoProducto> masterData = FXCollections.observableArrayList();
    private InformationAlertController infoController;
    private TipoProducto producto_seleccionado ;
    private final ObservableList<Kardex> masterDatakardex = FXCollections.observableArrayList();
    private List<OrdenEntradaxProducto> entradas;
    private List<OrdenSalida> salidas;
    private Integer existencias_cantidad;
    private Double existencias_precio;
    
    
    public void inhabilitar_formulario(){
        kardexProducto.setDisable(true);
    }
    
    public void habilitar_formulario(){
        kardexProducto.setDisable(false);
    }
    
    private void cargar_tabla_index(){
        tablaProductos.getItems().clear();
        masterData.clear();
        for (TipoProducto tipo : tipos){
            masterData.add(tipo);
        }
        tablaProductos.setEditable(false);
        ColumnaTipoProducto.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        ColumnaCodigoProducto.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        tablaProductos.getItems().addAll(masterData);
    }
    
    private Double obtenerPrecio(String id, String codigo, Date fecha){
        Double precio = 0.0;
        List<Precio> precios = Precio.findAll();
        
        for (Precio p : precios){
            if ((p.get("tipo_id").toString().equals(id)) && (p.get("tipo_cod").toString().equals(codigo)) && (fecha.after(p.getDate("fecha_inicio")) && (fecha.before(p.getDate("fecha_fin"))))){
                precio = p.getDouble("precio");
                break;
            }
        }        
        return precio;
    }
    
    private void cargarStock(String detalle){        
        Date auxfecha = producto_seleccionado.getDate("last_date_change");
        
        existencias_precio = obtenerPrecio(producto_seleccionado.get("tipo_id").toString(), producto_seleccionado.getString("tipo_cod"), auxfecha);
        
        Double costo = existencias_cantidad * existencias_precio;        
        
        masterDatakardex.add(new Kardex("", detalle, "", "", "", "", existencias_cantidad.toString(), costo.toString()));
    }
    
    private void cargarEntradas(){
        entradas = OrdenEntradaxProducto.where("tipo_id = ? AND tipo_cod = ?",producto_seleccionado.get("tipo_id"),producto_seleccionado.get("tipo_cod"));
        for (OrdenEntradaxProducto entrada_producto: entradas){
            OrdenEntrada entrada_padre = OrdenEntrada.findFirst("orden_entrada_id = ?", entrada_producto.get("orden_entrada_id"));
            //fecha
            Date auxfecha = entrada_padre.getDate("fecha_emision");
            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(auxfecha);
            //detalle
            String detalle = entrada_padre.getString("tipo");
            //Cantidad 
            Integer aux_ent_cant = entrada_producto.getInteger("cantidad");
            String ent_cant = aux_ent_cant.toString();
            
            Double precio = obtenerPrecio(entrada_producto.get("tipo_id").toString(), entrada_producto.getString("tipo_cod"), auxfecha);
            //Costo
            Double aux_ent_costo = aux_ent_cant * precio;
            String ent_costo = aux_ent_costo.toString();
            
            String sal_cant = "";
            String sal_costo = "";
            
            //Stock (Existencias)
            existencias_cantidad += aux_ent_cant;
            
            String exi_cant = existencias_cantidad.toString() ;
            
            Double aux_exi_costo = existencias_cantidad * existencias_precio;
            String exi_costo = aux_exi_costo.toString(); 
            
            masterDatakardex.add(new Kardex(fecha, detalle, ent_cant, ent_costo, sal_cant, sal_costo, exi_cant, exi_costo));
        }
    }
    
    private void cargarSalidas(){
        salidas = OrdenSalida.findAll();
        for (OrdenSalida orden_salida: salidas){
            
            List<OrdenesSalidaxEnvio> ordenesXenvio = OrdenesSalidaxEnvio.where("salida_id = ?", orden_salida.getInteger("salida_id"));
            Integer aux_sal_cant = 0;
            
            for (OrdenesSalidaxEnvio envio : ordenesXenvio){
                Integer id = envio.getInteger("orden_compra_id");
                Integer id_client = envio.getInteger("client_id");
                String cod = envio.getString("orden_compra_cod");
                
                OrdenCompraxProducto producto = OrdenCompraxProducto.findFirst("client_id = ? AND orden_compra_id = ? AND orden_compra_cod = ? AND tipo_id = ? AND tipo_cod = ?",id_client,id,cod,producto_seleccionado.getInteger("tipo_id"),producto_seleccionado.getString("tipo_cod") );
                
                aux_sal_cant += producto.getInteger("cantidad");
            }
            
            //fecha
            Date auxfecha = orden_salida.getDate("last_data_change");
            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(auxfecha);
            //detalle
            String detalle = orden_salida.getString("tipo");
             
            String ent_cant = "";
            String ent_costo = "";            
            
            //Cantidad
            
            String sal_cant = aux_sal_cant.toString();
            
            Double precio = obtenerPrecio(producto_seleccionado.get("tipo_id").toString(), producto_seleccionado.getString("tipo_cod"), auxfecha);
            
            //Costo
            Double aux_sal_costo = aux_sal_cant * precio;
            String sal_costo = aux_sal_costo.toString();
            
            //Stock (Existencias)
            existencias_cantidad -= aux_sal_cant;
            
            String exi_cant = existencias_cantidad.toString() ;
            
            Double aux_exi_costo = existencias_cantidad * existencias_precio;
            String exi_costo = aux_exi_costo.toString();
            
            masterDatakardex.add(new Kardex(fecha, detalle, ent_cant, ent_costo, sal_cant, sal_costo, exi_cant, exi_costo));
        }
    }
    
    private void mostrarEntradasYSalidas(){
        masterDatakardex.clear();
        
        cargarStock("Stock inicial");
        cargarEntradas();
        cargarSalidas();
        cargarStock("Stock final");
        
        columna_fecha.setCellValueFactory(cellData -> cellData.getValue().getFecha());
        columna_detalle.setCellValueFactory(cellData -> cellData.getValue().getDetalle());
        columna_ent_cant.setCellValueFactory(cellData -> cellData.getValue().getEnt_cant());
        columna_ent_costo.setCellValueFactory(cellData -> cellData.getValue().getEnt_costo());
        columna_sal_cant.setCellValueFactory(cellData -> cellData.getValue().getSal_cant());
        columna_sal_costo.setCellValueFactory(cellData -> cellData.getValue().getSal_costo());
        columna_exi_cant.setCellValueFactory(cellData -> cellData.getValue().getExi_cant());
        columna_exi_costo.setCellValueFactory(cellData -> cellData.getValue().getExi_costo());
        
        FilteredList<Kardex> filteredData = new FilteredList<>(masterDatakardex, p -> true);
        tablaKardex.setItems(filteredData);
    }
    
    @FXML
    public void mostrar_detalle_tipo_producto(ActionEvent event) throws IOException{
        producto_seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if(producto_seleccionado == null) return; 
        habilitar_formulario();
        producto_nombre.setText(producto_seleccionado.getString("nombre"));
        producto_codigo.setText(producto_seleccionado.getString("tipo_cod"));
        //Cargar entradas y salidas
        existencias_cantidad = 0;
        existencias_precio = 0.0;
        mostrarEntradasYSalidas();
    }  
    
    @FXML
    public void buscar_producto(ActionEvent event)throws IOException{
        String codigo_producto = codigoProductoBuscar.getText();
        String nombre_producto = tipoProductoBuscar.getText();
        List<TipoProducto> temp_productos = TipoProducto.findAll();
        
        if(codigo_producto!=null&&!codigo_producto.isEmpty())
        {            
            temp_productos = temp_productos.stream().filter(p -> p.getString("tipo_cod").equals(codigo_producto)).collect(Collectors.toList());
        }

        if(nombre_producto!=null&&!nombre_producto.isEmpty())
        {            
            temp_productos = temp_productos.stream().filter(p -> p.getString("nombre").equals(nombre_producto)).collect(Collectors.toList());
        }
                   
        tipos = temp_productos;
        cargar_tabla_index();
        try {                        
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);                    
        }        
    }
    
    public KardexController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        producto_seleccionado = null;
        infoController = new InformationAlertController();   
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inhabilitar_formulario();
        tipos = TipoProducto.findAll();
        cargar_tabla_index();
        /*
        tablaProductos.getSelectionModel().selectedIndexProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null){
                producto_seleccionado = tablaProductos.getSelectionModel().getSelectedItem();                
                tablaProductos.getSelectionModel().clearSelection();        
            }
        });        
        */
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Kardex;
    }
}
