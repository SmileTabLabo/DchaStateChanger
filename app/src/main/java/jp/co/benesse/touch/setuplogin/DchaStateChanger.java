package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.os.BenesseExtension;
import android.os.Bundle;

public class DchaStateChanger extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishAndRemoveTask();
        // DchaState が 3 の時に 0 に設定する
        if (BenesseExtension.getDchaState() == 3) {
            BenesseExtension.setDchaState(0);
        } else {
            // 0(3以外) の時は 3 に設定する
            BenesseExtension.setDchaState(3);
        }
    }
}
