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
    private boolean active;
    private Rectangle border; // se agrego private
    private Event<tileArgs> releaseTileEvent;
    private Event<tileArgs> activeTileEvent;
    private Event<tileArgs> pressedTileEvent;
    private Event<tileArgs> dragTileEvent;
    
    public GridTile(double width, double height,  int x, int y) {
        tile_width = width;
        tile_height = height;
        x_cord = x;
        y_cord = y;
        active = false;
        releaseTileEvent = new Event<>();
        activeTileEvent = new Event<>();
        pressedTileEvent = new Event<>();
        dragTileEvent = new Event<>(); 
        Text text = new Text();
        
        text.setFont(Font.font(14));
        text.setText(String.format("%d/%d", x, y));

        border = new Rectangle(width, height);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        setAlignment(Pos.CENTER);
        getChildren().addAll(border);
        setMouseEvents();
    }
    
    public void setFill(Color color)
    {
        border.setFill(color);
    }
    
    private void setMouseEvents() {
        // Se ejecuta cuando solo en el primer tile seleccionado
        setOnMousePressed((event) -> {
            activeTile(true);
            tileArgs args = new tileArgs();
            args.setX_cord(x_cord);
            args.setY_cord(y_cord);
           
            pressedTileEvent.fire(this, args);
        });
        
        // Se ejecuta cuando solo en el primer tile seleccionado
        setOnDragDetected((event) -> {
            startFullDrag();
            
        });
        
        // Se ejecuta cuando entra a un nuevo tile
        setOnMouseDragEntered((event) -> {
            activeTile(true);
             tileArgs args = new tileArgs();
            args.setX_cord(x_cord);
            args.setY_cord(y_cord);
           
            dragTileEvent.fire(this, args);            
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
    
    public Event<tileArgs> getPressedEvent() {
        return pressedTileEvent;
    }
    
     public Event<tileArgs> getDragEvent() {
        return dragTileEvent;
    }
    
    public void activeTile(boolean fromEvent) {
        if(fromEvent) {
            tileArgs args = new tileArgs();
            args.setX_cord(x_cord);
            args.setY_cord(y_cord);

            activeTileEvent.fire(this, args);
        }      
        border.setFill(Color.RED);
        active = true;
    }
    
     public void activeTileColor(boolean fromEvent,Color color) {
        if(fromEvent) {
            tileArgs args = new tileArgs();
            args.setX_cord(x_cord);
            args.setY_cord(y_cord);

            activeTileEvent.fire(this, args);
        }        
        border.setFill(color);
        active = true;
    }
    
    
    public void clearTile() {       
        border.setFill(null);
        active = false;
    }
    
    
    public Boolean isActive()
    {
        return active;    
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
    
    public void paintTile(Color color)
    {
        clearTile();
        border.setFill(color);
    }
}
