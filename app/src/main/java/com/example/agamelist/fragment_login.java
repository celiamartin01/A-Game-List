package com.example.agamelist;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragment_login extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Context mContext;
    ImageView btGoogle;
    ImageButton btatras;
    FirebaseFirestore db;
    int idRecurso;
    String uidGoogle, nameGoogle, surnameGoogle, emailGoogle, userGoogle;

    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mContext = requireContext();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSignInButton = view.findViewById(R.id.bt_Login_Iniciarsesion);
        btatras = view.findViewById(R.id.bt_Login_Atras);
        btGoogle = view.findViewById(R.id.bt_login_google);
        db = FirebaseFirestore.getInstance();
        idRecurso = getResources().getIdentifier("fotoperfil5", "drawable", mContext.getPackageName());

        btatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        btGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        iniciarSesion();
    }

    private void iniciarSesion() {
        mEmailField = getView().findViewById(R.id.et_Login_Email);
        mPasswordField = getView().findViewById(R.id.et_Login_Contraseña);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailField.getText().toString().trim();
                String password = mPasswordField.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(mContext, "Porfavor introduzca un email y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Inicio de sesión exitoso
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        NavController navController = Navigation.findNavController(view);
                                        navController.navigate(R.id.main_pagina_principal);
                                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(mContext, "Credenciales inválidas.", Toast.LENGTH_SHORT).show();
                                    } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                        Toast.makeText(mContext, "Usuario no existe.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "Falló el inicio de sesión.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                nameGoogle = account.getGivenName();
                emailGoogle = account.getEmail();
                surnameGoogle = account.getFamilyName();
                userGoogle = account.getDisplayName();
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
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
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uidGoogle = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            verifyGoogleAccountExists(user.getEmail());
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(mContext, "No se pudo iniciar sesión con la cuenta de Google.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void verifyGoogleAccountExists(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Si la consulta devuelve algún documento, la cuenta de Google existe
                            if (!task.getResult().isEmpty()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // La cuenta de Google no existe
                                crearListasGoogle();
                                crearBBDDGoogle();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            NavController navController = Navigation.findNavController(getView());
            navController.navigate(R.id.main_pagina_principal);
        }
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