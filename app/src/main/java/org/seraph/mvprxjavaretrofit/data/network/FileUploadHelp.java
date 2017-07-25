package org.seraph.mvprxjavaretrofit.data.network;

import org.seraph.mvprxjavaretrofit.utlis.Tools;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 文件上传帮助类
 * date：2017/7/25 17:52
 * author：xiongj
 * mail：417753393@qq.com
 **/
public class FileUploadHelp {

    /**
     * 多文件表单上传RequestBody(文件统一接收key)
     *
     * @param params  表单
     * @param files   文件list列表
     * @param fileKey 服务器接收文件key
     */
    public static RequestBody multipartRequestBody(Map<String, String> params, List<File> files, String fileKey) {
        MultipartBody.Builder requestBodyBuilder = initParamsBuilder(params);
        if (files != null) {
            for (File file : files) {
                requestBodyBuilder.addFormDataPart(Tools.isNull(fileKey) ? "file" : fileKey, file.getName(), RequestBody.create(MultipartBody.FORM, file));
            }
        }
        return requestBodyBuilder.build();
    }

    /**
     * 多文件表单上传RequestBody
     *
     * @param params 表单
     * @param files  文件map列表
     */
    public static RequestBody multipartRequestBody(Map<String, String> params, Map<String, File> files) {
        MultipartBody.Builder requestBodyBuilder = initParamsBuilder(params);
        if (files != null) {
            Set<String> fileKeys = files.keySet();
            for (String key : fileKeys) {
                File tempFile = files.get(key);
                requestBodyBuilder.addFormDataPart(key, tempFile.getName(), RequestBody.create(MultipartBody.FORM, tempFile));
            }
        }
        return requestBodyBuilder.build();
    }


    /**
     * 初始化和生成公共构建部分
     * @param params 表单
     */
    private static MultipartBody.Builder initParamsBuilder(Map<String, String> params){
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null) {
            Set<String> paramsKeys = params.keySet();
            for (String key : paramsKeys) {
                requestBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        return requestBodyBuilder;
    }

}
