package co.wscld.coachfy.Data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import co.wscld.coachfy.Objects.Exercicio;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class WebServiceManager {
    FirebaseFirestore db;

    public WebServiceManager(Context context){
        db = FirebaseFirestore.getInstance();
    }
    public void getExercicios(final int grupo, final GetExerciciosListener getExerciciosListener){
        DocumentReference docRef = db.collection("exercicios").document(String.valueOf(grupo));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<Exercicio> exercicios = new ArrayList<>();
                        Map<String, Object> map = document.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            Exercicio exercicio = new Exercicio();
                            exercicio.setNome(entry.getKey());
                            exercicio.setGrupoMuscular(grupo);
                            exercicios.add(exercicio);
                        }
                        getExerciciosListener.OnComplete(exercicios);
                    }
                } else {
                    getExerciciosListener.OnFailed();
                }
            }
        });
    }

    public interface GetExerciciosListener{
        void OnComplete(ArrayList<Exercicio> exercicios);
        void OnFailed();
    }
}
