/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaxTipo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Unidad;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Precio;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
/**
 *
 * @author herbert
 */
public class ProductosController extends Controller {
    
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmationController;
    // TABLA
    //------------------------------------------------------//
    @FXML
    private TableView<TipoProducto> tablaProductos;
    @FXML
    private TableColumn<TipoProducto, String> ColumnaTipoProducto;
    @FXML
    private TableColumn<TipoProducto, String> ColumnaCodigoProducto;
    @FXML
    private TableColumn<TipoProducto, String> ColumnaEstado;
    @FXML
    private AnchorPane tipo_producto_formulario;
   
    // DATOS
    //--------------------------------------------------------//
    @FXML
    private TextField nombre_producto;
    @FXML
    private TextField codigo_producto;
    @FXML
    private TextField largo_producto;
    @FXML
    private TextField ancho_producto;
    @FXML
    private TextField peso_producto;        
    @FXML
    private TextArea descripcion_producto;
    @FXML
    private CheckBox perecible;
    //BUSCAR
    //---------------------------------------------------------//
    @FXML
    private ComboBox<String> categoriaBuscar;     
    @FXML
     private TextField tipoProductoBuscar;        
    
    @FXML
    private AnchorPane producton_container;
    @FXML
    private ComboBox<String> estadoBuscar;
    @FXML
    private ComboBox<String> unidades_peso_producto;
    @FXML
    private ComboBox<String> unidades_medida_producto;
    @FXML
    private TextField alto_producto;
    @FXML
    private TableView<CategoriaProducto> TablaCategorias;
    @FXML
    private TableColumn<CategoriaProducto, String> TablaCatCodigoColumna;
    @FXML
    private TableColumn<CategoriaProducto, String> TablaCatNombreColumna;
    @FXML
    private TableColumn<CategoriaProducto, String> TablaCatDescripcionColumna;
    @FXML
    private ComboBox<String> categoriaDropBuscar;
    @FXML
    private Label VerStockFisico;
    @FXML
    private Label VerStockLogico;
    @FXML
    private TableView<Precio> TablaPrecios;
    @FXML
    private TableColumn<Precio, String> ColumnaPrecioValor;
    @FXML
    private TableColumn<Precio, String> ColumnaPrecioMoneda;
    @FXML
    private TableColumn<Precio, String> ColumnaPrecioFechaInicial;
    @FXML
    private TableColumn<Precio, String> ColumnaPrecioFechaFinal;
    @FXML
    private TextField VerPrecio;
    @FXML
    private ComboBox<String> VerMoneda;
    @FXML
    private DatePicker VerFechaInicial;
    @FXML
    private DatePicker VerFechaFinal;
    @FXML
    private CheckBox VerDefault;
    
     
    //CREACION - EDICION
    //---------------------------------------------------------//
    private boolean crear_nuevo = false;   
    
    private final ObservableList<TipoProducto> productos = FXCollections.observableArrayList();     
    private final ObservableList<CategoriaProducto> categorias = FXCollections.observableArrayList();         
    private final ObservableList<Precio> precios = FXCollections.observableArrayList();         
    
    private TipoProducto producto_seleccionado ;
    
    public ProductosController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        
        
        List<TipoProducto> tempProductos = TipoProducto.findAll();        
        for (TipoProducto producto : tempProductos) {
            productos.add(producto);
        }                    
        
        infoController = new InformationAlertController();
        confirmationController = new ConfirmationAlertController();
        
        crear_nuevo = false;
        producto_seleccionado = null;
    }
    
    public void inhabilitar_formulario(){
        tipo_producto_formulario.setDisable(true);
    }
    
    public void habilitar_formulario(){
        tipo_producto_formulario.setDisable(false);
    }  
    
    public void limpiar_formulario(){
        nombre_producto.clear();
        codigo_producto.clear();
        perecible.setDisable(false);
        largo_producto.clear();
        ancho_producto.clear();
        alto_producto.clear();
        peso_producto.clear();
        unidades_medida_producto.setDisable(false);
        unidades_peso_producto.setDisable(false);
        descripcion_producto.clear();
        
        categorias.clear();  
        precios.clear();
    }          
    
    @FXML
    public void buscar_tipo_producto(ActionEvent event) throws IOException{
                
        String nombre = tipoProductoBuscar.getText();
        String categoria = categoriaBuscar.getSelectionModel().getSelectedItem();
        String estado = estadoBuscar.getSelectionModel().getSelectedItem();        
        
        List<TipoProducto> tempProductos = TipoProducto.findAll();
        try{
            
            if(nombre!=null&&!nombre.isEmpty())
            {            
                tempProductos = tempProductos.stream().filter(p -> p.getString("nombre").equals(nombre)).collect(Collectors.toList());
            }
           
            if(estado!=null&&!estado.isEmpty())
            {                
                tempProductos = tempProductos.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
            }

            if(categoria!=null&&!categoria.isEmpty())
            {
                // WARNING PELIGRO NOMBRE !!!
                CategoriaProducto categoriaObj = CategoriaProducto.findFirst("nombre = ?", categoria);
                List<TipoProducto> tiposCategoria = categoriaObj.getAll(TipoProducto.class);
                tempProductos = tempProductos.stream().filter(p -> tiposCategoria.stream().anyMatch(x->Objects.equals(x.getInteger("tipo_id"), p.getInteger("tipo_id")))).collect(Collectors.toList());
            }        
            RefrescarTabla(tempProductos);
        }
        catch( Exception e){
            infoController.show("El producto contiene errores : " + e);        
        }
    } 
              
    @FXML
    private void visualizar_producto(ActionEvent event) {             
        habilitar_formulario();
        producto_seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        limpiar_formulario();
        if(producto_seleccionado == null) 
        {
            infoController.show("Producto no seleccionada");  
            return;
        }
        try {
            nombre_producto.setText(producto_seleccionado.getString("nombre"));
            codigo_producto.setText(producto_seleccionado.getString("tipo_cod"));
            perecible.setSelected(producto_seleccionado.getBoolean("pericible"));
            largo_producto.setText(producto_seleccionado.getString("longitud"));
            ancho_producto.setText(producto_seleccionado.getString("ancho"));
            alto_producto.setText(producto_seleccionado.getString("alto"));
            peso_producto.setText(producto_seleccionado.getString("peso"));
            unidades_peso_producto.getSelectionModel().select(Unidad.findFirst("unidad_id = ?", producto_seleccionado.get("unidad_peso_id")).getString("nombre"));
            unidades_medida_producto.getSelectionModel().select(Unidad.findFirst("unidad_id = ?", producto_seleccionado.get("unidad_tamano_id")).getString("nombre"));
            descripcion_producto.setText(producto_seleccionado.getString("descripcion"));   
            
            categorias.clear();
            
            List<CategoriaProducto> categoriasProducto = producto_seleccionado.getAll(CategoriaProducto.class);
            for(CategoriaProducto categoria:categoriasProducto)
            {
                categorias.add(categoria);
            }            
            
            List<Precio> preciosProducto = Precio.where("tipo_id = ?",producto_seleccionado.getId());
            for(Precio precio:preciosProducto)
            {
                precios.add(precio);
            }
            
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);        
        }        
    }

    @FXML
    private void agregar_categoria(ActionEvent event) {
        try {
            
            String precio = VerPrecio.getText();
            String monedaNombre = VerMoneda.getSelectionModel().getSelectedItem();
            String categoriaNombre = categoriaDropBuscar.getSelectionModel().getSelectedItem();
            CategoriaProducto categoria = CategoriaProducto.findFirst("nombre = ?", categoriaNombre);
            if(categoria == null)
            {
                infoController.show("Categoria no seleccionada");     
                return;
                
            }
            if(categorias.stream().anyMatch(x->x.getInteger("categoria_id").equals(categoria.getInteger("categoria_id")))) 
            {
                infoController.show("La Categoria ya se encuentra seleccionada");     
                return;
            }
            categorias.add(categoria);                        
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);     
        }
    }
    
    @FXML
    private void eliminar_categoria(ActionEvent event) {
        
        CategoriaProducto categoria = TablaCategorias.getSelectionModel().getSelectedItem();
        if(categoria == null)
        {
           infoController.show("Categoria no seleccionada");     
           return;
        }
        
        try {                     
            if(categorias.stream().anyMatch(x->x.getInteger("categoria_id").equals(categoria.getInteger("categoria_id"))))
            {
                categorias.removeIf(x->x.getInteger("categoria_id").equals(categoria.getInteger("categoria_id")));
            }            
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);              
        }                                
    }
    
    @FXML
    private void agregar_precio(ActionEvent event) {
        try {
            Float valor = Float.valueOf(VerPrecio.getText());            
            if(valor == null)
            {
                infoController.show("Debe ingresar un valor numerico para el precio");    
                return;
            }
            String monedaNombre = VerMoneda.getSelectionModel().getSelectedItem();
            Moneda moneda = Moneda.findFirst("nombre = ?", monedaNombre);
            Date fechaInicial = Date.valueOf(VerFechaInicial.getValue());
            Date fechaFinal = Date.valueOf(VerFechaFinal.getValue());
            Character esDefault = (this.VerDefault.isSelected() ? 'T' : 'F');
            
            if(moneda == null) 
            {
                infoController.show("Moneda no seleccionada");     
                return;
                
            }
            
            if(fechaFinal.before(fechaInicial))
            {
                infoController.show("La fecha final debe ser despues que la inicial");
                return;                
            }
            
            if(esDefault.equals('T') && precios.stream().anyMatch(x->x.getBoolean("es_default"))) 
            {
                infoController.show("No puede agregar mas de un precio default");
                return;                
            }
            
            Precio precio = new Precio();
            precio.setFloat("precio",valor);
            precio.set("moneda_id",moneda.getId());
            precio.setDate("fecha_inicio",fechaInicial);
            precio.setDate("fecha_fin",fechaFinal);    
            precio.set("es_default",esDefault);                        
            
            precios.add(precio);
            
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);     
        }
    }

    @FXML
    private void eliminar_precio(ActionEvent event) {
        Precio precio = TablaPrecios.getSelectionModel().getSelectedItem();
        if(precio == null) 
        {            
            infoController.show("Precio no seleccionado");     
            return;                           
        }
        
        try {
            
            if(precio.getBoolean("es_default"))
            {
                infoController.show("No puede quitar el unico precio por default");
                return;
            }
            
            precios.remove(precio);
            
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);     
        }
    }

    
 
    @Override
    public void nuevo(){
        crear_nuevo = true;
        limpiar_formulario();
        habilitar_formulario();
        codigo_producto.setEditable(true);
    }
    
    public void crear_tipo_producto(){
        try{
            
            if(precios.isEmpty())
            {
                infoController.show("Debe agregar un precio por default al Tipo de producto");
                crear_nuevo = true;
                return;
            }
            Base.openTransaction();            
            TipoProducto nuevo_tipo_producto = new TipoProducto();
            String cod = (codigo_producto.getText() + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from tiposproducto_tipo_id_seq")))) + 1));  
            if(!confirmationController.show("Se creara el tipo de producto con codigo: " + cod, "¿Desea continuar?")) return;
            float peso = Float.parseFloat(peso_producto.getText());
            float longitud = Float.parseFloat(largo_producto.getText());
            float ancho = Float.parseFloat(ancho_producto.getText());
            float alto = Float.parseFloat(alto_producto.getText());
            char perecible = (this.perecible.isSelected() ? 'T' : 'F');
            Unidad unidad_peso_producto = Unidad.first("nombre = ?", unidades_peso_producto.getSelectionModel().getSelectedItem().toString());
            Unidad unidad_medida_producto = Unidad.first("nombre = ?", unidades_medida_producto.getSelectionModel().getSelectedItem().toString());
            nuevo_tipo_producto.asignar_atributos(usuarioActual.getString("usuario_cod"), peso, nombre_producto.getText(), perecible, descripcion_producto.getText(), longitud, ancho,alto, unidad_peso_producto.getInteger("unidad_id"),unidad_medida_producto.getInteger("unidad_id"));
            nuevo_tipo_producto.set("tipo_cod",cod);
            nuevo_tipo_producto.saveIt();
            
            Stock stock = new Stock();
            stock.set("tipo_id",nuevo_tipo_producto.getId());
            stock.set("tipo_cod",nuevo_tipo_producto.get("tipo_cod"));
            stock.set("stock_real",0);
            stock.set("stock_logico",0);
            stock.saveIt();
                                        
            setCategoriasProducto(nuevo_tipo_producto);
            setPreciosProducto(nuevo_tipo_producto);
            Base.commitTransaction();       
               
            infoController.show("El producto ha sido creado satisfactoriamente"); 
            limpiar_formulario();
            crear_nuevo = false;
            limpiar_formulario();
            inhabilitar_formulario();            
        }
        catch(Exception e){
            infoController.show("El producto contiene errores : " + e);
            crear_nuevo = true;
            Base.rollbackTransaction();
        }finally{
            codigo_producto.setEditable(true);                        
        }            
    }    
    
    public void editar_producto(TipoProducto producto){
        try{
            Base.openTransaction();   
            String cod = codigo_producto.getText() + producto.getString("tipo_id");
            if(!confirmationController.show("Se editara el tipo de producto con codigo: " + codigo_producto.getText(), "¿Desea continuar?")) return;
            float peso = Float.parseFloat(peso_producto.getText());
            float longitud = Float.parseFloat(largo_producto.getText());
            float ancho = Float.parseFloat(ancho_producto.getText());
            float alto = Float.parseFloat(alto_producto.getText());
            char perecible = (this.perecible.isSelected() ? 'T' : 'F');
            Unidad unidad_peso_producto = Unidad.first("nombre = ?", unidades_peso_producto.getSelectionModel().getSelectedItem().toString());
            Unidad unidad_medida_producto = Unidad.first("nombre = ?", unidades_medida_producto.getSelectionModel().getSelectedItem().toString());
            producto.asignar_atributos(usuarioActual.getString("usuario_cod"),peso, nombre_producto.getText(), perecible, descripcion_producto.getText(), longitud, ancho,alto, unidad_peso_producto.getInteger("unidad_id"),unidad_medida_producto.getInteger("unidad_id"));
                                          
            setCategoriasProducto(producto);
            setPreciosProducto(producto);
            
            producto.saveIt();   
            Base.commitTransaction();   
                      
            infoController.show("El producto ha sido editado satisfactoriamente"); 
            limpiar_formulario();
        }
        catch(Exception e){
            infoController.show("El producto contiene errores : " + e);        
            Base.rollbackTransaction();
        }
    }
    
    private void setCategoriasProducto(TipoProducto producto) throws Exception
    {       
        List<CategoriaProducto> categoriasProductoGuardadas = producto.getAll(CategoriaProducto.class);

        List<CategoriaProducto> categoriasAdd = categorias.stream().filter(x -> categoriasProductoGuardadas.stream().noneMatch(y -> y.getInteger("categoria_id").equals(x.getInteger("categoria_id")))).collect(Collectors.toList());
        List<CategoriaProducto> categoriasDelete = categoriasProductoGuardadas.stream().filter(x -> categorias.stream().noneMatch(y -> y.getInteger("categoria_id").equals(x.getInteger("categoria_id")))).collect(Collectors.toList());

        for(CategoriaProducto categoria: categoriasAdd)
        {
            CategoriaxTipo.createIt("tipo_id",producto.getId(),"tipo_cod",producto.get("tipo_cod"),"categoria_id",categoria.getId(),"categoria_code",categoria.get("categoria_code"));
        }

        for(CategoriaProducto categoria: categoriasDelete)
        {
            CategoriaxTipo.delete("tipo_id = ? AND categoria_id = ?", producto.getId(),categoria.getId());
        }                                                      
    }
    
    public void setPreciosProducto(TipoProducto producto) throws Exception
    {
       
        for(Precio precio:precios)
        {
            if(precio.isNew())
            {
                precio.set("tipo_id",producto.getId());
                precio.set("tipo_cod",producto.get("tipo_cod"));
            }
            precio.saveIt();
        }           

        List<Precio> preciosGuardados = Precio.where("tipo_id = ?", producto.getId());
        if(preciosGuardados == null) return;
        List<Precio> preciosDelete = preciosGuardados.stream().filter(x -> precios.stream().noneMatch(y -> !y.isNew()&&y.getInteger("precio_id").equals(x.getInteger("precio_id")))).collect(Collectors.toList());
        if(preciosDelete == null)return;
        
        for(Precio precio:preciosDelete)
        {
            Precio.delete("precio_id = ?",precio.getId());
        }
                  
    }
    
    public void llenar_combobox_unidades(){
        try{
            List<String> unidades_combo_box = new ArrayList<String>();
            LazyList<Unidad> lista_unidades = Unidad.findAll();
            for(Unidad unidad : lista_unidades){
                unidades_combo_box.add(unidad.get("nombre").toString());
            }            
            unidades_medida_producto.getItems().addAll(unidades_combo_box);
            unidades_peso_producto.getItems().addAll(unidades_combo_box);

        }catch(Exception e){
            infoController.show("El producto contiene errores : " + e);      
        }
    }
    
    @Override
    public void guardar(){
        if (crear_nuevo){
            crear_tipo_producto();
        }
        else {
            if(producto_seleccionado==null) 
            {
                infoController.show("No ha seleccionado un tipo de producto");            
                return;
            }
            editar_producto(producto_seleccionado);
        }    
        RefrescarTabla(TipoProducto.findAll());
    }
    
    private void RefrescarTabla(List<TipoProducto> productoRefresh)
    {        
        try {
            productos.removeAll(productos);                 
            if(productoRefresh == null) return;
            for (TipoProducto producto : productoRefresh) {
                productos.add(producto);
            }               
            tablaProductos.getColumns().get(0).setVisible(false);
            tablaProductos.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);      
        }                                  
    }
    
   @Override
   public Menu.MENU getMenu()
   {
       return Menu.MENU.Productos;
   }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try {
            inhabilitar_formulario();
            ColumnaCodigoProducto.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
            ColumnaTipoProducto.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
            ColumnaEstado.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));
            
            TablaCatCodigoColumna.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("categoria_code")));
            TablaCatNombreColumna.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
            TablaCatDescripcionColumna.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));
            
            ColumnaPrecioValor.setCellValueFactory((TableColumn.CellDataFeatures<Precio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("precio")));
            ColumnaPrecioMoneda.setCellValueFactory((TableColumn.CellDataFeatures<Precio, String> p) -> new ReadOnlyObjectWrapper(Moneda.findById((p.getValue().get("moneda_id"))).getString("nombre")));
            ColumnaPrecioFechaInicial.setCellValueFactory((TableColumn.CellDataFeatures<Precio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_inicio")));
            ColumnaPrecioFechaFinal.setCellValueFactory((TableColumn.CellDataFeatures<Precio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_fin")));
            // ToDo hace ver el default            

            ObservableList<String> estados = FXCollections.observableArrayList();                           
            ObservableList<String> monedas = FXCollections.observableArrayList();            
            ObservableList<String> categoriasDrop = FXCollections.observableArrayList();                           
            ObservableList<String> categoriasNoPad = FXCollections.observableArrayList();     

            estados.add("");            
            estados.addAll(Arrays.asList(TipoProducto.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));   

            List<String> categoriasNombres = CategoriaProducto.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList());            
            monedas.addAll(Moneda.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList()));
            
            categoriasNoPad.addAll(categoriasNombres);
            categoriaDropBuscar.setItems(categoriasNoPad);
            categoriasNombres.add(0, "");
            categoriasDrop.addAll(categoriasNombres);

            estadoBuscar.setItems(estados);
            VerMoneda.setItems(monedas);
            categoriaBuscar.setItems(categoriasDrop);

            llenar_combobox_unidades();  
            
            tablaProductos.setItems(productos);
            TablaCategorias.setItems(categorias);
            TablaPrecios.setItems(precios);
            //codigo_producto.setEditable(false);
            
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);      
        }                   
    }  
}
 

