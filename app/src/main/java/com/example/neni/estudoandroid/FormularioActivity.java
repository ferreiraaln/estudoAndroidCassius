package com.example.neni.estudoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private String[] tipos = {"Rodizio","Fast Food","buffet"};
    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper (FormularioActivity.this);

        Spinner combo = findViewById(R.id.formulario_tipo);

        Intent intent = getIntent();
        Restaurante restaurante = (Restaurante) intent.getSerializableExtra("restaurante");
        if (restaurante !=null) {
            helper.preencheFormulario(restaurante);
        }

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item,
                        tipos);
        combo.setAdapter(adaptador);



        Button botaoSalvar = findViewById(R.id.formulario_botaoSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick (View v) {




                Restaurante restaurante = helper.pegaRestaurante();

                RestaurantDAO dao = new RestaurantDAO(FormularioActivity.this);

                if (restaurante.getId() != null)  {
                    dao.altera(restaurante);
                    Toast.makeText(FormularioActivity.this, "Resturante a salvo!", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insere(restaurante);
                }
                dao.close();

                Toast.makeText(FormularioActivity.this, "Resturante " + restaurante.getNome() + " salvo!", Toast.LENGTH_SHORT).show();



            finish();

            }
       });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_add:
                Toast.makeText(FormularioActivity.this, "salvar", Toast.LENGTH_SHORT).show();


                Restaurante restaurante = helper.pegaRestaurante();

                RestaurantDAO dao = new RestaurantDAO(FormularioActivity.this);

                if (restaurante.getId() != null)  {
                    dao.altera(restaurante);
                    Toast.makeText(FormularioActivity.this, "Resturante a salvo!", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insere(restaurante);
                }
                dao.close();

                Toast.makeText(FormularioActivity.this, "Resturante " + restaurante.getNome() + " salvo!", Toast.LENGTH_SHORT).show();



                finish();

        }
        return super.onOptionsItemSelected(item);

    }
}
