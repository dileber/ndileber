package com.drcosu.ndileber.tools.net.parser.form;

import com.drcosu.ndileber.tools.HJson;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import retrofit2.Converter;

public class FormRequestBodyConverter<T> implements Converter<T, RequestBody> {

    static final String TAG = FormRequestBodyConverter.class.getSimpleName();

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    @Override
    public RequestBody convert(final T value) throws IOException {
        String paramsJson = HJson.toJson(value);
        final Map<String, Object> paramsMap = HJson.toMaps(paramsJson,Object.class);
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Buffer buffer = sink.buffer();
                Set<String> keySet = paramsMap.keySet();
                int index = 0;
                for (String key : keySet) {
                    if (index++ > 0) {
                        buffer.writeByte('&');
                    }
                    String encodeKey = URLEncoder.encode(key, "UTF-8");
                    buffer.writeUtf8(encodeKey);
                    buffer.writeByte('=');
                    String encodeValue = URLEncoder.encode(((String) paramsMap.get(key)), "UTF-8");
                    buffer.writeUtf8(encodeValue);
                }
            }
        };
    }
}
