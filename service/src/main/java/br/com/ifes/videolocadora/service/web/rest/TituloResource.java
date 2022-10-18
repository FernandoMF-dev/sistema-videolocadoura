package br.com.ifes.videolocadora.service.web.rest;


import br.com.ifes.videolocadora.service.service.TituloService;
import br.com.ifes.videolocadora.service.service.dto.TituloDTO;
import br.com.ifes.videolocadora.service.service.dto.TituloListDTO;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/titulo")
@RequiredArgsConstructor
@Slf4j
public class TituloResource {
	private final TituloService servico;

	@GetMapping("/{id}")
	@Timed
	public ResponseEntity<TituloDTO> obter(@PathVariable Long id) {
		return ResponseEntity.ok().body(servico.obterPorId(id));
	}

	@GetMapping
	@Timed
	public ResponseEntity<Page<TituloListDTO>> obterTodos(Pageable page) {
		return ResponseEntity.ok().body(servico.obterTodos(page));
	}

	@PostMapping()
	@Timed
	public ResponseEntity<TituloDTO> salvar(@RequestBody TituloDTO dto) {
		return ResponseEntity.ok().body(servico.salvar(dto));
	}
}

