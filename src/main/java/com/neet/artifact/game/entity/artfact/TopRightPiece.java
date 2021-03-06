package com.neet.artifact.game.entity.artfact;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.snapgames.framework.entity.MapObject;
import com.snapgames.framework.gfx.tilemap.TileMap;

public class TopRightPiece extends MapObject {

	private BufferedImage[] sprites;

	public TopRightPiece(TileMap tm) {
		super(tm);
		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Other/Game.gif"));
			sprites = new BufferedImage[1];
			width = height = 4;
			sprites[0] = spritesheet.getSubimage(10, 0, 10, 10);
			animation.setFrames(sprites);
			animation.setDelay(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.framework.entity.MapObject#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.framework.entity.GameObject#reset()
	 */
	@Override
	public void reset() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.framework.entity.GameObject#update(long)
	 */
	@Override
	public void update(long delta) {
		x += dx;
		y += dy;
		animation.update(delta);
	}

}
