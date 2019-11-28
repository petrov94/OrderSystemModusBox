package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderFindByCustomerFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderRespnse> {

    @Override
    public HttpOrderRespnse handleRequest(HttpQuerystringRequest request, Context context) {
        context.getLogger().log("Input: " + request+ "\n");
        String customer = request.getQueryStringParameters().get("customer");
        return new HttpOrderRespnse(new DbManager(context.getLogger()).getAllOrdersByCustomer(customer));
    }

}
