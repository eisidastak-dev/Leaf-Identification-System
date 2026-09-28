package Controllers;

import UnionFind.UnionFind;
import com.example.ca1dsa2.AutumnLeaveSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;




public class MainController implements Initializable {
    //---------------------------------Changing Scenes---------------------------------------
    //---------------------------------------------------------------------------------------
    public Button colourScene;

    public void changeToColourPickerScene(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(AutumnLeaveSystem.class.getResource("ColourPicker.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Colour Picker Screen");
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setMinWidth(618);
            stage.setMaxWidth(618);
            stage.setMinHeight(600);
            stage.setMaxHeight(600);

            ColourPickerController controller = fxmlLoader.getController();
            controller.setImage(adjustedImage);

            controller.setMainController(this);

        } catch (Exception e){
            System.out.println("Can't load new window");
        }
    }


    //----------------------------------File Chooser-----------------------------------------
    //---------------------------------------------------------------------------------------
    FileChooser fileChooser = new FileChooser();


    public MenuItem chooseImageButton;

    public ImageView imageMain;
    public ImageView leafDetectionImageView;

    public Image selectedImage;

    public Image getCurrentImage(){
        return imageMain.getImage();
    }

    public void chooseImageAction(ActionEvent event){
        File file = fileChooser.showOpenDialog(new Stage());

        if(file != null){
            Image image = new Image(file.toURI().toString());
            selectedImage = image;
            imageMain.setImage(image);
            leafDetectionImageView.setImage(image);
            adjustedImage = imageMain.getImage();
            blackWhiteImageView.setImage(null);

        }

    }

    //--------------------------------------SLIDER METHODS--------------------------------
    //------------------------------------------------------------------------------------

    public Slider saturationAdjust;
    public Slider brightnessAdjust;
    public Slider hueAdjust;
    public Slider contrastAdjust;

    public TextField saturationText;
    public TextField brightnessText;
    public TextField hueText;
    public TextField contrastText;



    public double getSaturationAdjust(){
        return (int) saturationAdjust.getValue();
    }

    public double getBrightnessAdjust(){
        return (int) brightnessAdjust.getValue();
    }

    public double getHueAdjust(){
        return (int) hueAdjust.getValue();
    }

    public double getContrastAdjust(){
        return (int) contrastAdjust.getValue();
    }

    public void setSaturationText(){
        String text = Double.toString(getSaturationAdjust());
        saturationText.setText(text);
    }

    public void setBrightnessText(){
        String text = Double.toString(getBrightnessAdjust());
        brightnessText.setText(text);
    }

    public void setHueText(){
        String text = Double.toString(getHueAdjust());
        hueText.setText(text);

    }

    public void setContrastText(){
        String text = Double.toString(getContrastAdjust());
        contrastText.setText(text);
    }

    public Button resetButton;

    public void resetColourToDefault(){
        colorAdjust.setContrast(0);
        colorAdjust.setSaturation(0);
        colorAdjust.setBrightness(0);
        colorAdjust.setHue(0);
        adjustedImage();
    }


    //------------------------------------Color Adjust--------------------------------------
    //--------------------------------------------------------------------------------------

    ColorAdjust colorAdjust = new ColorAdjust();


    public double colorAdjustInput(double color){
            return (color/50) - 1;
    }

    public void adjustImageColour(){
        colorAdjust.setContrast(colorAdjustInput(getContrastAdjust()));
        colorAdjust.setBrightness(colorAdjustInput(getBrightnessAdjust()));
        colorAdjust.setHue(colorAdjustInput(getHueAdjust()));
        colorAdjust.setSaturation(colorAdjustInput(getSaturationAdjust()));
    }

    public Image adjustedImage;

    public void adjustedImage(){
        imageMain.setEffect(colorAdjust);
        adjustedImage = imageMain.snapshot(null,null);

    }



    //-----------------------------R.G.B TextFields--------------------------------------------//
    //-----------------------------------------------------------------------------------------

    public TextField redTextField;
    public TextField greenTextField;
    public TextField blueTextField;
    public Rectangle mainScreenRectangle;

    public int red;
    public int green;
    public int blue;


    public void setRedTextField(String text){
        redTextField.setText(text);
    }

    public void setGreenTextField(String text){
        greenTextField.setText(text);
    }

    public void setBlueTextField(String text){
        blueTextField.setText(text);
    }

    public void setSquareColor(Color color){
        mainScreenRectangle.setFill(color);
    }

    public void setColourVariables(int r, int g, int b){
        red = r;
        green = g;
        blue = b;
    }


    //-------------------------------BLACK/WHITE Image Conversion------------------------------//
    //-----------------------------------------------------------------------------------------//

    public Slider toleranceSlider;
    public TextField toleranceTextField;
    public ImageView blackWhiteImageView;

    public int getToleranceSlider(){
        return (int) toleranceSlider.getValue();
    }

    public void setToleranceTextField(){
        String text = Double.toString(getToleranceSlider());
        toleranceTextField.setText(text);
    }


    public void createBlackWhiteImage(){
        Image image = adjustedImage;
        Image blackWhiteImage = createBlackAndWhiteImage(getToleranceSlider(),red,green,blue,image);
        blackWhiteImageView.setImage(blackWhiteImage);

    }


    public WritableImage createBlackAndWhiteImage(int tolerance,int r, int g, int b, Image image){

        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();

        WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();


        for(int x = 0; x < imageWidth; x++){
            for(int y = 0; y < imageHeight; y++){

                Color color = reader.getColor(x,y);

                int currentRed = (int)(color.getRed() * 255);
                int currentGreen = (int)(color.getGreen() * 255);
                int currentBlue = (int)(color.getBlue() * 255);

                if (Math.abs(currentRed - r) < tolerance && Math.abs(currentGreen - g) < tolerance && Math.abs(currentBlue - b) < tolerance) {
                    writer.setColor(x, y, Color.WHITE);
                } else {
                    writer.setColor(x, y, Color.BLACK);
                }

            }
        }

    return writableImage;
    }

    //--------------------------------------COUNTING LEAVES ---------------------------
    //---------------------------------------------------------------------------------

    public Button countLeafButton;
    public TextField minimumText;
    public TextField maximumText;

    public TextField clusterMinText;
    public TextField clusterAmountField;
    public TextField leafAmountField;




    public void countLeavesAndShowSquares(ActionEvent event) {

        Image blackWhiteImage = blackWhiteImageView.getImage();

        if (blackWhiteImage == null) {
            System.out.println("Create the black and white image first.");
            return;
        }

        leafPane.getChildren().removeIf(node -> "leafBox".equals(node.getId()));

        int minimumSize = Integer.parseInt(minimumText.getText());
        int maximumSize = Integer.parseInt(maximumText.getText());
        int clusterMinSize = Integer.parseInt(clusterMinText.getText());

        int width = (int) blackWhiteImage.getWidth();
        int height = (int) blackWhiteImage.getHeight();

        int size = width * height;

        UnionFind unionFind = new UnionFind(size);

        PixelReader reader = blackWhiteImage.getPixelReader();

        // STEP 1: Connect touching white pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color currentColor = reader.getColor(x, y);

                if (isWhitePixel(currentColor)) {

                    int currentIndex = y * width + x;

                    // Check right pixel
                    if (x + 1 < width) {
                        Color rightColor = reader.getColor(x + 1, y);

                        if (isWhitePixel(rightColor)) {
                            int rightIndex = y * width + (x + 1);
                            unionFind.union(currentIndex, rightIndex);
                        }
                    }

                    // Check bottom pixel
                    if (y + 1 < height) {
                        Color bottomColor = reader.getColor(x, y + 1);

                        if (isWhitePixel(bottomColor)) {
                            int bottomIndex = (y + 1) * width + x;
                            unionFind.union(currentIndex, bottomIndex);
                        }
                    }
                }
            }
        }

        // STEP 2: Arrays to store rectangle information
        int[] minX = new int[size];
        int[] minY = new int[size];
        int[] maxX = new int[size];
        int[] maxY = new int[size];
        int[] pixelCount = new int[size];

        for (int i = 0; i < size; i++) {
            minX[i] = width;
            minY[i] = height;
            maxX[i] = 0;
            maxY[i] = 0;
            pixelCount[i] = 0;
        }

        // STEP 3: Find the rectangle around each leaf group
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color currentColor = reader.getColor(x, y);

                if (isWhitePixel(currentColor)) {

                    int currentIndex = y * width + x;
                    int root = unionFind.find(currentIndex);

                    if (x < minX[root]) {
                        minX[root] = x;
                    }

                    if (y < minY[root]) {
                        minY[root] = y;
                    }

                    if (x > maxX[root]) {
                        maxX[root] = x;
                    }

                    if (y > maxY[root]) {
                        maxY[root] = y;
                    }

                    pixelCount[root]++;
                }
            }
        }

        // STEP 4: Show the original/adjusted image
        leafDetectionImageView.setImage(selectedImage);

        int leafCount = 0;
        int clusterCount = 0;

        // STEP 5: Add blue rectangles for valid leaf groups
        for (int i = 0; i < size; i++) {

            if (pixelCount[i] >= minimumSize && pixelCount[i] <= maximumSize) {
                if(pixelCount[i] >= clusterMinSize){
                    clusterCount++;
                }else{
                    leafCount++;
                }

                addBlueRectangle(minX[i], minY[i], maxX[i], maxY[i], leafCount);
            }
        }

        clusterAmountField.setText(Integer.toString(clusterCount));
        leafAmountField.setText(Integer.toString(leafCount));

        minimumText.clear();
        maximumText.clear();
        clusterMinText.clear();
    }

    public boolean isWhitePixel(Color color){
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        return r > 200 && g > 200 && b > 200;
    }

    public Pane leafPane;

    public void addBlueRectangle(int minX, int minY, int maxX, int maxY, int leafNumber) {

        Image detectionImage = blackWhiteImageView.getImage();

        double detectionWidth = detectionImage.getWidth();
        double detectionHeight = detectionImage.getHeight();

        double displayedWidth = leafDetectionImageView.getBoundsInLocal().getWidth();
        double displayedHeight = leafDetectionImageView.getBoundsInLocal().getHeight();

        double scaleX = displayedWidth / detectionWidth;
        double scaleY = displayedHeight / detectionHeight;

        double rectX = minX * scaleX;
        double rectY = minY * scaleY;
        double rectWidth = (maxX - minX) * scaleX;
        double rectHeight = (maxY - minY) * scaleY;

        Rectangle rectangle = new Rectangle();

        rectangle.setId("leafBox");
        rectangle.setX(rectX);
        rectangle.setY(rectY);
        rectangle.setWidth(rectWidth);
        rectangle.setHeight(rectHeight);

        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLUE);
        rectangle.setStrokeWidth(2);

        Text numberText = new Text(Integer.toString(leafNumber));

        numberText.setId("leafBox");
        numberText.setX(rectX);
        numberText.setY(rectY - 3);
        numberText.setFill(Color.BLUE);
        numberText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        leafPane.getChildren().add(rectangle);
        leafPane.getChildren().add(numberText);
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    saturationAdjust.setValue(50);
    brightnessAdjust.setValue(50);
    hueAdjust.setValue(50);
    contrastAdjust.setValue(50);

    saturationText.setText("50");
    brightnessText.setText("50");
    hueText.setText("50");
    contrastText.setText("50");

    saturationAdjust.valueProperty().addListener((o, ov, nv) -> setSaturationText());
    brightnessAdjust.valueProperty().addListener((o, ov, nv) -> setBrightnessText());
    hueAdjust.valueProperty().addListener((o, ov, nv) -> setHueText());
    contrastAdjust.valueProperty().addListener((o, ov, nv) -> setContrastText());
    toleranceSlider.valueProperty().addListener((o, ov, nv) -> setToleranceTextField());

    saturationAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());
    brightnessAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());
    hueAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());
    contrastAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());

    saturationAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    brightnessAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    hueAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    contrastAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    toleranceSlider.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    toleranceSlider.valueProperty().addListener((o, ov, nv) -> createBlackWhiteImage());




    }
}