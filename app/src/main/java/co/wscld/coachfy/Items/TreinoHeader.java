package co.wscld.coachfy.Items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Data.StorageManager;
import co.wscld.coachfy.Objects.Exercicio;
import co.wscld.coachfy.Objects.Treino;
import co.wscld.coachfy.R;

public class TreinoHeader extends AbstractItem<TreinoHeader,TreinoHeader.ViewHolder> {
    private Treino treino;
    private StorageManager storageManager;

    public Treino getTreino() {
        return treino;
    }

    public TreinoHeader(Treino treino){
        this.treino = treino;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.header_treino;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.header_treino;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<TreinoHeader> {
        @BindView(R.id.textNome)
        TextView textNome;
        @BindView(R.id.textNumeroExercicios)
        TextView textNumeroExercicios;
        @BindView(R.id.textTempo)
        TextView textTempo;
        @BindView(R.id.imageIcon)
        ImageView imageIcon;
        @BindView(R.id.button_realizar)
        public CardView buttonRealizar;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            storageManager = new StorageManager(itemView.getContext());
        }

        @Override
        public void bindView(TreinoHeader item, List<Object> payloads) {
            ArrayList<Exercicio> exercicios = storageManager.getExercicios(item.treino.getId());
            int minutos = ((exercicios.size()*80)/60);
            textNome.setText(item.treino.getNome());
            textNumeroExercicios.setText(exercicios.size()+ (exercicios.size()==1? " exercicio":" exercicios"));
            textTempo.setText((minutos) + (minutos==1? " minuto":" minutos"));

            ArrayList iconList = storageManager.getTreinoImages();
            Picasso.get()
                    .load((int)iconList.get(item.treino.getIconId()))
                    .fit()
                    .into(imageIcon);
        }

        @Override
        public void unbindView(TreinoHeader item) {

        }
    }
}
