package proyectoDAM.giac_app_v01.menuPrincipal_T;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_T.datosTrabajador.DatosTrabajador;
import proyectoDAM.giac_app_v01.menuPrincipal_T.incidenciasAsignadas.IncidenciasAsignadas;
import proyectoDAM.giac_app_v01.menuPrincipal_T.listadoAccidentes.ListadoAccidentes;
import proyectoDAM.giac_app_v01.menuPrincipal_T.listadoIncidencias.ListadoIncidencias;
import proyectoDAM.giac_app_v01.menuPrincipal_T.listadoUsuarios.ListadoUsuarios;
import proyectoDAM.giac_app_v01.menuPrincipal_T.listadoVehiculos.ListadoVehiculos;

public class MenuPrincipal_T extends AppCompatActivity {

    // Atributos:
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123; // Damos valor al permiso.
    private String datosTrabajador; // Datos del Trabajador registrado en login
    private ViewPager2 viewPager2; //Menu de acciones
    private TextView tvBbienvenida; // String de Bienvenida de Usuario

    // Array de imagenes de acciones a realizar
    private int[] imagenes = {R.drawable.incidencia,R.drawable.asistencia,R.drawable.accidente,R.drawable.archivos};
    private MenuAdapter_T menuAdapter_T; // Activity del menu de acciones a realizar
    private int cont; //Contador de imagenes en array para interactuar.

    // Tipos Botones con acciones que se utilizan en menu principal:
    private Button btnTrab, btnListaVehiculos, btnListaUsuarios, btnListaAccidentes, btnListaIncidencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_t);

        // Traemos un string con todos los datos del trabajador logueado que
        // convertimos en un objeto JSON para poder extraer los datos que
        // necesitemos en cada momento.
        Bundle extras = getIntent().getExtras();
        datosTrabajador = extras.getString("Trabajador");
        JSONObject jsonDatosTrab;
        try {
            jsonDatosTrab = new JSONObject(datosTrabajador);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Damos valor a los elementos del menu principal:
        tvBbienvenida = findViewById(R.id.tvBienvenidaTrab);
        try {
            tvBbienvenida.setText("¡ Bienvenido " + jsonDatosTrab.getString("Nombre") + " !");
            //COGEMOS LA ID DEL TRABAJADOR
            String idTrabajador = jsonDatosTrab.getString("Id_Empleado");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        viewPager2 = findViewById(R.id.viewpagerTrab);
        menuAdapter_T = new MenuAdapter_T(imagenes);
        btnTrab = findViewById(R.id.btnTrab);
        btnListaVehiculos = findViewById(R.id.btnListaCoches);
        btnListaUsuarios = findViewById(R.id.btnListaUser);
        btnListaAccidentes = findViewById(R.id.btnListaAccidentes);
        btnListaIncidencias = findViewById(R.id.btnListaIncidencias);

        // Aplicamos acciones a viewPager2:
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager2.setAdapter(menuAdapter_T);

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
                            //
                            case 0:
                                Intent IncidenciasAsignadas = new Intent (view.getContext(), IncidenciasAsignadas.class);
                                //COGEMOS LA ID DEL TRABAJADOR Y LA PASAMOS A LA NUEVA ACTIVITY
                                String idTrabajador = null;
                                try {
                                    idTrabajador = jsonDatosTrab.getString("Id_Empleado");
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                IncidenciasAsignadas.putExtra("idTrabajador", idTrabajador);
                                startActivity(IncidenciasAsignadas);
                                break;
                            case 1: //Intent accidente = new Intent (view.getContext(), AccidenteActivity.class);
                                //startActivity(accidente);
                                break;
                            case 2: //Intent asistencia = new Intent (view.getContext(), AsisteciaActivity.class);
                                //startActivity(asistencia);
                                break;
                            case 3: //Intent asistencia = new Intent (view.getContext(), AsisteciaActivity.class);
                                //startActivity(asistencia);
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
                    case 1:
                        cont = 1;
                        break;
                    case 2:
                        cont = 2;
                        break;
                    case 3:
                        cont = 3;
                        break;
                    default:
                        cont = 0;
                        break;
                }
            }
        });

        // Aplicamos acciones a los botones:
        //Metodo para el boton de Datos del Trabajador
        btnTrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DatosTrabajador.class);
                intent.putExtra("Trabajador", datosTrabajador);
                startActivity(intent);
            }
        });

        //Metodo para el boton de Lista de Vehiculos
        btnListaVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListadoVehiculos.class);
                startActivity(intent);
            }
        });

        //Metodo para el boton de Lista de Usuarios
        btnListaUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListadoUsuarios.class);
                startActivity(intent);
            }
        });
        //Metodo para el boton de lista de Incidencias sin Asignar
        btnListaIncidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listaIncidencias = new Intent(getApplicationContext(), ListadoIncidencias.class);
                //COGEMOS LA ID DEL TRABAJADOR Y LA PASAMOS A LA NUEVA ACTIVITY
                String idTrabajador = null;
                try {
                    idTrabajador = jsonDatosTrab.getString("Id_Empleado");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                listaIncidencias.putExtra("idTrabajador", idTrabajador);
                startActivity(listaIncidencias);
            }
        });
        //Metodo para el boton de lista de Accidenctes sin Asignar
        btnListaAccidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ListaAccidentes = new Intent(getApplicationContext(), ListadoAccidentes.class);
                startActivity(ListaAccidentes);
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // El usuario acepto los permisos.
                    toasted("Gracias, aceptaste los permisos requeridos para el correcto funcionamiento de esta aplicación.");
                }else{
                    // Permiso denegado.
                    toasted("No se aceptó permisos");
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void toasted(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}