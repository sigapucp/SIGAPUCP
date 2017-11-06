/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenesSalidaxEnvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarEnviosArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.print.DocFlavor;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class OrdenesDeSalidaController  extends Controller{
    //TABLA ENVIOS AGREGAR
        //--------------------------------------------------//
    @FXML
    private AnchorPane envio_formulario;
    @FXML
    private TableView<Envio> tabla_envios_salida;
    @FXML
    private TableColumn<Envio, String> columna_envio_cod_salida;
    @FXML
    private TableColumn<Envio, String> columna_envio_cliente_salida;
    @FXML
    private TableColumn<Envio, String> columna_envio_pedido_salida;
    @FXML
    private TableColumn<Envio, String> columna_envio_estado_salida;
    //TABLA PRODUCTOS DE ENVIO
        //--------------------------------------------------//    
    @FXML
    private TableView<OrdenesCompraxProductosxenvio> tabla_productos;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> envio_cod_producto;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> envio_nombre_producto;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> envio_desc_producto;
        
    //LOGICA
        //--------------------------------------------------//
    private InformationAlertController infoController; 
    private List<Envio> envios_disponibles;
    private boolean crear_nuevo;
    private Envio envio_devuelto;
    private final ObservableList<Envio> masterDataEnvio = FXCollections.observableArrayList();
    private final ObservableList<OrdenesCompraxProductosxenvio> masterDataProductoEnvio = FXCollections.observableArrayList();
    
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();    

    private void insertar_orden_salida_envio(OrdenSalida salida){
        for(Envio envio : envios_disponibles){
            OrdenesSalidaxEnvio salida_envio = new OrdenesSalidaxEnvio();
            salida_envio.set("orden_compra_cod", envio.getString("orden_compra_cod"));
            salida_envio.set("client_id", envio.getInteger("client_id"));
            salida_envio.set("orden_compra_id", envio.getInteger("orden_compra_id"));
            salida_envio.set("salida_cod", salida.getString("salida_cod"));
            salida_envio.set("salida_id", salida.getInteger("salida_id"));
            salida_envio.set("envio_id", envio.getInteger("envio_id"));
            salida_envio.set("envio_cod", envio.getString("envio_cod"));
            salida_envio.saveIt();
        }
    }
    private void crear_envio(){
        Base.openTransaction();  
        OrdenSalida orden_salida = new OrdenSalida();
        orden_salida.set("last_user_change", usuarioActual.getString("usuario_cod"));
        //orden_salida.set("tipo",);
        //orden_salida.set("descripcion");
        //orden_salida.saveIt();
        insertar_orden_salida_envio(orden_salida);
        Base.commitTransaction();
    }
    
    
    @Override
    public void guardar(){
        if (crear_nuevo){
            crear_envio();
        } else {
            /*
            if ( == null){ 
                infoController.show("No ha seleccionado ninguna Orden de Compra");            
                return;
            }
            editarPedido(pedidoSeleccionado);
            */
        }
        crear_nuevo = false;
        llenar_orden_salida_tabla();        
    }
    
    public void limpiar_tabla_envio(){
        tabla_envios_salida.getItems().clear();
    }
    
    private void actualizar_lista_envios_tabla(){
        limpiar_tabla_envio();
        masterDataEnvio.clear();
        for(Envio envio : envios_disponibles){
            masterDataEnvio.add(envio);
        }
        columna_envio_cod_salida.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("envio_cod")));
        columna_envio_cliente_salida.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_envio_pedido_salida.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        columna_envio_estado_salida.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));
                
        tabla_envios_salida.setItems(masterDataEnvio);
    }
    private void agregar_a_lista_envios(){
        if(!envios_disponibles.stream().anyMatch(x -> x.getInteger("tipo_id").equals(envio_devuelto.getInteger("tipo_id")))){
            envios_disponibles.add(envio_devuelto);
            actualizar_lista_envios_tabla();
        }else{
            infoController.show("Ha seleccionado un envio ya agregado");
        }
    }
    @FXML
    private void agregar_envio(ActionEvent event) throws IOException{
        try {
            agregar_a_lista_envios();
        } catch (Exception e) {
            infoController.show("Ocurrio un error durante la seleccion del envio : " + e.getMessage());
        }
    }
    
    public void limpiar_tabla_productos_envio(){
        tabla_productos.getItems().clear();
    }
    
    public void mostrar_productos_envio(){
        //limpiar_tabla_productos_envio();
        masterDataProductoEnvio.clear();
        List<OrdenesCompraxProductosxenvio> productos_envio = OrdenesCompraxProductosxenvio.where("envio_id = ?",envio_devuelto.getId() );
        for( OrdenesCompraxProductosxenvio producto_envio : productos_envio){
            masterDataProductoEnvio.add(producto_envio);
        }
        
        envio_cod_producto.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        envio_nombre_producto.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        envio_desc_producto.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
        //envio_cant_producto.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(.findById(p.getValue().get("tipo_id")).getString("nombre"))));
        tabla_productos.setItems(masterDataProductoEnvio);
    }
    
   private void seleccionar_envio() throws Exception
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarEnviosOrdenSalida.fxml"));
            AgregarEnviosController controller = new AgregarEnviosController();
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);       
            controller.devolverEnvioEvent.addHandler((Object sender, agregarEnviosArgs args) -> {
                envio_devuelto = args.envio;
            });
            modal_stage.showAndWait();
        }catch(Exception e){
            infoController.show("No se pudo agregar los productos : " + e.getMessage());
        }
    }    
    @FXML
    private void handle_buscar_pedido(ActionEvent event) throws IOException{
        try{
            seleccionar_envio(); 
            mostrar_productos_envio();
        }catch(Exception e){
            infoController.show("No se pudo encontrar ordenes de salida : " + e.getMessage());
        }
    }    

    @Override
    public void nuevo(){
        crear_nuevo = true;
        habilitar_formulario();
    }
    public void llenar_orden_salida_tabla(){
        
    }
    public void habilitar_formulario(){
        envio_formulario.setDisable(false);
    }
    public void inhabilitar_formulario(){
        envio_formulario.setDisable(true);
    }
    
    public OrdenesDeSalidaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        envios_disponibles = new ArrayList<Envio>();
    }    
        
    public void initialize(DocFlavor.URL location, ResourceBundle resources) {  
        try {
            llenar_orden_salida_tabla();
            inhabilitar_formulario();
        } catch (Exception ex) {
            infoController.show("No se pudo cargar la ventana ordenes de salida : " + ex.getMessage());
        }
    }    
        
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.OrdendeSalida;
    }
}
