package pe.edu.idat.appborabora.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.adapter.CategoriaAdapter;
import pe.edu.idat.appborabora.adapter.ProductoPorCategoriaAdapter;
import pe.edu.idat.appborabora.databinding.FragmentCatalogoBinding;
import pe.edu.idat.appborabora.databinding.FragmentListarProdCategoriaBinding;
import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class ListarProdCategoriaFragment extends Fragment {

    private FragmentListarProdCategoriaBinding binding;
    private ProductoPorCategoriaAdapter productoAdapter = new ProductoPorCategoriaAdapter();
    private AuthViewModel viewModel;
    private int categoriaId; // ID de la categoría seleccionada

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListarProdCategoriaBinding.inflate(inflater,container,false);

        binding.rcvPlatillosPorCategoria.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.rcvPlatillosPorCategoria.setAdapter(productoAdapter);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Obtén el ID de la categoría desde los argumentos del fragmento
        if (getArguments() != null) {
            categoriaId = getArguments().getInt("categoriaId");
        }

        // Llama al método para obtener los productos por ID de categoría
        viewModel.getProductosByCategoriaId(categoriaId).observe(getViewLifecycleOwner(), new Observer<List<ProductoResponse>>() {
            @Override
            public void onChanged(List<ProductoResponse> productoResponses) {
                productoAdapter.setData(new ArrayList<>(productoResponses)); // Actualiza los datos de tu adaptador
            }
        });





        return binding.getRoot();



    }
}