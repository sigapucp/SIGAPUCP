/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import com.sun.javafx.font.FontConstants;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas.ClientesController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas.PedidosController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEventHandler;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Lote;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Unidad;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Precio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Proveedor;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
public class OrdenesDeEntradaController extends Controller {

    @FXML
    private AnchorPane usuario_container;
    @FXML
    private TextField BusquedaCodigo;
    @FXML
    private ComboBox<String> BusquedaEstado;
    @FXML
    private ComboBox<String> BusquedaTipo;
    @FXML
    private TableView<OrdenEntrada> TablaOrdenes;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaCodigo;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaFecha;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaTipo;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaEstado;
    @FXML
    private TitledPane TitlePane;
    @FXML
    private Label ProveedorLabel;
    @FXML
    private TextField ClienteBuscar;
    @FXML
    private ComboBox<String> VerTipo;
    @FXML
    private DatePicker VerFecha;
    @FXML
    private Label ClienteLabel;
    @FXML
    private TextField ProveedorBuscar;
    @FXML
    private TextArea VerDescripcion;
    @FXML
    private TextField VerProducto;
    @FXML
    private Spinner<Integer> verCantidad;
    private SpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
    
    @FXML
    private TableView<OrdenEntradaxProducto> TablaProductos;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoCodigo;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoNombre;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoCantidad;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoDescripcion;
    @FXML
    private DatePicker BusquedaFecha;
    @FXML
    private GridPane formulario_grid;
        
    static Stage modal_stage = new Stage();    
    private final ObservableList<OrdenEntrada> entradas = FXCollections.observableArrayList();
    private final ObservableList<OrdenEntradaxProducto> productos = FXCollections.observableArrayList();   
       
    private OrdenEntrada entradaSelecionada;
    private Boolean crearNuevo;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmationController;
    
    private List<Cliente> autoCompletadoClienteList;    
    private List<Proveedor> autoCompletadoProveedorList;
    private List<TipoProducto> autoCompletadoProductoList;
    ArrayList<String> possiblewordsCliente = new ArrayList<>();    
    ArrayList<String> possiblewordsProveedor = new ArrayList<>();    
    ArrayList<String> possiblewordsProducto = new ArrayList<>();       
    AutoCompletionBinding<String> autoCompletionBindingCliente;
    AutoCompletionBinding<String> autoCompletionBindingProveedor;
    AutoCompletionBinding<String> autoCompletionBindingProducto;
    TipoProducto productoDevuelto = null;
    Proveedor proveedorDevuelto = null;
    Cliente clienteDevuelto = null;
    Cliente clienteBuscado;
    Proveedor proveedorBuscado;
    TipoProducto procutoBuscado;
  
   
    public OrdenesDeEntradaController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        
        
        List<OrdenEntrada> tempEntradas = OrdenEntrada.findAll();        
        for (OrdenEntrada entrada : tempEntradas) {
            entradas.add(entrada);
        }                    
        
        infoController = new InformationAlertController();
        confirmationController = new ConfirmationAlertController();
        clienteBuscado = new Cliente();
        proveedorBuscado = new Proveedor();
        procutoBuscado = new TipoProducto();
        
        crearNuevo = false;
        entradaSelecionada = null;
    }
    
    private void deshabilitar_formulario()
    {
        formulario_grid.setDisable(true);
    }
    
    private void habilitar_formulario()
    {
        formulario_grid.setDisable(false);
    }
    
    @FXML
    private void visualizarOrden(ActionEvent event) {  
        try {            
            entradaSelecionada = TablaOrdenes.getSelectionModel().getSelectedItem();
            if(entradaSelecionada == null)
            {
                infoController.show("No ha seleccionado ninguna Orden");
                return;
            }
            habilitar_formulario();
            VerFecha.setValue(entradaSelecionada.getDate("fecha_emision").toLocalDate());
            VerDescripcion.setText(entradaSelecionada.getString("descripcion"));
            VerTipo.getSelectionModel().select(entradaSelecionada.getString("tipo"));

            productos.clear();            
            
            List<OrdenEntradaxProducto> ordenesxproductos = OrdenEntradaxProducto.where("orden_entrada_id = ?", entradaSelecionada.getId());
            productos.addAll(ordenesxproductos);
            String tipo = entradaSelecionada.getString("tipo");
            if(tipo.equals(OrdenEntrada.TIPO.Compra.name()))
            {
                ProveedorBuscar.setText(Proveedor.findFirst("proveedor_id = ?", entradaSelecionada.get("proveedor_id")).getString("name"));
            }
            
            if(tipo.equals(OrdenEntrada.TIPO.Devolucion.name()))
            {
                ProveedorBuscar.setText(Cliente.findFirst("client_id = ?", entradaSelecionada.get("client_id")).getString("nombre"));
            }
            
        } catch (Exception e) {
            infoController.show("Error en Entrada Seleccionada");
        }                        
    }

    @FXML
    private void buscarOrden(ActionEvent event) {
        String cod = BusquedaCodigo.getText();
        String estado = BusquedaEstado.getSelectionModel().getSelectedItem();
        LocalDate fecha = BusquedaFecha.getValue();
        String tipo = BusquedaTipo.getSelectionModel().getSelectedItem();
                
        List<OrdenEntrada> tempOrdenes = OrdenEntrada.findAll();
        
        
        if(cod!=null&&!cod.isEmpty())
        {            
            tempOrdenes = tempOrdenes.stream().filter(p -> p.getString("orden_entrada_cod").equals(cod)).collect(Collectors.toList());
        }

        if(estado!=null&&!estado.isEmpty())
        {                
            tempOrdenes = tempOrdenes.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
        }
        
        if(tipo!=null&&!tipo.isEmpty())
        {                
            tempOrdenes = tempOrdenes.stream().filter(p -> p.get("tipo").equals(estado)).collect(Collectors.toList());
        }
        
        if(fecha!=null)
        {                
            tempOrdenes = tempOrdenes.stream().filter(p -> p.get("fecha_emision").equals(Date.valueOf(fecha))).collect(Collectors.toList());
        }        
        RefrescarTabla(tempOrdenes);
    }

    @FXML
    private void agregarProducto(ActionEvent event) {
        if(productoDevuelto==null)
        {
            infoController.show("No ha seleccionado ningun producto");
            return;
        }
        
        for(OrdenEntradaxProducto producto:productos)
        {
            if(producto.getInteger("tipo_id").equals(productoDevuelto.getInteger("tipo_id")))
            {
                Integer cantidad = producto.getInteger("cantidad") + verCantidad.getValue();
                producto.set("cantidad",cantidad);
                TablaProductos.getColumns().get(0).setVisible(false);
                TablaProductos.getColumns().get(0).setVisible(true);
                return;
            }
        }
        
        OrdenEntradaxProducto ordenxproducto = new OrdenEntradaxProducto();
        ordenxproducto.set("tipo_id",productoDevuelto.getId());
        ordenxproducto.set("tipo_cod",productoDevuelto.get("tipo_cod"));
        ordenxproducto.set("estado",productoDevuelto.get("estado"));
        ordenxproducto.set("cantidad",verCantidad.getValue());
        ordenxproducto.set("ingresado","N");
        productos.add(ordenxproducto);
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
        OrdenEntradaxProducto ordenSeleccionada = TablaProductos.getSelectionModel().getSelectedItem();
        if(ordenSeleccionada==null)
        {
            infoController.show("No ha seleccionado ninguna Orden");
            return;
        } 
        
       
        if(!ordenSeleccionada.isNew())
        {
            OrdenEntrada orden = OrdenEntrada.findById(ordenSeleccionada.get("orden_entrada_id"));         
             String estado = orden.getString("estado");
            if((estado.equals(OrdenEntrada.ESTADO.Parcial.name())||estado.equals(OrdenEntrada.ESTADO.Completa.name())))
            {
                infoController.show("No puede eliminar producto ya que este ya se encuentra en almacen. Debera utilizar una Orden de Salida");
                return;                
            }                        
        }
        productos.remove(ordenSeleccionada);      
    }    
    
    @FXML
    private void buscarProducto(ActionEvent event) {
        modal_stage.showAndWait();
        if(productoDevuelto==null) return;        
        VerProducto.setText(productoDevuelto.getString("nombre"));
    }

    /**
     * Initializes the controller class.
     */
    
    private void crear_orden()
    {
        try {            
            Base.openTransaction();               
            String tipo = VerTipo.getSelectionModel().getSelectedItem();
            Date fecha = Date.valueOf(VerFecha.getValue());
            String descripcion = VerDescripcion.getText();                       
            if(tipo.equals(OrdenEntrada.TIPO.Compra.name())&&productoDevuelto==null)
            {
                infoController.show("Debe de especificar el proveedor");
                return;
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Devolucion.name())&&clienteDevuelto==null)
            {
                infoController.show("Debe de especificar el cliente");
                return;
            }                                                          
            OrdenEntrada orden = new OrdenEntrada();            
            orden.set("tipo",tipo);
            orden.setDate("fecha_emision",fecha);
            orden.set("descripcion",descripcion);
            orden.set("client_id",null);
            orden.set("proveedor_id",null);
            orden.set("provuder_ruc",null);
            String codInicial = "DEF";                        
            
            if(tipo.equals(OrdenEntrada.TIPO.Compra.name()))
            {
                codInicial = "CPR";
                orden.set("proveedor_id",proveedorDevuelto.getId());
                orden.set("provuder_ruc",proveedorDevuelto.get("provuder_ruc"));
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Devolucion.name()))
            {
                codInicial = "DEV";
                orden.set("client_id",clienteDevuelto.getId());
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Encuentro.name()))
            {
                codInicial = "ENC";
                
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Otras.name()))
            {
                codInicial = "OTR";                
            }
                        
            String cod = (codInicial + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from ordenesentrada_orden_entrada_id_seq")))) + 1));
                       
            orden.set("orden_entrada_cod",cod);
            orden.set("estado",OrdenEntrada.ESTADO.Pendiente.name());
            orden.set("last_user_change",usuarioActual.getString("usuario_cod"));            
            orden.saveIt();
            
            set_productos(orden);
            
            infoController.show("La orden ha sido creada exitosamente");
            Base.commitTransaction();    
            limpiar_formulario();
        } catch (Exception e) {
            infoController.show("La orden contiene errores : " + e);        
            Base.rollbackTransaction();
        }
    }
    
     public void editar_orden(OrdenEntrada orden){
        try{
            Base.openTransaction();   
            
            String tipo = VerTipo.getSelectionModel().getSelectedItem();
            Date fecha = Date.valueOf(VerFecha.getValue());
            String descripcion = VerDescripcion.getText();                       
            if(tipo.equals(OrdenEntrada.TIPO.Compra.name())&&productoDevuelto==null)
            {
                infoController.show("Debe de especificar el proveedor");
                return;
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Devolucion.name())&&clienteDevuelto==null)
            {
                infoController.show("Debe de especificar el cliente");
                return;
            }                                                                          
            orden.set("tipo",tipo);
            orden.setDate("fecha_emision",fecha);
            orden.set("descripcion",descripcion);
            String codInicial = "DEF";
            
            if(tipo.equals(OrdenEntrada.TIPO.Compra.name()))
            {
                codInicial = "CPR";
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Devolucion.name()))
            {
                codInicial = "DEV";
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Encuentro.name()))
            {
                codInicial = "ENC";
                
            }            
            if(tipo.equals(OrdenEntrada.TIPO.Otras.name()))
            {
                codInicial = "OTR";                
            }
                        
            String cod =  orden.getString("orden_entrada_id");
                                   
            orden.set("estado",OrdenEntrada.ESTADO.Pendiente.name());
            orden.set("last_user_change",usuarioActual.getString("usuario_cod"));            
            orden.saveIt();
            
            set_productos(orden);            
            
            Base.commitTransaction();                         
            infoController.show("La Orden ha sido editada satisfactoriamente"); 
            limpiar_formulario();
        }
        catch(Exception e){
            infoController.show("La orden contiene errores : " + e);                 
            Base.rollbackTransaction();
        }
    }
     
    private void set_productos(OrdenEntrada orden) throws Exception
    {      
        List<OrdenEntradaxProducto> ordenesxproductosGuardados = OrdenEntradaxProducto.where("orden_entrada_id = ?", orden.getId());
        for(OrdenEntradaxProducto ordenxproducto:productos)
        {
            if(ordenxproducto.isNew())
            {
                ordenxproducto.set("orden_entrada_id",orden.getId());
                ordenxproducto.set("orden_entrada_cod",orden.get("orden_entrada_cod"));               
            }
            ordenxproducto.saveIt();
        }                  
        if(ordenesxproductosGuardados == null) return;
        List<OrdenEntradaxProducto> ordenesProductosDelete = ordenesxproductosGuardados.stream().filter(x -> productos.stream().noneMatch(y -> !y.isNew() && 
                y.getInteger("orden_entrada_id").equals(x.getInteger("orden_entrada_id")) && 
                y.getInteger("tipo_id").equals(x.getInteger("tipo_id")))).collect(Collectors.toList());
        
        if(ordenesProductosDelete == null)return;
        
        for(OrdenEntradaxProducto ordenxproducto:ordenesProductosDelete)
        {
            OrdenEntradaxProducto.delete("orden_entrada_id = ? AND tipo_id = ?",ordenxproducto.get("orden_entrada_id"),ordenxproducto.get("tipo_id"));
        }
    }
    
    
    @Override
    public void nuevo(){
        crearNuevo = true;
        limpiar_formulario(); 
        habilitar_formulario();
        entradaSelecionada = null;
    }
    
    @Override
    public void cargar(){
        //validamos que los campos sean los correctos
        if(!confirmationController.show("Verifique que el formato del archivo .csv sea: \n Tipo de orden,fecha,descripcion,nombre cliente,nombre proveedor,ruc proveedor,", "¿Desea continuar?")) return;
        //csv
        String filename = "data_ordenes_entrada.csv";
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
                    if (values.length != 6) {
                        infoController.show("El archivo .csv no tiene el formato adecuado. Verifique que sea\nTipo de orden,fecha,descripcion,nombre cliente,nombre proveedor,ruc proveedor,"); 
                        return;
                    }                    
                    continue; //nos saltamos el encabezado
                }
                String tipo = values[0];
                Date fecha = Date.valueOf(values[1]);
                String descripcion = values[2];
                Base.openTransaction();
                OrdenEntrada orden = new OrdenEntrada();
                orden.set("tipo",tipo);
                orden.setDate("fecha_emision",fecha);
                orden.set("descripcion",descripcion);
                buscar_proveedor(values[4]);
                buscar_cliente(values[3]);
                orden.set("client_id",null);
                orden.set("proveedor_id",null);
                orden.set("provuder_ruc",null);
                String codInicial = "DEF";
                if(tipo.equals(OrdenEntrada.TIPO.Compra.name()))
                {
                    codInicial = "CPR";
                    orden.set("proveedor_id",proveedorBuscado.getId());
                    orden.set("provuder_ruc",proveedorBuscado.get("provuder_ruc"));
                }            
                if(tipo.equals(OrdenEntrada.TIPO.Devolucion.name()))
                {
                    codInicial = "DEV";
                    orden.set("client_id",clienteBuscado.getId());
                }            
                if(tipo.equals(OrdenEntrada.TIPO.Encuentro.name()))
                {
                    codInicial = "ENC";

                }            
                if(tipo.equals(OrdenEntrada.TIPO.Otras.name()))
                {
                    codInicial = "OTR";                
                }

                String cod = (codInicial + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from ordenesentrada_orden_entrada_id_seq")))) + 1));
                orden.set("orden_entrada_cod",cod);
                orden.set("estado",OrdenEntrada.ESTADO.Pendiente.name());
                orden.set("last_user_change",usuarioActual.getString("usuario_cod"));            
                //orden.saveIt();
                try {
                   // set_productos(orden);
                } catch (Exception ex) {
                    Logger.getLogger(OrdenesDeEntradaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //infoController.show("La orden ha sido creada exitosamente");
                //Base.commitTransaction();
                System.out.println("CORRECTO");
            }
            infoController.show("¡Carga masiva de datos de ordenes de entrada exitosa!");
            inputStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("INCORRECTO");
            Base.rollbackTransaction();
            Logger.getLogger(OrdenesDeEntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    @Override
    public void guardar(){
        if (crearNuevo){
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.OrdendeEntrada, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crearNuevo = false;
                return;
            }
            crear_orden();
            deshabilitar_formulario();
            entradaSelecionada = null;
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.OrdendeEntrada ,this.usuarioActual);
        }
        else {
            if(entradaSelecionada==null) 
            {
                infoController.show("No ha seleccionado una Orden de Entrada");            
                return;
            }
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.OrdendeEntrada, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            
            String estado = entradaSelecionada.getString("estado");
            
            if(estado.equals(OrdenEntrada.ESTADO.Parcial.name())||estado.equals(OrdenEntrada.ESTADO.Completa.name()))
            {
                infoController.show("La orden se encuentra en el estado: " + estado + " por lo que ya no puede ser modificada");
                return;
            }
                    
            editar_orden(entradaSelecionada);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.OrdendeEntrada ,this.usuarioActual);
        }    
        crearNuevo = false;
        RefrescarTabla(OrdenEntrada.findAll());
    }
    
    @Override
    public void cambiarEstado()
    {
        if(entradaSelecionada == null)
        {
            infoController.show("No ha seleccionado ninguna Orden de Entrada");
            return;
        }
        
        if(entradaSelecionada.getString("estado").equals(OrdenEntrada.ESTADO.Completa.name()))
        {
            infoController.show("La orden ya ha sido completada");
            return;
        }                
        
        try {
            Base.openTransaction();
            
            String estadoAnterior = entradaSelecionada.getString("estado");
            
            OrdenEntrada.ESTADO siguienteEstado = OrdenEntrada.ESTADO.valueOf(estadoAnterior).next();
            
            if(!confirmationController.show("Esta accion cambiara la Orden del estado " + estadoAnterior + " a " + siguienteEstado.name(), "¿Desea continuar?")) return;
            
            entradaSelecionada.set("estado",siguienteEstado.name());                       
            entradaSelecionada.saveIt();            
                                    
            Lote nuevoLote = null;
            if(estadoAnterior.equals(OrdenEntrada.ESTADO.Parcial.name())) 
            {
                infoController.show("La Orden de entrada fue actualizada correctamente. Los productos han sido ingresados");
                Base.commitTransaction();
            
                TablaOrdenes.getColumns().get(0).setVisible(false);
                TablaOrdenes.getColumns().get(0).setVisible(true); 
                
                return;
            }
            
            if(entradaSelecionada.getString("tipo").equals(OrdenEntrada.TIPO.Compra.name()))
            {
                nuevoLote = new Lote();
                nuevoLote.set("fecha_entrada",Date.valueOf(LocalDate.now()));
                nuevoLote.set("proveedor_id",entradaSelecionada.get("proveedor_id"));
                nuevoLote.set("proveedor_ruc",entradaSelecionada.get("provuder_ruc"));
                String cod = "LOT" + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from lotes_lote_id_seq")))) + 1);
                nuevoLote.set("lote_cod", cod);
                nuevoLote.saveIt();
            }
                        
            for(OrdenEntradaxProducto producto:productos)
            {
                Integer cantidadProductos = producto.getInteger("cantidad");
                for(int i = 0;i<cantidadProductos;i++)
                {
                    Producto nuevoProducto = new Producto();                
                    nuevoProducto.set("tipo_id",producto.get("tipo_id"));
                    nuevoProducto.set("tipo_cod",producto.get("tipo_cod"));
                    
                    nuevoProducto.set("orden_entrada_id",producto.get("orden_entrada_id"));
                    nuevoProducto.set("orden_entrada_cod",producto.get("orden_entrada_cod"));
                    
                    nuevoProducto.set("fecha_adquirida",entradaSelecionada.getDate("fecha_emision"));
                    nuevoProducto.set("fecha_entrada",Date.valueOf(LocalDate.now()));
                    
                    nuevoProducto.set("almacen_xy_id",null);
                    nuevoProducto.set("almacen_z_id",null);
                    nuevoProducto.set("rack_id",null);
                    nuevoProducto.set("almacen_id",null);
                    
                    nuevoProducto.set("ubicado","N");
                    nuevoProducto.set("estado",Producto.ESTADO.INGRESADO.name());
                    
                    // Manejar Lotes?
                    if(nuevoLote != null)
                    {
                        nuevoProducto.set("lote_id",nuevoLote.getId());
                        nuevoProducto.set("lote_cod",nuevoLote.get("lote_cod"));
                    }
                    
                    String cod = producto.get("tipo_cod") + "_" + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from productos_producto_id_seq")))) + 1);
                    
                    nuevoProducto.set("producto_cod",cod);                                                                    
                    nuevoProducto.saveIt();
                }                
                producto.set("ingresado","S");
                producto.saveIt();
            }                        
            Base.commitTransaction();            
            TablaOrdenes.getColumns().get(0).setVisible(false);
            TablaOrdenes.getColumns().get(0).setVisible(true);            
            infoController.show("La Orden de entrada fue actualizada correctamente. Los productos han sido ingresados");
        } catch (Exception e) {
            Base.rollbackTransaction();
            infoController.show("No se ha podido modificar el estado de la orden");
        }
    }
    
    private void limpiar_formulario()
    {      
        productos.clear();
        VerDescripcion.clear();
        VerProducto.clear();
        valueFactory.setValue(1);
        VerTipo.getSelectionModel().clearSelection();        
    }         
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.OrdendeEntrada;
    }
    
      private void RefrescarTabla(List<OrdenEntrada> entradaRefresh)
    {        
        try {
            entradas.removeAll(entradas);                 
            if(entradaRefresh == null) return;
            for (OrdenEntrada entrada : entradaRefresh) {
                entradas.add(entrada);
            }               
            TablaOrdenes.getColumns().get(0).setVisible(false);
            TablaOrdenes.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("La orden de entradada contiene errores : " + e.getMessage());      
        }                                  
    }
      
    private void handleAutoCompletarCliente() {
        int i = 0;
        for (Cliente cliente : autoCompletadoClienteList){
            String nombre = cliente.getString("nombre");
            if(nombre == null) continue;
            if (nombre.equals(ClienteBuscar.getText())){           
                  clienteDevuelto = cliente;
            }
        }
    }
    
    private void buscar_cliente(String nombre_cli){
        int i = 0;
        for (Cliente cliente : autoCompletadoClienteList){
            String nombre = cliente.getString("nombre");
            if(nombre == null) continue;
            if (nombre.equals(nombre_cli)){           
                  clienteBuscado = cliente;
            }
        }
    }
    
    private void buscar_proveedor(String nombre_prov){
        int i = 0;
        for (Proveedor proveedor : autoCompletadoProveedorList){
            String nombre = proveedor.getString("name");
            if(nombre == null) continue;
            if (nombre.equals(nombre_prov)){           
               proveedorBuscado = proveedor;
            }
        }    
    }
    
    
    private void handleAutoCompletarProveedor() {
        int i = 0;
        for (Proveedor proveedor : autoCompletadoProveedorList){
            String nombre = proveedor.getString("name");
            if(nombre == null) continue;
            if (nombre.equals(ProveedorBuscar.getText())){           
               proveedorDevuelto = proveedor;
            }
        }
    }
    
    private void handleAutoCompletarProducto() {
        int i = 0;
        for (TipoProducto tipoProducto : autoCompletadoProductoList){
            String nombre = tipoProducto.getString("nombre");
            if(nombre == null) continue;
            if (nombre.equals(VerProducto.getText())){           
                productoDevuelto = tipoProducto;             
            }
        }
    }
    
    private void clienteToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoClienteList){
            words.add(cliente.getString("nombre"));
        }        
        possiblewordsCliente = words;
    }
    
    private void proveedorToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Proveedor proveedor : autoCompletadoProveedorList){
            words.add(proveedor.getString("name"));
        }        
        possiblewordsProveedor = words;
    }
     
     private void productoToString() {
        ArrayList<String> words = new ArrayList<>();
        for (TipoProducto producto : autoCompletadoProductoList){
            words.add(producto.getString("nombre"));
        }               
        possiblewordsProducto = words;
    }             
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("orden_entrada_cod")));
            ColumnaFecha.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getDate("fecha_emision").toString()));
            ColumnaEstado.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("estado")));
            ColumnaTipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("tipo")));
            
            ColumnaProductoNombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
            ColumnaProductoDescripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
            ColumnaProductoCodigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("tipo_cod")));
            ColumnaProductoCantidad.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("cantidad")));
            
            ObservableList<String> estados = FXCollections.observableArrayList();                           
            ObservableList<String> tipos = FXCollections.observableArrayList();                        
            ObservableList<String> tiposNoPad = FXCollections.observableArrayList();            
            
            estados.add("");
            estados.addAll(Arrays.asList(OrdenEntrada.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));  
            
            tipos.add("");
            tipos.addAll( Arrays.asList(OrdenEntrada.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()) );
            
            tiposNoPad.addAll(Arrays.asList(OrdenEntrada.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList())); 
            
            BusquedaEstado.setItems(estados);
            BusquedaTipo.setItems(tipos);
            
            VerTipo.setItems(tiposNoPad);
            verCantidad.setValueFactory(valueFactory);
            
            VerTipo.valueProperty().addListener(new ChangeListener<String>() {           
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                    boolean enter = false;
                    if(newValue==null) return;
                    if(newValue.equals(OrdenEntrada.TIPO.Compra.name()))
                    {
                        ProveedorLabel.setVisible(true);
                        ProveedorBuscar.setVisible(true);
                        ClienteLabel.setVisible(false);
                        ClienteBuscar.setVisible(false);
                        enter = true;
                    }                    
                    if(newValue.equals(OrdenEntrada.TIPO.Devolucion.name()))
                    {
                        ProveedorLabel.setVisible(false);
                        ProveedorBuscar.setVisible(false);
                        ClienteLabel.setVisible(true);
                        ClienteBuscar.setVisible(true);
                        enter = true;
                    }                
                    
                    if(!enter)
                    {
                        ProveedorLabel.setVisible(false);
                        ProveedorBuscar.setVisible(false);
                        ClienteLabel.setVisible(false);
                        ClienteBuscar.setVisible(false);                        
                    }
                }
            });
            
            ProveedorLabel.setVisible(false);
            ProveedorBuscar.setVisible(false);
            ClienteLabel.setVisible(false);
            ClienteBuscar.setVisible(false);
            
            autoCompletadoClienteList = Cliente.findAll();
            autoCompletadoProductoList = TipoProducto.findAll();
            autoCompletadoProveedorList = Proveedor.findAll();
            
            clienteToString();
            autoCompletionBindingCliente = TextFields.bindAutoCompletion(ClienteBuscar, possiblewordsCliente);
            
            autoCompletionBindingCliente.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletarCliente();
            });
            
            proveedorToString();
            autoCompletionBindingProveedor = TextFields.bindAutoCompletion(ProveedorBuscar, possiblewordsProveedor);
            
            autoCompletionBindingProveedor.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletarProveedor();
            });
            
            productoToString();
            autoCompletionBindingProducto = TextFields.bindAutoCompletion(VerProducto, possiblewordsProducto);
            
            autoCompletionBindingProducto.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletarProducto();
            });
                                
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductos.fxml"));
            AgregarProductosController controller = new AgregarProductosController();
            loader.setController(controller);                      
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);
            if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    
            
            controller.devolverProductoEvent.addHandler((Object sender, agregarProductoArgs args) -> {
                productoDevuelto = args.producto;
            });
            
            deshabilitar_formulario();
                              
            TablaOrdenes.setItems(entradas);
            TablaProductos.setItems(productos);                                               
        } catch (Exception e) {
            infoController.show("La orden de entradada contiene errores : " + e);    
        }
    }
    

}
