package dev.whaabaam.com.quickblox;
/*
 * Created by RahulGupta on 30/8/18
 */

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.michaelflisar.rxbus2.RxBus;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSessionParameters;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBRoster;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.listeners.QBRosterListener;
import com.quickblox.chat.listeners.QBSubscriptionListener;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.model.QBPresence;
import com.quickblox.chat.request.QBMessageGetBuilder;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.core.server.Performer;
import com.quickblox.messages.services.SubscribeService;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import dev.whaabaam.com.app.AppConstants;
import dev.whaabaam.com.app.MyApplication;
import dev.whaabaam.com.ui.home.chatlist.ChatListAdapter;
import dev.whaabaam.com.utils.CommonUtils;

import static dev.whaabaam.com.app.MyApplication.context;
import static dev.whaabaam.com.app.MyApplication.isOnChatScreen;
import static dev.whaabaam.com.app.MyApplication.userModel;


/**
 * @author rahul
 */
public class QuickbloxUtils {

    public static Enum CONNECTION_STATUS;

    static String QB_TAG = "QBASDK";


    public static void init() {

        prepareChatService();
        initializeChatService();
        addConnectionListener();

    }

    public static void signUp() {
        Log.e("QBASDK", QBSettings.getInstance().getAccountKey());
        Log.e("QBASDK", QBSettings.getInstance().getAuthorizationKey());
        Log.e("QBASDK", QBSettings.getInstance().getAuthorizationSecret());
        QBUser user = new QBUser();
        user.setEmail("test123@rahul.com");
        user.setLogin("test123Rahul");
        user.setPassword("1234567890");
        user.setFullName("Rahul 1Test");
        QBUsers.signUp(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                // success
            }

            @Override
            public void onError(QBResponseException error) {
                // error
            }
        });
    }
//
//    public static void signin() {
//        try {
//            QBUser user = new QBUser();
//            user.setId(userModel.getQuickblox_id());
//            user.setPassword(userModel.getEmail());
//            user.setLogin(userModel.getEmail());
//            QBUsers.signIn(user).performAsync(new QBEntityCallback<QBUser>() {
//                @Override
//                public void onSuccess(QBUser user, Bundle args) {
//                    // success
////                    Log.e("QUICKBLOX", user.toString());
//                }
//
//                @Override
//                public void onError(QBResponseException error) {
//                    // error
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void trackSession() {
        QBSessionManager.getInstance().addListener(new QBSessionManager.QBSessionListener() {
            @Override
            public void onSessionCreated(QBSession session) {
                //calls when session was created firstly or after it has been expired
            }

            @Override
            public void onSessionUpdated(QBSessionParameters sessionParameters) {
                //calls when user signed in or signed up
                //QBSessionParameters stores information about signed in user.
            }

            @Override
            public void onSessionDeleted() {
                //calls when user signed Out or session was deleted
            }

            @Override
            public void onSessionRestored(QBSession session) {
                //calls when session was restored from local storage
            }

            @Override
            public void onSessionExpired() {
                //calls when session is expired
            }

            @Override
            public void onProviderSessionExpired(String provider) {
                //calls when provider's access token is expired or invalid
            }

        });
    }

    public static boolean checkSignInUsingSessionParams() {
        return QBSessionManager.getInstance().getSessionParameters() != null;
    }

    public static QBSessionParameters getSessionParameters() {
        QBSessionParameters sessionParameters = QBSessionManager.getInstance().getSessionParameters();
        sessionParameters.getUserId(); //stores user Id if user signed in via email
        sessionParameters.getUserEmail(); //stores user's Email if user signed in via email

        sessionParameters.getAccessToken(); //stores access token for social net if user signed in via social provider

        sessionParameters.getSocialProvider(); //stores so

        return sessionParameters;
    }

    public static void prepareChatService() {
        QBChatService.setDebugEnabled(true);
        QBChatService.setDefaultPacketReplyTimeout(10000);
        QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder = new QBChatService.ConfigurationBuilder();
        chatServiceConfigurationBuilder.setSocketTimeout(0);
        chatServiceConfigurationBuilder.setKeepAlive(true);
        chatServiceConfigurationBuilder.setUseTls(true);
        QBChatService.setConfigurationBuilder(chatServiceConfigurationBuilder);
    }

    public static void initializeChatService() {
        QBChatService chatService = QBChatService.getInstance();
        chatService.setUseStreamManagement(true);
        QBChatService.setDefaultPacketReplyTimeout(150000);
        QBChatService.setDefaultConnectionTimeout(150000);

        QBUser user = new QBUser();
        user.setLogin(String.valueOf(userModel.getId()));
        user.setPassword(org.jivesoftware.smack.util.stringencoder.Base64.encode((String.format("%sWXYZ1234", userModel.getId()))));
        Performer<QBSession> performer = QBAuth.createSession(user);

        performer.performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession result, Bundle params) {
                user.setId(result.getUserId());
                loginToChat(chatService, user);
            }

            @Override
            public void onError(QBResponseException responseException) {
                responseException.printStackTrace();
            }
        });
    }

    public static void loginToChat(QBChatService chatService, QBUser user) {
        if (!isOnline()) {
            chatService.login(user, new QBEntityCallback() {
                @Override
                public void onSuccess(Object o, Bundle bundle) {
                    SubscribeService.subscribeToPushes(context, false);
                    attachIncomingListener();
                }

                @Override
                public void onError(QBResponseException e) {
                    e.printStackTrace();
                    loginToChat(chatService, user);
                }
            });
        }
    }

    public static void addConnectionListener() {
        ConnectionListener connectionListener = new ConnectionListener() {
            @Override
            public void connected(XMPPConnection connection) {
                CONNECTION_STATUS = AppConstants.QUICKBLOX_STATUS.CONNECTED;
            }

            @Override
            public void authenticated(XMPPConnection xmppConnection, boolean b) {
                CONNECTION_STATUS = AppConstants.QUICKBLOX_STATUS.AUTHENTICATED;
            }


            @Override
            public void connectionClosed() {
                CONNECTION_STATUS = AppConstants.QUICKBLOX_STATUS.CLOSED;
            }

            @Override
            public void connectionClosedOnError(Exception e) {
                // connection closed on error. It will be established soon
                CONNECTION_STATUS = AppConstants.QUICKBLOX_STATUS.CLOSED;
                e.printStackTrace();
            }

            @Override
            public void reconnectingIn(int seconds) {
                CONNECTION_STATUS = AppConstants.QUICKBLOX_STATUS.RECONNECTING;
            }

            @Override
            public void reconnectionSuccessful() {
                CONNECTION_STATUS = AppConstants.QUICKBLOX_STATUS.CONNECTED;
            }

            @Override
            public void reconnectionFailed(Exception e) {
                CONNECTION_STATUS = AppConstants.QUICKBLOX_STATUS.CLOSED;
                e.printStackTrace();
            }
        };

        QBChatService.getInstance().addConnectionListener(connectionListener);
    }

    public static void getChatDialogs(ChatListAdapter.OnChatDialogFoundCallback callback) {

        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);
        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(
                new QBEntityCallback<ArrayList<QBChatDialog>>() {
                    @Override
                    public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {
                        callback.onChatDialogFound(result);

                    }

                    @Override
                    public void onError(QBResponseException responseException) {
                        responseException.printStackTrace();

                    }
                });
    }

    public static void sendMessage(String message, QBChatDialog chatDialog, QBAttachment attachment) {
        QBChatMessage msg = new QBChatMessage();
        msg.setDateSent(System.currentTimeMillis() / 1000);
        msg.setDialogId(chatDialog.getDialogId());
        if (attachment != null) {
            msg.addAttachment(attachment);
        }
        msg.setMarkable(true);
        msg.setSenderId(Integer.parseInt(userModel.getQuickblox_id()));
        msg.setBody(message.trim());
        msg.setSaveToHistory(true);

        chatDialog.sendMessage(msg, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                RxBus.get().withKey("QBChatMessageSent").send(msg);
            }

            @Override
            public void onError(QBResponseException e) {
                e.printStackTrace();
                Toast.makeText(context, "Cannot send message. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void sendAttachmentMessage(String filePath, QBChatDialog chatDialog) {
        File filePhoto = new File(filePath);
        QBContent.uploadFileTask(filePhoto, false, null, progress -> {
            // progress - progress in percentages
        }).performAsync(new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle bundle) {
                QBAttachment attachment = new QBAttachment("photo");
                attachment.setId(qbFile.getUid().toString());
                sendMessage("", chatDialog, attachment);
            }

            @Override
            public void onError(QBResponseException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to upload image. Cannot send message. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void markMessageRead(String dialogId, String msgId) {
        StringifyArrayList<String> messagesIDs = new StringifyArrayList<>();
        messagesIDs.add(msgId);
        QBRestChatService.markMessagesAsRead(dialogId, messagesIDs).performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    public static void attachIncomingListener() {
        QBChatService chatService = QBChatService.getInstance();
        QBIncomingMessagesManager incomingMessagesManager = chatService.getIncomingMessagesManager();
        incomingMessagesManager.addDialogMessageListener(
                new QBChatDialogMessageListener() {
                    @Override
                    public void processMessage(String dialogId, QBChatMessage message, Integer senderId) {
                        if (isOnChatScreen)
                            RxBus.get().withKey("QBChatMessageReceived").send(message);
                        else {
                            CommonUtils.sendNotificationForNewMessage(MyApplication.context, message, dialogId);
                        }
                    }

                    @Override
                    public void processError(String dialogId, QBChatException exception, QBChatMessage message, Integer senderId) {
                        exception.fillInStackTrace().printStackTrace();
                    }
                });
    }

    public static void logoutChatService(QBChatService chatService) {
        boolean isLoggedIn = QuickbloxUtils.isOnline();
        if (!isLoggedIn) {
            return;
        }
        chatService.logout(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                chatService.destroy();
            }

            @Override
            public void onError(QBResponseException e) {
                e.printStackTrace();
            }
        });


    }

    public static void createChatDialog(int participantID, OnChatDialogCreatedCallBack callBack) {
        QBChatDialog dialog = new QBChatDialog();
        dialog.setType(QBDialogType.PRIVATE);
        List<Integer> occupants = new ArrayList<>();
        occupants.add(participantID);
        dialog.setOccupantsIds(occupants);
        dialog.setCreatedAt(new Date());
        dialog.setUpdatedAt(new Date());
        QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog result, Bundle params) {
                callBack.onChatDialogCreated(result);
            }

            @Override
            public void onError(QBResponseException responseException) {
                responseException.fillInStackTrace().printStackTrace();
            }
        });
    }

    public static void getChatHistoryForGivenDialog(String dialogID, OnChatDialogCreatedCallBack callBack) {
        QBChatDialog chatDialog = new QBChatDialog(dialogID);

        QBMessageGetBuilder messageGetBuilder = new QBMessageGetBuilder();
        messageGetBuilder.setLimit(100);
//        messageGetBuilder.setSkip()

        QBRestChatService.getDialogMessages(chatDialog, messageGetBuilder)
                .performAsync(new QBEntityCallback<ArrayList<QBChatMessage>>() {
                    @Override
                    public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {
                        callBack.onChatHistoryLoaded(qbChatMessages);
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        e.printStackTrace();
                    }
                });
//        attachIncomingListener(callBack);

    }

    public static void setMutualSubscription() {
        QBRosterListener rosterListener = new QBRosterListener() {
            @Override
            public void entriesDeleted(Collection<Integer> userIds) {

            }

            @Override
            public void entriesAdded(Collection<Integer> userIds) {

            }

            @Override
            public void entriesUpdated(Collection<Integer> userIds) {

            }

            @Override
            public void presenceChanged(QBPresence presence) {

            }
        };

        QBSubscriptionListener subscriptionListener = new QBSubscriptionListener() {
            @Override
            public void subscriptionRequested(int userId) {

            }
        };


// Do this after success Chat login
        QBRoster chatRoster = QBChatService.getInstance().getRoster(QBRoster.SubscriptionMode.mutual, subscriptionListener);
        chatRoster.addRosterListener(rosterListener);
    }

    public static long getLastActivityOfUser(int qbId) {
//        QBUser targetUser = new QBUser();
//        targetUser.set
//        targetUser.setId(qbId);
        try {
            // returns time in second
            return (System.currentTimeMillis() - (QBChatService.getInstance().getLastUserActivity(qbId) * 1000));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
            //handle error
        }
    }

    public static boolean isOnline() {
        return QBChatService.getInstance().isLoggedIn();
    }


    public interface OnChatDialogCreatedCallBack {
        void onChatDialogCreated(QBChatDialog dialog);

        void onChatHistoryLoaded(ArrayList<QBChatMessage> qbChatMessages);

    }

}
