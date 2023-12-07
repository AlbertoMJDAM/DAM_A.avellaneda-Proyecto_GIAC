package proyectoDAM.giac_app_v01.menuPrincipal_T.documentosTrabajadores;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

import java.io.File;
import java.util.ArrayList;

import proyectoDAM.giac_app_v01.R;

public class adaptadorDocumentosTrabajador extends BaseAdapter {

    private Context context;
    private ArrayList<File> arrayList;

    public adaptadorDocumentosTrabajador(Context context, ArrayList<File> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();

    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.lista_documentos_trab_adapter,null);
        }

        TextView tvTitIncACC = (TextView) view.findViewById(R.id.tvTitIncACC);
        TextView tvIDIncAcc = (TextView) view.findViewById(R.id.tvIDIncAcc);
        ImageView ivIcono = (ImageView) view.findViewById(R.id.img);

        String direccion = arrayList.get(i).getAbsolutePath();
        Uri imagePDF = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                view.getResources().getResourcePackageName(R.drawable.pdf) + '/' +
                view.getResources().getResourceTypeName(R.drawable.pdf) + '/' +
                String.valueOf(R.drawable.pdf));

        tvIDIncAcc.setText(arrayList.get(i).getName());
        ivIcono.setImageURI(imagePDF);

        if(arrayList.get(i).getName().charAt(0) == '3'){
            tvTitIncACC.setText("INCIDENCIA");
        }else{
            tvTitIncACC.setText("ACCIDENTE");
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPDF(direccion + "/" + arrayList.get(i).getName()+".pdf", v.getContext());
            }
        });

        return view;
    }

    //METODO PARA ABRIR EL ARCHIVO PDF EN LA APLICACION PREDETERMINADA PARA ELLO
    public void mostrarPDF(String archivo, Context context) {
        Toast.makeText(context, "Visualizando documento", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, pdfView.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("archivo", archivo);
        context.startActivity(intent);
    }
}
