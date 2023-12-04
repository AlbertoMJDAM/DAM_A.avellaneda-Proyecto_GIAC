package proyectoDAM.giac_app_v01.menuPrincipal_U.DocumentosUsuario;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import proyectoDAM.giac_app_v01.R;

public class AdaptadorArchivos extends BaseAdapter {
    Context contexto; //contexto de la aplicacion

    List<Archivo> listArchivos; //lista de datos a generar. Podemos usar tb un ArrayList

    public AdaptadorArchivos(Context contexto, int i, List<Archivo> mislistArchivos) {

        this.contexto = contexto;

        listArchivos = mislistArchivos;
    }

    @Override
    public int getCount() {
        return listArchivos.size(); //Devuelve la cantidad de elementos de la lista
    }


    @Override
    public Object getItem(int i) { //Devuelve el objeto de la lista en la posición indicada como parametro
        return listArchivos.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { //Es el método que se ejecuta cuando se muestra en mi ListView cada item
        View vista=view;
        LayoutInflater inflate = LayoutInflater.from(contexto); //Obtenemos el contexto del item sobre el cual estamos trabajando del ListView
        vista=inflate.inflate(R.layout.itemlistview_archivos, null); //Consigo referenciar a la vista en sí

        TextView tipoParte =(TextView)vista.findViewById(R.id.tvTipoParte);
        TextView idParte =(TextView)vista.findViewById(R.id.tvIdparte);
        TextView nombreArchivo =(TextView)vista.findViewById(R.id.tvNomArchivo);
        ImageView tipoImagen=(ImageView)vista.findViewById(R.id.img);

        tipoParte.setText(listArchivos.get(i).getTipo_Parte());
        idParte.setText(listArchivos.get(i).getIdParte());
        nombreArchivo.setText(listArchivos.get(i).getNombre_Archivo());
        tipoImagen.setImageURI(listArchivos.get(i).getUri());

        return vista;
    }
}
