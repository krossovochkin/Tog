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

import com.krossovochkin.tog.TogRule;

public class CustomTogRule extends TogRule {

    private boolean mLogsEnabled;

    public CustomTogRule(boolean logsEnabled) {
        mLogsEnabled = logsEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof CustomTogRule) {
            CustomTogRule customTogRule = (CustomTogRule) o;
            return this.mLogsEnabled == customTogRule.isLogsEnabled();
        }
        return false;
    }

    public boolean isLogsEnabled() {
        return mLogsEnabled;
    }

    @Override
    public int hashCode() {
        return mLogsEnabled ? 1 : 0;
    }
}