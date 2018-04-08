package com.grupodespo.appcatalogo;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

import com.grupodespo.appcatalogo.adapters.categoriesAdapter;

import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.models.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Category> categories = new ArrayList();
    private List<Category> categoriesList = new ArrayList();
    private RecyclerView categoriesRecyclerView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;
    private TextView textViewWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoriesRecyclerView = (RecyclerView) findViewById(R.id.recycler_category_list);
        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoriesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        // specify an adapter (see also next example)
        categoriesAdapter = new categoriesAdapter(categories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        int categorySelectedId = 0;
        /*  obtener Categorías  */
        getCategories(categorySelectedId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                goConfiguration();
            }
        });
    }

    private void getCategories(int categorySelectedId) {
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(MainActivity.this,null,null,0);
        categoriesList = db.getCategories(0);
        for(int i=0; i<categoriesList.size(); i++) {
            //Log.i("MAIN", "categoriesList: "  + categories.get(i));
            categories.add(categoriesList.get(i));
        }
        if(categories.size()>0) {
            textViewWelcome.setText("");
        }
        //categoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ConfigurationActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void goConfiguration() {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        this.startActivity(intent);
    }
}
