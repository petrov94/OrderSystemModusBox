package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderFindByCustomerFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderResponse> {

	@Override
	public HttpOrderResponse handleRequest(HttpQuerystringRequest request, Context context) {
		HttpOrderResponse response = null;
		try {
			context.getLogger().log("Input: " + request + "\n");
			if(request.getQueryStringParameters().get("customer")== null)  throw new Exception("The customer name is mandatory.");
			String customer = request.getQueryStringParameters().get("customer");
			response = new HttpOrderResponse(new DbManager(context.getLogger()).getAllOrdersByCustomer(customer));
		} catch (Exception e) {
			response = new HttpOrderResponse();
			response.setBody(e.getLocalizedMessage());
			response.setStatusCode("400");
		}
		return response;
	}
}
