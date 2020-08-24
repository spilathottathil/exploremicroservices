package com.sarath.work.explorems.web;


import com.sarath.work.explorems.domain.Tour;
import com.sarath.work.explorems.domain.TourRating;
import com.sarath.work.explorems.domain.TourRatingPk;
import com.sarath.work.explorems.repo.TourRatingRepository;
import com.sarath.work.explorems.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Tour Rating Controller
 *
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    @Autowired
    TourRatingRepository tourRatingRepository;

    @Autowired
    TourRepository tourRepository;

    protected TourRatingController() {

    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto){
        Tour tour =   verifyTour(tourId);
        TourRatingPk pk = new TourRatingPk(tour,ratingDto.getCustomerId());
        tourRatingRepository.save(new TourRating(pk,ratingDto.getScore(),ratingDto.getComment()));

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RatingDto> getAllTourRating(@PathVariable(value = "tourId") int tourId){
        verifyTour(tourId);
       return tourRatingRepository.findByPkTourId(tourId).stream().map( this::toDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET,path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getScoreAverage(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        List<TourRating> tourRatings =   tourRatingRepository.findByPkTourId(tourId);
     OptionalDouble average = tourRatings.stream().mapToInt(TourRating::getScore).average();
     return new AbstractMap.SimpleEntry<String,Double>("average", average.isPresent() ? average.getAsDouble() : null);
    }

    /**
     * Convert the TourRating entity to a RatingDto
     *
     * @param tourRating
     * @return RatingDto
     */
    private RatingDto toDto(TourRating tourRating) {
        return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
    }

    /**
     * Verify and return the TourRating for a particular tourId and Customer
     * @param tourId
     * @param customerId
     * @return the found TourRating
     * @throws NoSuchElementException if no TourRating found
     */
    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        TourRating rating = tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId);
        if (rating == null) {
            throw new NoSuchElementException("Tour-Rating pair for request("
                    + tourId + " for customer" + customerId);
        }
        return rating;
    }

    /**
     * Verify and return the Tour given a tourId.
     *
     * @param tourId
     * @return the found Tour
     * @throws NoSuchElementException if no Tour found.
     */
    private Tour verifyTour(int tourId) throws NoSuchElementException {
        Optional<Tour> tour = tourRepository.findById(tourId);
        if (!tour.isPresent()) {
            throw new NoSuchElementException("Tour does not exist " + tourId);
        }
        return tour.get();
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }

}
