package iut.group42b.boardgames;

import iut.group42b.boardgames.application.ClientApplication;
import iut.group42b.boardgames.application.ServerApplication;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.impl.checker.CheckerGame;
import iut.group42b.boardgames.game.impl.chess.ChessGame;
import iut.group42b.boardgames.game.impl.connect4.Connect4Game;
import iut.group42b.boardgames.game.impl.monopoly.MonopolyGame;
import iut.group42b.boardgames.game.impl.werewolf.WereWolfGame;
import iut.group42b.boardgames.game.packet.PlayerJoinPacket;
import iut.group42b.boardgames.game.packet.PlayerLoosePacket;
import iut.group42b.boardgames.game.packet.PlayerSurrenderPacket;
import iut.group42b.boardgames.game.packet.PlayerWinPacket;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingJoinPacket;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingLeavePacket;
import iut.group42b.boardgames.network.packet.PacketRegistry;
import iut.group42b.boardgames.social.packet.friendship.FriendListPacket;
import iut.group42b.boardgames.social.packet.message.MessageListPacket;
import iut.group42b.boardgames.social.packet.message.SendMessagePacket;
import iut.group42b.boardgames.util.Logger;
import iut.group42b.boardgames.util.cli.Option;
import iut.group42b.boardgames.util.cli.OptionParser;

public class Bootstrap {

	/* Options */
	public static final Option HELP_OPTION = new Option('h', "help", "Show this help page.");
	public static final Option LOG_OPTION = new Option('l', "log", "Enable some log-level. (e.g: DEBUG,VERBOSE)", "");
	public static final Option SERVER_OPTION = new Option('s', "server", "Start a server.");
	public static final Option CLIENT_OPTION = new Option('c', "client", "Start a client.");
	public static final Option IP_OPTION = new Option('i', "ip", "Specify the IP address to connect to.", "localhost");
	public static final Option PORT_OPTION = new Option('p', "port", "Specify the port to connect to.", 5555);
	public static final Option CLIENT_GIVEN_EMAIL_OPTION = new Option((char) 0, "email", "Pre-fill the client UI with an email.", "");
	public static final Option CLIENT_GIVEN_PASSWORD_OPTION = new Option((char) 0, "password", "Pre-fill the client UI with a password.", "");
	public static final Option FRENCH_LANGUAGE = new Option('f', "french", "Select French 'fr' ");
	/* Logger */
	private static final Logger LOGGER = new Logger(Bootstrap.class);

	/* Bootstrap */
	public static void main(String[] args) throws Exception {
		OptionParser optionParser = new OptionParser();

		optionParser.addOption(HELP_OPTION);
		optionParser.addOption(LOG_OPTION);
		optionParser.addOption(SERVER_OPTION);
		optionParser.addOption(CLIENT_OPTION);
		optionParser.addOption(IP_OPTION);
		optionParser.addOption(PORT_OPTION);
		optionParser.addOption(CLIENT_GIVEN_EMAIL_OPTION);
		optionParser.addOption(CLIENT_GIVEN_PASSWORD_OPTION);
		optionParser.addOption(FRENCH_LANGUAGE);

		try {
			optionParser.parse(args);

			if (HELP_OPTION.isUsed()) {
				throw new UnsupportedOperationException();
			}
		} catch (Exception exception) {
			if (!(exception instanceof UnsupportedOperationException)) {
				System.out.println("Failed to parse arguments.");
				System.out.println("  Cause: ");
				exception.printStackTrace();
			}

			optionParser.printHelp(System.out);

			if (exception instanceof UnsupportedOperationException) {
				System.exit(0);
			}
		}

		/* Enable log-level from CLI. */
		if (LOG_OPTION.isUsed()) {
			String[] levelsToEnable = ((String) LOG_OPTION.getValue()).split(",");

			for (String levelToEnable : levelsToEnable) {
				try {
					Logger.Level level = Logger.Level.valueOf(levelToEnable);

					level.enable();
					LOGGER.log(level, "Level enabled!");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}

		/* Registering all packets. */
		PacketRegistry.get().register(MatchmakingJoinPacket.class);
		PacketRegistry.get().register(MatchmakingLeavePacket.class);
		PacketRegistry.get().register(PlayerWinPacket.class);
		PacketRegistry.get().register(PlayerLoosePacket.class);
		PacketRegistry.get().register(PlayerJoinPacket.class);
		PacketRegistry.get().register(PlayerSurrenderPacket.class);
		PacketRegistry.get().register(FriendListPacket.class);
		PacketRegistry.get().register(MessageListPacket.class);
		PacketRegistry.get().register(SendMessagePacket.class);

		/* Registering all games. */
		GameRegistry.get().register(new Connect4Game());
		GameRegistry.get().register(new CheckerGame());
		GameRegistry.get().register(new ChessGame());
		GameRegistry.get().register(new MonopolyGame());
		GameRegistry.get().register(new WereWolfGame());

		/* Starting a server or a client. */
		if (SERVER_OPTION.isUsed()) {
			ServerApplication.startFromBootstrap();
		} else {
			ClientApplication.startFromBootstrap();
		}
	}

}