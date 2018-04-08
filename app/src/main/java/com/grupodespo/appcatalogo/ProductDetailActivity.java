package com.grupodespo.appcatalogo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {
    public int subcategorySelectedId = 0;
    public String subcategorySelectedName;
    SharedPreferences preferencias;
    int productSelectedId = 0;
    String productSelectedName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("productSelectedId")) {
                productSelectedId = extras.getInt("productSelectedId");
            }
            if (extras.containsKey("productSelectedName")) {
                productSelectedName = extras.getString("productSelectedName");
                this.setTitle(productSelectedName);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Producto agregado al carrito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
