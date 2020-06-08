package iut.group42b.boardgames.server.database;

import java.sql.*;

public abstract class AbstractDatabaseConnection {

	/* Variables */
	protected Connection connection;

	/* Constructor */
	protected AbstractDatabaseConnection() {
		;
	}

	public boolean connect() throws Exception {
		String driver = getJdbcDriverClassPath();

		if (driver != null) {
			Class.forName(driver);
		}

		return (connection = DriverManager.getConnection(getJdbcUrl(), getUser(), getPassword())) != null;
	}

	public boolean disconnect() throws Exception {
		if (connection != null) {
			connection.close();
			connection = null;

			return true;
		}

		return false;
	}

	public boolean isConnected() {
		try {
			return connection != null && !connection.isClosed();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	public Statement createStatement() throws SQLException {
		throwIfNotConnected();

		return connection.createStatement();
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		throwIfNotConnected();

		return connection.prepareStatement(sql);
	}

	protected void throwIfNotConnected() {
		if (!isConnected()) {
			throw new IllegalStateException("Connection not open.");
		}
	}

	public String getJdbcDriverClassPath() {
		return null;
	}

	public abstract String getJdbcUrl();

	protected String getUser() {
		return null;
	}

	protected String getPassword() {
		return null;
	}

}