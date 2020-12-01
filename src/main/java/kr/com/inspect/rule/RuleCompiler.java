package kr.com.inspect.rule;

import edu.emory.mathcs.backport.java.util.Arrays;
import kr.com.inspect.service.PostgreService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuleCompiler extends Thread {

    @Autowired
    private PostgreService postgreService;

    public Object create(String body)throws Exception{
        System.out.println(postgreService);
        //System.out.println(postgreService.getMetadata().get(0));

        String path = "/home/namuhwang/github/spring-db-connect/src/main/java/";
        String classPath = "/home/namuhwang/github/spring-db-connect/target/classes/";

        // Source를 만들고 Java파일 생성
        File sourceFile = new File(path+"kr/com/inspect/rule/Test.java");
        String source = this.getSource(body);
        new FileWriter(sourceFile).append(source).close();

        List<String> optionList = new ArrayList<>();
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path"));
        System.out.println(System.getProperty("java.class.path"));
        optionList.add("-d");
        optionList.add(classPath);

        // 만들어진 Java 파일을 컴파일
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        List<String> sources = Arrays.asList(new String[] {path+"kr/com/inspect/rule/Test.java"});

        DiagnosticCollector<JavaFileObject> diagnostic = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostic, null, null);
        Iterable<? extends JavaFileObject> compilationUnit
                = fileManager.getJavaFileObjectsFromStrings(sources);
        System.out.println("before");
        Boolean task = compiler.getTask(
                null,
                fileManager,
                diagnostic,
                optionList,
                null,
                compilationUnit
        ).call();
        System.out.println("after");

//        compiler.run(null, System.out, System.out, path+"kr/com/inspect/rule/Test.java");


        System.out.println("before Load");
        // 컴파일된 Class를 Load
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {new File(classPath).toURI().toURL()});
//        Class<?> cls = Class.forName("kr.com.inspect.rule.Test", true, classLoader);
        Class<?> cls = classLoader.loadClass("kr.com.inspect.rule.Test");
        System.out.println("after Load");

        // Load한 Class의 Instance를 생성
        return cls.newInstance();
//        return (Object)true;
    }


    private String getSource(String body) {
        StringBuffer sb = new StringBuffer();

        // Java Source를 생성한다.
        // 간단한 테스트 소스
        sb.append("package kr.com.inspect.rule;\n"+
                "public class Test { \n"+
                "public int runMethod(int[] params) throws Exception {")
                .append(body)
                .append("} }");
        // jsonLog 읽어오는 소스
//        sb.append("package kr.com.inspect.rule;\n"+
//                "import kr.com.inspect.service.PostgreService;\n" +
//                "import org.springframework.beans.factory.annotation.Autowired;\n" +
//                "import kr.com.inspect.dto.JsonLog;\n"+
//                "import java.util.List;\n"+
//                "public class Test { \n"+
//                "@Autowired\n" +
//                "private PostgreService postgreService;\n"+
//                "public int runMethod(int[] params) throws Exception {\n")
//                .append(body)
//                .append("} }");
        return sb.toString();
    }

    public int runObject(Object obj) throws Exception{
        int[] params = {1, 2, 3};
        Class arguments[] = new Class[]{params.getClass()};

        // Source를 만들때 지정한 Method를 실행
        Method objMethod = obj.getClass().getMethod("runMethod", arguments);
        Object result = objMethod.invoke(obj, params);
        return (int) result;
    }
}
