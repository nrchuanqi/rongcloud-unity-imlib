package cn.rongcloud.imlib.unity;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imlib.CustomServiceConfig;
import io.rong.imlib.model.*;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.MentionedInfo.MentionedType;
import io.rong.message.*;
import io.rong.push.PushType;
import io.rong.push.notification.PushNotificationMessage;

import java.util.ArrayList;
import java.util.List;
class Convert {
    static Context unityContext;
    static Message toMessage(JsonObject msg){
        ConversationType conversationType = ConversationType.setValue(msg.get("conversationType").getAsInt());
        Gson gson=new Gson();
        String tmp=msg.get("content").getAsString();
        JsonObject obj=gson.fromJson(tmp,JsonObject.class);
        MessageContent content = toMessageContent( obj);
        return Message.obtain(msg.get("targetId").getAsString(), conversationType, content);
    }

    static MessageContent toMessageContent(JsonObject msg){
        if (msg == null) {
            return null;
        }
        String objectName = msg.get("objectName").getAsString();
        MessageContent messageContent = null;
        if (objectName != null) {
            switch (objectName) {
                case "RC:TxtMsg":
                    messageContent = TextMessage.obtain(msg.get("content").getAsString());
                    if (msg.has("extra")) {
                        ((TextMessage) messageContent).setExtra(msg.get("extra").getAsString());
                    }
                    break;
                case "RC:ImgMsg":
                    Uri uri = Utils.getFileUri(unityContext, msg.get("local").getAsString());
                    messageContent = ImageMessage.obtain(uri, uri);
                    if (msg.has("isFull")) {
                        ((ImageMessage) messageContent).setIsFull(msg.get("isFull").getAsBoolean());
                    }
                    if (msg.has("extra")) {
                        ((ImageMessage) messageContent).setExtra(msg.get("extra").getAsString());
                    }
                    break;
                case "RC:FileMsg":
                    messageContent = FileMessage.obtain(Utils.getFileUri(unityContext, msg.get("local").getAsString()));
                    if (msg.has("extra")) {
                        ((FileMessage) messageContent).setExtra(msg.get("extra").getAsString());
                    }
                    break;
                case "RC:LBSMsg":
                    Uri thumbnail = Utils.getFileUri(unityContext, msg.get("thumbnail").getAsString());
                    messageContent = LocationMessage.obtain(
                            msg.get("latitude").getAsDouble(), msg.get("longitude").getAsDouble(), msg.get("name").getAsString(), thumbnail);
                    if (msg.has("extra")) {
                        ((LocationMessage) messageContent).setExtra(msg.get("extra").getAsString());
                    }
                    break;
                case "RC:VcMsg":
                    Uri voice = Utils.getFileUri(unityContext, msg.get("local").getAsString());
                    messageContent = VoiceMessage.obtain(voice, msg.get("duration").getAsInt());
                    if (msg.has("extra")) {
                        ((VoiceMessage) messageContent).setExtra(msg.get("extra").getAsString());
                    }
                    break;
                case "RC:CmdMsg":
                    messageContent = CommandMessage.obtain(msg.get("name").getAsString(), msg.get("data").getAsString());
                    break;
            }
        }

        if (messageContent != null && msg.has("userInfo")) {
            JsonObject userInfoMsg = msg.getAsJsonObject("userInfo");
            if (userInfoMsg != null) {
                UserInfo userInfo = new UserInfo(
                        userInfoMsg.get("userId").getAsString(), userInfoMsg.get("name").getAsString(), Uri.parse(userInfoMsg.get("portraitUrl").getAsString()));
                messageContent.setUserInfo(userInfo);
            }
        }

        if (messageContent != null && msg.has("mentionedInfo")) {
            JsonObject mentionedJson = msg.getAsJsonObject("mentionedInfo");
            if (mentionedJson != null) {
                MentionedType type = MentionedType.valueOf(mentionedJson.get("type").getAsInt());
                ArrayList<String> userIdList = toStringList(mentionedJson.getAsJsonArray("userIdList"));
                String content = mentionedJson.get("mentionedContent").getAsString();
                MentionedInfo mentionedInfo = new MentionedInfo(type, userIdList, content);
                messageContent.setMentionedInfo(mentionedInfo);
            }
        }

        return messageContent;
    }
    static ArrayList<String> toStringList(JsonArray array)  {
        if (array == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i += 1) {
            list.add(array.get(i).getAsString());
        }
        return list;
    }

    static JsonObject toJson(PushNotificationMessage message, PushType pushType) {
        JsonObject msg = new JsonObject();
        Uri portrait = message.getSenderPortrait();
        msg.addProperty("pushType", pushType.getName());
        msg.addProperty("pushId", message.getPushId());
        msg.addProperty("pushTitle", message.getPushTitle());
        msg.addProperty("pushFlag", message.getPushFlag());
        msg.addProperty("pushContent", message.getPushContent());
        msg.addProperty("pushData", message.getPushData());
        msg.addProperty("objectName", message.getObjectName());
        msg.addProperty("senderId", message.getSenderId());
        msg.addProperty("senderName", message.getSenderName());
        msg.addProperty("senderPortraitUrl", portrait == null ? "" : portrait.toString());
        msg.addProperty("targetId", message.getTargetId());
        msg.addProperty("targetUserName", message.getTargetUserName());
        msg.addProperty("conversationType", message.getConversationType().getValue());
        msg.addProperty("extra", message.getExtra());
        return msg;
    }

    /**
     * @noinspection Duplicates
     */
    static JsonObject toJson(String objectName, MessageContent content)  {
        JsonObject msg = new JsonObject();
        msg.addProperty("objectName", objectName);
        switch (objectName) {
            case "RC:TxtMsg":
                TextMessage text = (TextMessage) content;
                msg.addProperty("content", text.getContent());
                msg.addProperty("extra", text.getExtra());
                break;
            case "RC:ImgMsg": {
                ImageMessage image = (ImageMessage) content;
                String local = "";
                Uri localUri = image.getLocalUri();
                if (localUri != null) {
                    local = localUri.toString();
                }
                String remote = "";
                Uri remoteUri = image.getRemoteUri();
                if (remoteUri != null) {
                    remote = remoteUri.toString();
                }
                String thumbnail = "";
                Uri thumbnailUri = image.getThumUri();
                if (remoteUri != null) {
                    thumbnail = thumbnailUri.toString();
                }
                msg.addProperty("local", local);
                msg.addProperty("remote", remote);
                msg.addProperty("thumbnail", thumbnail);
                msg.addProperty("isFull", image.isFull());
                msg.addProperty("extra", image.getExtra());
                break;
            }
            case "RC:FileMsg": {
                FileMessage file = (FileMessage) content;
                String local = "";
                Uri localUri = file.getLocalPath();
                if (localUri != null) {
                    local = localUri.toString();
                }
                String remote = "";
                Uri remoteUri = file.getFileUrl();
                if (remoteUri != null) {
                    remote = remoteUri.toString();
                }
                msg.addProperty("local", local);
                msg.addProperty("remote", remote);
                msg.addProperty("name", file.getName());
                msg.addProperty("size", file.getSize());
                msg.addProperty("fileType", file.getType());
                msg.addProperty("extra", file.getExtra());
                break;
            }
            case "RC:LBSMsg": {
                LocationMessage location = (LocationMessage) content;
                Uri imageUri = location.getImgUri();
                msg.addProperty("name", location.getPoi());
                msg.addProperty("thumbnail", imageUri == null ? "" : imageUri.toString());
                msg.addProperty("latitude", location.getLat());
                msg.addProperty("longitude", location.getLng());
                msg.addProperty("extra", location.getExtra());
                break;
            }
            case "RC:VcMsg": {
                VoiceMessage voice = (VoiceMessage) content;
                msg.addProperty("local", voice.getUri().toString());
                msg.addProperty("duration", voice.getDuration());
                msg.addProperty("extra", voice.getExtra());
                break;
            }
            case "RC:RcNtf": {
                RecallNotificationMessage message = (RecallNotificationMessage) content;
                msg.addProperty("operatorId", message.getOperatorId());
                msg.addProperty("recallTime", message.getRecallTime());
                msg.addProperty("originalObjectName", message.getOriginalObjectName());
                msg.addProperty("isAdmin", message.isAdmin());
                break;
            }
        }
        return msg;
    }

    static JsonObject toJson(Message message) {
        JsonObject msg = new JsonObject();

        msg.addProperty("conversationType", message.getConversationType().getValue());
        msg.addProperty("targetId", message.getTargetId());
        msg.addProperty("messageUId", message.getUId());
        msg.addProperty("messageId", message.getMessageId());
        msg.addProperty("messageDirection", message.getMessageDirection().getValue());
        msg.addProperty("senderUserId", message.getSenderUserId());
        msg.addProperty("sentTime", (double) message.getSentTime());
        msg.addProperty("receivedTime", (double) message.getReceivedTime());
        msg.addProperty("sentStatus", message.getSentStatus().getValue());
        msg.addProperty("extra", message.getExtra());
        msg.addProperty("objectName", message.getObjectName());
        String objectName = message.getObjectName();
        msg.add("content", toJson(objectName, message.getContent()));
        return msg;
    }

    static JsonObject toJson(Conversation conversation)  {
        if (conversation == null) {
            return null;
        }
        JsonObject msg = new JsonObject();
        msg.addProperty("conversationType", conversation.getConversationType().getValue());
        msg.addProperty("conversationTitle", conversation.getConversationTitle());
        msg.addProperty("isTop", conversation.isTop());
        msg.addProperty("unreadMessageCount", conversation.getUnreadMessageCount());
        msg.addProperty("draft", conversation.getDraft());
        msg.addProperty("targetId", conversation.getTargetId());
        msg.addProperty("objectName", conversation.getObjectName());
        msg.addProperty("latestMessageId", conversation.getLatestMessageId());
        msg.add("latestMessage", toJson(conversation.getObjectName(), conversation.getLatestMessage()));
        msg.addProperty("receivedStatus", conversation.getReceivedStatus().getFlag());
        msg.addProperty("receivedTime", conversation.getReceivedTime());
        msg.addProperty("sentStatus", conversation.getSentStatus().getValue());
        msg.addProperty("sentTime", conversation.getSentTime());
        msg.addProperty("senderUserId", conversation.getSenderUserId());
        msg.addProperty("mentionedCount", conversation.getMentionedCount());
        msg.addProperty("hasUnreadMentioned", conversation.getMentionedCount() > 0);
        return msg;
    }

    static JsonArray toJSON(List<Message> messages)  {
        JsonArray array = new JsonArray();
        if (messages != null) {
            for (Message message : messages) {
                array.add(toJson(message));
            }
        }
        return array;
    }
}
