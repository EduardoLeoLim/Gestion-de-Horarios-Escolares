package com.gamma.gestorhorariosescolares.usuario.aplicacion.registrar;

import com.gamma.gestorhorariosescolares.compartido.aplicacion.excepciones.FormatoInvalidoException;
import com.gamma.gestorhorariosescolares.usuario.dominio.Usuario;
import com.gamma.gestorhorariosescolares.usuario.dominio.UsuarioRepositorio;

import java.util.regex.Pattern;

public class RegistradorUsuario implements ServicioRegistradorUsuario {

    public final UsuarioRepositorio repositorio;

    public RegistradorUsuario(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public int registrar(String telefono, String correoElectronico, String claveAcceso, String tipo) throws FormatoInvalidoException {
        if (!esValidoCorreoElectronico(correoElectronico))
            throw new FormatoInvalidoException("El formato del correo electrónico es inválido.");
        if (!esValidoTelefono(telefono))
            throw new FormatoInvalidoException("El formato del número telefónico es inválido.");

        Usuario nuevoUsuario = new Usuario(telefono, correoElectronico, claveAcceso, tipo);
        return repositorio.registrar(nuevoUsuario);
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
