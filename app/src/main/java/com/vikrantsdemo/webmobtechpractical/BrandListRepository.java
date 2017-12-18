package com.vikrantsdemo.webmobtechpractical;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.vikrantsdemo.webmobtechpractical.db.AppDatabase;
import com.vikrantsdemo.webmobtechpractical.db.BrandList;
import com.vikrantsdemo.webmobtechpractical.db.BrandListDao;

import java.util.List;

/**
 * Created by Vikrant on 17-12-2017.
 */

public class BrandListRepository {


    private BrandListDao brandListDao;
    private LiveData<List<BrandList>> mAllBrands;

    BrandListRepository(Application application) {
        AppDatabase db = AppDatabase.getInMemoryDatabase(application);
        brandListDao = db.brandListModel();
        mAllBrands = brandListDao.loadAllBrands();
    }

    LiveData<List<BrandList>> getAllWords() {
        return mAllBrands;
    }


    public void insert(BrandList brandList) {
        new insertAsyncTask(brandListDao).execute(brandList);
    }

    private static class insertAsyncTask extends AsyncTask<BrandList, Void, Void> {

        private BrandListDao mAsyncTaskDao;

        insertAsyncTask(BrandListDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(BrandList... brandLists) {
            mAsyncTaskDao.insertBrand(brandLists[0]);
            return null;
        }
    }
}
