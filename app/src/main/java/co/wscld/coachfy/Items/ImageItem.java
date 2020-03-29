package co.wscld.coachfy.Items;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wscld.coachfy.Objects.Exercicio;
import co.wscld.coachfy.R;

public class ImageItem extends AbstractItem<ImageItem,ImageItem.ViewHolder> {
    private int image;

    public ImageItem(int image){
        this.image = image;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_image;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<ImageItem> {
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindView(ImageItem item, List<Object> payloads) {
            System.out.println(item.image);
            Picasso.get()
                    .load(item.image)
                    .fit()
                    .into(imageView);
        }

        @Override
        public void unbindView(ImageItem item) {

        }
    }
}
