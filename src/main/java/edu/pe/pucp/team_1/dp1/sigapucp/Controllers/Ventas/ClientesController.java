/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import java.io.File;
import java.io.FileNotFoundException;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Flete;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.util.stream.Collectors;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class ClientesController extends Controller{

    @FXML
    private TextField dniBusq;
    @FXML
    private Button buscafBttn;
    @FXML
    private ComboBox estadoBusq;
    @FXML
    private TextField rucBusq;
    @FXML
    private TextField nombreBusq;
    @FXML
    private TextField clienteSh;
    @FXML
    private TextField dni;
    @FXML
    private TextField envioDir;
    @FXML
    private TextField factDir;
    @FXML
    private CheckBox persoNatu;
    @FXML
    private CheckBox persoJuri;
    @FXML
    private TextField ruc;
    @FXML
    private TextField repLegal;
    @FXML
    private TextField telf;
    @FXML
    private AnchorPane cliente_formulario;
    
    
    @FXML
    private TableColumn<Cliente, String> columna_ruc;
    @FXML
    private TableColumn<Cliente, String> columna_dni;
    @FXML
    private TableColumn<Cliente, String> columna_nombre;
    @FXML
    private TableView<Cliente> tabla_clientes;
    
    private Cliente cliente_seleccioando;
    private Boolean crear_nuevo;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private List<Cliente> clientes;
    private final ObservableList<Cliente> masterData = FXCollections.observableArrayList();
    @FXML
    private AnchorPane mostrar_detalle_cliente;
    @FXML
    private ComboBox<String> VerDepartamento;
    /**
     * Initializes the controller class.
     */
    
    public ClientesController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
                
        cliente_seleccioando = null;
        crear_nuevo = false;        
    }
    
    public String obtener_tipo_cliente(){
        String tipo_cliente = "";
        tipo_cliente = (persoNatu.isSelected()) ? Cliente.TIPO.PersonaNatural.name() : Cliente.TIPO.PersonaJuridica.name();        
        return tipo_cliente;
    }
    
    public void crear_cliente(){
        try{
            cliente_seleccioando = null;
            Base.openTransaction();  
            Cliente nuevo_cliente = new Cliente();
            if(!confirmatonController.show("Se creará el cliente con nombre: " + clienteSh.getText(), "¿Desea continuar?")) return;
            nuevo_cliente.asignar_atributos(clienteSh.getText(), repLegal.getText(), telf.getText(), ruc.getText(), dni.getText(), obtener_tipo_cliente(), envioDir.getText(), factDir.getText());
            nuevo_cliente.set("last_user_change",usuarioActual.get("usuario_cod"));
            nuevo_cliente.set("departamento",VerDepartamento.getSelectionModel().getSelectedItem());
            if (nuevo_cliente.is_valid()){
                nuevo_cliente.saveIt();
                Base.commitTransaction();
                infoController.show("El cliente ha sido creado satisfactoriamente"); 
            }else{
                infoController.show("El cliente contiene errores"); 
            }
        }
        catch(Exception e){
            System.out.println(e);
            infoController.show("El cliente contiene errores :"+ e); 
            Base.rollbackTransaction();
        }finally{
            crear_nuevo = false;
        }        
    }
    
    public void editar_cliente(Cliente cliente){
        try{
            Base.openTransaction();  
            if(!confirmatonController.show("Se editará el cliente con nombre: " + clienteSh.getText(), "¿Desea continuar?")) return;
            cliente.asignar_atributos(clienteSh.getText(), repLegal.getText(), telf.getText(), ruc.getText(), dni.getText(), obtener_tipo_cliente(), envioDir.getText(), factDir.getText());
            cliente.set("last_user_change",usuarioActual.get("usuario_cod"));
            cliente.set("departamento",VerDepartamento.getSelectionModel().getSelectedItem());
            cliente.saveIt();
            Base.commitTransaction();
            infoController.show("El cliente ha sido editado creado satisfactoriamente"); 
        }
        catch(Exception e){
            Base.rollbackTransaction();
        }
    }
    
    @Override
    public void cargar(){
        //validamos que los campos sean los correctos
        if(!confirmatonController.show("Verifique que el formato del archivo .csv sea: \n nombre cliente,rep legal,telefono,ruc,dni,tipo cliente,dir. despacho, dir. facturacion, departamento,", "¿Desea continuar?")) return;
        //csv
        String filename = "data_clientes.csv";
        File file = new File(filename);
        Boolean primera_fila = true;
        try {
            Scanner inputStream = new Scanner(file);
 
            while (inputStream.hasNext()){
                String data = inputStream.nextLine();
                //manejo de la data aquí:
                String [] values = data.split(",");
                if (primera_fila) {
                    primera_fila = false;
                    if (values.length != 9) {
                        infoController.show("El archivo .csv no tiene el formato adecuado. Verifique que sea\nnombre cliente,rep legal,telefono,ruc,dni,tipo cliente,dir. despacho, dir. facturacion, departamento,"); 
                        return;
                    }                    
                    continue; //nos saltamos el encabezado
                }
                Base.openTransaction();
                Cliente nuevo_cliente = new Cliente();
                nuevo_cliente.asignar_atributos(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                nuevo_cliente.set("last_user_change",usuarioActual.get("usuario_cod"));
                nuevo_cliente.setString("departamento", values[8]);
                nuevo_cliente.saveIt();
                Base.commitTransaction();
                System.out.println("CORRECTO");
            }
            infoController.show("¡Carga masiva de datos de clientes exitosa!");
            inputStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("INCORRECTO");
            Base.rollbackTransaction();
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void guardar() {
        if (crear_nuevo){            
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Clientes, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crear_nuevo = false;
                return;
            }
            crear_cliente();
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.Clientes ,this.usuarioActual);
            limpiar_formulario();
        }else{            
            if (cliente_seleccioando == null) {
                infoController.show("No ha seleccionado un cliente");            
                return;
            }
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Clientes, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            editar_cliente(cliente_seleccioando);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.Clientes ,this.usuarioActual);
        }
        clientes = Cliente.findAll();
        cargar_tabla_index();
    }    
    
    public void nuevo(){
       crear_nuevo = true;
       habilitar_formulario();
       limpiar_formulario();
    }
    
    @Override
     public void desactivar()
     {
        if(cliente_seleccioando==null) 
        {
            infoController.show("No ha seleccionado un rol");            
            return;
        }
        try {
           if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Clientes, Accion.ACCION.DES)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            if(!confirmatonController.show("Se deshabilitara el cliente con nombre: " + clienteSh.getText(), "¿Desea continuar?")) return;
            Base.openTransaction();
           
           cliente_seleccioando.set("estado",Cliente.ESTADO.INACTIVO.name());
           cliente_seleccioando.saveIt();

           Base.commitTransaction();
           infoController.show("El cliente ha sido deshabilitado");
           AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.DES, Menu.MENU.Clientes ,this.usuarioActual);
           limpiar_formulario();
           cargar_tabla_index();
        } catch (Exception e) {
           Base.rollbackTransaction();
        }
     }
    
    public void limpiar_formulario(){
        clienteSh.clear();
        dni.clear();
        envioDir.clear();
        factDir.clear();
        persoNatu.setSelected(false);
        persoJuri.setSelected(false);
        ruc.clear();
        repLegal.clear();
        telf.clear();
    }

    @FXML
    public void mostrar_detalle_cliente(ActionEvent event) throws IOException{
        try{
            Cliente registro_seleccionado = tabla_clientes.getSelectionModel().getSelectedItem();
            cliente_formulario.setDisable(false);
            if (registro_seleccionado.getString("tipo_cliente").equals("PersonaNatural")){
                dni.setText(registro_seleccionado.getString("dni"));
                ruc.setDisable(false);
                persoNatu.setSelected(true);
                persoJuri.setSelected(false);
                repLegal.setDisable(true);
            }else if (registro_seleccionado.getString("tipo_cliente").equals("PersonaJuridica")){
                ruc.setText(registro_seleccionado.getString("ruc"));
                dni.setDisable(true);
                persoNatu.setSelected(false);
                persoJuri.setSelected(true);
                repLegal.setText(registro_seleccionado.getString("nombre_contacto"));
            }
            clienteSh.setText(registro_seleccionado.getString("nombre"));
            telf.setText(registro_seleccionado.getString("telef_contacto"));
            envioDir.setText(registro_seleccionado.getString("direccion_despacho"));
            factDir.setText(registro_seleccionado.getString("direccion_facturacion"));
            VerDepartamento.getSelectionModel().select(registro_seleccionado.getString("departamento"));
            
            
        }
        catch( Exception e){
            System.out.println(e);
        }
    }  

    
    public void habilitar_formulario(){
        cliente_formulario.setDisable(false);
    }
    
    public void inhabilitar_formulario (){
        cliente_formulario.setDisable(true);
    }
    
    public void limpiar_tabla_index(){
        tabla_clientes.getItems().clear();
    }
    
    public boolean cumple_condicion_busqueda(Cliente cliente, String ruc, String dni, String nombres, String estado){
        boolean match = true;
        if ( ruc.equals("") && dni.equals("") && nombres.equals("") && estado.equals("")){
            match = false;
        }
        else {
            match = (!ruc.equals("")) ? (match && (cliente.get("ruc")).equals(ruc)) : true;
            match = (!dni.equals("")) ? (match && (cliente.get("dni")).equals(dni)) : true;
            match = (!nombres.equals("")) ? (match && (cliente.get("nombre")).equals(nombres)) : true;
            match = (!estado.equals("")) ? (match && (cliente.get("tipo_cliente")).equals(estado)) : true;
        }
        return match;
    }
    @FXML
    public void buscar_cliente(ActionEvent event) throws IOException{

        String ruc = rucBusq.getText();
        String dni = dniBusq.getText();
        String nombres = nombreBusq.getText();
        String estado = ( estadoBusq.getSelectionModel().getSelectedItem() == null ) ? "" : estadoBusq.getSelectionModel().getSelectedItem().toString();
        System.out.println(estado);
        List<Cliente> temp_clientes = Cliente.findAll();
        
        if(ruc!=null&&!ruc.isEmpty())
        {            
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("ruc").equals(ruc)).collect(Collectors.toList());
        }

        if(dni!=null&&!dni.isEmpty())
        {            
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("dni").equals(ruc)).collect(Collectors.toList());
        }
                
        if(nombres!=null&&!nombres.isEmpty())
        {
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("nombre").equals(nombres)).collect(Collectors.toList());
        }

        if(estado!=null&&!estado.isEmpty())
        {
            temp_clientes = temp_clientes.stream().filter(p -> p.getString("tipo_cliente").equals(nombres)).collect(Collectors.toList());
        }        
        clientes = temp_clientes;
        cargar_tabla_index();
        try {                        
        } catch (Exception e) {
            infoController.show("El Cliente contiene errores : " + e);                    
        }
    }
    
    public void llenar_estado_social_busqueda(){
        estadoBusq.getItems().addAll("persona natural", "persona juridica");
    }
    
    public void control_check_box(){
        persoNatu.setOnAction(e -> manejarAreaTexto(ruc,repLegal));
        persoJuri.setOnAction(e -> manejarAreaTexto(dni));        
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        masterData.clear();
        for( Cliente cliente : clientes){
            if (cliente.getString("estado").equals("activo")){
                masterData.add(cliente);
            }
            
        }
        tabla_clientes.setEditable(false);
        columna_ruc.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("ruc")));
        columna_dni.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("dni")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        tabla_clientes.setItems(masterData);
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Clientes;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inhabilitar_formulario();
        llenar_estado_social_busqueda();
        control_check_box();
        
        clientes = null;
        clientes = Cliente.findAll();
        cargar_tabla_index();
          
        ObservableList<String> departamentos = FXCollections.observableArrayList();       
        departamentos.addAll(Arrays.asList(Flete.ZONA.values()).stream().map(x->x.name()).collect(Collectors.toList()));
        VerDepartamento.setItems(departamentos);
        
        
        tabla_clientes.getSelectionModel().selectedIndexProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null){
                cliente_seleccioando = tabla_clientes.getSelectionModel().getSelectedItem();                
                tabla_clientes.getSelectionModel().clearSelection();        
            }
        });
    }    
    
    private void manejarAreaTexto(TextField texto, TextField texto2){    
        if (persoNatu.isSelected()){
            texto.setDisable(true);
            texto2.setDisable(true);
            dni.setDisable(false);
            persoJuri.setDisable(false);
            persoJuri.setSelected(false);
        }
    }
    
    private void manejarAreaTexto(TextField texto){
        if (persoJuri.isSelected()){
            texto.setDisable(true);
            ruc.setDisable(false);
            repLegal.setDisable(false);
            persoNatu.setSelected(false);
        }
    }
}
