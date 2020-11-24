package kr.com.inspect.scheduler;

import kr.com.inspect.service.PostgreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 디렉토리 감시를 위한 스케쥴러
 * @author Woo Young
 * @version 1.0
 *
 */
@Component
@PropertySource(value = "classpath:properties/directory.properties")
public class WatchDir {

    /**
     * 감시할 디렉토리 경로
     */
    @Value("${input.json.directory}")
    private String jsonPath;

    /**
     * PostgreSQL 서비스 필드 선언
     */
    @Autowired
    private PostgreService postgreService;

    /**
     * 10분마다 디렉토리를 감시하며 json 파일을 DB에 파싱
     * @throws Exception 예외처리
     */
    @Scheduled(fixedDelay = 3000)
    public void watchDir() throws Exception {
//        WatchService watchService = FileSystems.getDefault().newWatchService();
//
//        Path path = Paths.get(jsonPath);
//        path.register(watchService,
//                StandardWatchEventKinds.ENTRY_CREATE,
//                StandardWatchEventKinds.ENTRY_DELETE,
//                StandardWatchEventKinds.ENTRY_MODIFY);

        String day = new SimpleDateFormat("yyyy-MM-dd-ss").format(new Date());

        System.out.println(day);

//        watchKey = watchService.take();
//
//        List<WatchEvent<?>> events = watchKey.pollEvents();
//
//        System.out.println(events.size());


//        File dir = new File(path);
//        File[] fileList = dir.listFiles();
//        for(File file : fileList){
//            System.out.println(file.getName());
//        }
        // 디렉토리에 변화가 있으면 감지하는 코드
//        new Thread(()-> {
//            while (true) {
//                try {
//                    watchKey = watchService.take();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                List<WatchEvent<?>> events = watchKey.pollEvents();
//
//                System.out.println(events.size());
////                if(events != null){
////                    System.out.println("change");
////                }
//
////                for (WatchEvent<?> event : events) {
////                    Kind<?> kind = event.kind();
////
////                    Path paths = (Path) event.context();
////
////                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
////                        try {
////                        System.out.println("create " + paths.getFileName() + " in directory ");
////                            postgreService.insertJSONDir(docxPath);
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    }
////                    else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
////                        System.out.println("delete " + paths.getFileName() + " in directory");
////                    } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
////                        System.out.println("modified " + paths.getFileName() + " in directory");
////                    } else if (kind.equals(StandardWatchEventKinds.OVERFLOW)) {
////                        System.out.println("OVERFLOW");
////                    }
////                }
////                if (!watchKey.reset()) {
////                    try {
////                        watchService.close();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
//            }
//        }).start();
    }
}
