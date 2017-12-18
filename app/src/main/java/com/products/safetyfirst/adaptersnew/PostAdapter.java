package com.products.safetyfirst.adaptersnew;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.R;
import com.products.safetyfirst.androidhelpers.PostDocument;
import com.products.safetyfirst.utils.JustifiedWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView adapter for a list of Posts.
 */
public class PostAdapter extends FirestoreAdapter<PostAdapter.ViewHolder>{


    public interface OnPostSelectedListener {

        void onPostSelected(DocumentSnapshot post);

    }

    private OnPostSelectedListener mListener;

    public PostAdapter(OnPostSelectedListener listener) {
        Log.e("PostAdapter","Post Adapter Constructor called");
        makeQuery();
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.discussion_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_image)
        ImageView imageView;

        @BindView(R.id.post_title)
        TextView postTitle;

        @BindView(R.id.post_author)
        TextView postAuthor;

        @BindView(R.id.post_body)
        JustifiedWebView postBody;

        @BindView(R.id.post_author_photo)
        ImageView postAutorPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final PostDocument snapshot,
                         final OnPostSelectedListener listener) {

            PostModel postModel= new PostModel(snapshot.getPostDocument().getData(),snapshot.getUserDocument().getData()) ;

            Resources resources = itemView.getResources();

            // Load image
         /*   Glide.with(imageView.getContext())
                    .load(postModel.getPhotoUrl())
                    .into(imageView);
            Glide.with(imageView.getContext())
                    .load(postModel.getAuthorImageUrl())
                    .into(postAutorPhoto);
            postTitle.setText(postModel.getTitle());
            postAuthor.setText(postModel.getAuthor());
            postBody.setText(postModel.getBody());*/

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onPostSelected(snapshot.getPostDocument());
                    }
                }
            });
        }

    }

}
