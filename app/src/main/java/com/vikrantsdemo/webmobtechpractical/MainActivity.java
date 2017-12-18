package com.vikrantsdemo.webmobtechpractical;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vikrantsdemo.webmobtechpractical.db.AppDatabase;
import com.vikrantsdemo.webmobtechpractical.db.BrandList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewBrandList;
    private FloatingActionButton fab;
    private Context context;
    private BrandViewModel brandViewModel;
    private Dialog addNewBrandDialog;
    private EditText edtName, edtDescription;
    private Button btnSave, btnCancel;
    private int lastId;
    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivity.this;
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        recyclerViewBrandList = findViewById(R.id.recyclerViewBrandList);
        final BrandAdapter brandAdapter = new BrandAdapter(this);
        recyclerViewBrandList.setAdapter(brandAdapter);
        recyclerViewBrandList.setLayoutManager(new LinearLayoutManager(this));

        brandViewModel = ViewModelProviders.of(this).get(BrandViewModel.class);
        brandViewModel.getAllBrands().observe(this, new Observer<List<BrandList>>() {
            @Override
            public void onChanged(@Nullable List<BrandList> brandLists) {
                brandAdapter.setList(brandLists);
                if (brandLists != null && brandLists.size() > 0) {
                    Log.e(TAG, "size : " + brandLists.size());
                    int size = brandLists.size() - 1;
                    lastId = Integer.parseInt(brandLists.get(size).getId());
                    Log.e(TAG, "LastId: " + lastId);

                    /*int maxIndex = brandLists.lastIndexOf("id");
                    lastId = Integer.parseInt(brandLists.get(maxIndex).getId());*/
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }

    @Override
    public void onClick(View view) {
        showAddNewBrandDialog();
    }

    private void showAddNewBrandDialog() {
        addNewBrandDialog = new Dialog(context);
        addNewBrandDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addNewBrandDialog.setContentView(R.layout.add_new_brand_dialog);
        addNewBrandDialog.setCancelable(true);
        addNewBrandDialog.show();

        Window window = addNewBrandDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mapAddNewBrandDialog(addNewBrandDialog);
    }

    private void mapAddNewBrandDialog(final Dialog addNewBrandDialog) {
        edtName = addNewBrandDialog.findViewById(R.id.edtName);
        edtDescription = addNewBrandDialog.findViewById(R.id.edtDescription);
        btnCancel = addNewBrandDialog.findViewById(R.id.btnCancel);
        btnSave = addNewBrandDialog.findViewById(R.id.btnSave);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBrandDialog.dismiss();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().length() > 0 && edtDescription.getText().toString().length() > 0) {
                    String newId = "" + (lastId + 1);
                    Log.e(TAG, "onClick: New Id " + newId);
                    @SuppressLint("SimpleDateFormat") String created_at = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    BrandList brandList = new BrandList(newId, edtName.getText().toString(), edtDescription.getText().toString(), created_at);
                    brandViewModel.insert(brandList);
                    addNewBrandDialog.dismiss();
                    Toast.makeText(context, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Please insert data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
