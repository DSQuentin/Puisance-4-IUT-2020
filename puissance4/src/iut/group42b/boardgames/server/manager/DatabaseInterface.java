package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.server.database.AbstractDatabaseConnection;
import iut.group42b.boardgames.util.Logger;

public class DatabaseInterface {

	/* Logger */
	private final static Logger LOGGER = new Logger(DatabaseInterface.class);

	/* Singleton */
	private final static DatabaseInterface INSTANCE = new DatabaseInterface();

	/* Variables */
	private AbstractDatabaseConnection connection;

	/* Constructor */
	private DatabaseInterface() {
	}

	public void initialize(AbstractDatabaseConnection connection) throws Exception {
		if (this.connection != null) {
			throw new IllegalStateException("There are already a database connection provided.");
		}

		LOGGER.info("Connecting to the database...");
		connection.connect();
		LOGGER.info("Connected!");

		this.connection = connection;
	}

	public AbstractDatabaseConnection getConnection() {
		return connection;
	}

	public static DatabaseInterface get() {
		return INSTANCE;
	}

}