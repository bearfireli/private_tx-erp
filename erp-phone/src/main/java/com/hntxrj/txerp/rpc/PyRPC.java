package com.hntxrj.txerp.rpc;

import com.hntxrj.txerp.core.exception.ErpException;
import okhttp3.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * 远程调用python服务
 */
public class PyRPC {

    static OkHttpClient client = new OkHttpClient();

    // TODO: 地址写到配置文件中
    static String rpcUrl = "http://localhost:5000";

    public static Object httpCall(HttpServletRequest request) throws RPCException {
        String url = rpcUrl + request.getRequestURI();
        FormBody.Builder requestBodyBuilder = new FormBody.Builder();
        request.getParameterMap().forEach((k, v) -> requestBodyBuilder.add(k, Arrays.toString(v)));
        Request req = new Request.Builder()
                .url(url)
                .method(request.getMethod(), requestBodyBuilder.build())
                .build();
        try (Response resp = client.newCall(req).execute()) {
            if (resp.code() == 404) {
                throw RPCException.NOT_FOUNT_404;
            }
            return resp.body();
        } catch (IOException e) {
            e.printStackTrace();
            throw RPCException.CALL_ERROR;
        }
    }

}
