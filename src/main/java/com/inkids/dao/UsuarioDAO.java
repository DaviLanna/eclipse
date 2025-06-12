package com.inkids.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.inkids.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsuarioDAO {
    private static final String COLLECTION = "usuarios";
    private final Firestore db = FirestoreClient.getFirestore();

    public Usuario insert(Usuario usuario) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(COLLECTION).document();
        usuario.setId(docRef.getId());
        if (usuario.getTipoUsuario() == null) usuario.setTipoUsuario("USER");
        docRef.set(usuario).get();
        return usuario;
    }

    public Usuario get(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
        return doc.exists() ? doc.toObject(Usuario.class) : null;
    }

    public Usuario getByEmail(String email) throws ExecutionException, InterruptedException {
        Query query = db.collection(COLLECTION).whereEqualTo("email", email).limit(1);
        List<QueryDocumentSnapshot> docs = query.get().get().getDocuments();
        return !docs.isEmpty() ? docs.get(0).toObject(Usuario.class) : null;
    }

    public List<Usuario> getAll() throws ExecutionException, InterruptedException {
        List<Usuario> list = new ArrayList<>();
        db.collection(COLLECTION).get().get().getDocuments().forEach(d -> list.add(d.toObject(Usuario.class)));
        return list;
    }

    public Usuario update(Usuario usuario) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION).document(usuario.getId()).set(usuario).get();
        return usuario;
    }

    public boolean delete(String id) {
        try {
            db.collection(COLLECTION).document(id).delete().get();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}