package proyectoDAM.giac_app_v01.menuPrincipal_T.accidentesAsignados;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import com.itextpdf.layout.property.TextAlignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Accidentes;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Usuarios;
import proyectoDAM.giac_app_v01.menuPrincipal_T.Model.Vehiculos;
import proyectoDAM.giac_app_v01.R;

public class gestionarAccidentes extends AppCompatActivity {

    // Atributos:
    private TextView tvAcc, tvID, tvVehicUsu, tvVehic2, tvVehic3, tvFecha, tvDireccion, tvDescripcion;
    private EditText etAnotaciones;
    private Button btnGenerarPDF, btnGuardar, btnBorrar;
    private ArrayList<Accidentes> arrayList;
    private ArrayList<Usuarios> listaUsuarios;
    private ArrayList<Vehiculos> listaVehiculos;
    private int nAccidente;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editorPreferencias;
    private String anotaciones, nEmpleado;
    private Usuarios usuario;
    private Vehiculos veh1, veh2, veh3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_accidentes);

        // RECOGEMOS LA LISTA DE INCIDENCIAS DESDE LA OTRA VISTA Y EL NUMERO DE EMPLEADO
        Bundle extras = getIntent().getExtras();
        arrayList = getIntent().getParcelableArrayListExtra("datos");
        nAccidente = extras.getInt("accidente");

        // OBTENEMOS UNA LISTA CON TODOS LOS USUARIOS QUE NECESITAREMOS PARA LOS DATOS DEL PDF
        listaUsuarios = new ArrayList<Usuarios>();
        obtenerUsuarios();

        // OBTENEMOS UNA LISTA CON TODOS LOS USUARIOS QUE NECESITAREMOS PARA LOS DATOS DEL PDF
        listaVehiculos = new ArrayList<Vehiculos>();
        obtenerVehiculos();

        // DAMOS VALOR A LOS ELEMENTOS
        tvAcc = (TextView) findViewById(R.id.tvAcc);
        tvID = (TextView) findViewById(R.id.tvID);
        tvVehicUsu = (TextView) findViewById(R.id.tvVehicUsu);
        tvVehic2 = (TextView) findViewById(R.id.tvVehic2);
        tvVehic3 = (TextView) findViewById(R.id.tvVehic3);
        tvFecha = (TextView) findViewById(R.id.tvFecha);
        tvDireccion = (TextView) findViewById(R.id.tvDireccion);
        tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        etAnotaciones = (EditText) findViewById(R.id.etAnotaciones);
        btnGenerarPDF = (Button) findViewById(R.id.btnGenerarPDF);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        preferencias = this.getPreferences(Context.MODE_PRIVATE);
        editorPreferencias = preferencias.edit();

        //REVISAMOS SI SHAREDPREFERENCES TIENE GUARDADAS GESTIONES ANTERIORES
        // PARA IMPLEMENTARLAS EN EL CAMPO DE GESTIONES
        anotaciones = "gestion"+arrayList.get(nAccidente).getId_Accidente();
        etAnotaciones.setText(this.preferencias.getString(anotaciones, ""));

        //ESTABLECEMOS LOS PARAMETROS
        tvAcc.setText(arrayList.get(nAccidente).getId_Accidente());
        tvID.setText(arrayList.get(nAccidente).getId_Usuario());
        tvVehicUsu.setText(arrayList.get(nAccidente).getVehiculo_usuario());
        if(!arrayList.get(nAccidente).getV_Implicado_Uno().equals("")){
            tvVehic2.setText(arrayList.get(nAccidente).getV_Implicado_Uno());
        }else{
            tvVehic2.setVisibility(View.GONE);

        }
        if(!arrayList.get(nAccidente).getV_Implicado_Dos().equals("")){
            tvVehic3.setText(arrayList.get(nAccidente).getV_Implicado_Dos());
        }else{
            tvVehic3.setVisibility(View.GONE);
        }
        tvFecha.setText(arrayList.get(nAccidente).getFecha_Accidente());
        tvDireccion.setText(arrayList.get(nAccidente).getUbicacion());
        tvDescripcion.setText(arrayList.get(nAccidente).getDescripcion());

        //METODO PARA EL BOTON btnRegresar
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAnotaciones.setText("");
            }
        });

        //METODO PARA EL BOTON btnGenerarPDF
        btnGenerarPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IDAccidente = arrayList.get(nAccidente).getId_Accidente().toString();
                crearPDF(IDAccidente);
            }
        });

        //METODO PARA EL BOTON btnGuardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorPreferencias.putString(anotaciones, etAnotaciones.getText().toString());
                editorPreferencias.apply();
                Toast.makeText(gestionarAccidentes.this, "Gestiones guardadas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void crearPDF(String IDAccidente){
        try{
            String direccion = getFilesDir()+"/GIAC/"+IDAccidente+"/";
            File dir = new File(direccion);
            if(!dir.exists()){
                dir.mkdirs();
            }
            //CREAMOS EL PDFWRITER
            String nomArchivo = IDAccidente+".pdf";
            File fichero = new File(direccion+nomArchivo);
            PdfWriter writer = new PdfWriter(String.valueOf(fichero));
            //CREAMOS EL PDF DOCUMENT
            PdfDocument pdfDocument = new PdfDocument(writer);
            //CREAMOS EL DOCUMENTO OBJETO
            Document documento  = new Document(pdfDocument);
            //INSERTAMOS EL LOGO
            Drawable d0 = getDrawable(R.drawable.giac_logo);
            Bitmap bitmap0 = ((BitmapDrawable) d0).getBitmap();
            ByteArrayOutputStream stream0 = new ByteArrayOutputStream();
            bitmap0.compress(Bitmap.CompressFormat.PNG, 100, stream0);
            byte[] bitmapData0 = stream0.toByteArray();
            ImageData imageData0 = ImageDataFactory.create(bitmapData0);
            Image image0 = new Image(imageData0);
            image0.setWidth(200f);
            DeviceRgb azulGiac = new DeviceRgb(8, 65, 114);

            //PRIMERA TABAL, TIENE EL LOGO Y LA DIRECCION DE LA EMPRESA
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

            //TABLA 2 DATOS DEL ACCIDENTE
            float columwidth2[] = {140, 140, 140, 140};
            Table table2 = new Table(columwidth2);
            table2.addCell(new Cell(1, 2).add(new Paragraph("Nº DE ACCIDENTE")));
            table2.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getId_Accidente()).setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell(1, 2).add(new Paragraph("FECHA DEL ACCIDENTE")));
            table2.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getFecha_Accidente())).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell(1, 2).add(new Paragraph("Nº DE EMPLEADO ASISTE")));
            table2.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getEmpleado()).setTextAlignment(TextAlignment.CENTER)));

            //TABLA 3 DATOS DEL USUARIO
            float columwidth3[] = {140, 140, 140, 140};
            Table table3 = new Table(columwidth2);
            table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DEL USUARIO")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("Nº IDENTIFICACION USUARIOS")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getId_Usuario())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("NOMBRE")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(usuario.getNombre())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("PRIMER APELLIDO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(usuario.getpApellido())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("SEGUNDO APELLIDO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(usuario.getsApellido())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("DNI")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(usuario.getDni())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("TELEFONO DE CONTACTO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(usuario.getTelefono())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("CORREO ELECTRONICO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(usuario.getEmail())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA DE VEHICULO IMPLICADO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getVehiculo_usuario())).setTextAlignment(TextAlignment.CENTER));

            //TABLA 3 DATOS VEHICULO USUARIO
            table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS AFECTADO EN EL ACCIDENTE")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("VEHICULO DEL USUARIO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(veh1.getMarca()+" "+veh1.getModelo())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA DEL VEHICULO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(veh1.getMatricula())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("COLOR VEHICULO")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(veh1.getColor())).setTextAlignment(TextAlignment.CENTER));

            //TABLA 3 DATOS DE OTROS AFECTADOS SI LOS HUBIERA
            if(!veh2.getMatricula().equals("null") && !veh2.getMatricula().equals("")){
                table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DE AFECTADO 2 EN EL ACCIDENTE")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
                if(veh2.getMarca() != null && veh2.getModelo() != null) {
                    table3.addCell(new Cell(1, 2).add(new Paragraph("VEHICULO DEL AFECTADO 2")));
                    table3.addCell(new Cell(1, 2).add(new Paragraph(veh2.getMarca() + " " + veh2.getModelo())).setTextAlignment(TextAlignment.CENTER));
                }
                table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA DEL VEHICULO 2")));
                table3.addCell(new Cell(1, 2).add(new Paragraph(veh2.getMatricula())).setTextAlignment(TextAlignment.CENTER));
                if(veh2.getColor() != null){
                    table3.addCell(new Cell(1, 2).add(new Paragraph("COLOR VEHICULO 2")));
                    table3.addCell(new Cell(1, 2).add(new Paragraph(veh2.getColor())).setTextAlignment(TextAlignment.CENTER));
                }
            }
            if(!veh3.getMatricula().equals("null") && !veh3.getMatricula().equals("")){
                table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DE AFECTADOS EN EL ACCIDENTE")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
                if(veh3.getMarca() != null && veh3.getModelo() != null) {
                    table3.addCell(new Cell(1, 2).add(new Paragraph("VEHICULO DEL AFECTADO 3")));
                    table3.addCell(new Cell(1, 2).add(new Paragraph(veh3.getMarca() + " " + veh3.getModelo())).setTextAlignment(TextAlignment.CENTER));
                }
                table3.addCell(new Cell(1, 2).add(new Paragraph("MATRICULA DEL VEHICULO 3")));
                table3.addCell(new Cell(1, 2).add(new Paragraph(veh3.getMatricula())).setTextAlignment(TextAlignment.CENTER));
                if(veh3.getColor() != null) {
                    table3.addCell(new Cell(1, 2).add(new Paragraph("COLOR VEHICULO 3")));
                    table3.addCell(new Cell(1, 2).add(new Paragraph(veh3.getColor())).setTextAlignment(TextAlignment.CENTER));
                }
            }

            //TABLA 3 DATOS DE LOCALIZACION
            table3.addCell(new Cell(1, 4).add(new Paragraph("DATOS DE LOCALIZACION")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("DIRECCION DEL ACCIDENTE")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getUbicacion())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("LATITUD")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getCoordenadaX())).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(1, 2).add(new Paragraph("LONGITUD")));
            table3.addCell(new Cell(1, 2).add(new Paragraph(arrayList.get(nAccidente).getCoordenadaY())).setTextAlignment(TextAlignment.CENTER));

            //Tabla 3 DESCRIPCION DEL SUCESO
            table3.addCell(new Cell(1, 4).add(new Paragraph("DESCRIPCION DEL ACCIDENTE")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(5, 4).add(new Paragraph(arrayList.get(nAccidente).getDescripcion())).setTextAlignment(TextAlignment.CENTER));

            //Tabla 3 GESTION DEL TRABAJADOR
            table3.addCell(new Cell(1, 4).add(new Paragraph("GESTIONES DEL ACCIDENTE")).setFontSize(14f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
            table3.addCell(new Cell(5, 4).add(new Paragraph(etAnotaciones.getText().toString())).setTextAlignment(TextAlignment.CENTER));


            // Añadimos las tablas al documento
            documento.add(table1);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("PARTE DE INCIDENCIA EN CARRETERA").setFontSize(18f).setBold().setFontColor(azulGiac).setTextAlignment(TextAlignment.CENTER));
            documento.add(table2);
            documento.add(new Paragraph("\n"));
            documento.add(table3);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("\n"));

            documento.close();
            Toast.makeText(this, "PDF generado y guardado.", Toast.LENGTH_SHORT).show();
            //subirFichero(fichero, direccion);

        }catch (FileNotFoundException e){
            String mensaje = e.getMessage().toString();
            Toast.makeText(this.getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }

    //METODO PARA OBTENER TODOS LOS USUARIOS REGISTRADOS Y FILTRAR POR EL USUARIO DEL ACCIDENTE
    private void obtenerUsuarios(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_usuarios.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("Id_Usuario");
                                String nombre = jsonObject.getString("Nombre");
                                String dni = jsonObject.getString("DNI");
                                String licencia = jsonObject.getString("Tipo_Licencia");
                                String email = jsonObject.getString("Email");
                                String pApellido = jsonObject.getString("Per_Apellido");
                                String sApellidos = jsonObject.getString("Sdo_Apellido");
                                String Fecha_Nacimiento = jsonObject.getString("Fecha_Nacimiento");
                                String Telefono = jsonObject.getString("Telefono");
                                Usuarios usuario = new Usuarios(id, nombre, dni, licencia, email, pApellido,
                                        sApellidos,Fecha_Nacimiento, Telefono);
                                listaUsuarios.add(usuario);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        for(int x=0; x<listaUsuarios.size(); x++){
                            if(arrayList.get(nAccidente).getId_Usuario().equals(listaUsuarios.get(x).getId()) ){
                                usuario = listaUsuarios.get(x);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    //METODO PARA OBTENER TODOS LOS VEHICULOS REGISTRADOS Y FILTRAR POR LOS VEHICULOS IMPLICADOS
    private void obtenerVehiculos(){
        String url = "https://appgiac.000webhostapp.com/obtener_listado_vehiculos.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String Id_Cliente = jsonObject.getString("Id_Cliente");
                                String Tipo_Vehiculo = jsonObject.getString("Tipo_Vehiculo");
                                String Marca = jsonObject.getString("Marca");
                                String Modelo = jsonObject.getString("Modelo");
                                String Color = jsonObject.getString("Color");
                                String Num_Puertas = jsonObject.getString("Num_Puertas");
                                String Motor = jsonObject.getString("Motor");
                                String Cv = jsonObject.getString("Cv");
                                String Matricula = jsonObject.getString("Matricula");
                                String Num_Bastidor = jsonObject.getString("Num_Bastidor");

                                Vehiculos vehiculo = new Vehiculos(Id_Cliente, Tipo_Vehiculo, Marca, Modelo, Color,
                                        Num_Puertas, Motor, Cv, Matricula, Num_Bastidor);
                                listaVehiculos.add(vehiculo);
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        for(int x=0; x<listaVehiculos.size(); x++){
                            if(arrayList.get(nAccidente).getVehiculo_usuario().equals(listaVehiculos.get(x).getMatricula())){
                                veh1 = listaVehiculos.get(x);
                            }
                            if(arrayList.get(nAccidente).getV_Implicado_Uno().equals(listaVehiculos.get(x).getMatricula())){
                                veh2 = listaVehiculos.get(x);
                            }
                            if(arrayList.get(nAccidente).getV_Implicado_Dos().equals(listaVehiculos.get(x).getMatricula())){
                                veh3 = listaVehiculos.get(x);
                            }
                        }
                        if(veh2 == null){
                            veh2 = new Vehiculos();
                            veh2.setMatricula(tvVehic2.getText().toString());
                        }
                        if(veh3 == null){
                            veh3 = new Vehiculos();
                            veh3.setMatricula(tvVehic3.getText().toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}