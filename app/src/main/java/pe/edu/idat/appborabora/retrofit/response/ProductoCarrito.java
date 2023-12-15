package pe.edu.idat.appborabora.retrofit.response;

import androidx.room.Entity;

@Entity(tableName = "carrito")
public class ProductoCarrito {

    private int id;
    private String descripcion;
    private String fvencimiento;
    private String marca;
    private String nombre;
    private String imagen;
    private double precio;
    private int stock;
    private int categoria_id;
    private int cantidad;


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void addOne() {
        this.cantidad++;
    }

    public void removeOne() {
        this.cantidad--;
    }
}
