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
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by aaronplamondon on 2015-09-30.
 */

//int occurrences = Collections.frequency(arrayOfValues, playerNumber);

public class DataManager {
    private String fileName;
    private ArrayList<Number> arrayOfValues = new ArrayList<>();

    public DataManager(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Number> getArrayOfValues() {
        return arrayOfValues;
    }

    public void addValuesToArray(Number value) {
        arrayOfValues.add(0, value);
    }

    public void clearFile(Context context) {
        arrayOfValues.clear();
        this.saveToFile(context);
    }

    public void saveToFile(Context context) {
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

    public void loadFromFile(Context context) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Following line based on https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
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