package proyectoDAM.giac_app_v01.menuPrincipal_U;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Vehiculo;

public class EditaVehiculo extends AppCompatActivity {

    private String idCliente;

    RequestQueue requestQueue;

    private TextView titulo;
    private TextInputEditText edtMarca,edtModelo,edtColor, edTNum_Puertas,edtMotor,edtCv, edTMatricula,edtNum_Bastidor,
            edtFechas;
    private Button btnSave,btnBorra,btnEliminar,btnAutoriza;

    private Spinner sptipov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_vehiculo);

        //CARGA DE ELEMENTOS DEL LAYOUT
        titulo = findViewById(R.id.tvtit);
        edtMarca = (TextInputEditText) findViewById(R.id.edtMarca);
        edtModelo = (TextInputEditText) findViewById(R.id.edtModelo);
        edtColor = (TextInputEditText) findViewById(R.id.edtColor);
        edTNum_Puertas = (TextInputEditText) findViewById(R.id.edTNum_Puertas);
        edtMotor = (TextInputEditText) findViewById(R.id.edtMotor);
        edtCv = (TextInputEditText) findViewById(R.id.edtCv);
        edTMatricula = (TextInputEditText) findViewById(R.id.edTMatricula);
        edtNum_Bastidor = (TextInputEditText) findViewById(R.id.edtNum_Bastidor);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnBorra =  (Button) findViewById(R.id.btnBorra);
        btnEliminar = (Button) findViewById(R.id.btneliminar);
        sptipov =(Spinner) findViewById(R.id.sptipov);
        btnAutoriza = (Button) findViewById(R.id.btnAutoriza);

        // RECIBIMOS DATOS DEL MENU PRINCIPAL
        Bundle objetoenviado = getIntent().getExtras();
        Vehiculo vehiculo;
        vehiculo = (Vehiculo) objetoenviado.getSerializable("vehiculo");

        //DAMOS VALOR A LOS CAMPOS DEL ACTIVITY CON EL OBJETO VEHICULO RECIBIDO
        titulo.setText(vehiculo.getMarca() + "  "  + vehiculo.getModelo());
        idCliente = String.valueOf(vehiculo.getId_Cliente());
        edtMarca.setText(vehiculo.getMarca());
        edtModelo.setText(vehiculo.getModelo());
        edtColor.setText(vehiculo.getColor());
        edTNum_Puertas.setText(String.valueOf(vehiculo.getNum_Puertas()));
        edtMotor.setText(vehiculo.getMotor());
        edtCv.setText(String.valueOf(vehiculo.getCv()));
        edTMatricula.setText(vehiculo.getMatricula());
        edtNum_Bastidor.setText(vehiculo.getNum_Bastidor());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.
                TipoVehiculo, android.R.layout.simple_spinner_item);
        sptipov.setAdapter(adapter);
        sptipov.setSelection(ValorSpinner(vehiculo.getTipo_Vehiculo().toString()));


        //BLOQUEAMOS LA EDICION DE LOS CAMPOS PARA EVITARA ERRORES
        CamposBloqueados();


        // ########## DAMOS ACCION A LOS BOTONES ##########

        // BOTON QUE DESBLOQUEA LOS CAMPOS PARA SU EDICION
        btnAutoriza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CamposDesbloqueados();
            }
        });


        // METODO ONCLICK DE BOTON "GUARDAR" PARA INSERTAR DATOS EN LA URL INDICADA EN EL METODO "insertaUsuarios"
        // TRAS LA VALIDACION DE LOS DATOS INSERTADOS MEDIANTE LOS METODOS DE VALIDACION.

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean datosOk = Boolean.TRUE;
                Toast.makeText(getApplicationContext(), edtColor.getText().toString(), Toast.LENGTH_SHORT).show();

                if(!ValidaMarcaModeloColorMotor(edtMarca.getText().toString())){
                    edtMarca.setError("¡Formato Nombre Incorrecto!");
                    datosOk = false;
                }
                if(!ValidaMarcaModeloColorMotor(edtModelo.getText().toString())){
                    edtModelo.setError("¡Formato apellido Incorrecto!");
                    datosOk = false;
                }
                if(!ValidaMarcaModeloColorMotor(edtColor.getText().toString())){
                    edtColor.setError("¡Formato apellido Incorrecto!");
                    datosOk = false;
                }
                if(!ValidaMarcaModeloColorMotor(edtMotor.getText().toString())){
                    edtMotor.setError("¡Formato apellido Incorrecto!");
                    datosOk = false;
                }
                if(!Validamatricula(edTMatricula.getText().toString())){
                    edTMatricula.setError("¡Formato DNI Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaNumPuertas(edTNum_Puertas.getText().toString())){
                    edTNum_Puertas.setError("¡Correo electronico Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaCv(edtCv.getText().toString())){
                    edtCv.setError("¡Formato telefono Incorrecto!");
                    datosOk = false;
                }
                if (!ValidaNbastidor(edtNum_Bastidor.getText().toString())){
                    edtNum_Bastidor.setError("¡Nombre Usuario Incorrecto!");
                    datosOk = false;
                }

                if(datosOk){
                    EditaVehiculo("https://appgiac.000webhostapp.com/actualizar_vehiculo.php?Matricula="+edTMatricula.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Algo va mal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // METODO ONCLICK DE BOTON "BORRAR" PARA ELIMINAR LOS DATOS DE LOS EDITTEXT
        btnBorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrarTodo();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = ConfirmaEliminacion();
                dialog.show();
            }
        });

    }


    /* ################################# METODOS #######################################
        A continuacion mostramos los metodos utilizados para el registro de vehiculos
     */

    // Metodo encargado de la validacion de campos de Marca,Modelo,Color y Motor mediante regex
    public static boolean ValidaMarcaModeloColorMotor(String texto){
        boolean datoCorrecto;
        String regexString = "^(?=.{2,15}$)[A-Za-záéíóúüÁÉÍÓÚÜñÑäëïöüäëïöü0-9\\,\\-\\_\\.\\s]+$";
        Pattern pat= Pattern.compile(regexString);
        Matcher mat= pat.matcher(texto);
        if (mat.matches()) {
            datoCorrecto = true;
        }
        else {
            datoCorrecto = false;
        }
        return datoCorrecto;
    }

    // Metodo encargado de la validacion de Fechas mediante regex
    public static boolean ValidaFechas(String fechavehiculo){
        boolean fecCorrecto;
        String regexfech = "^(19|20)(((([02468][048])|([13579][26]))-02-29)|(\\d{2})-((02-((0[1-9])" +
                "|1\\d|2[0-8]))|((((0[13456789])|1[012]))-((0[1-9])|((1|2)\\d)|30))|(((0[13578])|" +
                "(1[02]))-31)))$";
        Pattern pat= Pattern.compile(regexfech);
        Matcher mat= pat.matcher(fechavehiculo);
        if (mat.matches()) {
            fecCorrecto = true;
        }
        else {
            fecCorrecto = false;
        }
        return fecCorrecto;
    }

    // Metodo encargado de la validacion de Matricula mediante regex
    public static boolean Validamatricula(String matricula){
        boolean matCorrecta;
        String regexmat = "[0-9]{4}[A-Z]{2,3}";
        Pattern pat= Pattern.compile(regexmat);
        Matcher mat= pat.matcher(matricula);
        if (mat.matches()) {
            matCorrecta = true;
        }
        else {
            matCorrecta = false;
        }
        return matCorrecta;
    }

    // Metodo encargado de la validacion de Numero de puertas mediante regex
    public static boolean ValidaNumPuertas(String npuertas){
        boolean npuertasCorrecto;
        String regexpuertas = "^(?=.{1,2}$)[0-9]|10$";
        Pattern pat= Pattern.compile(regexpuertas);
        Matcher mat= pat.matcher(npuertas);
        if (mat.matches()) {
            npuertasCorrecto = true;
        }
        else {
            npuertasCorrecto = false;
        }
        return npuertasCorrecto;
    }

    // Metodo encargado de la validacion de caballos del vehiculo mediante regex
    public static boolean ValidaCv(String cv){
        boolean cvCorrecto;
        String regexcv = "^[1-9]$|^[1-9][1-9]?[0-9]$|^(600)$";
        Pattern pat= Pattern.compile(regexcv);
        Matcher mat= pat.matcher(cv);
        if (mat.matches()) {
            cvCorrecto = true;
        }
        else {
            cvCorrecto = false;
        }
        return cvCorrecto;
    }

    // Metodo encargado de la validacion de numero de bastidor de vehiculos mediante regex
    public static boolean ValidaNbastidor(String bastidor){
        boolean basCorrecto;
        String regexpass = "^(?=.{0,17}$)[1-9]{1}[A-Z]{2}[A-Z0-9]{5}[A-Z]{3}\\d{6}$";
        Pattern pat= Pattern.compile(regexpass);
        Matcher mat= pat.matcher(bastidor);
        if (mat.matches()) {
            basCorrecto = true;
        }
        else {
            basCorrecto = false;
        }
        return basCorrecto;
    }

    // Metodo encargado del borrado masivo de los editText
    public void BorrarTodo(){
        edtMarca.setText("");
        edtModelo.setText("");
        edtColor.setText("");
        edTNum_Puertas.setText("");
        edtMotor.setText("");
        edtCv.setText("");
        edTMatricula.setText("");
        edtNum_Bastidor.setText("");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////// METODOS DE MANEJO DE DATOS DE LA BBDD //////////////////////////////
    // METODO ENCARGADO DE ACTUALIZAR DATOS EN LA BBDD
    @SuppressLint("NotConstructor")
    private void EditaVehiculo(String urlactualizar){
        String tipo = sptipov.getItemAtPosition(sptipov.getSelectedItemPosition()).toString().trim();
        String marca = edtMarca.getText().toString().trim();
        String modelo = edtModelo.getText().toString().trim();
        String color = edtColor.getText().toString().trim();
        String NumPuertas = String.valueOf(edTNum_Puertas.getText().toString().trim());
        String motor = edtMotor.getText().toString().trim();
        String cv = String.valueOf(edtCv.getText().toString().trim());
        String matricula = edTMatricula.getText().toString().trim();
        String NumBastidor = edtNum_Bastidor.getText().toString().trim();

        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.show();
        StringRequest request =new StringRequest(Request.Method.POST, urlactualizar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), "actualizo correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("Id_Cliente",idCliente);
                parametros.put("Tipo_Vehiculo",tipo);
                parametros.put("Marca",marca);
                parametros.put("Modelo",modelo);
                parametros.put("Color",color);
                parametros.put("Num_Puertas",NumPuertas);
                parametros.put("Motor",motor);
                parametros.put("Cv",cv);
                parametros.put("Matricula",matricula);
                parametros.put("Num_Bastidor",NumBastidor);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //METODO QUE ELIMINA EL VEHICULO SELECCIONADO DE LA BBDD
    private void EliminaVehiculo(String urleliminar){
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Eliminando Vehiculo");
        progressDialog.show();
        StringRequest request =new StringRequest(Request.Method.POST, urleliminar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("datos eliminados")) {
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(EditaVehiculo.this, "Error no se puede registrar", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditaVehiculo.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>params=new HashMap<>();
                params.put("Id_Cliente",idCliente);
                params.put("Matricula", edTMatricula.getText().toString());
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(EditaVehiculo.this);
        requestQueue.add(request);

    }

    // CUADRO DE DIALOGO PARA CONFIRMAR LA ELIMINACION
    AlertDialog ConfirmaEliminacion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Quieres eliminar este vehiculo?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EliminaVehiculo("https://appgiac.000webhostapp.com/eliminar_vehiculo.php");
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

    // CUADRO DE DIALOGO PARA CONFIRMAR LA ACTUALIZACION (NO USADO)
    AlertDialog ConfirmaActualizacion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Se actualizaran los datos del vehiculo");
        builder.setPositiveButton("Si, deseo actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditaVehiculo("https://appgiac.000webhostapp.com/actualizar_vehiculo.php");
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // METODO ENCARGADO DE DAR VALORES AL SPINNER
    public int ValorSpinner(String texto){
        int valor = 0;
        switch (texto){
            case "COCHE - PEQUEÑO AUTOMOVIL":
                valor = 0;
                break;
            case "COCHE - BERLINA":
                valor = 1;
                break;
            case "COCHE - DEPORTIVO":
                valor = 2;
                break;
            case "COCHE - FAMILIAR":
                valor = 3;
                break;
            case "COCHE - CLASICO":
                valor = 4;
                break;
            case "COCHE - SUV":
                valor = 5;
                break;
            case "COCHE - TODOTERRENO":
                valor = 6;
                break;
            case "COCHE - FURGONETA":
                valor = 7;
                break;
            case "MOTO - SCOOTER":
                valor = 8;
                break;
            case "MOTO - MAXI SCOOTER":
                valor = 9;
                break;
            case "MOTO - CUSTOM":
                valor = 10;
                break;
            case "MOTO - TRAIL":
                valor = 11;
                break;
            case "MOTO - DEPORTIVA":
                valor = 12;
                break;
            case "MOTO - NAKED":
                valor = 13;
                break;
            case "MOTO - MOTOCROSS":
                valor = 14;
                break;
            case "OTROS - CAMION":
                valor = 15;
                break;
            case "OTROS - MICRO-BUS":
                valor = 16;
                break;
        }
        return valor;
    }

    // METODOS DE BLOQUEO Y DESBLOQUEO DE CAMPOS
    public void CamposBloqueados(){
        edtMarca.setEnabled(false);
        edtModelo.setEnabled(false);
        edtColor.setEnabled(false);
        edTNum_Puertas.setEnabled(false);
        edtMotor.setEnabled(false);
        edtCv.setEnabled(false);
        edTMatricula.setEnabled(false);
        edtNum_Bastidor.setEnabled(false);
        sptipov.setEnabled(false);
    }

    public void CamposDesbloqueados(){
        edtMarca.setEnabled(true);
        edtModelo.setEnabled(true);
        edtColor.setEnabled(true);
        edTNum_Puertas.setEnabled(true);
        edtMotor.setEnabled(true);
        edtCv.setEnabled(true);
        edTMatricula.setEnabled(true);
        edtNum_Bastidor.setEnabled(true);
        sptipov.setEnabled(true);
    }

}

/*
    //  Metodo que Volley que inserta Datos en la BBDD
    private void EditaVehiculo(String url){
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "actualizo correctamente", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Algo no ha ido bien, revisa tu conexión",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("Id_Cliente",idCliente);
                parametros.put("Tipo_Vehiculo",sptipov.getItemAtPosition
                        (sptipov.getSelectedItemPosition()).toString());
                parametros.put("Marca",edtMarca.getText().toString());
                parametros.put("Modelo",edtModelo.getText().toString());
                parametros.put("Color",edtColor.getText().toString());
                parametros.put("Num_Puertas", edTNum_Puertas.getText().toString());
                parametros.put("Motor",edtMotor.getText().toString());
                parametros.put("Cv",edtCv.getText().toString());
                //parametros.put("Fecha_Matriculacion",edtFli.getText().toString());
                parametros.put("Matricula", edTMatricula.getText().toString());
                parametros.put("Num_Bastidor",edtNum_Bastidor.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
 */