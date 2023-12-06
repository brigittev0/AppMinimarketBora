package pe.edu.idat.appborabora.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pe.edu.idat.appborabora.databinding.ItemProductoPorCategoriaBinding;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;

public class ProductoPorCategoriaAdapter extends RecyclerView.Adapter<ProductoPorCategoriaAdapter.ViewHolder> {

    private ArrayList<ProductoResponse> lista = new ArrayList<>();

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
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setData(ArrayList<ProductoResponse> data){
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