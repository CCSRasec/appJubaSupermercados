package jubasupermercados.jubasupermercados.jubasupermercados;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Login extends AppCompatActivity {

    String cidade;
    String jaPerguntou = "Não";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callConnection();

        Button btnEntrar                   = (Button) findViewById(R.id.btnLogin);
        final EditText txtEmail            = (EditText) findViewById(R.id.txtEmailLogin);
        final EditText txtSenha            = (EditText) findViewById(R.id.txtSenhaLogin);
        final ProgressBar progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin2);
        final TextView textViewLogin       = (TextView) findViewById(R.id.txtCarregando2);

        Intent serviceIntent = new Intent();
        boolean dataUrl = true;
        serviceIntent.putExtra("download_url", dataUrl);
        //final int RSS_JOB_ID = 1000;
        //RSSPullService.enqueueWork(this, RSSPullService.class, RSS_JOB_ID, serviceIntent);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        String TAG="";
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = (token);
                        Log.d(TAG, msg);
                        //Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                        //System.out.println(token);
                    }
                });

        btnEntrar.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        if (!txtEmail.getText().toString().isEmpty()) {
                            if (!txtSenha.getText().toString().isEmpty() && txtSenha.length() >= 5) {
                                ////////////////////////////////////////////////////////////////////
                                ///VERIFICA O LOGIN E SENHA
                                String email, senha;
                                email = txtEmail.getText().toString();
                                senha = txtSenha.getText().toString();

                                txtEmail.setVisibility(View.INVISIBLE);
                                txtSenha.setVisibility(View.INVISIBLE);

                                progressBarLogin.setVisibility(View.VISIBLE);
                                textViewLogin.setVisibility(View.VISIBLE);

                                ////////////////////////////////////////////////////////////////////
                                ////PEGA OS DADOS PARA A TAB LOG_LOGIN
                                ///---DATA
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
                                Date data = new Date();
                                Date data_atual = data;
                                String data_completa = dateFormat.format(data_atual);
                                ///-----------------------------------------------------------------
                                ///MODELO APARELHO E CIDADE
                                callConnection();
                                if (cidade == null) {
                                    cidade = "GPS Inativo, clicou fora - Não Localizado";
                                }
                                ////////////////////////////////////////////////////////////////////
                                Ion.with(Login.this)
                                        .load("http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn/verificaLogin.php")
                                        .setBodyParameter("email", email)
                                        .setBodyParameter("senha", senha)
                                        .setBodyParameter("dtLogin", data_completa)
                                        .setBodyParameter("aparelho", Build.MODEL)
                                        .setBodyParameter("cidade", cidade)
                                        .asJsonArray()
                                        .setCallback(new FutureCallback<JsonArray>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonArray result) {
                                                // do stuff with the result or error
                                                if (result.size() > 0) {
                                                    //finishAffinity();
                                                    //txtEmail.setVisibility(View.VISIBLE);
                                                    //txtSenha.setVisibility(View.VISIBLE);
                                                    Intent intent = new Intent(Login.this, Inicial.class);
                                                    startActivity(intent);
                                                } else {
                                                    //Cria o gerador do AlertDialog
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                                    //define o titulo
                                                    builder.setTitle("Encontramos um problema");
                                                    //define a mensagem
                                                    builder.setMessage("E-mail ou Senha incorretos.");

                                                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface arg0, int arg1) {
                                                            //finishAffinity();
                                                        }
                                                    });

                                                    //cria o AlertDialog
                                                    AlertDialog alerta = builder.create();
                                                    //Exibe
                                                    alerta.show();

                                                    txtEmail.setVisibility(View.VISIBLE);
                                                    txtSenha.setVisibility(View.VISIBLE);

                                                    progressBarLogin.setVisibility(View.INVISIBLE);
                                                    textViewLogin.setVisibility(View.INVISIBLE);
                                                }
                                            }
                                        });
                            } else {
                                txtSenha.setHintTextColor(Color.RED);
                                txtSenha.setText("");
                                txtSenha.requestFocus();
                            }
                        } else {
                            txtEmail.setHintTextColor(Color.RED);
                            txtEmail.requestFocus();
                        }
                    }
                });


    }

    public void jobIntentService (){

    }


    public void onBackPressed() {
        this.moveTaskToBack(false);
    }

    public void onClickBtnCriarConta(View view) {
        Intent intent = new Intent(Login.this, CadastroUsuario.class);
        startActivity(intent);
    }

    public void onClickBtnEsqueciSenha(View view) {
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
    private void callConnection() {
        while (ActivityCompat.checkSelfPermission(Login.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Login.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, BIND_ABOVE_CLIENT);
        }


        final LocationManager locationManager = (LocationManager) getSystemService(Login.LOCATION_SERVICE);

        long tempo = 500 * 1; //5 minutos
        float distancia = 30; // 30 metros
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER , tempo , distancia,  new LocationListener()  {

            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                //Toast.makeText(getApplicationContext(),"Status alterado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderEnabled(String arg0) {
                //Toast.makeText(getApplicationContext(), "Provider Habilitado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String arg0) {
                //Toast.makeText(getApplicationContext(), "Provider Desabilitado", Toast.LENGTH_LONG).show();
                if(jaPerguntou.equals("Não")){
                    final LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                    // Verifica se o GPS está ativo
                    boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    // Caso não esteja ativo abre um novo diálogo com as configurações para
                    // realizar se ativamento
                    if (!enabled) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        //define o titulo
                        builder.setTitle("Olá cliente amigo!");
                        //define a mensagem
                        builder.setMessage("Para otimizar sua experiência e para atendermos melhor as suas preferencias o uso do GPS é muito importante, mas fique tranquilo pois não é obrigatório, você pode aproveitar nossas ofertas mesmo sem ativar o GPS.");
                        builder.setNegativeButton("Ativar GPS", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });

                        builder.setPositiveButton("Não quero ativar o GPS", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                cidade = "GPS Inativo - Não Ativou GPS";
                                jaPerguntou = "Sim";
                            }
                        });
                        //cria o AlertDialog
                        AlertDialog alerta = builder.create();
                        //Exibe
                        alerta.show();
                    }
                }
            }

            @Override
            public void onLocationChanged(Location location) {
                if( location != null ){
                    String cid, bairro, estado,cep;
                    try {
                        cid = buscarEndereco(location.getLatitude(), location.getLongitude()).getSubAdminArea();

                        bairro = buscarEndereco(location.getLatitude(), location.getLongitude()).getSubLocality();
                        estado = buscarEndereco(location.getLatitude(), location.getLongitude()).getAdminArea();
                        cep = buscarEndereco(location.getLatitude(), location.getLongitude()).getPostalCode();
                        cidade = bairro + ", " + cid + " - " + estado + " - " + cep;
                        //Toast.makeText(getApplicationContext(), "Dados:"+cidade, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, null );
    }
}