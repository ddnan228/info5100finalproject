import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageConvertTool extends Application {
    //private controllers in file choose part
    private Label inputTF;
    private Button btnFileSingle;
    private Button btnFileMulti;
    private Button btnClearInput;

    //private controllers in outout path choose part
    private Button btnChooseOutput;
    private Label outputPath;
    private Button btnClearOutput;
    private String outPathStr;
    private String defaultOutputStr = "Same as Input";

    //private controllers in Format Convert part
    private ChoiceBox formatChoice;
    private Button convertFormat;

    //private controllers in show information part
    private Button btnShowInfo;
    private Label[] infoArray;
    private ImageView[] imageViewsArray;
    private String defaultThumbnail = "imageDefault/thumbnailDefault.jpg";
    private Image defThum;

    //private controllers in filter part
    private Button filterPre;
    private ImageView[] filtersIV;
    private ImageView orignalImage;
    private Button[] btnFilters;
    private Button btnFileter1;
    private Button btnFileter2;
    private Button btnFileter3;
    private Button btnFileter4;
    private Image defOrignal;
    private Image defFilter1;
    private Image defFilter2;
    private Image defFilter3;
    private Image defFilter4;

    private Button btnOpenFolder;

    //default content in Label inputTF
    private String defStr = "--- Please Select 1 ~ 5 Image Files ---";
    private ArrayList<ImageMaker> imagesFiles;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws FileNotFoundException {
        initialize();

        //set event handler
        btnFileSingle.setOnAction(new btnChooseSingleClick());
        btnFileMulti.setOnAction(new btnChooseMultiClick());
        btnClearInput.setOnAction(new btnClearInputClick());
        btnChooseOutput.setOnAction(new btnChooseOutputClick());
        btnClearOutput.setOnAction(new btnClearOutputClick());
        btnShowInfo.setOnAction(new btnShowInfoClick());
        convertFormat.setOnAction(new btnConvertFormatClick());
        filterPre.setOnAction(new btnPreFiltersClick());
        btnFileter1.setOnAction(new btnFilterClick(1));
        btnFileter2.setOnAction(new btnFilterClick(2));
        btnFileter3.setOnAction(new btnFilterClick(3));
        btnFileter4.setOnAction(new btnFilterClick(4));
        btnOpenFolder.setOnAction(new openOutputFolderClick());

        //set configuration
        VBox inputField = new VBox(inputTF);

        HBox chooseBTN = new HBox(btnFileSingle,btnFileMulti,btnClearInput);
        chooseBTN.setSpacing(20);
        VBox sub1 = new VBox(inputField,chooseBTN);
        sub1.setAlignment(Pos.TOP_CENTER);
        sub1.setSpacing(20);
        sub1.setPadding(new Insets(20));

        VBox sub2 = new VBox(btnChooseOutput,outputPath,btnClearOutput,formatChoice,convertFormat);
        sub2.setSpacing(20);
        sub2.setAlignment(Pos.CENTER_LEFT);
        sub2.setPadding(new Insets(20));
        sub2.setStyle("-fx-border-width:0 0 0 1; -fx-border-color:gray; -fx-border-style: dotted");

        HBox rec1 = new HBox(sub1,sub2);
        rec1.setStyle("-fx-border-width:0 0 1 0; -fx-border-color:gray; -fx-border-style: dotted");

        GridPane rec2 = new GridPane();
        rec2.setHgap(30);
        rec2.setVgap(20);
        rec2.setPadding(new Insets(20));
        rec2.setAlignment(Pos.CENTER);
        rec2.add(btnShowInfo,0,0);
        rec2.add(imageViewsArray[0],0,1);
        rec2.add(imageViewsArray[1],1,1);
        rec2.add(imageViewsArray[2],2,1);
        rec2.add(imageViewsArray[3],3,1);
        rec2.add(imageViewsArray[4],4,1);
        rec2.add(infoArray[0],0,2);
        rec2.add(infoArray[1],1,2);
        rec2.add(infoArray[2],2,2);
        rec2.add(infoArray[3],3,2);
        rec2.add(infoArray[4],4,2);

        GridPane rec3 = new GridPane();
        rec3.setHgap(20);
        rec3.setVgap(20);
        rec3.setPadding(new Insets(20));
        rec3.setAlignment(Pos.TOP_CENTER);
        rec3.add(filterPre,0,0);
        rec3.add(orignalImage,1,0);
        rec3.add(filtersIV[0],0,1);
        rec3.add(filtersIV[1],1,1);
        rec3.add(btnFileter1,0,2);
        rec3.add(btnFileter2,1,2);
        rec3.add(filtersIV[2],0,3);
        rec3.add(filtersIV[3],1,3);
        rec3.add(btnFileter3,0,4);
        rec3.add(btnFileter4,1,4);

        VBox rec4 = new VBox(btnOpenFolder);
        rec4.setPadding(new Insets(30));
        rec4.setAlignment(Pos.CENTER);

        VBox conbination1 = new VBox(rec1,rec2);
        VBox conbination2 = new VBox(rec3,rec4);
        conbination2.setStyle("-fx-border-width:0 0 0 1; -fx-border-color:gray; -fx-border-style: dotted");
        HBox root = new HBox(conbination1,conbination2);

        Scene scene = new Scene(root,1100,600);
        primaryStage.setTitle("Image Convert Tool 1.0 by Nan & Chen");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void initialize(){
        imagesFiles = new ArrayList<ImageMaker>();

        inputTF = new Label(defStr);
        inputTF.setStyle("-fx-border-color: gray; -fx-background-color: white; -fx-border-width: 2");
        inputTF.setPrefHeight(200);
        inputTF.setPrefWidth(500);

        btnFileSingle = new Button("Select Single Image");
        btnFileMulti = new Button("Select Multiple Images");
        btnClearInput = new Button("Clear Input");

        btnChooseOutput = new Button("Select output Path");
        outPathStr = defaultOutputStr;
        outputPath = new Label(defaultOutputStr);
        outputPath.setStyle("-fx-border-color: gray");
        outputPath.setPrefWidth(200);
        outputPath.setPrefHeight(25);

        btnClearOutput = new Button("Clear Output");

        formatChoice = new ChoiceBox(FXCollections.observableArrayList("Choose format",
                        "JPEG","PNG","BMP","TIFF","HEIC","PDF","SVG"));
        convertFormat = new Button("Convert Format");
        formatChoice.getSelectionModel().selectFirst();
        convertFormat.setPrefHeight(50);
        convertFormat.setPrefWidth(200);

        btnShowInfo = new Button("Show Info");
        infoArray = new Label[5];
        for(int i = 0; i < 5; i++){
            infoArray[i] = new Label(" ");
            infoArray[i].setPrefWidth(100);
            infoArray[i].setPrefHeight(100);
            infoArray[i].setStyle("-fx-border-style: dotted; -fx-border-color: gray;-fx-font-size: 12;-fx-spacing: 10");
        }

        imageViewsArray = new ImageView[5];

        filtersIV = new ImageView[4];
        //btnFilters = new Button[4];
        filterPre = new Button("Preview Filters");
        btnFileter1 = new Button("Try Enhanced");
        btnFileter2 = new Button("Try Autumn");
        btnFileter3 = new Button("Try Pinky");
        btnFileter4 = new Button("Try Ocean");

        btnOpenFolder = new Button("Open Output Folder");
        btnOpenFolder.setPrefWidth(200);
        btnOpenFolder.setPrefHeight(50);

        try{
            defThum = new Image(new FileInputStream(defaultThumbnail));
            for(int i = 0; i < 5; i++){
                imageViewsArray[i] = new ImageView(defThum);
            }

            defOrignal = new Image(new FileInputStream("imageDefault/fileter00.jpg"));
            defFilter1 = new Image(new FileInputStream("imageDefault/fileter01.jpg"));
            defFilter2 = new Image(new FileInputStream("imageDefault/fileter02.jpg"));
            defFilter3 = new Image(new FileInputStream("imageDefault/fileter03.jpg"));
            defFilter4 = new Image(new FileInputStream("imageDefault/fileter04.jpg"));

            orignalImage = new ImageView(defOrignal);
            filtersIV[0] = new ImageView(defFilter1);
            filtersIV[1] = new ImageView(defFilter2);
            filtersIV[2] = new ImageView(defFilter3);
            filtersIV[3] = new ImageView(defFilter4);

        } catch (FileNotFoundException e){
            System.out.println("Exception at setDefaultImage");
        }
    }

    private void setDefaultImage(){
        for(int i = 0; i < 5; i++){
            imageViewsArray[i].setImage(defThum);
            infoArray[i].setText(" ");
        }
        orignalImage.setImage(defOrignal);
        filtersIV[0].setImage(defFilter1);
        filtersIV[1].setImage(defFilter2);
        filtersIV[2].setImage(defFilter3);
        filtersIV[3].setImage(defFilter4);

    }

    //set the "Select Single Image" button's function
    private class btnChooseSingleClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            FileChooser fc1 = new FileChooser();
            fc1.setTitle("Select Single Image File");
            fc1.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files",
                                        "*.jpeg", "*.jpg", "*.png", "*.bmp","*.heic","*.svg")
            );
            File selectedFile = fc1.showOpenDialog(null);
            if(selectedFile != null && imagesFiles.size() < 5){
                String pathway = selectedFile.getAbsolutePath();
                ImageMaker curr = new ImageMaker(pathway);
                imagesFiles.add(curr);

                String str = "";
                for(int i = 0; i < imagesFiles.size(); i++){
                    str = str + imagesFiles.get(i).getPathway() + "\n";
                }
                str = str + "\n--- " + imagesFiles.size() + " files have been selected ---";
                inputTF.setText(str);
            }
        }
    }
    //set the "Select Multiple Images" button's function
    private class btnChooseMultiClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            FileChooser fc2 = new FileChooser();
            fc2.setTitle("Select Multiple Image Files");
            fc2.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.jpeg", "*.jpg", "*.png", "*.bmp","*.tiff")
            );
            List<File> selectedFiles = fc2.showOpenMultipleDialog(null);

            if(selectedFiles != null && imagesFiles.size() < 5){
                if(imagesFiles.size() + selectedFiles.size() <= 5){
                    for(int i = 0; i <selectedFiles.size(); i++){
                        String pathway = selectedFiles.get(i).getAbsolutePath();
                        ImageMaker curr = new ImageMaker(pathway);
                        imagesFiles.add(curr);
                    }
                } else{
                    int space = imagesFiles.size();
                    for(int i = 0; i < 5 - space; i++){
                        String pathway = selectedFiles.get(i).getAbsolutePath();
                        ImageMaker curr = new ImageMaker(pathway);
                        imagesFiles.add(curr);
                    }
                }
                String str = "";
                for (int i = 0; i < imagesFiles.size(); i++) {
                    str = str + imagesFiles.get(i).getPathway() + "\n";
                }

                str = str + "\n--- " + imagesFiles.size() + " files have been selected ---";
                inputTF.setText(str);
            }

        }
    }

    //set the "Clear Input" button's function
    private class btnClearInputClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            imagesFiles.clear();
            inputTF.setText(defStr);
            setDefaultImage();

        }
    }

    //set the "Choose Output path" button's function
    private class btnChooseOutputClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            DirectoryChooser dc = new DirectoryChooser();
            dc.setTitle("Select OutPut Path");
            File f = dc.showDialog(null);
            if( f != null){
                outPathStr = f.getAbsolutePath() + "/";
                outputPath.setText(outPathStr);
            }
        }
    }

    //set the "Clear Output path" button's function
    private class btnClearOutputClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            outPathStr = defaultOutputStr;
            outputPath.setText(outPathStr);
        }
    }

    //set the "Convert Format" button's function
    private class btnConvertFormatClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            String format = formatChoice.getValue().toString();
            if(!imagesFiles.isEmpty()) {
                if (! format.equals("Choose format")) {
                    if (outPathStr.equals(defaultOutputStr)) {
                        String temp =imagesFiles.get(0).getPathway();
                        int index = temp.lastIndexOf("/");
                        outPathStr = temp.substring(0, index+1);
                    }
                    for(int i = 0; i < imagesFiles.size(); i++){
                        imagesFiles.get(i).orderConvertFormat(outPathStr,format);
                    }
                    String nowInTF = inputTF.getText();
                    nowInTF = nowInTF + "\n\n" + "Convert Format has been done!";
                    inputTF.setText(nowInTF);
                }
            }
        }
    }

    //set the "Show Info" button's function
    private class btnShowInfoClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            if(imagesFiles.size() > 0 && imagesFiles.size() <= 5){
                for(int i = 0; i <imagesFiles.size(); i++){
                    String currInfo = imagesFiles.get(i).orderInfo();
                    infoArray[i].setText(currInfo);
                    String thumbnailPath = imagesFiles.get(i).orderThumbnail();
                    try{
                        Image currThumbnail = new Image(new FileInputStream(thumbnailPath));
                        imageViewsArray[i].setImage(currThumbnail);

                    }catch (FileNotFoundException ex){
                        System.out.println("Exception at showInfo");
                    }

                }
            }
        }
    }

    //set the "Preview Filters" button's function
    private class btnPreFiltersClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            if(!imagesFiles.isEmpty()){
                ImageMaker currImage = imagesFiles.get(0);
                String s = currImage.orderThumbnail();
                try{
                    Image filtered= new Image(new FileInputStream(s));
                    orignalImage.setImage(filtered);
                } catch (FileNotFoundException ex){
                    System.out.println("Exception at Preview Filters setting orignalImage");
                }
                ImageMaker thumbnail = new ImageMaker(s);
                for(int i = 0; i < filtersIV.length; i++){
                    String path = thumbnail.orderFilter("thumbnailsTEMP/",i+1);
                    try{
                        Image filtered= new Image(new FileInputStream(path));
                        filtersIV[i].setImage(filtered);
                    } catch (FileNotFoundException ex){
                        System.out.println("Exception at Preview Filters");
                    }
                }
            }
        }
    }

    //set the "filter" button's function
    private class btnFilterClick implements EventHandler<ActionEvent>{
        private int filter;

        btnFilterClick(int f){
            filter = f;
        }

        public void handle(ActionEvent e){
            if(!imagesFiles.isEmpty()){
                if (outPathStr.equals(defaultOutputStr)) {
                    String temp =imagesFiles.get(0).getPathway();
                    int index = temp.lastIndexOf("/");
                    outPathStr = temp.substring(0, index+1);
                }
                for(int i = 0; i < imagesFiles.size(); i++){
                    String s = imagesFiles.get(i).orderFilter(outPathStr,filter);
                }
                String nowInTF = inputTF.getText();
                nowInTF = nowInTF + "\n\n" + "Using Filter has been done!";
                inputTF.setText(nowInTF);
            }
        }
    }

    //set the "Open Output Folder" button's function
    private class openOutputFolderClick implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e) {
            if (!imagesFiles.isEmpty()) {
                if (outPathStr.equals(defaultOutputStr)) {
                    String temp = imagesFiles.get(0).getPathway();
                    int index = temp.lastIndexOf("/");
                    outPathStr = temp.substring(0, index+1);
                }
                try{
                    Process process = Runtime.getRuntime().exec("open " + outPathStr);
                } catch (IOException ex){
                    System.out.println("Exception at Open Output Folder");
                }
            }
        }
    }
}
