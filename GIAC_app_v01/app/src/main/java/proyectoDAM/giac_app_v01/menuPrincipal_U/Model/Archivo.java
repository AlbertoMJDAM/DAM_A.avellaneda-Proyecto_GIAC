package proyectoDAM.giac_app_v01.menuPrincipal_U.Model;

import android.net.Uri;

import java.io.Serializable;

public class Archivo implements Serializable {

    //Atributos
    public String tipo_Parte;
    public String idParte;
    public String nombre_Archivo;
    public Uri uri;
    public String ruta;



    public Archivo() {
    }

    public Archivo(String tipo_Parte, String idParte, String nombre_Archivo, Uri uri, String ruta) {
        this.tipo_Parte = tipo_Parte;
        this.idParte = idParte;
        this.nombre_Archivo = nombre_Archivo;
        this.uri = uri;
        this.ruta = ruta;
    }

    // Metodos getter y setter
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getTipo_Parte() {
        return tipo_Parte;
    }

    public void setTipo_Parte(String tipo_Parte) {
        this.tipo_Parte = tipo_Parte;
    }

    public String getNombre_Archivo() {
        return nombre_Archivo;
    }

    public void setNombre_Archivo(String nombre_Archivo) {
        this.nombre_Archivo = nombre_Archivo;
    }

    public String getIdParte() {
        return idParte;
    }

    public void setIdParte(String idParte) {
        this.idParte = idParte;
    }

    @Override
    public String toString() {
        return "Archivo{" +
                "tipo_Parte='" + tipo_Parte + '\'' +
                ", idParte='" + idParte + '\'' +
                ", nombre_Archivo='" + nombre_Archivo + '\'' +
                ", uri=" + uri +
                '}';
    }
}
