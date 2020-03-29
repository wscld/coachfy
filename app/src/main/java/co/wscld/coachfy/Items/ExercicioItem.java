package co.wscld.coachfy.Items;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.listeners.OnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Objects.Exercicio;
import co.wscld.coachfy.R;

public class ExercicioItem extends AbstractItem<ExercicioItem,ExercicioItem.ViewHolder> {
    private Exercicio exercicio;

    public ExercicioItem(Exercicio exercicio){
        this.exercicio = exercicio;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.item_exercicio;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_exercicio;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<ExercicioItem> {
        @BindView(R.id.textExercicio)
        TextView textExercicio;
        @BindView(R.id.textCarga)
        TextView textCarga;
        @BindView(R.id.textRepeticoes)
        TextView textRepeticoes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindView(ExercicioItem item, List<Object> payloads) {
            textExercicio.setText(item.exercicio.getNome());
            textCarga.setText(item.exercicio.getCarga()+"kg");
            textRepeticoes.setText(item.exercicio.getRepeticoes()+"x");
            if(item.exercicio.getCarga() == 0){
                textCarga.setVisibility(View.GONE);
            }

            if(item.exercicio.getRepeticoes() == 0){
                textRepeticoes.setVisibility(View.GONE);
            }
        }

        @Override
        public void unbindView(ExercicioItem item) {

        }
    }
}
