package proyectoDAM.giac_app_v01.menuPrincipal_U;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONException;
import org.json.JSONObject;

import proyectoDAM.giac_app_v01.Asistencia.MenuPartes;
import proyectoDAM.giac_app_v01.Ayuda.PopUp_tipocon;
import proyectoDAM.giac_app_v01.DocumentosUsuario.MenuArchivos;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.registraAccidentes.PopUp_Acciones;
import proyectoDAM.giac_app_v01.registraIncidencias.RegistraDatosIn;


public class MenuPrincipal_U extends AppCompatActivity {

    // Atributos:
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123; // Damos valor al permiso.
    private String datosUsuario,email;
    String idusuario;
    String nombreusu;
    private ViewPager2 viewPager2; //Menu de acciones
    private TextView tvBbienvenida; // String de Bienvenida de Usuario

    // Array de imagenes de acciones a realizar
    private int[] imagenes = {R.drawable.incidencia,R.drawable.accidente,R.drawable.asistencia,
            R.drawable.archivos, R.drawable.ayuda};
    private MenuAdapter menuAdapter; // Activity del menu de acciones a realizar
    private int cont; //Contador de imagenes en array para interactuar.

    // Tipos Botones con acciones que se utilizan en menu principal:
    private Button btnUser, btnVehiculos, btnUbicacion;
    private ImageButton btnSos;
    private TextView tvTutorial;


    // Metodo onCreate
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principalu);

        // Traemos un string con todos los datos del trabajador logueado que
        // convertimos en un objeto JSON para poder extraer los datos que
        // necesitemos en cada momento.
        Bundle extras = getIntent().getExtras();
        datosUsuario = extras.getString("Usuario");
        JSONObject jsonDatosTrab;
        try {
            jsonDatosTrab = new JSONObject(datosUsuario);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        // Damos valor a los elementos del menu principal:
        tvBbienvenida = findViewById(R.id.tvBienvenida);

        try {
            idusuario = jsonDatosTrab.getString("Id_Usuario");
            nombreusu = jsonDatosTrab.getString("Nombre");
            email  = jsonDatosTrab.getString("Email");
            //tvBbienvenida.setText("¡ Bienvenido " + jsonDatosTrab.getString("Nombre") + " !");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Damos valor a los elementos del menu principal:
        //tvBbienvenida = findViewById(R.id.tvBienvenida);
        tvBbienvenida.setText("¡ Bienvenido " + nombreusu + " !");
        viewPager2 = findViewById(R.id.viewpager);
        menuAdapter = new MenuAdapter(imagenes);
        btnUser = findViewById(R.id.btnUsu);
        btnVehiculos = findViewById(R.id.btncoche);
        btnUbicacion = findViewById(R.id.btnmapa);
        btnSos = findViewById(R.id.btnsos);
        tvTutorial = findViewById(R.id.tvtutorial);

        // Aplicamos acciones a viewPager2:
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager2.setAdapter(menuAdapter);

        // Lo transformamos en funcion a la cuerda de opciones que se muestra en pantalla
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (cont) {
                            // DESDE ESTE PUNTO SE INICIAN TODAS LAS INTENT DE LAS ACCIONS A REALIZAR POR EL USUARIO EN EL SWIPEVIEW
                            case 0:
                                Intent incidencia = new Intent (view.getContext(), RegistraDatosIn.class);
                                incidencia.putExtra("idusuario", idusuario);
                                startActivity(incidencia);
                                break;
                            case 1:
                                Intent accidente = new Intent (view.getContext(), PopUp_Acciones.class);
                                accidente.putExtra("idusuario", idusuario);
                                startActivity(accidente);
                                break;
                            case 2: //Intent asistencia = new Intent (view.getContext(), AsisteciaActivity.class);
                                Intent asistenciaPartes = new Intent (view.getContext(), MenuPartes.class);
                                asistenciaPartes.putExtra("idusuario", idusuario);
                                startActivity(asistenciaPartes);
                                break;
                            case 3: //Intent archivos = new Intent (view.getContext(), ArchivosActivity.class);
                                Intent archivos = new Intent (view.getContext(), MenuArchivos.class);
                                startActivity(archivos);
                                break;
                            case 4: //Intent ayuda = new Intent (view.getContext(), AyudaActivity.class);
                                Intent ayuda = new Intent (view.getContext(), PopUp_tipocon.class);
                                ayuda.putExtra("email", email);
                                startActivity(ayuda);
                                break;
                        }
                    }
                });
            }
        });
        // Proporcionamos un valor Integer a cada una de las posiciones del array de imagenes en funcion de la pagina seleccionada
        viewPager2.setPageTransformer(transformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        cont = 0;
                        break;
                    case 1:
                        cont = 1;
                        break;
                    case 2:
                        cont = 2;
                        break;
                    case 3:
                        cont = 3;
                        break;
                    case 4:
                        cont = 4;
                        break;
                    default:
                        cont = 0;
                        break;
                }
            }
        });

        // Aplicamos acciones a los botones:
        //Accion texto de solicitud de Ayuda nos redirige a canal de Youtube de la App donde estan los tutoriales de uso de la aplicacion.
        tvTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.youtube.com/channel/UCYBjK1y_U8Ge020g4FVWiuQ");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        //Acciones boton SOS:
        //1. Onclick: Saca un Toast Indicando que tiene que dejar el dedo pulsado 5 segundos asi
        //            evitamos que se marque al 112 por error.
        btnSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toasted("¡Manten pulsado 5 segundos para llamar!");
            }
        });

        //2. OnLongclick: Mantiene pulsado el boton para llamar.
        btnSos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MenuPrincipal_U.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        // Se tiene permiso
                    } else {
                        ActivityCompat.requestPermissions(MenuPrincipal_U.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                } else {
                    // No se necesita requerir permiso OS menos a 6.0.
                }

                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:+34657174544"));
                startActivity(i);
                return true;
            }
        });

        //Acciones boton Usuarios, administrara la edicion de los datos del usuario
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miperfilintent = new Intent(getApplicationContext(), EditaPerfil.class);
                miperfilintent.putExtra("idusuario", idusuario);
                startActivity(miperfilintent);
            }
        });


        //Acciones boton Vehiculos, administrara el menu de vehiculos pasando como dato ID_Usuario:
        btnVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Algo va BIEN", Toast.LENGTH_SHORT).show();
                Intent vehiculosintent = new Intent(getApplicationContext(), MenuVehiculo.class);
                vehiculosintent.putExtra("idusuario", idusuario);
                startActivity(vehiculosintent);
            }
        });

        //Acciones boton Mapas, dara tu localizacion en tiempo real (Direccion y coordenadas) y Ubicacion en Mapa:
        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Algo va BIEN", Toast.LENGTH_SHORT).show();
                Intent ubicacionintent = new Intent(getApplicationContext(), PopupDirActivity.class);
                startActivity(ubicacionintent);
            }
        });


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // El usuario acepto los permisos.
                    Toast.makeText(this, "Gracias, aceptaste los permisos requeridos para el correcto funcionamiento de esta aplicación.", Toast.LENGTH_SHORT).show();
                }else{
                    // Permiso denegado.
                    Toast.makeText(this, "No se aceptó permisos", Toast.LENGTH_SHORT).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void toasted(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }



}