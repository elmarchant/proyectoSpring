package m07uf2.proyecto.controller;

import m07uf2.proyecto.model.Game;
import m07uf2.proyecto.model.Message;
import m07uf2.proyecto.model.Usuario;
import m07uf2.proyecto.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    private ArrayList<Game> games = new ArrayList<Game>();

    @Autowired
    private UserRepo repository;

    @GetMapping("/game/{index}")
    public String game(@PathVariable(required = true) int index, Model model, Principal principal){
        String view = "game/game";
        int player = 0;
        Message message = new Message();

        Usuario user = repository.findByUsername(principal.getName());

        if(user.getId() == this.games.get(index).getPlayer1() || user.getId() == this.games.get(index).getPlayer2()){
            if(user.getId() == this.games.get(index).getPlayer1()) player = 1;
            else if(user.getId() == this.games.get(index).getPlayer2()) player = 2;

            if(this.games.get(index).getStatus()){
                if(this.games.get(index).getTurn() == player){
                    model.addAttribute("game", this.games.get(index));
                    model.addAttribute("index", index);
                    model.addAttribute("player", player);
                }else{
                    message.setAllMessage(
                            "Espera",
                            "Aún no es tu turno. Espera a que tu oponente pierda su turno.",
                            "/main"
                    );

                    model.addAttribute("message", message);

                    view = "user/status";
                }
            }else{
                String description = "";

                if(this.games.get(index).getWinner() == player){
                    description = "Has ganado la partida.";
                }else{
                    description = "Has perdido la partida.";
                }

                message.setAllMessage(
                        "Estado de partida",
                        description,
                        "/main"
                );

                model.addAttribute("message", message);

                view = "user/status";
            }
        }else{
            message.setAllMessage(
                    "Error",
                    "Este no es tu partida.",
                    "/main"
            );

            model.addAttribute("message", message);

            view = "user/status";
        }

        return view;
    }

    @PostMapping("/game/{index}/play")
    public String play(
            @PathVariable(required = true) int index,
            @RequestParam(required = true) int x,
            @RequestParam(required = true) int y,
            Model model,
            Principal principal
    ){
        String view = "redirect:/game/"+index;
        int player = 0;
        Message message = new Message();

        Usuario user = repository.findByUsername(principal.getName());

        if(user.getId() == this.games.get(index).getPlayer1() || user.getId() == this.games.get(index).getPlayer2()){
            if(user.getId() == this.games.get(index).getPlayer1()) player = 1;
            else if(user.getId() == this.games.get(index).getPlayer2()) player = 2;

            if(this.games.get(index).getStatus()){
                if(this.games.get(index).getTurn() == player){
                    Game game = this.games.get(index);

                    if(player == 1) game.attackTable2(x, y);
                    else if(player == 2) game.attackTable1(x, y);

                    game.analizeGame();

                    this.games.set(index, game);
                }else{
                    message.setAllMessage(
                            "Espera",
                            "Aún no es tu turno. Espera a que tu oponente pierda su turno.",
                            "/main"
                    );

                    model.addAttribute("message", message);

                    view = "user/status";
                }
            }else{
                String description = "";

                if(this.games.get(index).getWinner() == player){
                    description = "Has ganado la partida.";
                }else{
                    description = "Has perdido la partida.";
                }

                message.setAllMessage(
                        "Estado de partida",
                        description,
                        "/main"
                );

                model.addAttribute("message", message);

                view = "user/status";
            }
        }else{
            message.setAllMessage(
                    "Error",
                    "Este no es tu partida.",
                    "/main"
            );

            model.addAttribute("message", message);

            view = "user/status";
        }

        return view;
    }



    @GetMapping("/game/create")
    public String create(Principal principal, Model model){
        Usuario player = repository.findByUsername(principal.getName());
        List<Usuario> users = repository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("player", player);

        return "game/create";
    }

    @PostMapping("/game/create/save")
    public String save(@RequestParam(required = true) int rival, @RequestParam(required = true) String name,Principal principal, Model model){
        int id = games.size();

        Usuario player1 = repository.findByUsername(principal.getName());
        Usuario player2 = repository.findById(rival).get();
        Game game = new Game(name, id);
        game.setPlayer1(player1.getId());
        game.setPlayer2(player2.getId());

        games.add(game);

        return "redirect:/game/"+id;
    }

    @GetMapping("/game/get")
    public @ResponseBody ArrayList<Game> getGame(Principal principal){
        Usuario user = repository.findByUsername(principal.getName());
        ArrayList<Game> myGames = new ArrayList<Game>();

        for(int i=0; i<games.size(); i++){
            if(user.getId() == games.get(i).getPlayer1()
                    || user.getId() == games.get(i).getPlayer2()
            )myGames.add(games.get(i));
        }

        return myGames;
    }
}
