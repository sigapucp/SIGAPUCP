/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.DocVenta;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarDocVentasArgs;
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
 * @author Jauma
 */
public class AgregarDocVentasController implements Initializable{
    
    private DocVenta DocVentaBuscar = null;
    private InformationAlertController infoController;
    private final ObservableList<DocVenta> docVentas = FXCollections.observableArrayList();    
    
    
    public AgregarDocVentasController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();        
    }
    
    @FXML
    private TableView<DocVenta> TablaDocVentas;

    @FXML
    private TableColumn<DocVenta, String> ColumnaCodigo;

    @FXML
    private TableColumn<DocVenta, String> ColumnaCliente;

    @FXML
    private TableColumn<DocVenta, String> ColumnaTipo;

    @FXML
    private TableColumn<DocVenta, String> ColumnaGuiaRemision;

    @FXML
    private TextField BuscarCodigo;

    @FXML
    private TextField BuscarGuia;

    @FXML
    private ComboBox<String> BuscarTipo;

    @FXML
    private TextField BuscarCliente;

    @FXML
    private Button buscarDocVentaButon;

    @FXML
    private Button agregarDocVenta;

    @FXML
    void agregarDocVenta(ActionEvent event) {
        DocVentaBuscar = TablaDocVentas.getSelectionModel().getSelectedItem();
        if(DocVentaBuscar==null)
        {
            infoController.show("No ha seleccionado ningun Documento de Venta");
            return;
        }
        
        devolverDocVentaEvent.fire(this, new agregarDocVentasArgs(DocVentaBuscar));
        Stage stage = (Stage) agregarDocVenta.getScene().getWindow();    
        stage.close();
    }

    @FXML
    void buscarDocVenta(ActionEvent event) {
        
        String codigo = BuscarCodigo.getText();
        String cliente = BuscarCliente.getText();
        String tipo = BuscarTipo.getSelectionModel().getSelectedItem();    
        String guia = BuscarGuia.getText();
                
        List<DocVenta> tempOrdenes = DocVenta.findAll();
        
        if(codigo!=null&&!codigo.isEmpty())
        {            
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("doc_venta_cod").equals(codigo)).collect(Collectors.toList());
        }

        if(cliente!=null&&!cliente.isEmpty())
        {            
            tempOrdenes = tempOrdenes.stream().filter(p -> Cliente.findById(p.get("client_id")).getString("nombre").equals(cliente)).collect(Collectors.toList());
        }
                
        if(tipo!=null&&!tipo.isEmpty())
        {
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("tipo").equals(tipo)).collect(Collectors.toList());
        }
        
        if(guia!=null&&!guia.isEmpty())
        {
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("guia_cod").equals(guia)).collect(Collectors.toList());
        }
      
        RefrescarTabla(tempOrdenes);
        try {                        
        } catch (Exception e) {
            infoController.show("La Orden contiene errores : " + e);                    
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<DocVenta, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("docv_enta_cod")));
        ColumnaCliente.setCellValueFactory((TableColumn.CellDataFeatures<DocVenta, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        ColumnaTipo.setCellValueFactory((TableColumn.CellDataFeatures<DocVenta, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo")));
        ColumnaGuiaRemision.setCellValueFactory((TableColumn.CellDataFeatures<DocVenta, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("guia_cod")));
        
        ObservableList<String> tipos = FXCollections.observableArrayList();            
        tipos.add("");
        tipos.addAll(Arrays.asList(DocVenta.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()));   
        BuscarTipo.setItems(tipos);
        
        List<DocVenta> tempOrdenes = DocVenta.findAll();
        docVentas.addAll(tempOrdenes);
        TablaDocVentas.setItems(docVentas);                   
    }
    
      private void RefrescarTabla(List<DocVenta> docVentaRefresh)
    {        
        try {
            docVentas.removeAll(docVentas);                 
            if(docVentaRefresh == null) return;
            for (DocVenta docVenta : docVentaRefresh) {
                docVentas.add(docVenta);
            }               
            TablaDocVentas.getColumns().get(0).setVisible(false);
            TablaDocVentas.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Los documentos de venta errores : " + e.getMessage());      
        }                                  
    }    
      
    public Event<agregarDocVentasArgs> devolverDocVentaEvent = new Event<>();       
}
