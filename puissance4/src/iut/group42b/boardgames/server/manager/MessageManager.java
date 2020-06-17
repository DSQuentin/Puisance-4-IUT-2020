package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.application.ServerApplication;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.model.aware.ReadAwareUserProfile;
import iut.group42b.boardgames.social.packet.friendship.FriendListPacket;
import iut.group42b.boardgames.social.packet.message.MessageListPacket;
import iut.group42b.boardgames.social.packet.message.OpenedMessagesPacket;
import iut.group42b.boardgames.social.packet.message.SendMessagePacket;
import iut.group42b.boardgames.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessageManager implements INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(MessageManager.class);

	/* Singleton */
	private final static MessageManager INSTANCE = new MessageManager();

	private MessageManager() {
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof FriendListPacket) {
			List<UserProfile> friends = UserManager.get().findFriendsByUser(handler.getUserProfile());
			List<Integer> unseenCounts = this.getNotReadedCount(handler.getUserProfile(), friends);

			List<ReadAwareUserProfile> profiles = new ArrayList<>();

			for (int i = 0; i < friends.size(); i++) {
				profiles.add(new ReadAwareUserProfile(friends.get(i), unseenCounts.get(i)));
			}

			handler.queue(new FriendListPacket(profiles));
		} else if (packet instanceof MessageListPacket) {
			MessageListPacket messageListPacket = (MessageListPacket) packet;

			int to = messageListPacket.getSenderId();

			handler.queue(new MessageListPacket(to, this.getExchangedMessageWith(handler.getUserProfile().getId(), to)));
		} else if (packet instanceof SendMessagePacket) {
			SendMessagePacket sendMessagePacket = (SendMessagePacket) packet;

			ExchangedMessage message = sendMessagePacket.getMessage();

			int senderId = handler.getUserProfile().getId();
			int receiverId = message.getUserId();

			this.addMessage(senderId, message);

			handler.queue(new MessageListPacket(receiverId, this.getExchangedMessageWith(senderId, receiverId)));

			SocketHandler receiverHandler = ServerApplication.getServer().findSocketHandlerByProfileId(receiverId);
			if (receiverHandler != null) {
				receiverHandler.queue(new MessageListPacket(senderId, this.getExchangedMessageWith(receiverId, senderId)));
			}
		} else if (packet instanceof OpenedMessagesPacket) {
			OpenedMessagesPacket openedMessagesPacket = (OpenedMessagesPacket) packet;

			int senderId = handler.getUserProfile().getId();
			int receiverId = openedMessagesPacket.getUserId();

			this.updateOpenedMessage(senderId, receiverId);
		}
	}


	/**
	 * Get the message between tow people.
	 *
	 * @param fromUserId User id who send the message.
	 * @param toUserId   User id of receiver
	 * @return A List of ExchangedMessage between 2 user.
	 */
	public List<ExchangedMessage> getExchangedMessageWith(int fromUserId, int toUserId) {
		List<ExchangedMessage> messages = new ArrayList<>();

		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT `sent`, `content`, `id_sender` FROM `messages` WHERE (`id_sender` = ? OR `id_sender` = ?) AND (`id_receiver` = ? OR `id_receiver` = ?) ORDER BY `id` DESC LIMIT 50 ;")) {
			preparedStatement.setInt(1, fromUserId);
			preparedStatement.setInt(2, toUserId);
			preparedStatement.setInt(3, fromUserId);
			preparedStatement.setInt(4, toUserId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Date date = resultSet.getDate("sent");
					String content = resultSet.getString("content");
					int senderId = resultSet.getInt("id_sender");

					messages.add(new ExchangedMessage(senderId, date, content));
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		Collections.reverse(messages);
		return messages;
	}

	public void updateOpenedMessage(int fromUserId, int toUserId) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("UPDATE messages SET opened = 1 WHERE `id_sender` = ? AND `id_receiver` = ?;")) {
			preparedStatement.setInt(1, toUserId);
			preparedStatement.setInt(2, fromUserId);

			preparedStatement.execute();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void addMessage(int senderId, ExchangedMessage message) {
		try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("INSERT INTO messages (sent, content, id_sender, id_receiver, opened) VALUES (?, ?, ?, ?, false);")) {
			preparedStatement.setDate(1, new java.sql.Date(message.getDateObject().getTime())); /* Conversion was mandatory to work... */
			preparedStatement.setString(2, message.getContent());
			preparedStatement.setInt(3, senderId);
			preparedStatement.setInt(4, message.getUserId());

			preparedStatement.execute();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public List<Integer> getNotReadedCount(UserProfile from, List<UserProfile> others) {
		List<Integer> counts = new ArrayList<>(others.size());

		for (UserProfile other : others) {
			if (other == null) {
				continue;
			}

			int total = 0;

			try (PreparedStatement preparedStatement = DatabaseInterface.get().getConnection().prepareStatement("SELECT COUNT(`opened`) AS `total` FROM `messages` WHERE `opened` != 1 AND `id_receiver` = ? AND `id_sender` = ?;")) {
				preparedStatement.setInt(1, from.getId()); /* Conversion was mandatory to work... */
				preparedStatement.setInt(2, other.getId());

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						total = resultSet.getInt(1);
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}

			counts.add(total);
		}

		return counts;
	}

	public static MessageManager get() {
		return INSTANCE;
	}

}