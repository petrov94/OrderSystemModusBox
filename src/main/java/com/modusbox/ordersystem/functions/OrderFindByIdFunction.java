package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderFindByIdFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderRespnse> {

    @Override
    public HttpOrderRespnse handleRequest(HttpQuerystringRequest request, Context context) {
    	context.getLogger().log("Input: " + request + "\n");
    	String id = request.getQueryStringParameters().get("id");
        return new HttpOrderRespnse(new DbManager(context.getLogger()).getAllOrdersById(id));  
    }

}
