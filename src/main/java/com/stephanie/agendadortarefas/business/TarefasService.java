package com.stephanie.agendadortarefas.business;

import com.stephanie.agendadortarefas.business.dto.TarefasDTO;
import com.stephanie.agendadortarefas.business.mapper.TarefasConverter;
import com.stephanie.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.stephanie.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.stephanie.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.stephanie.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefaConverter;
    private final JwtUtil jwtUtil;


    public TarefasDTO gravarTarefa(String token, TarefasDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity((dto));

        return tarefaConverter.paraTarefaDTO(tarefasRepository.save(entity));
    }


}
