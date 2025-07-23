package com.ecommerce.ms_empresa.factories;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.enums.UnidadeFederativa;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmpresaFactory {

    public static EmpresaModel criarEmpresaModel() {
        EmpresaModel empresaModel = new EmpresaModel();

        empresaModel.setId(UUID.fromString("3ef38163-8438-43f2-847b-200e5e01b78f"));
        empresaModel.setCnpj("76427451000171");
        empresaModel.setInscricaoEstadual("131596235583");
        empresaModel.setRazaoSocial("Wayne Enterprises");
        empresaModel.setFantasia("Wayne Enterprises");
        empresaModel.setTelefone("11912345678");
        empresaModel.setEmail("waye.enterprises@email.com");
        empresaModel.setCep("01310930");
        empresaModel.setLogradouro("Av. Paulista");
        empresaModel.setNumero(1000);
        empresaModel.setComplemento("");
        empresaModel.setBairro("Centro");
        empresaModel.setCidade("São Paulo");
        empresaModel.setUf(UnidadeFederativa.SP);
        empresaModel.setCpfPresidente("90434517038");
        empresaModel.setNomePresidente("Bruce Wayne");
        empresaModel.setDtCriacao(LocalDateTime.of(2025,03,01,7,00));
        empresaModel.setDtUltAlteracao(LocalDateTime.of(2025,03,01,7,00));
        empresaModel.setIdUsuarioUltAlteracao(UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f"));

        return empresaModel;
    }

    public static EmpresaDTO criarEmpresaValidaDTO() {
        EmpresaDTO empresaDTO = new EmpresaDTO();

        BeanUtils.copyProperties(criarEmpresaModel(), empresaDTO);
        empresaDTO.setId(null);

        return empresaDTO;
    }
}
