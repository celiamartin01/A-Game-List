package com.example.agamelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;


public class fragment_forgetpass extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button enviar;
    EditText et_correo;
    ImageButton bt_atras;
    public fragment_forgetpass() {
        // Required empty public constructor
    }

    public static fragment_forgetpass newInstance(String param1, String param2) {
        fragment_forgetpass fragment = new fragment_forgetpass();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgetpass, container, false);

        enviar = view.findViewById(R.id.bt_recuperar_enviar);
        bt_atras = view.findViewById(R.id.bt_Recuperar_Atras);
        et_correo = view.findViewById(R.id.et_Recuperar_Email);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el correo electrónico ingresado por el usuario
                String emailAddress = et_correo.getText().toString();

                // Enviar solicitud de recuperación de contraseña
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Se envió el correo electrónico de recuperación correctamente
                                Toast.makeText(getActivity(), "Se ha enviado un correo electrónico para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                            } else {
                                // Hubo un error al enviar el correo electrónico de recuperación
                                Toast.makeText(getActivity(), "Error al enviar el correo electrónico de recuperación", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        bt_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });


        return view;
    }
}

