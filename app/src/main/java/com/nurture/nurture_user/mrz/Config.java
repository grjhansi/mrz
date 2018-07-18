package com.nurture.nurture_user.mrz;

import android.os.Parcelable;

import com.microblink.detectors.DecodingInfo;
import com.microblink.detectors.DetectorSettings;
import com.microblink.detectors.document.DocumentDetectorSettings;
import com.microblink.detectors.document.DocumentSpecification;
import com.microblink.detectors.document.DocumentSpecificationPreset;
import com.microblink.detectors.multi.MultiDetectorSettings;
import com.microblink.detectors.quad.mrtd.MRTDDetectorSettings;
import com.microblink.geometry.Rectangle;
import com.microblink.recognizers.blinkbarcode.usdl.USDLRecognizerSettings;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.detector.DetectorRecognizerSettings;
import com.microblink.recognizers.settings.RecognizerSettings;

/**
 * Created by nurture-user on 28-05-2018.
 */

public class Config {

    //this method is for detecting images only
    public static RecognizerSettings[] getRecognizerSettings() {
        //********* for detecting cards, not for scanning ********************/
        DocumentSpecification idSpec = DocumentSpecification.createFromPreset(
                DocumentSpecificationPreset.DOCUMENT_SPECIFICATION_PRESET_ID1_CARD);
        DocumentDetectorSettings dds = new DocumentDetectorSettings(new DocumentSpecification[] {idSpec});
        //Sets the number of subsequent close detections must occur before treating document detection as stable. Default is 1. Larger number guarantees more robust document detection at price of slower performance
        dds.setNumStableDetectionsThreshold(8);

        //for scanning only images we should provide dds in new DetectorSettings
        Parcelable[] settParc = new DetectorSettings[]{dds};
        if (settParc == null || settParc.length == 0) {
            throw new NullPointerException("EXTRAS_DETECTOR_SETTINGS not set."
                    + " Please set detector settings intent extra!");
        }
        DetectorSettings[] detSett = new DetectorSettings[settParc.length];
        for (int i = 0; i < settParc.length; i++) {
            detSett[i] = (DetectorSettings)settParc[i];
        }

        // Prepare detector recognizer settings, this recognizer is used to detect the
        // desired objects.
        DetectorRecognizerSettings drs = null;

        if (detSett.length == 1) {
            // if only one detector settings was sent via intent, use it directly
            drs = new DetectorRecognizerSettings(detSett[0]);
        } else {
            // Otherwise, prepare settings for multi detector that returns the first successful result from one of the
            // given detectors, here we use detector settings passed by intent.
            MultiDetectorSettings mds = new MultiDetectorSettings(detSett);

            drs = new DetectorRecognizerSettings(mds);
        }
        // now add sett to recognizer settings array that is used to configure
        //***************** if we want scan mrtd we should pass "sett, drs" both
        //****************** or if we want to detect only images we should pass "drs" only
        return new RecognizerSettings[]{drs};
    }

    //this method is for detecting images and scanning mrz code
    public static RecognizerSettings[] getMRTDAndRecognizerSettings() {
        MRTDRecognizerSettings sett = new MRTDRecognizerSettings();

        //********* for scanning mrtd i.e mrz code scanning ********************/
        DecodingInfo di = new DecodingInfo(new Rectangle(0.f, 0.f, 1.f, 1.f), 400, "MRTD");
        MRTDDetectorSettings mrtds = new MRTDDetectorSettings(new DecodingInfo[] {di});

        //for scanning mrtd i.e mrz we should provide mrtds in new DetectorSettings
        Parcelable[] settParc = new DetectorSettings[]{mrtds};
        if (settParc == null || settParc.length == 0) {
            throw new NullPointerException("EXTRAS_DETECTOR_SETTINGS not set."
                    + " Please set detector settings intent extra!");
        }
        DetectorSettings[] detSett = new DetectorSettings[settParc.length];
        for (int i = 0; i < settParc.length; i++) {
            detSett[i] = (DetectorSettings)settParc[i];
        }

        // Prepare detector recognizer settings, this recognizer is used to detect the
        // desired objects.
        DetectorRecognizerSettings drs = null;

        if (detSett.length == 1) {
            // if only one detector settings was sent via intent, use it directly
            drs = new DetectorRecognizerSettings(detSett[0]);
        } else {
            // Otherwise, prepare settings for multi detector that returns the first successful result from one of the
            // given detectors, here we use detector settings passed by intent.
            MultiDetectorSettings mds = new MultiDetectorSettings(detSett);

            drs = new DetectorRecognizerSettings(mds);
        }

        // now add sett to recognizer settings array that is used to configure
        //***************** if we want scan mrtd we should pass "sett, drs" both
        //****************** or if we want to detect only images we should pass "drs" only
        return new RecognizerSettings[]{sett, drs};
    }
}
