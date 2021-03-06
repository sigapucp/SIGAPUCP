/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import java.util.stream.Collectors;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Proveedor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class ProveedoresController extends Controller{

    @FXML
    private TextField proveedor_nombre;
    @FXML 
    private TextField ruc;
    @FXML
    private TextField telf;
    @FXML
    private TextField repLegal;
    @FXML
    private TextArea comentarios;
    @FXML
    private TextField ruc_busqueda;
    @FXML
    private TextField nombre_busqueda;
    @FXML
    private AnchorPane proveedor_formulario;
    @FXML
    private TableColumn<Proveedor, String> columna_ruc;
    @FXML
    private TableColumn<Proveedor, String> columna_nombre;
    @FXML
    private TableView<Proveedor> tabla_proveedor;
       
    private Boolean crear_nuevo;
    
    private Proveedor proveedor_seleccionado;
    
    private InformationAlertController infoController;
    
    private ConfirmationAlertController confirmatonController;
    
    private List<Proveedor> proveedores;
    
    private final ObservableList<Proveedor> masterData = FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class.
     */
    
    public ProveedoresController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        proveedor_seleccionado = null;
        crear_nuevo = false;
    }
    
    public void crear_proveedor(){
        try{
            Base.openTransaction();  
            Proveedor nuevo_proveedor = new Proveedor();
            if(!confirmatonController.show("Se creará el proveedor : " + proveedor_nombre.getText(), "¿Desea continuar?")) return;
            nuevo_proveedor.asignar_atributos(proveedor_nombre.getText(), repLegal.getText(), telf.getText(), ruc.getText(), comentarios.getText());
            nuevo_proveedor.set("last_user_change",usuarioActual.get("usuario_cod"));
            nuevo_proveedor.saveIt();
            Base.commitTransaction();
            infoController.show("El proveedor ha sido creado satisfactoriamente"); 
            limpiar_formulario();
            inhabilitar_formulario();
            crear_nuevo = false;
        }
        catch(Exception e){
            Base.rollbackTransaction();
            infoController.show("Error al crear proveedor:" + e);
            System.out.println(e);
            crear_nuevo = true;
        }   
        
    }
    
    public void editar_proveedor(Proveedor proveedor){
        try{
            Base.openTransaction();  
            if(!confirmatonController.show("Se editará el proveedor con código: " + proveedor_nombre.getText(), "¿Desea continuar?")) return;
            proveedor.asignar_atributos(proveedor_nombre.getText(), repLegal.getText(), telf.getText(), ruc.getText(), comentarios.getText());
            proveedor.saveIt();
            Base.commitTransaction();
            infoController.show("El proveedor ha sido editado creado satisfactoriamente"); 
        }
        catch(Exception e){
            Base.rollbackTransaction();
            infoController.show("Error al editar proveedor: "+ e ); 
            System.out.println(e);
        }        
    }
    
    @Override
    public void guardar() {
        if (crear_nuevo){
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Proveedores, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crear_nuevo = false;
                return;
            }
            crear_proveedor();
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.Proveedores ,this.usuarioActual);
        }else{
            if (proveedor_seleccionado == null){
                infoController.show("No ha seleccionado un proveedor");
                return;
            }
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Proveedores, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            editar_proveedor(proveedor_seleccionado);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.Proveedores ,this.usuarioActual);
        }
        proveedores = Proveedor.findAll();
        cargar_tabla_index();        
    }        
    
    @Override
    public void nuevo(){
       crear_nuevo = true;
       habilitar_formulario();
       limpiar_formulario();
    }
    
    @Override
    public void cargar(){
        //validamos que los campos sean los correctos
        if(!confirmatonController.show("Verifique que el formato del archivo .csv sea: \n nombre,rep. legal,telefono,ruc proveedor,anotaciones,", "¿Desea continuar?")) return;
        //csv
        //csv
        //pop up para seleccionar el archivo:
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escoger CSV de Carga Masiva");
        File file = fileChooser.showOpenDialog(stage);
        Boolean primera_fila = true;
        try {
            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNext()){
                String data = inputStream.nextLine();
                //manejo de la data aquí:
                String [] values = data.split(",");
                if (primera_fila) {
                    primera_fila = false;
                    if (values.length != 5) {
                        infoController.show("El archivo .csv no tiene el formato adecuado. Verifique que sea\nnombre,rep. legal,telefono,ruc proveedor,anotaciones,"); 
                        return;
                    }      
                    continue; //nos saltamos el encabezado
                }
                Base.openTransaction();
                Proveedor nuevo_proveedor = new Proveedor();
                nuevo_proveedor.asignar_atributos(values[0], values[1], values[2], values[3], values[4]);
                nuevo_proveedor.set("last_user_change",usuarioActual.get("usuario_cod"));
                nuevo_proveedor.saveIt();
                Base.commitTransaction();
                System.out.println("CORRECTO");
            }
            infoController.show("¡Carga masiva de datos de proveedores exitosa!");
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CSV, Menu.MENU.Proveedores, this.usuarioActual);
            inputStream.close();
            proveedores = Proveedor.findAll();
            cargar_tabla_index();
            
        } catch (FileNotFoundException ex) {
            infoController.show("Error en la carga masiva");
            System.out.println(ex);
            Base.rollbackTransaction();
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void limpiar_formulario(){
        this.proveedor_nombre.clear();
        this.ruc.clear();
        this.telf.clear();
        this.repLegal.clear();
        this.comentarios.clear();
    }
    
    public void habilitar_formulario(){
        proveedor_formulario.setDisable(false);
    }
    
    public  void inhabilitar_formulario (){
        proveedor_formulario.setDisable(true);
    }
    
    @FXML
    public void buscar_proveedor(ActionEvent event) throws IOException{
        String ruc = ruc_busqueda.getText();
        String nombres = nombre_busqueda.getText();
        List<Proveedor> temp_proveedores = Proveedor.findAll();
        
        if(ruc!=null&&!ruc.isEmpty())
        {            
            temp_proveedores = temp_proveedores.stream().filter(p -> p.getString("provuder_ruc").equals(ruc)).collect(Collectors.toList());
        }

        if(nombres!=null&&!nombres.isEmpty())
        {            
            temp_proveedores = temp_proveedores.stream().filter(p -> p.getString("name").equals(nombres)).collect(Collectors.toList());
        }
        
        
                
        proveedores = temp_proveedores;
        cargar_tabla_index();
        try {                        
        } catch (Exception e) {
            infoController.show("Error al filtrar proveedores");                    
            System.out.println(e);
        }                
        
    }
    
    public void cargar_tabla_index(){
        masterData.clear();
        for( Proveedor cliente : proveedores){
            if (cliente.getString("status").equals(Proveedor.ESTADO.ACTIVO.name())){
                masterData.add(cliente);
            }            
        }
        tabla_proveedor.setEditable(false);
        columna_ruc.setCellValueFactory((TableColumn.CellDataFeatures<Proveedor, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("provuder_ruc")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<Proveedor, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("name")));
        tabla_proveedor.setItems(masterData);
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Proveedores;
    }
        
    public void mostrar_detalle_proveedor(ActionEvent event) throws IOException{
        try{
            habilitar_formulario();
            Proveedor registro_seleccionado = tabla_proveedor.getSelectionModel().getSelectedItem();
            proveedor_nombre.setText(registro_seleccionado.getString("name"));
            ruc.setText(registro_seleccionado.getString("provuder_ruc"));
            telf.setText(registro_seleccionado.getString("phone_number"));
            repLegal.setText(registro_seleccionado.getString("contact_name"));
            comentarios.setText(registro_seleccionado.getString("annotation"));
        }
        catch( Exception e){
            infoController.show("Error al mostrar el detalle del proveedor");
            System.out.println(e);
        }
    }  
    @Override
    public void desactivar(){
        proveedor_seleccionado = tabla_proveedor.getSelectionModel().getSelectedItem();
        if (proveedor_seleccionado==null){
            infoController.show("No se selecciono un proveedor");
            return;
        }
        try{
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Proveedores, Accion.ACCION.DES)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            if(!confirmatonController.show("Se deshabilitara el proveedor con nombre: " + proveedor_seleccionado.getString("name"), "¿Desea continuar?")) return;
            Base.openTransaction();
            proveedor_seleccionado.set("status",Proveedor.ESTADO.INACTIVO.name());
            proveedor_seleccionado.saveIt();
            Base.commitTransaction();
            infoController.show("El proveedor ha sido deshabilitado");
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.DES, Menu.MENU.Proveedores ,this.usuarioActual);
            limpiar_formulario();
            cargar_tabla_index();
        }catch(Exception e){
            Base.rollbackTransaction();
            infoController.show("Error al desactivar el proveedor");
            System.out.println(e);           
        }
    }    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        inhabilitar_formulario();
        proveedores = null;
        proveedores = Proveedor.findAll();
        cargar_tabla_index();        
    }        
}
