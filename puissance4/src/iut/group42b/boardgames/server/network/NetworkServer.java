package iut.group42b.boardgames.server.network;

import iut.group42b.boardgames.game.packet.PlayerJoinPacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.server.manager.UserManager;
import iut.group42b.boardgames.network.packet.PacketRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {

	private final ServerSocket serverSocket;

	public NetworkServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}

	public void listen() {
		while (!serverSocket.isClosed()) {
			try {
				Socket socket = serverSocket.accept();
				SocketHandler socketHandler = new SocketHandler(socket);

				socketHandler.newThread();
				/*socketHandler.subscribe((wrapper, packet) -> {
					if (packet instanceof PongPacket) {
						PongPacket pongPacket = (PongPacket) packet;

						System.out.println("ping = " + (System.currentTimeMillis() - pongPacket.getMillis()));

						try {
							Thread.sleep(4000L);
						} catch (Exception exception) {

						}

						wrapper.queue(new PingPacket());
					}
				});*/

				socketHandler.subscribe(UserManager.get());
				//socketHandler.queue(new PingPacket());

				System.out.println(socket);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		PacketRegistry.get().register(PlayerJoinPacket.class);

		new NetworkServer(1234).listen();
	}

}
