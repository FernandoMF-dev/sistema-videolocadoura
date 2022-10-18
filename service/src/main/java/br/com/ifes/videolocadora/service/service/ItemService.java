package br.com.ifes.videolocadora.service.service;

import br.com.ifes.videolocadora.service.domain.entity.Item;
import br.com.ifes.videolocadora.service.repository.ItemRepository;
import br.com.ifes.videolocadora.service.service.dto.ItemDTO;
import br.com.ifes.videolocadora.service.service.dto.ItemListDTO;
import br.com.ifes.videolocadora.service.service.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository repositorio;
	private final ItemMapper mapper;

	public Item procurarPorId(Long id) {
		return repositorio.findById(id)
				.orElseThrow(() -> new RuntimeException("Item não encontrado"));
	}

	public ItemDTO obterPorId(Long id) {
		return mapper.toDto(procurarPorId(id));
	}

	public ItemDTO salvar(ItemDTO dto) {
		Item entity = mapper.toEntity(dto);
		entity.setExcluido(false);
		return mapper.toDto(repositorio.save(entity));
	}

	public Page<ItemListDTO> obterTodos(Pageable page) {
		return repositorio.findAllList(page);
	}

	public ItemDTO editar(Long id, ItemDTO dto) {
		procurarPorId(id);
		return salvar(dto);
	}

	public void deletar(Long id) {
		Item entity = procurarPorId(id);
		entity.setExcluido(true);
		repositorio.save(entity);
	}

	public Page<ItemListDTO> filtrar(ItemListDTO dto, Pageable page) {
		return repositorio.filtrar(dto, page);
	}
}
