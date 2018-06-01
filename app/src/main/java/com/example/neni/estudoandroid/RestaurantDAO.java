package com.example.neni.estudoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO extends SQLiteOpenHelper {
    public RestaurantDAO(Context context) {
        super(context, "Comercio", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Restaurants (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, tipo TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Restaurants";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Restaurante restaurante) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoRestaurante(restaurante);

        db.insert("Restaurants", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoRestaurante(Restaurante restaurante) {
        ContentValues dados = new ContentValues();
        dados.put("nome", restaurante.getNome());
        dados.put("endereco", restaurante.getEndereco());
        dados.put("tipo", restaurante.getTipo());
        return dados;
    }

    public List<Restaurante> buscaRestaurantes() {
        String sql = "SELECT * from Restaurants;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Restaurante> restaurantes = new ArrayList<>();
        while(c.moveToNext()){
            Restaurante restaurante = new Restaurante();
            restaurante.setId(c.getLong(c.getColumnIndex("id")));
            restaurante.setNome(c.getString(c.getColumnIndex("nome")));
            restaurante.setEndereco(c.getString(c.getColumnIndex("endereco")));
            restaurante.setTipo(c.getString(c.getColumnIndex("tipo")));
            restaurantes.add(restaurante);
        }
        c.close();
        return restaurantes;
    }

    public void deleta(Restaurante restaurante) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {restaurante.getId().toString()};
        db.delete("Restaurants", "id = ?", params);
    }

    public void altera(Restaurante restaurante) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoRestaurante(restaurante);

        String[] params = {restaurante.getId().toString()};
        db.update("Restaurants", dados, "id = ?", params);
    }
}