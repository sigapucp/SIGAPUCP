/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents;

import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.EventArgs;
import java.util.List;

/**
 *
 * @author herbert
 */
public interface Behavior {
    public void addSelectedTile(int tile_index);
    public Boolean checkDrawRules(List<GridTile> tiles, EventArgs args);
    public void clearActiveTiles(List<GridTile> tiles);
    public void saveActiveTiles(List<GridTile> tiles);
    public void clearCurrentActiveTiles(List<GridTile> tiles);
    public void clearAndSaveTempTiles(List<GridTile> tiles);
    public Boolean isTileSavedOrActive(int index);
}
