package pe.edu.idat.appborabora.retrofit.response;

public class TopProductosResponse {

    private Integer top;
    private Integer id;
    private String nombre;
    private String descripcion;
    private String marca;
    private Double precio;
    private String imagen;


    public TopProductosResponse() {
    }

    public TopProductosResponse(Integer top, Integer id, String nombre, String descripcion, String marca,
                                Double precio, String imagen) {
        super();
        this.top = top;
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
        this.imagen = imagen;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

