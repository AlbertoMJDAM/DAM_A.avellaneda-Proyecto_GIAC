package proyectoDAM.giac_app_v01.menuPrincipal_U.Asistencia;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
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

import com.android.volley.RequestQueue;

import java.util.List;

import proyectoDAM.giac_app_v01.R;
import proyectoDAM.giac_app_v01.menuPrincipal_U.Model.Partes;

public class AdaptadorPartes extends BaseAdapter {

    Context contexto; //contexto de la aplicacion
    RequestQueue requestQueue;
    List<Partes> listaPartes; //lista de datos a generar. Podemos usar tb un ArrayList
    String nomEmp,pApe,email,telefono;
    String tipo;
    String naEmpleado= "";
    String cnEmpleado = "";
    String identificador;
    String fechas;
    String estadoSuc;
    public AdaptadorPartes(Context contexto, List<Partes> mislistVehiculos) {

        this.contexto = contexto;

        listaPartes = mislistVehiculos;
    }



    @Override
    public int getCount() {
        return listaPartes.size(); //Devuelve la cantidad de elementos de la lista
    }


    @Override
    public Object getItem(int i) { //Devuelve el objeto de la lista en la posición indicada como parametro
        return listaPartes.get(i);
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
        vista=inflate.inflate(R.layout.itemlistview_partes, null); //Consigo referenciar a la vista en sí
        TextView tipoParte = (TextView)vista.findViewById(R.id.tvidentificacionIA);
        TextView fSuceso = (TextView)vista.findViewById(R.id.fechaSuceso);
        TextView empleado = (TextView)vista.findViewById(R.id.idempleado);
        TextView estado = (TextView)vista.findViewById(R.id.estadoSuceso);
        TextView contacto = (TextView)vista.findViewById(R.id.contacto);
        ImageView infogiac = (ImageView)vista.findViewById(R.id.imggiac);
        ImageView mail = (ImageView)vista.findViewById(R.id.imgMail);
        ImageView llamar = (ImageView)vista.findViewById(R.id.imgTelefono);


        if(listaPartes.get(i).getId_Parte()>15000){
            identificador = String.valueOf(listaPartes.get(i).getCod_Accidente());
            tipo = "ACCIDENTE   " + identificador;
        }
        else{
            identificador =String.valueOf(listaPartes.get(i).getCod_Incidencia());
            tipo = "INCIDENCIA   " + identificador;
        }

        fechas = listaPartes.get(i).getFechaAlta();

        if (listaPartes.get(i).getFechaFin().equalsIgnoreCase("0000-00-00")){
            estadoSuc = "Pendiente de resolución";
        }
        else{
            estadoSuc = "Finalizada el dia :" + listaPartes.get(i).getFechaFin();
        }

        nomEmp=listaPartes.get(i).getNombreEmpleado();
        pApe=listaPartes.get(i).getApellidoEmpleado();

        if((listaPartes.get(i).getEmpleado()>0)){
            naEmpleado = nomEmp;
            cnEmpleado = nomEmp + " " + pApe;

            infogiac.setVisibility(View.INVISIBLE);
            empleado.setText("Asistido por: " + naEmpleado);
            contacto.setText("Contacta con tu asistente " + cnEmpleado);
        }
        else{
            naEmpleado = "Sin asistencia";
            mail.setVisibility(View.INVISIBLE);
            llamar.setVisibility(View.INVISIBLE);
            empleado.setText("Asistido por: " + naEmpleado);
            contacto.setText("Contacta con informacion general GIAC");
        }

        tipoParte.setText(tipo);
        fSuceso.setText("Fecha Inicio: " + fechas);
        estado.setText("Estado: " + estadoSuc);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(contexto.getApplicationContext(), "MAIL" , Toast.LENGTH_LONG).show();
                Intent correo = new Intent (v.getContext(), EnvioMail_emp.class);
                email=listaPartes.get(i).getEmailEmpleado();
                correo.addFlags(FLAG_ACTIVITY_NEW_TASK);
                correo.putExtra("email", email);
                v.getContext().startActivity(correo);
            }
        });

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent llamar = new Intent(Intent.ACTION_CALL);
                telefono=listaPartes.get(i).getTelefonoEmpleado();
                llamar.setData(Uri.parse("tel:+34"+telefono));
                llamar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                v.getContext().startActivity(llamar);
            }
        });

        infogiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llamar = new Intent(Intent.ACTION_CALL);
                llamar.setData(Uri.parse("tel:+34657174544"));
                llamar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                v.getContext().startActivity(llamar);
            }
        });


        return vista;
    }


}
