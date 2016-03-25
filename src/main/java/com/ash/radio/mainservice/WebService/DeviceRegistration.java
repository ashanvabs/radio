package com.ash.radio.mainservice.WebService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.MediaTray;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ash.radio.mainservice.models.User;
import com.ash.radio.mainservice.persistent.HibernateUtil;
import com.ash.radio.mainservice.repository.UserRepository;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import javax.json.*;

@Path("/")
public class DeviceRegistration {

	/////////

	private static final long serialVersionUID = 1L;

	// The SENDER_ID here is the "Browser Key" that was generated when I
	// created the API keys for my Google APIs project.
	private static final String SENDER_ID = "AIzaSyDcIiva-fXdh6I9d4aA0TAgejWSykyg0-I";

	// This is a *cheat* It is a hard-coded registration ID from an Android
	// device
	// that registered itself with GCM using the same project id shown above.
	private static final String DROID_BIONIC = "cAzzbUpnRi4:APA91bGHApq7oBqztj0SkBXE2S7OwSHBUWiTOdn9XRTxIsTNk9M52-8w_TBvljacsB8uuP5WgapioC1VQD7IGjTvHvuulW_DrN89VBrqgO2MFmRgLbtWJLz4a50eLyAt8TWdM1wnTSV1";

	// This array will hold all the registration ids used to broadcast a
	// message.
	// for this demo, it will only have the DROID_BIONIC id that was captured
	// when we ran the Android client app through Eclipse.
	private List<String> androidTargets = new ArrayList<String>();

	////////////////

	private UserRepository userRepo;

	public DeviceRegistration() {
		// TODO Auto-generated constructor stub
		userRepo = new UserRepository();
		androidTargets.add(DROID_BIONIC);

	}
	
//	@GET	
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response doPost(@QueryParam("device") final String device) throws  IOException {
//        
//        // We'll collect the "CollapseKey" and "Message" values from our JSP page
//        String collapseKey = "";
//        String userMessage = "";
//         System.out.println("Device :" +device);
//        try {
//            userMessage = "Go to hell";
//            collapseKey = "message";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.status(200).entity("Ex").build();
//        }
// 
//        // Instance of com.android.gcm.server.Sender, that does the
//        // transmission of a Message to the Google Cloud Messaging service.
//        Sender sender = new Sender(SENDER_ID);
//         
//        // This Message object will hold the data that is being transmitted
//        // to the Android client devices.  For this demo, it is a simple text
//        // string, but could certainly be a JSON object.
//        Message message = new Message.Builder()
//         
//        // If multiple messages are sent using the same .collapseKey()
//        // the android target device, if it was offline during earlier message
//        // transmissions, will only receive the latest message for that key when
//        // it goes back on-line.
//        .collapseKey(collapseKey)
//        .timeToLive(30)
//        .delayWhileIdle(true)
//        .addData("message", userMessage)
//        .build();
//         
//        try {
//            // use this for multicast messages.  The second parameter
//            // of sender.send() will need to be an array of register ids.
//            MulticastResult result = sender.send(message, androidTargets, 1);
//            
//            int error = result.getFailure();
//            System.out.println("Broadcast failure: " + error);
//            
//             System.out.println(androidTargets.size());
//            if (result.getResults() != null) {
//                int canonicalRegId = result.getCanonicalIds();
//                System.out.println(" Send the message : "+canonicalRegId);
//                if (canonicalRegId != 0) {
//                	System.out.println("Send the message");
//                }
//            } else {
//                 error = result.getFailure();
//                System.out.println("Broadcast failure: " + error);
//            }
//             
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
// 
//        
//        return Response.status(200).entity("Done").build();
//                 
//    }
	
	

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getStartingPage(@QueryParam("id") final String h) {
		String output = "<h1>Hello World!<h1> " + h + " <p>RESTful Service is running ... <br>Ping @ "
				+ new Date().toString() + "</p<br>";

		//// db test
		 Session s=HibernateUtil.getSessionFactory().openSession();;
		 Transaction t=s.beginTransaction();
		 User kamal=new User();		 
		 kamal.setName("Kamal2");
		 kamal.setRole(2);
	
		 kamal.setDeviceId(DROID_BIONIC);
		 s.save(kamal);
		 t.commit();
		
		 System.out.println("Save successfully");
		////

		return Response.status(200).entity(output).build();
	}

	@POST
	@Path("/Login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("Hello") final String hello, InputStream input) {
		System.out.print("User loging");
		JsonReader jsonReader = Json.createReader(input);
		JsonObject jsonUser = jsonReader.readObject();
		User user = new User();
		user.setName(jsonUser.getString("name"));
		user.setPassword(jsonUser.getString("password"));

		String IsLogged = userRepo.login(user) ? "true" : "false";
		System.out.println(IsLogged);
		System.out.println(hello);
		return Response.status(200).entity(IsLogged).build();

	}
	
	@GET
	@Path("/update")
	public Response updateRole(@QueryParam("userid") final int id,@QueryParam("role") final int role,InputStream input){
		
		userRepo.updateRole(id,role);
		
		return Response.status(200).entity("updated").build();
	}
	
	
	@GET
	@Path("/register")	
	public Response registerUser(@QueryParam("deviceId") final String deviceId,@QueryParam("gcmId") final String gcmId,  InputStream input) {
		///todo: check whether device id is exist or not. if id is available then send the id of the user
		User user=new User();
		user.setDeviceId(deviceId);
		user.setGcmId(gcmId);
		userRepo.addUser(user);
		
		return Response.status(200).entity(user.getId()+"").build();
		
		/*System.out.println("Register user");
		StringBuilder stringBuilder = new StringBuilder();
		JsonReader jsonReader = Json.createReader(input);
		JsonObject jsonObj = jsonReader.readObject();
		System.out.println("user role :" + jsonObj.getString("user_role"));
		String regId=jsonObj.getString("registration_id");
		androidTargets.add(regId);
		System.out.println("user deice registration id :" + regId);
		// JsonObject jsonData=jsonObj.getJsonObject("data");

		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		stringBuilder = new StringBuilder();

		String line = null;
		try {

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Data Received: " + stringBuilder.toString());
*/
		// return HTTP response 200 in case of success
		
		
		
		
		
	}

}
