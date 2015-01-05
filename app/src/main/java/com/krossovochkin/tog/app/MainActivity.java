/*
* Copyright 2015 Vasya Drobushkov
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.krossovochkin.tog.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.krossovochkin.tog.DefaultTogRule;
import com.krossovochkin.tog.Tog;
import com.krossovochkin.tog.TogListener;
import com.krossovochkin.tog.TogStyle;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCustomFlavorTog();
        Tog.log(Log.WARN, MainActivity.class.getSimpleName(), "Hello world");
    }

    private void initTog() {
        Tog.init(new DefaultTogRule(BuildConfig.DEBUG));
        Tog.addTogStyle(new DefaultTogRule(true), new TogStyle(new TogListener() {
            @Override
            public void onTog(int priority, String tag, String message, Throwable throwable) {
                Log.println(priority, tag, message + "\n" + Log.getStackTraceString(throwable));
            }
        }));
        Tog.addTogStyle(new DefaultTogRule(false), new TogStyle());
    }

    private void initCustomTog() {
        Tog.init(new CustomTogRule(BuildConfig.LOGS_ENABLED));
        Tog.addTogStyle(new CustomTogRule(true), new TogStyle(new TogListener() {
            @Override
            public void onTog(int priority, String tag, String message, Throwable throwable) {
                Log.println(priority, tag, message + "\n" + Log.getStackTraceString(throwable));
            }
        }));
        Tog.addTogStyle(new CustomTogRule(false), new TogStyle());
    }

    private void initCustomFlavorTog() {
        Tog.init(new CustomFlavorTogRule(BuildConfig.FLAVOR));
        Tog.addTogStyle(new CustomFlavorTogRule("flavor1"), new TogStyle(new TogListener() {
            @Override
            public void onTog(int priority, String tag, String message, Throwable throwable) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
