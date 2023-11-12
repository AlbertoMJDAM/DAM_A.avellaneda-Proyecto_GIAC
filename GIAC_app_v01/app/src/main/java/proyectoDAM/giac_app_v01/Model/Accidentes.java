package proyectoDAM.giac_app_v01.Model;

public class Accidentes {

    String Id_Accidente, Empleado, Vehiculo_usuario, V_Implicado_Uno, V_Implicado_Dos, Ubicacion;
    String Descripcion, CoordenadaX, CoordenadaY, Fecha_Accidente;

    public Accidentes(String Id_Accidente, String Empleado, String Vehiculo_usuario, String V_Implicado_Uno,
                      String V_Implicado_Dos, String Ubicacion, String Descripcion,
                      String CoordenadaX, String CooredenadaY, String Fecha_Accidente){
        this.Id_Accidente = Id_Accidente;
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
