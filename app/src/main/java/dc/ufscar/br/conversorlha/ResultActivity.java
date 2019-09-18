package dc.ufscar.br.conversorlha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.text.DecimalFormat;

public class ResultActivity extends Activity {

    // área de um campo de futebol tamanho 110 x 75 m
    private final static double areaCampoFut = 800.00;

    public double converteMedida(double a){

        return a / areaCampoFut;
    }

    public String formataDouble(double d){

        DecimalFormat duasCasas = new DecimalFormat("#.##");
        return String.valueOf(duasCasas.format(d));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        // este double eh area total consumida pelo cara, ja em metros quadrados
        double resultado = intent.getDoubleExtra(Principal.CHAVE, 0);
        String mensagem = new StringBuilder().append(getResources().getString(R.string.ResultadoString1)).
                append(" ").append(formataDouble(resultado)).append(" ").append(getResources().
                getString(R.string.ResultadoString2)).append(" ").append(formataDouble(converteMedida(resultado))).
                append(" ").append(getResources().getString(R.string.ResultadoString3)).toString();

        TextView tv1 = (TextView) findViewById(R.id.tv_resultado);
        tv1.setTextSize(25);
        //tv1.setText(mensagem);

        String[] wordString = TextUtils.split(mensagem, " ");
        for (String word : wordString) {
            if (word.matches("^\\d*[0-9](\\.\\d*[0-9])?$")) {
                Spannable wordtoSpan = new SpannableString(word);
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, word.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv1.append(wordtoSpan);
            }
            else if (!tv1.getText().toString().equals("")) {
                tv1.append(" " + word + " ");
            }
            else
                tv1.append(word + " ");
        }


     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
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
