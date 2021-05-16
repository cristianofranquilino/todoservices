package br.com.todoservices.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class ResponseAPI {

	private Object data;
	private List<String> messages;
	private String message;
	private Map<String, String> headers;
	private Response.Status status;
	private int statusCode;

	
	/*Construtor*/
	public ResponseAPI(){
		setMessages(new ArrayList<String>());
		this.setHeaders(new HashMap<String, String>());
	}
	
	/*status*/
	public ResponseAPI status(Response.Status status){
		this.status = status;
		return this;
	}
	
	public ResponseAPI error(){
		statusCode = Response.Status.BAD_REQUEST.getStatusCode();
		this.status = Response.Status.BAD_GATEWAY;
		return this;
	}
	
	public ResponseAPI unauthorized(){
		statusCode = Response.Status.UNAUTHORIZED.getStatusCode();
		this.status = Response.Status.UNAUTHORIZED;
		return this;
	}
	
	
	public ResponseAPI success(){
		statusCode = Response.Status.OK.getStatusCode();
		this.status = Response.Status.OK;
		return this;
	}

	public ResponseAPI information(){
		statusCode = Response.Status.ACCEPTED.getStatusCode();
		this.status = Response.Status.ACCEPTED;
		return this;
	}

	/*Mensagens*/
	public List<String> getMessages() {
		return messages;
	}
	
	public ResponseAPI message(String message){
		this.setMessage(message);
		return this;
	}
	
	public ResponseAPI addMessage(String message){
		this.getMessages().add(message);
		return this;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public ResponseAPI messages(List<String> messages){
		this.setMessages(messages);
		return this;
	}

	public boolean hasMensages(){
		return getMessages().isEmpty() ? false : true;
	}
	
	public void clearMessages() {
		this.getMessages().clear();
	}
	
	
	public boolean hasError(){
		return this.status.equals(Response.Status.BAD_GATEWAY);
	}

	/*header*/
	public ResponseAPI header(String header, String valor){
		this.getHeaders().put(header, valor);
		return this;
	}

	/*data*/
	public ResponseAPI data(Object object){
		setData(object);
		return this;
	}
	
	public <T> T getData(Class<T> clazz) {		
		return clazz.cast(data);
	}
	
	
	/*build*/
	public Response build(){
		
		ResponseBuilder response = Response.status(status).type(MediaType.APPLICATION_JSON).entity(this);

		for (Entry<String, String> header : getHeaders().entrySet()) {
			response.header(header.getKey(), header.getValue());
		}
		
		return response.build();		
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Response.Status getStatus() {
		return status;
	}

	public void setStatus(Response.Status status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
