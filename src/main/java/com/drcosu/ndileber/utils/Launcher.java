/*
 * Copyright (c) 2016 congtaowang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.drcosu.ndileber.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 启动页面控制类
 * Created by WaTaNaBe 2017/8/19.
 */

public interface Launcher {

    void launch(Context launcher, Class<? extends Activity> actClass);

    void launch(Context launcher, Class<? extends Activity> actClass, Bundle bundle);

    void launchForResult(Activity launcher, Class<? extends Activity> actClass, int requestCode);

    void launchThenExit(Activity launcher, Class<? extends Activity> actClass);

    void launch(Context launcher, Intent intent);

    void launchForResult(Activity launcher, Intent intent, int resultCode);

    void launchExit(Activity launcher, Intent intent);

}
