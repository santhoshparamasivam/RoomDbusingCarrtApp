package com.example.mytask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressLint("CommitPrefEdits")
public class SessionManager extends Activity{

    SharedPreferences pref3;
    Editor editor3;

    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME3 = "CaspianTNB3";




    public static final String PENDING = "Product";

    // Constructor
    public SessionManager() {

    }

    public SessionManager(Context context) {
        this._context = context;

        pref3=_context.getSharedPreferences(PREF_NAME3,PRIVATE_MODE);
        editor3=pref3.edit();

    }



    public void addProduct(Productfile item) {
        List<Productfile> currentList;
        if (pref3.contains(PENDING)) {
            String jsonFavorites = pref3.getString(PENDING, null);
            Gson gson = new Gson();
            Productfile[] listItems = gson.fromJson(jsonFavorites, Productfile[].class);

            currentList = Arrays.asList(listItems);
            currentList = new ArrayList<>(currentList);
            currentList.add(0,item);
        } else {
            currentList = new ArrayList<>();
            currentList.add(item);
        }

        Gson gson = new Gson();
        String jsonItem = gson.toJson(currentList);
        editor3.putString(PENDING, jsonItem);
        editor3.commit();
    }
    public void RemoveProductList(int position){
        List<Productfile> currentList;
        if (pref3.contains(PENDING)) {
            String jsonFavorites = pref3.getString(PENDING, null);
            Gson gson = new Gson();
            Productfile[] listItems = gson.fromJson(jsonFavorites, Productfile[].class);
            currentList = Arrays.asList(listItems);
            currentList = new ArrayList<Productfile>(currentList);
            currentList.remove(position);
            Gson gson1 = new Gson();
            String jsonItem = gson1.toJson(currentList);
            editor3.putString(PENDING, jsonItem);
            editor3.commit();
        }

    }

    public void setProductList(List<Productfile> list) {

        Gson gson = new Gson();
        String jsonItem = gson.toJson(list);
        editor3.putString(PENDING, jsonItem);
        editor3.commit();
    }

    public ArrayList<Productfile> getProductList() {
        List<Productfile> items;
        if (pref3.contains(PENDING)) {
            String jsonFavorites = pref3.getString(PENDING, null);

            if(jsonFavorites != null) {
                Gson gson = new Gson();
                Productfile[] listItems = gson.fromJson(jsonFavorites,Productfile[].class);

                items = Arrays.asList(listItems);
                items = new ArrayList<Productfile>(items);
            }else{
                return null;
            }
        } else
            return null;

        return (ArrayList<Productfile>) items;
    }

}