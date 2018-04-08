package com.grupodespo.appcatalogo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.helpers.GetHttpCategories;
import com.grupodespo.appcatalogo.helpers.GetHttpProducts;
import com.grupodespo.appcatalogo.models.Category;
import com.grupodespo.appcatalogo.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigurationActivity extends AppCompatActivity {
    //private View mProgressView;
    private Button downloadProducts;
    private Button emptyProducts;
    private EditText urlText;
    private Button saveUrl;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private List<Product> items = new ArrayList();
    private List<Category> categories = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        SharedPreferences preferencias = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        urlText = (EditText) findViewById(R.id.urlText);
        urlText.setText(preferencias.getString("url",""));
        saveUrl = (Button) findViewById(R.id.saveUrl);
        saveUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences(v);
            }
        });

        list = (ListView) findViewById(R.id.contiguration_logs);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.log_item, arrayList);
        list.setAdapter(adapter);
        downloadProducts = (Button) findViewById(R.id.downloadProducts);
        downloadProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDownloadProducts();
            }
        });
        emptyProducts = (Button) findViewById(R.id.emptyProducts);
        emptyProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEmptyProducts();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void savePreferences(View view){
        SharedPreferences preferencias = getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("url", urlText.getText().toString());
        editor.commit();
        //finish();
    }

    private void goDownloadProducts() {
        if(checkInternetConenction()){
            GetHttpCategories wsCategories = new GetHttpCategories(ConfigurationActivity.this, categories, adapter, list, arrayList, null);
            wsCategories.execute();
            GetHttpProducts wsProducts = new GetHttpProducts(ConfigurationActivity.this, items, adapter, list, arrayList, null);
            wsProducts.execute();
        }else{
            Log.d("main","NO coneactado");
        }
    }

    private void goEmptyProducts(){
        if(checkInternetConenction()){
            AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(ConfigurationActivity.this,null,null,0);
            db.emptyProducts();
            db.emptyCategories();
            arrayList.clear();
            Toast.makeText(this, "Base de datos vaciada", Toast.LENGTH_SHORT).show();
        }else{
            Log.d("main","NO coneactado");
        }
    }

    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(this, " No estás conectado. ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


}
