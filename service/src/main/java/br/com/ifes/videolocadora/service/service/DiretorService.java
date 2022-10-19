package br.com.ifes.videolocadora.service.service;

import br.com.ifes.videolocadora.service.domain.entity.Diretor;
import br.com.ifes.videolocadora.service.repository.DiretorRepository;
import br.com.ifes.videolocadora.service.service.dto.DiretorDTO;
import br.com.ifes.videolocadora.service.service.dto.SelectItemDTO;
import br.com.ifes.videolocadora.service.service.mapper.DiretorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiretorService {

	private final DiretorRepository repositorio;
	private final DiretorMapper mapper;

	public Diretor procurarPorId(Long id) {
		return repositorio.findById(id)
				.orElseThrow(() -> new RuntimeException("Diretor não encontrado"));
	}

	public DiretorDTO obterPorId(Long id) {
		return mapper.toDto(procurarPorId(id));
	}

	public DiretorDTO salvar(DiretorDTO dto) {
		Diretor entity = mapper.toEntity(dto);
		entity.setExcluido(false);
		return mapper.toDto(repositorio.save(entity));
	}

	public Page<DiretorDTO> obterTodos(Pageable page) {
		return repositorio.findAllList(page);
	}

	public DiretorDTO editar(Long id, DiretorDTO dto) {
		procurarPorId(id);
		return salvar(dto);
	}

	public void deletar(Long id) {
		Diretor entity = procurarPorId(id);
		entity.setExcluido(true);
		repositorio.save(entity);
	}

	public Page<DiretorDTO> filtrar(DiretorDTO dto, Pageable pageable) {
		return repositorio.filtrar(dto, pageable);
	}

	public List<SelectItemDTO> obterSelectItem() {
		return repositorio.obterSelectItem();
	}

}
