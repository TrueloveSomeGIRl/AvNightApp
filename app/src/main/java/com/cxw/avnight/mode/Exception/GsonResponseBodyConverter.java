//package com.cxw.avnight.mode.Exception;
//
//import com.cxw.avnight.mode.bean.AvNightResponse;
//import com.google.gson.Gson;
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//
//import okhttp3.MediaType;
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//
//public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
//    private static final Charset UTF_8 = Charset.forName("UTF-8");
//    private final Gson mGson;
//    private final TypeAdapter<T> adapter;
//
//    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
//        mGson = gson;
//        this.adapter = adapter;
//    }
//
//    @Override
//    public T convert(ResponseBody value) throws IOException {
//        String response = value.string();
//        AvNightResponse re = mGson.fromJson(response, AvNightResponse.class);
//        //关注的重点，自定义响应码中非200的情况，一律抛出ApiException异常。
//        //这样，我们就成功的将该异常交给onError()去处理了。
//        if (re.getErrCode()!=200) {
//            value.close();
//            throw new ApiException(re.getErrCode(), re.getErrMsg());
//        }
//
//        MediaType mediaType = value.contentType();
//        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
//        ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes());
//        InputStreamReader reader = new InputStreamReader(bis,charset);
//        JsonReader jsonReader = mGson.newJsonReader(reader);
//        try {
//            return adapter.read(jsonReader);
//        } finally {
//            value.close();
//        }
//    }
//}