package com.tv.series.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tv.series.model.TvSeries;
import com.tv.series.repository.TvSeriesRepository;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {
	
	@Autowired
	TvSeriesRepository repo;
	
@GetMapping("all")
public List<TvSeries> getTvseries() {
	List<TvSeries> allshows = repo.findAll();
	allshows.forEach(show->{
		if(show.getPicByte()!=null) {
		show.setPicByte(decompressBytes(show.getPicByte()));
	
		}});
	return allshows;
	
}
@PostMapping("save")
public TvSeries saveTvSeries(@RequestBody TvSeries tvSeries) {
	TvSeries idrec =  repo.findTopByOrderByIdDesc();
if(idrec!=null) {
	System.out.println(idrec.getId());
if(idrec.getActor()==null && idrec.getPicByte() !=null) {	
	idrec.setActor(tvSeries.getActor());
	idrec.setGenre(tvSeries.getGenre());
	idrec.setReleaseDate(tvSeries.getReleaseDate());
	idrec.setTvShowName(tvSeries.getTvShowName());
	idrec.setNumberOfSeasons(tvSeries.getNumberOfSeasons());
	return repo.save(idrec);
	}
	}
return repo.save(tvSeries);
}

@GetMapping("{id}")
public Optional<TvSeries> GetTvShow(@PathVariable long id) {
	 Optional<TvSeries> show = repo.findById(id);
	 TvSeries one =  show.get();
	 one.setPicByte(decompressBytes(one.getPicByte()));
	return repo.findById(id);
	
}

@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
public void DeleteTvShow(@PathVariable long id) {
	repo.deleteById(id);
}

@PostMapping("/upload")
   public BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

	 
       System.out.println("Original Image Byte Size - " + file.getBytes().length);
       TvSeries img = new TvSeries();
       img.setName(file.getOriginalFilename());
       img.setPicByte(compressBytes(file.getBytes()));
       img.setType(file.getContentType());
		/*
		 * TvSeries img = new TvSeries(0, file.getOriginalFilename(),
		 * file.getContentType(),
		 * 
		 * null, null, null, null, null, null, null, compressBytes(file.getBytes()));
		 */
       repo.save(img);

       return ResponseEntity.status(HttpStatus.OK);

   }

	
	 
	 

   public static byte[] compressBytes(byte[] data) {

       Deflater deflater = new Deflater();

       deflater.setInput(data);

       deflater.finish();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

       byte[] buffer = new byte[1024];

       while (!deflater.finished()) {

           int count = deflater.deflate(buffer);
        outputStream.write(buffer, 0, count);

       }
   try {

           outputStream.close();

       } catch (IOException e) {

       }

       System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

       return outputStream.toByteArray();

   }

   // uncompress the image bytes before returning it to the angular application

	
	  public static byte[] decompressBytes(byte[] data) {
	  
	  Inflater inflater = new Inflater();
	  
	  inflater.setInput(data);
	  
	  ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	  
	  byte[] buffer = new byte[1024];
	  
	  try {
	  
	  while (!inflater.finished()) {
	  
	  int count = inflater.inflate(buffer);
	  
	  outputStream.write(buffer, 0, count);
	  
	  }
	  
	  outputStream.close();
	  
	  } catch (IOException ioe) { } catch (DataFormatException e) { } return
	  outputStream.toByteArray(); }
	 

}


