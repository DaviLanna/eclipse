package com.inkids.dao;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.inkids.model.Postagem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PostagemDAO {
    private static final String COLLECTION = "postagens";
    private final Firestore db = FirestoreClient.getFirestore();

    public Postagem save(Postagem postagem) throws ExecutionException, InterruptedException {
        if (postagem.getId() == null || postagem.getId().isEmpty()) {
            // Cria um novo post
            DocumentReference docRef = db.collection(COLLECTION).document();
            postagem.setId(docRef.getId());
            docRef.set(postagem).get();
        } else {
            // Atualiza um post existente
            db.collection(COLLECTION).document(postagem.getId()).set(postagem).get();
        }
        return postagem;
    }

    public Postagem get(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
        return doc.exists() ? doc.toObject(Postagem.class) : null;
    }

    public List<Postagem> getAll() throws ExecutionException, InterruptedException {
        List<Postagem> list = new ArrayList<>();
        // Ordena as postagens da mais nova para a mais antiga
        db.collection(COLLECTION).orderBy("createdAt", Query.Direction.DESCENDING).get().get().getDocuments().forEach(d -> list.add(d.toObject(Postagem.class)));
        return list;
    }
    
    public List<Postagem> getByUserId(String usuarioId) throws ExecutionException, InterruptedException {
        List<Postagem> list = new ArrayList<>();
        db.collection(COLLECTION).whereEqualTo("autorId", usuarioId).orderBy("createdAt", Query.Direction.DESCENDING).get().get().getDocuments().forEach(d -> list.add(d.toObject(Postagem.class)));
        return list;
    }

    public boolean delete(String id) {
        try {
            db.collection(COLLECTION).document(id).delete().get();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}