package kr.com.inspect.rule;

import edu.emory.mathcs.backport.java.util.Arrays;
import kr.com.inspect.service.PostgreService;
import kr.com.inspect.service.impl.PostgreServiceImpl;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

@Component
public class RuleCompiler extends Thread {

    // 자바파일 생성하고 컴파일후 class 파일 로드해오는 파일 (이 메서드는 잘 작동됩니다.)
    public Object create(String body) throws Exception {
        String path = "/home/wooyoung/github/spring-db-connect/src/main/java/";
        String classPath = "/home/wooyoung/github/spring-db-connect/target/classes/";

        // Source를 만들고 Java파일 생성
        File sourceFile = new File(path + "kr/com/inspect/rule/Test.java");
        String source = this.getSource(body);
        new FileWriter(sourceFile).append(source).close();

        // java파일 컴파일 할때 옵션주기
        List<String> optionList = new ArrayList<>();
        // CLASS PATH 추가
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path") + ":" + classPath);
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
        System.out.println("before Compile");
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostic,
                optionList,
                null,
                compilationUnit
        );
        Boolean success = task.call();
        System.out.println("after Compile");

        // 기본 comiler run하는 코드 (추후에 삭제 예정)
//        compiler.run(null, System.out, System.out, path+"kr/com/inspect/rule/Test.java");


        System.out.println("before Load");
        // 컴파일된 Class를 Load
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(classPath).toURI().toURL()});
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
        // report 패키지의 TestRuleCompiler 클래스를 호출하는 테스트 소스
//        sb.append("package kr.com.inspect.rule;\n"+
//                "import kr.com.inspect.report.TestRuleCompiler;\n"+
//                "public class Test { \n"+
//                "public int runMethod(int[] list) {\n")
//                .append(body)
//                .append("\t}\n}");
        // Metadata 읽어오는 소스
        sb.append("package kr.com.inspect.rule;\n" +
                "\tpublic class Test { \n" +
                "\t\tpublic void runMethod(Data data) throws Exception {\n")
                .append(body)
                .append("\n\t}\n}");
        return sb.toString();
    }

    // Test.class 안의 runMethod 메서드 실행
    public void runObject(Object obj, PostgreService postgreService) throws Exception {
        int[] list = {1, 2, 3};

//        Class arguments[] = new Class[]{postgreService.getClass()};
        Data data = new Data();
        data.setPostgreService(postgreService);

        // Source를 만들때 지정한 Method를 실행
        // runMethod 메소드 지정
//        try {
//            Method objMethod = obj.getClass().getMethod("runMethod", listArguments);
////        // 인자로 list를 보냄
//            Object result = objMethod.invoke(obj, list);
//
//            return (int)result;
//        }catch (Exception e) {
//            e.printStackTrace();
//        }

        // data 가 인자로 보내져야 하는데 보내지질 않음
        System.out.println("before Method");
        try {
            Class[] params = null;
            Method[] methods = obj.getClass().getDeclaredMethods();
            for(Method method : methods){
                params = method.getParameterTypes();
            }

            // 메소드 가져오기 (실행 됨)
            Method objMethod = obj.getClass().getMethod("runMethod", params[0]);
            System.out.println(objMethod.getName());

            // 문제 부분 invoke (java.lang.IllegalArgumentException: argument type mismatch)
            objMethod.invoke(obj, data);

            System.out.println("after Method");
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println("after Method");
//        Object result = objMethod.invoke(obj, postgreService);
//        System.out.println("after invoke");
//        return (int) result;
    }

}