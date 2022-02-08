package com.tv.series.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tv.series.model.TvSeries;

public interface TvSeriesRepository extends JpaRepository<TvSeries, Long> {

	TvSeries findByName(String imageName);

	TvSeries findTopByOrderByIdDesc();



}
