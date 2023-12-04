package proyectoDAM.giac_app_v01.menuPrincipal_U.Model;
import java.sql.Date;

public class Usuario {

    //Atributos
    private int Id_Usuario;
    private String Nombre;
    private String Per_Apellido;
    private String Sdo_Apellido;
    private String DNI;
    private Date Fecha_Nacimiento;
    private Date Fecha_Licencia;
    private String Tipo_Licencia;
    private String Email;
    private String Telefono;
    private String Nombre_Usuario;
    private String Password;

    public Usuario(int id_Usuario, String nombre, String per_Apellido, String sdo_Apellido, String DNI, Date fecha_Nacimiento, Date fecha_Licencia, String tipo_Licencia, String email, String telefono, String nombre_Usuario, String password) {
        Id_Usuario = id_Usuario;
        Nombre = nombre;
        Per_Apellido = per_Apellido;
        Sdo_Apellido = sdo_Apellido;
        this.DNI = DNI;
        Fecha_Nacimiento = fecha_Nacimiento;
        Fecha_Licencia = fecha_Licencia;
        Tipo_Licencia = tipo_Licencia;
        Email = email;
        Telefono = telefono;
        Nombre_Usuario = nombre_Usuario;
        Password = password;
    }

    public int getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        Id_Usuario = id_Usuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPer_Apellido() {
        return Per_Apellido;
    }

    public void setPer_Apellido(String per_Apellido) {
        Per_Apellido = per_Apellido;
    }

    public String getSdo_Apellido() {
        return Sdo_Apellido;
    }

    public void setSdo_Apellido(String sdo_Apellido) {
        Sdo_Apellido = sdo_Apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public Date getFecha_Nacimiento() {
        return Fecha_Nacimiento;
    }

    public void setFecha_Nacimiento(Date fecha_Nacimiento) {
        Fecha_Nacimiento = fecha_Nacimiento;
    }

    public Date getFecha_Licencia() {
        return Fecha_Licencia;
    }

    public void setFecha_Licencia(Date fecha_Licencia) {
        Fecha_Licencia = fecha_Licencia;
    }

    public String getTipo_Licencia() {
        return Tipo_Licencia;
    }

    public void setTipo_Licencia(String tipo_Licencia) {
        Tipo_Licencia = tipo_Licencia;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getNombre_Usuario() {
        return Nombre_Usuario;
    }

    public void setNombre_Usuario(String nombre_Usuario) {
        Nombre_Usuario = nombre_Usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Id_Usuario=" + Id_Usuario +
                ", Nombre='" + Nombre + '\'' +
                ", Per_Apellido='" + Per_Apellido + '\'' +
                ", Sdo_Apellido='" + Sdo_Apellido + '\'' +
                ", DNI='" + DNI + '\'' +
                ", Fecha_Nacimiento=" + Fecha_Nacimiento +
                ", Fecha_Licencia=" + Fecha_Licencia +
                ", Tipo_Licencia='" + Tipo_Licencia + '\'' +
                ", Email='" + Email + '\'' +
                ", Telefono='" + Telefono + '\'' +
                ", Nombre_Usuario='" + Nombre_Usuario + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
