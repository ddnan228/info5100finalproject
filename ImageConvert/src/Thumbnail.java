import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class Thumbnail implements Order {
    private ImageMaker image;
    public String defaultOutput;

    Thumbnail(ImageMaker i){
        this.image = i;
        defaultOutput = "thumbnailsTEMP/" + image.getFileName() + "(thumbnail).jpg";
    }

    public void execute(){
        ConvertCmd cmd = new ConvertCmd();
        IMOperation op = new IMOperation();
        String input = image.getPathway();
        op.addImage(input);
        op.resize(100,100);
        op.format("JEPG");
        op.addImage(defaultOutput);
        try{
            cmd.run(op);
        } catch (Exception e){
            System.out.println("Exception at Thumbnail.execute.");
        }
    }
}
