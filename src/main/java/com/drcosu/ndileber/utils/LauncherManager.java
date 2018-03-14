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

import com.drcosu.ndileber.tools.log.ULog;

/**
 * Created by WaTaNaBe 2017/8/19.
 */

public class LauncherManager {

    private LauncherManager() {

    }

    private final static class DefaultLauncher implements Launcher {
        @Override
        public void launch(Context launcher, Class<? extends Activity> actClass) {
            launch(launcher, new Intent(launcher, actClass));
        }

        @Override
        public void launch(Context launcher, Class<? extends Activity> actClass, Bundle bundle) {
            Intent intent = new Intent(launcher, actClass);
            if(bundle!=null) {
                intent.putExtras(bundle);
            }
            launch(launcher, intent);
        }

        @Override
        public void launchForResult(Activity launcher, Class<? extends Activity> actClass, int requestCode) {
            launchForResult(launcher, new Intent(launcher, actClass), requestCode);
        }

        @Override
        public void launchThenExit(Activity launcher, Class<? extends Activity> actClass) {
            launchExit(launcher, new Intent(launcher, actClass));
        }

        @Override
        public void launch(Context launcher, Intent intent) {
            launcher.startActivity(intent);
        }

        @Override
        public void launchForResult(Activity launcher, Intent intent, int resultCode) {
            launcher.startActivityForResult(intent, resultCode);
        }

        @Override
        public void launchExit(Activity launcher, Intent intent) {
            launcher.startActivity(intent);
            launcher.finish();
        }

    }

    public static Launcher launcher = new DefaultLauncher();

    public static void setLauncher(Launcher launcher) {
        LauncherManager.launcher = launcher;
    }
}
