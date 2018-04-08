package com.grupodespo.appcatalogo;

import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.grupodespo.appcatalogo.helpers.AdminSQLiteOpenHelper;
import com.grupodespo.appcatalogo.helpers.GetHttpCategories;
import com.grupodespo.appcatalogo.models.Category;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends AppCompatActivity {
    //private View mProgressView;
    private Button downloadCategories;
    private Button emptyCategories;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private List<Category> items = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        list = (ListView) findViewById(R.id.contiguration_logs);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.log_item, arrayList);
        list.setAdapter(adapter);
        downloadCategories = (Button) findViewById(R.id.downloadCategories);
        downloadCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDownloadCategories();
            }
        });
        emptyCategories = (Button) findViewById(R.id.emptyCategories);
        emptyCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEmptyCategories();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void goDownloadCategories() {
        if(checkInternetConenction()){
            GetHttpCategories wsClientes = new GetHttpCategories(ConfigurationActivity.this, items, adapter, list, arrayList, null);
            wsClientes.execute();
        }else{
            Log.d("main","NO coneactado");
        }

        /*arrayList.add("fabio");
        arrayList.add("esteban");
        arrayList.add("Uzeltinger");
        adapter.notifyDataSetChanged();*/
    }

    private void goEmptyCategories(){
        if(checkInternetConenction()){
            AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(ConfigurationActivity.this,null,null,0);
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
