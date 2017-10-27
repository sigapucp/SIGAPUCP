/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;

public class SelectableGrid extends AnchorPane  {
    private int num_rows;
    private int num_columns;
    private int grid_width;
    private int grid_heigth;
    private List<GridTile> tiles = new ArrayList<GridTile>();
    
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
                GridTile tile = new GridTile(aspect_ratio_width, aspect_ratio_heigth, String.format("f_%d_c_%d", i, j));
                tile.setTranslateX(j * aspect_ratio_width);
                tile.setTranslateY(i * aspect_ratio_heigth);
                
                tiles.add(tile);
                getChildren().add(tile);
            }
    }
    
    public void setNumRow(int rows) {
        num_rows = rows;
    }
    
    public void setNumColumns(int columns) {
        num_columns = columns;
    }
}
