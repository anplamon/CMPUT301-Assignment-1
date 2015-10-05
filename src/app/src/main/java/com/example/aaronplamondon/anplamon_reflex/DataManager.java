/**********************************************
 This file is part of anplamon-reflex.

 anplamon-reflex is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 anplamon-reflex is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 Copyright (c) 2015 Aaron Plamondon

 You should have received a copy of the GNU General Public License
 along with anplamon-reflex.  If not, see <http://www.gnu.org/licenses/>.
 **********************************************/

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


/*
The data manager is used to load, save, and clear data from a file. It first loads data
in from filename and stores it in its arrayOfValues. New values can be added and removed
from this array. Once data has been finished adding then the array is resaved to the
fileName. The data manager only works for one file at a time.
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

    // The followinh save and load are public domain written by Dr. Abram Hindle
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
