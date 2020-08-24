package com.sarath.work.explorems.repo;

import com.sarath.work.explorems.domain.TourRating;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository {
    TourRating findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);

}
