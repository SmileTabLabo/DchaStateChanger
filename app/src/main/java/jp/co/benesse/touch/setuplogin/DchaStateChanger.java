package jp.co.benesse.touch.setuplogin;

import android.app.Activity;
import android.os.Bundle;

import static android.os.BenesseExtension.*;

public class DchaStateChanger extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishAndRemoveTask();
        if (getDchaState() == 3) {
            setDchaState(0);
        } else {
            setDchaState(3);
        }
    }
}
