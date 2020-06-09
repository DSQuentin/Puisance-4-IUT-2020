package iut.group42b.boardgames.network;

import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.PacketRegistry;
import iut.group42b.boardgames.util.DataBuffer;
import iut.group42b.boardgames.util.Logger;

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

	/* Constructor */
	public SocketHandler(Socket socket) {
		this.socket = socket;
		this.packetQueue = new LinkedList<>();
		this.handlers = new ArrayList<>();
	}

	@Override
	public void run() {
		while (socket.isConnected()) {
			IPacket packetToSend;

			if ((packetToSend = packetQueue.poll()) != null) {
				try {
					LOGGER.debug("(%s) Send packet: %s", socket, packetToSend);
					sendPacket(socket, packetToSend);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

			try {
				if (socket.getInputStream().available() > 4) {
					IPacket packet = readPacket(socket);

					LOGGER.debug("(%s) Received packet: %s", socket, packet);
					handlers.forEach((handler) -> handler.handlePacket(this, packet));
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}

			try {
				Thread.sleep(20L);
			} catch (Exception exception) {
				;
			}
		}
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

}