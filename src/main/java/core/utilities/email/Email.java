package core.utilities.email;

public abstract class Email {

    Boolean isExactMatch;
    String htmlContent;
    String cssQueryToContent;
    private String body;


    public Email(){
        isExactMatch = true;
    }

    public abstract String getSubject();

    public void setHtmlContent(String content){
        htmlContent = content;
    }

    public String getHtmlContent(){
        return htmlContent;
    }

    public Boolean getIsExactMatch(){
        return isExactMatch;
    }

    public void setIsExactMatch(Boolean isExactMatch){
        this.isExactMatch = isExactMatch;
    }

    public String getCssQueryToContent() {
        return cssQueryToContent;
    }

    public void setCssQueryToContent(String cssQueryToContent) {
        this.cssQueryToContent = cssQueryToContent;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}