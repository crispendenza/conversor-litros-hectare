package dc.ufscar.br.conversorlha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by crp on 07/10/14.
 */

public class Principal extends Activity {

    public final static String CHAVE = "br.ufscar.dc.conversorLHa";

    private final static double SP;
    private final static double RJ;
    private final static double MG;
    private double valorGastoSemana;
    private double valorCombustivel;

    static {
        SP = 0.72;
        RJ = 0.72;
        MG = 0.72;
    }

    public double getValorGastoSemana() {
        return valorGastoSemana;
    }
    public double getValorCombustivel() {
        return valorCombustivel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        valorCombustivel = 1.87;
        valorGastoSemana = 20;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        EditText et1 = (EditText) findViewById(R.id.fieldtexto1);
        et1.setText(String.valueOf(getValorGastoSemana()));

        EditText et2 = (EditText) findViewById(R.id.editText4);
        et2.setText(String.valueOf(getValorCombustivel()));

        Spinner spinner = (Spinner) findViewById(R.id.spinner_estados);

        // cria um ArrayAdapter usando a array de Strings e um leiaute padrão de Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_estados, android.R.layout.simple_spinner_item);

        // Especifica o leiaute
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public double calcula(double y, double x, String estado){
        /* 'y' e o valor gasto por semana (R$) e 'x' o valor do combustivel atualmente (R$)

        */
        if (estado.equals("São Paulo"))
            return (y * 48) / x / SP ;
        else if (estado.equals("Minas Gerais"))
            return (y * 48) / x / MG;
        else if (estado.equals("Rio de Janeiro"))
            return (y * 48) / x / RJ;
        else
            return -1;
    }

    public void enviaResultado(View view){

        Intent intent = new Intent(this, ResultActivity.class );

        //recupera os ids dos campos de texto "valor gasto por semana (fieldtexto1)" e "valor do combustivel"
        EditText et1 = (EditText) findViewById(R.id.fieldtexto1);
        EditText et2 = (EditText) findViewById(R.id.editText4);

        //atribui o valor dos campos resgatados acima às variaveis
        valorGastoSemana = Double.parseDouble(et1.getText().toString());
        valorCombustivel = Double.parseDouble(et2.getText().toString());

        //recupera o valor do spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_estados);
        String estado = mySpinner.getSelectedItem().toString();

        //insere na Intent
        intent.putExtra(CHAVE, calcula(valorGastoSemana, valorCombustivel, estado));

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
