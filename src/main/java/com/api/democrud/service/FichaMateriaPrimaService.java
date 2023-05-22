package com.api.democrud.service;

import com.api.democrud.dto.FichaMateriaPrimaDto;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.model.FichaMateriaPrima;
import com.api.democrud.repositorie.FichaMateriaPrimaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FichaMateriaPrimaService {

    private final FichaMateriaPrimaRepository fichaMateriaPrimaRepository;

    public FichaMateriaPrima salvar(FichaMateriaPrimaDto fichaMateriaPrimaDto){

        FichaMateriaPrima fichaMateriaPrima = new FichaMateriaPrima();
        BeanUtils.copyProperties(fichaMateriaPrimaDto,fichaMateriaPrima);

        return fichaMateriaPrimaRepository.save(fichaMateriaPrima);
    }

    public FichaMateriaPrima editar(UUID id, FichaMateriaPrimaDto fichaMateriaPrimaDto){

        try{
            FichaMateriaPrima fichaMateriaPrima = fichaMateriaPrimaRepository.findById(id).get();
            fichaMateriaPrima.setId_materia(fichaMateriaPrimaDto.getId_materia());
            fichaMateriaPrima.setId_produto(fichaMateriaPrimaDto.getId_produto());
            return fichaMateriaPrimaRepository.save(fichaMateriaPrima);
        }
        catch (NoSuchElementException e)
        {
            throw new ElementoNencontradoException("Elemento não encontrado");
        }

    }

    public List<FichaMateriaPrima> buscaTodos(){
        return fichaMateriaPrimaRepository.findAll();
    }

    public Optional<FichaMateriaPrima> busca(UUID id) {
        return fichaMateriaPrimaRepository.findById(id);
    }

}
