package jubasupermercados.jubasupermercados.jubasupermercados;

import android.app.AlertDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class listaProduto extends AppCompatActivity {

    private String HOST = "http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn";
    private AlertDialog alerta;
    String urlImg = "";
    ProdutoAdapter produtoAdapter;
    List<Produto> lista;

    ListView listViewProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);


        ///////////LISTA PRODUTOS///////////////////////////////////////////////////////////////////
        listViewProdutos = (ListView) findViewById(R.id.listaProdutos);
        lista = new ArrayList<Produto>();
        produtoAdapter = new ProdutoAdapter(listaProduto.this,lista);
        listViewProdutos.setAdapter(produtoAdapter);

        //////////RECUPERO A ID DA EMPRESA PASSADA PELO ADAPTER EMPRESA/////////////////////////////
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String filial = bundle.getString("filial");
        String seqpromo = bundle.getString("seqpromo");

        TextView nomeCidade = (TextView) findViewById(R.id.txtCidadeEmpresa);
        nomeCidade.setText(bundle.getString("nomeCidade"));

        ImageView imgPerfEmpresa = (ImageView)   findViewById(R.id.imgPerfilEmpresa);

        Picasso.get()
                .load(bundle.getString("img"))
                .into(imgPerfEmpresa);

        listaProdutoListView(filial);

        /*////////ALIMENTA OS DADOS DA EMPRESA///////////////////////////////////////////////////////
        TextView txtHrFuncionamento = (TextView) findViewById(R.id.txtHrFuncionamento);
        TextView txtEndereco = (TextView) findViewById(R.id.txtEndereco);
        TextView txtContato = (TextView) findViewById(R.id.txtContato);

        TextView txtNomeEmpresa = (TextView) findViewById(R.id.txtNomeEmpresa);
        TextView txtCidadeEmpresa = (TextView) findViewById(R.id.txtCidadeEmpresa);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setNumStars(10);

        ImageView imgPerfilEmpresa = (ImageView) findViewById(R.id.imgPerfilEmpresa);

        txtNomeEmpresa.setText(bundle.getString("fantasia"));
        txtCidadeEmpresa.setText(bundle.getString("cidadeestado"));
        txtHrFuncionamento.setText(bundle.getString("funcionamento"));
        txtEndereco.setText(bundle.getString("endereco"));
        txtContato.setText(bundle.getString("contato"));

        Picasso.with(this)
                .load(bundle.getString("urlimageempresa"))
                .into(imgPerfilEmpresa);
        ///////////////////////////////////////////////////////////////////////////////////////////*/
    }

    ////////FUNÇÃO QUE BUSCA E LISTA OS PRODUTOS PARA A EMPRESA CORRESPONDENTE//////////////////////
    public void listaProdutoListView (String filial){
        final ProgressBar progressBarProd =  findViewById(R.id.progresBarProdutos);
        final TextView txtProgressBar = findViewById(R.id.textView6);

        final String url = HOST + "/listaProdutos.php";
        try {
            Ion.with(listaProduto.this)
                    .load(url)
                    .setBodyParameter("filial", filial)
                    .asJsonArray()
                    .setCallback(new FutureCallback<JsonArray>() {
                        @Override
                        public void onCompleted(Exception e, JsonArray result) {
                            // do stuff with the result or error
                            if (result.size() > 0) {
                                for (int i = 0; i < result.size(); i++) {
                                    JsonObject obj = result.get(i).getAsJsonObject();
                                    final Produto produto = new Produto();
                                    ///////ALIMENTANDO O ADAPTER////////////////////////////////////
                                    produto.setId(obj.get("ID_PRODUTO").getAsString());
                                    produto.setNome_prod(obj.get("DESCRICAO").getAsString());
                                    urlImg = obj.get("URL_IMG").getAsString();
                                    produto.setPrecoDe(obj.get("DE").getAsString());
                                    produto.setPrecoPor(obj.get("POR").getAsString());
                                    produto.setProdCategoria(obj.get("CATEGORIA").getAsString());
                                    produto.setSeqProduto(obj.get("SEQPRODUTO").getAsString());
                                    produto.setInicio(obj.get("INICIO").getAsString());
                                    produto.setFim(obj.get("FIM").getAsString());
                                    produto.setImg_prod_ofer(urlImg);
                                    ////////////////////////////////////////////////////////////////
                                    lista.add(produto);
                                }
                                progressBarProd.setVisibility(View.GONE);
                                txtProgressBar.setVisibility(View.GONE);
                                produtoAdapter.notifyDataSetChanged();
                            } else {
                                //objInicial.caixaDeDialogo ("Desculpe :´(","Pedimos desculpas mas não existem promoções para esta filial",listaProduto.this );
                            }
                        }
                    });
        }catch (Exception e){
            Toast.makeText(this,"Erro: "+e,Toast.LENGTH_LONG).show();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}

