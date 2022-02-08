package com.tv.series.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tv_series",schema="public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TvSeries {
	
@Id
@Column(name="id")
@GeneratedValue(strategy= GenerationType.AUTO)
private long id;	

@Column(name="actor")
private String actor;

@Column(name="tv_show_name")
private String tvShowName;

@Column(name="genre")		
private String genre;


@Column(name="number_of_seasons")
private String numberOfSeasons;

@Column(name="release_date")
private LocalDate releaseDate;

@Column(name = "name")
private String name;

@Column(name = "type")
private String type;

//image bytes can have large lengths so we specify a value
//which is more than the default length for picByte column
@Column(name = "picByte", length = 1000)
private byte[] picByte;

}
