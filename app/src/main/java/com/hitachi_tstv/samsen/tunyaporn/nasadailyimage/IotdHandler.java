package com.hitachi_tstv.samsen.tunyaporn.nasadailyimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
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
        } catch (IOException e) {
            Log.e("TAG", e.toString());
        } catch (SAXException e) {
            Log.e("TAG2", e.toString());
        } catch (ParserConfigurationException e) {
            Log.e("TAG3", e.toString());
        }
    }

    private Bitmap getBitmap(String url) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            Log.e("TAG", e.toString());
            return null;
        }
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("url")) {
            inUrlABoolean = true;
        } else {
            inUrlABoolean = false;
        }

        if (localName.startsWith("item")) {
            inItemABoolean = true;
        } else if (inItemABoolean) {
            if (localName.equals("title")) {
                inTitleABoolean = true;
            } else {
                inTitleABoolean = false;
            }

            if (localName.equals("description")) {
                inDescriptionABoolean = true;
            } else {
                inDescriptionABoolean = false;
            }

            if (localName.equals("pubDate")) {
                inDateABoolean = true;
            } else {
                inDateABoolean = false;
            }

            if (imageBitmap == null) {
                Log.d("Value", "image-url : " + attributes.getValue("url"));
                imageBitmap = getBitmap(attributes.getValue("url"));
            }
        }
    }

    public void characters(char ch[], int start, int length) {
        String chars = new String(ch).substring(start, start + length);

        if (inTitleABoolean && titleString == null) {
            titleString = chars;
            Log.d("Value", "Title : " + titleString);
        }
        if (inDateABoolean && dateString == null) {
            dateString = chars;
            Log.d("Value", "Date : " + dateString);
        }
        if (inDescriptionABoolean) {
            descriptionStringBuffer.append(chars);
            Log.d("Value", "Description : " + descriptionStringBuffer);
        }
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public String getTitleString() {
        return titleString;
    }

    public StringBuffer getDescriptionStringBuffer() {
        return descriptionStringBuffer;
    }

    public String getDateString() {
        return dateString;
    }


}



