package com.maxdeveloper.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.maxdeveloper.cursomc.domain.Categoria;
import com.maxdeveloper.cursomc.dto.CategoriaDTO;
import com.maxdeveloper.cursomc.repositories.CategoriaRepository;
import com.maxdeveloper.cursomc.services.excepion.DataIntegrityException;
import com.maxdeveloper.cursomc.services.excepion.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}
	
	//Insert
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	//Update
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	//Deletar
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
			
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível excluir uma categoria associada a produtos.");
		}
		
	}
	//Busca todas as categorias
	public List<Categoria> findAll(){
		return repo.findAll();
		}
	
	/* Busca as categorias paginando definindo a pagina para retornar, page, quantidade de linhas na pagina, linesPerPage, sua ordenação crescente ou
	 * decrescente, direction,
	*/
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}

}
