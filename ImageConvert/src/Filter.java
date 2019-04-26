import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;


public class Filter extends Convert {
    private ImageMaker image;
    public String outputPathway;
    private int enhanced = 1;
    private int yellow = 2;
    private int red = 3;
    private int blue = 4;
    private int filter;

    Filter(ImageMaker i, String output, int filter){
        this.image = i;
        this.filter = filter;
        String path = i.getPathway();
        int a = path.lastIndexOf(".");
        this.outputPathway = output + i.getFileName() + "(filtered 0" + filter + ")" + path.substring(a);
    }

    public void execute(){
        ConvertCmd cmd = new ConvertCmd();
        IMOperation op = new IMOperation();
        String input = image.getPathway();
        op.addImage(input);
        if(filter == enhanced){
            op.blackThreshold(40.0,true);
        }
        if(filter == yellow){
            op.colorize(0,0,100);
        }
        if(filter == red){
            op.colorize(0,50,30);
        }
        if(filter == blue){
            op.colorize(100,30,0);
        }

        //op.colorize(0,100,20);
        //op.colorize(0,255,255);
        op.addImage(outputPathway);
        try{
            cmd.run(op);
        } catch (Exception e){
            System.out.println("Exception at filter");
        }

    }
}
