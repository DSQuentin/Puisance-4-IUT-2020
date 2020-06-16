package iut.group42b.boardgames.network.packet;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.network.packet.impl.PingPacket;
import iut.group42b.boardgames.network.packet.impl.PongPacket;
import iut.group42b.boardgames.network.packet.impl.auth.*;
import iut.group42b.boardgames.network.packet.impl.connection.ConnectionLostPacket;
import iut.group42b.boardgames.network.packet.impl.connection.HeartbeatPacket;
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

	/**
	 * Constructor PacketRegistry, init the packet registry with core packets.
	 *
	 * @see PacketRegistry#registerDefaults()
	 */
	private PacketRegistry() {
		this.packetIdIncrement = new AtomicInteger(0); // incr at every packet add.
		this.packetClassMap = new HashMap<>();
		this.reversePacketClassMap = new HashMap<>();

		this.registerDefaults();
	}

	/**
	 * Core Packet
	 *
	 * @see PacketRegistry#register
	 */
	private void registerDefaults() {
		/* Line */
		this.register(HeartbeatPacket.class);
		this.register(ConnectionLostPacket.class);

		/* Ping - Pong */
		this.register(PingPacket.class);
		this.register(PongPacket.class);

		/* User */
		this.register(UserLoginPacket.class);
		this.register(UserRegisterPacket.class);
		this.register(UserAuthentificationSuccessPacket.class);
		this.register(UserAuthentificationErrorPacket.class);

		/* User Settings */
		this.register(UserSettingsChangePacket.class);
		this.register(UserSettingsChangedPacket.class);


	}

	/**
	 * Add packet in registry.
	 *
	 * @param clazz The packet class.
	 * @return Return -1 if already in registry.
	 */
	public int register(Class<? extends IPacket> clazz) {
		if (!this.reversePacketClassMap.containsKey(clazz)) {
			int id = this.packetIdIncrement.getAndIncrement();

			this.packetClassMap.put(id, clazz);
			this.reversePacketClassMap.put(clazz, id);
			LOGGER.debug("Registered packet ID %s with class: <app>%s", id, clazz.getCanonicalName().substring(Bootstrap.class.getPackage().getName().length()));

			return id;
		}

		return -1;
	}

	/**
	 * Retrieve class from packet id.
	 *
	 * @param packetId Id of packet.
	 * @return Class of packet found.
	 */
	public Class<? extends IPacket> lookup(int packetId) {
		if (!this.packetClassMap.containsKey(packetId)) {
			throw new IllegalStateException("The packet id is not registered: " + packetId);
		}

		return this.packetClassMap.get(packetId);
	}

	/**
	 * Retrieve packet id from this class.
	 *
	 * @param packetClass The packet class.
	 * @return the id of packet.
	 */
	public int lookup(Class<? extends IPacket> packetClass) {
		Objects.requireNonNull(packetClass, "Packet class can't be null");

		if (!this.reversePacketClassMap.containsKey(packetClass)) {
			throw new IllegalStateException("The packet class is not registered: " + packetClass);
		}

		return this.reversePacketClassMap.get(packetClass);
	}

	/**
	 * Get the PacketRegistry
	 *
	 * @return The instance of packet Registry
	 */
	public static PacketRegistry get() {
		return INSTANCE;
	}

}