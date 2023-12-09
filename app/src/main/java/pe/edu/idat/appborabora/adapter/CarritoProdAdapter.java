package pe.edu.idat.appborabora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ItemCarritocompraBinding;
import pe.edu.idat.appborabora.retrofit.response.CarritoProdResponse;

public class CarritoProdAdapter extends RecyclerView.Adapter<CarritoProdAdapter.ViewHolder> {

    private ArrayList<CarritoProdResponse> listcarritoProd = new ArrayList<>();


    @NonNull
    @Override
    public CarritoProdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(
                ItemCarritocompraBinding.inflate
                        (LayoutInflater.from(parent.getContext()),
                                parent,false
        ));

    }

    @Override
    public void onBindViewHolder(@NonNull CarritoProdAdapter.ViewHolder holder, int position) {
        final CarritoProdResponse carritoProdResponse = listcarritoProd.get(position);
        holder.
        // Aquí puedes asignar los valores a las vistas en tu diseño
        // Por ejemplo:
        // holder.tvNombrePlatilloDC.setText(carritoProdResponse.getProducto_id());
    }

    @Override
    public int getItemCount() {
        return listcarritoProd.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Aquí puedes definir las vistas en tu diseño
        // Por ejemplo:
        // TextView tvNombrePlatilloDC;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Aquí puedes inicializar las vistas en tu diseño
            // Por ejemplo:
            // tvNombrePlatilloDC = itemView.findViewById(R.id.tvNombrePlatilloDC);
        }


    }
}
