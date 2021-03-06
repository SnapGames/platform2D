package com.snapgames.framework.state.impl;

import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.snapgames.framework.GamePanel;
import com.snapgames.framework.audio.JukeBox;
import com.snapgames.framework.state.GSM;
import com.snapgames.framework.state.GameState;

public class GameStateManager implements GSM {

	/**
	 * Array of all possible game states.
	 */
	protected Map<String, Class<?>> gameStates = new HashMap<>();
	/**
	 * index of current active state.
	 */
	protected String currentState;
	/**
	 * The Pause State (when player push pause button.
	 */
	protected GameState pauseState;
	/**
	 * Pause state engaged ?
	 */
	protected boolean paused;
	private Map<String, GameState> activeStates = new HashMap<>();

	public GameStateManager() {
		JukeBox.init();
		loadState(currentState);
	}

	/**
	 * Prepare {@link GenericGameState} for the game <code>state</code>.
	 * 
	 * @param state
	 */
	protected void loadState(String state) {
		if (gameStates.containsKey(state)) {
			Class<?> stateClass = gameStates.get(state);
			currentState = state;
			try {
				// Constructor for the level.
				Constructor<?> activeStateCst = stateClass
						.getDeclaredConstructor(new Class[] { GameStateManager.class });
				// instances the GameSate, and store to cache..
				activeStates.put(state, (GenericGameState) activeStateCst
						.newInstance(new Object[] { this }));

			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * remove state.
	 * 
	 * @param state
	 */
	private void unloadState(String state) {
		activeStates.remove(state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.framework.state.GSM#setActiveState(java.lang.String)
	 */
	public void setActiveState(String state) {
		if (gameStates.containsKey(currentState)) {
			unloadState(currentState);
			currentState = state;
			loadState(currentState);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.framework.state.GSM#setPaused(boolean)
	 */
	public void setPaused(boolean b) {
		paused = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.framework.state.GSM#update(long)
	 */
	public void update(long delay) {
		if (paused) {
			pauseState.update(delay);
			return;
		}
		if (activeStates.containsKey(currentState))
			activeStates.get(currentState).update(delay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neet.framework.state.GSM#draw(java.awt.Graphics2D)
	 */
	public void draw(Graphics2D g) {
		if (paused) {
			pauseState.draw(g);
			return;
		}
		if (activeStates.containsKey(currentState))
			activeStates.get(currentState).draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

}