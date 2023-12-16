package pe.edu.idat.appborabora.utils;

public class ProductoCompra {
    private ProductoId producto;
    private int cantidad;

    public ProductoCompra(int id, int cantidad) {
        this.producto = new ProductoId(id);
        this.cantidad = cantidad;
    }

    public ProductoId getProducto() {
        return producto;
    }

    public void setProducto(ProductoId producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
