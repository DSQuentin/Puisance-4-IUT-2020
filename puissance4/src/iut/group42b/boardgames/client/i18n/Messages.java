package iut.group42b.boardgames.client.i18n;

import iut.group42b.boardgames.client.i18n.impl.I18nEnumMessage;
import iut.group42b.boardgames.client.i18n.impl.I18nMessage;

public class Messages {

	public static final IMessage ALERT_PSWD_EMAIL_ERROR_TITLE = new I18nMessage("alert.pswd_email.error.title");
	public static final IMessage ALERT_PSWD_EMAIL_ERROR_HEADER = new I18nMessage("alert.pswd_email.error.header");

	public static final IMessage ALERT_VICTORY_TITLE = new I18nMessage("alert.victory.title");
	public static final IMessage ALERT_VICTORY_HEADER = new I18nMessage("alert.victory.header");
	public static final IMessage ALERT_WINREASON_SURRENDER = new I18nMessage("alert.winreason.surrender");
	public static final IMessage ALERT_WINREASON_CONNECTIONLOST = new I18nMessage("alert.winreason.connectionlost");
	public static final IMessage ALERT_WINREASON_NORMAL = new I18nMessage("alert.winreason.normal");

	public static final IMessage ALERT_DEFEAT_TITLE = new I18nMessage("alert.defeat.title");
	public static final IMessage ALERT_DEFEAT_HEADER = new I18nMessage("alert.defeat.header");

	public static final IMessage ALERT_FRIEND_NOT_FOUND_TITLE = new I18nMessage("alert.friend.notfound.title");
	public static final IMessage ALERT_FRIEND_NOT_FOUND_HEADER = new I18nMessage("alert.friend.notfound.header");

	public static final IMessage ALERT_SERVER_ERROR_TITLE = new I18nMessage("alert.server.title");
	public static final IMessage ALERT_SERVER_ERROR_HEADER = new I18nMessage("alert.server.header");
	public static final IMessage ALERT_SERVER_ERROR_CONTENT = new I18nMessage("alert.server.content");

	public static final IMessage ALERT_LOGIN_TITLE = new I18nMessage("alert.login.title");
	public static final IMessage USER_ACCOUNT_CREATED_AT = new I18nMessage("ui.user.settings.created-at");
	public static final IMessage ALERT_REGISTER_TITLE = new I18nMessage("alert.register.title");
	public static final IMessage ENUM_USER_AUTH_ERROR = new I18nEnumMessage("user-auth.error");
	public static final IMessage GAME_AVAILABILIY_STATE = new I18nEnumMessage("game.availability");

	public static final IMessage GAME_NUMBER_OF_TOKENS = new I18nMessage("game.tokens");
	public static final IMessage GAME_NUMBER_OF_TOKENS_OPPONENTS = new I18nMessage("game.tokens.remaining.opponent");

	public static final IMessage UI_CONNECT4_TURN = new I18nMessage("ui.connect4.turn");
	public static final IMessage UI_ALERT_TITLE_SURRENDER = new I18nMessage("ui.alert.surrender.title");
	public static final IMessage UI_ALERT_CONTEXT_SURRENDER = new I18nMessage("ui.alert.surrender.context");
	public static final IMessage UI_ALERT_HEADER_SURRENDER = new I18nMessage("ui.alert.surrender.header");


	public static final IMessage UI_ALERT_TITLE_FIGHT = new I18nMessage("ui.alert.fight.title");
	public static final IMessage UI_ALERT_HEADER_FIGHT = new I18nMessage("ui.alert.fight.header");
	public static final IMessage UI_ALERT_CONTENT_FIGHT = new I18nMessage("ui.alert.fight.content");

	public static final IMessage UI_ALERT_TITLE_FRIEND = new I18nMessage("ui.alert.add.friend.title");
	public static final IMessage UI_ALERT_HEADER_FRIEND = new I18nMessage("ui.alert.add.friend.header");
	public static final IMessage UI_ALERT_CONTENT_FRIEND = new I18nMessage("ui.alert.add.friend.content");


	public static final IMessage ALERT_WIN_DEFEAT = new I18nMessage("game.windefeat.alert");


}