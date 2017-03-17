package com.drcosu.ndileber.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by jing on 2016/8/25.
 */
public class UImage {

    public static void showThumb(Uri uri, SimpleDraweeView draweeView,int w,int h){
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setAutoRotateEnabled(true)
                .setResizeOptions(new ResizeOptions(w, h))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }

    public static void loadDrawable(SimpleDraweeView draweeView, int resId) {
        Uri uri = new Uri.Builder().scheme(UriUtil.LOCAL_RESOURCE_SCHEME).path(String.valueOf(resId)).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri).setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }


    public static void loadFile(final SimpleDraweeView draweeView, String filePath, final int reqWidth, final int reqHeight) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(filePath)
                .build();
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).
                setAutoRotateEnabled(true)
                .setLocalThumbnailPreviewsEnabled(true)
                .setResizeOptions(new ResizeOptions(reqWidth, reqHeight))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                        if (imageInfo == null) {
                            return;
                        }
                        ViewGroup.LayoutParams vp = draweeView.getLayoutParams();
                        vp.width = reqWidth;
                        vp.height = reqHeight;
                        draweeView.requestLayout();
                    }
                }).build();
        draweeView.setController(controller);
    }

    /**
     * 获取图片
     * @param context
     * @param uri
     * @param baseBitmapDataSubscriber
     */
    public void getBitmap(Context context, Uri uri,BaseBitmapDataSubscriber baseBitmapDataSubscriber){
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest,context);

        dataSource.subscribe(baseBitmapDataSubscriber,CallerThreadExecutor.getInstance());
    }
}

