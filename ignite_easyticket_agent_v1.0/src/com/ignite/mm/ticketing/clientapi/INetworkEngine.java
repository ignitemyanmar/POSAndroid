package com.ignite.mm.ticketing.clientapi;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

import com.ignite.mm.ticketing.application.LoginUser;
import com.ignite.mm.ticketing.sqlite.database.model.AccessToken;
import com.ignite.mm.ticketing.sqlite.database.model.Operator;
import com.ignite.mm.ticketing.sqlite.database.model.Operators;

public interface INetworkEngine {
	
	@GET("/seatplan")
	void getItems(@Query("param") String param, Callback<Response> callback);

	@GET("/city")
	void getAllCity(@Query("access_token") String access_token, Callback<Response> callback);
	
	@GET("/trips")
	void getTrips(@Query("param") String param, Callback<Response> callback);
	
	@GET("/time")
	void getAllTime(@Query("param") String param, Callback<Response> callback);
			 
	@GET("/api/operatorlist")
	void getAllOperator(@Query("access_token")String access_token, Callback<List<Operator>> callback);
	
	@FormUrlEncoded
	@POST("/sale/{id}/delete")
	void deleteSaleOrder( @Field("param")String param, @Path("id") String id,Callback<Response> callback);
	
	@GET("/agent")
	void getAllAgent(@Query("param")String param, Callback<Response> callback);
	
	@GET("/sale/order")
	void getBookingOrder(@Query("param") String param, Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/sale/credit/delete/{id}")
	void deleteAllOrder(@Field("param")String param, @Path("id") String id,Callback<Response> callback);

	@FormUrlEncoded
	@POST("/sale/credit/cancelticket")
	void deleteOrderItem(@Field("param")String param, Callback<Response> callback);

	@FormUrlEncoded
	@POST("/report/customer/update")
	void editSeatInfo(@Field("param")String param, Callback<Response> callback);
	
	@FormUrlEncoded
	@POST("/ticket_delete")
	void deleteTicket(@Field("param")String param, Callback<Response> callback);
	
	@GET("/operatorgroup")
	void getOperatorGroupUser(@Query("param") String param, Callback<Response> callback);
	
	@GET("/extra_destination/{id}")
	void getExtraDestination(@Query("param")String param ,@Path("id")String id, Callback<Response> callback);

	@GET("/booking/notify")
	void getNotiBooking(@Query("param")String param, Callback<Response> callback);
	
	@GET("/citiesbyoperator")
	void getCitybyOperator(@Query("param") String param, Callback<Response> callback);
	
	@GET("/timesbyoperator")
	void getTimebyOperator(@Query("param") String param, Callback<Response> callback);

	@FormUrlEncoded
	@POST("/user-register")
	void postRegister(
			@Field("name") String name,
			@Field("email") String email,
			@Field("password") String password,
			@Field("phone") String phone,
			@Field("nrc") String nrc,
			@Field("address") String address,
			@Field("type") String type,
			Callback<LoginUser> callback);
	
	/*@FormUrlEncoded
	@POST("/user-login")
	void postLogin(
			@Field("email") String first_name,
			@Field("password") String last_name,
			Callback<LoginUser> callback);*/
	
	@FormUrlEncoded
	@POST("/oauth/access_token") 
	void getAccessToken(@Field("grant_type") String grant_type,
			@Field("client_id") String client_id,
			@Field("client_secret") String client_secret,
			@Field("username") String username,
			@Field("password") String password, 
			@Field("scope") String scope,
			@Field("state") String state, Callback<AccessToken> callback);
	
	@GET("/api/gettrips")
	void getPermission(@Query("access_token") String token, @Query("operator_id") String operatorId, Callback<Response> callback);
	
	@GET("/sale/order")
	void getBooking(@Query("access_token") String token,
			@Query("operator_id") String operator_id,
			@Query("departure_date") String departure_date,
			@Query("from") String from,
			@Query("to") String to,
			@Query("time") String time,
			@Query("book_code") String book_code, Callback<Response> callback);
}
