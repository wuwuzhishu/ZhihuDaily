package sptpc.com.zhihudaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sptpc.com.zhihudaily.model.News;
import sptpc.com.zhihudaily.R;

public class NewsAdapter extends ArrayAdapter<News.StoriesBean> {
    private int resourceID;
    private Context mContext;

    public NewsAdapter(Context context, int resource, List<News.StoriesBean> objects) {
        super(context, resource, objects);
        this.resourceID = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News.StoriesBean storiesBean = getItem(position);

        ViewHolder viewHolder;
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceID,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.textView = (TextView) view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(mContext)
                .load(storiesBean.getImages().get(0))
                .into(viewHolder.imageView);
        viewHolder.textView.setText(storiesBean.getTitle());
        return view;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}