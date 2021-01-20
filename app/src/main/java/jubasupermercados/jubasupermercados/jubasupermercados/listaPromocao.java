package jubasupermercados.jubasupermercados.jubasupermercados;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class listaPromocao extends AppCompatActivity {

    private String HOST = "http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn";
    private AlertDialog alerta;
    PromocaoAdapter promocaoAdapter;
    List<Promocao> lista;

    ListView listViewPromocoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_promocao);


        ///////////LISTA PROMOCOES//////////////////////////////////////////////////////////////////
        listViewPromocoes = (ListView) findViewById(R.id.listaPromocoes);
        lista = new ArrayList<Promocao>();
        promocaoAdapter = new PromocaoAdapter(listaPromocao.this,lista);
        listViewPromocoes.setAdapter(promocaoAdapter);

        //////////RECUPERO A ID DA EMPRESA PASSADA PELO ADAPTER EMPRESA/////////////////////////////
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String filial = bundle.getString("filial");
        listaPromocoesListView(filial);
    }

    public void listaPromocoesListView (String filial){
        final ProgressBar progresBarPromocoes =  findViewById(R.id.progresBarPromocoes);
        final TextView textView8 = findViewById(R.id.textView8);
        final String url = HOST + "/listaPromoEmpresa.php";
        Ion.with(listaPromocao.this)
                .load(url)
                .setBodyParameter("filial",filial)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        // do stuff with the result or error
                        if (result.size() > 0) {
                            for (int i = 0; i < result.size(); i++) {
                                JsonObject obj = result.get(i).getAsJsonObject();
                                final Promocao promocao = new Promocao();

                                ///////ALIMENTANDO O ADAPTER////////////////////////////////////////
                                promocao.setDescPromocao(obj.get("PROMOCAO").getAsString());
                                promocao.setDtInicioPromo(obj.get("DTAINICIO").getAsString());
                                promocao.setDtFimPromo(obj.get("DTAFIM").getAsString());
                                promocao.setSeqPromocao(obj.get("SEQPROMOCAO").getAsString());
                                promocao.setQtdProdPromo(obj.get("QTDPROD").getAsString());
                                promocao.setNroEmpresa(obj.get("NROEMPRESA").getAsString());

                                lista.add(promocao);

                            }
                            progresBarPromocoes.setVisibility(View.GONE);
                            textView8.setVisibility(View.GONE);
                            promocaoAdapter.notifyDataSetChanged();
                        }else{
                            //Inicial.cxaDialogErroNet("Desculpe :´(","Pedimos desculpas mas não existem empresas dessa cidade cadastrada em nosso banco de dados");
                        }
                    }
                });
    }
}
