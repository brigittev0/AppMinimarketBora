package pe.edu.idat.appborabora.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.adapter.HistorialCompraAdapter;
import pe.edu.idat.appborabora.databinding.FragmentHistorialCompraBinding;
import pe.edu.idat.appborabora.retrofit.response.HistorialComprasResponse;
import pe.edu.idat.appborabora.utils.ToastUtil;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class HistorialCompraFragment extends Fragment {

    private FragmentHistorialCompraBinding binding;
    private HistorialCompraAdapter historialCompraAdapter = new HistorialCompraAdapter();
    private AuthViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistorialCompraBinding.inflate(inflater,container,false);

        binding.rvhistorialcompras.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.rvhistorialcompras.setAdapter(historialCompraAdapter);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        viewModel.getComprasUser(userId).observe(getViewLifecycleOwner(), new Observer<List<HistorialComprasResponse>>() {
            @Override
            public void onChanged(List<HistorialComprasResponse> historialComprasResponse) {
                if (historialComprasResponse != null && !historialComprasResponse.isEmpty()) {
                    historialCompraAdapter.setData(new ArrayList<>(historialComprasResponse));
                } else {
                    ToastUtil.customMensaje(requireActivity(), "No hay historial de compras");
                }
            }
        });

        historialCompraAdapter.setOnItemClickListener(new HistorialCompraAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HistorialComprasResponse historial) {

                int compraId = historial.getId();

                Bundle bundle = new Bundle();
                bundle.putInt("compraId", compraId);
                NavController navController = NavHostFragment.findNavController(HistorialCompraFragment.this);
                navController.navigate(R.id.detalleHistorialCompras, bundle);
            }
        });

        return binding.getRoot();
    }
}