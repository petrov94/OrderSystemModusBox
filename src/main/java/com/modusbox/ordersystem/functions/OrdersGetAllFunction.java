package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrdersGetAllFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderRespnse> {

    @Override
    public HttpOrderRespnse handleRequest(HttpQuerystringRequest input, Context context) {
        context.getLogger().log("Input: " + input + "\n");
        return new HttpOrderRespnse(new DbManager(context.getLogger()).getAllOrders());  
    }

}
