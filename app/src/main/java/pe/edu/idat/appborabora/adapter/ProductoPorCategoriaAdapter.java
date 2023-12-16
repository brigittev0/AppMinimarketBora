package pe.edu.idat.appborabora.adapter;


import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ItemProductoPorCategoriaBinding;

import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;

import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;

import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.retrofit.response.TopProductosResponse;
import pe.edu.idat.appborabora.utils.Carrito;

public class ProductoPorCategoriaAdapter extends RecyclerView.Adapter<ProductoPorCategoriaAdapter.ViewHolder> {


    private ArrayList<ProductoResponse> lista = new ArrayList<>();
    public interface OnItemClickListener {
        void onItemClick(ProductoResponse producto);
    }

    private ProductoPorCategoriaAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ProductoPorCategoriaAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProductoPorCategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemProductoPorCategoriaBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoPorCategoriaAdapter.ViewHolder holder, int position) {
        final ProductoResponse producto = lista.get(position);
        holder.binding.nombreProducto.setText(producto.getNombre());
        holder.binding.txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));

        Glide.with(holder.itemView.getContext())
                .load(producto.getImagen())
                .into(holder.binding.imgProducto);

        // click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productoId = producto.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("productoId", productoId);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.detalleProductoFragment, bundle);
            }
        });

        holder.binding.btnOrdenar.setOnClickListener(v -> {
            ProductoCarrito carritoPedido = new ProductoCarrito();
            carritoPedido.setId(producto.getId());
            carritoPedido.setNombre(producto.getNombre());
            carritoPedido.setCantidad(1);
            carritoPedido.setPrecio(producto.getPrecio());
            carritoPedido.setImagen(producto.getImagen());

            // Obtener el userId de las preferencias compartidas
            SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("user_id", 0);

            // Agregar el producto al carrito
            Carrito.agregarProducto(userId, carritoPedido);

            // Notificar al adaptador que los datos han cambiado
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setData(ArrayList<ProductoResponse> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemProductoPorCategoriaBinding binding;
        public ViewHolder(ItemProductoPorCategoriaBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;


        }
    }

}