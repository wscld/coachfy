package co.wscld.coachfy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Data.WebServiceManager;
import co.wscld.coachfy.Items.ExercicioItem;
import co.wscld.coachfy.Objects.Exercicio;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaExerciciosFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button_criar)
    CardView button;
    ItemAdapter itemAdapter;
    String treinoId;
    public ListaExerciciosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_exercicios, container, false);
        ButterKnife.bind(this,view);
        treinoId = this.getArguments().getString("treinoId");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemAdapter = new ItemAdapter();
        FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        recyclerView.setAdapter(fastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("treinoId",treinoId);
                Navigation.findNavController(v).navigate(R.id.action_listaExerciciosFragment_to_criarExercicioFragment,bundle);
            }
        });

        loadExercicios();
    }

    public void loadExercicios(){
        WebServiceManager webServiceManager = new WebServiceManager(getContext());
        webServiceManager.getExercicios(4, new WebServiceManager.GetExerciciosListener() {
            @Override
            public void OnComplete(ArrayList<Exercicio> exercicios) {
                for(Exercicio exercicio : exercicios){
                    itemAdapter.add(new ExercicioItem(exercicio));
                }
            }

            @Override
            public void OnFailed() {

            }
        });
    }
}
