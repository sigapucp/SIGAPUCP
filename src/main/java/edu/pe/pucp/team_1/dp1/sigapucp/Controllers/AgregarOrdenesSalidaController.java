/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenSalidaArgs;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Jauma
 */
public class AgregarOrdenesSalidaController extends Controller{
    
    @FXML
    private TableView<OrdenSalida> TablaOrdenes;

    @FXML
    private TableColumn<OrdenSalida, String> ColumnaCodigo;

    @FXML
    private TableColumn<OrdenSalida, String> ColumnaCliente;

    @FXML
    private TableColumn<OrdenSalida, String> ColumnaTipo;

    @FXML
    private TableColumn<OrdenSalida, String> ColumnaDescripcion;

    @FXML
    private TextField BuscarCodigo;

    @FXML
    private ComboBox<String> BuscarTipo;

    @FXML
    private TextField BuscarCliente;

    @FXML
    private Button buscarCliente;

    @FXML
    private Button agregarOrdenButtom;

    private OrdenSalida OrdenSalidaBusqueda = null;
    private InformationAlertController infoController;
    private final ObservableList<OrdenSalida> ordenes = FXCollections.observableArrayList();    
    
    public AgregarOrdenesSalidaController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_salid_cod")));
        ColumnaCliente.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        ColumnaTipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo")));
        ColumnaTipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));
        
        ObservableList<String> tipos = FXCollections.observableArrayList();            
        tipos.add("");
        tipos.addAll(Arrays.asList(OrdenSalida.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()));   
        BuscarTipo.setItems(tipos);
        
        List<OrdenSalida> tempOrdenes = OrdenSalida.where("estado = ?", OrdenSalida.ESTADO.ENPROCESO.name());
        ordenes.addAll(tempOrdenes);
        TablaOrdenes.setItems(ordenes);           
    }
    
    private void RefrescarTabla(List<OrdenSalida> ordenesRefresh)
    {        
        try {
            ordenes.removeAll(ordenes);                 
            if(ordenesRefresh == null) return;
            for (OrdenSalida orden : ordenesRefresh) {
                ordenes.add(orden);
            }               
            TablaOrdenes.getColumns().get(0).setVisible(false);
            TablaOrdenes.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Las ordenes contienen errores : " + e.getMessage());      
        }                                  
    }
    
    @FXML
    void agregarOrden(ActionEvent event) {
        OrdenSalidaBusqueda = TablaOrdenes.getSelectionModel().getSelectedItem();
        if(OrdenSalidaBusqueda==null)
        {
            infoController.show("No ha seleccionado ninguna Orden");
            return;
        }
        
        devolverOrdenEvent.fire(this, new agregarOrdenSalidaArgs(OrdenSalidaBusqueda));
        Stage stage = (Stage) agregarOrdenButtom.getScene().getWindow();    
        stage.close();
    }

    @FXML
    void buscarOrden(ActionEvent event) {        
        String codigo = BuscarCodigo.getText();
        String cliente = BuscarCliente.getText();
        String tipo = BuscarTipo.getSelectionModel().getSelectedItem();        
                
        List<OrdenSalida> tempOrdenes = OrdenSalida.where("estado = ?",OrdenSalida.ESTADO.ENPROCESO.name());
        
        if(codigo!=null&&!codigo.isEmpty())
        {            
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("producto_cod").equals(codigo)).collect(Collectors.toList());
        }

        if(cliente!=null&&!cliente.isEmpty())
        {            
            tempOrdenes = tempOrdenes.stream().filter(p -> Cliente.findById(p.get("client_id")).getString("nombre").equals(cliente)).collect(Collectors.toList());
        }
                
        if(tipo!=null&&!tipo.isEmpty())
        {
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("tipo").equals(tipo)).collect(Collectors.toList());
        }
      
        RefrescarTabla(tempOrdenes);
        try {                        
        } catch (Exception e) {
            infoController.show("El Cliente contiene errores : " + e);                    
        }
    }
    
    public Event<agregarOrdenSalidaArgs> devolverOrdenEvent = new Event<>();          
    
}
