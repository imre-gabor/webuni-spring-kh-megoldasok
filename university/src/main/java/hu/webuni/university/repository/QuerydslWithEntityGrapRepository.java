package hu.webuni.university.repository;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Predicate;

public interface QuerydslWithEntityGrapRepository<T> {

	List<T> findAll(Predicate predicate, String entityGraphName, Sort sort);
	
}
