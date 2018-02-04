package com.example.aaronbrecher.todos;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.aaronbrecher.todos.data.TodoContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aaronbrecher on 2/4/18.
 */

public class Utils {

    /**
     * Format the date from a unixDate to a String readable date
     * @param unixDate the number representing seconds etc
     * @return a formatted date string
     */
    public static String formatDate(long unixDate){
        String dateString;

        Date date = new Date(unixDate * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        dateString = sdf.format(date);
        return dateString;
    }

    /**
     * Simple function to determine the english word for the category, uses constants
     * defined in the TODOS Contract
     * @param cat the number representation of the category
     * @return The english word associated to that number
     */
    public static String getCategoryString(int cat){
        String category;
        switch (cat){
            case TodoContract.TodosEntry.CATEGORY_FINANCES:
                category = "Finance";
                break;
            case TodoContract.TodosEntry.CATEGORY_FIXING:
                category = "Fixing";
                break;
            case TodoContract.TodosEntry.CATEGORY_HOME:
                category = "Home";
                break;
            case TodoContract.TodosEntry.CATEGORY_SHOPPING:
                category = "Shopping";
                break;
            case TodoContract.TodosEntry.CATEGORY_WORK:
                category = "Work";
                break;
            case TodoContract.TodosEntry.CATEGORY_OTHER:
            default:
                category = "Other";
        }
        return category;
    }

    /**
     * get color for a specific category
     * @param category the category number to find the colors
     * @param context the context within which to get the colors
     * @return the color in hexidecimal format
     */
    public static int getColor( int category, Context context){
        int resourceColor;
        switch (category){
            case TodoContract.TodosEntry.CATEGORY_FINANCES:
                resourceColor = R.color.categoryColorFinance;
                break;
            case TodoContract.TodosEntry.CATEGORY_FIXING:
                resourceColor = R.color.categoryColorFixing;
                break;
            case TodoContract.TodosEntry.CATEGORY_HOME:
                resourceColor = R.color.categoryColorHome;
                break;
            case TodoContract.TodosEntry.CATEGORY_SHOPPING:
                resourceColor = R.color.categoryColorShopping;
                break;
            case TodoContract.TodosEntry.CATEGORY_WORK:
                resourceColor = R.color.categoryColorWork;
                break;
            case TodoContract.TodosEntry.CATEGORY_OTHER:
            default:
                resourceColor = R.color.categoryColorOther;
        }
        return ContextCompat.getColor(context, resourceColor);
    }
}
