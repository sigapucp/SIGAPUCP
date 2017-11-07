/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenesSalidaxEnvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarEnviosArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.print.DocFlavor;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class OrdenesDeSalidaController  extends Controller{
    //FORMULARIO
        //--------------------------------------------------//    
    @FXML
    private TextField envio_dato;
    @FXML
    private TextField pedido_dato;
    @FXML
    private ComboBox<String> tipos;
    @FXML
    private TextField descripcion_envio;
    @FXML
    private TextField codigo_salida;
    //TABLA
        //--------------------------------------------------//    
    @FXML
    private TableView<OrdenSalida> tabla_salidas;
    @FXML
    private TableColumn<OrdenSalida, String> columna_cliente_salida;
    @FXML
    private TableColumn<OrdenSalida, String> columna_cod_salida;
    @FXML
    private TableColumn<OrdenSalida, String> columna_pedido_salida;
        //BUSCAR
        //--------------------------------------------------//    
    @FXML
    private TextField salida_buscar;
    @FXML
    private TextField cliente_buscar;
    @FXML
    private TextField pedido_buscar;
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
    private final ObservableList<OrdenSalida> masterDataSalidas = FXCollections.observableArrayList();
    private List<OrdenSalida> salidas_temp;
    private OrdenSalida salida_seleccionada;
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();    

    private void limpia_formulario(){
        try{
            codigo_salida.clear();
            descripcion_envio.clear();
            masterDataEnvio.clear();            
        }catch (Exception e){
            infoController.show("Ha ocurrido un error : " + e.getMessage());  
        }

    }
    private void setear_datos_salida(){
        llenar_combo_box_tipo();
        tipos.setValue(salida_seleccionada.getString("tipo"));
        codigo_salida.setText(salida_seleccionada.getString("salida_cod"));
        codigo_salida.setEditable(false);
        descripcion_envio.setText(salida_seleccionada.getString("descripcion"));
    }
    
    private void setear_envios_salida(){
        List<OrdenesSalidaxEnvio> salida_envios = OrdenesSalidaxEnvio.where("salida_cod = ?", salida_seleccionada.getString("salida_cod"));
        List<Envio> envios_temp = new ArrayList<Envio>();
        for(OrdenesSalidaxEnvio salida_envio : salida_envios){
            Envio envio_temp = Envio.findById(salida_envio.getInteger("envio_id"));
            envios_temp.add(envio_temp);
        }
        envios_disponibles = envios_temp;
        actualizar_lista_envios_tabla();
    }
    
    @FXML
    private void visualizar_orden_salida(ActionEvent event) throws  IOException{
        crear_nuevo = false;
        salida_seleccionada = tabla_salidas.getSelectionModel().getSelectedItem();
        if (salida_seleccionada == null){
            infoController.show("Salida no seleccionada");  
            return;            
        }
        try{
            habilitar_formulario();
            limpia_formulario();
            setear_datos_salida();
            setear_envios_salida();
        }catch (Exception e){
            
        }
    }
    public boolean cumple_condicion_busqueda(OrdenSalida salida, String codigo, String cliente, String codPedido){
        boolean match = true;        
        if ( codigo.equals("") && cliente.equals("") && codPedido.equals("")){
            match = true;
        }else {
            OrdenesSalidaxEnvio salida_envio = OrdenesSalidaxEnvio.first("salida_cod = ?", salida.get("salida_cod"));
            match = (!codigo.equals("")) ? (match && (salida.get("salida_cod")).equals(codigo)) : match;
            String nombre_cliente = Cliente.findById(salida_envio.get("client_id")).getString("nombre");
            match = (!cliente.equals("")) ? (match && (nombre_cliente == cliente)) : match;
            match = (!codPedido.equals("")) ? (match && (salida_envio.get("orden_compra_cod")).equals(codPedido)) : match;
        }
        return match;
    }    
    private void buscar_salida(ActionEvent event) throws IOException{
        salidas_temp = OrdenSalida.findAll();
        masterDataSalidas.clear();
        try{
            for(OrdenSalida salida : salidas_temp){
                if (cumple_condicion_busqueda(salida, salida_buscar.getText(), cliente_buscar.getText(), pedido_buscar.getText())){
                    masterDataSalidas.add(salida);
                }
            }
        }catch(Exception e){
            infoController.show("No se pudo buscar el envio : " + e.getMessage());
        }        
        llenar_orden_salida_tabla();        
    }
    
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
        try{
            Base.openTransaction();  
            OrdenSalida orden_salida = new OrdenSalida();
            orden_salida.set("last_user_change", usuarioActual.getString("usuario_cod"));
            orden_salida.set("tipo",tipos.getSelectionModel().getSelectedItem());
            orden_salida.set("descripcion", descripcion_envio.getText());
            orden_salida.set("salida_cod", codigo_salida.getText());
            orden_salida.set("estado", OrdenSalida.ESTADO.PENDIENTE.name());
            //falta
            //------------------------------------------------
            orden_salida.set("ruta_orden", "test");
            orden_salida.set("nr_asignados", 1);
            if (envios_disponibles.isEmpty()){
                infoController.show("No se pudo crear la orden de salida sin ningun envio"); 
            }
            else{
                orden_salida.saveIt();
                insertar_orden_salida_envio(orden_salida);                
            }

            Base.commitTransaction();            
        }catch (Exception e){
            infoController.show("No se pudo crear la orden de salida : " + e.getMessage()); 
        }

    }
    
    public void eliminar_orden_salida_envio(){
        OrdenesSalidaxEnvio.delete("salida_id = ?", salida_seleccionada.getId());
    }
    
    public void editar_orden_salida(){
        try{
            Base.openTransaction();  
            salida_seleccionada.set("descripcion", descripcion_envio.getText());
            salida_seleccionada.set("tipo",tipos.getSelectionModel().getSelectedItem());
            salida_seleccionada.saveIt();
            eliminar_orden_salida_envio();
            insertar_orden_salida_envio(salida_seleccionada);
            Base.commitTransaction();                        
        }catch (Exception e){
            infoController.show("No se pudo editar la orden de salida : " + e.getMessage()); 
        }
    }
    
    @Override
    public void guardar(){
        if (crear_nuevo){
            crear_envio();
        } else {
            if (salida_seleccionada == null){ 
                infoController.show("No ha seleccionado ninguna Orden de Salida"); 
                return;
            }
            editar_orden_salida();
        }
        crear_nuevo = false;
        masterDataSalidas.clear();
        List<OrdenSalida> salidas = OrdenSalida.findAll();
        for(OrdenSalida salida : salidas){
            masterDataSalidas.add(salida);
        }                  
        llenar_orden_salida_tabla();      
        limpia_formulario();
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
        }else{
            infoController.show("Ha seleccionado un envio ya agregado");
        }
    }
    @FXML
    private void agregar_envio(ActionEvent event) throws IOException{
        try {
            String codigo = codigo_salida.getText();
            String tipo_seleccionado = tipos.getSelectionModel().getSelectedItem();
            if ( (tipo_seleccionado == null ) || (codigo == null)){
                infoController.show("Debe completar los datos del envio ");
            }else{
                agregar_a_lista_envios();
                actualizar_lista_envios_tabla();                
            }
        } catch (Exception e) {
            infoController.show("Ocurrio un error durante la seleccion del envio : " + e.getMessage());
        }
    }
    
    private void eliminar_de_lista_envio(){
        Envio envio_eliminar = tabla_envios_salida.getSelectionModel().getSelectedItem();
        if (envio_eliminar == null){
            infoController.show("No ha seleccionado algun envio");
            return;            
        }else {
           envios_disponibles.remove(envio_eliminar);
        }
    }
    
    @FXML
    private void eliminar_envio(ActionEvent event) throws IOException{
        try{
            eliminar_de_lista_envio();
            actualizar_lista_envios_tabla();
        }catch (Exception e){
            infoController.show("Ocurrio un error durante la eliminacion del envio : " + e.getMessage());
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
    private void mostrar_datos_pedido(){
       envio_dato.setText(envio_devuelto.getString("envio_cod"));
       pedido_dato.setText(envio_devuelto.getString("orden_compra_cod"));
    }
    @FXML
    private void handle_buscar_pedido(ActionEvent event) throws IOException{
        try{
            seleccionar_envio(); 
            mostrar_productos_envio();
            mostrar_datos_pedido();
        }catch(Exception e){
            infoController.show("No se pudo encontrar ordenes de salida : " + e.getMessage());
        }
    }    

    @Override
    public void nuevo(){
        crear_nuevo = true;
        habilitar_formulario();
    }
    
    public void limpiar_tabla_salida(){
        
    }
    
    public void llenar_orden_salida_tabla(){
        limpiar_tabla_envio();
        
        columna_cliente_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(OrdenesSalidaxEnvio.first("salida_cod = ?", p.getValue().get("salida_cod")).get("client_id")).get("nombre")));
        columna_cod_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("salida_cod")));
        columna_pedido_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(OrdenesSalidaxEnvio.first("salida_cod = ?", p.getValue().get("salida_cod")).get("orden_compra_cod")));

        tabla_salidas.setItems(masterDataSalidas);
    }
    public void habilitar_formulario(){
        envio_formulario.setDisable(false);
    }
    public void inhabilitar_formulario(){
        envio_formulario.setDisable(true);
    }
    public void llenar_combo_box_tipo(){
        ObservableList<String> tipos_enum = FXCollections.observableArrayList();         
        tipos_enum.add("");
        tipos_enum.addAll(Arrays.asList(OrdenSalida.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()));  
        tipos.setItems(tipos_enum);
    }
    public OrdenesDeSalidaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        envios_disponibles = new ArrayList<Envio>();
    }    

    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        try {
            masterDataSalidas.clear();
            List<OrdenSalida> salidas = OrdenSalida.findAll();
            for(OrdenSalida salida : salidas){
                masterDataSalidas.add(salida);
            }            
            llenar_orden_salida_tabla();
            inhabilitar_formulario();
            llenar_combo_box_tipo();
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
