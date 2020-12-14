package kr.com.inspect.rule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
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

import kr.com.inspect.dto.Rule;
import org.apache.ibatis.io.Resources;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 텍스트를 자바 파일로 저장, 컴파일, 실행하는 클래스
 * @author Woo young
 * @author Yeonhee Kim
 * @version 1.0
 */
public class RuleCompiler {
	
	/**
     * java 파일을 저장할 위치
     */
    String path;

    /**
     * class 파일을 저장할 위치
     */
    String classPath;

    /**
     * 프로젝트에서 쓰이는 라이브러리들이 저장된 위치
     */
    String lib;
	

    /**
     * 객체를 생성할 때 각 path를 지정
     */
    public RuleCompiler() {
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
    
    /**
     * java 파일이 저장되는 경로를 리턴함
     * @return java 파일이 저장되는 경로
     */
    public String getPath() {
		return path;
	}
    
    /**
     * class 파일이 저장되는 경로를 리턴함
     * @return class 파일이 저장되는 경로
     */
	public String getClassPath() {
		return classPath;
	}

	/**
     * Rule을 받아 해당 룰의 컨텐츠를 java파일로 생성
     * @param rule java파일로 만들 Rule
     * @throws IOException 입출력 예외 처리
     */
    public void create(Rule rule) throws IOException {
        // Source를 만들고 Java파일 생성
        File sourceFile = new File(path + rule.getFile_name() + ".java");
        String source = getSource(rule);
        new FileWriter(sourceFile).append(source).close();
    }


    /**
     * java파일을 읽어와서 compile하여 class파일로 만든 후에 실행
     * @param rule 실행할 Rule
     * @return 결과 값 전송
     * @throws Exception 클래스 메소드 not found, url 포멧, 메서드 호출, 호출한 메서드 내 오류, 객체 생성 예외처리
     */
    public Object runObject(Rule rule) throws Exception{
        // java파일 컴파일 할때 옵션주기
        List<String> optionList = new ArrayList<>();
        // CLASS PATH 추가
        optionList.add("-classpath");
        optionList.add(System.getProperty("java.class.path")+":"+classPath);
        // CLASS 파일 저장할 디렉토리
        optionList.add("-d");
        optionList.add(classPath);

        try {
            // 만들어진 Java 파일을 컴파일
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            List<String> sources = Arrays.asList(new String[]{path + rule.getFile_name() + ".java"});
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
        } catch (Exception e){
            e.printStackTrace();
        }
        // Class 파일 Load
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(classPath).toURI().toURL(),new URL("file:"+lib+"postgresql-42.2.18.jar")});
        Class<?> cls = null;
        cls = classLoader.loadClass("kr.com.inspect.rule."+rule.getFile_name());

        // 가져온 Class 파일에서 메서드 실행
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
        Object result = new Object();
        try {
            result = objMethod.invoke(cls.getDeclaredConstructor().newInstance());
        } catch (InvocationTargetException e){
            e.getTargetException().printStackTrace();
        }
        return result;
    }

    /**
     * source 생성
     * @param rule 사용자가 입력한 Rule
     * @return source 코드
     */
    public String getSource(Rule rule){
        StringBuffer sb = new StringBuffer();

        // Java Source를 생성
        sb.append("package kr.com.inspect.rule;\n" +
                "import java.lang.Exception;\n" +
                "import kr.com.inspect.dto.Metadata;\n" +
                "import kr.com.inspect.dto.Speaker;\n" +
                "import kr.com.inspect.dto.Utterance;\n" +
                "import kr.com.inspect.dto.Program;\n" +
                "import kr.com.inspect.dto.EojeolList;\n" +
                "import java.util.List;\n\n" +
                "public class "+rule.getFile_name()+" {\n" +
                "\tpublic Object run() throws Exception{\n")
                .append(rule.getContents())
                .append("\n\t}\n}");
        return sb.toString();
    }

}