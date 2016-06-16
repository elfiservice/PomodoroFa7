package br.com.fa7.layoutcardview;

import java.util.List;

public interface IDatabase<T> {

    void insert(T obj);

    void update(T obj);

    void delete(T obj);

    T find(Integer id);

    List<T> findAll();

}
