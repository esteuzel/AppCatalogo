package com.grupodespo.appcatalogo.helpers;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.grupodespo.appcatalogo.models.Category;
import com.grupodespo.appcatalogo.models.Product;

import java.util.ArrayList;
import java.util.List;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "grupodesco.db";
    private List<Category> categories = new ArrayList();

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists categorias(" +
                "id integer primary key," +
                "parent integer," +
                "name text" +
                ")");
        db.execSQL("create table if not exists productos(" +
                "id integer primary key," +
                "catid integer," +
                "name text," +
                "detail text," +
                "image text" +
                ")");
        db.execSQL("create table if not exists ventas(" +
                "id integer primary key autoincrement," +
                "clientId integer," +
                "productId integer," +
                "productName text," +
                "productDetail text," +
                "productImage text" +
                ")");
        Log.d("AdminSQLiteOpenHelper", "onCreate");
    }

    public void doLoadInitialData(SQLiteDatabase db){
        categories.add(new Category(1,1,"fabio"));
        for(int i=0; i<categories.size(); i++) {
            long insert =  db.insert("categorias",
                    null, categories.get(i).toContentValues());
            Log.d("AdminSQLiteOpenHelper" ,"doLoadInitialData categorias agrtegadas: "
                    + categories.size());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categorias");
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS ventas");
        onCreate(db);
    }

    public void saveCategories(List<Category> cats){
        emptyCategories();
        SQLiteDatabase db = getReadableDatabase();
        for(int i=0; i<cats.size(); i++) {
            long insert =  db.insert("categorias",
                    null, cats.get(i).toContentValues());
        }
        Log.d("AdminSQLiteOpenHelper" ,"saveCategories categorias agrtegadas: "
                + cats.size());
    }
    public void saveProducts(List<Product> products){
        SQLiteDatabase db = getReadableDatabase();
        emptyProducts();
        for(int i=0; i<products.size(); i++) {
            long insert =  db.insert("productos",
                    null, products.get(i).toContentValues());
        }
        Log.d("AdminSQLiteOpenHelper" ,"saveProducts productos agrtegados: "
                + products.size());
    }
    public void emptyCategories(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS categorias");
        Log.d("AdminSQLiteOpenHelper", "emptyCategories");
        onCreate(db);
    }
    public void emptyProducts(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS productos");
        Log.d("AdminSQLiteOpenHelper", "emptyProductos");
        onCreate(db);
    }
    public List<Category> getAllCategories(){
        List<Category> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "categorias",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "name"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int parent = Integer.parseInt(c.getString(c.getColumnIndex("parent")));
            String name = c.getString(c.getColumnIndex("name"));
            Category item = new Category(id, parent, name);
            list.add(item);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getAllCategories total: " + list.size());
        return list;
    }
    public List<Category> getCategories(int categoryParentId){
        List<Category> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "categorias",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                "parent=?",  // Columnas para la cláusula WHERE
                new String[] { String.valueOf(categoryParentId)},  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "name"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int parent = Integer.parseInt(c.getString(c.getColumnIndex("parent")));
            String name = c.getString(c.getColumnIndex("name"));
            Category item = new Category(id, parent, name);
            list.add(item);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getCategories total: " + list.size());
        return list;
    }

    public List<Product> getProducts(int categoryParentId){
        List<Product> list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        //doLoadInitialData(db);
        Cursor c = db.query(
                "productos",  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                "catid=?",  // Columnas para la cláusula WHERE
                new String[] { String.valueOf(categoryParentId)},  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                "name"  // Cláusula ORDER BY
        );
        while(c.moveToNext()){
            int id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            int catid = Integer.parseInt(c.getString(c.getColumnIndex("catid")));
            String name = c.getString(c.getColumnIndex("name"));
            String detail = c.getString(c.getColumnIndex("detail"));
            String image = c.getString(c.getColumnIndex("image"));
            Product item = new Product(id, catid, name, detail, image);
            list.add(item);
        }
        Log.d("AdminSQLiteOpenHelper" ,"getProducts total: " + list.size());
        return list;
    }

}
