import * as functions from 'firebase-functions';

'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

/**
* Triggers when a user receives a Friend Request
*
* Node added to /Users/Gender/{gender}/{uid}/FriendRequests
* Users save their device notification tokens to /Users/Gender/{gender}/{uid}/token/
exports.sendFriendRequestNotification = functions.database.ref('/Users/Gender/{gender}/{uid}/FriendRequests')
.onWrite(async (change, context) => {
	const requesterId = context.params.requesterId;
	const requestedId = context.params.requestedId;
	//if unfriended we exit the function
	if(!change.after.val())
	{
		return console.log('User ', requesterId, 'unfriended user', requestedId);
	}
	console.log(requesterId, ' has requested you as a friend.);
	
	//Get the list of device notification tokens
	const getDeviceTokensPromsie = admin.database()
		.ref('/Users/Gender/{gender}/{uid}/notificationTokens').once('value');
		
		//Get the requester profile
		const getRequesterProfilePromise = admin.auth().getUser(requesterId);
		
		//The snapshot to the user's tokens
		let tokens;
		
		const results = await Promise.all([getDeviceTokensPromsie, getRequesterProfilePromise]);
		tokens = results[0];
		const requester = results[1];
		
		//Check if there are any device tokens.
		if(!tokens.hasChildren())
		{
			return console.log('No notifcation tokens to send to.');
		}
		console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');
      console.log('Fetched follower profile', follower);

      // Notification details.
      const payload = {
        notification: {
          title: 'You have a new follower!',
          body: `${follower.displayName} is now following you.`,
          icon: follower.photoURL
        }
      };

      // Listing all tokens as an array.
      tokens = Object.keys(tokensSnapshot.val());
      // Send notifications to all tokens.
      const response = await admin.messaging().sendToDevice(tokens, payload);
      // For each message check if there was an error.
      const tokensToRemove = [];
      response.results.forEach((result, index) => {
        const error = result.error;
        if (error) {
          console.error('Failure sending notification to', tokens[index], error);
          // Cleanup the tokens who are not registered anymore.
          if (error.code === 'messaging/invalid-registration-token' ||
              error.code === 'messaging/registration-token-not-registered') {
            tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
          }
        }
      });
      return Promise.all(tokensToRemove);
    });
