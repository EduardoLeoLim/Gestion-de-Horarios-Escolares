package com.gamma.gestorhorariosescolares.secretario.aplicacion;

import java.util.List;

public class SecretariosData {

    private final List<SecretarioData> secretarios;

    public SecretariosData(List<SecretarioData> secretarios) {
        this.secretarios = secretarios;
    }

    public List<SecretarioData> secretarios() {
        return secretarios;
    }
}
