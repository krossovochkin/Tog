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
package com.krossovochkin.tog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vasya Drobushkov on 05.01.2015.
 *
 * Main class, that manages log calling.
 */
public class Tog {
    private static TogRule mCurrentTogRule;
    private static TogStyle mCurrentTogStyle;
    private static Map<TogRule, TogStyle> mMap = new HashMap<>();

    /**
     * Triggers currently inited {@link com.krossovochkin.tog.TogRule}. If no current {@link com.krossovochkin.tog.TogRule} inited, nothing will happen.
     * @param priority log priority level from {@link android.util.Log} constants, {@link android.util.Log#ERROR} for example
     * @param tag log tag string
     * @param message log message string
     * @param throwable log throwable
     */
    public static void log(int priority, String tag, String message, Throwable throwable) {
        if (mCurrentTogStyle == null) {
            mCurrentTogStyle = getCurrentTogStyle();
        }
        if (mCurrentTogStyle != null && mCurrentTogStyle.isEnabled()) {
            mCurrentTogStyle.getListener().onTog(priority, tag, message, throwable);
        }
    }

    /**
     * Triggers currently inited {@link com.krossovochkin.tog.TogRule}. If no current {@link com.krossovochkin.tog.TogRule} inited, nothing will happen.
     * @param priority log priority level from {@link android.util.Log} constants, {@link android.util.Log#ERROR} for example
     * @param tag log tag string
     * @param message log message string
     */
    public static void log(int priority, String tag, String message) {
        log(priority, tag, message, null);
    }

    /**
     * Init with {@link com.krossovochkin.tog.TogRule}
     * @param togRule tog rule to init
     */
    public static void init(TogRule togRule) {
        mCurrentTogRule = togRule;
    }

    /**
     * Adds {@link com.krossovochkin.tog.TogStyle} for {@link com.krossovochkin.tog.TogRule}
     * @param togRule
     * @param togStyle
     */
    public static void addTogStyle(TogRule togRule, TogStyle togStyle) {
        mMap.put(togRule, togStyle);
    }

    private static TogStyle getCurrentTogStyle() {
        return mMap.get(mCurrentTogRule);
    }
}