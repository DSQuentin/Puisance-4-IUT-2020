package iut.group42b.boardgames.network;

import iut.group42b.boardgames.application.ServerApplication;
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
	private final boolean fromServerSide;
	private final Socket socket;
	private final Queue<IPacket> packetQueue; // paquet en attentes
	private final List<INetworkHandler> handlers;
	private long timeSinceLast;
	private boolean connected;
	private UserProfile userProfile;

	/* Constructor */
	public SocketHandler(Socket socket) {
		this(false, socket);
	}

	/**
	 * Constructor SocketHandler
	 *
	 * @param socket Socket
	 */
	public SocketHandler(boolean fromServerSide, Socket socket) {
		this.fromServerSide = fromServerSide;
		this.socket = socket;
		this.packetQueue = new LinkedList<>();
		this.handlers = new ArrayList<>();

		this.connected = true;
	}

	/**
	 * Send a packet.
	 *
	 * @param socket Socket.
	 * @param packet IPacket.
	 * @throws IOException
	 */
	public static void sendPacket(Socket socket, IPacket packet) throws IOException {
		DataBuffer packetBuffer = new DataBuffer();
		packet.write(packetBuffer);

		DataBuffer buffer = new DataBuffer();
		buffer.writeShort((short) PacketRegistry.get().lookup(packet.getClass()));
		buffer.writeShort((short) packetBuffer.size());
		buffer.writeBuffer(packetBuffer);

		socket.getOutputStream().write(buffer.toByteArray());
	}

	/**
	 * Build the packet received in Socket.
	 *
	 * @param socket Socket.
	 * @return IPacket.
	 * @throws Exception
	 */
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

	@Override
	public void run() {
		this.timeSinceLast = System.currentTimeMillis();

		while (this.socket.isConnected() && !this.socket.isClosed()) {
			IPacket packetToSend;

			packetToSend = this.packetQueue.poll();
			if (packetToSend == null && (System.currentTimeMillis() - this.timeSinceLast) > 1000) {
				packetToSend = HeartbeatPacket.INSTANCE;
			}

			if (packetToSend != null) {
				try {
					if (!(packetToSend instanceof HeartbeatPacket)) {
						LOGGER.debug("(%s) Send packet: %s", this.socket, packetToSend);
					}

					sendPacket(this.socket, packetToSend);

					this.timeSinceLast = System.currentTimeMillis();
				} catch (Exception exception) {
					exception.printStackTrace();

					Utils.slientClose(this.socket);

					continue;
				}
			}

			try {
				if (this.socket.getInputStream().available() > 4) {
					IPacket packet = readPacket(this.socket);

					if (!(packet instanceof HeartbeatPacket)) {
						LOGGER.debug("(%s) Received packet: %s", this.socket, packet);

						this.notify(packet);
					}

					this.timeSinceLast = System.currentTimeMillis();
				}
			} catch (Exception exception) {
				exception.printStackTrace();

				Utils.slientClose(this.socket);

				continue;
			}

			try {
				Thread.sleep(20L);
			} catch (Exception exception) {
			}
		}

		LOGGER.debug("(%s) Socket close", this.socket);
		this.notify(new ConnectionLostPacket());
		this.setProfile(null);
		this.connected = false;

		if (this.isFromServerSide()) {
			ServerApplication.getServer().unregister(this);
		} else {
			// TODO Notify interface that client connection has been lost

		}
	}

	/**
	 * Add packet to queue.
	 *
	 * @param packet IPacket.
	 */
	public void queue(IPacket packet) {
		this.packetQueue.add(packet);
	}

	/**
	 * Start listening handler.
	 *
	 * @param handler INetworkHandler.
	 */
	public void subscribe(INetworkHandler handler) {
		this.unsubscribe(handler);

		this.handlers.add(handler);
	}

	/**
	 * Stop listening handler.
	 *
	 * @param handler INetworkHandler.
	 */
	public void unsubscribe(INetworkHandler handler) {
		this.handlers.remove(handler);
	}

	/**
	 * For all subscribers send a packet.
	 *
	 * @param packet IPacket
	 */
	public void notify(IPacket packet) {
		for (INetworkHandler networkHandler : this.handlers) {
			try {
				networkHandler.handlePacket(this, packet);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Start a thread in background.
	 */
	public void newThread() {
		new Thread(this).start();
	}

	/**
	 * Set the user profile
	 *
	 * @param userProfile UserProfile
	 */
	public void setProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * Get the user profile.
	 *
	 * @return UserProfile.
	 */
	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public boolean isConnected() {
		return this.connected;
	}

	public boolean isFromServerSide() {
		return this.fromServerSide;
	}
}