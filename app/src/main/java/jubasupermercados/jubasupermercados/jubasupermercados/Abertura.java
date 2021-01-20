package jubasupermercados.jubasupermercados.jubasupermercados;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Abertura extends AppCompatActivity {

    private String HOST = "http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn";
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);

        //proximaTela();
        try {
            if(verificaConexao(this) == true){
                String cidade = "";
                String url = HOST + "/executa_select.php";
                Ion.with(Abertura.this)
                        .load(url)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                // do stuff with the result or error
                                    if (result.get("QTD").getAsInt() != 0) {
                                        /*Toast.makeText(Abertura.this,
                                                "Conexão com o banco de dados OK!!", Toast.LENGTH_LONG).show();*/
                                        while (ActivityCompat.checkSelfPermission(Abertura.this,
                                                Manifest.permission.ACCESS_FINE_LOCATION)
                                                != PackageManager.PERMISSION_GRANTED) {

                                                    ActivityCompat.requestPermissions(Abertura.this,
                                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, BIND_ABOVE_CLIENT);
                                        }


                                        proximaTela();


                                    }
                            }
                        });
            }
        }catch (Exception e){
            cxaDialogErroNet();
        }
    }

    /////////////////////////////////////////////
    /* Função Chama a proxima tela */
    public void proximaTela(){
        Intent intent = new Intent(this,Inicial.class);
        startActivity(intent);
    }
    /////////////////////////////////////////////
    /*função verifica a conexão */
    public static boolean verificaConexao(Context cont) {
        ConnectivityManager conmag = (ConnectivityManager)cont.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conmag != null ) {
            conmag.getActiveNetworkInfo();

            //Verifica internet pela WIFI
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                return true;
            }

            //Verifica se tem internet móvel
            if (conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                return true;
            }
        }

        return false;
    }
    ////////////////////////////////////////////
    /* Função caixa de dialogo */
    private void cxaDialogErroNet(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Falha na comunicação");
        //define a mensagem
        builder.setMessage("É necessário conexão com a Internet para utilizar o aplicativo");
        //define um botão como positivo
        builder.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finishAffinity();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
    ////////////////////////////////////////////


}
