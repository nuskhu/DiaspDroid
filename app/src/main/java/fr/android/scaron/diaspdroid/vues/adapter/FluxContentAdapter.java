package fr.android.scaron.diaspdroid.vues.adapter;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.acra.ACRA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import fr.android.scaron.diaspdroid.R;
import fr.android.scaron.diaspdroid.controler.LogControler;
import fr.android.scaron.diaspdroid.controler.ProfilControler;
import fr.android.scaron.diaspdroid.model.FluxPost;


public class FluxContentAdapter extends ArrayAdapter<FluxPost>{
    LayoutInflater inflater;
    Activity follower;
    private List<FluxPost> posts = new ArrayList<FluxPost>();

    private static Logger LOGGEUR = LoggerFactory.getLogger(FluxContentAdapter.class);
    private static LogControler LOG = LogControler.getInstance(LOGGEUR);


    public FluxContentAdapter(Activity follower,
                           int ressource, List<FluxPost> posts){
        super(follower, ressource);
        try{
            inflater = LayoutInflater.from(follower.getApplicationContext());
            this.follower = follower;
            this.posts = posts;
            setPosts(posts);
        } catch (Throwable thr) {
            LOG.e("Erreur : "+thr.toString());
            ACRA.getErrorReporter().handleException(thr);
            throw thr;
        }
    }

    public void setPosts(List<FluxPost> posts){
        try{
            //follower.updateTitle(String.valueOf(downloads.size()));
            this.posts = posts;
            super.notifyDataSetChanged();
        } catch (Throwable thr) {
            LOG.e("Erreur : "+thr.toString());
            ACRA.getErrorReporter().handleException(thr);
            throw thr;
        }
    }

    @Override
    public int getCount() {
        try{
            return posts.size();
        } catch (Throwable thr) {
            LOG.e("Erreur : "+thr.toString());
            ACRA.getErrorReporter().handleException(thr);
            throw thr;
        }
    }

    @Override
    public FluxPost getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        try{
            return posts.get(position).getId().longValue();
        } catch (Throwable thr) {
            LOG.e("Erreur : "+thr.toString());
            ACRA.getErrorReporter().handleException(thr);
            throw thr;
        }
    }

    private class FluxPostView {
        //flux_list_item_post_avatar
        ImageView avatarView;
        //flux_list_item_post_user
        TextView userView;
        //flux_list_item_post_datetime
        TextView datetimeView;
        //flux_list_item_post_detail
        //WebView detailView;
        TextView detailView;
        //flux_list_item_post_detail_picture
//        ImageView detailPictureView;
        ImageView detailPictureView;

        FluxPost post;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            FluxPostView fluxPostView;
            if(convertView == null) {
                fluxPostView = new FluxPostView();
                convertView = inflater.inflate(R.layout.flux_list_item, null);
                fluxPostView.avatarView = (ImageView)convertView.findViewById(R.id.flux_list_item_post_avatar);
                fluxPostView.userView = (TextView)convertView.findViewById(R.id.flux_list_item_post_user);
                fluxPostView.datetimeView = (TextView)convertView.findViewById(R.id.flux_list_item_post_datetime);
                //fluxPostView.detailView = (WebView)convertView.findViewById(R.id.flux_list_item_post_detail);
                fluxPostView.detailView = (TextView)convertView.findViewById(R.id.flux_list_item_post_detail);
                fluxPostView.detailPictureView = (ImageView)convertView.findViewById(R.id.flux_list_item_post_detail_picture);
//                fluxPostView.detailPictureView = (ImageView)convertView.findViewById(R.id.flux_list_item_post_detail_picture);
                convertView.setTag(fluxPostView);
            } else {
                fluxPostView = (FluxPostView) convertView.getTag();
            }
            FluxPost post = posts.get(position);
            fluxPostView.post = post;
            byte[] avatar = post.getAvatar();
            String avatarPath = post.getAvatarPath();
            if (avatar != null && avatar.length > 0) {
                try {
                    //TODO à mettre en place
                    fluxPostView.avatarView.setImageBitmap(BitmapFactory.decodeByteArray(post.getAvatar(), 0, post.getAvatar().length));
                } catch (Throwable thr) {
                }
            }else if (avatarPath != null && !avatarPath.isEmpty()){
                //TODO à mettre en place
                ProfilControler.putImage(fluxPostView.avatarView, avatarPath);
                //fluxPostView.avatarView.setImageBitmap(getImageBitmap(avatarPath));
            }else{
                fluxPostView.avatarView.setImageBitmap(null);
            }
            fluxPostView.userView.setText(post.getUsername());
            fluxPostView.datetimeView.setText(post.getDatetimeStr());

            String detailPicturePath = post.getDetailPicturePath();
//            fluxPostView.detailPictureView.getSettings().setUseWideViewPort(true);
//            fluxPostView.detailPictureView.getSettings().setLoadWithOverviewMode(true);
//            fluxPostView.detailPictureView.getSettings().setJavaScriptEnabled(true);
//            final String mimeType = "text/html";
//            final String encoding = "utf-8";
//            fluxPostView.detailPictureView.loadDataWithBaseURL(null, "<img style=\"width: 100%;\" src=\""+detailPicturePath+"\"></img>", mimeType, encoding, "");
//            if (detailPicturePath != null && !detailPicturePath.isEmpty()){
//                //TODO à mettre en place

            ProfilControler.putImage(fluxPostView.detailPictureView, detailPicturePath);
//                //fluxPostView.detailPictureView.setImageBitmap(getImageBitmap(detailPicturePath));
//            }else{
//                fluxPostView.detailPictureView.setImageBitmap(null);
//            }



            fluxPostView.detailView.setText(post.getDetail());
    //        fluxPostView.detailView.getSettings().setUseWideViewPort(true);
    //        fluxPostView.detailView.getSettings().setLoadWithOverviewMode(true);
    //        fluxPostView.detailView.getSettings().setJavaScriptEnabled(true);
    //        final String mimeType = "text/html";
    //        final String encoding = "utf-8";
    //        fluxPostView.detailView.loadDataWithBaseURL(null, post.getDetail(), mimeType, encoding, "");

            return convertView;
        } catch (Throwable thr) {
            LOG.e("Erreur : "+thr.toString());
            ACRA.getErrorReporter().handleException(thr);
            throw thr;
        }
    }

    private Bitmap getImageBitmap(String url) {
        try{
            LOG.v("<-- begin --> getImageBitmap with param url='"+url+"'");
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                LOG.e( "Erreur lors de la récupération de l'image '"+url+"' (" + e.getMessage()+")");
            }
            LOG.v("<-- end --> getImageBitmap with param url='"+url+"'");
            return bm;
        } catch (Throwable thr) {
            LOG.e("Erreur : "+thr.toString());
            ACRA.getErrorReporter().handleException(thr);
            throw thr;
        }
    }

}
