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
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Data.StorageManager;
import co.wscld.coachfy.Items.ExercicioItem;
import co.wscld.coachfy.Items.TreinoHeader;
import co.wscld.coachfy.Items.TreinoItem;
import co.wscld.coachfy.Objects.Exercicio;
import co.wscld.coachfy.Objects.Treino;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciciosFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button_criar)
    CardView buttonCriar;
    ItemAdapter itemAdapter;
    String treinoId;
    public ExerciciosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercicios, container, false);
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

        loadHeader();
        loadExercicios();

        fastAdapter.withEventHook(new ClickEventHook() {
            @javax.annotation.Nullable
            @Override
            public View onBind(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof TreinoHeader.ViewHolder) {
                    return ((TreinoHeader.ViewHolder) viewHolder).buttonRealizar;
                }
                return null;
            }

            @Override
            public void onClick(View v, int position, FastAdapter fastAdapter, IItem item) {
                StorageManager storageManager = new StorageManager(getContext());
                storageManager.createHistorico(treinoId);
            }
        });

        buttonCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("treinoId",treinoId);
                Navigation.findNavController(v).navigate(R.id.action_exerciciosFragment_to_listaExerciciosFragment,bundle);
            }
        });
    }

    public void loadHeader(){
        StorageManager storageManager = new StorageManager(getContext());
        itemAdapter.add(new TreinoHeader(storageManager.getTreino(treinoId)));
    }

    public void loadExercicios(){
        StorageManager storageManager = new StorageManager(getContext());
        ArrayList<Exercicio> exercicios = storageManager.getExercicios(treinoId);
        for(Exercicio exercicio : exercicios){
            itemAdapter.add(new ExercicioItem(exercicio));
        }
    }
}
