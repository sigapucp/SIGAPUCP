/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.tileArgs;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author herbert
 */
public class GridTile extends StackPane {
    private double tile_width;
    private double tile_height;
    private int x_cord;
    private int y_cord;
    Rectangle border;
    private Event<tileArgs> releaseTileEvent;
    private Event<tileArgs> activeTileEvent;
    
    public GridTile(double width, double height,  int x, int y) {
        tile_width = width;
        tile_height = height;
        x_cord = x;
        y_cord = y;
        releaseTileEvent = new Event<>();
        activeTileEvent = new Event<>();
//        Text text = new Text();
//        
//        text.setFont(Font.font(14));
//        text.setText(String.format("%d/%d", x, y));

        border = new Rectangle(width, height);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        setAlignment(Pos.CENTER);
//        getChildren().addAll(border, text);
        setMouseEvents();
    }
    
    private void setMouseEvents() {
        // Se ejecuta cuando solo en el primer tile seleccionado
        setOnMousePressed((event) -> {
            activeTile();
        });
        
        // Se ejecuta cuando solo en el primer tile seleccionado
        setOnDragDetected((event) -> {
            startFullDrag();
        });
        
        // Se ejecuta cuando entra a un nuevo tile
        setOnMouseDragEntered((event) -> {
            activeTile();
        });
        
        // Siempre se ejecuta desde el primer tile seleccionado
        setOnMouseReleased((event) -> {
            tileArgs args = new tileArgs();
            args.setX_cord(x_cord);
            args.setY_cord(y_cord);
           
            releaseTileEvent.fire(this, args);
        });
    }
    
    public Event<tileArgs> getReleaseEvent() {
        return releaseTileEvent;
    }
    
    public Event<tileArgs> getActiveTileEvent() {
        return activeTileEvent;
    }
    
    public void activeTile() {
        tileArgs args = new tileArgs();
        args.setX_cord(x_cord);
        args.setY_cord(y_cord);
        
        activeTileEvent.fire(this, args);
        border.setFill(Color.RED);
    }
    
    public void clearTile() {
        border.setFill(null);
    }
    
    public int getXCord() {
        return x_cord;
    }
    
    public int getYCord() {
        return y_cord;
    }
    
    
    public String getTileId() {
        return String.format("%d/%d", x_cord, y_cord);
    }
}
