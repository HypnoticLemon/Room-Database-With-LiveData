package com.vikrantsdemo.webmobtechpractical.db;

/**
 * Created by Vikrant on 17-12-2017.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface BrandListDao {

    @Query("select * from brandList")
    LiveData<List<BrandList>> loadAllBrands();

    @Query("select * from brandList where id = :id")
    BrandList loadBrandById(int id);

    @Insert(onConflict = IGNORE)
    void insertBrand(BrandList brandList);


    @Query("DELETE FROM brandList")
    void deleteAll();
}
