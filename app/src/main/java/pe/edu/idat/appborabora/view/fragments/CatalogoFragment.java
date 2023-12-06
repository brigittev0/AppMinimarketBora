package pe.edu.idat.appborabora.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.adapter.CategoriaAdapter;
import pe.edu.idat.appborabora.adapter.ProductoPorCategoriaAdapter;
import pe.edu.idat.appborabora.databinding.FragmentCatalogoBinding;
import pe.edu.idat.appborabora.retrofit.response.CategoriaResponse;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;


public class CatalogoFragment extends Fragment {
    private FragmentCatalogoBinding binding;
    private CategoriaAdapter categoriaAdapter = new CategoriaAdapter();
    private AuthViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCatalogoBinding.inflate(inflater,container,false);

        binding.rvcategoria.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        binding.rvcategoria.setAdapter(categoriaAdapter);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        viewModel.listarCategoria().observe(getViewLifecycleOwner(), new Observer<List<CategoriaResponse>>() {
            @Override
            public void onChanged(List<CategoriaResponse> categoriaResponses) {
                categoriaAdapter.setData(new ArrayList<>(categoriaResponses)); // Actualiza los datos de tu adaptador
            }
        });

        categoriaAdapter.setOnItemClickListener(new CategoriaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoriaResponse categoria) {
                // Aquí obtienes el id de la categoría y lo usas para obtener los productos
                int categoriaId = categoria.getId();

                // Navega al ListarProdCategoriaFragment pasando el ID de la categoría como argumento
                Bundle bundle = new Bundle();
                bundle.putInt("categoriaId", categoriaId);
                NavController navController = NavHostFragment.findNavController(CatalogoFragment.this);
                navController.navigate(R.id.listarProdCategoriaFragment, bundle);
            }
        });

        return binding.getRoot();
    }
}