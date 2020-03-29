package co.wscld.coachfy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Data.StorageManager;
import co.wscld.coachfy.Items.TreinoItem;
import co.wscld.coachfy.Objects.Treino;


/**
 * A simple {@link Fragment} subclass.
 */
public class TreinosFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.button_criar)
    CardView buttonCriar;

    ItemAdapter itemAdapter;
    public TreinosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_treinos, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemAdapter = new ItemAdapter();
        FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        recyclerView.setAdapter(fastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTreinos();

        buttonCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_treinosFragment_to_criarTreinoFragment2);
            }
        });

        fastAdapter.withOnClickListener(new OnClickListener() {
            @Override
            public boolean onClick(@javax.annotation.Nullable View v, IAdapter adapter, IItem item, int position) {
                TreinoItem treinoItem = (TreinoItem) item;
                Bundle bundle = new Bundle();
                bundle.putString("treinoId",treinoItem.getTreino().getId());



                Navigation.findNavController(v).navigate(R.id.action_treinosFragment_to_exerciciosFragment,bundle);
                return true;
            }
        });
    }

    public void loadTreinos(){
        StorageManager storageManager = new StorageManager(getContext());
        ArrayList<Treino> treinos = storageManager.getTreinos(null);
        for(Treino treino : treinos){
            itemAdapter.add(new TreinoItem(treino));
        }
    }
}
