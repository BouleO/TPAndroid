package bao.flickrapp;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GetImageOnClickListener implements View.OnClickListener {

    private AppCompatActivity mainAct;

    public GetImageOnClickListener(AppCompatActivity mainAct){
        this.mainAct = mainAct;
    }

    @Override
    public void onClick(View v) {

        AsyncFlickrJSONData asyncTask = new AsyncFlickrJSONData(mainAct);
        asyncTask.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");

    }
}
