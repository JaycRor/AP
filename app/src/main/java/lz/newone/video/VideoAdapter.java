package lz.newone.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import lz.newone.R;
import lz.newone.beans.VideoBean;
import lz.newone.url.Urls;
import lz.newone.utils.ImageLoaderUtils;

/**
 * Created by AdminJax on 2017/10/14.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<VideoBean> videoBeanList=new ArrayList<>();
    private boolean mShowFooter = true;
    private Context mContext;

    public VideoAdapter(Context context) {
        this.mContext = context;
    }

    public void setDate(List<VideoBean> list){
        this.videoBeanList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        ItemViewHolder vh = new ItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder){
            VideoBean videoBean = videoBeanList.get(position);
            if (videoBean !=null){
                ((ItemViewHolder) holder).jc.setUp(Urls.ZX_WEB+videoBean.getSrc(),
                        JCVideoPlayer.SCREEN_LAYOUT_LIST,videoBean.getTitle());
//                ImageLoader.getInstance().displayImage(Urls.ZX_WEB+videoBean.getCover(),
//                        ((ItemViewHolder) holder).jc.thumbImageView);
                ImageLoaderUtils.display(mContext,((ItemViewHolder) holder).jc.thumbImageView,
                        Urls.ZX_WEB+videoBean.getCover());
            }
        }
    }

    @Override
    public int getItemCount() {
        return videoBeanList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        JCVideoPlayerStandard jc ;

        public ItemViewHolder(View itemView) {
            super(itemView);
            jc = itemView.findViewById(R.id.video_play);
        }
    }
}
