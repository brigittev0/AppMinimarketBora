package pe.edu.idat.appborabora.retrofit.response;

public class CategoriaResponse {
    private int id;
    private String nombre;
    private String imagen;

    public CategoriaResponse(int id, String nombre, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return nombre; // Replace "nombre" with the name of your category name field
    }
    public int getId() {
        return id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
