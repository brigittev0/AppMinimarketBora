package pe.edu.idat.appborabora.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;
import pe.edu.idat.appborabora.utils.Carrito;
import pe.edu.idat.appborabora.view.fragments.CompraFragment;


public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {


    private List<ProductoCarrito> carrito;
    private OnCarritoChangeListener listener;

    public CarritoAdapter(List<ProductoCarrito> carrito, OnCarritoChangeListener listener) {
        this.carrito = carrito;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ProductoCarrito producto = carrito.get(position);
        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText(String.valueOf(producto.getPrecio()));
        int cant = producto.getCantidad();
        holder.edtCantidad.setText(Integer.toString(cant));

        //-------------Actualizar Cantidad del Carrito-------------------------
        holder.btnAdd.setOnClickListener(v -> {
            if (producto.getCantidad() != 10) {
                producto.addOne();
                CarritoAdapter.this.notifyDataSetChanged();
                listener.onCarritoChange();
            }
        });
        holder.btnDecrease.setOnClickListener(v -> {
            if (producto.getCantidad() != 1) {
                producto.removeOne();
                CarritoAdapter.this.notifyDataSetChanged();
                listener.onCarritoChange();
            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el userId de las preferencias compartidas
                SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("user_id", 0);

                // Eliminar el producto del carrito
                Carrito.eliminarProducto(userId, producto.getId());
                carrito.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, carrito.size());
                listener.onCarritoChange();
            }
        });

    }
    public interface OnCarritoChangeListener {
        void onCarritoChange();
    }

    @Override
    public int getItemCount() {
        return carrito.size();
    }

    public void setListaProductosCarrito(List<ProductoCarrito> listaProductosCarrito) {
        this.carrito = listaProductosCarrito;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvPrecio;
        ImageView imgProducto;
        EditText edtCantidad;
        ImageView btnAdd;
        ImageView btnDecrease;
        ImageView btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombrePlatilloDC);
            tvPrecio = itemView.findViewById(R.id.tvPrecioPDC);
            imgProducto = itemView.findViewById(R.id.imgPlatilloDC);
            edtCantidad = itemView.findViewById(R.id.edtCantidad);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnEliminar = itemView.findViewById(R.id.btnEliminarPCarrito);
        }


    }
}

