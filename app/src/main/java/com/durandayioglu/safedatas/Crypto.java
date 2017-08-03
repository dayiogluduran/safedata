package com.durandayioglu.safedatas;

import java.util.Random;

/**
 * Created by durandayioglu on 02.08.2017.
 */

public class Crypto {
    public char[] alfabe;
    public String key = null;
    public int keyN;
    public int cons;


    public Crypto(int keyN ,int cons) {
        this.cons = cons;
        this.keyN = keyN;
        alfabe = "abcçdefgğhjkıilmnoöprsştuüvyzxABCÇDEFGĞHJKIİLMNOÖPRSŞTUÜVYZX<>1234567890*!'^+%&/()=?-_£#$½¾{[]}|@€Â§,.;:~".toCharArray();
        key = createKey(keyN);

    }

    public Crypto(String key , int cons) {
        this.cons = cons;
        this.key = key;
        keyN = key.length();
        alfabe = "abcçdefgğhjkıilmnoöprsştuüvyzxABCÇDEFGĞHJKIİLMNOÖPRSŞTUÜVYZX<>1234567890*!'^+%&/()=?-_£#$½¾{[]}|@€Â§,.;:~".toCharArray();
    }

    public String createKey(int keyN) {
        String key = "";

        for (int i = 0; i <= keyN; i++) {
            Random rnd = new Random();
            int index = Math.abs(rnd.nextInt()) % alfabe.length;
            key += "" + alfabe[index];
        }
        return key;
    }

    public String encode(String input) {
        String encoded = "";
        int keyI, inI, resI;

        int k = 0;

        if (key == null) {
            createKey(keyN);
        }

        for (int i = 0; i < input.length(); i++) {
            inI = indexOf(input.charAt(i));
            keyI = indexOf(key.charAt(k));
            k = (k + 1) % keyN;
            resI = ((inI + keyI) + cons) % alfabe.length;
            encoded += alfabe[resI];
        }

        return encoded;
    }

    public String decode(String input) {
        String decoded = "";
        int keyI, inI, resI;

        int k = 0;

        if (key == null) {
            createKey(keyN);
        }

        for (int i = 0; i < input.length(); i++) {
            inI = indexOf(input.charAt(i));
            keyI = indexOf(key.charAt(k));
            k = (k + 1) % keyN;
            resI = (((inI - keyI) - cons)+alfabe.length) % alfabe.length;
            decoded += alfabe[resI];
        }

        return decoded;
    }

    public int indexOf(char c) {

        for (int i = 0; i <= alfabe.length; i++) {
            if (c == alfabe[i]) {
                return i;
            }
        }
        return -1;
    }

    public String getKey() {
        return key;
    }

    public int getKeyN() {
        return keyN;
    }

    public int getCons() {
        return cons;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKeyN(int keyN) {
        this.keyN = keyN;
    }

    public void setCons(int cons) {
        this.cons = cons;
    }
}
