package proyectoDAM.giac_app_v01.menuPrincipal_U;

import java.io.Serializable;

public class Vehiculo implements Serializable {

    //Atributos
    public int id_Cliente;
    public String Tipo_Vehiculo;
    public String Marca;
    public String Modelo;
    public String Color;
    public int Num_Puertas;
    public String Motor;
    public int Cv;
    public String Matricula;
    public String Num_Bastidor;


    public Vehiculo() {
    }

    public Vehiculo(int id_Cliente, String tipo_Vehiculo, String marca, String modelo, String color, int num_Puertas, String motor, int cv, String matricula, String num_Bastidor) {
        this.id_Cliente = id_Cliente;
        Tipo_Vehiculo = tipo_Vehiculo;
        Marca = marca;
        Modelo = modelo;
        Color = color;
        Num_Puertas = num_Puertas;
        Motor = motor;
        Cv = cv;
        Matricula = matricula;
        Num_Bastidor = num_Bastidor;
    }

    // Metodos getter y setter


    public int getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
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

    public int getNum_Puertas() {
        return Num_Puertas;
    }

    public void setNum_Puertas(int num_Puertas) {
        Num_Puertas = num_Puertas;
    }

    public String getMotor() {
        return Motor;
    }

    public void setMotor(String motor) {
        Motor = motor;
    }

    public int getCv() {
        return Cv;
    }

    public void setCv(int cv) {
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

    //Metodos Tostring


    @Override
    public String toString() {
        return "Vehiculo{" +
                "id_Cliente=" + id_Cliente +
                ", Tipo_Vehiculo='" + Tipo_Vehiculo + '\'' +
                ", Marca='" + Marca + '\'' +
                ", Modelo='" + Modelo + '\'' +
                ", Color='" + Color + '\'' +
                ", Num_Puertas=" + Num_Puertas +
                ", Motor='" + Motor + '\'' +
                ", Cv=" + Cv +
                ", Matricula='" + Matricula + '\'' +
                ", Num_Bastidor='" + Num_Bastidor + '\'' +
                '}';
    }
}
