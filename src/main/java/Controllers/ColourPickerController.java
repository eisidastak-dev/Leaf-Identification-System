package Controllers;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ColourPickerController implements Initializable {

    private MainController mainController;

    public void setMainController(MainController controller){
        this.mainController = controller;
    }

    @FXML
    private ImageView colourPickerImageView;
    public Image currentImage;

    public void setImage(Image image){
        colourPickerImageView.setImage(image);
        currentImage = image;
    }


    public ToggleButton toggleButton;
    public TextField redText;
    public TextField blueText;
    public TextField greenText;
    public Rectangle colourRectangle;

    public void getPixelColour(MouseEvent event){
        if(toggleButton.isSelected()) {
            double imageWidth = currentImage.getWidth();
            double imageHeight = currentImage.getHeight();

            double viewWidth = colourPickerImageView.getBoundsInLocal().getWidth();
            double viewHeight = colourPickerImageView.getBoundsInLocal().getHeight();

            double xPos1 = event.getX();
            double yPos1 = event.getY();

            int xPos2 = (int) (xPos1 * imageWidth / viewWidth);
            int yPos2 = (int) (yPos1 * imageHeight / viewHeight);

            Color color = currentImage.getPixelReader().getColor(xPos2, yPos2);

            int r = (int) (color.getRed() * 255);
            int g = (int) (color.getGreen() * 255);
            int b = (int) (color.getBlue() * 255);

            redText.setText("Red is:" + r);
            greenText.setText("Green is: " + g);
            blueText.setText("Blue is: " + b);
            colourRectangle.setFill(color);

            if(mainController != null){
                mainController.setRedTextField(String.valueOf(r));
                mainController.setGreenTextField(String.valueOf(g));
                mainController.setBlueTextField(String.valueOf(b));
                mainController.setSquareColor(color);
                mainController.setColourVariables(r,g,b);
                mainController.createBlackWhiteImage();
            }

        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
