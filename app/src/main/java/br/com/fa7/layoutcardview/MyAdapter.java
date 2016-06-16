package br.com.fa7.layoutcardview;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Armando on 24/05/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Tarefa> tarefas;
    public Context c;

    public MyAdapter(Context context, List<Tarefa> tarefas) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tarefas = tarefas;
        c = context;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Tarefa tarefa = tarefas.get(position);
        holder.textView.setText(tarefa.getNome());
        holder.descricao.setText(tarefa.getDescricao());
        holder.nPomodoro.setText(tarefa.getnPomodoros());

        holder.setItemClickListener(new ItemClickListener() {



            @Override
            public void onItemClick(View v, int pos) {

                Intent intent = new Intent(c, novaTarefaActivity.class);
                intent.putExtra("nomeTarefa", tarefas.get(pos).getNome());
                intent.putExtra("nomeDescricao", tarefas.get(pos).getDescricao());
                intent.putExtra("nPomodoros", tarefas.get(pos).getnPomodoros());
                intent.putExtra("ID", tarefas.get(pos).getId());

                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tarefas.size(); //ALTERAR .. para pegar o tamanho da Lista !!
    }

    /////////------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        BoundService mBoundService = new BoundService();
    //   public ImageView imageView;
       public TextView textView;
        public TextView descricao;
        public TextView nPomodoro;
        public Button itemBtnConcluido;
        public Button itemBtnIniciar;
        TarefaDao tarefaDao = new TarefaDao(c);
        ItemClickListener itemClickListener;
        Handler mHandler = new Handler();
        StartedService startedService;
        //RecyclerView recyclerView;

        TextView textViewMAinTimer;

        public MyViewHolder(View itemView) {
            super(itemView);

           // imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            descricao = (TextView) itemView.findViewById(R.id.itemDescricao);
            nPomodoro = (TextView) itemView.findViewById(R.id.itemNumeroPomodoros);

            itemBtnIniciar = (Button) itemView.findViewById(R.id.itemBtnIniciar);
            itemBtnIniciar.setOnClickListener(this);
            itemBtnConcluido = (Button) itemView.findViewById(R.id.itemBtnConcluido);
            itemBtnConcluido.setOnClickListener(this);
            //recyclerView = (RecyclerView) itemView.findViewById(R.id.mainReclyclerView);
            textView.setOnClickListener(this);
            descricao.setOnClickListener(this);
            nPomodoro.setOnClickListener(this);
            itemView.setOnClickListener(this);

//            Intent mBoundServiceIntent = new Intent(c, BoundService.class);
//            c.startService(mBoundServiceIntent);
            textViewMAinTimer = (TextView) ((Activity) c).findViewById(R.id.mainTimer);
            //startedService.add(this);
        }

        @Override
        public void onClick(View v) {



            switch (v.getId()){
                case R.id.itemBtnConcluido:
                    Tarefa t = tarefas.get(getLayoutPosition());
                    Log.i("Tarefa: ", "ID "+t.getId()+" e nome "+t.getNome());
                    tarefaDao.delete(t);
                    tarefas.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    notifyItemRangeChanged(getLayoutPosition(), tarefas.size());
                break;
                case R.id.itemBtnIniciar:

                 // mBoundService.startCounter();

   // Activity ac = (Activity) c;

//
//Handler mHandler = new Handler();
//
                    //Intent it = new Intent((Activity) c, StartedService.class);

                    //c.startService(it);
//iniciarContador();
                   // TextView textView = (TextView) ((Activity) c).findViewById(R.id.mainTimer);

                    //
                    //textView.setTextColor(Color.parseColor("#BABABA"));
//                    textView.setText("Ops");
//                    Log.i("Tarefa: ", "Activity "+textView);
                    break;
                default:
                    itemClickListener.onItemClick(v,getLayoutPosition());
                    break;

            }
        }

        public void setItemClickListener(final ItemClickListener ic){
            this.itemClickListener = ic;
        }

//        public void iniciarContador(){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    //Context context = getBaseContext();
//                     TextView textView = (TextView) ((Activity) c).findViewById(R.id.mainTimer);
//                    for (int i = 0; i < 100; i++) {
//                        try {
//
//                            //notifyValue(i + 1);
//                            textView.setText(String.valueOf(i + 1));
//
//                            Log.i("App", "Valor: " + (i + 1));
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    //stopSelf();
//                }
//            }).start();
//        }

//        @Override
//        public void newValue(final long value) {
//
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                   // TextView textView = (TextView) ((Activity) c).findViewById(R.id.mainTimer);
//                    textViewMAinTimer.setText(String.valueOf(value));
//                }
//            });
//        }





//        private ServiceConnection mServiceConnection = new ServiceConnection() {
//
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
//                mBoundService = binder.getService();
//                Activity ac = (Activity) c;
//                mBoundService.add();
//                //mIsServiceBound = true;
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//                //mIsServiceBound = false;
//            }
//        };


    }

}

