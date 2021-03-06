package com.neet.artifact.game.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.neet.artifact.game.entity.PlayerSave;
import com.snapgames.framework.Game;
import com.snapgames.framework.GamePanel;
import com.snapgames.framework.audio.JukeBox;
import com.snapgames.framework.handler.InputHandler;
import com.snapgames.framework.state.impl.GameStateManager;
import com.snapgames.framework.state.impl.GenericGameState;

/**
 * The Menu for the game.
 * 
 * @author 
 *         ForeignGuyMike(https://www.youtube.com/channel/UC_IV37n-uBpRp64hQIwywWQ
 *         )
 * @author Frédéric Delorme<frederic.delorme@web-context.com>(refactoring)
 *
 */
public class MenuState extends GenericGameState {

	private BufferedImage head;

	private int currentChoice = 0;

	private String[] options = { Game.getMessage("menu.item.start"),
			Game.getMessage("menu.item.options"),
			Game.getMessage("menu.item.quit") };

	private Color titleColor;
	private Font titleFont;

	private Font font;
	private Font font2;

	/**
	 * Default constructor to initialize this menu state.
	 * 
	 * @param gsm
	 */
	public MenuState(GameStateManager gsm) {

		super(gsm);

		try {

			// load floating head
			head = ImageIO.read(getClass().getResourceAsStream("/HUD/Hud.gif"))
					.getSubimage(0, 12, 12, 11);

			// titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 10);

			// load sound fx
			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
			JukeBox.load("/SFX/menuselect.mp3", "menuselect");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setLanguage() {
		options[0] = Game.getMessage("menu.item.start");
		options[1] = Game.getMessage("menu.item.options");
		options[2] = Game.getMessage("menu.item.quit");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.artifact.gamestate.GameState#init()
	 */
	public void init() {
		setLanguage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.artifact.gamestate.GameState#update()
	 */
	public void update(long delay) {

		// check keys
		handleInput();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.artifact.gamestate.GameState#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics2D g) {

		// draw bg
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(Game.getMessage("game.title"), 70, 90);

		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(options[0], 145, 165);
		g.drawString(options[1], 145, 185);
		g.drawString(options[2], 145, 205);

		// draw floating head
		g.drawImage(head, 125, 154 + (20 * currentChoice), null);

		// other
		g.setFont(font2);
		g.drawString(Game.getMessage("game.copyright"), 10, 232);

	}

	/**
	 * Menu item select.
	 */
	private void select() {
		switch (currentChoice) {
		case 0:
			JukeBox.play("menuselect");
			PlayerSave.init();
			gsm.setActiveState(ArtifactGameStateManager.LEVEL1ASTATE);
			break;
		case 1:
			gsm.setActiveState(ArtifactGameStateManager.OPTIONSTATE);
			break;
		case 2:
			System.exit(0);
			break;
		}
	}

	/**
	 * Handle input for the menu state.
	 */
	public void handleInput() {
		if (InputHandler.isPressed(InputHandler.KeyCode.ENTER))
			select();
		if (InputHandler.isPressed(InputHandler.KeyCode.UP)) {
			if (currentChoice > 0) {
				JukeBox.play("menuoption", 0);
				currentChoice--;
			}
		}
		if (InputHandler.isPressed(InputHandler.KeyCode.DOWN)) {
			if (currentChoice < options.length - 1) {
				JukeBox.play("menuoption", 0);
				currentChoice++;
			}
		}
	}

	@Override
	public void reset() {

	}

}
