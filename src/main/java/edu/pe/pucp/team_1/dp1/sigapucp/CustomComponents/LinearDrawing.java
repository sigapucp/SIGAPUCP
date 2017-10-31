/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createRackArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.tileArgs;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author herbert
 */
public class LinearDrawing implements Behavior{
    private List<Integer> active_tiles;
    private List<Integer> temp_tiles;
    private List<Integer> saved_tiles;
    private Boolean directionX;
    private Boolean directionY;
    private IEvent<createRackArgs> createRackEvent;

    public LinearDrawing() {
        active_tiles = new ArrayList<>();
        temp_tiles = new ArrayList<>();
        saved_tiles = new ArrayList<>();
        directionX = false;
        directionY = false;
        createRackEvent = new Event<>();
    }
    
    @Override
    public void addSelectedTile(int tile_index) {
        active_tiles.add(tile_index);
    }
    
    @Override
    public Boolean checkDrawRules(List<GridTile> tiles, EventArgs args) {
        AtomicBoolean conditionX = new AtomicBoolean(true);
        AtomicBoolean conditionY = new AtomicBoolean(true);
        tileArgs tile_args =  (tileArgs) args;
        int x_init = tile_args.getX_cord();
        int y_init = tile_args.getY_cord();
        
        active_tiles.forEach((index) -> {
            GridTile tile = tiles.get(index);
            conditionX.set(conditionX.get() && x_init == tile.getXCord());
            conditionY.set(conditionY.get() && y_init == tile.getYCord());
        });
        
        directionX = conditionX.get();
        directionY = conditionY.get();
        
        return conditionX.get() || conditionY.get();
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
        args.setLongitud(largo);
        args.setIs_uniforme(true);
        
        active_tiles.clear();
        createRackEvent.fire(this, args);
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
   
    public IEvent<createRackArgs> getCreateRackEvent() {
        return createRackEvent;
    }
}
