package jubasupermercados.jubasupermercados.jubasupermercados;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmpresaAdapter extends BaseAdapter {
    private Context ctx;
    private AlertDialog alerta;
    private List<Empresa> lista;

    public EmpresaAdapter(Context ctx2, List<Empresa> lista2) {
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Empresa getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;


        final Empresa empresa = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.item_list_empresa, null);
        } else {
            v = convertView;
        }


        TextView fantasia = (TextView) v.findViewById(R.id.txtNomeEmpresa);
        TextView cidade   = (TextView) v.findViewById(R.id.cidadeEmpresa);
        TextView logradouro   = (TextView) v.findViewById(R.id.txtLogradouro);
        TextView telefone   = (TextView) v.findViewById(R.id.txtFone);
        TextView horarios   = (TextView) v.findViewById(R.id.txtHorarios);
        TextView gerente   = (TextView) v.findViewById(R.id.txtGerente);
        TextView aberto_fechado   = (TextView) v.findViewById(R.id.txt_aberto_Fechado);

        Button btnEmpresa = (Button) v.findViewById(R.id.btnJuba);

        ImageView imgEmpresa = (ImageView) v.findViewById(R.id.imgEmpresa);

        ///////////////////////////////////////////////////////////////////////////////////////////

        fantasia.setText(empresa.getFantasia());
        cidade.setText(empresa.getCidade()+", "+empresa.getUf());
        logradouro.setText("Logradouro: "+empresa.getLogradouro());
        telefone.setText("Telefone: "+empresa.getTelefone());
        horarios.setText("Horário: "+empresa.getAbre()+" - "+empresa.getFecha());
        gerente.setText("Gerente: "+empresa.getGerente());

        Picasso.get()
                .load(empresa.getImg())
                .into(imgEmpresa);

        btnEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, listaProduto.class);
                Bundle bundle = new Bundle();

                bundle.putString("filial", empresa.getIdEmpresa());
                bundle.putString("nomeCidade",empresa.getCidade()+", "+empresa.getUf());
                bundle.putString("img",empresa.getImg());
                intent.putExtras(bundle);
                ctx.startActivity(intent,bundle);
                //Toast.makeText(ctx,"idEmpresa: "+empresa.getIdEmpresa(),Toast.LENGTH_LONG).show();

            }
        });


        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        // OU
        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH.ss");

        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();

        String data_completa = dateFormat.format(data_atual);

        String hora_atual = dateFormat_hora.format(data_atual);

        //Log.i("data_completa", data_completa);
        //Log.i("data_atual", data_atual.toString());
        Log.i("hora_atual", hora_atual); // Esse é o que você quer

        String dMin=null,dMax=null;
        String dAgora;
        Date abre = null, fecha = null;


        dAgora = hora_atual;
        try {
            abre = dateFormat.parse(empresa.getAbre());

            fecha = dateFormat.parse(empresa.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dMin = dateFormat_hora.format(abre);
        dMax = dateFormat_hora.format(fecha);

        if (Float.parseFloat(dAgora) >= Float.parseFloat(dMin)  && Float.parseFloat(dAgora) <= Float.parseFloat(dMax) ){
            aberto_fechado.setText("Aberto");
            aberto_fechado.setTextColor(Color.parseColor("#008000"));

        }else{
            aberto_fechado.setText("Fechado");
            aberto_fechado.setTextColor(Color.RED);
        }


        return v;
    }
}


