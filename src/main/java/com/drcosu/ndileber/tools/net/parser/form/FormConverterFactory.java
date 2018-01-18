package com.drcosu.ndileber.tools.net.parser.form;

import com.drcosu.ndileber.mvp.data.model.SModel;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by congtaowang on 16/6/22.
 */
public final class FormConverterFactory extends Converter.Factory {

    public static FormConverterFactory create() {
        return new FormConverterFactory();
    }
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (!(type instanceof Class<?>)) {
            return null;
        }
        Class<?> c = (Class<?>) type;
        if (!SModel.class.isAssignableFrom(c)) {
            return null;
        }
        return new FormRequestBodyConverter<>();
    }

}
