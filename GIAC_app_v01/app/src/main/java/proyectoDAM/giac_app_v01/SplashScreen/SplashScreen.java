package proyectoDAM.giac_app_v01.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.login.Login;

public class SplashScreen extends AppCompatActivity {

    ImageView portada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        hideSystemUI();


        //1. Establecemos orientación exclusiva de retrato
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        portada = (ImageView) findViewById(R.id.imageV);

        //4. Creamos objeto TimerTask para controlar el tiempo que durara el splashScreen
        TimerTask task = new TimerTask() {
            //4.1. Dentro del objeto task creamos el metodo RUN()
            @Override
            public void run() {
                //4.2. Indiamos que arrancara la siguiente actividad pasado el tiempo indicado
                Intent login = new Intent().setClass(SplashScreen.this, Login.class);
                startActivity(login);
                //4.3. Cerramos esta actividad para que el usuario no pueda volver a ella mediante botón de volver atras
                finish();
            }
        };

        //5. Con el objeto TIMER Simulamos un tiempo en el proceso de carga durante el cual mostramos el SplashScreen
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }

    //METODO QUE ESCONDE TODAS LAS BARRAS DE SISTEMA PARA DEJAR PANTALLA COMPLETA AL SPLASH
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                         View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}