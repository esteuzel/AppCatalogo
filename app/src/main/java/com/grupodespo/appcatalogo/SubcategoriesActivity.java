package com.grupodespo.appcatalogo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.adapters.categoriesAdapter;
import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Category;

import java.util.ArrayList;
import java.util.List;

public class SubcategoriesActivity extends AppCompatActivity {
    private List<Category> subcategories = new ArrayList();
    private List<Category> categoriesList = new ArrayList();
    private RecyclerView categoriesRecyclerView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int categorySelectedId = 0;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("categorySelectedId")) {
                categorySelectedId = extras.getInt("categorySelectedId");
            }
            if (extras.containsKey("categorySelectedName")) {
                String categorySelectedName = extras.getString("categorySelectedName");
                this.setTitle(categorySelectedName);
            }
        }

        categoriesRecyclerView = (RecyclerView) findViewById(R.id.recycler_category_list);
        categoriesRecyclerView.setHasFixedSize(true);

        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        categoriesAdapter = new categoriesAdapter(subcategories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        getSubCategories(categorySelectedId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getSubCategories(int categorySelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(SubcategoriesActivity.this,null,null,0);
        categoriesList = db.getCategories(categorySelectedId);
        for(int i=0; i<categoriesList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categories.get(i));
            subcategories.add(categoriesList.get(i));
        }
        if(subcategories.size()>0) {
            Toast.makeText(this, "Subcategorías cargadas", Toast.LENGTH_SHORT).show();
            //textViewWelcome.setText("");
        }else{
            Toast.makeText(this, "No hay subcategorías para esta categoría", Toast.LENGTH_SHORT).show();
        }
    }

}
