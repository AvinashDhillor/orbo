package com.ad.analyse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.ad.Config;
import com.ad.analyse.notify.Notifications;
import com.ad.analyse.ocr.DetectPost;

import net.sourceforge.tess4j.TesseractException;

public class Analyse {

    public void start(BufferedImage bufferedImage, int canvasX, int canvasY, int cavasWidth, int canvasHeight)
            throws TesseractException, IOException, IllegalStateException {
        DetectPost detectPost = new DetectPost();
        BufferedImage dataImage = bufferedImage.getSubimage(canvasX, canvasY, cavasWidth, canvasHeight);
        String result = detectPost.relevent(dataImage);
        if (result != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");
            Date date = new Date();
            String name = formatter.format(date).toString();
            File file = new File(Config.saveToDir + name + "_[ " + result + " ]" + ".png");
            ImageIO.write(bufferedImage, "png", file);
            Notifications.notify(file);
        }
    }
}
