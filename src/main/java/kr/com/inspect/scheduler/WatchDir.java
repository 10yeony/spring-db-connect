package kr.com.inspect.scheduler;

import kr.com.inspect.service.PostgreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 디렉토리 감시를 위한 스케쥴러
 * 
 * @author Woo Young
 * @version 1.0
 *
 */
@Component
@PropertySource(value = "classpath:properties/directory.properties")
public class WatchDir {

	/**
	 * 로그 출력을 위한 logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(WatchDir.class);

	/**
	 * 감시할 디렉토리 경로<br>
	 * 이곳에서 uploadJson 디렉토리로 복사된다.
	 */
	@Value("${input.json.directory}")
	private String pathFrom;

	/**
	 * json 디렉토리의 파일이 저장되는 디렉토리 경로<br>
	 * 파일이 디비에 저장되는 디렉토리
	 */
	@Value("${input.uploadJson.directory}")
	public String pathTo;

	/**
	 * DB에 저장된 모든 json 파일을 저장할 경로
	 */
	@Value("${input.jsonStorage.directory}")
	public String pathStorage;

	/**
	 * PostgreSQL 서비스 필드 선언
	 */
	@Autowired
	private PostgreService postgreService;

	/**
	 * 10분마다 디렉토리를 감시하며 json 파일을 DB에 파싱
	 * 
	 * @throws Exception 예외처리
	 */
	@Scheduled(fixedDelay = 600000)
	public void watchDir() throws Exception {
		File dirFrom = new File(pathFrom);
		File dirTo = new File(pathTo);
		File dirStor = new File(pathStorage);
		File[] fileList = dirFrom.listFiles();

		/* json 디렉토리에서 uploadJson 디렉토리와 jsonStorage 디렉토리로 파일 복사 */
		for (File file : fileList) {
			File temp = new File(dirTo.getAbsolutePath() + File.separator + file.getName());
			File tempS = new File(dirStor.getAbsolutePath() + File.separator + file.getName());

			FileInputStream fis = null;
			FileOutputStream fos = null;
			FileOutputStream fosS = null;

			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(temp);
				fosS = new FileOutputStream(tempS);
				byte[] b = new byte[4096];
				int cnt = 0;
				while ((cnt = fis.read(b)) != -1) {
					fos.write(b, 0, cnt);
					fosS.write(b, 0, cnt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					fos.close();
					fosS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file.delete();
		}

		/* uploadJson 디렉토리안의 파일을 파싱하고 삭제 */
		postgreService.insertJSONDir(pathTo);
	}
}
