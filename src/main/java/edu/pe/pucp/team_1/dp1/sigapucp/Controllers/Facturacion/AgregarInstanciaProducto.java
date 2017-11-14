/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarInstanciaProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class AgregarInstanciaProducto implements Initializable {
    @FXML
    private TextField BuscarCodigo;
    @FXML
    private TextField BuscarNombre;
    @FXML
    private ComboBox<String> combobox_tipo;
    @FXML
    private TableView<Producto> tabla_producto;
    @FXML
    private TableColumn<Producto, String> columna_codigo;
    @FXML
    private TableColumn<Producto, String> columna_nombre;
    @FXML
    private TableColumn<Producto, String> columna_fecha_entrada;
    @FXML
    private TableColumn<Producto, String> columna_almacen;
    @FXML
    private TableColumn<Producto, String> columna_rack;
    @FXML
    private Button agregarProductoButtom;

    private InformationAlertController infoController;
    private List<Producto> instancias_productos;
    private final ObservableList<Producto> masterDataProducto = FXCollections.observableArrayList();
    private Producto producto_busqueda;
 
    public void llenar_productos_tabla(){
        try{            
            masterDataProducto.addAll(instancias_productos);
            
            columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("producto_cod")));
            columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?",p.getValue().getInteger("tipo_id")).getString("nombre")));
            columna_fecha_entrada.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_entrada")));
            columna_almacen.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("almacen_cod")));
            columna_rack.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod")));
            
            tabla_producto.setItems(masterDataProducto);
        }catch(Exception e){            
            System.out.println(e);
        }
    }    
    
    @FXML
    private void agregarProducto(ActionEvent event) {
        producto_busqueda = tabla_producto.getSelectionModel().getSelectedItem();
        if(producto_busqueda==null)
        {
            infoController.show("No ha seleccionado ningun producto");
            return;
        }

        devolver_instancia_producto.fire(this, new agregarInstanciaProductoArgs(producto_busqueda));
        Stage stage = (Stage) agregarProductoButtom.getScene().getWindow();    
        stage.close();
    }    
    
    public AgregarInstanciaProducto(List<Producto> instancias_productos)
    {
        try{
            if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
            infoController = new InformationAlertController();
            this.instancias_productos = instancias_productos;
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    public boolean cumple_condicion_busqueda(Producto producto, String codigo, String nombre, String tipo){
        boolean match = true;        
        if ( codigo.equals("") && nombre.equals("") && tipo.equals("")){
            match = true;
        }else {
            match = (!codigo.equals("")) ? (match && (producto.get("tipo_cod")).equals(codigo)) : match;
            match = (!nombre.equals("")) ? (match && (TipoProducto.findById(producto.get("tipo_id")).getString("nombre").equals(nombre))) : match;
            match = (!tipo.equals("")) ? (match && (TipoProducto.findById(producto.get("tipo_id")).getString("nombre").equals(tipo))) : match;
        }
        return match;
    }
    
    
    @FXML
    public void buscar_producto(ActionEvent event) throws IOException{
        masterDataProducto.clear();
        String tipo = (combobox_tipo.getSelectionModel().getSelectedItem()==null) ? "" : combobox_tipo.getSelectionModel().getSelectedItem();
        try{
            for(Producto producto : masterDataProducto){
                if (cumple_condicion_busqueda(producto, BuscarCodigo.getText(), BuscarNombre.getText(),tipo)){
                    masterDataProducto.add(producto);
                }
            }
        }catch(Exception e){
            infoController.show("No se pudo buscar el producto : " + e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenar_productos_tabla();
    }   
    public Event<agregarInstanciaProductoArgs> devolver_instancia_producto = new Event<>();          

}
