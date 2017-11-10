/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Jauma
 */
public class NullDrawing implements Behavior{

    @Override
    public void addSelectedTile(int i_index, int j_index) {
        
    }

    @Override
    public Boolean checkDrawRules(TreeMap<Integer, List<GridTile>> tiles) {
        return false;
    }

    @Override
    public void clearActiveTiles(TreeMap<Integer, List<GridTile>> tiles) {
        
    }

    @Override
    public void saveActiveTiles(TreeMap<Integer, List<GridTile>> tiles) {
        
    }

    @Override
    public void clearCurrentActiveTiles(TreeMap<Integer, List<GridTile>> tiles) {
        
    }

    @Override
    public void clearAndSaveTempTiles(TreeMap<Integer, List<GridTile>> tiles) {
        
    }

    @Override
    public Boolean isNotTileSavedOrActive(int i_index, int j_index) {
        return false;
    }

    @Override
    public void addToSavedTiles(int i_index, int j_index) {
        
    }

    @Override
    public void startDrag(int i_index, int j_index) {       
    }

    @Override
    public void preSaveTransformation(TreeMap<Integer, List<GridTile>> tiles,int i_index, int j_index) {        
    }

    @Override
    public void clearUserTiles(List<GridTile> tiles) {
        for(GridTile tile:tiles)
        {
            tile.clearTile();            
        }       
    }    
    public void fireDrawingErrorEvent() {
    }
    
}
