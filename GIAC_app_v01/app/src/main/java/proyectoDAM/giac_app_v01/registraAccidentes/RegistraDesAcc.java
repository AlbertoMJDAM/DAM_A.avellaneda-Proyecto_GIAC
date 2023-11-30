package proyectoDAM.giac_app_v01.registraAccidentes;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import proyectoDAM.giac_app_v01.R;

public class RegistraDesAcc extends AppCompatActivity {

    Accidente accidente;
    RequestQueue requestQueue;
    Uri uri;
    private TextView tvidusu, tvidNumAccidente;
    private EditText edtdescripcion;
    private Button btnGuarda, btnBorra, btnPDF;
    private ImageView img1, img2, img3,img4, img5, img6;
    int control = 0;
    LoadingDialogBar loadingDialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_des_acc);

        // CARGAMOS EL POP-UP DE INFORMACION
        startActivity(new Intent(RegistraDesAcc.this, InfoDesPdfAcc.class));
        // DAMOS PERMISO DE USAO DE CAMARA
        permisosCamara();
        // RECIBIMOS OBJETO INCIDENCIA CON LOS DATOS CARGADOS EN EL ACTIVITY ANTERIOR
        Bundle objetoenviado = getIntent().getExtras();
        accidente = (Accidente) objetoenviado.getSerializable("Accidente");
        // INSTANCIAMOS EL CUADRO DE DIALOGO
        loadingDialogBar = new LoadingDialogBar(this);


        //CARGA DE ELEMENTOS DEL LAYOUT
        tvidusu = findViewById(R.id.tvidNumUsuario);
        tvidNumAccidente = findViewById(R.id.tvidNumAccidente);
        edtdescripcion = findViewById(R.id.edtDescripcion);
        btnGuarda = findViewById(R.id.btnguardar);
        btnBorra = findViewById(R.id.btnBorra);
        btnPDF = findViewById(R.id.btnpdf);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);

        // RELLENAMOS LOS TEXTVIEW DE INCIDENCIA Y USUARIO
        tvidusu.setText(accidente.getIdUsuario());
        tvidNumAccidente.setText(accidente.getIdAccidente());

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
        img5.setImageURI(imageUri);
        img6.setImageURI(imageUri);

        // ########## DAMOS ACCION A LOS BOTONES ##########
        // BOTON PARA GUARDADO DE BASE DE DATOS DE LOS DATOS INTROUDCIDOS
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accidente.setDescripcion(edtdescripcion.getText().toString());
                InsertaAccidente("https://appgiac.000webhostapp.com/insertar_accidentes.php");
                InsertaParte("https://appgiac.000webhostapp.com/insertar_parte.php");
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        // ACCIONES DE LOS "BOTONES/IMAGENES" QUE AL PULSAR CARGARAN FOTOS
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 1;
                //AbrirGaleria();
                muestraAlertDialog();
                accidente.setImg1(uri);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 2;
                //AbrirGaleria();
                muestraAlertDialog();
                accidente.setImg2(uri);

            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 3;
                //AbrirGaleria();
                muestraAlertDialog();
                accidente.setImg3(uri);

            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 4;
                //AbrirGaleria();
                muestraAlertDialog();
                accidente.setImg4(uri);

            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 5;
                //AbrirGaleria();
                muestraAlertDialog();
                accidente.setImg5(uri);

            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = 6;
                //AbrirGaleria();
                muestraAlertDialog();
                accidente.setImg6(uri);

            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////

        // BOTON PDF CREA EL PDF
        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Creando Documento PDF", Toast.LENGTH_LONG).show();
                if (checkPermission()) {
                    Toast.makeText(getApplicationContext(), "Permisos Disponibles", Toast.LENGTH_LONG).show();
                    accidente.setDescripcion(edtdescripcion.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Permisos Necesarios", Toast.LENGTH_LONG).show();
                    requestPermissions();
                }
                try {
                    createPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
    }


    /* ################################# METODOS #######################################
        A continuacion mostramos los metodos utilizados para el registro de usuarios
     */


    ///////////////////////////////METODOS PARA USO Y CAPTURA DE IMAGENES //////////////////////////

    // METODOS ENCARGADOS DE ABRIR LA GALERIA DE IMAGENES Y LA CAMARA DE FOTOS PARA LA CARGA DE LAS FOTOS DEL ACCIDENTE.
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
                                accidente.setImg1(uri);
                                break;
                            case 2:
                                img2.setImageURI(uri);
                                accidente.setImg2(uri);
                                break;
                            case 3:
                                img3.setImageURI(uri);
                                accidente.setImg3(uri);
                                break;
                            case 4:
                                img4.setImageURI(uri);
                                accidente.setImg4(uri);
                                break;
                            case 5:
                                img5.setImageURI(uri);
                                accidente.setImg4(uri);
                                break;
                            case 6:
                                img6.setImageURI(uri);
                                accidente.setImg4(uri);
                                break;
                        }
                    } else {
                        Toast.makeText(RegistraDesAcc.this, "Cancelado", Toast.LENGTH_SHORT).show();
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
                                accidente.setImg1(uri);
                                break;
                            case 2:
                                img2.setImageURI(uri);
                                accidente.setImg2(uri);
                                break;
                            case 3:
                                img3.setImageURI(uri);
                                accidente.setImg3(uri);
                                break;
                            case 4:
                                img4.setImageURI(uri);
                                accidente.setImg4(uri);
                                break;
                            case 5:
                                img5.setImageURI(uri);
                                accidente.setImg5(uri);
                                break;
                            case 6:
                                img6.setImageURI(uri);
                                accidente.setImg6(uri);
                                break;}
                    }else {
                        Toast.makeText(RegistraDesAcc.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    // METODO DEL CUADRO DE DIALOGO PARA SELECCION DE CAMARA O DE GALERIA - MEJORAR
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

    /////////////////////////// METODOS DE MANEJO DE DATOS DE LA BBDD //////////////////////////////

    // METODO ENCARGADO DE INSERTAR INCIDENCIA EN BBDD.

    private void InsertaAccidente(String url){
        Toast.makeText(getApplicationContext(), accidente.toString(), Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de insercion", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("Id_Accidente",accidente.getIdAccidente());
                parametros.put("Id_Usuario", accidente.getIdUsuario());
                parametros.put("Empleado",accidente.getEmpleado());
                parametros.put("Vehiculo_usuario", accidente.getVehiculoUsuario());
                parametros.put("V_Implicado_Uno",accidente.getVehiculoImplicadoUno());
                parametros.put("V_Implicado_Dos",accidente.getVehiculoImplicadoDos());
                parametros.put("Ubicacion",accidente.getUbicacion());
                parametros.put("Descripcion",accidente.getDescripcion());
                parametros.put("CoordenadaX",accidente.getLatSuceso());
                parametros.put("CoordenadaY",accidente.getLonSuceso());
                parametros.put("Fecha_Accidente",accidente.getfSuceso());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // METODO ENCARGADO DE INSERTAR PARTE EN BBDD.
    private void InsertaParte(String url) {
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Creando un parte del Accidente");
        progressDialog.show();
        int idParte = Integer.parseInt(accidente.getIdAccidente()) + 10000;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Id_Parte", String.valueOf(idParte));
                parametros.put("Cod_Incidencia", "0");
                parametros.put("Cod_Accidente", accidente.getIdAccidente());
                parametros.put("Usuario", accidente.getIdUsuario());
                parametros.put("Empleado", accidente.getEmpleado());
                parametros.put("Fecha_Alta", accidente.getfSuceso());
                parametros.put("Estado_Parte", "En proceso");
                parametros.put("Fecha_Finalizacion", "0000-00-00");
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /////////////////////////////////// METODOS GENERAR PDF/////////////////////////////////////////

    // METODO ENCARGADO DE CREAR EL ARCHIVO PDF.
    private void createPdf() throws FileNotFoundException {
        File file = new File(getExternalStorageDirectory() + "/giac/", "Accidente_" + accidente.getIdAccidente() + "_v1" + ".pdf");
        while (file.exists()){
            String existente = file.getName();
            String [] nombre = existente.split("_v");
            int version = Integer.parseInt(nombre[1].substring(0, 1));
            version++;
            File file2 = new File(getExternalStorageDirectory() + "/giac/", "Accidente_" + accidente.getIdAccidente() + "_v" + String.valueOf(version) + ".pdf");
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


        //Cuarta imagen cargada
        Drawable d5 = img5.getDrawable();
        Bitmap bitmap5 = ((BitmapDrawable) d5).getBitmap();
        ByteArrayOutputStream stream5 = new ByteArrayOutputStream();
        bitmap5.compress(Bitmap.CompressFormat.PNG, 100, stream5);
        byte[] bitmapData5 = stream5.toByteArray();
        ImageData imageData5 = ImageDataFactory.create(bitmapData5);
        Image image5 = new Image(imageData5);
        image5.setWidth(400f);
        image5.setHeight(200f);


        //Cuarta imagen cargada
        Drawable d6 = img6.getDrawable();
        Bitmap bitmap6 = ((BitmapDrawable) d6).getBitmap();
        ByteArrayOutputStream stream6 = new ByteArrayOutputStream();
        bitmap6.compress(Bitmap.CompressFormat.PNG, 100, stream6);
        byte[] bitmapData6 = stream6.toByteArray();
        ImageData imageData6 = ImageDataFactory.create(bitmapData6);
        Image image6 = new Image(imageData6);
        image6.setWidth(400f);
        image6.setHeight(200f);



        //Primera tabla tiene el logo y direccion de empresa
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

        //Segunda tabla tiene los datos de id de incidencia.
        float columwidth2[] = {140, 140, 140, 140};
        Table table2 = new Table(columwidth2);

        //Tabla 2 rellena datos de identificacion de incidencia
        table2.addCell(new Cell(1, 2).add(new Paragraph("Nº DE ACCIDENTE")));
        table2.addCell(new Cell(1, 2).add(new Paragraph(accidente.getIdAccidente())).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell(1, 2).add(new Paragraph("FECHA DE LA INCIDENCIA")));
        table2.addCell(new Cell(1, 2).add(new Paragraph(accidente.getfSuceso())).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell(1, 2).add(new Paragraph("Nº DE EMPLEADO ASISTE")));
        table2.addCell(new Cell(1, 2).add(new Paragraph(accidente.getEmpleado())).setTextAlignment(TextAlignment.CENTER));

        //Tercera tabla rellena datos del Usuario, parte y descripcion
        float columwidth3[] = {140, 140, 140, 140};
        Table table3 = new Table(columwidth2);

        //Tabla 3 Datos personales
        table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DEL USUARIO")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("Nº IDENTIFICACION USUARIOS")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getIdUsuario())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("NOMBRE")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getNombre())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("PRIMER APELLIDO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getPapellido())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("SEGUNDO APELLIDO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getSapellido())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("DNI")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getDNI())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("TELEFONO DE CONTACTO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getTelefono())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("CORREO ELECTRONICO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getEmail())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA DE VEHICULO IMPLICADO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getVehiculoUsuario())).setTextAlignment(TextAlignment.CENTER));
        //Tabla 3 Datos de implicados
        table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DE AFECTADOS EN EL INCIDENTE")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA VEHICULO DEL PRIMER AFECTADO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getVehiculoImplicadoUno())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("NOMBRE Y APELLIDOS")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getNombreImplicadoUno())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("CONTACTO PROPORCIONADO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getContactoImplicadoUno())).setTextAlignment(TextAlignment.CENTER));
        // Segundo Afectado
        table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DE AFECTADOS EN EL INCIDENTE")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA VEHICULO DEL SEGUNDO AFECTADO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getVehiculoImplicadoDos())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("NOMBRE Y APELLIDOS")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getNombreImplicadoDos())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("CONTACTO PROPORCIONADO")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getContactoImplicadoDos())).setTextAlignment(TextAlignment.CENTER));

        //Tabla 4 Datos de localizacion
        table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DE LOCALIZACION")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("DIRECCION DE LA INCIDENCIA")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getUbicacion())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("LATITUD")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getLatSuceso())).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(1, 2).add(new Paragraph("LONGITUD")));
        table3.addCell(new Cell(1, 2).add(new Paragraph(accidente.getLonSuceso())).setTextAlignment(TextAlignment.CENTER));

        //Tabla 5 Descripcion del suceso
        table3.addCell(new Cell(1, 4).add(new Paragraph("DESCRIPCION DEL SUCESO")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        table3.addCell(new Cell(5, 4).add(new Paragraph(accidente.getDescripcion())).setTextAlignment(TextAlignment.CENTER));

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
        float columwidth8[] = {140, 140, 140, 140};
        Table table8 = new Table(columwidth8);
        table8.addCell(new Cell(5, 5).add(image5).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
        float columwidth9[] = {140, 140, 140, 140};
        Table table9 = new Table(columwidth9);
        table9.addCell(new Cell(5, 5).add(image6).setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER));
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
        document.add(new Paragraph("IMAGENES VEHICULOS IMPLICADOS").setFontSize(18f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));
        document.add(table8);
        document.add(new Paragraph("\n"));
        document.add(table9);
        document.add(new Paragraph("\n"));

        // cerramos el documento.
        document.close();
        // Avisamos mediante TOAST que el pdf ha sido creado.
        Toast.makeText(this, "PDF CREADO", Toast.LENGTH_LONG).show();
    }


    /////////////////////////////////////// PERMISOS ///////////////////////////////////////////////
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
        int estadoPermiso= ContextCompat.checkSelfPermission(RegistraDesAcc.this, CAMERA);
        if(estadoPermiso == PackageManager.PERMISSION_GRANTED){

        }
        else {
            ActivityCompat.requestPermissions(RegistraDesAcc.this,new String[]{CAMERA,Manifest.permission.ACCESS_MEDIA_LOCATION},
                    200);
        }
    }
}
