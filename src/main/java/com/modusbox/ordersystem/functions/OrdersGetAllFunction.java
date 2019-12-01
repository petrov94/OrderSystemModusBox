package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrdersGetAllFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderResponse> {

	@Override
	public HttpOrderResponse handleRequest(HttpQuerystringRequest input, Context context) {
		context.getLogger().log("Input: " + input + "\n");
		HttpOrderResponse response = null;
		try {
			return new HttpOrderResponse(new DbManager(context.getLogger()).getAllOrders());
		} catch (Exception e) {
			response = new HttpOrderResponse();
			response.setBody(e.getLocalizedMessage());
			response.setStatusCode("400");
		}
		return response;
	}

}
