package pe.edu.idat.appborabora.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;
import pe.edu.idat.appborabora.retrofit.response.TopProductosResponse;
import pe.edu.idat.appborabora.utils.Carrito;

public class ProductosDashboardAdapter extends RecyclerView.Adapter<ProductosDashboardAdapter.ViewHolder> {

    private List<TopProductosResponse> productoResponses;

    private ArrayList<ProductoCarrito> p = new ArrayList<>();

    public ProductosDashboardAdapter(List<TopProductosResponse> productoResponses) {
        this.productoResponses = productoResponses;
    }

    @NonNull
    @Override
    public ProductosDashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductosDashboardAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosDashboardAdapter.ViewHolder holder, int position) {
        holder.setItem(this.productoResponses.get(position));
    }

    @Override
    public int getItemCount() {
        return this.productoResponses.size();
    }

    public void updateItems(List<TopProductosResponse> productoResponses) {
        this.productoResponses.clear();
        this.productoResponses.addAll(productoResponses);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final TopProductosResponse productoResponse) {
            ImageView imgProducto = itemView.findViewById(R.id.ivProducto);
            TextView nombreProducto = itemView.findViewById(R.id.tvNombre);
            TextView precioProducto = itemView.findViewById(R.id.tvPrecio);
            Button btnOrdenar = itemView.findViewById(R.id.btnOrdenar);

            Glide.with(itemView.getContext())
                    .load(productoResponse.getImagen())
                    .into(imgProducto);
            nombreProducto.setText(productoResponse.getNombre());
            precioProducto.setText(String.valueOf(productoResponse.getPrecio()));

            btnOrdenar.setOnClickListener(v -> {
                ProductoCarrito carritoPedido = new ProductoCarrito();
                carritoPedido.setId(productoResponse.getId());
                carritoPedido.setNombre(productoResponse.getNombre());
                carritoPedido.setCantidad(1);
                carritoPedido.setPrecio(productoResponse.getPrecio());
                carritoPedido.setImagen(productoResponse.getImagen());

                // Obtener el userId de las preferencias compartidas
                SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("user_id", 0);

                // Agregar el producto al carrito
                Carrito.agregarProducto(userId, carritoPedido);
                Snackbar.make(v, "Producto añadido", Snackbar.LENGTH_SHORT).show();



            });


        }
    }
}


