package proyectoDAM.giac_app_v01.DocumentosUsuario;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

import proyectoDAM.giac_app_v01.R;

public class MenuArchivos extends AppCompatActivity {

    // Instanciamos Listview y un arraylist de la clase creada Alojamiento. Con el que tomaremos los valores
    // para el adaptador. Tambien instanciamos el SearchView.
    ListView listaDatos;
    ArrayList<Archivo> lista;
    File ruta;
    SearchView buscador;
    CheckBox borrado;
    Button borrar;
    AdaptadorArchivos miAdaptador;
    // Variables del buscador para que muestre todos los objetos del ListView al incicion.
    private String selectedFilter = "all";
    private String textoBusqueda = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_archivos);
        startActivity(new Intent(MenuArchivos.this, InfoDocumentos.class));

        // Creamos los elementos de la activity
        listaDatos=(ListView)findViewById(R.id.lstDatos);
        // Creamos el arraylist y el File con la ruta donde se encuentran los partes generados.
        lista = new ArrayList<Archivo>();
        ruta = new File(Environment.getExternalStorageDirectory() + "/giac");

        //Creamos una URI a partir de una imagen cargada en la carpeta del proyecto "Drawable"
        // Esta aparecera en el itemlistview para indicar que el documento sera un pdf.

        Uri Parte = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(R.drawable.pdf) + '/' +
                getResources().getResourceTypeName(R.drawable.pdf) + '/' +
                String.valueOf(R.drawable.pdf));

        // Comprobamos que tenemos permisos
        if (checkPermission()) {
            // Comprobamos que la carpeta "giac" existe
            if(ruta.exists()) {
                // Si existe, llamamos a los metodos encargados de llenar el listview y del filtrado de archivos
                listaarchivos(ruta,Parte,lista);
                initSearchWidgets();
                setUpList();
            }
            else{
                // Si no existe incluimos un cuadro de alerta informando que no hay partes generados.
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuArchivos.this);
                builder.setTitle("Información Usuario");
                builder.setMessage("¡Todavía no has generado ningún parte!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "RETROCEDE AL MENU PRINCIPAL", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        else {
            requestPermissions();
        }

        // Metodo setOnItemClickListener, al pinchar cada uno de los ItemListView, abrira el PDF
        // correspondiente
        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Tomamos la ruta
                String rutas = lista.get(position).getRuta();
                File file = new File(rutas);
                // Comprobamos la version para llamar a la apertura del archivo de distnta forma.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri uri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(file.getPath()), "application/pdf");
                    intent = Intent.createChooser(intent, "Open File");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

        });

        // Al mantener pulsado, saltara el menu alertDialog para eliminar el archivo.
        listaDatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ruta +"/" + lista.get(position).getNombre_Archivo(), Toast.LENGTH_SHORT).show();
                File borrado = new File(ruta +"/" + lista.get(position).getNombre_Archivo());
                final int which_item = position;
                new AlertDialog.Builder(MenuArchivos.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("ELIMINAR ARCHIVO")
                        .setMessage("Se va a eliminar el archivo:\n" +  lista.get(position).getNombre_Archivo())
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        lista.remove(which_item);
                                        File borrado = new File(ruta +"/" + lista.get(position).getNombre_Archivo());
                                        borrado.delete();
                                        miAdaptador.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Cancelar",null)
                        .show();
                                //borrado.delete();
                                //miAdaptador.remove(miAdaptador.getItem(position));
                                //new File(ruta,lista.get(position).getNombre_Archivo()).delete();
                return false;
            }

        });

    }

    // Metodo encargado de listar los elementos de la carpeta GIAC. Este SOLO reconocera los archivos
    // PDF pues son los unicos que GENERA la aplicacion, cualquier otro archivo sera excluico.

    public void listaarchivos(File dir, Uri uriPfd, ArrayList<Archivo> listarch) {
        File elements[] = dir.listFiles();
        String tipo = "";
        String id = "";
        String numParte = "";
        String nombreArchivo = "";
        Uri uri = uriPfd;
        String ruta = "";
        for (File element : elements) {
            if (element.isFile() && element.getName().contains(".pdf") && !element.equals(".") && !element.equals("..")) {
                ruta = element.getPath();
                if (element.getName().contains("Accidente")) {
                    tipo = "ACCIDENTE";
                    String[] ides = element.getName().split("_");
                    id = (ides[1]).substring(0, 4);
                    int calculo = Integer.parseInt(id)+10000;
                    numParte = "Numero de Parte: " + String.valueOf(calculo);
                    nombreArchivo = element.getName();
                    uri = uriPfd;
                } else if (element.getName().contains("Incidencia")) {
                    tipo = "INCIDENCIA";
                    String[] ides = element.getName().split("_");
                    id = (ides[1]).substring(0, 4);
                    int calculo = Integer.parseInt(id)+10000;
                    numParte = "Numero de Parte: " + String.valueOf(calculo);
                    nombreArchivo = element.getName();
                    uri = uriPfd;
                }
            }
            //Si estuviera contenido dentro de una subcarpeta, podria leer el archivo tambien.
            else if (element.isDirectory()) {
                listaarchivos(element.getAbsoluteFile(), uriPfd, listarch);
            }

            Archivo archivo = new Archivo(tipo, numParte, nombreArchivo, uri, ruta);
            listarch.add(archivo);
        }
    }

    // Metodo encargado de proporcionar un adaptador al listview creado.
    private void setUpList() {
        //Creamos objeto de clase Adaptador con parametros contex y el listado de objetos Alojamiento
        //AdaptadorArchivos miAdaptador = new AdaptadorArchivos(getApplicationContext(), 0, lista);
        miAdaptador = new AdaptadorArchivos(getApplicationContext(), 0, lista);
        //Añadimos al listview el adaptador
        listaDatos.setAdapter(miAdaptador);
    }

    // Metodo encargado del filtrado en el searchview
    private void initSearchWidgets() {
        buscador = findViewById(R.id.buscador);
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                textoBusqueda = s;
                ArrayList<Archivo> filteredShapes = new ArrayList<Archivo>();

                for (Archivo archivo : lista) {
                    if (archivo.getNombre_Archivo().toLowerCase().contains(s.toLowerCase())) {
                        if (selectedFilter.equals("all")) {
                            filteredShapes.add(archivo);
                        } else {
                            if (archivo.getNombre_Archivo().toLowerCase().contains(selectedFilter)) {
                                filteredShapes.add(archivo);
                            }
                        }
                    }
                }

                AdaptadorArchivos adaBusqueda = new AdaptadorArchivos(getApplicationContext(), 0, filteredShapes);
                //Añadimos al listview el adaptador
                listaDatos.setAdapter(adaBusqueda);
                //Preparamos el evento de clicado por si lo requiere (No util en este caso)
                return false;
            }
        });
    }

    // Creamos los metodos de lectura y escritura
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



}




