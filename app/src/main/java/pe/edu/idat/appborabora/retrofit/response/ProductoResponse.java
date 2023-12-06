package pe.edu.idat.appborabora.retrofit.response;

public class ProductoResponse {
    private int id;
    private String descripcion;
    private String fvencimiento;
    private String marca;
    private String nombre;
    private double precio;
    private int stock;
    private int categoria_id;

    public ProductoResponse(int id, String descripcion, String fvencimiento, String marca, String nombre, double precio, int stock, int categoria_id) {
        this.id = id;
        this.descripcion = descripcion;
        this.fvencimiento = fvencimiento;
        this.marca = marca;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria_id = categoria_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFvencimiento() {
        return fvencimiento;
    }

    public void setFvencimiento(String fvencimiento) {
        this.fvencimiento = fvencimiento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }
}