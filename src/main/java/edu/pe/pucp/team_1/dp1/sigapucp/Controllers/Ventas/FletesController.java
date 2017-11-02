/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import com.sun.webkit.BackForwardList;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarCategoriasController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Flete;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarCategoriaArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class FletesController extends Controller {

    @FXML
    private TextField busqCodigoFlete;
    @FXML
    private ComboBox<String> busqTipoFlete;
    @FXML
    private Button botonTipo2;
    @FXML
    private Button botonCategoria3;
    @FXML
    private TableView<Flete> TablaFletes;
    @FXML
    private TableColumn<Flete, String> ColumnaCodigo;
    @FXML
    private TableColumn<Flete, String> ColumnaTipo;
    @FXML
    private TableColumn<Flete, String> ColumnaProducto;
    @FXML
    private TextField VerCodigoFlete;
    @FXML
    private DatePicker VerFechaInicial;
    @FXML
    private DatePicker VerFechaFinal;
    @FXML
    private ComboBox<String> VerTipo;
    @FXML
    private ComboBox<String> VerMoneda;
    @FXML
    private TextField VerValor;
    @FXML
    private TextField busqCodigoProdCat;  
    @FXML
    private Label NombreTipo;
    @FXML
    private Label AbrvTipo;
    @FXML
    private TextField VerProdCat;
    @FXML
    private AnchorPane anchor_formulario;
 
    private Stage modal_stage_productos = new Stage();
    private Stage modal_stage_categorias = new Stage();

    /**
     * Initializes the controller class.
     */
    
    private InformationAlertController infoController;    
    private final ObservableList<Flete> fletes = FXCollections.observableArrayList();
    private TipoProducto productoDevuelto;
    private CategoriaProducto categoriaDevuelta;
    private Boolean es_categoria = false;
    private Boolean crear_nuevo = false;
    private Flete fleteSeleccionado = null;

    
    
    public FletesController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        infoController = new InformationAlertController();                
    }
    
    private void llenar_tablas() throws Exception
    {
        List<Flete> tempFletes = Flete.findAll();        
        fletes.addAll(tempFletes);
        
        ColumnaCodigo.setCellValueFactory((CellDataFeatures<Flete, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("flete_code")));   
        ColumnaTipo.setCellValueFactory((CellDataFeatures<Flete, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("tipo")));           
        ColumnaProducto.setCellValueFactory((CellDataFeatures<Flete, String> p) -> new ReadOnlyObjectWrapper(
                (p.getValue().getString("es_categoria").equals("S")) ? p.getValue().getString("categoria_cod") : p.getValue().getString("tipo_cod")));   
        
        TablaFletes.setItems(fletes);
    }
    
    private void llenar_combobox() throws Exception
    {                
        ObservableList<String> monedas = FXCollections.observableArrayList();            
        monedas.addAll(Moneda.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList()));
        VerMoneda.setItems(monedas);

        ObservableList<String> estados = FXCollections.observableArrayList();       
        ObservableList<String> estadosNoPad = FXCollections.observableArrayList();       
        List<String> tempEstado = Arrays.asList(Flete.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList());
        
        estados.add("");        
        estados.addAll(tempEstado);   
        estadosNoPad.addAll(tempEstado);
        busqTipoFlete.setItems(estados);   
        VerTipo.setItems(estadosNoPad);
        
    }
    
    private void setAgregarProductos() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductos.fxml"));
        AgregarProductosController controller = new AgregarProductosController();
        loader.setController(controller);                      
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage_productos.setScene(modal_content_scene);
        if(modal_stage_productos.getModality() != Modality.APPLICATION_MODAL) modal_stage_productos.initModality(Modality.APPLICATION_MODAL);    

        controller.devolverProductoEvent.addHandler((Object sender, agregarProductoArgs args) -> {
            productoDevuelto = args.producto;
        });             
    }
    
     private void setAgregarCategoria() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarCategorias.fxml"));
        AgregarCategoriasController controller = new AgregarCategoriasController();
        loader.setController(controller);                      
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage_categorias.setScene(modal_content_scene);
        if(modal_stage_categorias.getModality() != Modality.APPLICATION_MODAL) modal_stage_categorias.initModality(Modality.APPLICATION_MODAL);    

        controller.devolverCategoriaEvent.addHandler((Object sender, agregarCategoriaArgs args) -> {
            categoriaDevuelta = args.categoria;
        });             
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            llenar_tablas();
            llenar_combobox();
            setAgregarProductos();     
            setAgregarCategoria();
            
            VerTipo.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if(newValue == null) return;
                    if(newValue.equals(Flete.TIPO.PORCENTAJE))
                    {
                        NombreTipo.setText("Procentaje");
                    }
                    
                    if(newValue.equals(Flete.TIPO.PORCENTAJE.name()))
                    {
                        NombreTipo.setText("Procentaje sobre Precio Base");
                        AbrvTipo.setText("  %  ");
                    }
                    
                    if(newValue.equals(Flete.TIPO.VOLUMEN.name()))
                    {
                        NombreTipo.setText("Metro Cubico por Kilometro");
                        AbrvTipo.setText("M^3/Km");
                    }
                    
                    if(newValue.equals(Flete.TIPO.PESO.name()))
                    {
                        NombreTipo.setText("Kilogramos por Kilometro");
                        AbrvTipo.setText("Kg/Km");
                    }
                }            
            });
            inhabilitar_formulario();
        } catch (Exception e) {
            infoController.show("No se han podido cargar los fletes: " + e.getMessage());
        }        
    }    
    
     private void RefrescarTabla(List<Flete> fletesRefresh)
    {        
        try {
            fletes.removeAll(fletes);                 
            if(fletesRefresh == null) return;
            for (Flete flete : fletesRefresh) {
                fletes.add(flete);
            }               
            TablaFletes.getColumns().get(0).setVisible(false);
            TablaFletes.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("El Flete contiene errores : " + e);      
        }                                  
    }

    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Fletes;
    }

    @FXML
    private void buscarFlete(ActionEvent event) {
        String codigo = busqCodigoFlete.getText();        
        String tipo = busqTipoFlete.getSelectionModel().getSelectedItem();        
        String codigoProd = busqCodigoProdCat.getText();
        
        List<Flete> tempFletes = Flete.findAll();
        try{
            
            if(codigo!=null&&!codigo.isEmpty())
            {            
                tempFletes = tempFletes.stream().filter(p -> p.getString("flete_code").equals(codigo)).collect(Collectors.toList());
            }
           
            if(tipo!=null&&!tipo.isEmpty())
            {                
                tempFletes = tempFletes.stream().filter(p -> p.getString("tipo").equals(tipo)).collect(Collectors.toList());
            }

            if(codigoProd!=null&&!codigoProd.isEmpty())
            {
                
                tempFletes = tempFletes.stream().filter(p -> 
                        (p.getString("es_categoria").equals("S")) ? p.getString("categoria_cod").equals(codigoProd) : 
                                p.getString("tipo_cod").equals(codigoProd)).collect(Collectors.toList());
            }        
            RefrescarTabla(tempFletes);
        }
        catch( Exception e){
            infoController.show("El producto contiene errores : " + e);        
        }
    }

    @FXML
    private void visualizarFlete(ActionEvent event) {
        
        fleteSeleccionado = TablaFletes.getSelectionModel().getSelectedItem();
        if(fleteSeleccionado == null)
        {
            infoController.show("No ha selecionado ningun flete");
            return;
        }
        VerCodigoFlete.setDisable(true);
        VerCodigoFlete.setText(fleteSeleccionado.getString("flete_code"));
        VerFechaInicial.setValue(fleteSeleccionado.getDate("fecha_inicio").toLocalDate());
        VerFechaFinal.setValue(fleteSeleccionado.getDate("fecha_fin").toLocalDate());
        VerTipo.getSelectionModel().select(fleteSeleccionado.getString("tipo"));
        VerMoneda.getSelectionModel().select(Moneda.findById(fleteSeleccionado.get("moneda_id")).getString("nombre"));
        VerValor.setText(fleteSeleccionado.getString("valor"));
        
                
        
        es_categoria = fleteSeleccionado.getString("es_categoria").equals("S");
        if(es_categoria)
        {
            categoriaDevuelta = CategoriaProducto.findById(fleteSeleccionado.get("categoria_id"));
            productoDevuelto = null;
            VerProdCat.setText(categoriaDevuelta.getString("nombre"));
        }else
        {
            productoDevuelto = TipoProducto.findById(fleteSeleccionado.get("tipo_id"));
            categoriaDevuelta = null;
            VerProdCat.setText(productoDevuelto.getString("nombre"));
        }
        habilitar_formulario();
    }

    @FXML
    private void agregarProducto(ActionEvent event) {
        modal_stage_productos.showAndWait();
        es_categoria = false;
        if(productoDevuelto == null) return;
        VerProdCat.setText(productoDevuelto.getString("nombre"));
    }

    @FXML
    private void agregarCategoria(ActionEvent event) {
        modal_stage_categorias.showAndWait();
        es_categoria = true;
        if(categoriaDevuelta == null) return;
        VerProdCat.setText(categoriaDevuelta.getString("nombre"));
    }
    
      public void limpiar_formulario(){
        VerCodigoFlete.clear();
        VerProdCat.clear();
        VerValor.clear();
        
        VerTipo.getSelectionModel().clearSelection();
        es_categoria = false;                
    }          
      
    public void inhabilitar_formulario(){
        anchor_formulario.setDisable(true);
    }
    
     public void habilitar_formulario(){
        anchor_formulario.setDisable(false);
    }
    
    @Override
    public void nuevo()
    {
        crear_nuevo = true;
        limpiar_formulario();
        habilitar_formulario();
        VerCodigoFlete.setDisable(false);
    }
    
    @Override
    public void guardar()
    {
        if (crear_nuevo){
            crear_flete();
        }
        else {
            if(fleteSeleccionado==null) 
            {
                VerCodigoFlete.setDisable(true);
                infoController.show("No ha seleccionado un Flete");            
                return;
            }
            editar_flete(fleteSeleccionado);
        }    
        RefrescarTabla(Flete.findAll());        
    }
    
    private void crear_flete()
    {
        try {           
             Base.openTransaction();
            String tipo = VerTipo.getSelectionModel().getSelectedItem();
            Date fechaInicial = Date.valueOf(VerFechaInicial.getValue());
            Date fechaFinal = Date.valueOf(VerFechaFinal.getValue());
            Integer moneda_id = Moneda.findFirst("nombre = ?", VerMoneda.getSelectionModel().getSelectedItem()).getInteger("moneda_id");            
            Double valor = Double.valueOf(VerValor.getText());
            
            Flete flete = new Flete();
            String cod = VerCodigoFlete.getText() + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from fletes_flete_id_seq")))) + 1);  
            asignar_valores(flete,fechaInicial, fechaFinal, tipo,Flete.ESTADO.ACTIVO.name(),valor,moneda_id);
            flete.set("flete_code",cod);
            
            flete.saveIt();      
            Base.commitTransaction();
            infoController.show("El Flete con el cod " + cod + " fue creado exitosamente");
        } catch (Exception e) {
            infoController.show("No se pudo crear el Flete: " + e.getMessage());
            Base.rollbackTransaction();
        }
        finally
        {
            crear_nuevo = false;
            inhabilitar_formulario();
            limpiar_formulario();
            fleteSeleccionado = null;
            VerCodigoFlete.setDisable(true);
        }
        
    }
    
    private void editar_flete(Flete flete)
    {
        try {
            Base.openTransaction();
            String tipo = VerTipo.getSelectionModel().getSelectedItem();
            Date fechaInicial = Date.valueOf(VerFechaInicial.getValue());
            Date fechaFinal = Date.valueOf(VerFechaFinal.getValue());
            Integer moneda_id = Moneda.findFirst("nombre = ?", VerMoneda.getSelectionModel().getSelectedItem()).getInteger("moneda_id");            
            Double valor = Double.valueOf(VerValor.getText());
                     
            String cod = fleteSeleccionado.getString("flete_cod");
            asignar_valores(fleteSeleccionado,fechaInicial, fechaFinal, tipo,Flete.ESTADO.ACTIVO.name(),valor,moneda_id);                        
            flete.saveIt();            
            Base.commitTransaction();
            infoController.show("El Flete con el cod " + cod + " fue editado exitosamente");            
        } catch (Exception e) {
            infoController.show("No se pudo editar el Flete: " + e.getMessage());
            Base.rollbackTransaction();
        }finally
        {
            crear_nuevo = false;
            inhabilitar_formulario();
            limpiar_formulario();
            fleteSeleccionado = null;
            VerCodigoFlete.setDisable(true);
        }        
    }
    
    private void asignar_valores(Flete flete,Date fecha_inicio,Date fecha_fin,String tipo,String estado,Double valor,Integer monedaid)
    {
        flete.set("prioridad",1);
        flete.setDate("fecha_inicio",fecha_inicio);
        flete.setDate("fecha_fin",fecha_fin);
        flete.set("es_categoria",(es_categoria) ? "S" : "N");
        flete.set("tipo",tipo);
        flete.set("estado",estado);
        flete.set("valor",valor);
        flete.set("moneda_id",monedaid);
        
        if(es_categoria)
        {
            flete.set("categoria_id",categoriaDevuelta.getId());
            flete.set("categoria_code",categoriaDevuelta.get("categoria_code"));
        }else
        {
            flete.set("tipo_cod",productoDevuelto.get("tipo_cod"));
            flete.set("tipo_id",productoDevuelto.getId());
        }                
    }       
}
