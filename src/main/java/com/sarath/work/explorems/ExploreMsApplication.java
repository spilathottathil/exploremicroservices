package com.sarath.work.explorems;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarath.work.explorems.domain.Difficulty;
import com.sarath.work.explorems.domain.Region;
import com.sarath.work.explorems.services.TourPackageService;
import com.sarath.work.explorems.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ExploreMsApplication implements CommandLineRunner {

	@Autowired
	private TourPackageService tourPackageService;

	@Autowired
	private TourService tourService;

	public static void main(String[] args) {
		SpringApplication.run(ExploreMsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Create the default tour packages
		tourPackageService.createTourPackage("BC", "Backpack Cal");
		tourPackageService.createTourPackage("CC", "California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");

		tourPackageService.lookUp().forEach(System.out::println);
		TourFromFile.importFromFile().forEach(t -> tourService.createTour(t.title,t.description,t.blurb,
				Integer.parseInt(t.price),t.length,t.bullets,t.keywords,t.packageType, Difficulty.valueOf(t.difficulty), Region.findByLabel(t.region)));
		System.out.println("total tours in DB :" + tourService.total());
	}

	static class TourFromFile{
		private String packageType,title,blurb,description,bullets,difficulty,length,price,region,keywords;

		static List<TourFromFile> importFromFile() throws IOException {
			return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).
					readValue(TourFromFile.class.getResourceAsStream("/ExploreCalifornia.json"),new TypeReference<List<TourFromFile>>(){});
		}
	}
}
