package pe.edu.idat.appborabora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.databinding.ItemCategoriasBinding;
import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private ArrayList<CategoriaResponse> lista = new ArrayList<>();

    @NonNull
    @Override
    public CategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemCategoriasBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.ViewHolder holder, int position) {
        final CategoriaResponse objCategoria = lista.get(position);
        holder.binding.txtNombreCategoria.setText(objCategoria.getNombre());

        Glide.with(holder.itemView.getContext())
                .load(objCategoria.getImagen())
                .into(holder.binding.imgCategoria);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setData(ArrayList<CategoriaResponse> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCategoriasBinding binding;
        public ViewHolder(ItemCategoriasBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}