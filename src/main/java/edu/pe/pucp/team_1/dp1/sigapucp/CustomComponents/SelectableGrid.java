/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEventHandler;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.tileArgs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    private List<GridTile> userTiles;    
    private Behavior behavior;
    private int current_x_tile;
    private int current_y_tile;

    // Largo -> Width
    // Ancho -> Height
    public SelectableGrid(int width, int heigth, int grid_size, Behavior external_behavior,int uiGridWidth,int uiGridHeight) {
        grid_width = uiGridWidth;
        grid_heigth = uiGridHeight;
        tile_size = grid_size;
        num_columns = width/grid_size;
        num_rows = heigth/grid_size;
        tiles = new TreeMap<>();
        userTiles = new ArrayList<>();
        grid_real_width = width;
        grid_real_heigth = heigth;
        behavior = external_behavior;
        initializeTiles();
    }
    
    private void initializeTiles() {
        int aspect_ratio_heigth = grid_heigth/num_rows;
        int aspect_ratio_width = grid_width/num_columns;
        aspect_ratio_heigth = Integer.min(aspect_ratio_heigth, aspect_ratio_width);
        aspect_ratio_width = Integer.min(aspect_ratio_heigth, aspect_ratio_width);
        // X-> j && Y -> i
        for(int i = 0; i < num_rows; i++)
            for(int j = 0; j < num_columns; j++) {
                GridTile tile = new GridTile(aspect_ratio_width, aspect_ratio_heigth, j, i);
                tile.setTranslateX(j * aspect_ratio_width);
                tile.setTranslateY(i * aspect_ratio_heigth);

                tile.getActiveTileEvent().addHandler((sender, args) -> {
                    if(!tile.isActive()) userTiles.add(tile);
                    if (behavior.isNotTileSavedOrActive(args.getY_cord(), args.getX_cord())) behavior.addSelectedTile(args.getY_cord(), args.getX_cord());
                });

                tile.getReleaseEvent().addHandler((sender, args) -> {
                    behavior.preSaveTransformation(tiles,current_x_tile,current_y_tile);                   
                    if (behavior.checkDrawRules(tiles)) behavior.saveActiveTiles(tiles);
                    else {
                        behavior.clearActiveTiles(tiles); // System.out.println("Borrando~");
                        behavior.clearUserTiles(userTiles);
                        behavior.fireDrawingErrorEvent();                      
                    }
                });

                tile.getPressedEvent().addHandler((Object sender, tileArgs args) -> {
                    behavior.startDrag(args.getX_cord(),args.getY_cord());
                });

                tile.getDragEvent().addHandler((Object sender, tileArgs args) -> {
                    current_x_tile = args.getX_cord() + 1;
                    current_y_tile = args.getY_cord() + 1;
                    //userTiles.add(tiles.get(args.getY_cord()).get(args.getX_cord()));                    
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
    
    public void changeNullToWhite()
    {
          tiles.forEach((i, list) -> {
            list.forEach((j) -> {
                if(!j.isActive())
                {
                    j.setFill(Color.WHITE);
                }
            });
        });        
    }
    
    public void changeNullToColor(Color color)
    {        
        tiles.forEach((i, list) -> {
          list.forEach((j) -> {
              if(!j.isActive())
              {
                  j.setFill(color);
              }
          });
      });        

    }
    
    public void changeWhiteToNull()
    {
          tiles.forEach((i, list) -> {
            list.forEach((j) -> {
                if(!j.isActive())
                {
                    j.setFill(null);
                }
            });
        });        
    }
    
    public void clearAndSaveTempTiles() {
        behavior.clearAndSaveTempTiles(tiles);
    }
    
    public void clearUserTiles()
    {
        for(GridTile tile:userTiles)
        {
            tile.clearTile();
        }
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
                    int numRow = ancho/tileSizeAtomic.get();
                    int numColumn = largo/tileSizeAtomic.get();

                    for(int i = y_relativo; i < y_relativo + numRow; i++)
                        for(int j = x_relativo; j < x_relativo + numColumn; j++) {
                            tiles.get(i).get(j).activeTile(false);
                            behavior.addToSavedTiles(i, j);
                        }
                });        
            } catch(Exception e) {
                Logger.getLogger(SelectableGrid.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    public void drawAlmacenesRacks(LazyList<Almacen> almacenes,int anchoCentral,int altoCentral, int tileSize) {
         if (almacenes.size() <= 0) return;
         try {
              AtomicInteger tileSizeAtomic = new AtomicInteger(tileSize);
                almacenes.forEach((almacen) -> {
                    int x_relativo = Integer.valueOf(String.valueOf(almacen.get("x_relativo_central")));
                    int y_relativo = Integer.valueOf(String.valueOf(almacen.get("y_relativo_central")));
                    int numRow = anchoCentral/tileSizeAtomic.get();
                    int numColumn = altoCentral/tileSizeAtomic.get();

                    List<Rack> racks = Rack.where("almacen_id = ?",almacen.getId());
                    for(Rack rack:racks) {
                        int anchorX1 = rack.getInteger("x_ancla1");
                        int anchorY1 = rack.getInteger("y_ancla1");

                        int anchorX2 = rack.getInteger("x_ancla2");
                        int anchorY2 = rack.getInteger("y_ancla2");

                        if (rack.getString("tipo").equals(Rack.TIPO.HORIZONTAL.name())) {
                            int start = Integer.min(anchorX1, anchorX2) + x_relativo;
                            int finish = Integer.max(anchorX1, anchorX2) + x_relativo;

                            for(;start<finish;start++) {
                                tiles.get(anchorY2 + y_relativo).get(start).activeTile(false);
                                behavior.addToSavedTiles(anchorY2 + y_relativo, start);
                            }

                        } else {
                            int start = Integer.min(anchorY1, anchorY2) + y_relativo;
                            int finish = Integer.max(anchorY1, anchorY2) + y_relativo;

                            for(;start<finish;start++) {
                                tiles.get(start).get(anchorX1 + x_relativo).activeTile(false);
                                behavior.addToSavedTiles(start, anchorX1 + x_relativo);
                            }
                        }
                    }
                });
        } catch (Exception e) {
             Logger.getLogger(SelectableGrid.class.getName()).log(Level.SEVERE, null, e);
        }        
    }        
        
    public void drawRacks(LazyList<Rack> racks, int tileSize,Color color) {
        if(racks.size() > 0) {
            try {
                racks.forEach((rack) -> {
                    int rack_x1 = Integer.valueOf(String.valueOf(rack.get("x_ancla1")));
                    int rack_x2 = Integer.valueOf(String.valueOf(rack.get("x_ancla2")));
                    int rack_y1 = Integer.valueOf(String.valueOf(rack.get("y_ancla1")));
                    int rack_y2 = Integer.valueOf(String.valueOf(rack.get("y_ancla2")));
                    System.out.println(String.format("Rack: (X1, Y1) -> (%d, %d) - (X2, Y2) -> (%d, %d)", rack_x1, rack_y1, rack_x2, rack_y2));
                    if(rack_x1 == rack_x2) {
                        for(int i = rack_y1; i <= rack_y2; i++) {
                            tiles.get(i).get(rack_x1).activeTileColor(false,color);
                            behavior.addToSavedTiles(i, rack_x1);
                        }
                            
                    } 
                    else if (rack_y1 == rack_y2) {
                        for(int j = rack_x1; j <= rack_x2; j++) {
                            tiles.get(rack_y1).get(j).activeTile(false);
                            behavior.addToSavedTiles(rack_y1, j);
                        }
                            
                    }
                });
            } catch(Exception e) {
                Logger.getLogger(SelectableGrid.class.getName()).log(Level.SEVERE, null, e);
            }    
        }
    }
    
    public void pintarTile(int x,int y,Color color)
    {
        GridTile tile = tiles.get(y).get(x);      
        tile.activeTileColor(false, color);
    }
    
    public void pintarTileDeletable(int x,int y,Color color)
    {
        GridTile tile = tiles.get(y).get(x);
        userTiles.add(tile);
        tile.activeTileColor(false, color);
    }
}
