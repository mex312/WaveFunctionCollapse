import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main{
    public static JFrame screen;

    public static Toolkit toolkit = Toolkit.getDefaultToolkit();

    public static boolean compareImage(BufferedImage img1, BufferedImage img2){
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++){
                if(img1.getRGB(i, j) != img2.getRGB(i, j)) return false;
            }
        }
        return true;
    }

    public static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
        int w = bufferedimage.getWidth();// Получаем ширину изображения.
        int h = bufferedimage.getHeight();// Получаем высоту изображения.
        int type = bufferedimage.getColorModel().getTransparency();// Получаем прозрачность изображения.
        BufferedImage img;// Пустая картинка.
        Graphics2D graphics2d;// Пустое перо.
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);// Вращение, градус - целое число, градус, например, по вертикали 90 градусов.
        graphics2d.drawImage(bufferedimage, 0, 0, null);// От изображения bufferedimagecopy к img 0,0 - координаты img.
        graphics2d.dispose();
        return img;// Возврат к скопированному изображению, исходное изображение все еще не изменилось, не повернуто и может быть использовано в следующий раз.
    }

    ArrayList<CollapsePattern> generateCollapsePatternsList() throws IOException, URISyntaxException {
        ArrayList<CollapsePattern> out = new ArrayList<>();


        URL URLToIMGs = getClass().getResource("/IMGs/");
        System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        File pathToIMGs = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replace("WaveFunctionCollapse.jar", "") + "/Tiles/");
        System.out.println(pathToIMGs);
        if (pathToIMGs.mkdir()) {new File(pathToIMGs.getPath() + "/ThereGoYourTiles").createNewFile();}
        File[] allTheTiles = pathToIMGs.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.toString().endsWith(".png");
            }
        });
        ArrayList<BufferedImage> tilesBufferedIMGs = new ArrayList<>();
        for(File file : allTheTiles){
            System.out.println(file.getName());
            BufferedImage img = ImageIO.read(file);
            if(img.getHeight() == 20 && img.getWidth() == 20){
                BufferedImage img90 = rotateImage(img, 90);
                BufferedImage img180 = rotateImage(img, 180);
                BufferedImage img270 = rotateImage(img, 270);
                tilesBufferedIMGs.add(img);
                out.add(new CollapsePattern(img));
                if(!compareImage(img, img90)) {
                    tilesBufferedIMGs.add(img90);
                    out.add(new CollapsePattern(img90));
                }
                if(!compareImage(img, img90) && !compareImage(img, img180)) {
                    tilesBufferedIMGs.add(img180);
                    out.add(new CollapsePattern(img180));
                }
                if(!compareImage(img, img90) && !compareImage(img, img180) && !compareImage(img, img270)) {
                    tilesBufferedIMGs.add(img270);
                    out.add(new CollapsePattern(img270));
                }
            }
        }

        return out;
    }

    public Main() throws IOException, URISyntaxException {
        screen = new JFrame("Wave function collapse");
        screen.setBounds(0, 0, 800, 600);
        screen.setLayout(null);
        screen.setVisible(true);
        screen.setLocationRelativeTo(null);
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        TileMap tileMap = new TileMap(0, 0, 30, 30, generateCollapsePatternsList());
        screen.add(tileMap);
        tileMap.collapse();
        //Tile tile = new Tile(200, 200, collapsePatterns);
        //screen.add(tile);
        //tile.collapse();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        new Main();
    }
}
