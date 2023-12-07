package proyectoDAM.giac_app_v01.menuPrincipal_U.Asistencia;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Environment.getExternalStorageDirectory;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Partes;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Incidencia;

public class EditaIncidencia extends AppCompatActivity {

    private String idUsuario,idIncidencia, CoordenadaX, CoordenadaY,id_Parte;

    RequestQueue requestQueue;
    private ImageView logoGIAC;
    private TextView tvidinci,tvnuminci;
    private TextInputEditText edtNombre,edtPApe,edtSApe, edtDNI, edteMail,edtphone, edtIdemp,edtMat,etdFsuc,edtDir;
    private EditText edtdescripcion;
    private ImageView img1, img2, img3, img4;
    int control = 0;
    private Button btnSave,btnBorra,btnGenerapdf,bntAutoriza;
    Bundle objetoenviado;
    LoadingDialogBar loadingDialogBar;
    Uri uri;
    Partes parte;
    Incidencia incidencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_incidencia);

        loadingDialogBar =new LoadingDialogBar(this);
        parte = new Partes();
        incidencia = new Incidencia();

        //CARGA DE ELEMENTOS DEL LAYOUT
        tvnuminci = findViewById(R.id.tvnuminci);
        edtNombre = (TextInputEditText) findViewById(R.id.edtNombre);
        edtPApe = (TextInputEditText) findViewById(R.id.edtPApe);
        edtSApe = (TextInputEditText) findViewById(R.id.edtSApe);
        edtDNI = (TextInputEditText) findViewById(R.id.edTtvDNI);
        edteMail = (TextInputEditText) findViewById(R.id.edTeMail);
        edtphone = (TextInputEditText) findViewById(R.id.edtphone);
        edtMat= (TextInputEditText) findViewById(R.id.edtmatricula);
        edtIdemp = (TextInputEditText) findViewById(R.id.edtidempas);
        etdFsuc = (TextInputEditText) findViewById(R.id.edtFsuceso);
        edtDir= (TextInputEditText) findViewById(R.id.edtDirec);
        edtdescripcion = findViewById(R.id.edtDescripcion);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        btnSave = (Button) findViewById(R.id.btnSave);
        bntAutoriza = findViewById(R.id.btnAutoriza);
        btnBorra =  (Button) findViewById(R.id.btnBorra);
        btnGenerapdf = findViewById(R.id.btnpdf);

        // TRAEMOS EL OBJETO PARTE DESDE EL MENU PARTES.
        objetoenviado = getIntent().getExtras();
        parte = (Partes) objetoenviado.getSerializable("Parte");

        // DAMOS VALOR AL ID DE INCIDENCIA Y AL ID DE USUARIO
        idIncidencia = String.valueOf(parte.getCod_Incidencia());
        idUsuario = String.valueOf(parte.getUsuario());
        id_Parte = String.valueOf(Integer.parseInt(idIncidencia)+10000);

        // DAMOS IMAGEN PREVIA A LAS IMAGENES Y CARGAMOS EN LAS MISMAS
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(R.drawable.fotosapp) + '/' +
                getResources().getResourceTypeName(R.drawable.fotosapp) + '/' +
                String.valueOf(R.drawable.fotosapp));

        // FIJAMOS EL VALOR DE LA URI
        img1.setImageURI(imageUri);
        img2.setImageURI(imageUri);
        img3.setImageURI(imageUri);
        img4.setImageURI(imageUri);


        // COMPLEMTAMOS EL TEXTVIEW DE ID DE INCIDENCIA
        tvnuminci.setText(idIncidencia);


        // AUTORELLENADO DE LOS DATOS DEL USUARIO
        muestraUsuario("https://appgiac.000webhostapp.com/mostrar_usuario.php?Id_Usuario=" + idUsuario);
        muestraIncidencia("https://appgiac.000webhostapp.com/mostrar_incidencia.php?Id_Incidencia=" + idIncidencia);

        //BLOQUEO DE DATOS HASTA PULSADO DE BOTON DE EDITAR
        edtNombre.setEnabled(false);
        edtPApe.setEnabled(false);
        edtSApe.setEnabled(false);
        edtDNI.setEnabled(false);
        edteMail.setEnabled(false);
        edtphone.setEnabled(false);
        edtMat.setEnabled(false);
        edtIdemp.setEnabled(false);
        etdFsuc.setEnabled(false);
        edtDir.setEnabled(false);
        edtdescripcion.setEnabled(false);
        //btnGenerapdf.setEnabled(false);

        // ACCIONES DE LOS "BOTONES/IMAGENES" QUE AL PULSAR CARGARAN FOTOS
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 1;
                //AbrirGaleria();
                muestraAlertDialog();
                incidencia.setImg1(uri);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 2;
                //AbrirGaleria();
                muestraAlertDialog();
                incidencia.setImg2(uri);

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 3;
                //AbrirGaleria();
                muestraAlertDialog();
                incidencia.setImg3(uri);

            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 4;
                //AbrirGaleria();
                muestraAlertDialog();
                incidencia.setImg4(uri);

            }
        });


        bntAutoriza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMat.setEnabled(true);
                edtIdemp.setEnabled(true);
                etdFsuc.setEnabled(true);
                edtdescripcion.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Permitida Edicion datos accidente", Toast.LENGTH_LONG).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean datosOk = Boolean.TRUE;
                /*

                if(!ValidaDNI(edtDNI.getText().toString())){
                    edtDNI.setError("¡Formato DNI Incorrecto!");
                    datosOk = false;
                }

                if(tfnoImpUno.isChecked() || tfnoImpDos.isChecked()){
                    if (!ValidaTelefono(edtphone.getText().toString())){
                        edtphone.setError("¡Formato telefono Incorrecto!");
                        datosOk = false;
                    }
                }

                if(emailImpUno.isChecked() || emailImpDos.isChecked()){
                    if (!ValidaMail(edteMail.getText().toString())){
                        edteMail.setError("¡Correo electronico Incorrecto!");
                        datosOk = false;
                    }
                }

                if (!ValidaFechas(etdFsuc.getText().toString())){
                    etdFsuc.setError("¡Fomato de fecha Incorrecto!");
                    datosOk = false;
                }
                if (!Validamatricula(edtMat.getText().toString())){
                    edtMat.setError("¡Matricula Incorrecta!");
                    datosOk = false;
                }

                if (!Validamatricula(edtMatriculaImpUno.getText().toString())){
                    edtMatriculaImpUno.setError("¡Matricula Incorrecta!");
                    datosOk = false;
                }

                if (!Validamatricula(edtMatriculaImpDos.getText().toString())){
                    edtMatriculaImpDos.setError("¡Matricula Incorrecta!");
                    datosOk = false;
                }

                */
                if(datosOk){
                    EditaIncidencia("https://appgiac.000webhostapp.com/actualizar_incidencia.php?Id_Incidencia="+ idIncidencia);
                    EditaParte("https://appgiac.000webhostapp.com/actualizar_parte.php?="+ id_Parte);
                    btnGenerapdf.setEnabled(true);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnGenerapdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {
                    Toast.makeText(getApplicationContext(), "Permisos Necesarios", Toast.LENGTH_LONG).show();
                } else {
                    requestPermissions();
                    Toast.makeText(getApplicationContext(), "Permisos Necesarios", Toast.LENGTH_LONG).show();
                }
                try {
                    createPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /* ################################# METODOS #######################################
   A continuacion mostramos los metodos utilizados para el registro de usuarios
*/

    ///////////////////////////////METODOS PARA USO Y CAPTURA DE IMAGENES //////////////////////////

    // METODOS ENCARGADOS DE ABRIR LA GALERIA DE IMAGENES Y LA CAMARA DE FOTOS PARA LA CARGA DE LAS FOTOS DE LA INCIDENCIA.
    private void AbrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galeria.launch(intent);
    }

    private ActivityResultLauncher<Intent> galeria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        switch (control) {
                            case 1:
                                img1.setImageURI(uri);
                                incidencia.setImg1(uri);
                                break;
                            case 2:
                                img2.setImageURI(uri);
                                incidencia.setImg2(uri);
                                break;
                            case 3:
                                img3.setImageURI(uri);
                                incidencia.setImg3(uri);
                                break;
                            case 4:
                                img4.setImageURI(uri);
                                incidencia.setImg4(uri);
                                break;
                        }
                    } else {
                        Toast.makeText(EditaIncidencia.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void abrirCamara(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Titulo");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Descripcion");
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intentcamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentcamara.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        camara.launch(intentcamara);
    }

    private ActivityResultLauncher<Intent> camara = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        switch (control) {
                            case 1:
                                img1.setImageURI(uri);
                                incidencia.setImg1(uri);
                                break;
                            case 2:
                                img2.setImageURI(uri);
                                incidencia.setImg2(uri);
                                break;
                            case 3:
                                img3.setImageURI(uri);
                                incidencia.setImg3(uri);
                                break;
                            case 4:
                                img4.setImageURI(uri);
                                incidencia.setImg4(uri);
                                break;}
                    }else {
                        Toast.makeText(EditaIncidencia.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    /////////// METODO DEL CUADRO DE DIALOGO PARA SELECCION DE CAMARA O DE GALERIA - MEJORAR ////////
    private void muestraAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Carga de imagenes");
        builder.setMessage("Selecciona carga de imagenes");
        builder.setPositiveButton("Camara", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                abrirCamara();
                dialog.dismiss();
            }

        });
        builder.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AbrirGaleria();
                dialog.dismiss();
            }
        });
        builder.create().show();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////// METODOS DE MANEJO DE DATOS DE LA BBDD ////////////////////////////
    //METODO QUE MUESTRA EL USUARIO INDICADO EN SENTENCIA
    public void muestraUsuario(String urlUsuario){
        //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
        StringRequest request =new StringRequest(Request.Method.POST, urlUsuario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.w("Response VOLLEY SC", response.toString());
                            //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject =new JSONObject(response);
                            Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    edtNombre.setText(object.getString("Nombre"));
                                    edtPApe.setText(object.getString("Per_Apellido"));
                                    edtSApe.setText(object.getString("Sdo_Apellido"));
                                    edtDNI.setText(object.getString("DNI"));
                                    edteMail.setText(object.getString("Email"));
                                    edtphone.setText(object.getString("Telefono"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        }
        ){
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    //METODO QUE MUESTRA LA INCIDENCIA SELECCIONADA EN EL MENU PARTES
    public void muestraIncidencia(String urlUsuario){
        //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
        StringRequest request =new StringRequest(Request.Method.POST, urlUsuario,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.w("Response VOLLEY SC", response.toString());
                            //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject =new JSONObject(response);
                            Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray =jsonObject.getJSONArray("datos");

                            if (exito.equals("1")){
                                //Toast.makeText(getApplicationContext(), "hasta aqui bien", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    if (object.getString("empleado").equalsIgnoreCase("0")){
                                        edtIdemp.setText("Pendiente de indicar empleado");
                                    }
                                    else{
                                        edtIdemp.setText(object.getString("empleado"));
                                    }
                                    if (object.getString("Vehiculo_Usuario").equalsIgnoreCase("0000AAA")){
                                        edtMat.setText("Pendiente de asignar");
                                    }
                                    else{
                                        edtMat.setText(object.getString("Vehiculo_Usuario"));
                                    }

                                    if (object.getString("Direccion").equalsIgnoreCase("")){
                                        edtDir.setText("Indica aproximadamente donde ocurrio el accidente");
                                    }
                                    else{
                                        edtDir.setText(object.getString("Direccion"));
                                    }
                                    if (object.getString("Descripcion").equalsIgnoreCase("Pendiente")){
                                        edtdescripcion.setText("Haz una breve descripcion de lo ocurrido");
                                    }
                                    else{
                                        edtdescripcion.setText(object.getString("Descripcion"));
                                    }
                                    if (object.getString("Fecha_Incidencia").equalsIgnoreCase("0000-00-00")){
                                        etdFsuc.setText("Indica fecha del accidente");
                                    }
                                    else{
                                        etdFsuc.setText(object.getString("Fecha_Incidencia"));
                                    }
                                    CoordenadaX = object.getString("CoordenadaX");
                                    CoordenadaY = object.getString("CoordenadaY");

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion!", Toast.LENGTH_SHORT).show();
            }
        }
        ){
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    //METODO QUE EDITA LA INCIDENCIA SELECCIONADA EN EL MENU PARTES
    @SuppressLint("NotConstructor")
    private void EditaIncidencia(String urlactualizar){
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.show();
        StringRequest request =new StringRequest(Request.Method.POST, urlactualizar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "actualizado correctamente", Toast.LENGTH_SHORT).show();
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
                parametros.put("Id_Incidencia", tvnuminci.getText().toString());
                parametros.put("empleado", edtIdemp.getText().toString());
                parametros.put("Vehiculo_Usuario", edtMat.getText().toString());
                parametros.put("Fecha_Incidencia", etdFsuc.getText().toString());
                parametros.put("Descripcion", edtdescripcion.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //METODO QUE EDITA EL LISTADO DE PARTES ASOCIADO A LA INCIDENCIA
    private void EditaParte(String urlactualizar){
        String idAccidente = "0";
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
                parametros.put("Id_Parte",id_Parte);
                parametros.put("Cod_Incidencia",idIncidencia);
                parametros.put("Cod_Accidente",idAccidente);
                parametros.put("empleado",edtIdemp.getText().toString());
                parametros.put("Fecha_Alta",etdFsuc.getText().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ///////////////////////////////////  METODOS REGEX  ////////////////////////////////////////////
    // Metodo encargado de la validacion de Nombres y apellidos mediante regex
    public static boolean ValidaNombreApell(String nombre){
        boolean nomCorrecto;
        String regexnom = "^(?=.{3,30}$)([a-zA-ZáéíóúüÁÉÍÓÚÜñÑ]{2,26}[\\,\\-\\.]{0,1}[\\s]{0,1}){1,3}$";
        Pattern pat= Pattern.compile(regexnom);
        Matcher mat= pat.matcher(nombre);
        if (mat.matches()) {
            nomCorrecto = true;
        }
        else {
            nomCorrecto = false;
        }
        return nomCorrecto;
    }

    // Metodo encargado de la validacion de Fechas mediante regex
    public static boolean ValidaFechas(String fechas){
        boolean fecCorrecto;
        String regexfech = "^(19|20)(((([02468][048])|([13579][26]))-02-29)|(\\d{2})-((02-((0[1-9])" +
                "|1\\d|2[0-8]))|((((0[13456789])|1[012]))-((0[1-9])|((1|2)\\d)|30))|(((0[13578])|" +
                "(1[02]))-31)))$";
        Pattern pat= Pattern.compile(regexfech);
        Matcher mat= pat.matcher(fechas);
        if (mat.matches()) {
            fecCorrecto = true;
        }
        else {
            fecCorrecto = false;
        }
        return fecCorrecto;
    }

    // Metodo encargado de la validacion de DNI mediante regex
    public static boolean ValidaDNI(String DNI){
        boolean dniCorrecto;
        String regexdni = "[0-9]{8}[A-Z]";
        Pattern pat= Pattern.compile(regexdni);
        Matcher mat= pat.matcher(DNI);
        if (mat.matches()) {
            dniCorrecto = true;
        }
        else {
            dniCorrecto = false;
        }
        return dniCorrecto;
    }

    // Metodo encargado de la validacion de correos electronicos mediante regex
    public static boolean ValidaMail(String email){
        boolean mailCorrecto;
        String regexmail = "^(?=.{6,30}$)[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pat= Pattern.compile(regexmail);
        Matcher mat= pat.matcher(email);
        if (mat.matches()) {
            mailCorrecto = true;
        }
        else {
            mailCorrecto = false;
        }
        return mailCorrecto;
    }

    // Metodo encargado de la validacion de telefonos mediante regex
    public static boolean ValidaTelefono(String phone){
        boolean telCorrecto;
        String regextel = "(6|7|8|9)[ -]*([0-9][ -]*){8}";
        Pattern pat= Pattern.compile(regextel);
        Matcher mat= pat.matcher(phone);
        if (mat.matches()) {
            telCorrecto = true;
        }
        else {
            telCorrecto = false;
        }
        return telCorrecto;
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
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////// METODOS GENERAR PDF/////////////////////////////////////////
    ///////////////////////// METODO ENCARGADO DE CREAR EL ARCHIVO PDF. ////////////////////////////
    private void createPdf() throws FileNotFoundException {
        File file = new File(getExternalStorageDirectory() + "/giac/", "Incidencia_" + idIncidencia + "_v1" + ".pdf");
        while (file.exists()){
            String existente = file.getName();
            String [] nombre = existente.split("_v");
            int version = Integer.parseInt(nombre[1].substring(0, 1));
            version++;
            File file2 = new File(getExternalStorageDirectory() + "/giac/", "Incidencia_" + idIncidencia + "_v" + String.valueOf(version) + ".pdf");
            file = file2;
        }
        //Generamos archivo pdf
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        //Imagen Logo
        Drawable d0 = getDrawable(R.drawable.giac_logo);
        Bitmap bitmap0 = ((BitmapDrawable) d0).getBitmap();
        ByteArrayOutputStream stream0 = new ByteArrayOutputStream();
        bitmap0.compress(Bitmap.CompressFormat.PNG, 100, stream0);
        byte[] bitmapData0 = stream0.toByteArray();
        ImageData imageData0 = ImageDataFactory.create(bitmapData0);
        Image image0 = new Image(imageData0);
        image0.setWidth(200f);
        DeviceRgb azulGiac = new DeviceRgb(8, 65, 114);

        //Primera imagen cargada
        Drawable d1 = img1.getDrawable();
        Bitmap bitmap1 = ((BitmapDrawable) d1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] bitmapData1 = stream1.toByteArray();
        ImageData imageData1 = ImageDataFactory.create(bitmapData1);
        Image image1 = new Image(imageData1);
        image1.setWidth(400f);
        image1.setHeight(200f);

        //Segunda imagen cargada
        Drawable d2 = img2.getDrawable();
        Bitmap bitmap2 = ((BitmapDrawable) d2).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] bitmapData2 = stream2.toByteArray();
        ImageData imageData2 = ImageDataFactory.create(bitmapData2);
        Image image2 = new Image(imageData2);
        image2.setWidth(400f);
        image2.setHeight(200f);

        //Tercera imagen cargada
        Drawable d3 = img3.getDrawable();
        Bitmap bitmap3 = ((BitmapDrawable) d3).getBitmap();
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
        byte[] bitmapData3 = stream3.toByteArray();
        ImageData imageData3 = ImageDataFactory.create(bitmapData3);
        Image image3 = new Image(imageData3);
        image3.setWidth(400f);
        image3.setHeight(200f);

        //Cuarta imagen cargada
        Drawable d4 = img4.getDrawable();
        Bitmap bitmap4 = ((BitmapDrawable) d4).getBitmap();
        ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
        bitmap4.compress(Bitmap.CompressFormat.PNG, 100, stream4);
        byte[] bitmapData4 = stream4.toByteArray();
        ImageData imageData4 = ImageDataFactory.create(bitmapData4);
        Image image4 = new Image(imageData4);
        image4.setWidth(400f);
        image4.setHeight(200f);

        //Tabla 1. tiene el logo y direccion de empresa.
        float columwidth[] = {180, 80, 140, 140};
        Table table1 = new Table(columwidth);
        table1.addCell(new Cell(3, 1).add(image0).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell(3, 3).add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("GIAC S.L.")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("C. de Vitoria, 3. Alcalá de Henares")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("C.P. 28802  - MADRID")).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph()).setBorder(Border.NO_BORDER));

        //Tabla 2. tiene los datos de id de incidencia.
        float columwidth2[] = {140, 140, 140, 140};
        Table table2 = new Table(columwidth2);

        //Tabla 2 rellena datos de identificacion de incidencia.
        table2.addCell(new Cell(1, 2).add(new Paragraph("Nº DE INCIDENCIA")));
        table2.addCell(new Cell(1, 2).add(new Paragraph(idIncidencia).setTextAlignment(TextAlignment.CENTER)));
        table2.addCell(new Cell(1, 2).add(new Paragraph("FECHA DE LA INCIDENCIA")));
        table2.addCell(new Cell(1, 2).add(new Paragraph(etdFsuc.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell(1, 2).add(new Paragraph("Nº DE EMPLEADO ASISTE")));
        table2.addCell(new Cell(1, 2).add(new Paragraph(edtIdemp.getText().toString())).setTextAlignment(TextAlignment.CENTER));

        //Tabla 3. rellena datos del Usuario, parte y descripcion.
        float columwidth3[] = {140, 140, 140, 140};
        Table table3 = new Table(columwidth2);

        //Tabla 3 Datos personales.
        table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DEL USUARIO")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("Nº IDENTIFICACION USUARIOS")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(idUsuario).setTextAlignment(TextAlignment.CENTER)));
        table3.addCell(new Cell(1, 2).add(new Paragraph("NOMBRE")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edtNombre.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("PRIMER APELLIDO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edtPApe.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("SEGUNDO APELLIDO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edtSApe.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("DNI")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edtDNI.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("TELEFONO DE CONTACTO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edtphone.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("CORREO ELECTRONICO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edteMail.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA DE VEHICULO IMPLICADO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edtMat.getText().toString())).setTextAlignment(TextAlignment.CENTER));

        //Tabla 3 Datos de localizacion.
        table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DE LOCALIZACION")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("DIRECCION DE LA INCIDENCIA")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(edtDir.getText().toString())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("LATITUD")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(CoordenadaX)).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("LONGITUD")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(CoordenadaY)).setTextAlignment(TextAlignment.CENTER));

        //Tabla 3 Descripcion del suceso.
        table3.addCell(new Cell(1, 4).add(new Paragraph("DESCRIPCION DEL SUCESO")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(5, 4).add(new Paragraph(edtdescripcion.getText().toString())).setTextAlignment(TextAlignment.CENTER));

        // Tablas 4 a 7 Imagenes del suceso.
        float columwidth4[] = {140, 140, 140, 140};
        Table table4 = new Table(columwidth4);
        table1.addCell(new Cell(0,2).add(new Paragraph()).setBorder(Border.NO_BORDER));
        table4.addCell(new Cell(1, 1).add(image1).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        float columwidth5[] = {140, 140, 140, 140};
        Table table5 = new Table(columwidth5);
        table5.addCell(new Cell(5, 5).add(image2).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        float columwidth6[] = {140, 140, 140, 140};
        Table table6 = new Table(columwidth6);
        table6.addCell(new Cell(5, 5).add(image3).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        float columwidth7[] = {140, 140, 140, 140};
        Table table7 = new Table(columwidth5);
        table7.addCell(new Cell(5, 5).add(image4).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));

        // Añadimos las tablas al documento.
        document.add(table1);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("PARTE DE INCIDENCIA EN CARRETERA").setFontSize(18f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        document.add(table2);
        document.add(new Paragraph("\n"));
        document.add(table3);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("IMAGENES ADJUNTAS DEL SUCESO").setFontSize(18f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));
        document.add(table4);
        document.add(new Paragraph("\n"));
        document.add(table5);
        document.add(new Paragraph("\n"));
        document.add(table6);
        document.add(new Paragraph("\n"));
        document.add(table7);
        document.add(new Paragraph("\n"));

        // cerramos el documento.
        document.close();

        // Avisamos mediante TOAST que el pdf ha sido creado.
        Toast.makeText(this, "PDF CREADO", Toast.LENGTH_LONG).show();
    }

    /////////////////////////////////////// PERMISOS ////////////////////////////////////////////////
    // METODOS ENCARGADOS DE LOS PERMISOS DE LECTURA Y ESCRITURA PARA PDF NECESARIOS.
    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    // METODOS ENCARGADOS DE LOS PERMISOS DE CAMARA.
    private void permisosCamara(){
        int estadoPermiso= ContextCompat.checkSelfPermission(EditaIncidencia.this, CAMERA);
        if(estadoPermiso == PackageManager.PERMISSION_GRANTED){

        }
        else {
            ActivityCompat.requestPermissions(EditaIncidencia.this,new String[]{CAMERA, Manifest.permission.ACCESS_MEDIA_LOCATION},
                    200);
        }
    }

}

