public class ImageMaker {
    private String pathway;

    ImageMaker(String pathway){
        this.pathway = pathway;
    }

    public String getPathway(){
        return pathway;
    }

    public String getFileName(){
        int a = pathway.lastIndexOf("/");
        int b = pathway.lastIndexOf(".");
        return pathway.substring(a+1, b);
    }

    public String orderInfo(){
        Information in = new Information(this);
        in.execute();
        return in.info;
    }

    public String orderThumbnail(){
        Thumbnail t = new Thumbnail(this);
        t.execute();
        return t.defaultOutput;
    }

    public void orderConvertFormat(String output, String format){
        ConvertFormat c = new ConvertFormat(this,output,format);
        c.execute();
        //return c.outputPathway;
    }

    public String orderFilter(String output, int filter){
        Filter f = new Filter(this, output, filter);
        f.execute();
        return f.outputPathway;
    }
}