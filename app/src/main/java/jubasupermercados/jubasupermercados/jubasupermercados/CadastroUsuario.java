package jubasupermercados.jubasupermercados.jubasupermercados;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CadastroUsuario extends AppCompatActivity {
    private String HOST = "http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn";

    String cpf, nome, sobreNome, email, senha, reSenha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        Button btnConfirmarConta = (Button)findViewById(R.id.btnConfirmarConta);
        Button btnJatenhoConta = (Button)findViewById(R.id.btnJaTenhoConta);

        final EditText txtCPF = (EditText) findViewById(R.id.txtCPF);
        final EditText txtNome = (EditText) findViewById(R.id.txtNome);
        final EditText txtSobreNome = (EditText) findViewById(R.id.txtSobreNome);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        final EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
        final EditText txtReSenha = (EditText) findViewById(R.id.txtReSenha);


        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        final TextView textView = (TextView) findViewById(R.id.txtCarregando);

        btnConfirmarConta.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        if (!txtCPF.getText().toString().isEmpty() && txtCPF.length() <= 11) {
                            cpf = txtCPF.getText().toString();
                            if (!txtNome.getText().toString().isEmpty()) {
                                nome = txtNome.getText().toString();
                                if (!txtSobreNome.getText().toString().isEmpty()) {
                                    sobreNome = txtSobreNome.getText().toString();
                                    if (!txtEmail.getText().toString().isEmpty()) {
                                        email = txtEmail.getText().toString();
                                        if (txtSenha.getText().toString().isEmpty() && txtSenha.length() <= 5){
                                            txtSenha.setHintTextColor(Color.RED);
                                            txtSenha.requestFocus();
                                        }else{
                                            if(txtSenha.getText().toString().equals(txtReSenha.getText().toString())){
                                                senha = txtSenha.getText().toString();

                                                ////VERIFICA SE O CPF JÁ ESTA CADASTRADO////////////

                                                txtCPF.setVisibility(View.INVISIBLE);
                                                txtNome.setVisibility(View.INVISIBLE);
                                                txtSobreNome.setVisibility(View.INVISIBLE);
                                                txtEmail.setVisibility(View.INVISIBLE);
                                                txtSenha.setVisibility(View.INVISIBLE);
                                                txtReSenha.setVisibility(View.INVISIBLE);

                                                progressBar.setVisibility(View.VISIBLE);
                                                textView.setVisibility(View.VISIBLE);

                                                Ion.with(CadastroUsuario.this)
                                                        .load("http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn/buscaCPF.php")
                                                        .setBodyParameter("cpf",cpf)
                                                        .asJsonArray()
                                                        .setCallback(new FutureCallback<JsonArray>() {
                                                            @Override
                                                            public void onCompleted(Exception e, JsonArray result) {
                                                                // do stuff with the result or error
                                                                if (result.size() > 0) {
                                                                    //Cria o gerador do AlertDialog
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroUsuario.this);
                                                                    //define o titulo
                                                                    builder.setTitle("Encontramos um problema");
                                                                    //define a mensagem
                                                                    builder.setMessage("O CPF informado já esta cadastrado.");
                                                                    //define um botão como positivo
                                                                    builder.setNegativeButton("Voltar para login", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface arg0, int arg1) {
                                                                            //finishAffinity();
                                                                            Intent intent = new Intent(CadastroUsuario.this, Login.class);
                                                                            startActivity(intent);
                                                                        }
                                                                    });
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface arg0, int arg1) {
                                                                            //finishAffinity();
                                                                            txtCPF.setVisibility(View.VISIBLE);
                                                                            txtNome.setVisibility(View.VISIBLE);
                                                                            txtSobreNome.setVisibility(View.VISIBLE);
                                                                            txtEmail.setVisibility(View.VISIBLE);
                                                                            txtSenha.setVisibility(View.VISIBLE);
                                                                            txtReSenha.setVisibility(View.VISIBLE);

                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            textView.setVisibility(View.INVISIBLE);
                                                                        }
                                                                    });
                                                                    //cria o AlertDialog
                                                                    AlertDialog alerta = builder.create();
                                                                    //Exibe
                                                                    alerta.show();
                                                                }else{
                                                                    Ion.with(CadastroUsuario.this)
                                                                            .load("http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn/buscaEmail.php")
                                                                            .setBodyParameter("email",email)
                                                                            .asJsonArray()
                                                                            .setCallback(new FutureCallback<JsonArray>() {
                                                                                @Override
                                                                                public void onCompleted(Exception e, JsonArray result) {
                                                                                    // do stuff with the result or error
                                                                                    if (result.size() > 0) {
                                                                                        //Cria o gerador do AlertDialog
                                                                                        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroUsuario.this);
                                                                                        //define o titulo
                                                                                        builder.setTitle("Encontramos um problema");
                                                                                        //define a mensagem
                                                                                        builder.setMessage("O E-mail informado já esta cadastrado.");
                                                                                        //define um botão como positivo
                                                                                        builder.setNegativeButton("Voltar para login", new DialogInterface.OnClickListener() {
                                                                                            public void onClick(DialogInterface arg0, int arg1) {
                                                                                                //finishAffinity();
                                                                                                Intent intent = new Intent(CadastroUsuario.this, Login.class);
                                                                                                startActivity(intent);
                                                                                            }
                                                                                        });
                                                                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                            public void onClick(DialogInterface arg0, int arg1) {
                                                                                                //finishAffinity();
                                                                                                txtCPF.setVisibility(View.VISIBLE);
                                                                                                txtNome.setVisibility(View.VISIBLE);
                                                                                                txtSobreNome.setVisibility(View.VISIBLE);
                                                                                                txtEmail.setVisibility(View.VISIBLE);
                                                                                                txtSenha.setVisibility(View.VISIBLE);
                                                                                                txtReSenha.setVisibility(View.VISIBLE);

                                                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                                                textView.setVisibility(View.INVISIBLE);
                                                                                            }
                                                                                        });
                                                                                        //cria o AlertDialog
                                                                                        AlertDialog alerta = builder.create();
                                                                                        //Exibe
                                                                                        alerta.show();
                                                                                    }else{
                                                                                        ////CADASTRA O USUÁRIO NOVO/////

                                                                                        txtCPF.setVisibility(View.INVISIBLE);
                                                                                        txtNome.setVisibility(View.INVISIBLE);
                                                                                        txtSobreNome.setVisibility(View.INVISIBLE);
                                                                                        txtEmail.setVisibility(View.INVISIBLE);
                                                                                        txtSenha.setVisibility(View.INVISIBLE);
                                                                                        txtReSenha.setVisibility(View.INVISIBLE);

                                                                                        progressBar.setVisibility(View.VISIBLE);
                                                                                        textView.setVisibility(View.VISIBLE);

                                                                                        Ion.with(CadastroUsuario.this)
                                                                                                .load("http://ofertafacil.net/mnbcgfhdwokehfwPHPjijjwnenqwekjn/cadUser.php")
                                                                                                .setBodyParameter("cpf", cpf)
                                                                                                .setBodyParameter("nome", nome)
                                                                                                .setBodyParameter("sobreNome", sobreNome)
                                                                                                .setBodyParameter("email", email)
                                                                                                .setBodyParameter("senha", senha)
                                                                                                .asJsonArray()
                                                                                                .setCallback(new FutureCallback<JsonArray>() {
                                                                                                    @Override
                                                                                                    public void onCompleted(Exception e, JsonArray result) {
                                                                                                        // do stuff with the result or error
                                                                                                        if (result.size() > 0) {
                                                                                                            //Cria o gerador do AlertDialog
                                                                                                            final AlertDialog.Builder builder = new AlertDialog.Builder(CadastroUsuario.this);
                                                                                                            //define o titulo
                                                                                                            builder.setTitle("Sucesso!");
                                                                                                            //define a mensagem
                                                                                                            builder.setMessage("Usuário cadastrado com sucesso! Você receberá um e-mail para ativar a conta. Verifique o lixo eletrônico e spam em casos de não encontrar o e-mail.");
                                                                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                                public void onClick(DialogInterface arg0, int arg1) {
                                                                                                                    //finishAffinity();
                                                                                                                    txtCPF.setVisibility(View.VISIBLE);
                                                                                                                    txtNome.setVisibility(View.VISIBLE);
                                                                                                                    txtSobreNome.setVisibility(View.VISIBLE);
                                                                                                                    txtEmail.setVisibility(View.VISIBLE);
                                                                                                                    txtSenha.setVisibility(View.VISIBLE);
                                                                                                                    txtReSenha.setVisibility(View.VISIBLE);

                                                                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                                                                    textView.setVisibility(View.INVISIBLE);


                                                                                                                    Intent intent = new Intent(CadastroUsuario.this, Login.class);
                                                                                                                    startActivity(intent);
                                                                                                                }
                                                                                                            });
                                                                                                            //cria o AlertDialog
                                                                                                            AlertDialog alerta = builder.create();
                                                                                                            //Exibe
                                                                                                            alerta.show();
                                                                                                        }
                                                                                                    }
                                                                                                });

                                                                                    }
                                                                                }
                                                                                });
                                                                }
                                                            }
                                                        });
                                            }else{
                                                txtSenha.setHintTextColor(Color.RED);
                                                txtReSenha.setText("");
                                                txtReSenha.setHint("Senhas não conferem");
                                                txtReSenha.setHintTextColor(Color.RED);
                                                txtSenha.requestFocus();
                                            }
                                        }
                                    }else{
                                        txtEmail.setHintTextColor(Color.RED);
                                        txtEmail.requestFocus();
                                    }
                                }else{
                                    txtSobreNome.setHintTextColor(Color.RED);
                                    txtSobreNome.requestFocus();
                                }
                            }else{
                                txtNome.setHintTextColor(Color.RED);
                                txtNome.requestFocus();
                            }
                        }else{
                            txtCPF.setHintTextColor(Color.RED);
                            txtCPF.requestFocus();
                        }
                    }
                });

        btnJatenhoConta.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        cxaDialogo("Você já tem uma conta ?", "Deseja realmente sair da tela de cadastro ?");
                    }
                });
    }

    private void cxaDialogo(String titulo, String mensagem){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //define o titulo
        builder.setTitle(titulo);

        //define a mensagem
        builder.setMessage(mensagem);

        //define um botão como positivo
        builder.setNegativeButton("Voltar para login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //finishAffinity();
                Intent intent = new Intent(CadastroUsuario.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //finishAffinity();
            }
        });
        //cria o AlertDialog
        AlertDialog alerta = builder.create();

        //Exibe
        alerta.show();
    }
}
