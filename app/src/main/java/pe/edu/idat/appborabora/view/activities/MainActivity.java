package pe.edu.idat.appborabora.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pe.edu.idat.appborabora.R;
import pe.edu.idat.appborabora.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btningresar.setOnClickListener(this);
        binding.tvregistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btningresar) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        } else if (v.getId() == R.id.tvregistro) {
            startActivity(new Intent(MainActivity.this, RegisterUserActivity.class));
        }
    }
}

