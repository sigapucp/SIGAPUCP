/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author herbert
 */
public class GridTile extends StackPane {
    private double tile_width;
    private double tile_height;
    private String id_child;
    Rectangle border;
    private Boolean active_tile;
    
    public GridTile(double width, double height, String id) {
        tile_width = width;
        tile_height = height;
        id_child = id;
        active_tile = false;
        
        border = new Rectangle(width, height);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(border);
        
        setOnMousePressed((event) -> {
            border.setFill(Color.RED);
            active_tile = true;
        });
        
        setOnDragDetected((event) -> {
            System.out.print(id_child);
            System.out.println(" on Mouse drag detected");
            startFullDrag();
        });
        
        setOnMouseDragEntered((event) -> {
            System.out.print(id_child);
            System.out.println(" on Mouse drag entered");
            border.setFill(Color.RED);
            active_tile = true;
        });
        
    }
}
