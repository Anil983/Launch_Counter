package com.anil.applaunchcounter;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView countTextView;
    int count;
    final int DEFAULT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countTextView = (TextView) findViewById(R.id.countTextView);
        ///SAVE CSV FILE
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File CsvFile = new File(path, "");

        FileWriter writer = null;

        SharedPreferences sharedPreferences = this.getSharedPreferences("AppLaunchData", MODE_PRIVATE);

        if (sharedPreferences.getInt("count", DEFAULT) == DEFAULT) {
            // if app runs first time
            Log.i("1234", "App runs first time");
            count = 1;

        } else {
            Log.i("1234", "App runs more then 1 times");
            count = sharedPreferences.getInt("count", DEFAULT);// default value
            count++;


        }
        sharedPreferences.edit().putInt("count", count).apply();
        countTextView.setText(String.valueOf(sharedPreferences.getInt("count", DEFAULT))); // DEFAULT value



        try {
            writer = new FileWriter(path + "/csvfile.csv");  //create a file called csvfile.csv in download folder
            writer.append("Launch count");
            writer.append("\n"+ count);   //inserting count in csv file
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


}