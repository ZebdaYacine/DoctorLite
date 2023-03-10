/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorlite.BackEnd.ReceiptPrinter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SamplesCommon {

    public enum sampleImages {
        github("github.png")
        ,dog("dog.png")
        ;
        public String imageName;
        sampleImages(String imageName){
            this.imageName = imageName;
        }
    }


    public static BufferedImage getImage(sampleImages image) throws IOException {
        URL url = getURL(image.imageName);
        return ImageIO.read(url);
    }

    public static BufferedImage getImage(String str) throws IOException {
        URL url = getURL(str);
        return ImageIO.read(url);
    }


    private static URL getURL(String imageName){
        String strPath =  "images/" +  imageName;
        return SamplesCommon.class
                .getClassLoader()
                .getResource(strPath);
    }

}