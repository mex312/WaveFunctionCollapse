import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CollapsePattern {
    public Integer[] up;
    public Integer[] right;
    public Integer[] down;
    public Integer[] left;
    public Image img;

    public CollapsePattern(BufferedImage img){
        this.img = img;
        up = new Integer[20];
        right = new Integer[20];
        down = new Integer[20];
        left = new Integer[20];
        for(int i = 0; i < 20; i++){
            up[i] = img.getRGB(i, 0);
        }
        for(int i = 0; i < 20; i++){
            right[i] = img.getRGB(19, i);
        }
        for(int i = 0; i < 20; i++){
            down[i] = img.getRGB(19 - i, 19);
        }
        for(int i = 0; i < 20; i++){
            left[i] = img.getRGB(0, 19 - i);
        }
    }
}
