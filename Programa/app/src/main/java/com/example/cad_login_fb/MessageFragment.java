package com.example.cad_login_fb;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MessageFragment extends Fragment {

    private static final String TAG = "MessageFragment";
    private FirebaseFirestore firestore;
    private MessageAdapter messageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("messages").orderBy("timestamp");
        messageAdapter = new MessageAdapter(new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText messageEditText = view.findViewById(R.id.messageEditText);
        Button addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Botão clicado");

                String name = nameEditText.getText().toString().trim();
                String messageText = messageEditText.getText().toString().trim();

                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "Message: " + messageText);

                if (!name.isEmpty() && !messageText.isEmpty()) {
                    Log.d(TAG, "Adicionando mensagem ao Firestore");
                    addMessageToFirestore(name, messageText);
                    nameEditText.getText().clear();
                    messageEditText.getText().clear();
                } else {
                    Log.d(TAG, "Nome ou mensagem vazios");
                }
            }
        });

        return view;
    }

    private void addMessageToFirestore(String name, String text) {
        Log.d(TAG, "Adicionando mensagem ao Firestore");
        Map<String, Object> message = new HashMap<>();
        message.put("name", name);
        message.put("text", text);
        message.put("timestamp", FieldValue.serverTimestamp());

        firestore.collection("messages").add(message)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Mensagem adicionada com sucesso: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Erro ao adicionar mensagem ao Firestore", e);
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        messageAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        messageAdapter.stopListening();
    }
}
