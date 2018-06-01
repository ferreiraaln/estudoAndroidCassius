package com.example.neni.estudoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaRestaurantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaRestaurantes = findViewById(R.id.lista_restaurantes);
        registerForContextMenu(listaRestaurantes);

        listaRestaurantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Restaurante restaurante = (Restaurante)  listaRestaurantes.getItemAtPosition(position);
                Intent intentVaiProFormuIario = new Intent(MainActivity.this, FormularioActivity.class);
                intentVaiProFormuIario.putExtra("restaurante", restaurante);
                startActivity(intentVaiProFormuIario);
            }
        });
    }

    private void carregaLista() {
        RestaurantDAO dao = new RestaurantDAO(this);
        List<Restaurante> restaurantes = dao.buscaRestaurantes();
        dao.close();

        listaRestaurantes = findViewById(R.id.lista_restaurantes);
        ArrayAdapter<Restaurante> adapter = new ArrayAdapter <Restaurante> (this, android.R.layout.simple_list_item_1, restaurantes);
        listaRestaurantes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_add:
                Toast.makeText(MainActivity.this, "Adicionar restaurante", Toast.LENGTH_SHORT).show();
                Intent intentVaiProFormulario = new Intent (MainActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo)  {

        MenuItem editar = menu.add("Editar");
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Restaurante restaurante = (Restaurante) listaRestaurantes.getItemAtPosition(info.position);

                RestaurantDAO dao = new RestaurantDAO(MainActivity.this);
                dao.deleta(restaurante);
                dao.close();

                carregaLista();

                Toast.makeText(MainActivity.this, restaurante.getNome() + " deletado", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Restaurante restaurante = (Restaurante) listaRestaurantes.getItemAtPosition(info.position);

                Intent intentVaiProFormuIario = new Intent(MainActivity.this, FormularioActivity.class);
                intentVaiProFormuIario.putExtra("restaurante", restaurante);
                startActivity(intentVaiProFormuIario);
                return false;
            }
        });



        /*
        listaRestaurantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Restaurante restaurante = (Restaurante)  listaRestaurantes.getItemAtPosition(position);
                Intent intentVaiProFormuIario = new Intent(MainActivity.this, FormularioActivity.class);
                intentVaiProFormuIario.putExtra("restaurante", restaurante);
                startActivity(intentVaiProFormuIario);
            }
        });
         */
    }
}
