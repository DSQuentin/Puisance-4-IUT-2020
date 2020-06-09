package iut.group42b.boardgames.social.model;

import iut.group42b.boardgames.network.rw.IWritableReadableObject;
import iut.group42b.boardgames.util.DataBuffer;

public class UserProfile implements IWritableReadableObject {

	/* Variables */
	private String name;
	private String url;
	private boolean connected;
	private boolean admin;

	@Override
	public void read(DataBuffer buffer) {

	}

	@Override
	public void write(DataBuffer buffer) {

	}

}