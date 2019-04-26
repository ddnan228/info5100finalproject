import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class ConvertFormat extends Convert{
    private ImageMaker image;
    public String outputPathway;
    private String targetFormat;

    ConvertFormat(ImageMaker i, String output, String type){
        this.image = i;
        this.outputPathway = output + i.getFileName() + "(converted)." + type.toLowerCase();
        targetFormat = type.toUpperCase();
    }

    public void execute(){
        ConvertCmd cmd = new ConvertCmd();
        IMOperation op = new IMOperation();
        String input = image.getPathway();
        op.addImage(input);
        op.format(targetFormat);
        op.addImage(outputPathway);
        try{
            cmd.run(op);
        } catch (Exception e){
            System.out.println("Exception at ConvertFormat.execute");
        }
    }
}
