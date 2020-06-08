package iut.group42b.boardgames.network.packet;

import iut.group42b.boardgames.network.packet.impl.PingPacket;
import iut.group42b.boardgames.network.packet.impl.PongPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationErrorPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationSuccessPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserLoginPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserRegisterPacket;
import iut.group42b.boardgames.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketRegistry {

	/* Logger */
	private final static Logger LOGGER = new Logger(PacketRegistry.class);

	/* Singleton */
	private static final PacketRegistry INSTANCE = new PacketRegistry();

	/* Variables */
	private final AtomicInteger packetIdIncrement;
	private final Map<Integer, Class<? extends IPacket>> packetClassMap;
	private final Map<Class<? extends IPacket>, Integer> reversePacketClassMap;

	/* Constructor */
	private PacketRegistry() {
		this.packetIdIncrement = new AtomicInteger(0);
		this.packetClassMap = new HashMap<>();
		this.reversePacketClassMap = new HashMap<>();

		registerDefaults();
	}

	private void registerDefaults() {
		/* Ping - Pong */
		register(PingPacket.class);
		register(PongPacket.class);

		/* User */
		register(UserLoginPacket.class);
		register(UserRegisterPacket.class);
		register(UserAuthentificationSuccessPacket.class);
		register(UserAuthentificationErrorPacket.class);
	}

	public int register(Class<? extends IPacket> clazz) {
		if (!reversePacketClassMap.containsKey(clazz)) {
			int id = packetIdIncrement.getAndIncrement();

			packetClassMap.put(id, clazz);
			reversePacketClassMap.put(clazz, id);
			LOGGER.debug("Registered packet ID %s with class: %s", id, clazz.getCanonicalName());

			return id;
		}

		return -1;
	}

	public Class<? extends IPacket> lookup(int packetId) {
		if (!packetClassMap.containsKey(packetId)) {
			throw new IllegalStateException("The packet id is not registered: " + packetId);
		}

		return packetClassMap.get(packetId);
	}

	public int lookup(Class<? extends IPacket> packetClass) {
		Objects.requireNonNull(packetClass, "Packet class can't be null");

		if (!reversePacketClassMap.containsKey(packetClass)) {
			throw new IllegalStateException("The packet class is not registered: " + packetClass);
		}

		return reversePacketClassMap.get(packetClass);
	}

	public static PacketRegistry get() {
		return INSTANCE;
	}

}