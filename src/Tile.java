import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Tile extends JComponent {
    public Tile(int x, int y, ArrayList<CollapsePattern> collapsePatterns){
        setBounds(x, y, 20, 20);
        availablePatterns = collapsePatterns;
    }

    public static boolean compareSides(Integer[] rule1, Integer[] rule2){
        if(rule1.length != rule2.length) return false;
        for(int i = 0; i < rule2.length; i++) {
            if(!Objects.equals(rule1[i], rule2[rule1.length - i - 1])) return false;
        }
        return true;
    }

    ArrayList<CollapsePattern> availablePatterns;

    public boolean isCollapsed = false;

    public Tile leftTile = null;
    public Tile rightTile = null;
    public Tile upTile = null;
    public Tile downTile = null;

    CollapsePattern pattern = null;

    public void collapse(){
        isCollapsed = true;
        pattern = availablePatterns.get(new Random().nextInt(availablePatterns.size()));
        availablePatterns.clear();
        availablePatterns.add(pattern);
    }

    public int getWaysToCollapse(){
        ArrayList<CollapsePattern> patternsToRemove = new ArrayList<>();
        for(CollapsePattern pattern : availablePatterns){
            boolean canBe = true;
            if(upTile != null) {
                canBe = false;
                for (CollapsePattern neighborPattern : upTile.availablePatterns){
                    if(compareSides(neighborPattern.down, pattern.up)) {
                        canBe = true;
                        break;
                    }
                }
                if (!canBe) patternsToRemove.add(pattern);
            }
            if(rightTile != null & canBe) {
                canBe = false;
                for (CollapsePattern neighborPattern : rightTile.availablePatterns){
                    if(compareSides(neighborPattern.left, pattern.right)) {
                        canBe = true;
                        break;
                    }
                }
                if (!canBe) patternsToRemove.add(pattern);
            }
            if(downTile != null & canBe) {
                canBe = false;
                for (CollapsePattern neighborPattern : downTile.availablePatterns){
                    if(compareSides(neighborPattern.up, pattern.down)) {
                        canBe = true;
                        break;
                    }
                }
                if (!canBe) patternsToRemove.add(pattern);
            }
            if(leftTile != null & canBe) {
                canBe = false;
                for (CollapsePattern neighborPattern : leftTile.availablePatterns){
                    if(compareSides(neighborPattern.right, pattern.left)) {
                        canBe = true;
                        break;
                    }
                }
                if (!canBe) patternsToRemove.add(pattern);
            }

        }

        if(patternsToRemove.size() > 0) {
            for (CollapsePattern pattern : patternsToRemove) availablePatterns.remove(pattern);
            if (upTile != null) upTile.getWaysToCollapse();
            if (rightTile != null) rightTile.getWaysToCollapse();
            if (downTile != null) downTile.getWaysToCollapse();
            if (leftTile != null) leftTile.getWaysToCollapse();
        }
        return availablePatterns.size();
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        if(!isCollapsed) {
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(0, 0, 20, 20);
        } else {
            g2.drawImage(pattern.img, 0, 0, this);
        }
    }
}
