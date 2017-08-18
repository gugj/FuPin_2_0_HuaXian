package com.roch.fupin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.roch.fupin.entity.PoorPhoto;
import com.roch.fupin.utils.Common;
import com.roch.fupin.utils.FileUtils;
import com.roch.fupin.utils.LogUtil;
import com.roch.fupin.utils.URLs;
import com.roch.fupin_2_0.R;

import java.util.List;

public class newchildAdapter1 extends BaseAdapter {

    protected LayoutInflater mInflater;
    /**
     * ListView中每个条目的GridView
     */
    private GridView mgridView;
    private List<PoorPhoto> list;

    private BitmapUtils utils;
    BitmapDisplayConfig bitmapDisplayConfig;
    BitmapLoadCallBack<ImageView> callback;

    public newchildAdapter1(final Context cn, List<PoorPhoto> liam, GridView g) {
        this.list = liam;
        this.mgridView = g;
        this.mInflater = LayoutInflater.from(cn);

        //获取应用程序的最大可用内存
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        int catchSize = maxMemory / 8;
        FileUtils fileUtils = new FileUtils(cn, Common.CACHE_DIR);
        utils = new BitmapUtils(cn, fileUtils.getCacheDir(), catchSize);

        bitmapDisplayConfig=new BitmapDisplayConfig();
        bitmapDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
        bitmapDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(cn));

        callback = new DefaultBitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
//                Toast.makeText(cn, uri, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                super.onLoadCompleted(container, uri, bitmap, config, from);
//                Toast.makeText(cn, bitmap.getWidth() + "*" + bitmap.getHeight(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public int getCount() {
        LogUtil.println("照片组里每一组的照片数量为：=="+list.size());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_list1, null);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.img1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PoorPhoto poorPhoto = (PoorPhoto)getItem(position);
        if (poorPhoto != null) {
            String aString = URLs.IMAGE_URL_New;
            String url=aString+poorPhoto.getImagepath();
            LogUtil.println("拼接之后的照片路径为==="+url);
//            utils.display(holder.mImageView, url);
            utils.display(holder.mImageView, url, bitmapDisplayConfig, callback);
        }
        return convertView;
    }

    public static class ViewHolder {
        private ImageView mImageView;
    }

}
