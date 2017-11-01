/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createAlmacenArgs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author herbert
 */
public class RectangularDrawing implements Behavior {
    private List<Integer> active_tiles;
    private List<Integer> temp_tiles;
    private List<Integer> saved_tiles;
    private Boolean directionX;
    private Boolean directionY;
    private IEvent<createAlmacenArgs> createLogicalWarehouse;
    
    public RectangularDrawing() {
        active_tiles = new ArrayList<>();
        temp_tiles = new ArrayList<>();
        saved_tiles = new ArrayList<>();
        directionX = false;
        directionY = false;
        createLogicalWarehouse = new Event<>();
    }
    
    @Override
    public Boolean checkDrawRules(List<GridTile> tiles, EventArgs args) {
        int currentRow = active_tiles.get(0)/10;
        int previousRow = -1;
        int currentNumberOfRows = 0;
        int previousNumberOfRows = 0;
        
        Collections.sort(active_tiles);
        
        for(int tile : active_tiles) {
            int tileFirstRow;
            if(previousRow != -1) {
                tileFirstRow = tile - (currentRow - previousRow)*10;
                if(!active_tiles.contains(tileFirstRow)) return false;
            }
            
            if(tile/10 == currentRow) {
                currentNumberOfRows++;
            } else {
                previousRow = currentRow;
                currentRow = tile/10;
                tileFirstRow = tile - (currentRow - previousRow)*10;
                if(!active_tiles.contains(tileFirstRow)) return false;
                if(previousNumberOfRows != 0 && previousNumberOfRows != currentNumberOfRows) return false;
                previousNumberOfRows = currentNumberOfRows;
                currentNumberOfRows = 1;
            }
        }
        
        return previousNumberOfRows == currentNumberOfRows && currentNumberOfRows > 1;
    }

    @Override
    public void clearActiveTiles(List<GridTile> tiles) {
        active_tiles.forEach((i) -> {
            tiles.get(i).clearTile();
        });
        
        active_tiles.clear();
    }

    @Override
    public void saveActiveTiles(List<GridTile> tiles) {
        temp_tiles.addAll(active_tiles);
        active_tiles.clear();
    }

    @Override
    public void addSelectedTile(int tile_index) {
        active_tiles.add(tile_index);
    }

    @Override
    public void clearCurrentActiveTiles(List<GridTile> tiles) {
        temp_tiles.forEach((i) -> {
            tiles.get(i).clearTile();
        });
        
        temp_tiles.clear();
    }

    @Override
    public void clearAndSaveTempTiles(List<GridTile> tiles) {
        saved_tiles.addAll(temp_tiles);
        temp_tiles.clear();
    }

    @Override
    public Boolean isTileSavedOrActive(int index) {
        return !active_tiles.contains(index) && !saved_tiles.contains(index);
    }
}
