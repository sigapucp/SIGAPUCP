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
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.javalite.activejdbc.validation.length.Min;

/**
 *
 * @author herbert
 * TreeMap<RowIndex, List<ColumnIndex>> elements;
 */
public class RectangularDrawing implements Behavior {
    private TreeMap<Integer, List<Integer>> active_tiles;
    private TreeMap<Integer, List<Integer>> temp_tiles;
    private TreeMap<Integer, List<Integer>> saved_tiles;
    private Boolean directionX;
    private Boolean directionY;
    private int x_drag_start = 0;
    private int y_drag_start = 0;
    private IEvent<createAlmacenArgs> createLogicalWarehouse;
    private IEvent<EventArgs> disableGridEvent;
    private IEvent<EventArgs> drawingErrorEvent;

    public RectangularDrawing() {
        active_tiles = new TreeMap<>();
        temp_tiles = new TreeMap<>();
        saved_tiles = new TreeMap<>();
        drawingErrorEvent = new Event<>();
        directionX = false;
        directionY = false;
        createLogicalWarehouse = new Event<>();
        disableGridEvent = new Event<>();
    }
    
    @Override
    public Boolean checkDrawRules(TreeMap<Integer, List<GridTile>> tiles) {
        AtomicInteger expectedNumberOfRows = new AtomicInteger(0);
        AtomicBoolean sameNumberOfRows = new AtomicBoolean(true);
                
        active_tiles.forEach((i, list) -> {
            if(expectedNumberOfRows.get() == 0) 
            {
                expectedNumberOfRows.set(list.size());
            }
            Boolean condition = sameNumberOfRows.get() && expectedNumberOfRows.get() == list.size();
            sameNumberOfRows.set(condition);
        });
        
        return active_tiles.size() > 1 &&
               expectedNumberOfRows.get() > 1 &&
               sameNumberOfRows.get();
    }

    @Override
    public void clearActiveTiles(TreeMap<Integer, List<GridTile>> tiles) {
        active_tiles.forEach((i, list) -> {
            list.forEach((j) -> {
                GridTile tile = tiles.get(i).get(j);
                tile.clearTile();
            });
        });
        active_tiles.clear();
    }

    @Override
    public void saveActiveTiles(TreeMap<Integer, List<GridTile>> tiles) {                                              
        temp_tiles.putAll(active_tiles);
        active_tiles.clear();
        disableGridEvent.fire(this, EventArgs.Empty);
    }

    @Override
    public void addSelectedTile(int i_index, int j_index) {
        List<Integer> tmpList = active_tiles.get(i_index);
        
        if(tmpList == null) {
            tmpList = new ArrayList<>();
            tmpList.add(j_index);
            active_tiles.put(i_index, tmpList);
        } else {
            tmpList.add(j_index);
            active_tiles.replace(i_index, tmpList);
        }
    }

    @Override
    public void clearCurrentActiveTiles(TreeMap<Integer, List<GridTile>> tiles) {
        temp_tiles.forEach((i, list) -> {
            list.forEach((j) -> {
                tiles.get(i).get(j).clearTile();
            });
        });
        
        temp_tiles.clear();
    }

    @Override
    public void clearAndSaveTempTiles(TreeMap<Integer, List<GridTile>> tiles) {
        saved_tiles.putAll(temp_tiles);
        
        Map.Entry<Integer, List<Integer>> entry = temp_tiles.entrySet().iterator().next();
        List<Integer> tmpValues = entry.getValue();
        Collections.sort(tmpValues);
        
        createAlmacenArgs args = new createAlmacenArgs();
        
        args.setLargo(tmpValues.size());
        args.setAncho(temp_tiles.size());
        args.setEs_cental('F');
        args.setX_relativo(tmpValues.get(0));
        args.setY_relativo(entry.getKey());
        createLogicalWarehouse.fire(this, args);
        
        temp_tiles.clear();
    }

    @Override
    public Boolean isNotTileSavedOrActive(int i_index, int j_index) {
        List<Integer> active_tilesList = active_tiles.get(i_index);
        List<Integer> saved_tilesList = saved_tiles.get(i_index);

        return (active_tilesList == null || !active_tilesList.contains(j_index)) &&
               (saved_tilesList == null || !saved_tilesList.contains(j_index));
    }

    @Override
    public void addToSavedTiles(int i_index, int j_index) {
        List<Integer> tmpList = saved_tiles.get(i_index);
        
        if(tmpList == null) {
            tmpList = new ArrayList<>();
            tmpList.add(j_index);
            saved_tiles.put(i_index, tmpList);
        } else {
            tmpList.add(j_index);
            saved_tiles.replace(i_index, tmpList);
        }
    }
    
    public IEvent<createAlmacenArgs> getCreateLogicalWarehouseEvent() {
        return createLogicalWarehouse;
    }
    
    public IEvent<EventArgs> getDisableGridEvent() {
        return disableGridEvent;
    }

    public IEvent<EventArgs> getDrawingErrorEvent() {
        return drawingErrorEvent;
    }

    @Override
    public void startDrag(int i_index, int j_index) {
        x_drag_start = i_index;
        y_drag_start = j_index; 
    }

    @Override
    public void preSaveTransformation(TreeMap<Integer, List<GridTile>> tiles,int i_index, int j_index) {
        int startx = Integer.min(x_drag_start,i_index);
        int finishx = Integer.max(x_drag_start,i_index);
        
        int starty = Integer.min(y_drag_start,j_index);
        int finishy = Integer.max(y_drag_start,j_index);
        
        for(int i = startx;i<finishx;i++)
        {
            for(int j = starty;j<finishy;j++)
            {                
                tiles.get(j).get(i).activeTile(true);
            }
        }
    }

    @Override
    public void fireDrawingErrorEvent() {
        getDrawingErrorEvent().fire(this, new EventArgs());
    }

    @Override
    public void clearUserTiles(List<GridTile> tiles) {
       
    }
}
