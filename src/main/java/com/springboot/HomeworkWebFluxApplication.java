package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

// R2DBC 사용 위해
@EnableR2dbcRepositories
// 데이터베이스에 엔티티가 저장 및 수정될 때, 생성날자와 수정날짜를 자동으로 저장할 수 있도록
// Auditing 기능을 사용.
@EnableR2dbcAuditing
@SpringBootApplication
public class HomeworkWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkWebFluxApplication.class, args);
	}

}
