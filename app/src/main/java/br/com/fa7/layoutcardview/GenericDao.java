package br.com.fa7.layoutcardview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 25/05/2016.
 */
public abstract class GenericDao<T extends IModel> implements IDatabase<T> {

    protected SQLiteDatabase mDb;
    protected String mTableName;

    public GenericDao(Context context, String databaseName) {
        DatabaseHelper mDbHelper = new DatabaseHelper(context);
        this.mDb = mDbHelper.getWritableDatabase();
        this.mTableName = databaseName;
    }

    @Override
    public void insert(T obj) {
        mDb.insert(mTableName, null, getContentValues(obj));
    }

    @Override
    public void update(T obj) {
        mDb.update(mTableName, getContentValues(obj), "_id = ?",
                new String[]{obj.getId().toString()});
    }

    @Override
    public void delete(T obj) {
        mDb.delete(mTableName, "_id = ?",
                new String[]{obj.getId().toString()});
    }

    @Override
    public T find(Integer id) {
        T obj = null;
        Cursor c = mDb.query(mTableName, null, "_id = ?",
                new String[]{id.toString()}, null, null, null);

        if(c.getCount() > 0){
            c.moveToFirst();
            obj = getObjectFromCursor(c);
        }
        return obj;
    }

    @Override
    public List<T> findAll() {

        List<T> list = null;
        Cursor c = mDb.query(mTableName, null, null,
                null, null, null, "_id DESC");

        if(c.getCount() > 0){
            list = new ArrayList<>();
            c.moveToFirst();
            do {
                list.add(getObjectFromCursor(c));
            }while (c.moveToNext());
        }

        return list;
    }

    public abstract ContentValues getContentValues(T obj);

    public abstract T getObjectFromCursor(Cursor cursor);

}
