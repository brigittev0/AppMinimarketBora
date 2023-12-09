package pe.edu.idat.appborabora.retrofit.response;

import androidx.recyclerview.widget.RecyclerView;

public class CarritoProdResponse  {

     private int id;
     private int cantidad;
     private int carrito_id;
     private int producto_id;

    public CarritoProdResponse(int id, int cantidad, int carrito_id, int producto_id) {
        this.id = id;
        this.cantidad = cantidad;
        this.carrito_id = carrito_id;
        this.producto_id = producto_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCarrito_id() {
        return carrito_id;
    }

    public void setCarrito_id(int carrito_id) {
        this.carrito_id = carrito_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

}

