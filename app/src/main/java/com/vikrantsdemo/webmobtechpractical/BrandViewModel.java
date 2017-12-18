package com.vikrantsdemo.webmobtechpractical;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.vikrantsdemo.webmobtechpractical.db.BrandList;

import java.util.List;

/**
 * Created by Vikrant on 17-12-2017.
 */

public class BrandViewModel extends AndroidViewModel {

    private BrandListRepository mRepository;

    private LiveData<List<BrandList>> mAllBrands;

    public BrandViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BrandListRepository(application);
        mAllBrands = mRepository.getAllWords();
    }

    LiveData<List<BrandList>> getAllBrands() {
        return mAllBrands;
    }

    public void insert(BrandList brandList) {
        mRepository.insert(brandList);
    }
}
