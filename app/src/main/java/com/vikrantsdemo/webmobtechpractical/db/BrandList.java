package com.vikrantsdemo.webmobtechpractical.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Vikrant on 17-12-2017.
 */

@Entity
public class BrandList {

    @PrimaryKey
    @NonNull
    public String id;

    public String name;

    public String description;

    public String created_at;


    public BrandList(@NonNull String id, String name, String description, String created_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCreated_at() {
        return created_at;
    }
}
