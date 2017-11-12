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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author herbert
 */
public class LinearDrawing implements Behavior{
    private TreeMap<Integer, List<Integer>> active_tiles;
    private TreeMap<Integer, List<Integer>> temp_tiles;
    private TreeMap<Integer, List<Integer>> saved_tiles;
    private Boolean directionX;
    private Boolean directionY;
    private IEvent<createRackArgs> createRackEvent;
    private IEvent<EventArgs> disableGridEvent;
    private IEvent<EventArgs> drawingErrorEvent;

    public LinearDrawing() {
        active_tiles = new TreeMap<>();
        temp_tiles = new TreeMap<>();
        saved_tiles = new TreeMap<>();
        directionX = false;
        directionY = false;
        createRackEvent = new Event<>();
        disableGridEvent = new Event<>();
        drawingErrorEvent = new Event<>();
    }

    private boolean checkFirstTileRow(int x, int y, TreeMap<Integer, List<GridTile>> tiles) {
        Map.Entry<Integer, List<GridTile>> firstEntry = tiles.entrySet().iterator().next();
        int rows = tiles.size();
        boolean condition = true;

        if (y < 1) { 
            // (y + 1; x)
            condition = condition && !tiles.get(y+1).get(x).isActive();
            if (x >= 1)  { // (y; x - 1) && (y + 1 ; x - 1)
                condition = condition && !tiles.get(y).get(x-1).isActive();
                condition = condition && !tiles.get(y+1).get(x-1).isActive();
            }
        } else if (y == rows -1) {
            // (y-1; x)
            condition = condition && !tiles.get(y-1).get(x).isActive();
            if(x >= 1) { // (y; x-1) && (y-1; x-1)
                condition = condition && !tiles.get(y).get(x-1).isActive();
                condition = condition && !tiles.get(y-1).get(x-1).isActive();
            }
        } else {
            // (y-1; x) && (y+1; x)
            condition = condition && !tiles.get(y-1).get(x).isActive();
            condition = condition && !tiles.get(y+1).get(x).isActive();

            if (x >= 1) { // (y; x-1) && (y+1;x-1) && (y-1;x-1)
                condition = condition && !tiles.get(y).get(x-1).isActive();
                condition = condition && !tiles.get(y+1).get(x-1).isActive();
                condition = condition && !tiles.get(y-1).get(x-1).isActive();
            }
        }
        return condition;
    }

    private boolean checkLastTileRow(int x, int y, TreeMap<Integer, List<GridTile>> tiles) {
        Map.Entry<Integer, List<GridTile>> firstEntry = tiles.entrySet().iterator().next();
        int rows = tiles.size();
        int columns = firstEntry.getValue().size();
        boolean condition = true;

        if (y < 1) {
            condition = condition && !tiles.get(y+1).get(x).isActive();

            if(x < columns - 1) {
                condition = condition && !tiles.get(y).get(x+1).isActive();
                condition = condition && !tiles.get(y+1).get(x+1).isActive();
            }
        } else if (y == rows - 1) {
            condition = condition && !tiles.get(y-1).get(x).isActive();

            if (x < columns - 1) {
                condition = condition && !tiles.get(y).get(x+1).isActive();
                condition = condition && !tiles.get(y-1).get(x+1).isActive();
            }
        } else {
            condition = condition && !tiles.get(y-1).get(x).isActive();
            condition = condition && !tiles.get(y+1).get(x).isActive();

            if(x < columns - 1) {
                condition = condition && !tiles.get(y-1).get(x+1).isActive();
                condition = condition && !tiles.get(y).get(x+1).isActive();
                condition = condition && !tiles.get(y+1).get(x+1).isActive();
            }
        }
        return condition;
    }

    private boolean checkUpAndDown(int x, int y, TreeMap<Integer, List<GridTile>> tiles) {
        Map.Entry<Integer, List<GridTile>> firstEntry = tiles.entrySet().iterator().next();
        int rows = tiles.size();
        boolean condition = true;

        if (y < 1) {
            condition = condition && !tiles.get(y+1).get(x).isActive();
        } else if (y == rows - 1) {
            condition = condition && !tiles.get(y-1).get(x).isActive();
        } else {
            condition = condition && !tiles.get(y-1).get(x).isActive();
            condition = condition && !tiles.get(y+1).get(x).isActive();
        }
        return condition;
    }

    private boolean checkFirstTileColumn(int x, int y, TreeMap<Integer, List<GridTile>> tiles) {
        boolean condition = true;
        Map.Entry<Integer, List<GridTile>> firstEntry = tiles.entrySet().iterator().next();
        int columns = firstEntry.getValue().size();

        if(x < 1) {
            condition = condition && !tiles.get(y).get(x+1).isActive();

            if(y >= 1) {
                condition = condition && !tiles.get(y-1).get(x+1).isActive();
                condition = condition && !tiles.get(y-1).get(x).isActive();
            }
        } else if (x == columns - 1) {
            condition = condition && !tiles.get(y).get(x-1).isActive();

            if(y >= 1) {
                condition = condition && !tiles.get(y-1).get(x-1).isActive();
                condition = condition && !tiles.get(y-1).get(x).isActive();
            }
        } else {
            condition = condition && !tiles.get(y).get(x-1).isActive();
            condition = condition && !tiles.get(y).get(x+1).isActive();

            if (y>= 1) {
                condition = condition && !tiles.get(y-1).get(x).isActive();
                condition = condition && !tiles.get(y-1).get(x+1).isActive();
                condition = condition && !tiles.get(y-1).get(x-1).isActive();
            }
        }

        return condition;
    }

    private boolean checkLastTileColumn(int x, int y, TreeMap<Integer, List<GridTile>> tiles) {
        boolean condition = true;
        Map.Entry<Integer, List<GridTile>> firstEntry = tiles.entrySet().iterator().next();
        int columns = firstEntry.getValue().size();
        int rows = tiles.size();

        if(x < 1) {
            condition = condition && !tiles.get(y).get(x+1).isActive();

            if (y < rows - 1) {
                condition = condition && !tiles.get(y+1).get(x).isActive();
                condition = condition && !tiles.get(y+1).get(x+1).isActive();
            }
        } else if (x == columns - 1) {
            condition = condition && !tiles.get(y).get(x-1).isActive();

            if(y < rows - 1){
                condition = condition && !tiles.get(y+1).get(x).isActive();
                condition = condition && !tiles.get(y+1).get(x-1).isActive();
            }
        } else {
            condition = condition && !tiles.get(y).get(x-1).isActive();
            condition = condition && !tiles.get(y).get(x+1).isActive();

            if(y < rows - 1) {
                condition = condition && !tiles.get(y+1).get(x).isActive();
                condition = condition && !tiles.get(y+1).get(x-1).isActive();
                condition = condition && !tiles.get(y+1).get(x+1).isActive();
            }
        }
        return condition;
    }

    private boolean checkRightAndLeft(int x, int y, TreeMap<Integer, List<GridTile>> tiles) {
        boolean condition = true;
        Map.Entry<Integer, List<GridTile>> firstEntry = tiles.entrySet().iterator().next();
        int columns = firstEntry.getValue().size();

        if(x< 1) {
            condition = condition && !tiles.get(y).get(x+1).isActive();
        } else if (x == columns - 1) {
            condition = condition && !tiles.get(y).get(x-1).isActive();
        } else {
            condition = condition && !tiles.get(y).get(x+1).isActive();
            condition = condition && !tiles.get(y).get(x-1).isActive();
        }
        return condition;
    }

    private boolean checkAroundDraw(TreeMap<Integer, List<GridTile>> tiles) {
        boolean condition = true;
        Iterator<Map.Entry<Integer, List<Integer>>> iterator = active_tiles.entrySet().iterator();
        int index = 0;

        if (directionX) { // Horizontal
            Map.Entry<Integer, List<Integer>> firstEntry = iterator.next();
            int row = firstEntry.getKey();
            List<Integer> firstEntryList = firstEntry.getValue();
            Collections.sort(firstEntryList);

            for(int index_x : firstEntryList) {
                if(index == 0) condition = condition && checkFirstTileRow(index_x, row, tiles);
                else if(index == firstEntryList.size() - 1) condition = condition && checkLastTileRow(index_x, row, tiles);
                else condition = condition && checkUpAndDown(index_x, row, tiles);
                index++;
            }
        } else { // Vertical
            while(iterator.hasNext()) {
                Map.Entry<Integer, List<Integer>> entry = iterator.next();
                int row = entry.getKey();
                int column = entry.getValue().get(0);

                if(index == 0) condition = condition && checkFirstTileColumn(column, row, tiles);
                else if(index == active_tiles.size() - 1) condition = condition && checkLastTileColumn(column, row, tiles);
                else condition = condition && checkRightAndLeft(column, row, tiles);

                index++;
            }
        }

        return condition;
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
            active_tiles.put(i_index, tmpList);
        }
    }
    
    @Override
    public Boolean checkDrawRules(TreeMap<Integer, List<GridTile>> tiles) {
        boolean condition;
        
        directionX = active_tiles.size() <= 1;
        directionY = active_tiles.size() > 1;
        
        Iterator<Map.Entry<Integer, List<Integer>>> iterator = active_tiles.entrySet().iterator();
        Map.Entry<Integer, List<Integer>> firstEntry = iterator.next();
        List<Integer> firstEntryList = firstEntry.getValue();

        if(active_tiles.size() < 2 && firstEntryList.size() > 1) { // Verifica el rack es Horizontal
            Collections.sort(firstEntryList);
            int currentColumn = firstEntryList.get(0);
            condition = true;

            for(int i = 1 ; i < firstEntryList.size(); i++) {
                condition = condition && currentColumn == firstEntryList.get(i) - 1;
                currentColumn = firstEntryList.get(i);
            }
        } else { // Verifca los racks verticales
            AtomicBoolean atomicCond = new AtomicBoolean(true);
            AtomicInteger atomicColumn = new AtomicInteger(firstEntryList.get(0));
            AtomicInteger atomicPreviousRow = new AtomicInteger(firstEntry.getKey());

            while(iterator.hasNext()) {
                Map.Entry<Integer, List<Integer>> entry = iterator.next();
                Integer key = entry.getKey();
                List<Integer> value = entry.getValue();

                atomicCond.set( atomicCond.get() &&
                                value.size() == 1 &&
                                key - 1 == atomicPreviousRow.get() &&
                                atomicColumn.get() == value.get(0) );
                atomicPreviousRow.set(key);
            }
            condition = atomicCond.get();
        }

        condition = condition && checkAroundDraw(tiles); // Verifica que no existan tiles activos alrededor del tile actual

        return condition;
    }

    @Override
    public void clearActiveTiles(TreeMap<Integer, List<GridTile>> tiles) {
        active_tiles.forEach((i, list) -> {
            list.forEach((j) -> {
                tiles.get(i).get(j).clearTile();
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
        createRackArgs args = new createRackArgs();
        AtomicInteger index = new AtomicInteger(0);
        AtomicInteger firstIndex = new AtomicInteger(0);
        AtomicInteger lastIndex = new AtomicInteger(0);

        saved_tiles.putAll(temp_tiles);

        temp_tiles.forEach((i, list) -> {
            if(index.get() == 0 )
                firstIndex.set(i);

            if(index.get() == temp_tiles.size() - 1)
                lastIndex.set(i);

            index.set(index.get() + 1);
        });
        
        List<Integer> firstList = temp_tiles.get(firstIndex.get());
        List<Integer> lastList = temp_tiles.get(lastIndex.get());
        Collections.sort(firstList);
        Collections.sort(lastList);
        
        int x_ancla1 = firstList.get(0);
        int x_ancla2 = lastList.get(lastList.size() - 1);
        int y_ancla1 = firstIndex.get();
        int y_ancla2 = lastIndex.get();
        
        args.setX_ancla1(x_ancla1);
        args.setY_ancla1(y_ancla1);
        args.setX_ancla2(x_ancla2);
        args.setY_ancla2(y_ancla2);
        int largo = directionX ? (x_ancla2 - x_ancla1 + 1) : (directionY ? (y_ancla2 - y_ancla1 + 1) : 0);
        args.setLongitud(largo);
        args.setIs_uniforme('T');

        createRackEvent.fire(this, args);
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
   
    public IEvent<createRackArgs> getCreateRackEvent() {
        return createRackEvent;
    }
    
    public IEvent<EventArgs> getDisableGridEvent() {
        return disableGridEvent;
    }

    public IEvent<EventArgs> getDrawingErrorEvent() {
        return drawingErrorEvent;
    }

    @Override
    public void startDrag(int i_index, int j_index) {
    }
   

    @Override
    public void clearUserTiles(List<GridTile> tiles) {    
    }

    public void preSaveTransformation(TreeMap<Integer, List<GridTile>> tiles, int i_index, int j_index) {
    }

    @Override
    public void fireDrawingErrorEvent() {
        getDrawingErrorEvent().fire(this, new EventArgs());

    }
}
