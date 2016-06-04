package com.maxclay;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.w3c.dom.Document;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.converter.Converter;

@SuppressWarnings("restriction")
public class Main extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Cauchy problem solving");
        initRootLayout();
	}
	
	public static ImageView loadDefaultFunctionImage() {
    	
    	DOMParser parser = new DOMParser();
    	ImageView imageView = null;
	    try {
	    	
			parser.parse("resources/DefaultFunction.xml");
			Document doc = parser.getDocument();
			Converter jeuclidConverter = Converter.getInstance();
			imageView = getImageViewFromBuffuredImage(jeuclidConverter.render(doc, LayoutContextImpl.getDefaultLayoutContext()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return imageView;
    }

	public void initRootLayout() {
		
        try {
        	
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		loadDefaultFunctionImage();
		launch(args);
	}
	
	public static ImageView getImageViewFromBuffuredImage(BufferedImage bf) {
		
		WritableImage wr = null;
        if (bf != null) {
        	
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            
            for (int x = 0; x < bf.getWidth(); x++)
                for (int y = 0; y < bf.getHeight(); y++)
                    pw.setArgb(x, y, bf.getRGB(x, y));
             
        }
         
        return new ImageView(wr);
	}

}
