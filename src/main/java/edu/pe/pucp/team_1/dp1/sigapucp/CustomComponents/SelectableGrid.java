/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.layout.AnchorPane;

public class SelectableGrid extends AnchorPane  {
    private int num_rows;
    private int num_columns;
    private int grid_width;
    private int grid_heigth;
    private List<GridTile> tiles = new ArrayList<>();
    private List<Integer> active_tiles = new ArrayList<>();
    private List<Integer> saved_tiles = new ArrayList<>();
    
    public SelectableGrid(int rows, int columns, int width, int height) {
        num_rows = rows;
        num_columns = columns;
        grid_width = width;
        grid_heigth = height;
        
        initializeTiles();
    }
    
    private void initializeTiles() {
        double aspect_ratio_width = grid_width/num_rows;
        double aspect_ratio_heigth = grid_heigth/num_columns;
        
        for(int i = 0; i < num_columns; i++)
            for(int j = 0; j < num_rows; j++) {
                GridTile tile = new GridTile(aspect_ratio_width, aspect_ratio_heigth, i, j);
                tile.setTranslateX(j * aspect_ratio_width);
                tile.setTranslateY(i * aspect_ratio_heigth);
                tile.getActiveTileEvent().addHandler((sender, args) -> {
                    System.out.println(num_columns*args.getY_cord() + args.getX_cord() + 1);
                });
                tile.getReleaseEvent().addHandler((sender, args) -> {
                    if (!checkDirectionOfActiveTiles(args.getX_cord(), args.getY_cord())) clearActiveTiles();
                });
                
                tiles.add(tile);
                getChildren().add(tile);
            }
    }
    
    private Boolean checkDirectionOfActiveTiles(int x_init, int y_init) {
//        AtomicBoolean conditionX = new AtomicBoolean(true);
//        AtomicBoolean conditionY = new AtomicBoolean(true);
//        
//        tiles.stream().filter((tile) -> (tile.isActive())).forEach((tile) -> {
//            conditionX.set(conditionX.get() && x_init == tile.getXCord());
//            conditionY.set(conditionY.get() && y_init == tile.getYCord());
//        });
//        
//        
//        
//        return conditionX.get() || conditionY.get();
        return false;
    }
    
    private void clearActiveTiles() {
        for(int i = 0; i < active_tiles.length; i++)
            tiles.get(i).clearTile();
    }
    
    public void setNumRow(int rows) {
        num_rows = rows;
    }
    
    public void setNumColumns(int columns) {
        num_columns = columns;
    }
}
