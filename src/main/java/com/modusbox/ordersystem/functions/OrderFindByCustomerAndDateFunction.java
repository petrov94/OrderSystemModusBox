package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderFindByCustomerAndDateFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderRespnse> {

    @Override
    public HttpOrderRespnse handleRequest(HttpQuerystringRequest request, Context context) {
    	 context.getLogger().log("Input: " + request+ "\n");
         String customer = request.getQueryStringParameters().get("customer");
         String placementDate = request.getQueryStringParameters().get("date");
         return new HttpOrderRespnse(new DbManager(context.getLogger()).getAllOrdersByCustomerAndDate(customer, placementDate));
    }

}
