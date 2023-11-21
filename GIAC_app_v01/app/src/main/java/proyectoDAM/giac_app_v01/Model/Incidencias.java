package proyectoDAM.giac_app_v01.Model;

import android.widget.CheckBox;

public class Incidencias {

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

    public Incidencias (){

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
