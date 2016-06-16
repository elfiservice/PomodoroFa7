package br.com.fa7.layoutcardview;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListenValue, View.OnClickListener {

    private RecyclerView recyclerView;

    private TextView mainTextViewTimer;
    private Button mainBtnNovaTarefa;

    private Intent mBoundServiceIntent;
    public BoundService mBoundService;
    private Handler mHandler;
    private boolean mIsServiceBound;
    //StartedService startedService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //startedService.add(MainActivity.this);


        //Button btTeste = (Button) findViewById(R.id.mainBtnTeste);
        //btTeste.setOnClickListener(this);
 //--------------------------Contador -----------------
        mainTextViewTimer = (TextView) findViewById(R.id.mainTimer);

        mBoundServiceIntent = new Intent(this, BoundService.class);
        startService(mBoundServiceIntent);
        mHandler = new Handler();
//--------------------------------------------------------

        TarefaDao tarefaDao = new TarefaDao(this);
        List<Tarefa> tarefas = tarefaDao.findAll();

        recyclerView = (RecyclerView) findViewById(R.id.mainReclyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        if(tarefas != null) {

            atualizaAdapter(tarefas);
        }
        ///----------------------------------------------------

        mainBtnNovaTarefa = (Button) findViewById(R.id.mainBtnNovaTarefa);
        mainBtnNovaTarefa.setOnClickListener(this);



    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//        TarefaDao tarefaDao = new TarefaDao(this);
//        List<Tarefa> tarefas = tarefaDao.findAll();
//
//        if(tarefas != null) {
//
//            atualizaAdapter(tarefas);
//        }
//
//
//    }



    public void  atualizaAdapter(List<Tarefa> tarefas){
        MyAdapter mAdapter = new MyAdapter(this, tarefas);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                String tarefaSalva = data.getStringExtra("tarefaSalva");
                String tarefaDescricaoSalva = data.getStringExtra("tarefaDescricaoSalva");
                String tarefaPomodorosSalva = data.getStringExtra("tarefaPomodorosSalva");

                TarefaDao tarefaDao = new TarefaDao(this);
                tarefaDao.insert(new Tarefa(tarefaSalva,tarefaDescricaoSalva, tarefaPomodorosSalva));
                List<Tarefa> tarefas = tarefaDao.findAll();
                MyAdapter mAdapter = new MyAdapter(this, tarefas);
                recyclerView.setAdapter(mAdapter);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainBtnNovaTarefa:
                Intent intent = new Intent(this, novaTarefaActivity.class);
                startActivityForResult(intent, 1);
                break;




        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(mBoundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void newValue(final String value) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mainTextViewTimer.setText(String.valueOf(value));
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.LocalBinder binder = (BoundService.LocalBinder) service;
            mBoundService = binder.getService();
            mBoundService.add(MainActivity.this);
            //startedService.add(MainActivity.this);
            mIsServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsServiceBound = false;
        }
    };



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
           // BoundService mBoundService = new BoundService();
            //   public ImageView imageView;
            public TextView textView;
            public TextView descricao;
            public TextView nPomodoro;
            public Button itemBtnConcluido;
            public Button itemBtnIniciar;
            public RelativeLayout fundoDoItem;
            TarefaDao tarefaDao = new TarefaDao(c);
            ItemClickListener itemClickListener;
           // Handler mHandler = new Handler();
            //StartedService startedService;
            //RecyclerView recyclerView;

            //TextView textViewMAinTimer;

            public MyViewHolder(View itemView) {
                super(itemView);

                // imageView = (ImageView) itemView.findViewById(R.id.imageView);
                fundoDoItem = (RelativeLayout) findViewById(R.id.fundoDoItem);
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
                //textViewMAinTimer = (TextView) ((Activity) c).findViewById(R.id.mainTimer);
                //startedService.add(this);
            }

            @Override
            public void onClick(View v) {



                switch (v.getId()){
                    case R.id.itemBtnConcluido:
                        mBoundService.stopCounter();
//                        Tarefa t = tarefas.get(getLayoutPosition());
//                        Log.i("Tarefa: ", "ID "+t.getId()+" e nome "+t.getNome());
//                        tarefaDao.delete(t);
//                        tarefas.remove(getLayoutPosition());
//                        notifyItemRemoved(getLayoutPosition());
//                        notifyItemRangeChanged(getLayoutPosition(), tarefas.size());
                        break;
                    case R.id.itemBtnIniciar:
                        mBoundService.startCounter();
                        itemBtnIniciar.setEnabled(false);
                        //Tarefa t = tarefas.get(getLayoutPosition());
                        textView.setBackgroundColor(Color.parseColor("#B0C4DE"));



                        break;
                    default:
                        itemClickListener.onItemClick(v,getLayoutPosition());
                        break;

                }
            }

            public void setItemClickListener(final ItemClickListener ic){
                this.itemClickListener = ic;
            }


        }

    }


}
