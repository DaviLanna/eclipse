package com.inkids.dao;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.inkids.model.Contato;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ContatoDAO {
    private static final String COLLECTION = "contatos";
    private final Firestore db = FirestoreClient.getFirestore();

    public Contato save(Contato contato) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(COLLECTION).document();
        contato.setId(docRef.getId());
        docRef.set(contato).get();
        return contato;
    }
    
    public List<Contato> getAll() throws ExecutionException, InterruptedException {
        List<Contato> list = new ArrayList<>();
        db.collection(COLLECTION).orderBy("createdAt", Query.Direction.DESCENDING).get().get().getDocuments().forEach(d -> list.add(d.toObject(Contato.class)));
        return list;
    }

    public boolean delete(String id) {
        try {
            db.collection(COLLECTION).document(id).delete().get();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}