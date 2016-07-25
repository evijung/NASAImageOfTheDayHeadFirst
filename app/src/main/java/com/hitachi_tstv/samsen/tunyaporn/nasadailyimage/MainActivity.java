package com.hitachi_tstv.samsen.tunyaporn.nasadailyimage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Explicit
    IotdHandler iotdHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iotdHandler = new IotdHandler();
        new MyTask().execute();


    }

    public class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            iotdHandler.processFeed();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resetDisplay(iotdHandler.getTitleString(),iotdHandler.getDateString(),iotdHandler.getImageBitmap(),iotdHandler.getDescriptionStringBuffer());
            super.onPostExecute(aVoid);
        }
    }

    private void resetDisplay(String title, String date,
                              Bitmap imageUrl, StringBuffer description) {

        TextView titleView = (TextView)findViewById(R.id.txtTitle);

        titleView.setText(title);

        TextView dateView = (TextView)findViewById(R.id.txtDate);

        dateView.setText(date);

        ImageView imageView =(ImageView)findViewById(R.id.imageView);

        imageView.setImageBitmap(imageUrl);

        TextView descriptionView = (TextView)findViewById(R.id.txtDescription);

        descriptionView.setText(description);


    }
}

