package com.demo.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.game.model.Game;
import com.demo.game.service.api.GameService;

@RestController
@RequestMapping("/games")
public class GamesController {
	
	@Autowired
	private GameService gameService;

    @Autowired
    private CounterService counterService;


	@RequestMapping(method=RequestMethod.GET)
	public Iterable<Game> listGames() {
		
		return gameService.listGames();
	}

	
	@RequestMapping(method=RequestMethod.POST)
	public Game createGame(@RequestParam("p") String playerName) {
		
		this.counterService.increment("com.demo.gamecontroller.invoked");
		return gameService.startGame(playerName);
        
	}

	
	@RequestMapping(value="{gameId}", method=RequestMethod.GET)
	public Game getGame(@PathVariable("gameId") long gameId) {
		
		return gameService.getGame(gameId);
	}
	
	@RequestMapping(value="{gameId}", method=RequestMethod.DELETE)
	public void deleteGame(@PathVariable("gameId") long gameId) {
		gameService.deleteGame(gameId);
	}
	
	
	@RequestMapping(value="{gameId}", method=RequestMethod.POST)
	public Game moveJoin(
			@PathVariable("gameId") long gameId, 
			@RequestParam("p") String playerName,
			@RequestParam(name="m", required=false) String position ) {
		
		if( position == null ) {
			return gameService.joinGame(gameId, playerName);
		}
		
		return gameService.makeMove(gameId, playerName, position);

	}
	    
}