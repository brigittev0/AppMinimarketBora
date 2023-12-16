package pe.edu.idat.appborabora.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.edu.idat.appborabora.databinding.FragmentDetalleProductoBinding;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class DetalleProductoFragment extends Fragment {

    private FragmentDetalleProductoBinding binding;
    private AuthViewModel viewModel;
    private int productoId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleProductoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Inicializa viewModel
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Obtén el ID del producto desde los argumentos del fragmento
        int productoId = getArguments().getInt("productoId");

        // Llama al método para obtener los detalles del producto por ID
        viewModel.getProductoById(productoId).observe(getViewLifecycleOwner(), new Observer<ProductoResponse>() {
            @Override
            public void onChanged(ProductoResponse producto) {
                // Actualiza los TextViews con los detalles del producto
                Glide.with(binding.ivImagen.getContext())
                        .load(producto.getImagen())
                        .into(binding.ivImagen);
                binding.tvNombre.setText(producto.getNombre());
                binding.tvDescripcion.setText(producto.getDescripcion());
                binding.tvMarca.setText(producto.getMarca());
                binding.tvPrecio.setText("S/ "+ String.valueOf(producto.getPrecio()));
                binding.tvStock.setText(String.valueOf(producto.getStock()));
            }
        });

        return view;
    }
}