package jubasupermercados.jubasupermercados.jubasupermercados;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class ProdutoAdapter extends BaseAdapter {
    private Context ctx;
    private AlertDialog alerta;
    private List<Produto> lista;

    DecimalFormat dfPreco = new DecimalFormat();

    public ProdutoAdapter(Context ctx2, List<Produto> lista2){
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Produto getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Locale.setDefault(new Locale("pt", "BR"));  // mudança global
        View v = null;

        final Produto produto = getItem(position);

        DecimalFormat dfPreco = new DecimalFormat();
        dfPreco.applyPattern("R$ #,##0.00");

        if (convertView == null){
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.item_list_produto, null);
        }else{
            v = convertView;
        }

        TextView   nomeProduto = (TextView) v.findViewById(R.id.txtnomeprod);
        TextView   precoDe = (TextView) v.findViewById(R.id.txtPrecoDe);
        TextView   precoPor = (TextView) v.findViewById(R.id.txtPrecoPor);
        ImageView  img_prod_ofer = (ImageView) v.findViewById(R.id.img_prod_ofer);
        ImageView  img_oferta = (ImageView) v.findViewById(R.id.img_oferta);
        TextView   txtOFF = (TextView) v.findViewById(R.id.txtOFF);
        TextView   txtProdCate = (TextView) v.findViewById(R.id.txtProdCate);
        TextView   txtSeqProduto = (TextView) v.findViewById(R.id.txtSeqProduto);
        TextView   txtPeriodoProdPromo = (TextView) v.findViewById(R.id.txtPeriodoProdPromo);

        //banerEmpresa.setImageResource(empresa.getBannerEmpresa());

        /////MUDA VIRGULA PARA PONTO////////////////////////////////////////////////////////////////
        Double precoDE,precoPOR,floatOFF;
        String precostrDE,precostrPOR;
        //precoDE = Float.parseFloat(produto.getPrecoDe());
        //precoPOR = Float.parseFloat(produto.getPrecoPor());

        precostrDE = produto.getPrecoDe().replaceFirst(",", ".");
        precoDE = Double.valueOf((precostrDE));
        precostrPOR = produto.getPrecoPor().replaceFirst(",", ".");
        precoPOR = Double.valueOf(precostrPOR);

        //////CALCULA O VALOR DO DESCONTO EM PORCENTAGEM////////////////////////////////////////////
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#0");
        floatOFF = ((((precoPOR - precoDE)/precoDE)*100)*-1);
        txtOFF.setText(df.format(floatOFF) + "% OFF");

        /////TESTA SE TEM VALOR PROMOCIONAL/////////////////////////////////////////////////////////
        if (precoPOR >= precoDE){
            txtOFF.setVisibility(View.GONE);
            img_oferta.setVisibility(View.GONE);
            precoDe.setVisibility(View.INVISIBLE);
        }


        nomeProduto.setText(produto.getNome_prod());

        precoDe.setText(dfPreco.format(precoDE));
        precoDe.setPaintFlags(precoDe.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        precoPor.setText(dfPreco.format(precoPOR));


        txtProdCate.setText("Categoria: "+produto.getProdCategoria());
        txtSeqProduto.setText("Código: "+produto.getSeqProduto());
        txtPeriodoProdPromo.setText("Período: "+produto.getInicio()+" - "+produto.getFim());

        try {
            Picasso.get()
                    .load(produto.getImg_prod_ofer())
                    .into(img_prod_ofer);
        }catch (Exception e){
            System.out.println("Erro: "+e);
        }

        Button btnInformacoes = (Button) v.findViewById(R.id.btnInformacoes);
        btnInformacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, listaProduto.class);
                Bundle bundle = new Bundle();

                bundle.putString("id_produto", produto.getId());
                intent.putExtras(bundle);
                Toast.makeText(ctx,"ID_PRODUTO: "+produto.getId(),Toast.LENGTH_LONG).show();

            }
        });

        return v;
    }
}