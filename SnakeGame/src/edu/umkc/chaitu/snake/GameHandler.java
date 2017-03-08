package edu.umkc.chaitu.snake;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import edu.umkc.chaitu.board.IGameBoard;
import edu.umkc.chaitu.clock.IClock;
import edu.umkc.chaitu.side.ISidePanel;

public class GameHandler{
	private SnakeMain game;
	private IGameBoard board;
	private ISidePanel side;
	private IClock clock;
	
	public GameHandler(){
		//System.out.println("inside gamehandler constructor");
	}
	
	public void setGame(SnakeMain game) {
		this.game = game;
	}
	
	public void evaluateExtensions() {
		System.out.println("evaluating extensions");
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("edu.umkc.chaitu.snakegame");
		try {
			for (IConfigurationElement e : config) {
				System.out.println("Evaluating extension");
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IGameBoard) {
					((IGameBoard) o).initialize(game);
					System.out.println(((IGameBoard) o).getClass().getName());
					this.board = (IGameBoard) o;
				} else if (o instanceof ISidePanel) {
					((ISidePanel) o).initialize(game);
					System.out.println(((ISidePanel) o).getClass().getName());
					this.side = (ISidePanel) o;
				} else if (o instanceof IClock) {
					((IClock) o).initialize(9.0f);
					System.out.println(((IClock) o).getClass().getName());
					this.clock = (IClock) o;
				}
			}
		} catch (CoreException ex) {
			//System.out.println("Some exception in coreExtensions evaluation");
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}

	public IGameBoard getBoard() {
		return board;
	}

	public ISidePanel getSide() {
		return side;
	}

	public IClock getClock() {
		return clock;
	}
		
}
