package com.example.how_to_use_model_mapper.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	private final ModelMapper modelMapper = new ModelMapper();

	@Bean
	public ModelMapper modelMapper() {
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull())
				.setMatchingStrategy(MatchingStrategies.STRICT);

		//.setPropertyCondition(Conditions.isNotNull()) 널인 값들은 복사 X
		return modelMapper;
	}
}
