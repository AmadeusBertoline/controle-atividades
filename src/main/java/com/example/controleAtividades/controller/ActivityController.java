package com.example.controleAtividades.controller;

import com.example.controleAtividades.entity.Activity;
import com.example.controleAtividades.repository.ActivityRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ActivityController {

    private final ActivityRepository repository;

    public ActivityController(ActivityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listar(Model model) {

        model.addAttribute("atividades", repository.findAll());

        return "index";
    }

    @GetMapping("/nova")
    public String novaAtividade(Model model) {

        model.addAttribute("activity", new Activity());

        return "form";
    }

    @PostMapping("/salvar")
    public String salvar(
            @ModelAttribute Activity activity,
            @RequestParam(value = "arquivo", required = false)
            MultipartFile arquivo
    ) throws IOException {

        if (activity.getId() != null) {

            Activity atividadeBanco = repository.findById(activity.getId())
                    .orElseThrow();

            if (arquivo == null || arquivo.isEmpty()) {

                activity.setImagem(atividadeBanco.getImagem());

            } else {

                activity.setImagem(arquivo.getBytes());

            }

        } else {

            if (arquivo != null && !arquivo.isEmpty()) {

                activity.setImagem(arquivo.getBytes());

            }

        }

        repository.save(activity);

        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Activity activity = repository.findById(id)
                .orElseThrow();

        model.addAttribute("activity", activity);

        return "form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        repository.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/imagem/{id}")
    @ResponseBody
    public byte[] exibirImagem(@PathVariable Long id) {

        Activity activity = repository.findById(id)
                .orElseThrow();

        return activity.getImagem();
    }
}