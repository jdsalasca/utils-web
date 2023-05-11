package com.jdsalasca.crud;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.jdsalasca.defaultresponse.DefaultResponse;
import com.jdsalasca.defaultresponse.DefaultResponse.DEFAULTMESSAGES;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;


/**

A abstract class that provides CRUD operations for a given entity.

It implements the {@link com.jdsk.people.interfaces.ICrudInterface} interface.

@param <T> the entity type to perform CRUD operations on

@param <K> the data transfer object (DTO) type used to receive or send data

@param <ID> the type of the entity's id

@param <R> the repository that will be used to access the entity data
*/
@Slf4j
public abstract class CrudServiceImpl<T, K, ID extends Serializable, R extends JpaRepository<T, ID>>
implements ICrudInterface<T, K, ID, R> {

	@Autowired
	private R repository;
	@Autowired
	private ModelMapper modelMapper;
    
    /**

    Returns all entities of the given type.
    @return a {@link org.springframework.http.ResponseEntity} containing the response of the request.
    The response will be a {@link com.jdsk.people.utils.response.DefaultResponse} with status 200 and
    the list of entities found in the database, or a {@link com.jdsk.people.utils.response.DefaultResponse}
    with status 404 if there are no entities of the given type in the database.
    */
    @Override 
    public ResponseEntity<DefaultResponse<T>> getAll() {
    	List<T> entities  = repository.findAll();
    	if (entities.isEmpty()) {
    		return DefaultResponse.onThrow404Response(DEFAULTMESSAGES.NOT_INFO_FOUND_MESSAGE.value());
    	}else {
    		return DefaultResponse.onThrow200Response(entities);
    	}
    }

/**

Returns the entity with the given id.
@param id the id of the entity to retrieve
@return a {@link org.springframework.http.ResponseEntity} containing the response of the request.
The response will be a {@link com.jdsk.people.utils.response.DefaultResponse} with status 200 and
the entity found in the database, or a {@link com.jdsk.people.utils.response.DefaultResponse} with status 404
if no entity with the given id exists in the database.
*/
    @Override
    public ResponseEntity<DefaultResponse<T>> getById(ID id) {
    	Optional<T> entity  = repository.findById(id);
    	if (entity.isEmpty()) {
    		return DefaultResponse.onThrow404Response(DEFAULTMESSAGES.NOT_INFO_FOUND_MESSAGE.value());
    	}else {
    		
    		return DefaultResponse.onThrow200Response(List.of(entity.get()));
    	}	
    }
    
	@Override
	public ResponseEntity<DefaultResponse<T>> update(  ID id, K dto, BindingResult bindigResult,  Class<T> entityClass) {
        if (bindigResult.hasErrors()) {
            return DefaultResponse.onThrow400ResponseBindingResult(bindigResult);
        }
        try {
        	T entity = repository.findById(id).orElseThrow( () -> new EntityNotFoundException(DEFAULTMESSAGES.NOT_INFO_FOUND_MESSAGE.value()));
        	modelMapper.map(dto, entity);
            
            return DefaultResponse.onThrow200Response( List.of(repository.save(entity)));
		} catch (DataIntegrityViolationException e) {
			log.info("error creating entity for {} because of {}", entityClass ,e.getLocalizedMessage());
			return DefaultResponse.onThrow400ResponseTypeInfo(e.getMostSpecificCause().getLocalizedMessage());
		}catch (ConstraintViolationException constraintViolationException) {
            return DefaultResponse.onThrow400ResponseTypeError(List.of(constraintViolationException.getMessage()).toString());
	        
		}catch (EntityNotFoundException e) {
			return DefaultResponse.onThrow400ResponseTypeInfo(e.getLocalizedMessage());
	        
		}catch (Exception e) {
			log.info("IMPORTANT unhandle exception {}", e.getLocalizedMessage());
			log.info("error creating entity for {} because of " + e.getLocalizedMessage(), entityClass);
			return DefaultResponse.onThrow400ResponseTypeInfo(e.getLocalizedMessage());
		}
	}

    /**

    Saves a new entity to the database.
    @param dto the DTO that contains the data to create the entity
    @param bindingResult the result of the validation process
    @param entityClass the class of the entity to create
    @return a {@link org.springframework.http.ResponseEntity} containing the response of the request.
    The response will be a {@link com.jdsk.people.utils.response.DefaultResponse} with status 200 and the created entity,
    or a {@link com.jdsk.people.utils.response.DefaultResponse} with status 400 if there is an error in the request body,
    or a {@link com.jdsk.people.utils.response.DefaultResponse} with status 400 if there is a data integrity violation in the database.
    */
    @Override
    public ResponseEntity<DefaultResponse<T>> save(K dto, BindingResult bindingResult,  Class<T> entityClass) {
        if (bindingResult.hasErrors()) {
            return DefaultResponse.onThrow400ResponseBindingResult(bindingResult);
        }
        try {
            T entity = modelMapper.map(dto, entityClass);
            
            
            return DefaultResponse.onThrow200Response( List.of(repository.save(entity)));
		} catch (DataIntegrityViolationException e) {
			log.info("error creating entity for {} because ofe " + e.getLocalizedMessage(), entityClass);
			return DefaultResponse.onThrow400ResponseTypeInfo(e.getMostSpecificCause().getMessage());
			
		}catch (ConstraintViolationException constraintViolationException) {
	            return DefaultResponse.onThrow400ResponseTypeError(List.of(constraintViolationException.getMessage()).toString());
	        
		}catch (Exception e) {
			log.info("IMPORTANT unhandle exception {}", e.getLocalizedMessage());
			log.info("error creating entity for {} because of " + e.getLocalizedMessage(), entityClass);
			return DefaultResponse.onThrow400ResponseTypeInfo(e.getLocalizedMessage());
		}

    }
    /**
     * Updates an existing entity by ID with the provided DTO. 
     * If the provided BindingResult contains errors, it returns a 400 response with the errors.
     * 
     * @param id          the ID of the entity to update
     * @param dto         the DTO containing the updated entity information
     * @param bindingResult the binding result to validate the DTO
     * @param entityClass the class of the entity being updated
     * @return a ResponseEntity containing the updated entity as a body with a 200 status code, 
     * or a 400 response with an error message if there was an issue with the DTO or the update process.
     */

	@Override
	public void deleteById(ID id) {
		
		//repository.deleteById(id);
	}
    }
