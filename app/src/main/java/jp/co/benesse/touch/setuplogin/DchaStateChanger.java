package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.os.Bundle;
import static android.os.BenesseExtension.*;

public class DchaStateChanger extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishAndRemoveTask();
        // DchaState が 3 の時に 0 に設定する
        if (getDchaState() == 3) {
            setDchaState(0);
        } else {
            // 3以外の時は 3 に設定する
            setDchaState(3);
        }
    }
}
