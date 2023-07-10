package com.example.agamelist.Perfil;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agamelist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class perfil_privado extends AppCompatActivity {

    TextView tv_name_title, tv_user_title;
    EditText et_surname, et_email, et_phone, et_password;
    AutoCompleteTextView ac_pais, ac_localidad;
    ImageView fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_privado);

        tv_name_title = findViewById(R.id.tv_name_title);
        tv_user_title = findViewById(R.id.tv_user_title);
        et_surname = findViewById(R.id.et_surname);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        ac_pais = findViewById(R.id.ac_pais);
        et_password = findViewById(R.id.et_password);
        fotoPerfil = findViewById(R.id.ib_profile);

        ac_localidad = findViewById(R.id.ac_localidad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_localidades, android.R.layout.simple_dropdown_item_1line);
        ac_localidad.setAdapter(adapter);

        ac_pais = findViewById(R.id.ac_pais);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.array_paises, android.R.layout.simple_dropdown_item_1line);
        ac_localidad.setAdapter(adapter2);

        obtenerDatosUsuario();
    }

    public void cerrarPerfil(View view) {
        finish();
    }

    private void obtenerDatosUsuario() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Documento encontrado, puedes obtener los datos del usuario
                    String usuario = documentSnapshot.getString("user");
                    String nombre = documentSnapshot.getString("name");
                    String apellidos = documentSnapshot.getString("surname");
                    String email = documentSnapshot.getString("email");
                    int phone = documentSnapshot.getLong("telf").intValue();
                    String pais = documentSnapshot.getString("pais");
                    String localidad = documentSnapshot.getString("localidad");
                    String password = documentSnapshot.getString("password");
                    int foto = documentSnapshot.getLong("profilePic").intValue();
                    // Otros datos...
                    tv_name_title.setText(nombre);
                    tv_user_title.setText(usuario);
                    et_surname.setText(apellidos);
                    et_email.setText(email);
                    et_phone.setText(String.valueOf(phone));
                    ac_pais.setText(pais);
                    ac_localidad.setText(localidad);
                    et_password.setText(password);
                    fotoPerfil.setImageResource(foto);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al cargar los datos de la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void actualizarDatos(View view) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(uid);

        String nuevoApellido = et_surname.getText().toString();
        String nuevoEmail = et_email.getText().toString();
        String nuevoTelefono = et_phone.getText().toString();
        String nuevoPais = ac_pais.getText().toString();
        String nuevaLocalidad = ac_localidad.getText().toString();

        // Actualizar los datos en Firestore
        docRef.update(
                "surname", nuevoApellido,
                "email", nuevoEmail,
                "telf", nuevoTelefono,
                "pais", nuevoPais,
                "localidad", nuevaLocalidad
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                actualizarCamposDeTexto(nuevoApellido, nuevoEmail, nuevoTelefono, nuevoPais, nuevaLocalidad);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void actualizarCamposDeTexto(String nuevoApellido, String nuevoEmail, String nuevoTelefono, String nuevoPais, String nuevaLocalidad) {
        et_surname.setText(nuevoApellido);
        et_email.setText(nuevoEmail);
        et_phone.setText(nuevoTelefono);
        ac_pais.setText(nuevoPais);
        ac_localidad.setText(nuevaLocalidad);
    }

}