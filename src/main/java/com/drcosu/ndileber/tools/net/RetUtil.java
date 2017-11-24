package com.drcosu.ndileber.tools.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by WaTaNaBe on 2017/11/24.
 */

public class RetUtil {

//    /**
//     * Multipart 生成 需要传值的内容需要变为unicode
//     * @param dataSign
//     * @return
//     */
//    public static MultipartBody getMultipartParamsMap(Map<String, Object> params, String dataSign) {
//
//        StringBuilder paramsBuilder = new StringBuilder();
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        Map<String, Object> sortedMap = sort(params);
//
//        for(Map.Entry<String, Object> entry:sortedMap.entrySet()){
//            if(entry.getValue() instanceof String){
//                builder.addFormDataPart("data["+entry.getKey()+"]", (String) entry.getValue());
//                paramsBuilder.append("&");
//                paramsBuilder.append("data["+entry.getKey()+"]="+(String) entry.getValue());
//            }else if (entry.getValue() instanceof File){
//                File file = (File) entry.getValue();
//                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
//                builder.addFormDataPart(entry.getKey(), file.getName(), requestBody);
//
//                builder.addFormDataPart("data["+entry.getKey()+"]",file.getName());
//                paramsBuilder.append("&");
//                paramsBuilder.append("data["+entry.getKey()+"]="+file.getName());
//                sortedMap.put(entry.getKey(),file.getName());
//            }else if (entry.getValue() instanceof String[]){
//                String[] strings = (String[]) entry.getValue();
//                List<Object> temp = new ArrayList<>();
//                for(int i=0;i<strings.length;i++){
//                    String str = strings[i];
//                    RequestBody requestFile =
//                            RequestBody.create(MediaType.parse("utf-8"), str);
//                    builder.addFormDataPart("data["+entry.getKey()+"]["+i+"]",null, requestFile);
////                    builder.addFormDataPart("data["+entry.getKey()+"]["+i+"]", str);
//                    paramsBuilder.append("&");
//                    paramsBuilder.append("data["+entry.getKey()+"]["+i+"]="+ str);
//                    temp.add(str);
//                }
//                sortedMap.put(entry.getKey(),temp);
//            }else if (entry.getValue() instanceof List){
//                List strings = (List) entry.getValue();
//                for(int i=0;i<strings.size();i++){
//                    builder.addFormDataPart("data["+entry.getKey()+"]["+i+"]", strings.get(i).toString());
//                    paramsBuilder.append("&");
//                    paramsBuilder.append("data["+entry.getKey()+"]["+i+"]="+ strings.get(i).toString());
//                }
//            }else{
//                builder.addFormDataPart("data["+entry.getKey()+"]", entry.getValue().toString());
//                paramsBuilder.append("&");
//                paramsBuilder.append("data["+entry.getKey()+"]="+entry.getValue().toString());
//            }
//        }
//        String paramsJson  = JsonParser.toJson(sortedMap);
//
//        //String tempparamsJson = "{\"content\":\"作业内容\",\"deadline\":\"2017-11-18 12:00:00\",\"employee_id\":\"0197c4994dace843e569f8b3e04b9049\",\"file\":[\"http:\\/\\/file.baonahao.com.cn\\/image\\/tpjx\\/20171114\\/abfeb5032b9360e209b0a1955ea07bfd.png\",\"http:\\/\\/file.baonahao.com.cn\\/image\\/tpjx\\/20171114\\/d36c93688ff3bfa0b0d47938e443f264.png\"],\"goods_id\":\"01630b58536e4a0a94b8fb255c8ed078\",\"lesson_id\":\"0a403fdc613f4e429c4a4d37fb58e020\",\"merchant_id\":\"a7e431fbeb2b805b38180cd2ca8d1d27\",\"platform_id\":\"cb48836df7d1a60b2bc7e06ea8072245\",\"version\":\"2.0.1\"}";
//
////        if(tempparamsJson.equals(paramsJson)){
////            ZLogger.logger.d("Retrofit", "%%s 等于 %s", paramsJson,tempparamsJson);
////        }else{
////            ZLogger.logger.d("Retrofit", "%s 不等于 %s", paramsJson,tempparamsJson);
////        }
//
//        JsonLog.printJson("Retrofit",paramsJson,"编码后的JSON");
//        ZLogger.logger.d("Retrofit", "%s", paramsJson);
//
//        String sign = "";
//        if(null == dataSign){
//            sign = SignJobber.sign(paramsJson + Api.SECURITY_CODE);
//        }else{
//            sign = SignJobber.sign(paramsJson + dataSign);
//        }
//
//
//        builder.addFormDataPart("keys[data_sign]", sign);
//        paramsBuilder.append("&");
//        paramsBuilder.append("keys[data_sign]="+sign);
//
//        builder.addFormDataPart("keys[data_type]", Api.DATA_TYPE);
//        paramsBuilder.append("&");
//        paramsBuilder.append("keys[data_type]="+Api.DATA_TYPE);
//
//        builder.addFormDataPart("keys[packey]", Api.PAC_KEY);
//        paramsBuilder.append("&");
//        paramsBuilder.append("keys[packey]="+Api.PAC_KEY);
//
//        builder.addFormDataPart("keys[security_code]", Api.SECURITY_CODE);
//        paramsBuilder.append("&");
//        paramsBuilder.append("keys[security_code]="+Api.SECURITY_CODE);
//
//        builder.addFormDataPart("keys[token_key]", BaoNaHaoSchool.getTokenKey());
//        paramsBuilder.append("&");
//        paramsBuilder.append("keys[token_key]="+BaoNaHaoSchool.getTokenKey());
//
//        builder.addFormDataPart("keys[token_val]", BaoNaHaoSchool.getTokenValue());
//        paramsBuilder.append("&");
//        paramsBuilder.append("keys[token_val]="+ BaoNaHaoSchool.getTokenValue());
//
//        String logStr = paramsBuilder.toString();
//        logStr = logStr.substring(1,logStr.length());
//
//        ZLogger.logger.d("Retrofit", "%s", logStr);
//        SocketLog.log(logStr);
//
//        builder.setType(MultipartBody.FORM);
//
//        return builder.build();
//    }


}
