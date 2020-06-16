package iut.group42b.boardgames.util;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

public class Debug {

	/**
	 * Dump all node in the view.
	 *
	 * @param n Node.
	 * @see <a href="https://stackoverflow.com/questions/21175767/how-to-traverse-the-entire-scene-graph-hierarchy">Debugging routine to dump the scene graph.</a>
	 **/
	public static void dump(Node n) {
		dump(n, 0);
	}

	/**
	 * Simple dfs
	 *
	 * @param n     Node.
	 * @param depth int.
	 */
	private static void dump(Node n, int depth) {
		for (int i = 0; i < depth; i++) System.out.print("  ");
		System.out.println(n);
		if (n instanceof Parent) {
			if (n instanceof SplitPane) {
				for (Node c : ((SplitPane) n).getItems()) {
					dump(c, depth + 1);
				}
			} else {
				for (Node c : ((Parent) n).getChildrenUnmodifiable()) {
					dump(c, depth + 1);
				}
			}
		}
	}

}