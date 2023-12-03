package proyectoDAM.giac_app_v01.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Accidentes implements Parcelable {

    String Id_Accidente, Id_Usuario, Empleado, Vehiculo_usuario, V_Implicado_Uno, V_Implicado_Dos, Ubicacion;
    String Descripcion, CoordenadaX, CoordenadaY, Fecha_Accidente;

    public Accidentes(String Id_Accidente, String Id_Usuario, String Empleado, String Vehiculo_usuario, String V_Implicado_Uno,
                      String V_Implicado_Dos, String Ubicacion, String Descripcion,
                      String CoordenadaX, String CooredenadaY, String Fecha_Accidente){
        this.Id_Accidente = Id_Accidente;
        this.Id_Usuario = Id_Usuario;
        this.Empleado = Empleado;
        this.Vehiculo_usuario = Vehiculo_usuario;
        this.V_Implicado_Uno = V_Implicado_Uno;
        this.V_Implicado_Dos = V_Implicado_Dos;
        this.Ubicacion = Ubicacion;
        this.Descripcion = Descripcion;
        this.CoordenadaX = CoordenadaX;
        this.CoordenadaY = CooredenadaY;
        this.Fecha_Accidente = Fecha_Accidente;
    }

    public Accidentes(Parcel parcel)
    {
        //Seguimos el mismo orden que el usado en el metodo writeToParcel
        Id_Accidente = parcel.readString();
        Id_Usuario = parcel.readString();
        Empleado = parcel.readString();
        Vehiculo_usuario = parcel.readString();
        V_Implicado_Uno = parcel.readString();
        V_Implicado_Dos = parcel.readString();
        Ubicacion = parcel.readString();
        Descripcion = parcel.readString();
        CoordenadaX = parcel.readString();
        CoordenadaY = parcel.readString();
        Fecha_Accidente = parcel.readString();
    }

    public static final Parcelable.Creator<Accidentes> CREATOR =
            new Parcelable.Creator<Accidentes>()
            {
                @Override
                public Accidentes createFromParcel(Parcel parcel)
                {
                    return new Accidentes(parcel);
                }

                @Override
                public Accidentes[] newArray(int size)
                {
                    return new Accidentes[size];
                }
            };

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeString(Id_Accidente);
        parcel.writeString(Id_Usuario);
        parcel.writeString(Empleado);
        parcel.writeString(Vehiculo_usuario);
        parcel.writeString(V_Implicado_Uno);
        parcel.writeString(V_Implicado_Dos);
        parcel.writeString(Ubicacion);
        parcel.writeString(Descripcion);
        parcel.writeString(CoordenadaX);
        parcel.writeString(CoordenadaY);
        parcel.writeString(Fecha_Accidente);
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

    public String getId_Accidente() {
        return Id_Accidente;
    }

    public void setId_Accidente(String id_Accidente) {
        Id_Accidente = id_Accidente;
    }

    public String getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(String empleado) {
        Empleado = empleado;
    }

    public String getVehiculo_usuario() {
        return Vehiculo_usuario;
    }

    public void setVehiculo_usuario(String vehiculo_usuario) {
        Vehiculo_usuario = vehiculo_usuario;
    }

    public String getV_Implicado_Uno() {
        return V_Implicado_Uno;
    }

    public void setV_Implicado_Uno(String v_Implicado_Uno) {
        V_Implicado_Uno = v_Implicado_Uno;
    }

    public String getV_Implicado_Dos() {
        return V_Implicado_Dos;
    }

    public void setV_Implicado_Dos(String v_Implicado_Dos) {
        V_Implicado_Dos = v_Implicado_Dos;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
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

    public String getFecha_Accidente() {
        return Fecha_Accidente;
    }

    public void setFecha_Accidente(String fecha_Accidente) {
        Fecha_Accidente = fecha_Accidente;
    }
}
