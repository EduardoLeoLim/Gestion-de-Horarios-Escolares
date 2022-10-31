package com.gamma.gestorhorariosescolares.usuario.aplicacion.actualizar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;

import java.util.regex.Pattern;

public class ActualizadorUsuario implements ServicioActualizadorUsuario {

    private final UsuarioRepositorio repositorio;

    public ActualizadorUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizar(Usuario usuario) throws FormatoInvalidoException {
        if (!esValidoCorreoElectronico(usuario.correoElectronico()))
            throw new FormatoInvalidoException("Correo electronico inválido", "El formato del correo electrónico es inválido.");
        if (!esValidoTelefono(usuario.telefono()))
            throw new FormatoInvalidoException("Teléfono inválido", "El formato del número telefónico es inválido.");

        repositorio.actualizar(usuario);
    }

    private boolean esValidoCorreoElectronico(String correoElectronico) {
        //Expresión regular para correo electrónico según RFC 5322
        String regex = "^((?:[A-Za-z0-9!#$%&'*+\\-\\/=?^_`{|}~]|(?<=^|\\.)\"|\"(?=$|\\.|@)|(?<=\".*)[ .](?=.*\")" +
                "|(?<!\\.)\\.){1,64})(@)((?:[A-Za-z0-9.\\-])*(?:[A-Za-z0-9])\\.(?:[A-Za-z0-9]){2,})$";
        Pattern patron = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return patron.matcher(correoElectronico).matches();
    }

    private boolean esValidoTelefono(String telefono) {
        //Expresión regular para número de 10 dígitos
        String regex = "^[0-9]{10}$";
        Pattern patron = Pattern.compile(regex);
        return patron.matcher(telefono).matches();
    }
}
