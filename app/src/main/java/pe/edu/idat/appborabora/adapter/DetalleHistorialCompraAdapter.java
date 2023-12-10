package pe.edu.idat.appborabora.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pe.edu.idat.appborabora.databinding.ItemCompraProductoBinding;
import pe.edu.idat.appborabora.retrofit.response.ProductoCompraResponse;

public class DetalleHistorialCompraAdapter extends RecyclerView.Adapter<DetalleHistorialCompraAdapter.ViewHolder> {

    private ArrayList<ProductoCompraResponse> lista = new ArrayList<>();

    @NonNull
    @Override
    public DetalleHistorialCompraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemCompraProductoBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleHistorialCompraAdapter.ViewHolder holder, int position) {
        final ProductoCompraResponse compra = lista.get(position);
        holder.binding.tvnombreproducto.setText(compra.getNombre());
        holder.binding.tvmarca.setText(compra.getMarca());

        String precioSimbolo = "S/ " + compra.getPrecio();
        holder.binding.tvprecioproducto.setText(precioSimbolo);

        String cantidadSimbolo = "Unidades: " + compra.getCantidad();
        holder.binding.tvcantidad.setText(cantidadSimbolo);

        Glide.with(holder.itemView.getContext())
                .load(compra.getImagen())
                .into(holder.binding.imgdetalleprod);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setData(ArrayList<ProductoCompraResponse> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCompraProductoBinding binding;
        public ViewHolder(ItemCompraProductoBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
