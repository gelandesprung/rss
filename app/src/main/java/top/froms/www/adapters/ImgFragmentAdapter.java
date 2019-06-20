package top.froms.www.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import top.froms.www.R;
import top.froms.www.adapters.ImgFragmentAdapter.ImgHolder;
import top.froms.www.repo.ImgData;

/**
 * 北京中油瑞飞信息技术有限责任公司 研究院 瑞信项目All Rights Reserved
 *
 * @author : yanchao
 * @version ：
 * @project : 瑞信项目
 * @ClassName:ImgFragmentAdapter
 * @Description:
 * @date : 2019-06-20 13:03
 */
public class ImgFragmentAdapter extends PagedListAdapter<ImgData, ImgHolder> {

    public ImgFragmentAdapter(
            @NonNull ItemCallback<ImgData> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImgHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.img_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImgHolder holder, int position) {
        holder.title.setText(getItem(position).getTitle());
        holder.publication.setText(getItem(position).getPublishTime());
        Glide.with(holder.img).load(getItem(position).getImgUrl()).into(holder.img);

    }

    class ImgHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView publication;
        ImageView img;

        public ImgHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            publication = itemView.findViewById(R.id.tv_content);
            img = itemView.findViewById(R.id.iv_img);
        }
    }
}
