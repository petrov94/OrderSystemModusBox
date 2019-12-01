package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderFindByIdFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderResponse> {

    @Override
    public HttpOrderResponse handleRequest(HttpQuerystringRequest request, Context context) {
    	HttpOrderResponse response = null;
    	try {
    	context.getLogger().log("Input: " + request + "\n");
    	if(request.getQueryStringParameters().get("id")== null)  throw new Exception("The order id is mandatory.");
    	String id = request.getQueryStringParameters().get("id");
    	response = new HttpOrderResponse(new DbManager(context.getLogger()).getAllOrdersById(id)); 
    	}catch(Exception e) {
    		response = new HttpOrderResponse();
    		response.setBody(e.getLocalizedMessage());
    		response.setStatusCode("400");
    	}
		return response;
    }

}
