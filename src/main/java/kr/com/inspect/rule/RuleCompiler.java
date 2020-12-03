package kr.com.inspect.rule;

import edu.emory.mathcs.backport.java.util.Arrays;
import kr.com.inspect.service.PostgreService;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuleCompiler extends Thread {

    String path = "/home/wooyoung/github/spring-db-connect/src/main/java/";
    String classPath = "/home/wooyoung/github/spring-db-connect/target/classes/";

    // 자바파일 생성하고 컴파일후 class 파일 로드해오는 파일
    public void create(String body) throws Exception {
        // Source를 만들고 Java파일 생성
        File sourceFile = new File(path + "kr/com/inspect/rule/Test.java");
        String source = body;
        new FileWriter(sourceFile).append(source).close();

        // java파일 컴파일 할때 옵션주기
        List<String> optionList = new ArrayList<>();
        // CLASS PATH 추가
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path") + ":" + classPath+":/opt/tomcat/resources/input/rule/");
//        optionList.add(System.getProperty("java.class.path"));
        // CLASS 파일 저장할 디렉토리
        optionList.add("-d");
        optionList.add(classPath);

        // 만들어진 Java 파일을 컴파일
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        List<String> sources = Arrays.asList(new String[]{path + "kr/com/inspect/rule/Test.java"});

        DiagnosticCollector<JavaFileObject> diagnostic = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostic, null, null);
        Iterable<? extends JavaFileObject> compilationUnit
                = fileManager.getJavaFileObjectsFromStrings(sources);
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostic,
                optionList,
                null,
                compilationUnit
        );
        Boolean success = task.call();

    }

    // Test.class 안의 runMethod 메서드 실행
    public void runObject() throws Exception {

        // 컴파일된 Class를 Load
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(classPath).toURI().toURL(),new File("/opt/tomcat/resources/input/rule/").toURI().toURL()});
//        classLoader.getResourceAsStream("/home/wooyoung/github/spring-db-connect/target/inspect-1.0.0-BUILD-SNAPSHOT/WEB-INF/lib/*.jar");
        Class<?> cls = classLoader.loadClass("kr.com.inspect.rule.Test");

        try {
            Class[] params = null;
            // 클래스 안의 모든 메소드 가져오기
            Method[] methods = cls.getDeclaredConstructor().newInstance().getClass().getDeclaredMethods();
            // 메소드 안의 모든 파라미터 가져오기
            for(Method method : methods){
                params = method.getParameterTypes();
            }

            // 메소드 가져오기
            Method objMethod = cls.getDeclaredConstructor().newInstance().getClass().getMethod(methods[0].getName());
            System.out.println("method Name: " + objMethod.getName());

            // 메서드 실행
            objMethod.invoke(cls.getDeclaredConstructor().newInstance());

        }catch (InvocationTargetException e){
            e.getTargetException().printStackTrace();
        }
    }

}