package com.alium.nibo.autocompletesearchbar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.speech.RecognizerIntent;

import androidx.annotation.RequiresApi;

import com.alium.nibo.R;

import java.util.List;

public class NiboDefaultVoiceRecognizerDelegate extends VoiceRecognitionDelegate {

    public NiboDefaultVoiceRecognizerDelegate(Activity activity) {
        super(activity);
    }

    public NiboDefaultVoiceRecognizerDelegate(Activity activity, int activityRequestCode) {
        super(activity, activityRequestCode);
    }

    public NiboDefaultVoiceRecognizerDelegate(Fragment fragment) {
        super(fragment);
    }

    public NiboDefaultVoiceRecognizerDelegate(Fragment fragment, int activityRequestCode) {
        super(fragment, activityRequestCode);
    }

    public NiboDefaultVoiceRecognizerDelegate(androidx.fragment.app.Fragment supportFragment) {
        super(supportFragment);
    }

    public NiboDefaultVoiceRecognizerDelegate(androidx.fragment.app.Fragment supportFragment, int activityRequestCode) {
        super(supportFragment, activityRequestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Intent buildVoiceRecognitionIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getContext().getString(R.string.speak_now));
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean isVoiceRecognitionAvailable() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        PackageManager mgr = getContext().getPackageManager();
        if (mgr != null) {
            List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        }
        return false;
    }
}
