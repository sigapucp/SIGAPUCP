/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Kardex;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalidaxProductoFinal;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
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
    private TextField producto_categoria;
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
    private List<OrdenSalidaxProductoFinal> salidas;
    
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
    
    private void cargarEntradas(){
        entradas = OrdenEntradaxProducto.where("tipo_id = ? AND tipo_cod = ?",producto_seleccionado.get("tipo_id"),producto_seleccionado.get("tipo_cod"));
        for (OrdenEntradaxProducto entrada_producto: entradas){
            OrdenEntrada entrada_padre = OrdenEntrada.findFirst("orden_entrada_id = ?", entrada_producto.get("orden_entrada_id"));
            
            Date auxfecha = entrada_padre.getDate("fecha_emision");
            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(auxfecha);
            String detalle = entrada_padre.getString("tipo");
            //String ent_cant
            
            //masterDatakardex.add(new Kardex(fecha, detalle, ent_cant, ent_costo, sal_cant, sal_costo, exi_cant, exi_costo));
        }
    }
    
    private void cargarSalidas(){
        salidas = OrdenSalidaxProductoFinal.where("tipo_id = ? AND tipo_cod = ?",producto_seleccionado.get("tipo_id"),producto_seleccionado.get("tipo_cod"));
    }
    
    private void mostrarEntradasYSalidas(){
        cargarEntradas();
        cargarSalidas();
        
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
