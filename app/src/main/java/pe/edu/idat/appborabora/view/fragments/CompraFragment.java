package pe.edu.idat.appborabora.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.adapter.CarritoProdAdapter;
import pe.edu.idat.appborabora.databinding.FragmentCompraBinding;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarritoResponse;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class CompraFragment extends Fragment {

    private FragmentCompraBinding binding;
    private CarritoProdAdapter carritoProdAdapter = new CarritoProdAdapter();
    private AuthViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompraBinding.inflate(inflater, container, false);

        binding.rvcarrito.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvcarrito.setAdapter(carritoProdAdapter);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);

        // Imprime el userId en Logcat
        Log.d("MyApp", "UserId: " + userId);
        //-------------

        viewModel.getCarritoProductos(userId).observe(getViewLifecycleOwner(), new Observer<List<ProductoCarritoResponse>>() {
            @Override
            public void onChanged(List<ProductoCarritoResponse> carritoProdResponses) {

                        carritoProdAdapter.setData(new ArrayList<>(carritoProdResponses)); // Actualiza los datos de tu adaptador

                }
            });

        binding.btnmetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.metodoPagoFragment);
            }
        });

        return binding.getRoot();
    }
}