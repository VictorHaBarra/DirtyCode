package br.com.fiap.DirtyCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.DirtyCode.model.Usuario;
import br.com.fiap.DirtyCode.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/user")
public class UsuarioResourceThymeleaf {

    @Autowired
    private UsuarioRepository user;

    // Formulário para adicionar/editar usuário
    @GetMapping("/new")
    public ModelAndView usuarioForm() {
        ModelAndView modelAndView = new ModelAndView("usuario-form"); // Nome da view
        modelAndView.addObject("usuario", new Usuario());
        modelAndView.addObject("title", "Adicionar/Editar Usuário");
        return modelAndView;
    }


    // Salvar usuário no banco de dados
    @PostMapping("/save")
    public ModelAndView saveUsuario(@ModelAttribute Usuario usuario) {
        user.save(usuario);
        ModelAndView mv = new ModelAndView("redirect:/user/list");
        return mv;
    }


    // Listar todos os usuários
    @GetMapping("/list")
    public ModelAndView listUsuarios() {
        ModelAndView modelAndView = new ModelAndView("usuario-list"); // Nome da view
        modelAndView.addObject("usuarios", user.findAll());
        modelAndView.addObject("title", "Lista de Usuários");
        return modelAndView;
    }




    // Editar usuário
    @GetMapping("/edit/{id}")
    public ModelAndView editUsuario(@PathVariable("id") Long id) {
        Usuario usuario = user.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário inválido: " + id));
        ModelAndView modelAndView = new ModelAndView("usuario-form"); // Nome da view
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("title", "Editar Usuário");
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public ModelAndView deleteUsuario(@PathVariable("id") Long id) {
        user.deleteById(id);
        ModelAndView mView = new ModelAndView("redirect:/user/list");
        return mView;
    }

}
