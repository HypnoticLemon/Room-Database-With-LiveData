package com.vikrantsdemo.webmobtechpractical.db;

/**
 * Created by Vikrant on 17-12-2017.
 */

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vikrantsdemo.webmobtechpractical.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


@Database(entities = {BrandList.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private String TAG = AppDatabase.class.getSimpleName();
    private static AppDatabase INSTANCE;

    public abstract BrandListDao brandListModel();


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    String url = "http://appsdata2.cloudapp.net/demo/androidApi/list.php";
                    getBrandList(url);
                }
            };


    public static AppDatabase getInMemoryDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    .allowMainThreadQueries()
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    private static void getBrandList(String url) {

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("AppDatabase", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("error_code") == 1) {
                        JSONArray brandListArray = jsonObject.getJSONArray("brand_list");
                        for (int i = 0; i < brandListArray.length(); i++) {
                            JSONObject brandListObject = (JSONObject) brandListArray.get(i);
                            BrandList brandList = new BrandList(brandListObject.getString("id"), brandListObject.getString("name"), brandListObject.getString("description"), brandListObject.getString("created_at"));
                            INSTANCE.brandListModel().insertBrand(brandList);
                        }
                    }
                } catch (Exception e) {
                    Log.e("AppDatabase", "onResponse: " + e.getMessage());
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e("AppDatabase", "onErrorResponse: " + volleyError.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request, "BrandList");
    }
}