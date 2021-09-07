package com.example.kcaltrack;

import androidx.annotation.NonNull;

public class Esercizio {
    int icon;
    String nome;
    int kcal;

    public Esercizio(String nome, int icon, int kcal) {
        this.icon = icon;
        this.nome = nome;
        this.kcal = kcal;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    @NonNull
    @Override
    public String toString() {

        return super.toString();
    }
}
