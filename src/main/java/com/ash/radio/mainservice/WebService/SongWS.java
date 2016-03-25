package com.ash.radio.mainservice.WebService;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ash.radio.mainservice.gcm.GCMService;
import com.ash.radio.mainservice.models.SongRequest;
import com.ash.radio.mainservice.repository.SongRequestRepository;


@Path("/song")
public class SongWS {

	private SongRequestRepository songReqRepo;
	private GCMService gcmService;
	public SongWS() {
		songReqRepo=new SongRequestRepository();
		gcmService=new GCMService();
	}
	
	@GET
	@Path("/request")
	@Produces(MediaType.TEXT_PLAIN)	
	public Response requestSong(@QueryParam("userid") final int userId,@QueryParam("song") String song , InputStream input){
		System.out.println("Request a song");	
		
			SongRequest sr=new SongRequest(userId,song);
			//sr.setAccept(false);
			songReqRepo.addRequest(sr);
			gcmService.handleSongRequest(sr);
		return Response.status(200).entity("handle a song request").build();
		
	}
	
	@GET
	@Path("/getrequests")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSongRequests(InputStream input){
		List<SongRequest> pending =songReqRepo.getNotAcceptedRequests();
		System.out.println("Got data");
		JsonArrayBuilder jsonArray = Json.createArrayBuilder();
	    for(SongRequest sr : pending) {
	    	
	        jsonArray.add(Json.createObjectBuilder()
	        	.add("requestId", sr.getId())
	        	//.add("requesterId", sr.getUser().getId())
	            //.add("requester", sr.getUser().getName())
	            //.add("requesttime", sr.getRequestTime().toString())
	        	.add("song", sr.getSong()));
	    	
	    }
	    JsonArray array =jsonArray.build();
	    System.out.println(array);
		
		return Response.status(200).entity(array.toString()).build();
	}
	
	@GET
	@Path("/isacceptrequest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceptRequests(@QueryParam("id") final int requestId ,InputStream input){
		
		songReqRepo.IsAccept(requestId, true);
		return Response.status(200).entity("Accept").build();
	}
	
	@GET
	@Path("/rejectrequest")
	@Produces(MediaType.APPLICATION_JSON)
	public Response rejectRequests(@QueryParam("id") final int songId, InputStream input){
		return Response.status(200).entity("Reject").build();
	}	
}
