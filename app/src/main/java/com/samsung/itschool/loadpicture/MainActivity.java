package com.samsung.itschool.loadpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TableRow imageRow;
    Button loadButton;
    ProgressBar progress;

    public String[] imageUrls = {
            "https://img1.fonwall.ru/o/ns/galaxy-nebula-stars-bokeh.jpeg",
            "https://img1.fonwall.ru/o/pi/blue-nebula-galaxy-stars-gas-cloud.jpeg",
            "https://img1.fonwall.ru/o/bq/space-atmosphere-space-kcep.jpg"
    };

    ArrayList<ImageView> imageView = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRow = findViewById(R.id.imageRow);
        loadButton = findViewById(R.id.loadButton);
        progress = findViewById(R.id.progress);

        setUpView();

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                for (int i = 0; i < imageUrls.length; i++) {
                    try {
                        Bitmap image = getImageByURL(imageUrls[i]);
                    } catch (MalformedURLException e){
                        Toast.makeText(getApplicationContext(), "Неверный адрес", Toast.LENGTH_SHORT).show();
                    }catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }*/

            }
        });


    }

    private void setUpView(){
        for (int i = 0; i < imageRow.getChildCount(); i++) {
            imageView.add((ImageView) imageRow.getChildAt(i));
        }
    }

    class AsyncImageLoader extends AsyncTask<String, Pair<Integer, Bitmap>, Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            //TODO вся работа параллельного потока
            return null;
        }

        @Override
        protected void onProgressUpdate(Pair<Integer, Bitmap>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private Bitmap getImageByURL(String url)throws MalformedURLException, IOException{
            Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            return image;
        }
    }
}
