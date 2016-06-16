package br.com.fa7.layoutcardview;

/**
 * Created by Armando on 24/05/2016.
 */
public class Tarefa implements IModel{
    private Integer id;
    private String nome;
    private String descricao;
    private String nPomodoros;

    public Tarefa(String nome, String descricao, String nPomodoros) {
        this(null, nome, descricao, nPomodoros);
    }

    public Tarefa(Integer id, String nome, String descricao, String nPomodoros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.nPomodoros = nPomodoros;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getnPomodoros() {
        return nPomodoros;
    }

    public void setnPomodoros(String nPomodoros) {
        this.nPomodoros = nPomodoros;
    }

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
