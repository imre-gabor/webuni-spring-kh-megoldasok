package hu.webuni.university.repository;

import java.util.List;

import com.querydsl.core.types.Predicate;

public interface QuerydslWithEntityGraphRepository<T, ID> {

	List<T> findAll(Predicate predicate, String entityGraphName);

}