package pe.edu.idat.appborabora.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;

public class Carrito {
    private static final double IGV = 0.18;
    private static final Map<Integer, ArrayList<ProductoCarrito>> carritos = new HashMap<>();

    public static String agregarProducto(int userId, ProductoCarrito producto) {
        if (!carritos.containsKey(userId)) {
            carritos.put(userId, new ArrayList<>());
        }
        ArrayList<ProductoCarrito> productosEnCarrito = carritos.get(userId);

        for (ProductoCarrito prod : productosEnCarrito) {
            if (prod.getId() == producto.getId()) {
                prod.setCantidad(prod.getCantidad() + producto.getCantidad());
                return "El producto ha sido agregado al carrito, se actualizará la cantidad";
            }
        }

        productosEnCarrito.add(producto);
        return "El producto ha sido agregado al carrito con éxito";
    }


    public static void eliminarProducto(int userId, int idProducto) {
        ArrayList<ProductoCarrito> productosEnCarrito = carritos.get(userId);

        for (int i = 0; i < productosEnCarrito.size(); i++) {
            if (productosEnCarrito.get(i).getId() == idProducto) {
                productosEnCarrito.remove(i);
                return;
            }
        }
    }

    public static ArrayList<ProductoCarrito> getProductosEnCarrito(int userId) {
        return carritos.get(userId);
    }

    public static void limpiarCarrito(int userId) {
        ArrayList<ProductoCarrito> productosEnCarrito = carritos.get(userId);
        productosEnCarrito.clear();
    }

    public static double calcularSubtotal(int userId) {
        ArrayList<ProductoCarrito> productosEnCarrito = carritos.get(userId);

        double subtotal = 0;
        for (ProductoCarrito producto : productosEnCarrito) {
            subtotal += producto.getPrecio() * producto.getCantidad();
        }
        return subtotal;
    }

    public static double calcularIGV(int userId) {
        double subtotal = calcularSubtotal(userId);
        return subtotal * IGV;
    }

    public static double calcularTotal(int userId) {
        double subtotal = calcularSubtotal(userId);
        double igv = calcularIGV(userId);
        return subtotal + igv;
    }

}
