package sample;

import java.net.Socket;
import java.sql.SQLException;
import java.util.ResourceBundle;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.i18n.impl.I18nMessage;
import iut.group42b.boardgames.game.packet.PlayerJoinPacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.packet.PacketRegistry;
import iut.group42b.boardgames.network.packet.impl.PingPacket;
import iut.group42b.boardgames.network.packet.impl.PongPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserLoginPacket;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    /* Logger */
    private final static Logger LOGGER = new Logger(Main.class);

    public static String getByteBinaryString(long b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 63; i >= 0; --i) {
            sb.append(b >>> i & 1);

            if ((i) % 8 == 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    // java -jar app.jar --server --port 1234
    // java -jar app.jar --client --email='hello@world.fr' --password='thisismypass'

    public static void main(String[] args) throws SQLException {
        //PacketRegistry.get().register(PlayerJoinPacket.class);

        // java -jar app.jar -log=INFO,VERSBOSE

        //Logger.Level.DEBUG.disable();


        //   new PlayerJoinPacket(Long.MAX_VALUE, 7l).write(buffer);


       /* PlayerJoinPacket received = new PlayerJoinPacket();
        buffer.rewind();
        received.read(buffer);

        buffer.dump(System.out);

        LOGGER.info("player id %s game id %s", received.getPlayerId(), received.getGameId());

*/
        /*DataBuffer buffer = new DataBuffer();

        try {
            Socket socket = new Socket("127.0.0.1", 1234);

            Thread.sleep(200L);

            byte[] array = new byte[512];
            int readed = socket.getInputStream().read(array);

            System.out.println(Arrays.toString(array));

            for (int i = 0; i < array.length; i++) {
                buffer.writeByte(array[i]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/


      /*  DataBuffer buffer2 = new DataBuffer();
        buffer2.write(Integer.MAX_VALUE);
        int read = buffer2.readInt();
        System.out.println(read);
        System.out.println(read == Integer.MAX_VALUE);
        buffer2.dump(System.out);*/
        /*buffer.dump(System.out);
        buffer.rewind();
        System.out.println(buffer.readString());
        System.out.println(buffer.readInt());
        System.out.println(buffer.readString());
        System.out.println(buffer.readLong());*/

       /* try {
            Socket socket = new Socket("127.0.0.1", 1234);

            Thread.sleep(200L);

            SocketHandler socketHandler = new SocketHandler(socket);

            socketHandler.newThread();
            socketHandler.subscribe((wrapper, packet) -> {
                System.out.println(packet);

                if (packet instanceof PingPacket) {
                    wrapper.queue(new PongPacket((PingPacket) packet));
                }
            });

            socketHandler.queue(new UserLoginPacket("hello@world.fr", ""));

            while (true) {
                Thread.sleep(100L);
            }

            //IPacket packet = NetworkServer.readPacket(socket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

 // TODO demanger pour le matchmaching
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle resourceBundle = Resource.loadResourceBundle("strings");
        I18nMessage.setGlobalResourceBundle(resourceBundle);

        Parent parent = FXMLLoader.load(Resource.loadForm("index.fxml"), resourceBundle);
        Parent parent2 = FXMLLoader.load(Resource.loadForm("waiting-player.fxml"), resourceBundle);

        Scene scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.show();

        Platform.runLater(() -> {
           Stage dialog = new Stage();
           dialog.setScene(new Scene(parent2));

            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        });

    }

}