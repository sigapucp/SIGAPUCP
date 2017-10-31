/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
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
    
    public RectangularDrawing() {
        active_tiles = new ArrayList<>();
        temp_tiles = new ArrayList<>();
        saved_tiles = new ArrayList<>();
        directionX = false;
        directionY = false;
    }
    
    @Override
    public Boolean checkDrawRules(List<GridTile> tiles, EventArgs args) {
        Collections.sort(active_tiles);
        System.out.println(active_tiles);
        return false;
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
        //
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
