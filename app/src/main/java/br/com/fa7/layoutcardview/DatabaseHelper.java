package br.com.fa7.layoutcardview;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bruno on 25/05/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "example.db";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        readFileAndExecSql("db/create.sql", db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        readFileAndExecSql("db/update.sql", db);
        onCreate(db);
    }

    private void readFileAndExecSql(String path, SQLiteDatabase db){
        try {

            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open(path);

            String line = null;
            BufferedReader reader = new
                    BufferedReader(new InputStreamReader(in));

            while((line = reader.readLine()) != null){
                db.execSQL(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}