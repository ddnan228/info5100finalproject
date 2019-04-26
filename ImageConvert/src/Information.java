
import javaxt.io.Image;
import java.util.HashMap;

public class Information implements Order {
    private ImageMaker image;
    private int height;
    private int width;
    private double[] coord;
    private String camera;
    private String manu;
    private Image i;
    private HashMap<Integer, Object> exif;
    public String info;

    Information(ImageMaker im) {
        this.image = im;
        String pathway = im.getPathway();
        i = new Image(pathway);
    }

    public void execute(){
        exif = i.getExifTags();
        height = i.getHeight();
        width = i.getWidth();
        camera = (String) exif.get(0x0110);
        manu = (String) exif.get(0x010F);
        coord = i.getGPSCoordinate();
        String fileName = image.getFileName();
        info = fileName + '\n' + "size: " +height + "X" + width + "\n";
        if(camera != null){
            info = info + "Cam: " + camera + "\n";
        } else{
            info = info + "Cam: Unknown\n";
        }
        if(manu != null){
            info = info + "Manu: " +  manu + "\n";
        } else{
            info = info + "Manu: Unknown\n";
        }
        if(coord != null){
            info = info + "GPS: " + String.format("%.2f",coord[0]) + "," + String.format("%.2f",coord[1]);
        } else{
            info = info + "GPS: Unknown\n";
        }
    }
}


