package iut.group42b.boardgames.network;

import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.PacketRegistry;
import iut.group42b.boardgames.network.packet.impl.connection.ConnectionLostPacket;
import iut.group42b.boardgames.network.packet.impl.connection.HeartbeatPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;
import iut.group42b.boardgames.util.Logger;
import iut.group42b.boardgames.util.Utils;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SocketHandler implements Runnable {

	/* Logger */
	private final static Logger LOGGER = new Logger(SocketHandler.class);

	/* Variables */
	private final Socket socket;
	private final Queue<IPacket> packetQueue; // paquet en attentes
	private final List<INetworkHandler> handlers;
	private long timeSinceLast;
	private UserProfile userProfile;

	/* Constructor */
	public SocketHandler(Socket socket) {
		this.socket = socket;
		this.packetQueue = new LinkedList<>();
		this.handlers = new ArrayList<>();
	}

	@Override
	public void run() {
		timeSinceLast = System.currentTimeMillis();

		while (socket.isConnected() && !socket.isClosed()) {
			IPacket packetToSend;

			packetToSend = packetQueue.poll();
			if (packetToSend == null && (System.currentTimeMillis() - timeSinceLast) > 1000) {
				packetToSend = HeartbeatPacket.INSTANCE;
			}

			if (packetToSend != null) {
				try {
					if (!(packetToSend instanceof HeartbeatPacket)) {
						LOGGER.debug("(%s) Send packet: %s", socket, packetToSend);
					}

					sendPacket(socket, packetToSend);

					timeSinceLast = System.currentTimeMillis();
				} catch (Exception exception) {
					exception.printStackTrace();

					Utils.slientClose(socket);

					continue;
				}
			}

			try {
				if (socket.getInputStream().available() > 4) {
					IPacket packet = readPacket(socket);

					if (!(packet instanceof HeartbeatPacket)) {
						LOGGER.debug("(%s) Received packet: %s", socket, packet);

						notify(packet);
					}

					timeSinceLast = System.currentTimeMillis();
				}
			} catch (Exception exception) {
				exception.printStackTrace();

				Utils.slientClose(socket);

				continue;
			}

			try {
				Thread.sleep(20L);
			} catch (Exception exception) {
				;
			}
		}

		LOGGER.debug("(%s) Socket close", socket);
		notify(new ConnectionLostPacket());
		setProfile(null);
	}


	public void queue(IPacket packet) {
		packetQueue.add(packet);
	}

	public void subscribe(INetworkHandler handler) {
		unsubscribe(handler);

		this.handlers.add(handler);
	}

	public void unsubscribe(INetworkHandler handler) {
		this.handlers.remove(handler);
	}

	public void notify(IPacket packet) {
		for (INetworkHandler networkHandler : handlers) {
			try {
				networkHandler.handlePacket(this, packet);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public static void sendPacket(Socket socket, IPacket packet) throws IOException {
		DataBuffer packetBuffer = new DataBuffer();
		packet.write(packetBuffer);

		DataBuffer buffer = new DataBuffer();
		buffer.writeShort((short) PacketRegistry.get().lookup(packet.getClass()));
		buffer.writeShort((short) packetBuffer.size());
		buffer.writeBuffer(packetBuffer);

		socket.getOutputStream().write(buffer.toByteArray());
	}

	public static IPacket readPacket(Socket socket) throws Exception {
		byte[] tmp = new byte[2];
		DataBuffer buffer = new DataBuffer();

		buffer.reset();
		socket.getInputStream().read(tmp);
		buffer.writeBytes(tmp);

		short packetId = buffer.readShort();
		Class<? extends IPacket> packetClass = PacketRegistry.get().lookup(packetId);

		buffer.reset();
		socket.getInputStream().read(tmp);
		buffer.writeBytes(tmp);

		short packetSize = buffer.readShort();

		byte[] packetBytes = new byte[packetSize];

		buffer.reset();
		socket.getInputStream().read(packetBytes);
		buffer.writeBytes(packetBytes);

		if (packetClass == null) {
			throw new IllegalStateException("Unknown packet id: " + packetId);
		}

		IPacket packet = packetClass.newInstance();
		buffer.rewind();
		packet.read(buffer);

		return packet;
	}

	public void newThread() {
		new Thread(this).start();
	}

	public void setProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}


}