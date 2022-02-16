package org.pisey.betterreads;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.codahale.metrics.EWMA;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.pisey.betterreads.author.Author;
import org.pisey.betterreads.author.AuthorRepository;
import org.pisey.betterreads.book.Book;
import org.pisey.betterreads.connection.DataStaxAstraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
@EnableCassandraRepositories
public class BetterreadsApplication {

	@Autowired
	AuthorRepository authorRepository;

	@Value("${datadump.location.author}")
	private String authorDumpLocation;
	@Value("${datadump.location.work}")
	private String workDumpLocation;

	public static void main(String[] args) {
		SpringApplication.run(BetterreadsApplication.class, args);
	}

	// private void initWorks() {
	// 	Path path = Paths.get(workDumpLocation);
	// 	try (Stream<String> lines = Files.lines(path,StandardCharsets.UTF_8)) {
	// 		lines.forEach(line -> {
	// 			String jsonString = line.substring(line.indexOf("{"));
	// 			try {
	// 				JSONObject jsonObject = new JSONObject(jsonString);
	// 				Book book = new Book();
	// 				book.setId();
	// 				book.setName(jsonObject.optString("title"));
	// 				JSONObject descriptionObj = jsonObject.optJSONObject("description");
	// 				if(descriptionObj != null){
	// 					book.setDescription(descriptionObj.optString("value"));
	// 				}
	// 				book.setCoverIds();
	// 				book.setAuthorNames();
	// 				book.setAuthorIds();   
	// 			} catch (Exception e) {
	// 				//TODO: handle exception
	// 			}
	// 		});
	// 	} catch (Exception e) {
	// 		//TODO: handle exception
	// 	}
	// }

	private void initAuthors() {
		Path path = Paths.get(authorDumpLocation);
		try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
			lines.forEach(line -> {
				String jsonString = line.substring(line.indexOf("{"));
				try {

					JSONObject jsonObject = new JSONObject(jsonString);

					Author author = new Author();
					author.setName(jsonObject.optString("name"));
					author.setPersonalName(jsonObject.optString("personal_name"));
					author.setId(jsonObject.optString("key").replace("/a/", "").replace("/authors/", ""));

					authorRepository.save(author);
					System.out.println(author.getName() + "...");
				} catch (JSONException exception) {
					exception.printStackTrace();
				}
			});
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@PostConstruct
	public void start() {
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();

		return Builder -> Builder.withCloudSecureConnectBundle(bundle);
	}

}
