package com.example.how_to_use_model_mapper.controller;

import com.example.how_to_use_model_mapper.dto.response.AttachResponse;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

	private final ModelMapper modelMapper;

	@GetMapping(value = "/")
	public String test1() {

		HashMap<String, Object> testMap = new HashMap<>();

		testMap.put("filename", "filename");
		testMap.put("savename", "savename");
		testMap.put("path", "path");
		testMap.put("path2", "path2");
		testMap.put("contentType", "contentType");
		testMap.put("size", 6L);
		testMap.put("size1", 7L);

		HashMap<String, Object> testMenuMap = new HashMap<>();
		AttachResponse response  = new AttachResponse();
		testMenuMap.put("name", "name"); //모두 충족 (같은이름, 같은형식)
		//testMenuMap.put("12name1", 22l); //LOOSE, STANDARD 충족 (다른 이름 + 기본적으로 변환가능한 형식 Long과 String )
		//testMenuMap.put("name", 21l); //모두 충족 (이름만 같고 + 기본적으로 변환가능한 형식 Long과 String)
		//testMap.put("menu", response); //모두 불가 이름만 같고 변환 불가능한 형식 객체)

		AttachResponse map = modelMapper.map(testMap, AttachResponse.class);

		ModelMapper modelMapperSTRICT = new ModelMapper();
		modelMapperSTRICT.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ModelMapper modelMapperLOOSE = new ModelMapper();
		modelMapperLOOSE.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		ModelMapper modelMapperSTANDARD = new ModelMapper();
		modelMapperSTANDARD.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);


		AttachResponse attachResponseStrict = modelMapperSTRICT.map(testMap, AttachResponse.class);
		AttachResponse attachResponseLoose = modelMapperLOOSE.map(testMap, AttachResponse.class);
		AttachResponse attachResponseStandard = modelMapperSTANDARD.map(testMap, AttachResponse.class);

		System.out.println(attachResponseStrict);
		System.out.println(attachResponseLoose);
		System.out.println(attachResponseStandard);

		return "test";
	}

	@GetMapping(value = "/xss")
	public String test() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String param = "<a href='/xss' onclick='location.href=\"/test\";l'>TEST</a><script>window.alert('TEST');</script><img src='http://localhost:8081/a/b/c.png'>";
		System.out.println("param = " + param);
		String xss = Jsoup.clean(param, Whitelist.basic());  //원하는 값 기본적으로 xss 필터
		String xss3 = Jsoup.clean(param, Whitelist.basicWithImages()); //원하는 값 중에 이미지를 빼고 xss
		System.out.println("xss = " + xss);
		System.out.println("xss3 = " + xss3);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		return "test";
	}
}
