package com.hitachi_tstv.samsen.tunyaporn.nasadailyimage;

import android.graphics.Bitmap;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by tunyaporns on 7/23/2016.
 */
public class IotdHandler extends DefaultHandler {
    //Explicit
    private String url = "http://www.nasa.gov/rss/image_of_the_day.rss";
    private boolean inUrlABoolean = false;
    private boolean inTitleABoolean = false;
    private boolean inItemABoolean = false;
    private boolean inDescriptionABoolean = false;
    private boolean inDateABoolean = false;
    private Bitmap imageBitmap = null;
    private String titleString = null;
    private StringBuffer descriptionStringBuffer = new StringBuffer();
    private String dateString = null;

    public void processFeed() {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(this);
            InputStream inputStream = new URL(url).openStream();
            xmlReader.parse(new InputSource(inputStream));
        } catch (Exception e) {
        }
    }

    private Bitmap getBitmap(String url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setDoInput(true);

        } catch (Exception e) {

        }
    }
}
