package com.example.workoutgen;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirestoreHelper {
    private FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    // Add a document to a specific collection
    public Task<DocumentReference> addDocument(String collectionPath, Object data) {
        CollectionReference collectionReference = db.collection(collectionPath);
        return collectionReference.add(data);
    }

    // Update a document in a specific collection
    public Task<Void> updateDocument(String collectionPath, String documentId, Object data) {
        DocumentReference documentReference = db.collection(collectionPath).document(documentId);
        return documentReference.set(data);
    }

    // Get all documents from a specific collection
    public Task<QuerySnapshot> getAllDocuments(String collectionPath) {
        CollectionReference collectionReference = db.collection(collectionPath);
        return collectionReference.get();
    }

    // Get documents based on a query from a specific collection
    public Task<QuerySnapshot> getDocumentsWithQuery(String collectionPath, Query query) {
        CollectionReference collectionReference = db.collection(collectionPath);
        return query.get();
    }
}
