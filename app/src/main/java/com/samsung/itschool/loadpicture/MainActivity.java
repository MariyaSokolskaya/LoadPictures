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

    ArrayList<ImageView> imageViews = new ArrayList();
    AsyncImageLoader loader;

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
                if(loader == null || loader.getStatus() == AsyncTask.Status.FINISHED){
                    loader = new AsyncImageLoader();
                    loader.execute(imageUrls);
                    progress.setProgress(0);
                }else {
                    Toast.makeText(getApplicationContext(), "Подождите окончания загрузки",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setUpView(){
        for (int i = 0; i < imageRow.getChildCount(); i++) {
            imageViews.add((ImageView) imageRow.getChildAt(i));
        }
    }

    class AsyncImageLoader extends AsyncTask<String, Pair<Integer, Bitmap>, Void>{
        boolean isInternet = true;
        String messageError = "";
        @Override
        protected Void doInBackground(String... strings) {
            for (int i = 0; i < strings.length; i++) {
                try {
                    Bitmap image = getImageByURL(strings[i]);
                    Pair<Integer, Bitmap> pair = new Pair<>(i, image);
                    publishProgress(pair);
                } catch (MalformedURLException e){
                    //Toast.makeText(getApplicationContext(),"Неверный адрес", Toast.LENGTH_SHORT).show();
                    messageError = "Неверный адрес";
                    isInternet = false;
                    Pair<Integer, Bitmap> pair = new Pair<>(0, null);
                    publishProgress(pair);
                }catch (IOException e) {
                    //Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    messageError = e.getMessage();
                    isInternet = false;
                    Pair<Integer, Bitmap> pair = new Pair<>(0, null);
                    publishProgress(pair);
                }
            }
            return null;
        }

        private Bitmap getImageByURL(String url) throws MalformedURLException, IOException{
            Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            return image;
        }

        @Override
        protected void onProgressUpdate(Pair<Integer, Bitmap>... values) {
            super.onProgressUpdate(values);
            if(isInternet) {
                int position = values[0].first;//порядковый номер загруженной картинки
                int currentProgress = position + 1;//для ProgressBar
                Bitmap image = values[0].second;
                //обновляем прогресс-бар
                progress.setProgress(currentProgress * 100);
                //показ картинки вариант 1
                imageViews.get(position).setImageBitmap(image);
                //показ картинки вариант 2 (без предварительного массива)
                ((ImageView) imageRow.getChildAt(position)).setImageBitmap(image);
            }else {
                Toast.makeText(getApplicationContext(), messageError, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Загрука завершена", Toast.LENGTH_SHORT).show();
        }
    }

}
