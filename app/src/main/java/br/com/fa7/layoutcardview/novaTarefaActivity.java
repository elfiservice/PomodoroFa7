package br.com.fa7.layoutcardview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class novaTarefaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText novaTarefaNome;
    private EditText novaTarefaDescricao;
    private EditText novaTarefaPomodoros;
    private Button  novaTarefaBtnSalvar;
    private Button novaTarefaBtnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);

        novaTarefaNome = (EditText) findViewById(R.id.novaTarefaNome);
        novaTarefaDescricao = (EditText) findViewById(R.id.novaTarefaDescricao);
        novaTarefaPomodoros = (EditText) findViewById(R.id.novaTarefaNumeroPomodoros);
        novaTarefaBtnSalvar = (Button) findViewById(R.id.novaTarefaBtnSalvar);
        novaTarefaBtnSalvar.setOnClickListener(this);
        novaTarefaBtnExcluir = (Button) findViewById(R.id.novaTarefaBtnExcluir);
        novaTarefaBtnExcluir.setOnClickListener(this);



        Intent it = getIntent();
        //recebendo Dados para Atualizar Editar
        if(it.hasExtra("nomeTarefa")) {
            final String nome = it.getExtras().getString("nomeTarefa");
            final String descricao = it.getExtras().getString("nomeDescricao");
            final String nPomodoros = it.getExtras().getString("nPomodoros");
            final int id = it.getExtras().getInt("ID");

            novaTarefaNome.setText(nome);
            novaTarefaDescricao.setText(descricao);
            novaTarefaPomodoros.setText(nPomodoros);
        }else{
            novaTarefaBtnExcluir.setEnabled(false);
        }





    }

    private void deletar(int id, String nome, String descricao, String nPomodoros){
        TarefaDao tarefaDao = new TarefaDao(this);
        Tarefa t = new Tarefa(id, nome, descricao, nPomodoros);
        tarefaDao.delete(t);
    }


    private void atualizar(int id, String nome, String descricao, String nPomodoros){
        TarefaDao tarefaDao = new TarefaDao(this);
        Tarefa t = new Tarefa(id, nome, descricao, nPomodoros);
        tarefaDao.update(t);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.novaTarefaBtnSalvar:
                Intent i = getIntent();
                if(i.hasExtra("nomeTarefa")) {

                    final int id = i.getExtras().getInt("ID");


                    Context context = getApplicationContext();
                    String entradaNomeNT = novaTarefaNome.getText().toString();
                    String entradaDescricaoNT = novaTarefaDescricao.getText().toString();
                    String entradaPomodorosNT = novaTarefaPomodoros.getText().toString();
                    if(!entradaNomeNT.isEmpty() && !entradaDescricaoNT.isEmpty() && !entradaPomodorosNT.isEmpty()){
                        atualizar(id,entradaNomeNT,entradaDescricaoNT,entradaPomodorosNT);
                        Toast toast = Toast.makeText(context, "Todos os dados foram ATUALIZADOS!", Toast.LENGTH_LONG);
                        toast.show();
                        Intent i2 = new Intent(context, MainActivity.class);
                        startActivity(i2);
                    }else{
                        //emitir mensagem para preencher todos os campos
                        //Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Preencha todos os dados", Toast.LENGTH_LONG);
                        toast.show();

                    }

                }else{
                    String entradaNomeNT = novaTarefaNome.getText().toString();
                    String entradaDescricaoNT = novaTarefaDescricao.getText().toString();
                    String entradaPomodorosNT = novaTarefaPomodoros.getText().toString();
                    if(!entradaNomeNT.isEmpty() && !entradaDescricaoNT.isEmpty() && !entradaPomodorosNT.isEmpty()){
                        Intent intent = new Intent();
                        intent.putExtra("tarefaSalva", entradaNomeNT);
                        intent.putExtra("tarefaDescricaoSalva", entradaDescricaoNT);
                        intent.putExtra("tarefaPomodorosSalva", entradaPomodorosNT);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        //emitir mensagem para preencher todos os campos
                        Context context = getApplicationContext();

                        Toast toast = Toast.makeText(context, "Preencha todos os dados", Toast.LENGTH_LONG);
                        toast.show();

                    }
                }


                break;
            case R.id.novaTarefaBtnExcluir:
                Intent i2 = getIntent();
                final int id = i2.getExtras().getInt("ID");
                Context context = getApplicationContext();
                String entradaNomeNT = novaTarefaNome.getText().toString();
                String entradaDescricaoNT = novaTarefaDescricao.getText().toString();
                String entradaPomodorosNT = novaTarefaPomodoros.getText().toString();

                deletar(id,entradaNomeNT,entradaDescricaoNT,entradaPomodorosNT);
                Toast toast = Toast.makeText(context, "Pomodoro Excluido!", Toast.LENGTH_LONG);
                toast.show();
                Intent i3 = new Intent(context, MainActivity.class);
                startActivity(i3);
                break;
        }
    }
}
