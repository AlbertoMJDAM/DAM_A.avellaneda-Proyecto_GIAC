package proyectoDAM.giac_app_v01.menuPrincipal_U;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class RegistroVehiculo extends AppCompatActivity {

    private int idCliente;

    RequestQueue requestQueue;

    private ImageView logoGIAC;

    private TextInputEditText edtMarca,edtModelo,edtColor, edTNum_Puertas,edtMotor,edtCv, edTMatricula,edtNum_Bastidor,
            edtFechas;
    private Button btnSave,btnBorra;

    private Spinner sptipov;

    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vehiculo);

        // Damos valor a las variables de los elementos del Activity
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
        sptipov =(Spinner) findViewById(R.id.sptipov);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.
        TipoVehiculo, android.R.layout.simple_spinner_item);
        sptipov.setAdapter(adapter);

        // Recibimos el valor del ID_Usuario del usuario que ha iniciado sesion
        bundle = getIntent().getExtras();
        idCliente = Integer.valueOf(bundle.getString("idusuario"));

        // METODO ONCLICK DE BOTON "GUARDAR" PARA INSERTAR DATOS EN LA URL INDICADA EN EL METODO "insertaVehiculos"
        // TRAS LA VALIDACION DE LOS DATOS INSERTADOS MEDIANTE LOS METODOS DE VALIDACION.

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean datosOk = Boolean.TRUE;

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
                /*
                if (!ValidaFechas(edtFechas.getText().toString())){
                    edtFechas.setError("¡Fomato de fecha Incorrecto!");
                    datosOk = false;
                }*/

                if (!ValidaNbastidor(edtNum_Bastidor.getText().toString())){
                    edtNum_Bastidor.setError("¡Nombre Usuario Incorrecto!");
                    datosOk = false;
                }

                if(datosOk){
                    // Conexion con WebHost mediante metodo insertaUsuarios
                    insertaVehiculos("https://appgiac.000webhostapp.com/insertar_vehiculo.php");
                    Toast.makeText(getApplicationContext(), "Algo va BIEN", Toast.LENGTH_SHORT).show();
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

    //  Metodo que Volley que inserta Datos en la BBDD
    private void insertaVehiculos(String url){

        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Insertando Datos");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("id_Cliente",String.valueOf(idCliente));
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


}