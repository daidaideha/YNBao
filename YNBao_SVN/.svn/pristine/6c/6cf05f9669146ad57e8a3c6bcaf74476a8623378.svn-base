package com.innouni.yinongbao.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IntentToOther {
	
	public IntentToOther(Activity activity,Class<?> cls, Bundle bundle){
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		if(bundle != null)
			intent.putExtras(bundle);
		activity.startActivity(intent);
	}

}
