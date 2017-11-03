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
 * @author herbert
 */
public interface Behavior {
    public void addSelectedTile(int i_index, int j_index);
    public Boolean checkDrawRules();
    public void clearActiveTiles(TreeMap<Integer, List<GridTile>> tiles);
    public void saveActiveTiles(TreeMap<Integer, List<GridTile>> tiles);
    public void clearCurrentActiveTiles(TreeMap<Integer, List<GridTile>> tiles);
    public void clearAndSaveTempTiles(TreeMap<Integer, List<GridTile>> tiles);
    public Boolean isNotTileSavedOrActive(int i_index, int j_index);
    public void addToSavedTiles(int i_index, int j_index);
}
