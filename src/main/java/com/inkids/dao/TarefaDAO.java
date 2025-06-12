package com.inkids.dao;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.inkids.model.Tarefa;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TarefaDAO {
    private static final String COLLECTION = "tarefas";
    private final Firestore db = FirestoreClient.getFirestore();

    public Tarefa save(Tarefa tarefa) throws ExecutionException, InterruptedException {
        if (tarefa.getId() == null || tarefa.getId().isEmpty()) {
            DocumentReference docRef = db.collection(COLLECTION).document();
            tarefa.setId(docRef.getId());
            docRef.set(tarefa).get();
        } else {
            db.collection(COLLECTION).document(tarefa.getId()).set(tarefa).get();
        }
        return tarefa;
    }

    public Tarefa get(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
        return doc.exists() ? doc.toObject(Tarefa.class) : null;
    }

    public List<Tarefa> getAll() throws ExecutionException, InterruptedException {
        List<Tarefa> list = new ArrayList<>();
        db.collection(COLLECTION).orderBy("dataCriacao", Query.Direction.DESCENDING).get().get().getDocuments().forEach(d -> list.add(d.toObject(Tarefa.class)));
        return list;
    }

    public boolean delete(String id) {
        try {
            db.collection(COLLECTION).document(id).delete().get();
            return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}