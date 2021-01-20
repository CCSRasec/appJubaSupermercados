package jubasupermercados.jubasupermercados.jubasupermercados;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.indicators.IndicatorShape;

////////////////////////////////////////////////////////////////////////////////////////////////////
public class Inicial extends AppCompatActivity {
        //implements NavigationView.OnNavigationItemSelectedListener {
    ////////////////////////////////////////////////////////////////////////////////////////////////
        private String HOST = "http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn";

        String urlImg = "";
        EmpresaAdapter empresaAdapter;
        List<Empresa> lista;

        ListView listViewEmpresa;

        Uri imagemSelecionada;
        Slider slider;
        private LocationRequest mLocationRequest;
        private int REQUEST_CHECK_SETTINGS=613;
        private AlertDialog alerta;
        SimpleDateFormat df;




    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);


        //createLocationRequest();

        ////////////////////////////////////////////////////////////////////////////////////////////
        df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");


        ////////////////////////////////////////////////////////////////////////////////////////////
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //////////INICIALIZANDO BOTÕES//////////////////////////////////////////////////////////////

        ////////SLIDER COM PICASSO//////////////////////////////////////////////////////////////////
        Slider.init(new PicassoImageLoadingService(this));
        setupViews();

        /////////FAB BUTTON/////////////////////////////////////////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////NAV MENNU LATERAL

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */

        /////////////////////////////////////////////////////////////////////////////////////////////

        listViewEmpresa = (ListView) findViewById(R.id.listaEmpresas);
        lista = new ArrayList<Empresa>();
        empresaAdapter = new EmpresaAdapter(Inicial.this,lista);
        listViewEmpresa.setAdapter(empresaAdapter);

        listaEmpresaListView();

    }
    ///////////////////CAIXA DE DIÁLOGO/////////////////////////////////////////////////////////////
    public void caixaDeDialogo(String titulo, String mensage, Context ctx) {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(mensage);
        //define um botão como positivo
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //finishAffinity();
                chamaProximaTela(listaEmpresa.class);
            }
        });

        builder.setNegativeButton("Usar GPS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                askForLocationChange();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    ///////////////////SLIDER///////////////////////////////////////////////////////////////////////
    private void setupViews() {
        //setupToolbar();
        //setupPageIndicatorChooser();
        //setupSettingsUi();
        slider = findViewById(R.id.banner_slider1);


        //delay for testing empty view functionality
        slider.postDelayed(new Runnable() {
            @Override
            public void run() {
                slider.setAdapter(new MainSliderAdapter());
                slider.setSelectedSlide(1);
                slider.setInterval(6500);
            }
        }, 1500);

    }                     //NÃO ESTOU USANDO

    private void setupSettingsUi() {
        final SeekBar intervalSeekBar = findViewById(R.id.seekbar_interval);
        intervalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    seekBar.setProgress(1500);
                    slider.setInterval(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                slider.setInterval(500);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                slider.setInterval(500);
            }
        });

        SeekBar indicatorSizeSeekBar = findViewById(R.id.seekbar_indicator_size);
        indicatorSizeSeekBar.setMax(getResources().getDimensionPixelSize(R.dimen.max_slider_indicator_size));
        indicatorSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    slider.setIndicatorSize(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SwitchCompat loopSlidesSwitch = findViewById(R.id.checkbox_loop_slides);
        loopSlidesSwitch.setChecked(true);
        SwitchCompat mustAnimateIndicators = findViewById(R.id.checkbox_animate_indicators);
        mustAnimateIndicators.setChecked(true);

        loopSlidesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                slider.setLoopSlides(true);
            }
        });

        mustAnimateIndicators.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                slider.setAnimateIndicators(true);
            }
        });

        SwitchCompat hideIndicatorsSwitch = findViewById(R.id.checkbox_hide_indicators);
        hideIndicatorsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    slider.hideIndicators();
                } else {
                    slider.showIndicators();
                }
            }
        });
    }                //NÃO ESTOU USANDO


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView githubSourceImageView = findViewById(R.id.image_github);
        githubSourceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/saeedsh92/Banner-Slider");
                if (Build.VERSION.SDK_INT > 15) {

                } else {
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), "Choose Browser..."));
                }
            }
        });
    }                   //NÃO ESTOU USANDO*/ ///tolbar

    private void setupPageIndicatorChooser() {

        String[] pageIndicatorsLabels = getResources().getStringArray(R.array.page_indicators);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                pageIndicatorsLabels
        );
        Spinner spinner = findViewById(R.id.spinner_page_indicator);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        slider.setIndicatorStyle(IndicatorShape.CIRCLE);
                        break;
                    case 1:
                        slider.setIndicatorStyle(IndicatorShape.DASH);
                        break;
                    case 2:
                        slider.setIndicatorStyle(IndicatorShape.ROUND_SQUARE);
                        break;
                    case 3:
                        slider.setIndicatorStyle(IndicatorShape.SQUARE);
                        break;
                    case 4:
                        slider.setSelectedSlideIndicator(ContextCompat.getDrawable(Inicial.this, R.drawable.selected_slide_indicator));
                        slider.setUnSelectedSlideIndicator(ContextCompat.getDrawable(Inicial.this, R.drawable.unselected_slide_indicator));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }      //NÃO ESTOU USANDO

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void onBackPressed() {
        this.moveTaskToBack(false);

    }

    /*///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicial, menu);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_editar_perfil) {
            // Handle the camera action
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 123);
        } else if (id == R.id.nav_favoritos) {
            System.out.println("Data: "+ (df.format(new Date()))+"\nProduto:"+Build.PRODUCT+"\nModelo:"+Build.MODEL);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.RESULT_OK) {
            if (requestCode == 123) {
                imagemSelecionada = data.getData();
                ImageView imgTeste = (ImageView) this.findViewById(R.id.imgViewPerfilUser);
                imgTeste.setImageURI(imagemSelecionada);
            }
        }

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    //Toast.makeText(this, "GPS Ativado", Toast.LENGTH_SHORT).show();
                    chamaProximaTela(listaEmpresa.class);
                    break;
                case Activity.RESULT_CANCELED:
                    //Toast.makeText(this, "O usuário preferiu não ativar o GPS"
                    //        , Toast.LENGTH_SHORT).show();
                    caixaDeDialogo("Vamos Economizar!","Você preferiu não tulizar" +
                            " o GPS para buscar empresas na sua cidade, mas não se preocupe, você" +
                            " pode pesquisar qualquer cidade do MUNDO!\nBasta usar o campo de" +
                            " pesquisa no canto superior direito do seu celular, caso haja empresas" +
                            " parceiras na cidade elas irão aparecer!",this);
                    break;
            }
        }
    }
    */

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

    ////////////EVENTO DOS SUPERMERCADOS///////////////////////////////////////////////////////

    ////////CONECTA NO WEBSERVICE E LISTA AS EMPRESAS///////////////////////////////////////////////1,3,4,5,6,7
    public void listaEmpresaListView() {
        final String url = HOST + "/listaEmpresas.php";
        Ion.with(Inicial.this)
                .load(url)
                .setBodyParameter("filiais","6")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        // do stuff with the result or error
                        if (result.size() > 0) {
                            for (int i = 0; i < result.size(); i++) {
                                JsonObject obj = result.get(i).getAsJsonObject();
                                final Empresa empresa = new Empresa();

                                empresa.setIdEmpresa(obj.get("ID").getAsString());
                                empresa.setFantasia(obj.get("FANTASIA").getAsString());
                                empresa.setCidade(obj.get("CIDADE").getAsString());
                                empresa.setUf(obj.get("UF").getAsString());
                                empresa.setLogradouro(obj.get("LOGRADOURO").getAsString());
                                empresa.setTelefone(obj.get("TELEFONE").getAsString());
                                empresa.setAbre(obj.get("ABRE").getAsString());
                                empresa.setFecha(obj.get("FECHA").getAsString());
                                empresa.setGerente(obj.get("GERENTE").getAsString());
                                urlImg = obj.get("IMG").getAsString();
                                empresa.setImg(urlImg);

                                lista.add(empresa);
                            }
                            empresaAdapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                });
        empresaAdapter.notifyDataSetChanged();
    }

    ////////VERIFICA SE O GPS TA ATIVO//////////////////////////////////////////////////////////////
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(100);
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
                //Toast.makeText(Inicial.this, "GPS já está ativo"
                //        , Toast.LENGTH_SHORT).show();
                chamaProximaTela(listaEmpresa.class);
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(Inicial.this
                                , REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException ignored) {
                    }
                }
            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void chamaProximaTela(Class destino){
        Intent intent = new Intent(this,destino);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}