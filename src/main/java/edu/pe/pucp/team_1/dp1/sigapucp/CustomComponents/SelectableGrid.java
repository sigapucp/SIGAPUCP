/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.LazyList;

public class SelectableGrid extends AnchorPane  {
    private int num_rows;
    private int num_columns;
    private int tile_size;
    private int grid_width;
    private int grid_heigth;
    private int grid_real_width;
    private int grid_real_heigth;
    private TreeMap<Integer, List<GridTile>> tiles;
    private Behavior behavior;
    
    // Longitud de area nos define el tamano de un cuadrado dentro del dibujo para un almacen
    public SelectableGrid(int width, int heigth, int grid_size, Behavior external_behavior) {
        grid_width = width > 400 ? 400 : width;
        grid_heigth = heigth > 400 ? 400 : heigth;
        tile_size = grid_size;
        num_rows = (int) Math.ceil(width/grid_size);
        num_columns = (int) Math.ceil(heigth/grid_size);
        tiles = new TreeMap<>();
        grid_real_width = width;
        grid_real_heigth = heigth;
        behavior = external_behavior;
        initializeTiles();
    }
    
    private void initializeTiles() {
        int aspect_ratio_width = grid_width/num_rows;
        int aspect_ratio_heigth = grid_heigth/num_columns;
        
        for(int i = 0; i < num_rows; i++)
            for(int j = 0; j < num_columns; j++) {
                GridTile tile = new GridTile(aspect_ratio_width, aspect_ratio_heigth, j, i);
                tile.setTranslateX(i * aspect_ratio_width);
                tile.setTranslateY(j * aspect_ratio_heigth);
                tile.getActiveTileEvent().addHandler((sender, args) -> {
                    if (behavior.isNotTileSavedOrActive(args.getY_cord(), args.getX_cord())) behavior.addSelectedTile(args.getY_cord(), args.getX_cord());
                });
                tile.getReleaseEvent().addHandler((sender, args) -> {
                    if (behavior.checkDrawRules()) behavior.saveActiveTiles(tiles);
                    else behavior.clearActiveTiles(tiles); // System.out.println("Borrando~");
                });
                if(tiles.get(i) == null) {
                    List<GridTile> tmpList = new ArrayList<>();
                    tmpList.add(tile);
                    tiles.put(i, tmpList);
                } else {
                    List<GridTile> tmpList = tiles.get(i);
                    tmpList.add(tile);
                    tiles.replace(i, tmpList);
                }
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
    
    public int getTileSize() {
        return tile_size;
    }
    
    public void drawAlmacenes(LazyList<Almacen> almacenes, int tileSize) {
        if(almacenes.size() > 0) {
            try {
                AtomicInteger tileSizeAtomic = new AtomicInteger(tileSize);
                almacenes.forEach((almacen) -> {
                    int largo = Integer.valueOf(String.valueOf(almacen.get("largo")));
                    int ancho = Integer.valueOf(String.valueOf(almacen.get("ancho")));
                    int x_relativo = Integer.valueOf(String.valueOf(almacen.get("x_relativo_central")));
                    int y_relativo = Integer.valueOf(String.valueOf(almacen.get("y_relativo_central")));
                    int numRow = largo/tileSizeAtomic.get();
                    int numColumn = ancho/tileSizeAtomic.get();

                    for(int i = x_relativo; i < x_relativo + numRow; i++)
                        for(int j = y_relativo; j < y_relativo + numColumn; j++) {
                            tiles.get(i).get(j).activeTile();
                            behavior.addToSavedTiles(i, j);
                        }
                });        
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
    
    public void drawRacks(LazyList<Rack> racks, int tileSize) {
        if(racks.size() > 0) {
            try {
                racks.forEach((rack) -> {
                    int rack_x1 = Integer.valueOf(String.valueOf(rack.get("x_ancla1")));
                    int rack_x2 = Integer.valueOf(String.valueOf(rack.get("x_ancla2")));
                    int rack_y1 = Integer.valueOf(String.valueOf(rack.get("y_ancla1")));
                    int rack_y2 = Integer.valueOf(String.valueOf(rack.get("y_ancla2")));

                    for(int i = rack_x1 ; i < rack_x2; i++)
                        for(int j = rack_y1; j < rack_y2; j++){
                            tiles.get(i).get(j).activeTile();
                            behavior.addToSavedTiles(i, j);
                        }
                });
            } catch(Exception e) {
                System.out.println(e);
            }    
        }
    }
}
