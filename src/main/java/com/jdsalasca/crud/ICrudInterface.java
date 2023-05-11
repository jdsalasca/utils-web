package com.jdsalasca.crud;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.jdsalasca.defaultresponse.DefaultResponse;

/**

This interface defines a CRUD (Create, Read, Update, Delete) contract for generic entities using Spring Data JPA.

@param <T> the type of the entity

@param <K> the type of the DTO (Data Transfer Object) used to create or update the entity

@param <ID> the type of the entity identifier

@param <L> the type of the Spring Data JPA repository interface used to interact with the database

@author jdsalasca
*/
public interface ICrudInterface<T, K , ID extends Serializable, L extends JpaRepository<T, ID>>  {


	/**

    Retrieves all the entities of type T.
    @return a ResponseEntity with a DefaultResponse containing the list of entities, or an error message if an exception occurs
    */
   ResponseEntity<DefaultResponse<T>> getAll();
    /**

    Retrieves the entity of type T identified by the given ID.
    @param id the identifier of the entity
    @return a ResponseEntity with a DefaultResponse containing the entity, or an error message if an exception occurs
    */
    ResponseEntity<DefaultResponse<T>> getById(ID id);
    /**

    Saves a new entity of type T using the data contained in the provided DTO.
    @param dto the DTO containing the data for the new entity
    @param bindingResult the result of the data validation process
    @param entityClass the class of the entity being created
    @return a ResponseEntity with a DefaultResponse containing the newly created entity, or an error message if an exception occurs
    */
    ResponseEntity<DefaultResponse<T>> save(K dto, BindingResult bindingResult, Class<T> entityClass);
    /**

    Updates the entity of type T identified by the given ID using the data contained in the provided DTO.
    @param id the identifier of the entity being updated
    @param dto the DTO containing the updated data for the entity
    @param bindingResult the result of the data validation process
    @param entityClass the class of the entity being updated
    @return a ResponseEntity with a DefaultResponse containing the updated entity, or an error message if an exception occurs
    */
    ResponseEntity<DefaultResponse<T>> update( ID id, K entity, BindingResult bindigResult,  Class<T> entityClass);
    /**

    Deletes the entity of type T identified by the given ID.
    @param id the identifier of the entity being deleted
    */
    void deleteById(ID id);
   
    
}
