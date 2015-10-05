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

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by aaronplamondon on 2015-10-02.
 */

/*
Calculatet the mean, median, max, min, and numer of instances of a player
number in a passed in data manager's arrayOfValues. Each function will
calculate only the number of values required.
 */

public class StatisticsCalculator {

    public String calculateMin(Integer numberOfValues, DataManager dataManager) {
        ArrayList<Long> arrayOfValues = dataManager.getArrayOfValues();
        ArrayList<Long> subList;
        Long minimumNumber = Long.MAX_VALUE;

        if (arrayOfValues.size() == 0) { return "No Data"; }

        // Change the number of values if the number of values needed is > the array size
        if (numberOfValues > arrayOfValues.size()) { numberOfValues = arrayOfValues.size(); }
        subList = new ArrayList<>(arrayOfValues.subList(0,numberOfValues));

        for (long value: subList) {
            if(value < minimumNumber) { minimumNumber = value;}
        }

        return Long.toString(minimumNumber) + "ms";
    }

    public String calculateMax(Integer numberOfValues, DataManager dataManager) {
        ArrayList<Long> arrayOfValues = dataManager.getArrayOfValues();
        ArrayList<Long> subList;
        Long maximumNumber = 0L;

        if (arrayOfValues.size() == 0) { return "No Data"; }

        // Change the number of values if the number of values needed is > the array size
        if (numberOfValues > arrayOfValues.size()) { numberOfValues = arrayOfValues.size(); }
        subList = new ArrayList<>(arrayOfValues.subList(0,numberOfValues));

        for (long value: subList) {
            if(value > maximumNumber) { maximumNumber = value;}
        }

        return Long.toString(maximumNumber) + "ms";
    }

    public String calculateMedian(Integer numberOfValues, DataManager dataManager) {
        ArrayList<Long> arrayOfValues = dataManager.getArrayOfValues();
        ArrayList<Long> subList;
        Long medianNumber;

        if (arrayOfValues.size() == 0) { return "No Data"; }

        // Change the number of values if the number of values needed is > the array size
        if (numberOfValues > arrayOfValues.size()) { numberOfValues = arrayOfValues.size(); }
        subList = new ArrayList<>(arrayOfValues.subList(0,numberOfValues));
        Collections.sort(subList);

        if (subList.size() % 2 == 0) {
            medianNumber = (subList.get(subList.size() / 2) + subList.get(subList.size() / 2 - 1)) / 2;
        } else {
            medianNumber = subList.get(subList.size() / 2);
        }

        return Long.toString(medianNumber) + "ms";
    }

    public String calculateMean(Integer numberOfValues, DataManager dataManager) {
        ArrayList<Long> arrayOfValues = dataManager.getArrayOfValues();
        ArrayList<Long> subList;
        Long meanNumber = 0L;

        if (arrayOfValues.size() == 0) { return "No Data"; }

        // Change the number of values if the number of values needed is > the array size
        if (numberOfValues > arrayOfValues.size()) { numberOfValues = arrayOfValues.size(); }
        subList = new ArrayList<>(arrayOfValues.subList(0,numberOfValues));

        for (long value : subList) {
            meanNumber += value;
        }

        return Long.toString(meanNumber/numberOfValues) + "ms";
    }

    public String numberOfBuzzes(Long playerNumber, DataManager dataManager) {
        ArrayList<Long> arrayOfValues = dataManager.getArrayOfValues();
        Integer occurrences = Collections.frequency(arrayOfValues, playerNumber);
        return Integer.toString(occurrences);
    }

}
