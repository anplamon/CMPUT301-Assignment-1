package com.example.aaronplamondon.anplamon_reflex;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by aaronplamondon on 2015-09-30.
 */

public class DataManager {
    private Context context;
    private String fileName;
    private ArrayList<Long> arrayOfValues = new ArrayList<>();

    public DataManager(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public ArrayList<Long> getArrayOfValues() {
        return arrayOfValues;
    }

    public void addValuesToArray(Long value) {
        arrayOfValues.add(0, value);
    }

    public void clearFile() {
        arrayOfValues.clear();
        this.saveToFile();
    }

    public void saveToFile() {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(arrayOfValues, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public void loadFromFile() {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type listType = new TypeToken<ArrayList<Long>>() {}.getType();
            arrayOfValues = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            arrayOfValues = new ArrayList<>();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

}
