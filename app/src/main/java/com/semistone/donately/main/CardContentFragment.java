package com.semistone.donately.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.semistone.donately.R;
import com.semistone.donately.data.Content;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by semistone on 2017-02-20.
 */

public class CardContentFragment extends Fragment {

    private static final String KEY_TYPE = "KEY_TYPE";
    private Realm mRealm;
    private String mType;

    public static CardContentFragment newInstance(String type) {
        CardContentFragment f = new CardContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(KEY_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ButterKnife.bind(this, recyclerView);
        mRealm = Realm.getDefaultInstance();
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext(), mRealm.where(Content.class).equalTo(Content.TYPE, mType).findAll());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ContentAdapter extends RealmRecyclerViewAdapter<Content, ViewHolder> {

        private Context mContext;

        public ContentAdapter(Context context, OrderedRealmCollection<Content> data) {
            super(context, data, true);
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Content content = getData().get(position);
            holder.data = content;
            Glide.with(mContext).load(content.getPictureUrl()).into(holder.image);
            holder.title.setText(content.getTitle());
            holder.description.setText(content.getDescription());
            holder.updateFavoriteImageButton(content.isFavorite());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_image)
        protected ImageView image;
        @BindView(R.id.card_title)
        protected TextView title;
        @BindView(R.id.card_text)
        protected TextView description;
        @BindView(R.id.action_button)
        protected Button button;
        @BindView(R.id.favorite_button)
        protected ImageButton favoriteImageButton;
        @BindView(R.id.link_button)
        protected ImageButton linkImageButton;
        @BindColor(R.color.colorPrimary)
        protected int colorPrimary;
        @BindColor(R.color.button_grey)
        protected int buttonGrey;

        private Content data;
        private Realm mRealm;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.CONTENT_ID, data.getId());
                    context.startActivity(intent);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, String.valueOf(data.getId()),
                            Snackbar.LENGTH_SHORT).show();
                }
            });

            favoriteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRealm = Realm.getDefaultInstance();
                    Content content = mRealm.where(Content.class).equalTo(Content.ID, data.getId()).findFirst();
                    final boolean newFavorite = !content.isFavorite();
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Content subject = realm.where(Content.class).equalTo(Content.ID, data.getId()).findFirst();
                            subject.setFavorite(newFavorite);
                            updateFavoriteImageButton(newFavorite);
                        }
                    });
                    if(newFavorite) {
                        Snackbar.make(v, "Added to Favorites.", Snackbar.LENGTH_SHORT).show();
                    }
                    mRealm.close();
                }
            });

            linkImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebPage(data.getLinkUrl());
                }
            });
        }

        public void updateFavoriteImageButton(boolean isFavorite) {
            int iconColor = isFavorite ? colorPrimary : buttonGrey;
            favoriteImageButton.setColorFilter(iconColor);
        }

        private void openWebPage(String url) {
            // TODO: 2017-02-21  
//            Uri webPage = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
//            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
//                mContext.startActivity(intent);
//            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}