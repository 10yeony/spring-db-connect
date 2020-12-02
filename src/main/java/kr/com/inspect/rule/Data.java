package kr.com.inspect.rule;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.service.PostgreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Data {

    private PostgreService postgreService;

    @Autowired
    public void setPostgreService(PostgreService postgreService){
        this.postgreService = postgreService;
    }

    public PostgreService getPostgreService(){
        return this.postgreService;
    }

    public void metadata(){
        System.out.println("metadata() called");
//        System.out.println(postgreService);
//        List<Metadata> metadata= postgreService.getMetadata();
//        System.out.println(metadata.get(1));
//        return metadata;
    }
}
