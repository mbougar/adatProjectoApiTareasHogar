package com.es.adatProjectoApiTareasHogar.util

import com.es.adatProjectoApiTareasHogar.dto.UsuarioDTO
import com.es.adatProjectoApiTareasHogar.dto.UsuarioRegisterDTO
import com.es.adatProjectoApiTareasHogar.model.Direccion
import com.es.adatProjectoApiTareasHogar.model.Usuario
import org.springframework.security.crypto.password.PasswordEncoder

object DTOMapper {

    fun usuarioRegisteredDTOToEntity(usuarioInsertDTO: UsuarioRegisterDTO, passwordEncoder: PasswordEncoder) : Usuario {
        return Usuario(
            _id = null,
            username = usuarioInsertDTO.username,
            password = passwordEncoder.encode(usuarioInsertDTO.password),
            email = usuarioInsertDTO.email,
            roles = usuarioInsertDTO.rol ?: "",
            direccion = Direccion(
                calle = usuarioInsertDTO.calle,
                num = usuarioInsertDTO.num,
                municipio = usuarioInsertDTO.municipio,
                provincia = usuarioInsertDTO.provincia,
                cp = usuarioInsertDTO.cp,
                ciudad = usuarioInsertDTO.ciudad
            ),
        )
    }

    fun entityToUsuarioRegisteredDTO(usuario: Usuario) : UsuarioRegisterDTO {

        return UsuarioRegisterDTO(
            username = usuario.username,
            email = usuario.email,
            password = usuario.password,
            passwordRepeat = usuario.password,
            rol = usuario.roles,
            calle = usuario.direccion.calle,
            num = usuario.direccion.num,
            municipio = usuario.direccion.municipio,
            provincia = usuario.direccion.provincia,
            cp = usuario.direccion.cp,
            ciudad = usuario.direccion.ciudad,
        )
    }

    fun usuarioEntityToUsuarioDTO(usuario: Usuario) : UsuarioDTO {
        return UsuarioDTO(
            username = usuario.username,
            email = usuario.email,
            rol = usuario.roles
        )
    }
}