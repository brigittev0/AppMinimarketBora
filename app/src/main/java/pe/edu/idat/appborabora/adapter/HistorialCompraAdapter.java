package pe.edu.idat.appborabora.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pe.edu.idat.appborabora.databinding.ItemHistorialcompraBinding;
import pe.edu.idat.appborabora.retrofit.response.HistorialComprasResponse;

public class HistorialCompraAdapter extends RecyclerView.Adapter<HistorialCompraAdapter.ViewHolder> {

    private ArrayList<HistorialComprasResponse> lista = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public HistorialCompraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemHistorialcompraBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialCompraAdapter.ViewHolder holder, int position) {
        final HistorialComprasResponse objHistorialCompra = lista.get(position);

        String idConTextoAdicional = "1223726765-0" + objHistorialCompra.getId();
        holder.binding.tvnrocompra.setText(idConTextoAdicional);
        holder.binding.tvfcompra.setText(objHistorialCompra.getFcompra());
        holder.binding.tvmpago.setText(objHistorialCompra.getMetodopago());
        String totalSimbolo = "S/ " + objHistorialCompra.getTotal();
        holder.binding.tvtotal.setText(totalSimbolo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(objHistorialCompra);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setData(ArrayList<HistorialComprasResponse> data){
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(HistorialComprasResponse historialCompra);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemHistorialcompraBinding binding;
        public ViewHolder(ItemHistorialcompraBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.btnDetalleProductos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        onItemClickListener.onItemClick(lista.get(position));
                    }
                }
            });
        }
    }
}