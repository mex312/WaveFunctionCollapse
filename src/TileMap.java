import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class TileMap extends JComponent {
    public ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    public ArrayList<Tile> tilesToUpdate = new ArrayList<>();

    public TileMap(int x, int y, int widthInTiles, int heightInTiles, ArrayList<CollapsePattern> collapsePatterns){
        setBounds(x, y, widthInTiles * 20, heightInTiles * 20);
        for(int i = 0; i < widthInTiles; i++){
            tiles.add(new ArrayList<Tile>());
            for(int j = 0; j < heightInTiles; j++){
                Tile tile = new Tile(i * 20, j * 20, (ArrayList<CollapsePattern>) collapsePatterns.clone());

                if(i > 0){
                    tile.leftTile = tiles.get(i-1).get(j);
                    tiles.get(i-1).get(j).rightTile = tile;
                }
                if(j > 0){
                    tile.upTile = tiles.get(i).get(j-1);
                    tiles.get(i).get(j-1).downTile = tile;
                }

                add(tile);
                tiles.get(i).add(tile);
                tilesToUpdate.add(tile);
            }
        }
    }

    public void collapse(){
        int x = new Random().nextInt(tiles.size());
        int y = new Random().nextInt(tiles.get(x).size());
        Tile tileToCollapse = tiles.get(x).get(y);
        tileToCollapse.collapse();
        tilesToUpdate.remove(tileToCollapse);
        repaint();
        while(tilesToUpdate.size() > 0){
            ArrayList<Tile> tilesToCollapse = new ArrayList<>();
            int waysToCollapse = Integer.MAX_VALUE;
            for(Tile tile : tilesToUpdate){
                if(tile.getWaysToCollapse() < waysToCollapse){
                    waysToCollapse = tile.getWaysToCollapse();
                    tilesToCollapse = new ArrayList<>();
                }
                if(tile.getWaysToCollapse() == waysToCollapse){
                    tilesToCollapse.add(tile);
                }
            }
            if(waysToCollapse == 1){
                for(Tile tile : tilesToCollapse){
                    tile.collapse();
                    tilesToUpdate.remove(tile);
                }
            } else {
                Tile tile = tilesToCollapse.get(new Random().nextInt(tilesToCollapse.size()));
                tile.collapse();
                tilesToUpdate.remove(tile);
            }
            repaint();
        }
    }
}
