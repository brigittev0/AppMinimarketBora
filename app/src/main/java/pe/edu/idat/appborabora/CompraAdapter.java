package pe.edu.idat.appborabora;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pe.edu.idat.appborabora.databinding.ItemCompraBinding;
import pe.edu.idat.appborabora.retrofit.response.CompraResponse;

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.ViewHolder> {

    private ArrayList<CompraResponse> lista = new ArrayList<>();

    @NonNull
    @Override
    public CompraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemCompraBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CompraAdapter.ViewHolder holder, int position) {
        final CompraResponse objCompra = lista.get(position);
        // Actualiza estos campos con los campos de tu clase CompraResponse
        String idConTextoAdicional = "1264608726765-0" + objCompra.getId();
        holder.binding.tvnrocompra.setText(idConTextoAdicional);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String fechaFormateada = sdf.format(objCompra.getFcompra());
        holder.binding.tvfcompra.setText(fechaFormateada);

        holder.binding.tvmpago.setText(objCompra.getMetodopago());
        holder.binding.tvtotal.setText(String.valueOf(objCompra.getTotal()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setData(ArrayList<CompraResponse> data){
        lista.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCompraBinding binding;
        public ViewHolder(ItemCompraBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}