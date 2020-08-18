package com.sarath.work.explorems.services;

import com.sarath.work.explorems.domain.TourPackage;
import com.sarath.work.explorems.repo.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourPackageService {

    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    public TourPackage createTourPackage(String code, String name){
        if(!tourPackageRepository.existsById(code)){
            tourPackageRepository.save(new TourPackage(code,name));
        }
        return null;
    }

    public Iterable<TourPackage> lookUp(){
      return   tourPackageRepository.findAll();
    }

    public long total(){
        return tourPackageRepository.count();
    }
}
