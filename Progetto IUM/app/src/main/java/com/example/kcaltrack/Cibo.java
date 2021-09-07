package com.example.kcaltrack;

import androidx.annotation.NonNull;

public class Cibo {
    String nome;
    int icon;
    int m_grassi, m_carbo, m_prote, Kcal;

    public Cibo(String nome, int icon, int kcal,  int m_prote,int m_carbo, int m_grassi) {
        this.nome = nome;
        this.icon = icon;
        this.m_grassi = m_grassi;
        this.m_carbo = m_carbo;
        this.m_prote = m_prote;
       this.Kcal = kcal;
    }
    public Cibo(String nome, int icon) {
        this.nome = nome;
        this.icon = icon;
    }
    public Cibo(){};

    @NonNull
    @Override
    public String toString() {
        System.out.println(nome);
        System.out.println(m_prote);
        System.out.println(m_grassi);
        System.out.println(m_carbo);
        System.out.println(Kcal);
        return super.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getM_grassi() {
        return m_grassi;
    }

    public void setM_grassi(int m_grassi) {
        this.m_grassi = m_grassi;
    }

    public int getM_carbo() {
        return m_carbo;
    }

    public void setM_carbo(int m_carbo) {
        this.m_carbo = m_carbo;
    }

    public int getM_prote() {
        return m_prote;
    }

    public void setM_prote(int m_prote) {
        this.m_prote = m_prote;
    }

    public int getKcal() {
        return Kcal;
    }

    public void setKcal(int kcal) {
        Kcal = kcal;
    }
}
