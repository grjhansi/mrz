package com.nurture.nurture_user.mrz;

import android.graphics.Bitmap;
import android.os.Environment;

import com.microblink.detectors.DetectorResult;
import com.microblink.detectors.quad.QuadDetectorResult;
import com.microblink.image.Image;
import com.microblink.metadata.DetectionMetadata;
import com.microblink.metadata.ImageMetadata;
import com.microblink.metadata.Metadata;
import com.microblink.metadata.MetadataListener;
import com.microblink.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by nurture-user on 28-05-2018.
 */

public class myMetadataListener implements MetadataListener {

    @Override
    public void onMetadataAvailable(Metadata metadata) {
        // detection location will be available as DetectionMetadata
        if (metadata instanceof DetectionMetadata) {
            // DetectionMetadata contains DetectorResult which is null if object detection
            // has failed and non-null otherwise
            // Let's assume that we have a QuadViewManager which can display animated frame
            // around detected object (for reference, please check javadoc and demo apps)
            DetectorResult dr = ((DetectionMetadata) metadata).getDetectionResult();
            if (dr == null) {
                // animate frame to default location if detection has failed
                //mQuadViewManager.animateQuadToDefaultPosition();
            } else if (dr instanceof QuadDetectorResult) {
                // otherwise, animate frame to detected location
                //mQuadViewManager.animateQuadToDetectionPosition((QuadDetectorResult) dr);
            }
            // images will be available inside ImageMetadata
        } else if (metadata instanceof ImageMetadata) {
            // obtain image

            // Please note that Image's internal buffers are valid only
            // until this method ends. If you want to save image for later,
            // obtained a cloned image with image.clone().

            Image image = ((ImageMetadata) metadata).getImage().clone();
            // to convert the image to Bitmap, call image.convertToBitmap()
            Bitmap bitmap = image.convertToBitmap();

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/req_images");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-" + n + ".jpg";
            File file = new File(myDir, fname);
            Log.i("Tag", "" + file);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // after this line, image gets disposed. If you want to save it
            // for later, you need to clone it with image.clone()
        }
    }
}
