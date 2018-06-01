package com.example.neni.estudoandroid;


import android.widget.EditText;
import android.widget.Spinner;


public class FormularioHelper {

    private EditText campoNome;
    private EditText campoEndereco;
    private Spinner campoTipo;
    private String[] tipos = {"Rodizio","Fast Food","buffet"};

    private Restaurante restaurante;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = activity.findViewById(R.id.formulario_nome);
        campoEndereco = activity.findViewById(R.id.formulario_endereco);
        campoTipo = activity.findViewById(R.id.formulario_tipo);
        restaurante = new Restaurante();
    }

    public Restaurante pegaRestaurante() {
        restaurante.setNome(campoNome.getText().toString());
        restaurante.setEndereco(campoEndereco.getText().toString());
        restaurante.setTipo(campoTipo.getSelectedItem().toString());

        return restaurante;
    }

    public void preencheFormulario(Restaurante restaurante)  {
        campoNome.setText(restaurante.getNome());
        campoEndereco.setText(restaurante.getEndereco());

        for (int i = 0; i < tipos.length; i++){
            if (tipos[i] == restaurante.getTipo()){
                campoTipo.setSelection(i);
            }
        }
    }
}
