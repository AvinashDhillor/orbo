package com.ad.analyse.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.ad.Config;
import com.ad.utils.Logger;

public class DetectPost {

    ITesseract instance;

    public DetectPost() throws NullPointerException {
        instance = new Tesseract();
        instance.setDatapath("src\\main\\resources\\com\\ad\\tessdata");
    }

    public String relevent(BufferedImage bf) throws TesseractException {
        String result = instance.doOCR(bf);
        ArrayList<String> keys = Config.lookupWords;
        for (String i : keys) {
            if (result.toLowerCase().contains(i.toLowerCase())) {
                Logger.log("Found : " + i);
                return i;
            }
        }
        return null;
    }

}
