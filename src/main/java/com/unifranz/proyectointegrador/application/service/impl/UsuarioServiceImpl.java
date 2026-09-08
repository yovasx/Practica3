package com.unifranz.proyectointegrador.application.service.impl;

import com.unifranz.proyectointegrador.application.dto.UsuarioDto;
import com.unifranz.proyectointegrador.application.service.UsuarioService;
import com.unifranz.proyectointegrador.domain.Usuario;
import com.unifranz.proyectointegrador.infrastructure.persistence.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto guardar (UsuarioDto usuarioDto){
        Usuario usuario = new Usuario();
        // validacion
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        Usuario guardado =  usuarioRepository.save(usuario);
        return new UsuarioDto(guardado.getId(), guardado.getNombre(),guardado.getEmail());
    }

    @Override
    public List<UsuarioDto> listar(){
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(u -> new UsuarioDto(u.getId(),u.getNombre(), u.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarLogico(Long id){
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado o ya eliminado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}