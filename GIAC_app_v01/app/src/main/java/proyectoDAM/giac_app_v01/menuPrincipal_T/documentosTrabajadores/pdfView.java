package proyectoDAM.giac_app_v01.menuPrincipal_T.documentosTrabajadores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pdfview.PDFView;

import java.io.File;

import proyectoDAM.giac_app_v01.R;

public class pdfView extends AppCompatActivity {

    private PDFView vistaPDF;
    private String archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        // RECOGEMOS LA DIRECCION DEL ARCHIVO A ABRIR
        Bundle extras = getIntent().getExtras();
        archivo = getIntent().getStringExtra("archivo");

        // DAMOS VALOR A LOS ELEMENTOS
        vistaPDF = (PDFView) findViewById(R.id.vistaPDF);

        //CREAMOS EL ARCHIVO PDF
        File file = new File(archivo);

        //AÑADIMOS EL ARCHIVO PDF A LA VISTA Y ACTIVAMOS LA OPCION DE HACER ZOOM
        vistaPDF.fromFile(file);
        vistaPDF.isZoomEnabled();

        //MOSTRAMOS EL ARHCIVO
        vistaPDF.show();
    }
}