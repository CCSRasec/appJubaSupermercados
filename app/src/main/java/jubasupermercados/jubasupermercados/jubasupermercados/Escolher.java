package jubasupermercados.jubasupermercados.jubasupermercados;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Escolher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher);
    }
    /////////////////////////////////////////////

    /* Função Chama a proxima tela */
    public void onClickBtnEmpresa(View view){

    }
    public void onClickBtnSAC(View view){
            String url = "http://jubasupermercados.jubasupermercados/adm/index.php";
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://" + url;
            }

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
