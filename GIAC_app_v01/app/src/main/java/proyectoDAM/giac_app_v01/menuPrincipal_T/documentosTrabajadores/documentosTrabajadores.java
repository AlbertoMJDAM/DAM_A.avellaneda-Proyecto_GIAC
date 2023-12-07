package proyectoDAM.giac_app_v01.menuPrincipal_T.documentosTrabajadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tashila.pleasewait.PleaseWaitDialog;

import java.io.File;
import java.util.ArrayList;

import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.LoadingDialogBar;
import proyectoDAM.giac_app_v01.R;

public class documentosTrabajadores extends AppCompatActivity {

    // ATRIBUTOS
    private ListView lvDocumentosTrabajador;
    private ArrayList<File> lista;
    private adaptadorDocumentosTrabajador adapter;
    private Button btnSalir;
    private String idTrabajador;
    private PleaseWaitDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_trabajadores);

        // DAMOS VALOR A LOS ELEMENTOS
        lvDocumentosTrabajador = (ListView) findViewById(R.id.lvDocumentosTrabajador);
        lista = new ArrayList<File>();
        btnSalir = (Button) findViewById(R.id.btnSalir);
        progressDialog = new PleaseWaitDialog(this);


        // TRAEMOS UN STRING CON LOS DATOS DEL ID DEL TRABAJADOR
        Bundle extras = getIntent().getExtras();
        idTrabajador = extras.getString("idTrabajador");

        //METODO DEL BOTON btnSalir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //ACTIVAMOS LOADINGGIALOGBAR
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Cargando documentos...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        obtenerDocumentos();
    }

    private void obtenerDocumentos(){
        String direccion = getFilesDir()+"/GIAC/";
        File dir = new File(direccion);
        File[] listaDirectorios;
        listaDirectorios = dir.listFiles();
        if(listaDirectorios != null) {
            for(int x=0; x<listaDirectorios.length; x++){
                lista.add(listaDirectorios[x]);
            }
        }
        adapter = new adaptadorDocumentosTrabajador(getApplicationContext(), lista);
        lvDocumentosTrabajador.setAdapter(adapter);
        progressDialog.dismiss();
    }

}