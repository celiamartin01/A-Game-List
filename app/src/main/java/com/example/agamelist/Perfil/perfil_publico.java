package com.example.agamelist.Perfil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agamelist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class perfil_publico extends AppCompatActivity {

    TextView tv_name_title, tv_user_title;
    EditText et_user, et_name, et_pronouns, et_date, et_bio;
    Date fechacumBuena;
    ImageButton fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_publico);

        tv_name_title = findViewById(R.id.tv_name_title);
        tv_user_title = findViewById(R.id.tv_user_title);
        et_name = findViewById(R.id.et_name);
        et_user = findViewById(R.id.et_user);
        et_pronouns = findViewById(R.id.et_pronouns);
        et_date = findViewById(R.id.et_fechaCum);
        et_bio = findViewById(R.id.et_bio);
        fotoPerfil = findViewById(R.id.ib_profile);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorFecha();
            }
        });
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
                    String pronombres = documentSnapshot.getString("pronouns");
                    String biografia = documentSnapshot.getString("biografia");
                    int foto = documentSnapshot.getLong("profilePic").intValue();
                    Timestamp timestamp = documentSnapshot.getTimestamp("fechaCum");

                    if (timestamp != null) {
                        // Obtener la fecha y hora
                        Date date = timestamp.toDate();

                        // Crear el formato deseado para la fecha y hora
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        // Convertir el objeto Date en una cadena de texto
                        String fechaFormateada = dateFormat.format(date);

                        // Establecer la fecha en el EditText
                        et_date.setText(fechaFormateada);
                    }
                    // Otros datos...
                    tv_name_title.setText(nombre);
                    tv_user_title.setText(usuario);
                    et_name.setText(nombre);
                    et_user.setText(usuario);
                    et_pronouns.setText(pronombres);
                    //et_date.setText(fecha_nac);//modificar
                    et_bio.setText(biografia);
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

        String nuevoUsuario = et_user.getText().toString();
        String nuevoNombre = et_name.getText().toString();
        String nuevosPronombres = et_pronouns.getText().toString();
        String nuevaFechaNac = et_date.getText().toString();
        String nuevaBiografia = et_bio.getText().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));

        try {
            Date date = dateFormat.parse(nuevaFechaNac);
            // Aquí tienes el objeto Date que puedes almacenar en Firestore

            fechacumBuena = date;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Actualizar los datos en Firestore
        docRef.update(
                "user", nuevoUsuario,
                "name", nuevoNombre,
                "pronouns", nuevosPronombres,
                "fechaCum", fechacumBuena,
                "biografia", nuevaBiografia
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                actualizarCamposDeTexto(nuevoUsuario, nuevoNombre, nuevosPronombres, nuevaFechaNac, nuevaBiografia);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mostrarSelectorFecha() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog y configurarlo
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Obtener la fecha seleccionada y mostrarla en el EditText
                String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                et_date.setText(fechaSeleccionada);
            }
        }, year, month, dayOfMonth);

        // Mostrar el selector de fecha
        datePickerDialog.show();
    }

    private void actualizarCamposDeTexto(String nuevoUsuario, String nuevoNombre, String nuevosPronombres, String nuevaFechaNac, String nuevaBiografia) {
        tv_user_title.setText(nuevoUsuario);
        tv_name_title.setText(nuevoNombre);
        et_user.setText(nuevoUsuario);
        et_name.setText(nuevoNombre);
        et_pronouns.setText(nuevosPronombres);
        et_date.setText(nuevaFechaNac);
        et_bio.setText(nuevaBiografia);
    }
}