package iut.group42b.boardgames.server.database.impl;

import iut.group42b.boardgames.server.database.AbstractDatabaseConnection;

import java.util.Objects;

public class MariaConnection extends AbstractDatabaseConnection {

	/* Variables */
	private final String host, database, user, password;

	/**
	 * Constructor MariaConnection.
	 *
	 * @param host     String.
	 * @param database String.
	 * @param user     String.
	 * @param password String.
	 */
	public MariaConnection(String host, String database, String user, String password) {
		this.host = Objects.requireNonNull(host, "Database host can't be null.");
		this.database = Objects.requireNonNull(database, "Database database can't be null.");
		this.user = Objects.requireNonNull(user, "Database user can't be null.");
		this.password = password;
	}

	@Override
	public String getJdbcDriverClassPath() {
		return "org.mariadb.jdbc.Driver";
	}

	@Override
	public String getJdbcUrl() {
		return String.format("jdbc:mariadb://%s/%s", this.host, this.database);
	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

}