package proyectoDAM.giac_app_v01.Model;

public class Vehiculos {

    private String id;
    private String marca;
    private String modelo;
    private String color;
    private String matricula;
    private String nPuertas;

    public Vehiculos(String id, String marca, String modelo, String color, String matricula, String nPuertas){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.matricula = matricula;
        this.nPuertas = nPuertas;
    }
    public String getnPuertas() {
        return nPuertas;
    }
    public void setnPuertas(String nPuertas) {
        this.nPuertas = nPuertas;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}