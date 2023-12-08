package pe.edu.idat.appborabora.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.retrofit.response.TopProductosResponse;

public class ProductosDashboardAdapter extends RecyclerView.Adapter<ProductosDashboardAdapter.ViewHolder> {

    private List<TopProductosResponse> productoResponses;

    public ProductosDashboardAdapter(List<TopProductosResponse> productoResponses){
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
                Toast.makeText(itemView.getContext(),"Hola mundo", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
