package jubasupermercados.jubasupermercados.jubasupermercados;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PromocaoAdapter extends BaseAdapter {
    private Context ctx;
    private AlertDialog alerta;
    private List<Promocao> lista;
    public PromocaoAdapter(Context ctx2, List<Promocao> lista2){
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Promocao getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;

        if (convertView == null){
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.item_list_emp_promocao, null);
        }else{
            v = convertView;
        }

        final Promocao promocao = getItem(position);

        TextView   descPromocao = (TextView) v.findViewById(R.id.txtPromocao);
        TextView   periodoPromocao = (TextView) v.findViewById(R.id.txtPeriodo);
        TextView   qtdProdutos = (TextView) v.findViewById(R.id.txtQtdProdutos);

        //banerEmpresa.setImageResource(empresa.getBannerEmpresa());



        descPromocao.setText(promocao.getDescPromocao()+" - "+promocao.getSeqPromocao());
        periodoPromocao.setText("Período: "+promocao.getDtInicioPromo()+" - "+promocao.getDtFimPromo());
        qtdProdutos.setText("Produtos em oferta: "+promocao.getQtdProdPromo());


        Button btnListaProdutos = (Button) v.findViewById(R.id.btnListaProdutos);
        btnListaProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(ctx, listaProduto.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("filial", promocao.getNroEmpresa());
                    bundle.putString("seqpromo", promocao.getSeqPromocao());
                    intent.putExtras(bundle);

                    //Toast.makeText(ctx,"SeqPromocao: "+promocao.getSeqPromocao(),Toast.LENGTH_LONG).show();
                    ctx.startActivity(intent, bundle);
                }catch (Exception e){
                    System.out.println("Erro: "+e);
                }
            }
        });


        return v;
    }
}
