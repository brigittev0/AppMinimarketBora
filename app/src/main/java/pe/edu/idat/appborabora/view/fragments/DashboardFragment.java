package pe.edu.idat.appborabora.view.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.SliderItem;
import pe.edu.idat.appborabora.adapter.ProductosDashboardAdapter;
import pe.edu.idat.appborabora.adapter.SliderAdapter;
import pe.edu.idat.appborabora.databinding.FragmentDashboardBinding;
import pe.edu.idat.appborabora.retrofit.response.ProductoCarrito;
import pe.edu.idat.appborabora.retrofit.response.ProductoResponse;
import pe.edu.idat.appborabora.retrofit.response.TopProductosResponse;
import pe.edu.idat.appborabora.utils.Carrito;
import pe.edu.idat.appborabora.viewmodel.AuthViewModel;

public class DashboardFragment extends Fragment  {
    private AuthViewModel authViewModel;
    private ProductosDashboardAdapter productosDashboardAdapter;
    private List<TopProductosResponse> productoResponses = new ArrayList<>();
    private RecyclerView recyclerView;
    private SliderView svCarrusel;
    private SliderAdapter sliderAdapter;
    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(getLayoutInflater());
        return (binding.getRoot());
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        initAdapter();
        loadData();
    }

    private void init(View v) {
        svCarrusel = v.findViewById(R.id.svCarrusel);
        recyclerView = v.findViewById(R.id.rvTopProductos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void initAdapter() {
        //Carrusel de Imágenes
        sliderAdapter = new SliderAdapter(getContext());
        svCarrusel.setSliderAdapter(sliderAdapter);
        svCarrusel.setIndicatorAnimation(IndicatorAnimationType.WORM);
        svCarrusel.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarrusel.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarrusel.setIndicatorSelectedColor(Color.WHITE);
        svCarrusel.setIndicatorUnselectedColor(Color.GRAY);
        svCarrusel.setScrollTimeInSec(4);
        svCarrusel.startAutoCycle();

        productosDashboardAdapter = new ProductosDashboardAdapter(productoResponses);
        recyclerView.setAdapter(productosDashboardAdapter);
    }
    private void loadData() {

        List<SliderItem> lista = new ArrayList<>();
        lista.add(new SliderItem(R.drawable.img_8, ""));
        lista.add(new SliderItem(R.drawable.img_5, ""));
        lista.add(new SliderItem(R.drawable.img_1, ""));
        lista.add(new SliderItem(R.drawable.img_4, ""));
        lista.add(new SliderItem(R.drawable.img_3, ""));

        sliderAdapter.updateItem(lista);

        authViewModel.listarTopProductos().observe(getViewLifecycleOwner(), response ->{
            productosDashboardAdapter.updateItems(response);
        });
    }


}