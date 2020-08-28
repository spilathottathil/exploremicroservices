package com.sarath.work.explorems.repo;

import com.sarath.work.explorems.domain.TourRating;
import com.sarath.work.explorems.domain.TourRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {
    TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);
    List<TourRating> findByPkTourId(Integer tourId);
    Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable);
}
