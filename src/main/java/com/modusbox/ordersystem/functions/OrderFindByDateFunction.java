package com.modusbox.ordersystem.functions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderFindByDateFunction implements RequestHandler<HttpQuerystringRequest, HttpOrderResponse> {

	@Override
	public HttpOrderResponse handleRequest(HttpQuerystringRequest request, Context context) {
		HttpOrderResponse response = null;
		try {
			if(request.getQueryStringParameters().get("date")==null) throw new Exception("The placement date is mandatory.");
			context.getLogger().log("Execute find function." + request + "\n");
			String date = request.getQueryStringParameters().get("date");
			response = new HttpOrderResponse(new DbManager(context.getLogger()).getAllOrdersByDate(date));
		} catch (Exception e) {
			response = new HttpOrderResponse();
			response.setBody(e.getLocalizedMessage());
			response.setStatusCode("400");
		}
		return response;
	}

}
