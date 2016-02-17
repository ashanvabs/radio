package com.ash.radio.mainservice.gcm;

import java.util.ArrayList;
import java.util.List;

import com.ash.radio.mainservice.models.SongRequest;
import com.ash.radio.mainservice.models.User;
import com.ash.radio.mainservice.repository.UserRepository;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class GCMService {

	private static GCMService gcmService;

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
	// private List<String> androidTargets = new ArrayList<String>();

	private UserRepository userRepo;

	public GCMService() {
		userRepo = new UserRepository();
	}

	public void handleSongRequest(SongRequest songRequest) {
		List<User> users = userRepo.getAllUsers(1);
		List<String> gcmId = createGCMIdList(users);
		multiCastMessage(gcmId, "message", songRequest.getSong());
	}

	public List<String> createGCMIdList(List<User> users) {
		List<String> list = new ArrayList<>();
		for (User u : users) {
			list.add(u.getGcmId());
		}
		return list;
	}

	public void multiCastMessage(List<String> androidTargets, String collapseKey, String userMessage) {
		// We'll collect the "CollapseKey" and "Message" values from our JSP
		// page

		// Instance of com.android.gcm.server.Sender, that does the
		// transmission of a Message to the Google Cloud Messaging service.
		Sender sender = new Sender(SENDER_ID);

		// This Message object will hold the data that is being transmitted
		// to the Android client devices. For this demo, it is a simple text
		// string, but could certainly be a JSON object.
		Message message = new Message.Builder()

				// If multiple messages are sent using the same .collapseKey()
				// the android target device, if it was offline during earlier
				// message
				// transmissions, will only receive the latest message for that
				// key when
				// it goes back on-line.
				.collapseKey(collapseKey).timeToLive(30).delayWhileIdle(true).addData("message", userMessage).build();

		try {
			// use this for multicast messages. The second parameter
			// of sender.send() will need to be an array of register ids.
			MulticastResult result = sender.send(message, androidTargets, 1);

			int error = result.getFailure();
			System.out.println("Broadcast failure: " + error);

			System.out.println(androidTargets.size());
			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				System.out.println(" Send the message : " + canonicalRegId);
				if (canonicalRegId != 0) {
					System.out.println("Send the message");
				}
			} else {
				error = result.getFailure();
				System.out.println("Broadcast failure: " + error);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
