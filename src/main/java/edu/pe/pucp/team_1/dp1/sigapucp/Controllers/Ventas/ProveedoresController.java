/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Proveedor;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private List<Proveedor> proveedores;
    private final ObservableList<Proveedor> masterData = FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class.
     */
    
    public ProveedoresController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        proveedor_seleccionado = null;
        crear_nuevo = false;
    }
    
    public void crear_proveedor(){

        try{
            Base.openTransaction();  
            Proveedor nuevo_proveedor = new Proveedor();
            nuevo_proveedor.asignar_atributos(proveedor_nombre.getText(), repLegal.getText(), telf.getText(), ruc.getText(), comentarios.getText());
            nuevo_proveedor.set("last_user_change",usuarioActual.get("usuario_cod"));
            nuevo_proveedor.saveIt();
            Base.commitTransaction();
            infoController.show("El cliente ha sido creado satisfactoriamente"); 
        }
        catch(Exception e){
            System.out.println(e);
            Base.rollbackTransaction();
        }   
        
    }
    
    public void editar_proveedor(Proveedor proveedor){
        try{
            Base.openTransaction();  
            proveedor.asignar_atributos(proveedor_nombre.getText(), repLegal.getText(), telf.getText(), ruc.getText(), comentarios.getText());
            proveedor.saveIt();
            Base.commitTransaction();
            infoController.show("El cliente ha sido editado creado satisfactoriamente"); 
        }
        catch(Exception e){
            Base.rollbackTransaction();
        }        
    }
    
    @Override
    public void guardar() {
        if (crear_nuevo){
            crear_proveedor();
        }else{
            if (proveedor_seleccionado == null) return;
            editar_proveedor(proveedor_seleccionado);
        }
        proveedores = Proveedor.findAll();
        cargar_tabla_index();        
    }        
    
    public void nuevo(){
       crear_nuevo = true;
       habilitar_formulario();
       limpiar_formulario();
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
    
    public boolean cumple_condicion_busqueda(Proveedor proveedor, String ruc, String nombres){
        boolean match = true;
        if ( ruc.equals("") && nombres.equals("") ){
            match = false;
        }
        else {
            match = (!ruc.equals("")) ? (match && (proveedor.get("ruc")).equals(ruc)) : true;
            match = (!nombres.equals("")) ? (match && (proveedor.get("nombre")).equals(nombres)) : true;
        }
        return match;        
    }
    @FXML
    public void buscar_proveedor(ActionEvent event) throws IOException{
        proveedores = Proveedor.findAll();
        masterData.clear();
        try{
            for(Proveedor proveedor : proveedores){
                if (cumple_condicion_busqueda(proveedor, ruc_busqueda.getText(), nombre_busqueda.getText())){
                    masterData.add(proveedor);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void cargar_tabla_index(){
        for( Proveedor cliente : proveedores){
            masterData.add(cliente);
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
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        inhabilitar_formulario();
        proveedores = null;
        proveedores = Proveedor.findAll();
        cargar_tabla_index();
    }    
    
}
