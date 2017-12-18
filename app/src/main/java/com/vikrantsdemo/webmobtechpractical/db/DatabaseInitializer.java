package com.vikrantsdemo.webmobtechpractical.db;

/**
 * Created by Vikrant on 17-12-2017.
 */


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;


public class DatabaseInitializer {

    private static BrandList addNewBrand(final AppDatabase db, final String id, final String name, final String created_at, final String description) {
        BrandList brandList = new BrandList(id, name, description, created_at);
        db.brandListModel().insertBrand(brandList);
        return brandList;
    }
}
