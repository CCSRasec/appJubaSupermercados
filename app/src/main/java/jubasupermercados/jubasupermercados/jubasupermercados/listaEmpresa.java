package jubasupermercados.jubasupermercados.jubasupermercados;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class listaEmpresa extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String HOST = "http://jubasupermercados.jubasupermercados/mnbcgfhdwokehfwPHPjijjwnenqwekjn";
    private AlertDialog alerta;
    String urlImg = "";
    Boolean ja = false;
    String cdAnt;
    EmpresaAdapter empresaAdapter;
    List<Empresa> lista;
    ListView listViewEmpresas;

    //PARA USO DO GPS///////////////////////////////////////////////////////////////////////////////
    private LocationRequest mLocationRequest;
    private int REQUEST_CHECK_SETTINGS = 613;
    boolean GPSAtivo;
    double latitude;
    double longitude;
    String nomeCidade;
    String estadoCidade;
    String paisCidade;
    Address enderecoCidade;
    private Object savedInstanceState;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_empresa);
        //////////TOOLBAR///////////////////////////////////////////////////////////////////////////
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ////////////////////////////////////////////////////////////////////////////////////////////

        //String empresaID;
        //TextView txtEmpresaID = (TextView) findViewById(R.id.txtID);



        ////////////////////////////////////////////////////////////////////////////////////////////
        listViewEmpresas = (ListView) findViewById(R.id.listaEmpresas);
        lista = new ArrayList<Empresa>();
        empresaAdapter = new EmpresaAdapter(listaEmpresa.this, lista);
        listViewEmpresas.setAdapter(empresaAdapter);

        ////////////////////////////////////////////////////////////////////////////////////////////
        createLocationRequest();
        ////////////////////////////////////////////////////////////////////////////////////////////

    }
    ///////////////BUSCA O ENDEREÇO/////////////////////////////////////////////////////////////////

    /**
     * @param latitude
     * @param longitude
     * @return
     * @throws IOException
     */
    public Address buscarEndereco(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if (addresses.size() > 0) {
            address = addresses.get(0);
        }
        return address;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();


        searchView.setOnQueryTextListener(onSearch());
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String cidadeNome) {
                if (ja == false) {
                    if (lista.size() >= 0) {
                        lista.clear();
                        cdAnt = cidadeNome;
                        ja = true;
                    }
                } else {
                    if (cdAnt != cidadeNome) {
                        ja = false;
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //ja = false;
                return false;
            }
        };
    }

    /////RESULTADO DA TELA//////////////////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    //Toast.makeText(this, "GPS Ativado", Toast.LENGTH_SHORT).show();
                    //enabledGPS = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    //GPS FOI ATIVADO
                    break;
                case Activity.RESULT_CANCELED:
                    //Toast.makeText(this, "O usuário preferiu não ativar o GPS"
                    //      , Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.search) {
            //quando clickar no icone de pesquisa
            //alert("Localizando");
        }
        return super.onOptionsItemSelected(item);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void msgAlerta(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onQueryTextSubmit(String s) {
        // User pressed the search button
        // Usuário clicou para pesquisar

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        // Usuário digitou algo no campo de pesquisa


        return false;
    }

    ////////VERIFICA SE O GPS TA ATIVO//////////////////////////////////////////////////////////////
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void askForLocationChange() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //Toast.makeText(listaEmpresa.this, "GPS já está ativo"
                //      , Toast.LENGTH_SHORT).show();
                //GPS JÁ ESTÁ ATIVO
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(listaEmpresa.this
                                , REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException ignored) {
                    }
                }
            }
        });
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void chamaListaProdEmpresa(Context ctx) {
        Intent intent = new Intent(this, listaProduto.class);
        startActivity(intent);
    }

    /* Função caixa de dialogo */
    private void cxaDialogErroNet(String titulo, String mensage) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(mensage);
        //define um botão como positivo
        builder.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //finishAffinity();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }


    /////PEGA LOCALIZAÇÃO PELO GPS E RETORNA A LACATION/////////////////////////////////////////////

    /////FUNÇÃO CHAMA PROXIMA TELA//////////////////////////////////////////////////////////////////
    public void chamaProximaTela(){
        //Intent intent = new Intent(this,listaProduto.class);
        //startActivity(intent);
    }
}