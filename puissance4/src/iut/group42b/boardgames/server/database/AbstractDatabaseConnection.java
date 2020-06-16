package iut.group42b.boardgames.server.database;

import java.sql.*;

public abstract class AbstractDatabaseConnection {

	/* Variables */
	protected Connection connection;

	/**
	 * Constructor AbstractDatabaseConnection
	 */
	protected AbstractDatabaseConnection() {
	}

	/**
	 * Connect to database.
	 *
	 * @return boolean.
	 * @throws Exception
	 */
	public boolean connect() throws Exception {
		String driver = this.getJdbcDriverClassPath();

		if (driver != null) {
			Class.forName(driver);
		}

		return (this.connection = DriverManager.getConnection(this.getJdbcUrl(), this.getUser(), this.getPassword())) != null;
	}

	/**
	 * Disconnect the database.
	 *
	 * @return true if success, else false.
	 * @throws Exception
	 */
	public boolean disconnect() throws Exception {
		if (this.connection != null) {
			this.connection.close();
			this.connection = null;

			return true;
		}

		return false;
	}

	/**
	 * Check if connection is connected to database.
	 *
	 * @return boolean
	 */
	public boolean isConnected() {
		try {
			return this.connection != null && !this.connection.isClosed();
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Create a statement.
	 *
	 * @return Statement.
	 * @throws SQLException
	 */
	public Statement createStatement() throws SQLException {
		this.throwIfNotConnected();

		return this.connection.createStatement();
	}

	/**
	 * Create a statement.
	 *
	 * @param sql String.
	 * @return PreparedStatement.
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		this.throwIfNotConnected();

		return this.connection.prepareStatement(sql);
	}

	/**
	 * Create a statement.
	 *
	 * @param sql               String.
	 * @param autoGeneratedKeys int.
	 * @return PreparedStatement.
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		this.throwIfNotConnected();

		return this.connection.prepareStatement(sql, autoGeneratedKeys);
	}

	/**
	 * Throw an Exception if not connected to database.
	 */
	protected void throwIfNotConnected() {
		if (!this.isConnected()) {
			throw new IllegalStateException("Connection not open.");
		}
	}

	/**
	 * Get the Jdbc Driver Class Path.
	 *
	 * @return String
	 */
	public String getJdbcDriverClassPath() {
		return null;
	}

	/**
	 * Get the JDBC URL.
	 *
	 * @return String
	 */
	public abstract String getJdbcUrl();

	/**
	 * Get the User.
	 *
	 * @return String
	 */
	protected String getUser() {
		return null;
	}

	/**
	 * Get the password.
	 *
	 * @return String
	 */
	protected String getPassword() {
		return null;
	}

}