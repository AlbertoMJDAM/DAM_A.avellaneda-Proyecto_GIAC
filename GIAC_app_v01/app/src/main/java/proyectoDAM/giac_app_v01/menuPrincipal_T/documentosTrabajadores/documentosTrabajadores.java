package proyectoDAM.giac_app_v01.menuPrincipal_T.documentosTrabajadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import proyectoDAM.giac_app_v01.Model.Accidentes;
import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_T.listadoAccidentes.adaptadorListaAccidentes;

public class documentosTrabajadores extends AppCompatActivity {

    // ATRIBUTOS
    private ListView lvDocumentosTrabajador;
    private ArrayList<Accidentes> lista;
    private adaptadorListaAccidentes adapter;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_trabajadores);

        // DAMOS VALOR A LOS ELEMENTOS
        lvDocumentosTrabajador = (ListView) findViewById(R.id.lvDocumentosTrabajador);
        lista = new ArrayList<Accidentes>();
        btnSalir = (Button) findViewById(R.id.btnSalir);

        //METODO DEL BOTON btnSalir
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}