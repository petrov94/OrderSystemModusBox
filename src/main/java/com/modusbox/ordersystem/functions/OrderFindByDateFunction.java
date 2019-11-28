package com.modusbox.ordersystem.functions;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderFindByDateFunction  implements RequestHandler<HttpQuerystringRequest, HttpOrderRespnse> {

	@Override
    public HttpOrderRespnse handleRequest(HttpQuerystringRequest request, Context context) {
        context.getLogger().log("Execute find function." + request + "\n");
        String date = request.getQueryStringParameters().get("date");
		return new HttpOrderRespnse(new DbManager(context.getLogger()).getAllOrdersByDate(date));  
    }

}
