package pe.edu.idat.appborabora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pe.edu.idat.appborabora.databinding.ActivityListarProductoPorCategoriaBinding;

public class ListarProductoPorCategoriaActivity extends AppCompatActivity {
    private ActivityListarProductoPorCategoriaBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListarProductoPorCategoriaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}