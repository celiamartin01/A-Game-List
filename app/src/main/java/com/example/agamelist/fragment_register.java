package com.example.agamelist;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class fragment_register extends Fragment {

    private Context mContext;
    String uidGoogle, nameGoogle, surnameGoogle, emailGoogle, userGoogle;
    ImageButton btatras, btGoogle;
    Button btregister;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    FirebaseAuth mAuth;
    EditText etEmail, etContraseña, et_telefono, et_nombre, et_username, et_fechanacimiento;
    CheckBox cbinfo;
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db;
    String email, tlf_texto, password, paisSeleccionado, fechacumple, username, nombre, uid;
    int telefono;
    boolean tema;
    AutoCompleteTextView ac_localidad, ac_pais;
    String localidadSeleccionada;
    int idRecurso;
    String[] Localidad = {"Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres",
            "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara",
            "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra",
            "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona",
            "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"};

    String[] Pais = {"Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina",
            "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Barbados", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia",
            "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde",
            "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Ciudad del Vaticano", "Colombia", "Comoras", "Corea del Norte",
            "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador",
            "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Etiopía", "Filipinas", "Finlandia",
            "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guyana", "Guinea", "Guinea ecuatorial",
            "Guinea-Bisáu", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón",
            "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia",
            "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos",
            "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua",
            "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay",
            "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República de Macedonia", "República del Congo",
            "República Democrática del Congo", "República Dominicana", "República Sudafricana", "Ruanda", "Rumanía", "Rusia", "Samoa", "San Cristóbal y Nieves",
            "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur",
            "Siria", "Somalia", "Sri Lanka", "Suazilandia", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Tanzania", "Tayikistán",
            "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán",
            "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};

    public fragment_register() {

    }

    // TODO: Rename and change types and number of parameters
    public static fragment_register newInstance(String param1, String param2) {
        fragment_register fragment = new fragment_register();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btregister = view.findViewById(R.id.bt_Registrarse_Registro);
        etEmail = view.findViewById(R.id.et_Registrarse_Email);
        etContraseña = view.findViewById(R.id.et_Registrarse_contraseña);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        idRecurso = getResources().getIdentifier("fotoperfil5", "drawable", mContext.getPackageName());
        cbinfo = view.findViewById(R.id.cb_infolegal);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                nameGoogle = account.getGivenName();
                emailGoogle = account.getEmail();
                surnameGoogle = account.getFamilyName();
                userGoogle = account.getDisplayName();
            } catch (ApiException e) {

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uidGoogle = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            crearListasGoogle();
                            crearBBDDGoogle();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getContext(), main_pagina_principal.class);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mContext = getActivity();
        btatras = view.findViewById(R.id.bt_Registrarse_Atras);
        btregister = view.findViewById(R.id.bt_Registrarse_Registro);
        etEmail = view.findViewById(R.id.et_Registrarse_Email);
        etContraseña = view.findViewById(R.id.et_Registrarse_contraseña);
        cbinfo = view.findViewById(R.id.cb_infolegal);
        et_username = view.findViewById(R.id.et_registro_usuario);
        ac_localidad = view.findViewById(R.id.ac_registro_localidad);
        et_telefono = view.findViewById(R.id.et_registro_telefono);
        et_nombre = view.findViewById(R.id.et_Registrarse_Nombre);
        ac_pais = view.findViewById(R.id.ac_registro_pais);
        et_fechanacimiento = view.findViewById(R.id.et_registro_cumple);

        ArrayAdapter<String> adapterPais = new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line,Pais);
        ac_pais.setAdapter(adapterPais);
        ac_pais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                paisSeleccionado = (String) parent.getItemAtPosition(position);
            }
        });

        ArrayAdapter<String> adapterLocalidad = new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, Localidad);
        ac_localidad.setAdapter(adapterLocalidad);
        ac_localidad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                localidadSeleccionada = (String) parent.getItemAtPosition(position);
            }
        });

        et_fechanacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorFecha();
            }
        });

        btGoogle = view.findViewById(R.id.bt_registro_google);
        btGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inicioGoogle();
                signIn();
            }
        });
        btatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });

        return view;
    }

    private void registrarUsuario() {
        tlf_texto = et_telefono.getText().toString();
        nombre = et_nombre.getText().toString();
        username = et_username.getText().toString();
        fechacumple = et_fechanacimiento.getText().toString();
        password = etContraseña.getText().toString();
        email = etEmail.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(mContext, "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(mContext, "Por favor ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(fechacumple)) {
            Toast.makeText(mContext, "Por favor ingrese una fecha de nacimiento", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Integer.toString(telefono))) {
            Toast.makeText(mContext, "Por favor ingrese un telefono", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, "Por favor ingrese un usuario", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(localidadSeleccionada)) {
            Toast.makeText(mContext, "Por favor ingrese una localidad valida", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(mContext, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbinfo.isChecked()) {
            Toast.makeText(mContext, "Acepte los términos y condiciones", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            NavController navController = Navigation.findNavController(getView());
                            navController.navigate(R.id.main_pagina_principal);
                            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            crearBBDD();
                            crearListas();
                        } else {
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(mContext, "Contraseña débil.", Toast.LENGTH_SHORT).show();
                            } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(mContext, "Credenciales inválidas.", Toast.LENGTH_SHORT).show();
                            } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(mContext, "El usuario ya existe.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Falló el registro.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private int comprobarTelefono() {


        if (tlf_texto.length() == 9) {
            try {
                int numeroTlf = Integer.parseInt(tlf_texto);
                return numeroTlf;
            } catch (NumberFormatException e) {
                Toast.makeText(mContext, "Formato incorrecto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "El numero no tiene 9 digitos", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    public void crearBBDD() {
        ArrayList<DocumentReference> listas = new ArrayList<>();
        ArrayList<DocumentReference> reseñas = new ArrayList<>();
        ArrayList<String> redes = new ArrayList<>();
        redes.add(0, "");
        redes.add(1, "");
        redes.add(2, "");
        redes.add(3, "");
        redes.add(4, "");
        // CollectionReference usersCollection = db.collection("Users");
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("Redes", redes);
        newUser.put("fechaCreacion", FieldValue.serverTimestamp());
        newUser.put("biografia", "");
        newUser.put("email", email);

        // Obtener referencia a la colección de listas
        CollectionReference listasCollection = db.collection("Lists");

        // Crear referencias a las colecciones de listas
        for (int i = 0; i < 6; i++) {
            String documentPath = "Lista" + uid + "_" + (i + 1);
            DocumentReference listaRef = listasCollection.document(documentPath);
            listas.add(listaRef);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));

        try {
            Date date = dateFormat.parse(fechacumple);
            newUser.put("fechaCum", date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (Arrays.asList(Localidad).contains(localidadSeleccionada)) {
            newUser.put("localidad", localidadSeleccionada);
        } else {
            Toast.makeText(mContext, "Inserta una localidad valdia.", Toast.LENGTH_SHORT).show();
        }

        newUser.put("name", nombre);
        newUser.put("pais", paisSeleccionado);
        newUser.put("password", password);
        newUser.put("profilePic", idRecurso); //poner id icono default
        newUser.put("pronouns", "");
        newUser.put("subType", 0);
        newUser.put("surname", "");
        newUser.put("tema", false);
        newUser.put("user", username);
        newUser.put("seguidores", 0);
        newUser.put("siguiendo", 0);
        newUser.put("reseñasCount", 0);
        newUser.put("visibilidad", "Todos");
        newUser.put("coments_respuestas", "Todos");
        newUser.put("noti_email", "Todos");
        newUser.put("notis", "Todos");
        newUser.put("google", false);
        newUser.put("listas", listas);
        newUser.put("reseñas",reseñas);

        telefono = comprobarTelefono();
        newUser.put("telf", telefono);

        db.collection("Users").document(uid)
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "Usuario creado correctamente.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Error al crear un usuario.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void crearListas() {

        Map<String, Object> data1 = new HashMap<>();
        ArrayList<Integer> games = new ArrayList<>();


        String[] opciones = {"Todos", "Jugando", "Jugados", "Pausado", "Dejado de jugar", "Para jugar"};
        for (int i = 0; i < opciones.length; i++) {
            String opcion = opciones[i];
            data1.put("games", games);
            data1.put("name", opcion);
            final int vuelta = i + 1;
            final String documentPath = "Lista" + uid + "_" + vuelta;
            db.collection("Lists").document(documentPath)
                    .set(data1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mContext, "Usuario creado correctamente en la vuelta " + vuelta, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Error al crear un usuario en la vuelta " + vuelta, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void mostrarSelectorFecha() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog y configurarlo
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Obtener la fecha seleccionada y mostrarla en el EditText
                String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                et_fechanacimiento.setText(fechaSeleccionada);
            }
        }, year, month, dayOfMonth);

        // Mostrar el selector de fecha
        datePickerDialog.show();
    }

    public void crearBBDDGoogle() {
        ArrayList<DocumentReference> listas = new ArrayList<>();
        ArrayList<DocumentReference> reseñas = new ArrayList<>();
        ArrayList<String> redes = new ArrayList<>();
        redes.add(0, "");
        redes.add(1, "");
        redes.add(2, "");
        redes.add(3, "");
        redes.add(4, "");
        // CollectionReference usersCollection = db.collection("Users");
        Map<String, Object> newUser = new HashMap<>();

        // Obtener referencia a la colección de listas
        CollectionReference listasCollection = db.collection("Lists");

        // Crear referencias a las colecciones de listas
        for (int i = 0; i < 6; i++) {
            String documentPath = "Lista" + uidGoogle + "_" + (i + 1);
            DocumentReference listaRef = listasCollection.document(documentPath);
            listas.add(listaRef);
        }

        newUser.put("Redes", redes);
        newUser.put("fechaCreacion", FieldValue.serverTimestamp());
        newUser.put("biografia", "");
        newUser.put("email", emailGoogle);
        newUser.put("fechaCum", FieldValue.serverTimestamp());
        newUser.put("localidad", "");
        newUser.put("name", nameGoogle);
        newUser.put("pais", "");
        newUser.put("password", "");
        newUser.put("profilePic", idRecurso);
        newUser.put("pronouns", "");
        newUser.put("subType", 0);
        newUser.put("surname", surnameGoogle);
        newUser.put("telf", "");
        newUser.put("tema", false);
        newUser.put("user", userGoogle);
        newUser.put("seguidores", 0);
        newUser.put("siguiendo", 0);
        newUser.put("reseñasCount", 0);
        newUser.put("visibilidad", "Todos");
        newUser.put("coments_respuestas", "Todos");
        newUser.put("noti_email", "Todos");
        newUser.put("notis", "Todos");
        newUser.put("google", true);
        newUser.put("listas", listas);
        newUser.put("reseñas",reseñas);

        db.collection("Users").document(uidGoogle)
                .set(newUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "Usuario creado correctamente.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Error al crear un usuario.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void crearListasGoogle() {

        Map<String, Object> data1 = new HashMap<>();
        ArrayList<Integer> games = new ArrayList<>();


        String[] opciones = {"Todos", "Jugando", "Jugados", "Pausado", "Dejado de jugar", "Para jugar"};
        for (int i = 0; i < opciones.length; i++) {
            String opcion = opciones[i];
            data1.put("games", games);
            data1.put("name", opcion);
            final int vuelta = i + 1;
            final String documentPath = "Lista" + uidGoogle + "_" + vuelta;
            db.collection("Lists").document(documentPath)
                    .set(data1)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mContext, "Usuario creado correctamente en la vuelta " + vuelta, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Error al crear un usuario en la vuelta " + vuelta, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}

