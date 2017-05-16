package com.lyf.bookreader.view.refreshload;

import android.content.Context;

import com.lyf.bookreader.view.refreshload.deserializers.KFImageDeserializer;
import com.lyf.bookreader.view.refreshload.model.KFImage;

import java.io.IOException;
import java.io.InputStream;

public class Tool {

    public static KeyframesDrawable get(Context context, String str) {
        InputStream stream = null;
        try {
            // stream =loading_animation_from_bottom_white
            // getResources().getAssets().open("loading_animation_from_bottom_gray");
            stream = context.getResources().getAssets().open("loading_animation_from_bottom.txt");
            // stream = getResources().getAssets().open("sample_file");
            KFImage sampleImage = KFImageDeserializer.deserialize(stream);

            KeyframesDrawable mKeyFramesDrawable = new KeyframesDrawableBuilder().withImage(sampleImage)
                    .withMaxFrameRate(60).withExperimentalFeatures().withBitmaps().build();

            return mKeyFramesDrawable;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }
}
