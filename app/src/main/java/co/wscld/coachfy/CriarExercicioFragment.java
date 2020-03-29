package co.wscld.coachfy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Data.StorageManager;
import co.wscld.coachfy.Data.ListaGruposMusculares;


/**
 * A simple {@link Fragment} subclass.
 */
public class CriarExercicioFragment extends Fragment {

    @BindView(R.id.inputExercicio)
    TextInputEditText inputExercicio;
    @BindView(R.id.inputDetalhes)
    TextInputEditText inputDetalhes;
    @BindView(R.id.inputCarga)
    TextInputEditText inputCarga;
    @BindView(R.id.inputRepeticoes)
    TextInputEditText inputRepeticoes;
    @BindView(R.id.selectGrupoMuscular)
    Spinner selectGrupoMuscular;
    @BindView(R.id.button_criar)
    CardView buttonCriar;
    @BindView(R.id.inputSeries)
    TextInputEditText inputSeries;
    String treinoId;
    public CriarExercicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criar_exercicio, container, false);
        ButterKnife.bind(this,view);
        treinoId = this.getArguments().getString("treinoId");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Integer> gruposMusculares = new ArrayList<>();
        ArrayList<String> gruposMuscularesString = new ArrayList<>();
        StorageManager storageManager = new StorageManager(getContext());
        ListaGruposMusculares listaGruposMusculares = new ListaGruposMusculares();
        int foco = storageManager.getFocoTreino(treinoId);

        if(foco != 0){
            gruposMusculares.add(foco);
        }

        for(int grupoMuscular : listaGruposMusculares.lista()){
            if(!gruposMusculares.contains(grupoMuscular)){
                gruposMusculares.add(grupoMuscular);
            }
        }

        for(int grupoMuscular : gruposMusculares){
            gruposMuscularesString.add(listaGruposMusculares.getNomeFor(grupoMuscular));
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(),  android.R.layout.simple_spinner_dropdown_item, gruposMuscularesString);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        selectGrupoMuscular.setAdapter(adapter);

        buttonCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int carga = Helper.StringtoInt(inputCarga.getText().toString());
                int repeticoes =  Helper.StringtoInt(inputRepeticoes.getText().toString());
                int series =  Helper.StringtoInt(inputSeries.getText().toString());
                String exercicio = inputExercicio.getText().toString();
                String detalhes = inputDetalhes.getText().toString();
                final int grupoMuscular = gruposMusculares.get(selectGrupoMuscular.getSelectedItemPosition());

                StorageManager storageManager = new StorageManager(getContext());
                storageManager.createExercicio(treinoId,exercicio,grupoMuscular,detalhes,carga,repeticoes,series);
            }
        });
    }
}
