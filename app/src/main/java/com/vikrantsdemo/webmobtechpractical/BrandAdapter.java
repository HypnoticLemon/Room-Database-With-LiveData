package com.vikrantsdemo.webmobtechpractical;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vikrantsdemo.webmobtechpractical.db.BrandList;

import java.util.List;

/**
 * Created by Vikrant on 17-12-2017.
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandListRowHolder> {

    private Context context;
    private List<BrandList> mBrandLists;

    public BrandAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BrandListRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brand_list_row_holder, parent, false);
        BrandListRowHolder brandListRowHolder = new BrandListRowHolder(view);
        return brandListRowHolder;
    }

    @Override
    public void onBindViewHolder(BrandListRowHolder holder, int position) {
        if (mBrandLists != null) {
            BrandList brandList = mBrandLists.get(position);
            holder.textViewName.setText(brandList.getName());
            holder.textViewDescription.setText(brandList.getDescription());
        }
    }

    void setList(List<BrandList> list) {
        mBrandLists = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return (mBrandLists != null ? mBrandLists.size() : 0);
    }

    public class BrandListRowHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewDescription;

        public BrandListRowHolder(View itemView) {
            super(itemView);

            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewName = itemView.findViewById(R.id.textViewName);
        }
    }
}
