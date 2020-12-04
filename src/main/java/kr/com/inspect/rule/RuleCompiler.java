package kr.com.inspect.rule;

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.ibatis.io.Resources;

import edu.emory.mathcs.backport.java.util.Arrays;

public class RuleCompiler {

        public RuleCompiler(){
        String resource = "properties/directory.properties";
        Properties properties = new Properties();

        try{
            Reader reader = Resources.getResourceAsReader(resource);
            properties.load(reader);

            this.path = properties.getProperty("rule.java.directory");
            this.classPath = properties.getProperty("rule.class.directory");
            this.lib = properties.getProperty("rule.lib.directory");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    String path;
    String classPath;
    String lib;

    // 자바파일 생성
    public void create(String body) throws Exception {
        // Source를 만들고 Java파일 생성
        File sourceFile = new File(path + "Test.java");
        String source = getSource(body);
        new FileWriter(sourceFile).append(source).close();
    }

    // java파일을 읽어와서 compile하여 class파일로 만든 후에 실행
    public Object runObject() throws Exception {
        // java파일 컴파일 할때 옵션주기
        List<String> optionList = new ArrayList<>();
        // CLASS PATH 추가
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path")+":"+classPath);
        // CLASS 파일 저장할 디렉토리
        optionList.add("-d");
        optionList.add(classPath);

        // 만들어진 Java 파일을 컴파일
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        List<String> sources = Arrays.asList(new String[]{path + "Test.java"});
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

        // Class 파일 Load
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(classPath).toURI().toURL(),new File(lib).toURI().toURL()});
        Class<?> cls = null;
        try {
        	cls = classLoader.loadClass("kr.com.inspect.rule.Test");
        }catch(ClassNotFoundException e) { //코드가 틀려서 컴파일되지 않았을 경우
        	e.printStackTrace();
        }
       

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