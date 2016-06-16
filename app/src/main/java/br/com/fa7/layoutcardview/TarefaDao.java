package br.com.fa7.layoutcardview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by bruno on 25/05/2016.
 */
public class TarefaDao extends GenericDao<Tarefa> {

    private static final String TABLE_NAME = "tarefas";

    public TarefaDao(Context context) {
        super(context, TABLE_NAME);
    }

    @Override
    public ContentValues getContentValues(Tarefa obj) {

        ContentValues cv = new ContentValues();
        cv.put("_id", obj.getId());
        cv.put("nome", obj.getNome());
        cv.put("descricao", obj.getDescricao());
        cv.put("npomodoros", obj.getnPomodoros());


        return cv;
    }

    @Override
    public Tarefa getObjectFromCursor(Cursor cursor) {

        Integer id = cursor.getInt(cursor.getColumnIndex("_id"));
        String nome = cursor.getString(cursor.getColumnIndex("nome"));
        String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
        String npomodoros = cursor.getString(cursor.getColumnIndex("npomodoros"));


        return new Tarefa(id,nome, descricao,npomodoros);

    }
}