package br.com.fa7.layoutcardview;

/**
 * Created by Armando on 26/05/2016.
 */
public interface ServiceNotifier {

    void add(ListenValue obj);

    void notifyValue(String value);
}