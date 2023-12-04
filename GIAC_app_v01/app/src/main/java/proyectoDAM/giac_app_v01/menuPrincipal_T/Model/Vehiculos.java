package proyectoDAM.giac_app_v01.menuPrincipal_T.Model;

public class Vehiculos {

    private String Id_Cliente;
    private String Tipo_Vehiculo;
    private String Marca;
    private String Modelo;
    private String Color;
    private String Num_Puertas;
    private String Motor;
    private String Cv;
    private String Matricula;
    private String Num_Bastidor;

    public Vehiculos(String Id_Cliente, String Tipo_Vehiculo, String Marca, String Modelo, String Color,
                     String Num_Puertas, String Motor, String Cv, String Matricula, String Num_Bastidor){
        this.Id_Cliente = Id_Cliente;
        this.Tipo_Vehiculo = Tipo_Vehiculo;
        this.Marca = Marca;
        this.Modelo = Modelo;
        this.Color = Color;
        this.Num_Puertas = Num_Puertas;
        this.Motor = Motor;
        this.Cv = Cv;
        this.Matricula = Matricula;
        this.Num_Bastidor = Num_Bastidor;
    }
    public Vehiculos(){

    }

    public String getId_Cliente() {
        return Id_Cliente;
    }

    public void setId_Cliente(String id_Cliente) {
        Id_Cliente = id_Cliente;
    }

    public String getTipo_Vehiculo() {
        return Tipo_Vehiculo;
    }

    public void setTipo_Vehiculo(String tipo_Vehiculo) {
        Tipo_Vehiculo = tipo_Vehiculo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getNum_Puertas() {
        return Num_Puertas;
    }

    public void setNum_Puertas(String num_Puertas) {
        Num_Puertas = num_Puertas;
    }

    public String getMotor() {
        return Motor;
    }

    public void setMotor(String motor) {
        Motor = motor;
    }

    public String getCv() {
        return Cv;
    }

    public void setCv(String cv) {
        Cv = cv;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getNum_Bastidor() {
        return Num_Bastidor;
    }

    public void setNum_Bastidor(String num_Bastidor) {
        Num_Bastidor = num_Bastidor;
    }
}