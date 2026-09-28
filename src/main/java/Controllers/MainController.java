package com.example.ca1dsa2;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


public class MainController implements Initializable {
    FileChooser fileChooser = new FileChooser();


    public MenuItem chooseImageButton;

    public ImageView imageMain;

    public Image selectedImage;

    public void chooseImageAction(ActionEvent event){
        File file = fileChooser.showOpenDialog(new Stage());

        if(file != null){
            Image image = new Image(file.toURI().toString());
            selectedImage = image;
            imageMain.setImage(image);

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

    public void adjustedImage(){
        imageMain.setEffect(colorAdjust);
    }



    //--------------------------------------------------------------------------------------//
    //--------------------------------------------------------------------------------------



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

    saturationAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());
    brightnessAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());
    hueAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());
    contrastAdjust.valueProperty().addListener((o, ov, nv) -> adjustImageColour());

    saturationAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    brightnessAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    hueAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());
    contrastAdjust.valueProperty().addListener((o, ov, nv) -> adjustedImage());



    }
}