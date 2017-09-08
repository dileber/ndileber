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

package com.drcosu.ndileber.tools;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Size;
import android.util.SizeF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BundleBuilder {

    private final Map<String, Object> extras = new HashMap<>();
    private final List<Bundle> bundleExtras = new ArrayList<>();

    public BundleBuilder() {
    }

    public interface ValueCreator {

        @NonNull
        String getKey();

        @NonNull
        Object getValue();
    }

    public BundleBuilder(@NonNull BundleBuilder builder) {
        addBundle(builder.build());
    }

    public BundleBuilder(@NonNull String key, @NonNull Object value) {
        addObject(key, value);
    }

    public BundleBuilder(@NonNull ValueCreator creator) {
        addValueCreator(creator);
    }

    public BundleBuilder addString(@NonNull String key, @NonNull String value) {
        return addObject(key, value);
    }

    public BundleBuilder addInteger(@NonNull String key, @NonNull Integer value) {
        return addObject(key, value);
    }

    public BundleBuilder addParcelable(@NonNull String key, @NonNull Parcelable value) {
        return addObject(key, value);
    }

    public BundleBuilder addDouble(@NonNull String key, @NonNull Double value) {
        return addObject(key, value);
    }

    public BundleBuilder addFloat(@NonNull String key, @NonNull Float value) {
        return addObject(key, value);
    }

    public BundleBuilder addLong(@NonNull String key, @NonNull Long value) {
        return addObject(key, value);
    }

    public BundleBuilder addByte(@NonNull String key, @NonNull Byte value) {
        return addObject(key, value);
    }

    public BundleBuilder addChar(@NonNull String key, @NonNull Character value) {
        return addObject(key, value);
    }

    public BundleBuilder addAll(@NonNull String key, @NonNull Bundle value) {
        return addBundle(value);
    }

    public BundleBuilder addBuilder(@NonNull BundleBuilder value) {
        return addBundle(value.build());
    }

    public BundleBuilder addSerializable(@NonNull String key, @NonNull Serializable value) {
        return addObject(key, value);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public BundleBuilder addBinder(@NonNull String key, @NonNull IBinder value) {
        return addObject(key, value);
    }

    public BundleBuilder addBoolean(@NonNull String key, @NonNull Boolean value) {
        return addObject(key, value);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BundleBuilder addSize(@NonNull String key, @NonNull Size value) {
        return addObject(key, value);
    }

    public BundleBuilder addSizeF(@NonNull String key, @NonNull SizeF value) {
        return addObject(key, value);
    }

    public BundleBuilder addCharSequence(@NonNull String key, @NonNull CharSequence value) {
        return addObject(key, value);
    }

    public BundleBuilder addShort(@NonNull String key, @NonNull Short value) {
        return addObject(key, value);
    }

    public BundleBuilder addValueCreator(@NonNull ValueCreator creator) {
        if (creator == null) {
            return this;
        }
        return addObject(creator.getKey(), creator.getValue());
    }

    private BundleBuilder addObject(@NonNull String key, @NonNull Object value) {
        if (value == null || key == null) {
            return this;
        }
        extras.put(key, value);
        return this;
    }

    private BundleBuilder addBundle(@NonNull Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return this;
        }
        bundleExtras.add(bundle);
        return this;
    }

    public Object get(@NonNull String key) {
        if (key == null) {
            return null;
        }
        if (extras.containsKey(key)) {
            return extras.get(key);
        }
        for (Bundle bundleExtra : bundleExtras) {
            if (bundleExtra.containsKey(key)) {
                return bundleExtra.get(key);
            }
        }
        return null;
    }

    public boolean contains(@NonNull String key) {
        if (key == null) {
            return false;
        }
        if (extras.containsKey(key)) {
            return true;
        }
        for (Bundle bundleExtra : bundleExtras) {
            if (bundleExtra.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        boolean isEmptyResult = extras.isEmpty();
        for (Bundle bundleExtra : bundleExtras) {
            isEmptyResult &= bundleExtra.isEmpty();
        }
        return isEmptyResult;
    }


    public Bundle build() {
        Bundle bundle = new Bundle();
        Set<String> keySet = extras.keySet();
        for (String s : keySet) {
            Object value = extras.get(s);
            if (value instanceof String) {
                bundle.putString(s, ((String) value));
            } else if (value instanceof Integer) {
                bundle.putInt(s, ((Integer) value).intValue());
            } else if (value instanceof Parcelable) {
                bundle.putParcelable(s, ((Parcelable) value));
            } else if (value instanceof Double) {
                bundle.putDouble(s, ((Double) value).doubleValue());
            } else if (value instanceof Float) {
                bundle.putFloat(s, ((Float) value).floatValue());
            } else if (value instanceof Long) {
                bundle.putLong(s, ((Long) value).longValue());
            } else if (value instanceof Byte) {
                bundle.putByte(s, ((Byte) value).byteValue());
            } else if (value instanceof Character) {
                bundle.putChar(s, ((Character) value).charValue());
            } else if (value instanceof Bundle) {
                bundle.putBundle(s, ((Bundle) value));
            } else if (value instanceof Serializable) {
                bundle.putSerializable(s, ((Serializable) value));
            } else if (value instanceof IBinder) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    bundle.putBinder(s, ((IBinder) value));
                } else {
                    throw new RuntimeException(String.format("Unsupported bundle type below %s android version.", Build.VERSION_CODES.JELLY_BEAN_MR2));
                }
            } else if (value instanceof Boolean) {
                bundle.putBoolean(s, ((Boolean) value).booleanValue());
            } else if (value instanceof Size) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bundle.putSize(s, ((Size) value));
                } else {
                    throw new RuntimeException(String.format("Unsupported bundle type below %s android version.", Build.VERSION_CODES.LOLLIPOP));
                }
            } else if (value instanceof CharSequence) {
                bundle.putCharSequence(s, ((CharSequence) value));
            } else if (value instanceof Short) {
                bundle.putShort(s, ((Short) value).shortValue());
            } else if (value instanceof SizeF) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bundle.putSizeF(s, ((SizeF) value));
                } else {
                    throw new RuntimeException(String.format("Unsupported bundle type below %s android version.", Build.VERSION_CODES.LOLLIPOP));
                }
            } else {
                throw new RuntimeException(String.format("Unsupported bundle type: %s", value.getClass().getName()));
            }
        }
        for (Bundle value : bundleExtras) {
            bundle.putAll(value);
        }
        return bundle;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("BundleBuild ");
        builder.append("extras=")
                .append("[")
                .append(extras.toString())
                .append("]");
        builder.append("bundleExtras=")
                .append("[")
                .append(bundleExtras.toString())
                .append("]");
        return builder.toString();
    }

    /**
     * Clear all value set.
     */
    public BundleBuilder clear() {
        extras.clear();
        bundleExtras.clear();
        return this;
    }

    /**
     * Wrapper bundle generated by {@link #build()} into {@link Intent}
     *
     * @param intent
     */
    public void wrapperTo(Intent intent) {
        if (intent == null) {
            throw new NullPointerException("Can't wrapper bundle object to null Intent object.");
        }
        intent.putExtras(build());
    }

}
