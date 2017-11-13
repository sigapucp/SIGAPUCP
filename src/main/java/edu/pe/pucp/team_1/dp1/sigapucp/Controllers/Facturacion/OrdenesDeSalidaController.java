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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalidaxProductoFinal;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenesSalidaxEnvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarEnviosArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarInstanciaProductoArgs;
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
import javafx.scene.control.TitledPane;
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
    private ComboBox<String> tipo_buscar;
    @FXML
    private TableView<OrdenSalida> tabla_salidas;
    @FXML
    private TableColumn<OrdenSalida, String> columna_cod_salida;
    @FXML
    private TableColumn<OrdenSalida, String> columna_tipo_salida;
    
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
    @FXML
    private Tab tab_tipo;

    @FXML
    private AnchorPane pane_tipo;
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
    @FXML
    private TitledPane pane_acciones;
    
    //TAB PRODUCTOS
    @FXML
    private Tab tab_producto;
    @FXML
    private AnchorPane pane_producto;
        //TABLA PRODUCTOS
    @FXML
    private TableView<OrdenSalidaxProductoFinal> tabla_productos;
    @FXML
    private TableColumn<OrdenSalidaxProductoFinal, String> columna_cod_prod;
    @FXML
    private TableColumn<OrdenSalidaxProductoFinal, String> columna_tipo_prod;
    @FXML
    private TableColumn<OrdenSalidaxProductoFinal, String> columna_fecha_prod;
     @FXML
    private TableColumn<OrdenSalidaxProductoFinal, String> columna_almacen_prod;
    @FXML
    private TableColumn<OrdenSalidaxProductoFinal, String> columna_rack_prod;

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
    private final ObservableList<OrdenSalidaxProductoFinal> masterDataProductoFinal = FXCollections.observableArrayList();
    private List<OrdenSalida> salidas_temp;
    private List<Producto> productos_instancias;
    private OrdenSalida salida_seleccionada;
    private TipoProducto tipo_devuelto;
    private Producto instancia_devuelta;
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage_envio = new Stage();    
    Stage modal_stage_tipo = new Stage();
    Stage modal_stage_instancia = new Stage();
    
    private void limpia_formulario(){
        try{
            codigo_salida.clear();
            descripcion_envio.clear();
            masterDataTipo.clear();
            masterDataTipoSalida.clear();
            tipos.getItems().clear();
            llenar_combo_box_tipo();
            tipos.setDisable(false);
            masterDataProductoFinal.clear();
            boton_tipo.setVisible(false); 
            lbl_cant_tipo.setVisible(false);
            sp_cant_tipo.setVisible(false);
            
            boton_envio.setVisible(false);
            lbl_codigo_envio.setVisible(false);
            envio_dato.setVisible(false);
            lbl_codigo_pedido.setVisible(false);
            pedido_dato.setVisible(false);
            
            pane_producto.setDisable(true);
            pane_acciones.setDisable(false);
            pane_tipo.setDisable(false);
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
    
    private void setear_tipos_salida(){
        String tipo_salida = obtenerTipo_salida();
        if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name())){
            List<OrdenesSalidaxEnvio> salida_envios = OrdenesSalidaxEnvio.where("salida_id = ?", salida_seleccionada.getString("salida_id"));
            List<Envio> envios_temp = new ArrayList<Envio>();
            for(OrdenesSalidaxEnvio salida_envio : salida_envios){
                Envio envio_temp = Envio.findById(salida_envio.getInteger("envio_id"));
                envios_temp.add(envio_temp);
            }
            envios_disponibles = envios_temp;
        } 
        
        List<OrdenSalidaxProducto> salidaxtipoproducto = OrdenSalidaxProducto.where("salida_id = ?",salida_seleccionada.getInteger("salida_id"));
        for (OrdenSalidaxProducto tipoProd : salidaxtipoproducto)
            masterDataTipoSalida.add(tipoProd);
        llenar_tabla_salida();
    }
    
    private void mostrarProductosFinales(){
        List<OrdenSalidaxProductoFinal> productos_finales = OrdenSalidaxProductoFinal.where("salida_id = ?", salida_seleccionada.getInteger("salida_id"));
        for (OrdenSalidaxProductoFinal producto : productos_finales){
            masterDataProductoFinal.add(producto);            
        }
    }
    
    @FXML
    private void visualizar_orden_salida(ActionEvent event) throws  IOException{
        crear_nuevo = false;
        salida_seleccionada = tabla_salidas.getSelectionModel().getSelectedItem();
        if (salida_seleccionada == null){
            infoController.show("No ha seleccionado ninguna orden de salida");  
            return;            
        }
        try{
            masterDataProductoFinal.clear();
            habilitar_formulario();
            limpia_formulario();
            setear_datos_salida();
            setear_tipos_salida();
            if (salida_seleccionada.getString("estado").equals(OrdenSalida.ESTADO.ENPROCESO.name())){
                pane_producto.setDisable(false);
                pane_tipo.setDisable(true);                
                pane_acciones.setDisable(true);
                
                tipos.setDisable(true);
                descripcion_envio.setEditable(false);
                codigo_salida.setEditable(false);
                mostrarProductosFinales(); 
                actualizar_tabla_instancias_productos();
            } else if (salida_seleccionada.getString("estado").equals(OrdenSalida.ESTADO.PENDIENTE.name())){
                pane_producto.setDisable(true);
                pane_tipo.setDisable(false);
                pane_acciones.setDisable(false);
                
                tipos.setDisable(false);
                descripcion_envio.setEditable(true);
                codigo_salida.setEditable(true);
            } else{
                pane_producto.setDisable(true);
                pane_tipo.setDisable(true);
                pane_acciones.setDisable(true);
                
                tipos.setDisable(true);
                descripcion_envio.setEditable(true);
                codigo_salida.setEditable(true);
            }
        }catch (Exception e){
            infoController.show("No se pudo visualizar la orden de salida : " + e.getMessage());
        }
    }
    
    private boolean cumple_condicion_busqueda(OrdenSalida salida, String codigo, String tipo){
        boolean match = true;        
        if ( codigo.equals("") && tipo.equals("")){
            match = true;
        }else {
            OrdenesSalidaxEnvio salida_envio = OrdenesSalidaxEnvio.first("salida_cod = ?", salida.get("salida_cod"));
            match = (!codigo.equals("")) ? (match && (salida.get("salida_cod")).equals(codigo)) : match;
            match = (!tipo.equals("")) ? (match && (salida_envio.get("orden_compra_cod")).equals(tipo)) : match;
        }
        return match;
    }  
    
    @FXML
    private void buscar_salida(ActionEvent event) throws IOException{
        salidas_temp = OrdenSalida.findAll();
        masterDataSalidas.clear();
        String tipo = (tipos.getSelectionModel().getSelectedItem()==null) ? "":tipos.getSelectionModel().getSelectedItem();
        try{
            for(OrdenSalida salida : salidas_temp){
                if (cumple_condicion_busqueda(salida, salida_buscar.getText(), tipo)){
                    masterDataSalidas.add(salida);
                }
            }
        }catch(Exception e){
            infoController.show("No se pudo buscar la orden de salida : " + e.getMessage());
        }               
    }
    
    private void insertar_orden_salida_envio(OrdenSalida salida){
        for(Envio envio : envios_disponibles){
            OrdenesSalidaxEnvio salidaxenvio = new OrdenesSalidaxEnvio();
            salidaxenvio.set("orden_compra_cod", envio.getString("orden_compra_cod"));
            salidaxenvio.set("client_id", envio.getInteger("client_id"));
            salidaxenvio.set("orden_compra_id", envio.getInteger("orden_compra_id"));
            salidaxenvio.set("salida_cod", salida.getString("salida_cod"));
            salidaxenvio.set("salida_id", salida.getInteger("salida_id"));
            salidaxenvio.set("envio_id", envio.getInteger("envio_id"));
            salidaxenvio.set("envio_cod", envio.getString("envio_cod"));
            salidaxenvio.saveIt();
        }
    }
    
    private void insertarDetalle(Integer idSalida, String codSalida){
        for (OrdenSalidaxProducto salidaxtipoproducto : masterDataTipoSalida){
            salidaxtipoproducto.set("salida_id",idSalida);
            salidaxtipoproducto.set("salida_cod",codSalida);
            salidaxtipoproducto.set("despachado","N");
            salidaxtipoproducto.set("tipo_salida",tipos.getSelectionModel().getSelectedItem());
            salidaxtipoproducto.saveIt();
        }
    }
    
    private void crear_salida(){ 
        try{
            String tipo_salida = obtenerTipo_salida();
            Base.openTransaction();  
            OrdenSalida orden_salida = new OrdenSalida();
            orden_salida.set("last_user_change", usuarioActual.getString("usuario_cod"));
            orden_salida.set("tipo",tipo_salida);
            orden_salida.set("descripcion", descripcion_envio.getText());
            orden_salida.set("salida_cod", codigo_salida.getText());
            orden_salida.set("estado", OrdenSalida.ESTADO.PENDIENTE.name());
            //Se eliminarán estos campos
            //------------------------------------------------
            orden_salida.set("ruta_orden", "test");
            orden_salida.set("nr_asignados", 1);
            orden_salida.saveIt();
            if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name()) && !envios_disponibles.isEmpty())                
                insertar_orden_salida_envio(orden_salida);                
                        
            insertarDetalle(orden_salida.getInteger("salida_id"),orden_salida.getString("salida_cod"));
            Base.commitTransaction();
            infoController.show("La orden ha sido creada satisfactoriamente");
        }catch (Exception e){
            infoController.show("No se pudo crear la orden de salida : " + e.getMessage()); 
        }
    }
    
    private void eliminar_orden_salida_envios(){
        OrdenesSalidaxEnvio.delete("salida_id = ?", salida_seleccionada.getId());
    }
    private void eliminar_detalle(){
        OrdenSalidaxProducto.delete("salida_id = ?", salida_seleccionada.getId());
    }
    
    private void editar_orden_salida(){
        try{
            String tipo_salida = obtenerTipo_salida();
            Base.openTransaction();  
            salida_seleccionada.set("salida_cod",codigo_salida.getText());
            salida_seleccionada.set("descripcion", descripcion_envio.getText());
            salida_seleccionada.set("tipo",tipos.getSelectionModel().getSelectedItem());
            salida_seleccionada.saveIt();
            eliminar_orden_salida_envios();
            if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name()) && !envios_disponibles.isEmpty())  
                insertar_orden_salida_envio(salida_seleccionada);
            if (salida_seleccionada.getString("estado").equals(OrdenSalida.ESTADO.PENDIENTE.name()))
                eliminar_detalle();
            insertarDetalle(salida_seleccionada.getInteger("salida_id"),salida_seleccionada.getString("salida_cod"));
            Base.commitTransaction();  
            infoController.show("La orden ha sido modificada satisfactoriamente");
        }catch (Exception e){
            infoController.show("No se pudo editar la orden de salida : " + e.getMessage()); 
        }
    }
    
    private void guardar_instancias_productos(){
        List<OrdenSalidaxProductoFinal> productosGuardados = OrdenSalidaxProductoFinal.where("salida_id = ?", salida_seleccionada.getId());
        if(productosGuardados!=null)
        {
             List<OrdenSalidaxProductoFinal> salidasProductosDelete = productosGuardados.stream().filter(x -> masterDataProductoFinal.stream().noneMatch(y -> !y.isNew() && 
                y.getInteger("salida_id").equals(x.getInteger("salida_id")) && 
                y.getInteger("producto_id").equals(x.getInteger("producto_id")))).collect(Collectors.toList());
        
            if(salidasProductosDelete == null) return;

            for(OrdenSalidaxProductoFinal salidaxProductoFinal:salidasProductosDelete)
            {
                OrdenSalidaxProductoFinal.delete("salida_id = ? AND producto_id = ?",salidaxProductoFinal.get("salida_id"),salidaxProductoFinal.get("producto_id"));
            }            
        }
              
        for(OrdenSalidaxProductoFinal salidaxproductoFinal:masterDataProductoFinal)
        {
            if(salidaxproductoFinal.isNew())
            {
                salidaxproductoFinal.set("salida_id",salida_seleccionada.get("salida_id"));
                salidaxproductoFinal.set("salida_cod",salida_seleccionada.getString("salida_cod"));
                salidaxproductoFinal.set("estado",OrdenSalidaxProductoFinal.ESTADO.RESERVADO.name());
                salidaxproductoFinal.saveIt(); 
                
                Producto p = Producto.findFirst("producto_id = ?", salidaxproductoFinal.getInteger("producto_id"));
                p.set("estado",Producto.ESTADO.DESPACHADO.name());
                p.saveIt();
            }             
        }           
    }
    
    @Override
    public void guardar(){     
        if (crear_nuevo){
            crear_salida();
        }else {
            if (salida_seleccionada == null){ 
                infoController.show("No ha seleccionado ninguna Orden de Salida"); 
                return;
            }
            if (salida_seleccionada.get("estado").equals(OrdenSalida.ESTADO.ENPROCESO.name())){
                guardar_instancias_productos();
            }
            editar_orden_salida();
        }        
        crear_nuevo = false;
        limpia_formulario();
        inhabilitar_formulario();
        cargar_tabla_index();
    }
    
    private void actualizar_lista_salida_tabla(){
        String tipo_salida = obtenerTipo_salida();
        Integer cantidad = 0;
        for (OrdenSalidaxProducto tipoProd : masterDataTipo){
            Integer cantidad_agregar = 0;
            if (!tipo_salida.equals(OrdenSalida.TIPO.Venta.name())){
                cantidad_agregar = sp_cant_tipo.getValue();
                if (cantidad_agregar>0)
                    tipoProd.set("cantidad",cantidad_agregar);
                else{
                    infoController.show("No se puede agregar un tipo de producto con cantidad 0");
                    return;
                }
            }                
            
            if(masterDataTipoSalida.stream().anyMatch(x -> x.getInteger("tipo_id").equals(tipoProd.getInteger("tipo_id")))){
                for (OrdenSalidaxProducto salidaxtipoproducto : masterDataTipoSalida){
                    if (tipoProd.get("tipo_id")==salidaxtipoproducto.get("tipo_id")){
                        cantidad = salidaxtipoproducto.getInteger("cantidad")+tipoProd.getInteger("cantidad");
                        salidaxtipoproducto.set("cantidad",cantidad);
                    }
                }
            } else {
                masterDataTipoSalida.add(tipoProd);
            }
        }
        masterDataTipo.clear();
    }
    
    private void llenar_tabla_salida(){
        ObservableList<OrdenSalidaxProducto> productos = FXCollections.observableArrayList(); 
        productos.clear();
        productos.addAll(masterDataTipoSalida);
        
        tabla_tipos_salida.getColumns().get(0).setVisible(false);
        tabla_tipos_salida.getColumns().get(0).setVisible(true); 
    }
    
    private void agregar_a_lista_envios(){
        if(!envios_disponibles.stream().anyMatch(x -> x.getInteger("envio_id").equals(envio_devuelto.getInteger("envio_id")))){
            envios_disponibles.add(envio_devuelto);
        }else{
            infoController.show("Ha seleccionado un envio ya agregado");
        }
    }
    
    @FXML
    private void eliminar_tipos_salida(ActionEvent event) throws IOException{
        try {
            masterDataTipoSalida.clear();
            llenar_tabla_salida();
        } catch (Exception e) {
            infoController.show("Ocurrio un error al eliminar los tipos de productos a la orden de salida : " + e.getMessage());
        }
    }
    
    public void actualizar_tabla_instancias_productos(){          
        tabla_productos.getColumns().get(0).setVisible(false);
        tabla_productos.getColumns().get(0).setVisible(true);
    }
    
    private void seleccionar_instancia() throws Exception
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarInstanciaProducto.fxml"));
            AgregarInstanciaProducto controller = new AgregarInstanciaProducto(productos_instancias);
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage_instancia.setScene(modal_content_scene); 
            if(modal_stage_instancia.getModality() != Modality.APPLICATION_MODAL) modal_stage_instancia.initModality(Modality.APPLICATION_MODAL); 
            controller.devolver_instancia_producto.addHandler((Object sender, agregarInstanciaProductoArgs args) -> {
                instancia_devuelta = args.instancia_producto;
            });
            modal_stage_instancia.showAndWait();
        }catch(Exception e){
            infoController.show("No se pudo agregar los productos : " + e.getMessage());
        }
    }      
    
    @FXML
    private void agregar_instancias_producto(ActionEvent event) throws IOException{
        try{            
            Integer cantidad_tipos_total = 0;
            String tipo_salida = obtenerTipo_salida();
            for(OrdenSalidaxProducto salidaxtipoproducto : masterDataTipoSalida){
                Integer cantidad_tipo = 0;
                for (OrdenSalidaxProductoFinal salidaxproductofinal : masterDataProductoFinal){
                    if (salidaxproductofinal.getInteger("tipo_id")==salidaxtipoproducto.getInteger("tipo_id"))
                        cantidad_tipo += 1;
                }
                if (cantidad_tipo<salidaxtipoproducto.getInteger("cantidad")){
                    List<Producto> productosAlmacen;
                    if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name()))
                        productosAlmacen = Producto.where("tipo_id = ? AND estado = ?", salidaxtipoproducto.get("tipo_id"), Producto.ESTADO.RESERVADO.name()).orderBy("fecha_entrada asc");
                    else
                        productosAlmacen = Producto.where("tipo_id = ? AND estado = ?", salidaxtipoproducto.get("tipo_id"), Producto.ESTADO.INGRESADO.name()).orderBy("fecha_entrada asc");
                    productos_instancias.addAll(productosAlmacen);
                    for (OrdenSalidaxProductoFinal salidaxproductofinal : masterDataProductoFinal){
                        for (Producto productoAlmacen : productosAlmacen){
                            if (salidaxproductofinal.getInteger("producto_id")==productoAlmacen.getInteger("producto_id"))
                                productos_instancias.remove(productoAlmacen);
                        }
                    }                            
                }
                cantidad_tipos_total += salidaxtipoproducto.getInteger("cantidad");
            }
            if (cantidad_tipos_total > masterDataProductoFinal.size()){
                seleccionar_instancia(); 
                if (instancia_devuelta != null){
                    OrdenSalidaxProductoFinal salidaxproductofinal = new OrdenSalidaxProductoFinal();
                    salidaxproductofinal.set("producto_cod",instancia_devuelta.getString("producto_cod"));
                    salidaxproductofinal.set("producto_id",instancia_devuelta.getInteger("producto_id"));
                    salidaxproductofinal.set("tipo_cod",instancia_devuelta.getString("tipo_cod"));
                    salidaxproductofinal.set("tipo_id",instancia_devuelta.getInteger("tipo_id"));
                    salidaxproductofinal.set("producto_cod",instancia_devuelta.getString("producto_cod"));
                    salidaxproductofinal.set("orden_entrada_cod",instancia_devuelta.get("orden_entrada_cod"));
                    salidaxproductofinal.set("orden_entrada_id",instancia_devuelta.get("orden_entrada_id"));
                    masterDataProductoFinal.add(salidaxproductofinal);
                }
                productos_instancias.clear();
                //actualizar_tabla_instancias_productos();
            } else{
                infoController.show("Se completó la cantidad de productos de cada tipo");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML
    private void eliminar_instancias_producto(ActionEvent event) throws IOException{
        OrdenSalidaxProductoFinal producto_seleccionado = tabla_productos.getSelectionModel().getSelectedItem();
        if (producto_seleccionado == null){
            infoController.show("No se ha seleccionado ningún producto");
            return;
        }
        String tipo_salida = obtenerTipo_salida();
        
        Producto producto_aux = Producto.findFirst("producto_id = ?", producto_seleccionado.get("producto_id"));
        if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name()))
            producto_aux.set("estado",Producto.ESTADO.RESERVADO.name());
        else
            producto_aux.set("estado",Producto.ESTADO.INGRESADO.name());
        producto_aux.saveIt();
        masterDataProductoFinal.remove(producto_seleccionado);
        
        actualizar_tabla_instancias_productos();
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
        
        String tipo_salida = obtenerTipo_salida();
        if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name())){
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
    }
       
    private void mostrar_datos_pedido(){
       envio_dato.setText(envio_devuelto.getString("envio_cod"));
       pedido_dato.setText(envio_devuelto.getString("orden_compra_cod"));
    }
    
    @Override
    public void nuevo(){
        crear_nuevo = true;
        habilitar_formulario();
        limpia_formulario();
    }
    
    private void cargar_tabla_index(){
        masterDataSalidas.clear();
        List<OrdenSalida> salidas = OrdenSalida.findAll();
        for(OrdenSalida salida : salidas){
            masterDataSalidas.add(salida);
        }        
        
        columna_cod_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("salida_cod")));
        columna_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalida, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo")));

        tabla_salidas.setItems(masterDataSalidas);        
        
        columna_cod_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("nombre")));        
        columna_cant_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getInteger("cantidad").toString()));
        columna_desc_tipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("descripcion")));
        
        tabla_tipos.setItems(masterDataTipo);
        
        columna_cod_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("nombre")));
        columna_cant_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getInteger("cantidad")));
        columna_desc_tipo_salida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?", p.getValue().getInteger("tipo_id")).getString("descripcion")));

        tabla_tipos_salida.setItems(masterDataTipoSalida);
        
        //tabla 2do tab
        columna_cod_prod.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProductoFinal, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("producto_cod")));
        columna_tipo_prod.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProductoFinal, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?",p.getValue().getInteger("tipo_id")).getString("nombre")));
        columna_fecha_prod.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProductoFinal, String> p) -> new ReadOnlyObjectWrapper(Producto.findFirst("tipo_id = ?",p.getValue().getInteger("tipo_id")).get("fecha_entrada")));
        columna_almacen_prod.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProductoFinal, String> p) -> new ReadOnlyObjectWrapper(Producto.findFirst("producto_id = ?",p.getValue().getInteger("producto_id")).get("almacen_cod")));
        columna_rack_prod.setCellValueFactory((TableColumn.CellDataFeatures<OrdenSalidaxProductoFinal, String> p) -> new ReadOnlyObjectWrapper(Producto.findFirst("producto_id = ?",p.getValue().getInteger("producto_id")).get("rack_cod")));
        
        tabla_productos.setItems(masterDataProductoFinal);
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
    
    private String obtenerTipo_salida(){
        String tipo = (tipos.getSelectionModel().getSelectedItem() == null ) ? "" : tipos.getSelectionModel().getSelectedItem(); 
        return tipo;
    }
    
    private void manejarcomboBoxTipoSalida(){
        masterDataTipo.clear();        
        tabla_tipos.getItems().clear();
        
        masterDataTipoSalida.clear();
        tabla_tipos_salida.getItems().clear();
        
        String tipo_salida = obtenerTipo_salida();
        
        if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name())){
            envios_disponibles.clear();
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
            infoController.show("No ha seleccionado ninguna orden de salida");
            return;
        }
        if (salida_seleccionada.getString("estado").equals(OrdenSalida.ESTADO.COMPLETA.name())){
            infoController.show("Esta orden de salida ya ha sido completada");
            return;            
        }
        try{
            Base.openTransaction();
            String estado_anterior = salida_seleccionada.getString("estado");
            OrdenSalida.ESTADO siguiente_estado = OrdenSalida.ESTADO.valueOf(estado_anterior).next();
            if(!confirmatonController.show("Esta acción cambiará la orden de salida de estado " + estado_anterior + " a " + siguiente_estado.name(), "¿Desea continuar?")) return;
            Integer cantidad = 0;
            
            if (siguiente_estado.name().equals(OrdenSalida.ESTADO.COMPLETA.name())){
                for (OrdenSalidaxProducto salidaxtipoproducto : masterDataTipoSalida)
                    cantidad += salidaxtipoproducto.getInteger("cantidad");
                if (masterDataProductoFinal.size()==cantidad){
                    salida_seleccionada.set("estado", siguiente_estado.name());
                    salida_seleccionada.saveIt();
                    infoController.show("La orden de salida fue actualizada correctamente. Todos productos han sido despachados");
                    Base.commitTransaction();
                    return;
                } else{
                    infoController.show("No se ha completado la cantidad de productos específicos necesario en la orden de salida");
                    return;
                }
            }
            salida_seleccionada.set("estado", siguiente_estado.name());
            salida_seleccionada.saveIt();
            
            // generar productos más antiguos en venta
            String tipo_salida = obtenerTipo_salida();
            if (tipo_salida.equals(OrdenSalida.TIPO.Venta.name())){
                productos_instancias.clear();
                for(OrdenSalidaxProducto salidaxtipoproducto : masterDataTipoSalida){
                    Integer cantidad_tipo = 0;
                    for (OrdenSalidaxProductoFinal salidaxproductofinal : masterDataProductoFinal){
                        if (salidaxproductofinal.getInteger("tipo_id")==salidaxtipoproducto.getInteger("tipo_id"))
                            cantidad_tipo += 1;
                    }
                    if (cantidad_tipo<salidaxtipoproducto.getInteger("cantidad")){
                        List<Producto> aux_productos = Producto.where("tipo_id = ? AND estado = ?", salidaxtipoproducto.get("tipo_id"),Producto.ESTADO.RESERVADO.name()).orderBy("fecha_entrada asc");
                        Integer contador = salidaxtipoproducto.getInteger("cantidad");
                        for (Producto p : aux_productos){
                            OrdenSalidaxProductoFinal salidaxproductofinal = new OrdenSalidaxProductoFinal();
                            masterDataProductoFinal.add(salidaxproductofinal);
                            p.set("estado",Producto.ESTADO.DESPACHADO.name());
                            p.saveIt();
                            contador --;
                            if (contador ==0) break;
                        }                                    
                    }
                }
                actualizar_tabla_instancias_productos();
                guardar_instancias_productos();
                infoController.show("Fueron asignados los productos más antiguos de cada tipo seleccionado a esta salida");
            }
            infoController.show("La orden de salida fue actualizada correctamente. Ya puede modificar los productos específicos");
            Base.commitTransaction();
            limpia_formulario();
            inhabilitar_formulario();
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
            tipo_buscar.getItems().add("");
            ObservableList<String> tipos_enum = FXCollections.observableArrayList();   
            tipos_enum.addAll(Arrays.asList(OrdenSalida.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()));  
            tipo_buscar.setItems(tipos_enum);
            llenar_combo_box_tipo();
            productos_instancias = new ArrayList<Producto>();
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
