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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenesSalidaxEnvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarEnviosArgs;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class OrdenesDeSalidaController  extends Controller{
    //BUSCAR SALIDAS
    //--------------------------------------------------//    
    @FXML
    private TextField salida_buscar;
    @FXML
    private TextField cliente_buscar;
    @FXML
    private TextField pedido_buscar;
    @FXML
    private TableView<OrdenSalida> tabla_salidas;
    @FXML
    private TableColumn<OrdenSalida, String> columna_cliente_salida;
    @FXML
    private TableColumn<OrdenSalida, String> columna_cod_salida;
    @FXML
    private TableColumn<OrdenSalida, String> columna_pedido_salida;
    
    //FORMULARIO
        //--------------------------------------------------//    
    @FXML
    private AnchorPane salida_formulario;
    
    @FXML
    private ComboBox<String> tipos;
    @FXML
    private TextField codigo_salida; 
    @FXML
    private TextArea descripcion_envio;    
    
    //TAB TIPOS
        //TABLA TIPOS DE PRODUCTO
        //--------------------------------------------------//    
    @FXML
    private TableView<TipoProducto> tabla_tipos;
    @FXML
    private TableColumn<TipoProducto, String> columna_cod_tipo;
    @FXML
    private TableColumn<TipoProducto, String> columna_nombre_tipo;
    @FXML
    private TableColumn<TipoProducto, String> columna_cant_tipo;
    @FXML
    private TableColumn<TipoProducto, String> columna_desc_tipo;
    
    @FXML
    private Button boton_tipo;

    @FXML
    private Button boton_envio;    
    @FXML
    private Label lbl_codigo_pedido;
    @FXML
    private TextField envio_dato;
    @FXML
    private Label lbl_codigo_envio;
    @FXML
    private TextField pedido_dato;
    
        //TABLA TIPOS SALIDA 
        //--------------------------------------------------//
    
    @FXML
    private TableView<TipoProducto> tabla_tipos_salida;
    @FXML
    private TableColumn<TipoProducto, String> columna_cod_tipo_salida;
    @FXML
    private TableColumn<TipoProducto, String> columna_nombre_tipo_salida;
    @FXML
    private TableColumn<TipoProducto, String> columna_cant_tipo_salida;
    @FXML
    private TableColumn<TipoProducto, String> columna_desc_tipo_salida;
    
    
    //TAB PRODUCTOS
        //TABLA PRODUCTOS
    @FXML
    private TableView<Producto> tabla_productos;
    @FXML
    private TableColumn<Producto, String> columna_cod_prod;
    @FXML
    private TableColumn<Producto, String> columna_nombre_prod;
    @FXML
    private TableColumn<Producto, String> columna_tipo_prod;
    @FXML
    private TableColumn<Producto, String> columna_fecha_prod;

    //LOGICA
        //--------------------------------------------------//
    private InformationAlertController infoController; 
    private List<Envio> envios_disponibles;
    private boolean crear_nuevo;
    private Envio envio_devuelto;
    private final ObservableList<OrdenSalida> masterDataSalidas = FXCollections.observableArrayList();
    private final ObservableList<TipoProducto> masterDataTipo = FXCollections.observableArrayList();
    private final ObservableList<TipoProducto> masterDataTipoSalida = FXCollections.observableArrayList();    
    private final ObservableList<Producto> masterDataProducto = FXCollections.observableArrayList();
    private List<OrdenSalida> salidas_temp;
    private OrdenSalida salida_seleccionada;
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();    

    
    private void limpia_formulario(){
        try{
            codigo_salida.clear();
            descripcion_envio.clear();
            masterDataTipo.clear();
            masterDataTipoSalida.clear();
            llenar_combo_box_tipo();
            masterDataProducto.clear();
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
        actualizar_lista_salida_tabla();
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
    
    private boolean cumple_condicion_busqueda(OrdenSalida salida, String codigo, String cliente, String codPedido){
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
    
    @FXML
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
            infoController.show("No se pudo buscar la orden de salida : " + e.getMessage());
        }               
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
            } else{
                orden_salida.saveIt();
                insertar_orden_salida_envio(orden_salida);                
            }

            Base.commitTransaction();            
        }catch (Exception e){
            infoController.show("No se pudo crear la orden de salida : " + e.getMessage()); 
        }
    }
    
    private void eliminar_orden_salida_envio(){
        OrdenesSalidaxEnvio.delete("salida_id = ?", salida_seleccionada.getId());
    }
    
    private void editar_orden_salida(){
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
        limpia_formulario();
        inhabilitar_formulario();
    }
    
    private void actualizar_lista_salida_tabla(){
        /*for(Envio envio : envios_disponibles){
            masterDataTipoSalida.add(envio);
        }*/
        columna_cod_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("envio_cod")));
        columna_nombre_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_cant_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        columna_desc_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));
                
        tabla_tipos_salida.setItems(masterDataTipoSalida);
    }
    
    private void agregar_a_lista_envios(){
        if(!envios_disponibles.stream().anyMatch(x -> x.getInteger("tipo_id").equals(envio_devuelto.getInteger("tipo_id")))){
            envios_disponibles.add(envio_devuelto);
        }else{
            infoController.show("Ha seleccionado un envio ya agregado");
        }
    }
    
    @FXML
    private void agregar_tipos_salida(ActionEvent event) throws IOException{
        try {
            String codigo = codigo_salida.getText();
            String tipo_seleccionado = tipos.getSelectionModel().getSelectedItem();
            if ( (tipo_seleccionado == null ) || (codigo == null)){
                infoController.show("Debe completar los datos de la orden de salida ");
            }else{
                //if (tipo.equals("VENTA"))
                    agregar_a_lista_envios();
                actualizar_lista_salida_tabla();                
            }
        } catch (Exception e) {
            infoController.show("Ocurrio un error al agregar los tipos de productos a la orden de salida : " + e.getMessage());
        }
    }
            
    private void cargar_tipos_producto(){
        masterDataTipo.clear();
        
        String tipo = obtenerTipo();
        if (tipo.equals(OrdenSalida.TIPO.Venta.name())){
            List<OrdenesCompraxProductosxenvio> productos_envio = OrdenesCompraxProductosxenvio.where("envio_id = ?",envio_devuelto.getId() );
            for( OrdenesCompraxProductosxenvio producto_envio : productos_envio){
                TipoProducto tipo_producto = new TipoProducto();
                //tipo_producto.set("",producto_envio.get(""));
                masterDataTipo.add(tipo_producto);
            }
        } else{
            //
        }
        columna_cod_tipo.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre_tipo.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        //columna_cant_tipo.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(.findById(p.getValue().get("tipo_id")).getString("nombre"))));
        columna_desc_tipo.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
        
        tabla_tipos.setItems(masterDataTipo);
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
    private void handle_buscar_envio(ActionEvent event) throws IOException{
        try{
            seleccionar_envio(); 
            cargar_tipos_producto();
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
    
    private void cargar_tabla_index(){
        masterDataSalidas.clear();
        List<OrdenSalida> salidas = OrdenSalida.findAll();
        for(OrdenSalida salida : salidas){
            masterDataSalidas.add(salida);
        }        
        
        columna_cliente_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(OrdenesSalidaxEnvio.first("salida_cod = ?", p.getValue().get("salida_cod")).get("client_id")).get("nombre")));
        columna_cod_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("salida_cod")));
        columna_pedido_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(OrdenesSalidaxEnvio.first("salida_cod = ?", p.getValue().get("salida_cod")).get("orden_compra_cod")));

        tabla_salidas.setItems(masterDataSalidas);
    }
    
    private void habilitar_formulario(){
        salida_formulario.setDisable(false);
    }
    
    private void inhabilitar_formulario(){
        salida_formulario.setDisable(true);
    }
    
    private void llenar_combo_box_tipo(){
        ObservableList<String> tipos_enum = FXCollections.observableArrayList();   
        tipos_enum.addAll(Arrays.asList(OrdenSalida.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()));  
        tipos.setItems(tipos_enum);
    }
    
    private String obtenerTipo(){
        String tipo = (tipos.getSelectionModel().getSelectedItem() == null ) ? "" : tipos.getSelectionModel().getSelectedItem(); 
        return tipo;
    }
    
    private void manejarcomboBoxTipoSalida(){
        masterDataTipo.clear();        
        tabla_tipos.getItems().clear();
        
        masterDataTipoSalida.clear();
        tabla_tipos_salida.getItems().clear();
        
        String tipo = obtenerTipo();
        
        if (tipo.equals(OrdenSalida.TIPO.Venta.name())){
            boton_envio.setVisible(true);
            boton_tipo.setVisible(false);
            
            lbl_codigo_envio.setVisible(true);
            envio_dato.setVisible(true);
            lbl_codigo_pedido.setVisible(true);
            pedido_dato.setVisible(true);
        } else{
            boton_envio.setVisible(false);
            boton_tipo.setVisible(true);
            
            lbl_codigo_envio.setVisible(false);
            envio_dato.setVisible(false);
            lbl_codigo_pedido.setVisible(false);
            pedido_dato.setVisible(false);
        }
    }
    
    public OrdenesDeSalidaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        //igual se deben guardar los envios para poder llenar la tabla ordenesSalidaXenvio
        envios_disponibles = new ArrayList<Envio>();
    }    

    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        try {                        
            cargar_tabla_index();
            inhabilitar_formulario();
            llenar_combo_box_tipo();
            tipos.setOnAction(e -> manejarcomboBoxTipoSalida());
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
