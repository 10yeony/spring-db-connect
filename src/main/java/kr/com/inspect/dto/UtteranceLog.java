package kr.com.inspect.dto;

/**
 * Rule 관련 로그
 * @author Wooyoung Lee
 * @version 1.0
 */
public class UtteranceLog extends UsingLog {
    /**
     * 사용 로그 번호(Foreign Key)
     */
    private int using_log_no;

    /**
     * 바뀐 문장
     */
    private String after;

    /**
     * 기존 문장
     */
    private String before;

    /**
     * 문장의 metadata id
     */
    private int metadata_id;

    public UtteranceLog() {}
    public UtteranceLog(int using_log_no, String after, String before, int metadata_id) {
        super();
        this.after = after;
        this.before = before;
        this.metadata_id = metadata_id;
        this.using_log_no = using_log_no;
    }

    public String getAfter(){
        return after;
    }
    public void setAfter(String after){
        this.after = after;
    }
    public String getBefore(){
        return before;
    }
    public void setBefore(String before){
        this.before = before;
    }
    public int getUsing_log_no(){
        return using_log_no;
    }
    public void setUsing_log_no(int using_log_no){
        this.using_log_no = using_log_no;
    }
    public int getMetadata_id(){
        return metadata_id;
    }
    public void setMetadata_id(int metadata_id){
        this.metadata_id = metadata_id;
    }

    @Override
    public String toString() {
        return "UtteranceLog [before=" + before + ", after=" + after + ", metadata_id="
                + metadata_id + ", using_log_no=" + using_log_no + "]";
    }
}
