/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createRackArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.layout.AnchorPane;

public class SelectableGrid extends AnchorPane  {
    private int num_rows;
    private int num_columns;
    private int grid_width;
    private int grid_heigth;
    private int grid_real_width;
    private int grid_real_heigth;
    private List<GridTile> tiles;
    private List<Integer> active_tiles;
    private List<Integer> temp_tiles;
    private List<Integer> saved_tiles;
    private Boolean directionX;
    private Boolean directionY;
    private IEvent<createRackArgs> createRackEvent;
    
    public SelectableGrid(int rows, int columns, int width, int heigth) {
        grid_width = width > 400 ? 400 : width;
        grid_heigth = heigth > 400 ? 400 : heigth;
        num_rows = rows*(width/400);
        num_columns = columns*(heigth/400);
        grid_real_width = width;
        grid_real_heigth = heigth;
        tiles = new ArrayList<>();
        active_tiles = new ArrayList<>();
        temp_tiles = new ArrayList<>();
        saved_tiles = new ArrayList<>();
        directionX = false;
        directionY = false;
        createRackEvent = new Event<>();
        
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
                    if (!active_tiles.contains(index) && !saved_tiles.contains(index)) active_tiles.add(index);
                });
                tile.getReleaseEvent().addHandler((sender, args) -> {
                    if (checkDirectionOfActiveTiles(args.getX_cord(), args.getY_cord())) saveActiveTiles();
                    else clearActiveTiles();
                });
                
                tiles.add(tile);
                getChildren().add(tile);
            }
    }
    
    private Boolean checkDirectionOfActiveTiles(int x_init, int y_init) {
        AtomicBoolean conditionX = new AtomicBoolean(true);
        AtomicBoolean conditionY = new AtomicBoolean(true);
        
        active_tiles.forEach((index) -> {
            GridTile tile = tiles.get(index);
            conditionX.set(conditionX.get() && x_init == tile.getXCord());
            conditionY.set(conditionY.get() && y_init == tile.getYCord());
        });
        
        directionX = conditionX.get();
        directionY = conditionY.get();
        
        return conditionX.get() || conditionY.get();
    }
    
    private void saveActiveTiles() {
        createRackArgs args = new createRackArgs();

        temp_tiles.addAll(active_tiles);
        
        int x_ancla1 = tiles.get(active_tiles.get(0)).getXCord();
        int x_ancla2 = tiles.get(active_tiles.get(active_tiles.size() - 1)).getXCord();
        int y_ancla1 = tiles.get(active_tiles.get(0)).getYCord();
        int y_ancla2 = tiles.get(active_tiles.get(active_tiles.size() - 1)).getYCord();
        
        args.setX_ancla1(x_ancla1);
        args.setY_ancla1(y_ancla1);
        args.setX_ancla2(x_ancla2);
        args.setY_ancla2(y_ancla2);
        double largo = directionX ? (y_ancla2 - y_ancla1) : (directionY ? (x_ancla2 - x_ancla1) : 0);
        System.out.println(directionX);
        System.out.println(directionY);
        System.out.println(largo);
        args.setLongitud(largo);
        args.setIs_uniforme(true);
        
        active_tiles.clear();
        createRackEvent.fire(this, args);
    }
    
    private void clearActiveTiles() {
        active_tiles.forEach((i) -> {
            tiles.get(i).clearTile();
        });
        
        active_tiles.clear();
    }
    
    public void clearCurrentActiveTiles() {
        temp_tiles.forEach((i) -> {
            tiles.get(i).clearTile();
        });
        
        temp_tiles.clear();
    }
    
    public IEvent<createRackArgs> getCreateRackEvent() {
        return createRackEvent;
    }
    
    public void clearAndSaveTempTiles() {
        saved_tiles.addAll(temp_tiles);
        temp_tiles.clear();
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
