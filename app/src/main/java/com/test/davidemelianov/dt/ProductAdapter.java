package com.test.davidemelianov.dt;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    protected Context mContext;
    protected List<Product> mEntries;

    ImageLoader imageLoader = ImageLoader.getInstance();

    public ProductAdapter(Context context, List<Product> entries) {
        super(context, R.layout.item_entry, entries);

        mContext = context;
        mEntries = entries;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder listItem;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_entry, null);
            listItem = new ViewHolder();
        } else {
            listItem = (ViewHolder) convertView.getTag();
        }

        Product product = mEntries.get(position);

        listItem.mTitleLabel = (TextView) convertView.findViewById(R.id.beat_title);
        listItem.mTitleLabel.setText(product.title);

        listItem.mDescriptionLabel = (TextView) convertView.findViewById(R.id.beat_artist);
        listItem.mDescriptionLabel.setText(product.description);

        listItem.mThumbnail = (ImageView) convertView.findViewById(R.id.cover_art);
        listItem.mThumbnail.setImageResource(R.drawable.empty);
        listItem.mThumbnailProgress = (ProgressBar) convertView.findViewById(R.id.progress);
        listItem.mThumbnailProgress.setVisibility(View.VISIBLE);

        String mPictureURL = product.thumbnail;

        if (mPictureURL != null) {
            if (mPictureURL.equals("0")) {
                listItem.mThumbnailProgress.setVisibility(View.GONE);
            } else {
                imageLoader.displayImage(mPictureURL, listItem.mThumbnail, null, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        listItem.mThumbnailProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        listItem.mThumbnailProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        listItem.mThumbnailProgress.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view, int current, int total) {
                    }
                });
            }
        } else {
            listItem.mThumbnailProgress.setVisibility(View.GONE);
        }

        convertView.setTag(listItem);

        // TRY THIS TO FIX RECYCLING !!!!!!!!!!!!!!!!

        // if (position != lastClicked) {
        // // holder.playButton.setImageResource(R.drawable.play_button);
        // holder.mBackground
        // .setBackgroundColor(Color.parseColor("#19FFFFFF"));
        // }

        return convertView;
    }

    private static class ViewHolder {

        TextView mTitleLabel;
        TextView mDescriptionLabel;
        ImageView mThumbnail;
        ProgressBar mThumbnailProgress;

    }

}