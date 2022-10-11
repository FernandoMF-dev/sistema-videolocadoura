package br.com.ifes.videolocadora.service.repository;


import br.com.ifes.videolocadora.service.domain.entity.Classe;
import br.com.ifes.videolocadora.service.service.dto.ClasseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long>, JpaSpecificationExecutor<Classe> {

	@Query("select new br.com.ifes.videolocadora.service.service.dto.ClasseDTO(c.id,c.nome,c.valor,c.prazoDevolucao,c.excluido) from Classe c where  c.excluido = false")
	Page<ClasseDTO> findAllList(Pageable page);

	@Query("SELECT new br.com.ifes.videolocadora.service.service.dto.ClasseDTO(c.id,c.nome,c.valor,c.prazoDevolucao,c.excluido)" +
			" FROM Classe c " +
			" WHERE (c.excluido = false ) " +
			" AND (LOWER(c.nome) LIKE LOWER(CONCAT('%', COALESCE(:#{#filter.nome}, ''), '%'))) " +
			"AND (:#{#filter.valor} IS NULL OR c.valor = :#{#filter.valor})" +
			"AND (:#{#filter.prazoDevolucao} IS NULL OR c.prazoDevolucao = :#{#filter.prazoDevolucao})")
	Page<ClasseDTO> filtrar(@Param("filter") ClasseDTO filtro, Pageable pageable);

}