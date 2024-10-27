package org.example;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FirebaseInitializer {

    // Initialize Firebase
    public static void initializeFirebase() {
        try {
            // Load the service account key JSON file
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

            // Set up Firebase options using the service account
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Initialize Firebase
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase has been initialized.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to initialize Firebase.");
        }
    }

    // Get Firestore instance
    public static Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // Add session data to Firestore
    public static void addSessionToFirestore(int focusDuration, int numDistractions, int mindfulnessLevel, String condition, String task) {
        Firestore db = getFirestore();

        if (db != null) {
            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("focusDuration", focusDuration);
            sessionData.put("numDistractions", numDistractions);
            sessionData.put("mindfulnessLevel", mindfulnessLevel);
            sessionData.put("condition", condition);
            sessionData.put("task", task);

            ApiFuture<DocumentReference> result = db.collection("sessions").add(sessionData);
            result.addListener(() -> System.out.println("Session added to Firestore."), Runnable::run);
        }
    }

    // Retrieve all session data from Firestore
    public static void getSessionsFromFirestore() {
        Firestore db = getFirestore();

        if (db != null) {
            ApiFuture<QuerySnapshot> future = db.collection("sessions").get();
            try {
                QuerySnapshot querySnapshot = future.get();
                querySnapshot.getDocuments().forEach(document -> {
                    System.out.println("Document ID: " + document.getId());
                    System.out.println("Data: " + document.getData());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
