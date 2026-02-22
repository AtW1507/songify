package com.songify.infrastructure.crud.song.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    private Map<Integer, String> dataBase = new HashMap<>();

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/view/songs")
    public String songs(Model model){
        dataBase.put(1, "Song 1");
        dataBase.put(2, "Song 2");
        dataBase.put(3, "Song 3");
        dataBase.put(4, "Song 4");

        model.addAttribute("songMap", dataBase);

        return "songs";
    }
}
