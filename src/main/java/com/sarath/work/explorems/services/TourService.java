package com.sarath.work.explorems.services;

import com.sarath.work.explorems.domain.Difficulty;
import com.sarath.work.explorems.domain.Region;
import com.sarath.work.explorems.domain.Tour;
import com.sarath.work.explorems.domain.TourPackage;
import com.sarath.work.explorems.repo.TourPackageRepository;
import com.sarath.work.explorems.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    private TourPackageRepository tourPackageRepository;
    private TourRepository tourRepository;

    @Autowired
    public TourService(TourPackageRepository tourPackageRepository, TourRepository tourRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    public Tour createTour(String title, String description, String blurb, Integer price, String duration, String bullets,
                           String keywords, String tourPackageCode, Difficulty difficulty, Region region){

        TourPackage tPackage = tourPackageRepository.findByName(tourPackageCode).
                orElseThrow(() -> new RuntimeException("Tour package does not exist " + tourPackageCode));

       return tourRepository.save(new Tour(title,description,blurb,price,duration,bullets,keywords,tPackage,difficulty,region));
    }

    public Iterable<Tour> lookUp(){
        return tourRepository.findAll();
    }

    public long total(){
        return tourRepository.count();
    }
}
