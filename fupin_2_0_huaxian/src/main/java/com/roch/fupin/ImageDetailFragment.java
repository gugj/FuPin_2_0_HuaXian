package com.roch.fupin;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.roch.fupin.entity.Photo;
import com.roch.fupin.photo.PhotoViewAttacher;
import com.roch.fupin.photo.PhotoViewAttacher.OnPhotoTapListener;
import com.roch.fupin_2_0.R;

/**
 * @author ZhaoDongShao
 * 2016年5月10日
 */
public class ImageDetailFragment extends BaseFragment{

	/**
	 * 照片的服务器地址
	 */
	private String mImageUrl;
	/**
	 * 照片显示器---imageView
	 */
	@ViewInject(R.id.image)
	private ImageView mImageView;
	/**
	 * 加载照片的ProgressBar
	 */
	@ViewInject(R.id.loading)
	private ProgressBar progressBar;
	/**
	 * 加载照片的容器
	 */
	private PhotoViewAttacher mAttacher;

	public static ImageDetailFragment newInstance(Photo imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();
		final Bundle bundle = new Bundle();
		bundle.putString("url", imageUrl.getUrl());
		f.setArguments(bundle);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
		ViewUtils.inject(this, view);
		//创建加载照片的容器
		mAttacher = new PhotoViewAttacher(mImageView);
		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				//点击照片时关闭activity
				getActivity().finish();
			}
		});
		return view;
	}

	DisplayImageOptions options;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

//		options = new DisplayImageOptions.Builder()
//		                     .resetViewBeforeLoading(false) // default
//		                     .delayBeforeLoading(1000) // default
//		                    .considerExifParams(false) // default
//		                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
//		                    .bitmapConfig(Bitmap.Config.RGB_565) // default
//		                    .displayer(new SimpleBitmapDisplayer()) // default
//		                     .build();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "下载错误";
					break;
				case DECODING_ERROR:
					message = "图片无法显示";
					break;
				case NETWORK_DENIED:
					message = "网络有问题，无法下载";
					break;
				case OUT_OF_MEMORY:
					message = "图片太大无法显示";
					break;
				case UNKNOWN:
					message = "未知的错误";
					break;
				}
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressBar.setVisibility(View.GONE);
				mAttacher.update();
			}
		});
	}

}
