package pe.edu.idat.appborabora.utils;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pe.edu.idat.appborabora.R;

public class ToastUtil {
    public static void customMensaje(Context context, String message) {
        // Infla el layout del Toast
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_layout, null);

        // Configura el texto del Toast
        TextView text = layout.findViewById(R.id.text);
        text.setText(message);

        // Crea el Toast y lo muestra
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
