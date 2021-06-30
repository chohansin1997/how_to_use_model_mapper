package com.example.how_to_use_model_mapper.dto.response;

import lombok.Data;

@Data
public class AttachResponse {

	private String filename;

	private String path;

	private String contentType;

	private Long size;

	private NameResponse menu;

}
