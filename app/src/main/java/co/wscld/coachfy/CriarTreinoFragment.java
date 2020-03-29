package co.wscld.coachfy;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Data.ListaGruposMusculares;
import co.wscld.coachfy.Data.StorageManager;
import co.wscld.coachfy.Data.WebServiceManager;
import co.wscld.coachfy.Items.ImageItem;
import co.wscld.coachfy.Objects.Tag;


/**
 * A simple {@link Fragment} subclass.
 */
public class CriarTreinoFragment extends Fragment {
    @BindView(R.id.button_criar)
    CardView buttonCriar;
    @BindView(R.id.inputDescricao)
    TextInputEditText inputDescricao;
    @BindView(R.id.inputNome)
    TextInputEditText inputNome;
    @BindView(R.id.selectTag)
    Spinner selectTag;
    @BindView(R.id.buttonCriarTag)
    Button buttonCriarTag;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iconPicker)
    RelativeLayout iconPicker;
    @BindView(R.id.imageIcon)
    ImageView imageIcon;

    ItemAdapter itemAdapter;
    ArrayList iconList;
    int iconId = 0;

    public CriarTreinoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criar_treino, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iconList = new StorageManager(getContext()).getTreinoImages();
        itemAdapter = new ItemAdapter();
        FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        recyclerView.setAdapter(fastAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        Picasso.get()
                .load((int)iconList.get(iconId))
                .fit()
                .into(imageIcon);
        iconPicker.setVisibility(View.GONE);

        loadTags();
        loadImages();

        imageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconPicker.setVisibility(View.VISIBLE);
            }
        });

        fastAdapter.withOnClickListener(new OnClickListener() {
            @Override
            public boolean onClick(@javax.annotation.Nullable View v, IAdapter adapter, IItem item, int position) {
                iconId = position;
                Picasso.get()
                        .load((int)iconList.get(iconId))
                        .fit()
                        .into(imageIcon);
                iconPicker.setVisibility(View.GONE);
                return true;
            }
        });

        buttonCriarTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                final EditText edittext = new EditText(getContext());
                alert.setMessage("Nova Tag");
                alert.setTitle("Criar nova tag");

                alert.setView(edittext);

                alert.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = edittext.getText().toString();
                        StorageManager storageManager = new StorageManager(getContext());
                        storageManager.createTag(name);
                        loadTags();
                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });

        buttonCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descricao = inputDescricao.getText().toString();
                String nome = inputNome.getText().toString();

                final ArrayList<Tag> tags = new ArrayList<>();
                tags.addAll(new StorageManager(getContext()).getTags());

                StorageManager storageManager = new StorageManager(getContext());
                storageManager.createTreino(nome,descricao,tags.get(selectTag.getSelectedItemPosition()).getId(),iconId);

            }
        });
    }

    private void loadImages(){
        for(Object image : iconList){
            itemAdapter.add(new ImageItem((int)image));
        }
    }

    private void loadTags(){
        final ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<String> tagNames = new ArrayList<>();
        tags.addAll(new StorageManager(getContext()).getTags());
        for(Tag tag : tags){
                tagNames.add(tag.getName());
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(),  android.R.layout.simple_spinner_dropdown_item, tagNames);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        selectTag.setAdapter(adapter);

    }
}
