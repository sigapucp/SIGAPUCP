/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Flete;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarClienteArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class AgregarClientesController implements Initializable {

    @FXML
    private TableView<Cliente> TablaClientes;
    @FXML
    private TableColumn<Cliente, String> ColumnaNombre;
    @FXML
    private TableColumn<Cliente, String> ColumnaTipo;
    @FXML
    private TableColumn<Cliente, String> ColumnaRUC;
    @FXML
    private TableColumn<Cliente, String> ColumnaDNI;
    @FXML
    private TableColumn<Cliente, String> ColumnaDpt;
    @FXML
    private TextField BuscarNombre;
    @FXML
    private ComboBox<String> BuscarTipo;
    @FXML
    private TextField BuscarRuc;
    @FXML
    private TextField BuscarDNI;
    @FXML
    private ComboBox<String> BuscarDepartamento;
    @FXML
    private Button buscarCliente;
    @FXML
    private Button agregarClienteButtom;
    
    ArrayList<String> possiblewords = new ArrayList<>();        
    AutoCompletionBinding<String> autoCompletionBinding; 
    private List<Cliente> autoCompletadoList;
    /**
     * Initializes the controller class.
     */
    private Cliente ClienteBusqueda = null;
    private InformationAlertController infoController;
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();         
    
    public AgregarClientesController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
    }      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {                        
            ColumnaNombre.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
            ColumnaTipo.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cliente")));
            ColumnaRUC.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper((p.getValue().get("tipo_cliente").equals(Cliente.TIPO.PersonaNatural.name())) ? p.getValue().get("dni") : "--"));
            ColumnaRUC.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("dni")));            
            ColumnaDpt.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("departamento")));

            ObservableList<String> tipos = FXCollections.observableArrayList();                                           
            ObservableList<String> departamentos = FXCollections.observableArrayList(); 

            departamentos.add(""); 
            departamentos.addAll(Arrays.asList(Flete.ZONA.values()).stream().map(x->x.name()).collect(Collectors.toList()));   

            tipos.add("");
            tipos.addAll(Arrays.asList(Cliente.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()));   


            BuscarTipo.setItems(tipos);
            BuscarDepartamento.setItems(departamentos);            
            List<Cliente> tempCliente = Cliente.where("estado = ?", Cliente.ESTADO.ACTIVO.name());
            clientes.addAll(tempCliente);
            TablaClientes.setItems(clientes);   
            llenar_autocompletado();
        } catch (Exception e) {
            infoController.show("Problemas en la inicializaciond de busqueda de Clientes");
        }       
    }   
    
      private void llenar_autocompletado() throws Exception
    {
        autoCompletadoList = Cliente.findAll();

        clienteToString();
        autoCompletionBinding = TextFields.bindAutoCompletion(BuscarNombre, possiblewords);
        autoCompletionBinding.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletar();
        });        
    }
    
      private void RefrescarTabla(List<Cliente> clienteRefresh)
    {        
        try {
            clientes.removeAll(clientes);                 
            if(clienteRefresh == null) return;
            for (Cliente cliente : clienteRefresh) {
                clientes.add(cliente);
            }               
            TablaClientes.getColumns().get(0).setVisible(false);
            TablaClientes.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Los clientes contienen errores : " + e.getMessage());      
        }                                  
    }

    @FXML
    private void buscarCliente(ActionEvent event) {
        String ruc = BuscarRuc.getText();
        String dni = BuscarDNI.getText();
        String nombre = BuscarNombre.getText();
        String tipo = BuscarTipo.getSelectionModel().getSelectedItem();
        String dpt = BuscarDepartamento.getSelectionModel().getSelectedItem();
                
        List<Cliente> temp_clientes = Cliente.where("estado = ?",Cliente.ESTADO.ACTIVO.name());
        
        if(ruc!=null&&!ruc.isEmpty())
        {            
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("ruc").equals(ruc)).collect(Collectors.toList());
        }

        if(dni!=null&&!dni.isEmpty())
        {            
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("dni").equals(ruc)).collect(Collectors.toList());
        }
                
        if(nombre!=null&&!nombre.isEmpty())
        {
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("nombre").equals(nombre)).collect(Collectors.toList());
        }

        if(tipo!=null&&!tipo.isEmpty())
        {
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("tipo_cliente").equals(tipo)).collect(Collectors.toList());
        }   
        
         if(dpt!=null&&!dpt.isEmpty())
        {
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("departamento").equals(dpt)).collect(Collectors.toList());
        }      
        RefrescarTabla(temp_clientes);
        try {                        
        } catch (Exception e) {
            infoController.show("El Cliente contiene errores : " + e);                    
        }
    }

    @FXML
    private void agregarCliente(ActionEvent event) {
        ClienteBusqueda = TablaClientes.getSelectionModel().getSelectedItem();
        if(ClienteBusqueda==null)
        {
            infoController.show("No ha seleccionado ningun producto");
            return;
        }
        devolverClienteEvent.fire(this, new agregarClienteArgs(ClienteBusqueda));
        Stage stage = (Stage) agregarClienteButtom.getScene().getWindow();    
        stage.close();
    }
    
    public Event<agregarClienteArgs> devolverClienteEvent = new Event<>();          

    private void clienteToString() throws Exception{
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoList){
            words.add(cliente.getString("nombre"));
        }
        possiblewords = words;
    }

    private void handleAutoCompletar() {
        
        try {            
             for (Cliente cliente : autoCompletadoList){
                if (cliente.getString("nombre").equals(BuscarNombre.getText())){                            
                    //setInformacionCliente(cliente,true);
                    TablaClientes.getColumns().get(0).setVisible(false);
                    TablaClientes.getColumns().get(0).setVisible(true);
                }
            }            
        } catch (Exception e) {
            infoController.show("No se ha podido cargar la informacion del cliente: " + e.getMessage());
        }
       
    }

    private void setInformacionCliente(Cliente cliente, boolean b) {
        String tipo_cliente = cliente.getString("tipo_cliente");
        String dni = cliente.getString("dni");
        String ruc = cliente.getString("ruc");
        
        if(tipo_cliente.equals(Cliente.TIPO.PersonaNatural.name()))
        {
            BuscarDNI.setText(dni);
            BuscarTipo.setValue(cliente.getString("tipo"));
            BuscarDepartamento.setValue(cliente.getString("departamento")); 
        }else
        {
            BuscarRuc.setText(ruc); 
            BuscarTipo.setValue(cliente.getString("tipo"));
            BuscarDepartamento.setValue(cliente.getString("departamento")); 
        } 
    }
}
