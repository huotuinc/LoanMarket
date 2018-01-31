package com.huotu.loanmarket.service.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * @author luyuanyuan on 2017/11/22.
 */
public class JsonResponseHandler implements ResponseHandler<JsonObject> {

    @Override
    public JsonObject handleResponse(HttpResponse response) throws IOException {
        String jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}