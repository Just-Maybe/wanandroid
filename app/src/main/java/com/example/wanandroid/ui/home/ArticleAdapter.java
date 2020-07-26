package com.example.wanandroid.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.databinding.ItemArticleBinding;
import com.example.wanandroid.databinding.ItemBannerBinding;
import com.example.wanandroid.databinding.ItemBannerBindingImpl;
import com.example.wanandroid.ui.article_detail.ArticleWebViewActivity;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miracle on 2020/7/20
 * Email: zhaoqirong96@gmail.com
 * Describe:
 */
public class ArticleAdapter extends RecyclerView.Adapter {
    private Context context;
    private static final int TYPE_BANNER = 773;  //banner 样式
    private static final int TYPE_ARTICLE = 777; //文章样式
    private List<BannerBean> bannerList = new ArrayList<>();
    private List<ArticleBean> articleList = new ArrayList<>();
    private List<ArticleBean> topArticleList = new ArrayList<>();

    public ArticleAdapter(Context context) {
        this.context = context;
    }

    public void setBanner(List<BannerBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            this.bannerList = dataList;
            notifyItemChanged(0);
        }
    }

    public void setTopArticle(List<ArticleBean> dataList) {
        topArticleList.clear();
        if (dataList != null && dataList.size() > 0) {
            for (ArticleBean articleBean : dataList) {
                articleBean.setTopArticle(true);
            }
            topArticleList.addAll(dataList);
            articleList.addAll(0, topArticleList);
            notifyItemRangeChanged(1, topArticleList.size());
        }
    }

    public void setData(List<ArticleBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            articleList = dataList;
            if(topArticleList.size()>0){
                articleList.addAll(0, topArticleList);
            }
            notifyDataSetChanged();
        }
    }

    public void addData(List<ArticleBean> dataList) {
        if (dataList != null && dataList.size() > 0) {
            articleList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        articleList.clear();
        bannerList.clear();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BANNER:
                ItemBannerBindingImpl bannerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_banner, parent, false);
                return new BannerViewHolder(bannerBinding);
        }
        ItemArticleBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_article, parent, false);
        return new ArticleViewHolder(dataBinding);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && bannerList.size() > 0) {
            return TYPE_BANNER;
        }
        return TYPE_ARTICLE;
    }

    @Override
    public int getItemCount() {
        return bannerList.size() > 0 ? 1 + articleList.size() : articleList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ARTICLE:
                ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
                ArticleBean bean = articleList.get(bannerList.size() == 0 ? position : position - 1);
                articleViewHolder.bind(bean);
                break;
            case TYPE_BANNER:
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                bannerViewHolder.initBanner();
                break;
        }
    }


    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        private ItemArticleBinding binding;

        public ArticleViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ArticleBean bean) {
            binding.setArticle(bean);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArticleWebViewActivity.launch(context, bean.getTitle(), bean.getLink());
                }
            });
            binding.cbCollection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private ItemBannerBinding databinding;

        public BannerViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            this.databinding = binding;
        }

        public void initBanner() {
            databinding.banner.setIndicator(new CircleIndicator(context));
            databinding.banner.setAdapter(new BannerImageAdapter<BannerBean>(bannerList) {
                @Override
                public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                    Glide.with(context).load(data.getImagePath()).into(holder.imageView);
                }
            });
            databinding.banner.setOnBannerListener(new OnBannerListener<BannerBean>() {
                @Override
                public void OnBannerClick(BannerBean bean, int position) {
                    ArticleWebViewActivity.launch(context, bean.getTitle(), bean.getUrl());
                }
            });
        }
    }

    private static class ArticleDiffCallback extends DiffUtil.ItemCallback<ArticleBean> {

        @Override
        public boolean areItemsTheSame(@NonNull ArticleBean oldItem, @NonNull ArticleBean newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ArticleBean oldItem, @NonNull ArticleBean newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

    }
}
