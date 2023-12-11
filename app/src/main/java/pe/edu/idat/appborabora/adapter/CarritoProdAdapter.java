package pe.edu.idat.appborabora.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pe.edu.idat.appborabora.databinding.ItemCarritocompraBinding;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarritoResponse;

public class CarritoProdAdapter extends RecyclerView.Adapter<CarritoProdAdapter.ViewHolder> {

    private ArrayList<ProductoCarritoResponse> listcarritoProd = new ArrayList<>();


    @NonNull
    @Override
    public CarritoProdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(
                ItemCarritocompraBinding.inflate
                        (LayoutInflater.from(parent.getContext()),
                                parent,false)
        );

    }

    @Override
    public void onBindViewHolder(@NonNull CarritoProdAdapter.ViewHolder holder, int position) {
        final ProductoCarritoResponse carritoProdResponse = listcarritoProd.get(position);
        holder.binding.tvNombrePlatilloDC.setText(carritoProdResponse.getNombre());
        holder.binding.tvPrecioPDC.setText(String.valueOf(carritoProdResponse.getPrecio()));
        holder.binding.edtCantidad.setText(String.valueOf(carritoProdResponse.getCantidad()));

        Glide.with(holder.itemView.getContext())
                .load(carritoProdResponse.getImagen())
                .into(holder.binding.imgPlatilloDC);

    }

    @Override
    public int getItemCount() {
        return listcarritoProd.size();
    }

    public void setData(ArrayList<ProductoCarritoResponse> data){
        listcarritoProd.clear();
        listcarritoProd.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ItemCarritocompraBinding binding;

        public ViewHolder(ItemCarritocompraBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;

        }


    }
        public double[] calcularTotales() {
            double subtotal = 0;
            double igv = 0;
            double total = 0;

            for (ProductoCarritoResponse producto : listcarritoProd) {
                subtotal += producto.getCantidad() * producto.getPrecio();
            }

            igv = subtotal * 0.18;
            total = subtotal + igv;

            return new double[]{subtotal, igv, total};
        }
    }
