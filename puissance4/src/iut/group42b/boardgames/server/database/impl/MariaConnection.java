package iut.group42b.boardgames.server.database.impl;

import iut.group42b.boardgames.server.database.AbstractDatabaseConnection;

import java.util.Objects;

public class MariaConnection extends AbstractDatabaseConnection {

	/* Variables */
	private final String host, database, user, password;

	/* Constructor */
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
		return String.format("jdbc:mariadb://%s/%s", host, database);
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return password;
	}

}