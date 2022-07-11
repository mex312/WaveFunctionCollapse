import javax.swing.*;
import java.awt.*;

public class DummyTile extends JComponent {
    Image img;

    public DummyTile(int x, int y, Image img){
        this.img = img;
        setBounds(x, y, 20, 20);
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(img, 0, 0, this);
    }
}
