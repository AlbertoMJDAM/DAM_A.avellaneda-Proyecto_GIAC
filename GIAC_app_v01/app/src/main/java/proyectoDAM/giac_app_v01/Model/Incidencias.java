package proyectoDAM.giac_app_v01.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Incidencias implements Parcelable {

    private String Id_Incidencia, Id_Usuario, empleado, Vehiculo_Usuario, Fecha_Incidencia, Direccion;
    private String CoordenadaX, CoordenadaY, Descripcion;

    public Incidencias (String Id_Incidencia, String Id_Usuario, String empleado, String Vehiculo_Usuario,
                        String Fecha_Incidencia, String Direccion, String CoordenadaX,
                        String CoordenadaY, String Descripcion){
        this.Id_Incidencia = Id_Incidencia;
        this.Id_Usuario = Id_Usuario;
        this.empleado = empleado;
        this.Vehiculo_Usuario = Vehiculo_Usuario;
        this.Fecha_Incidencia = Fecha_Incidencia;
        this.Direccion = Direccion;
        this.CoordenadaX = CoordenadaX;
        this.CoordenadaY = CoordenadaY;
        this.Descripcion = Descripcion;
    }

    public Incidencias(Parcel parcel)
    {
        //seguir el mismo orden que el usado en el método writeToParcel
        Id_Incidencia = parcel.readString();
        Id_Usuario = parcel.readString();
        empleado = parcel.readString();
        Vehiculo_Usuario = parcel.readString();
        Fecha_Incidencia = parcel.readString();
        Direccion = parcel.readString();
        CoordenadaX = parcel.readString();
        CoordenadaY = parcel.readString();
        Descripcion = parcel.readString();

    }

    public static final Parcelable.Creator<Incidencias> CREATOR =
            new Parcelable.Creator<Incidencias>()
            {
                @Override
                public Incidencias createFromParcel(Parcel parcel)
                {
                    return new Incidencias(parcel);
                }

                @Override
                public Incidencias[] newArray(int size)
                {
                    return new Incidencias[size];
                }
            };

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeString(Id_Incidencia);
        parcel.writeString(Id_Usuario);
        parcel.writeString(empleado);
        parcel.writeString(Vehiculo_Usuario);
        parcel.writeString(Fecha_Incidencia);
        parcel.writeString(Direccion);
        parcel.writeString(CoordenadaX);
        parcel.writeString(CoordenadaY);
        parcel.writeString(Descripcion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(String id_Usuario) {
        Id_Usuario = id_Usuario;
    }

    public String getId_Incidencia() {
        return Id_Incidencia;
    }

    public void setId_Incidencia(String id_Incidencia) {
        Id_Incidencia = id_Incidencia;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getVehiculo_Usuario() {
        return Vehiculo_Usuario;
    }

    public void setVehiculo_Usuario(String vehiculo_Usuario) {
        Vehiculo_Usuario = vehiculo_Usuario;
    }

    public String getFecha_Incidencia() {
        return Fecha_Incidencia;
    }

    public void setFecha_Incidencia(String fecha_Incidencia) {
        Fecha_Incidencia = fecha_Incidencia;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCoordenadaX() {
        return CoordenadaX;
    }

    public void setCoordenadaX(String coordenadaX) {
        CoordenadaX = coordenadaX;
    }

    public String getCoordenadaY() {
        return CoordenadaY;
    }

    public void setCoordenadaY(String coordenadaY) {
        CoordenadaY = coordenadaY;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


}
