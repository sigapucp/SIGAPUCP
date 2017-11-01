/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.layout.AnchorPane;

public class SelectableGrid extends AnchorPane  {
    private int num_rows;
    private int num_columns;
    private int grid_width;
    private int grid_heigth;
    private int grid_real_width;
    private int grid_real_heigth;
//    private HashMap<Integer, List<GridTile>> tiles_aux;
    private List<GridTile> tiles;
    private Behavior behavior;
    
    // Longitud de area nos define el tamano de un cuadrado dentro del dibujo para un almacen
    public SelectableGrid(int rows, int columns, int width, int heigth, Behavior external_behavior) {
        grid_width = width > 400 ? 400 : width;
        grid_heigth = heigth > 400 ? 400 : heigth;
        num_rows = rows*(width/400);
        num_columns = columns*(heigth/400);
        grid_real_width = width;
        grid_real_heigth = heigth;
        tiles = new ArrayList<>();
        behavior = external_behavior;
        initializeTiles();
    }
    
    private void initializeTiles() {
        int aspect_ratio_width = grid_width/num_rows;
        int aspect_ratio_heigth = grid_heigth/num_columns;
        
        for(int i = 0; i < num_columns; i++)
            for(int j = 0; j < num_rows; j++) {
                GridTile tile = new GridTile(aspect_ratio_width, aspect_ratio_heigth, i, j);
                tile.setTranslateX(j * aspect_ratio_width);
                tile.setTranslateY(i * aspect_ratio_heigth);
                tile.getActiveTileEvent().addHandler((sender, args) -> {
                    int index = num_rows*args.getX_cord() + args.getY_cord();
                    if (behavior.isTileSavedOrActive(index)) behavior.addSelectedTile(index);
                });
                tile.getReleaseEvent().addHandler((sender, args) -> {
                    if (behavior.checkDrawRules(tiles, args)) behavior.saveActiveTiles(tiles);
                    else behavior.clearActiveTiles(tiles); // System.out.println("Borrando~");
                });
                
                tiles.add(tile);
                getChildren().add(tile);
            }
    }
    
    public void clearCurrentActiveTiles() {
        behavior.clearCurrentActiveTiles(tiles);
    }
    
    public void clearAndSaveTempTiles() {
        behavior.clearAndSaveTempTiles(tiles);
    }
    
    public void setNumRow(int rows) {
        num_rows = rows;
    }
    
    public void setNumColumns(int columns) {
        num_columns = columns;
    }

    /**
     * @return the grid_width
     */
    public int getGrid_width() {
        return grid_real_width;
    }

    /**
     * @return the grid_heigth
     */
    public int getGrid_heigth() {
        return grid_real_heigth;
    }
}
