package proyectoDAM.giac_app_v01.menuPrincipal_U;

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

public class AdaptadorVehiculos extends BaseAdapter {
    Context contexto; //contexto de la aplicacion

    List<Vehiculo> listVehiculos; //lista de datos a generar. Podemos usar tb un ArrayList

    public AdaptadorVehiculos(Context contexto, List<Vehiculo> mislistVehiculos) {

        this.contexto = contexto;

        listVehiculos = mislistVehiculos;
    }



    @Override
    public int getCount() {
        return listVehiculos.size(); //Devuelve la cantidad de elementos de la lista
    }


    @Override
    public Object getItem(int i) { //Devuelve el objeto de la lista en la posición indicada como parametro
        return listVehiculos.get(i);
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
        vista=inflate.inflate(R.layout.itemlistview, null); //Consigo referenciar a la vista en sí

        TextView titulo=(TextView)vista.findViewById(R.id.tvTitulo);
        TextView direccion=(TextView)vista.findViewById(R.id.tvDireccion);
        ImageView coche=(ImageView)vista.findViewById(R.id.img);

        String Modelo = listVehiculos.get(i).getModelo().toString();
        String Marca = listVehiculos.get(i).getMarca().toString();
        String color = listVehiculos.get(i).getColor().toString();
        String matricula = listVehiculos.get(i).getMatricula().toString();
        int puertas = listVehiculos.get(i).getNum_Puertas();

        titulo.setText(Marca + " " +Modelo + "   color: " + color + "\nNº puertas: " + puertas);
        direccion.setText("Matricula: " + matricula);
        return vista;
    }
}
