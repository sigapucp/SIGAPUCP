/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalidaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenesSalidaxEnvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarEnviosArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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
    private TableView<OrdenSalidaxProducto> tabla_tipos;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_cod_tipo;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_nombre_tipo;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_cant_tipo;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_desc_tipo;
    
    @FXML
    private Button boton_tipo;
    @FXML
    private Label lbl_cant_tipo;
    @FXML
    private Spinner<Integer> sp_cant_tipo;

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
    private TableView<OrdenSalidaxProducto> tabla_tipos_salida;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_cod_tipo_salida;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_nombre_tipo_salida;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_cant_tipo_salida;
    @FXML
    private TableColumn<OrdenSalidaxProducto, String> columna_desc_tipo_salida;
    
    
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
    private ConfirmationAlertController confirmatonController;
    private List<Envio> envios_disponibles;
    private boolean crear_nuevo;
    private Envio envio_devuelto;
    private final ObservableList<OrdenSalida> masterDataSalidas = FXCollections.observableArrayList();
    private final ObservableList<OrdenSalidaxProducto> masterDataTipo = FXCollections.observableArrayList();
    private final ObservableList<OrdenSalidaxProducto> masterDataTipoSalida = FXCollections.observableArrayList();    
    private final ObservableList<Producto> masterDataProducto = FXCollections.observableArrayList();
    private List<OrdenSalida> salidas_temp;
    private OrdenSalida salida_seleccionada;
    private TipoProducto tipo_devuelto;
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage_envio = new Stage();    
    Stage modal_stage_tipo = new Stage();  
    
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
    
    private void setear_tipos_salida(){ // REVISAR
        String tipo = obtenerTipo();
        if (tipo.equals(OrdenSalida.TIPO.Venta.name())){
            List<OrdenesSalidaxEnvio> salida_envios = OrdenesSalidaxEnvio.where("salida_cod = ?", salida_seleccionada.getString("salida_cod"));
            List<Envio> envios_temp = new ArrayList<Envio>();
            for(OrdenesSalidaxEnvio salida_envio : salida_envios){
                Envio envio_temp = Envio.findById(salida_envio.getInteger("envio_id"));
                envios_temp.add(envio_temp);
            }
            envios_disponibles = envios_temp;
        } 
        
        
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
            setear_tipos_salida();
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
    
    private void insertar_orden_salida_envio(OrdenSalida salida){ //CORREGIR
        // IF TIPO....
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
    
    private void insertarDetalle(Integer idSalida, String codSalida){
       /* for (TipoProducto tipo : masterDataTipoSalida){
            OrdenSalidaxProducto tipoProducto = new OrdenSalidaxProducto();
            tipoProducto.setInteger("salida_id",idSalida);
            tipoProducto.set("salida_cod",codSalida);
            tipoProducto.setInteger("cantidad",tipo.getInteger("cantidad"));
            //falta
        }*/
    }
    
    private void crear_salida(){ //REVISAR
        try{
            Base.openTransaction();  
            OrdenSalida orden_salida = new OrdenSalida();
            orden_salida.set("last_user_change", usuarioActual.getString("usuario_cod"));
            orden_salida.set("tipo",tipos.getSelectionModel().getSelectedItem());
            orden_salida.set("descripcion", descripcion_envio.getText());
            orden_salida.set("salida_cod", codigo_salida.getText());
            orden_salida.set("estado", OrdenSalida.ESTADO.PENDIENTE.name());
            //revisar
            //------------------------------------------------
            orden_salida.set("ruta_orden", "test");
            orden_salida.set("nr_asignados", 1);
            orden_salida.saveIt();
            if (!envios_disponibles.isEmpty()){                
                insertar_orden_salida_envio(orden_salida);                
            }            
            insertarDetalle(orden_salida.getInteger("salida_id"),orden_salida.getString("salida_cod"));
            Base.commitTransaction();            
        }catch (Exception e){
            infoController.show("No se pudo crear la orden de salida : " + e.getMessage()); 
        }
    }
    
    private void eliminar_orden_salida_envio(){
        OrdenesSalidaxEnvio.delete("salida_id = ?", salida_seleccionada.getId());
    }
    
    private void editar_orden_salida(){ //REVISAR
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
            crear_salida();
        } else {
            if (salida_seleccionada == null){ 
                infoController.show("No ha seleccionado ninguna Orden de Salida"); 
                return;
            }
            editar_orden_salida();
        }
        crear_nuevo = false;
        /* Creo que no es necesario
        masterDataSalidas.clear();
        List<OrdenSalida> salidas = OrdenSalida.findAll();
        for(OrdenSalida salida : salidas){
            masterDataSalidas.add(salida);
        } */                  
        limpia_formulario();
        inhabilitar_formulario();
    }
    
    private void actualizar_lista_salida_tabla(){
        for (OrdenSalidaxProducto tipo : masterDataTipo){
            for (OrdenSalidaxProducto tipo_salida : masterDataTipoSalida){
                if (tipo.get("tipo_id")==tipo_salida.get("tipo_id")){
                    tipo_salida.set("cantidad",tipo_salida.getInteger("cantidad")+tipo.getInteger("cantidad"));
                }
            }
        }
            Boolean isNew = false; 
            try{
                if(!masterDataTipoSalida.stream().anyMatch(x -> x.getInteger("tipo_id").equals(producto_disponible.getInteger("tipo_id")))){
                    OrdenCompraxProducto productoxenvio = new OrdenCompraxProducto();
                    productoxenvio.set("tipo_id",producto_disponible.get("tipo_id"));
                    productoxenvio.set("tipo_cod",producto_disponible.get("tipo_cod"));  
                    productoxenvio.set("cantidad", cantidad_producto.getValue());       
                    productoxenvio.set("precio_unitario",producto_disponible.get("precio_unitario"));    
                    productoxenvio.set("subtotal_previo",producto_disponible.get("subtotal_previo")); 
                    productoxenvio.set("descuento",0);
                    productoxenvio.set("flete",0);                    
                    productoxenvio.set("subtotal_final",producto_disponible.get("subtotal_final"));             
                    productos_a_agregar.add(productoxenvio);
                    isNew = true;
                }else{
                    RecalcularTabla(isNew);
                }
            } catch (Exception e) {
                infoController.show("No se ha podido agregar ese Producto: " + e.getMessage());
            } 
        
    }
    
    private void llenar_tabla_salida(){
        for (OrdenSalidaxProducto tipoProd : masterDataTipo){
            masterDataTipoSalida.add(tipoProd);
        }
        
        columna_cod_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("nombre")));
        columna_cant_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getInteger("cantidad").toString()));
        columna_desc_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("descripcion")));

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
                if (tipo_seleccionado.equals(OrdenSalida.TIPO.Venta.name()))
                    agregar_a_lista_envios();                
                actualizar_lista_salida_tabla();
                llenar_tabla_salida();
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
                OrdenSalidaxProducto producto_salida = new OrdenSalidaxProducto();
                producto_salida.set("tipo_id",producto_envio.get("tipo_id"));
                producto_salida.set("tipo_cod",producto_envio.get("tipo_cod"));
                producto_salida.set("cantidad",producto_envio.get("cantidad"));
                                
                masterDataTipo.add(producto_salida);
            }            
        } else{
            OrdenSalidaxProducto producto_salida = new OrdenSalidaxProducto();
            producto_salida.set("tipo_id",tipo_devuelto.getInteger("tipo_id"));
            producto_salida.set("tipo_cod",tipo_devuelto.getString("tipo_cod"));
            producto_salida.setInteger("cantidad",0);
            masterDataTipo.add(producto_salida);            
        }        
        columna_cod_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("nombre")));        
        columna_cant_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getInteger("cantidad").toString()));
        columna_desc_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("descripcion")));
        
        tabla_tipos.setItems(masterDataTipo);
    }
       
    private void mostrar_datos_pedido(){
       envio_dato.setText(envio_devuelto.getString("envio_cod"));
       pedido_dato.setText(envio_devuelto.getString("orden_compra_cod"));
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
            boton_tipo.setVisible(false);
            lbl_cant_tipo.setVisible(false);
            sp_cant_tipo.setVisible(false);
            
            boton_envio.setVisible(true);
            lbl_codigo_envio.setVisible(true);
            envio_dato.setVisible(true);
            lbl_codigo_pedido.setVisible(true);
            pedido_dato.setVisible(true);
        } else{
            boton_tipo.setVisible(true); //Falta implementar este modal
            lbl_cant_tipo.setVisible(true);
            sp_cant_tipo.setVisible(true); //llenarlo con el stock lógico disponible
            
            boton_envio.setVisible(false);
            lbl_codigo_envio.setVisible(false);
            envio_dato.setVisible(false);
            lbl_codigo_pedido.setVisible(false);
            pedido_dato.setVisible(false);
        }
    }    
    
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void cambiarEstado(){
        if(salida_seleccionada == null){
            infoController.show("No ha seleccionado ningun Envio");
            return;
        }
        if (salida_seleccionada.getString("estado").equals(OrdenSalida.ESTADO.COMPLETA.name())){
            infoController.show("El envio ya ha sido completada");
            return;            
        }
        try{
            Base.openTransaction();
            String estado_anterior = salida_seleccionada.getString("estado");
            OrdenSalida.ESTADO siguiente_estado = OrdenSalida.ESTADO.valueOf(estado_anterior).next();
            if(!confirmatonController.show("Esta accion cambiara el envio de estado " + estado_anterior + " a " + siguiente_estado.name(), "¿Desea continuar?")) return;
            salida_seleccionada.set("estado", siguiente_estado.name());
            salida_seleccionada.saveIt();
            
            if (estado_anterior.equals(OrdenSalida.ESTADO.ENPROCESO.name())){
                infoController.show("El Envio fue actualizada correctamente. Todos productos han sido despachados");
                Base.commitTransaction();
                return;
            }            
        }catch (Exception e){
            Base.rollbackTransaction();
            infoController.show("No se ha podido modificar el estado del Envio");
        }
    }
    
    public OrdenesDeSalidaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        //igual se deben guardar los envios para poder llenar la tabla ordenesSalidaXenvio
        envios_disponibles = new ArrayList<Envio>();
    }    
    
    @FXML
    private void handle_buscar_envio(ActionEvent event) throws IOException{
        modal_stage_envio.showAndWait();
        if(envio_devuelto==null) return;
        cargar_tipos_producto();
        mostrar_datos_pedido();
    }    
    
    private void seleccionar_envio() throws Exception
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarEnviosOrdenSalida.fxml"));
            AgregarEnviosController controller = new AgregarEnviosController();
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage_envio.setScene(modal_content_scene); 
            if(modal_stage_envio.getModality() != Modality.APPLICATION_MODAL) modal_stage_envio.initModality(Modality.APPLICATION_MODAL); 
            controller.devolverEnvioEvent.addHandler((Object sender, agregarEnviosArgs args) -> {
                envio_devuelto = args.envio;
            });
            
        }catch(Exception e){
            infoController.show("No se pudo agregar los productos : " + e.getMessage());
        }
    }  
    
    @FXML
    private void handle_buscar_tipo(ActionEvent event) throws IOException{        
        modal_stage_tipo.showAndWait();
        if(tipo_devuelto==null) return;
        cargar_tipos_producto();
        SpinnerValueFactory cantidad_productoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Stock.findFirst("tipo_id = ?", tipo_devuelto.getInteger("tipo_id")).getInteger("stock_real"), 0);
        sp_cant_tipo.setValueFactory(cantidad_productoValues);       
    }
    
    private void setAgregarProductos() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductos.fxml"));
        AgregarProductosController controller = new AgregarProductosController();
        loader.setController(controller);                      
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage_tipo.setScene(modal_content_scene);
        if(modal_stage_tipo.getModality() != Modality.APPLICATION_MODAL) modal_stage_tipo.initModality(Modality.APPLICATION_MODAL);    

        controller.devolverProductoEvent.addHandler((Object sender, agregarProductoArgs args) -> {
            tipo_devuelto = args.producto;
        });       
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        try {                        
            cargar_tabla_index();
            inhabilitar_formulario();
            llenar_combo_box_tipo();
            seleccionar_envio();
            setAgregarProductos();
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
