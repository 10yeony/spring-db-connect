package kr.com.inspect.rule;

import edu.emory.mathcs.backport.java.util.Arrays;
import kr.com.inspect.dto.Rule;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class RuleCompiler {

    String path = "/home/wooyoung/github/spring-db-connect/src/main/java/";
    String classPath = "/home/wooyoung/github/spring-db-connect/target/classes/";

    // 자바파일 생성하고 컴파일후 class 파일 로드해오는 파일
    public void create(String body) throws Exception {
        // Source를 만들고 Java파일 생성
        File sourceFile = new File(path + "kr/com/inspect/rule/Test.java");
        String source = getSource(body);
        new FileWriter(sourceFile).append(source).close();

        // java파일 컴파일 할때 옵션주기
        List<String> optionList = new ArrayList<>();
        // CLASS PATH 추가
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path"));
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
    public Object runObject() throws Exception {

        // Class 파일 Load
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(classPath).toURI().toURL(),new File("/opt/tomcat/resources/input/rule/").toURI().toURL()});
        Class<?> cls = classLoader.loadClass("kr.com.inspect.rule.Test");

        // 가져온 Class 파일에서 메서드 실행
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
            // 메서드 실행
            Object result = objMethod.invoke(cls.getDeclaredConstructor().newInstance());
            return result;

        }catch (InvocationTargetException e){
            e.getTargetException().printStackTrace();
        }
        return null;
    }

    private String getSource(String body){
        StringBuffer sb = new StringBuffer();

        // Java Source를 생성
        sb.append("package kr.com.inspect.rule;\n" +
                "import kr.com.inspect.dto.Metadata;\n" +
                "import kr.com.inspect.dto.Speaker;\n" +
                "import kr.com.inspect.dto.Utterance;\n" +
                "import kr.com.inspect.dto.Program;\n" +
                "import kr.com.inspect.dto.EojeolList;\n" +
                "import java.util.List;\n" +
                "public class Test{\n")
                .append(body)
                .append("\n}");
        return sb.toString();
    }

}