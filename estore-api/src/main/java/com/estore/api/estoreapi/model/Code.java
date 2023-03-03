package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

//wowie its sam's turn on the java
public class Code {
    
    @JsonProperty("code") private String code;
    @JsonProperty("percent") private int percent;

    //constructor ggez
    public Code(@JsonProperty("code") String code, @JsonProperty("percent") int percent) {
        this.code = code;
        this.percent = percent;
    }

    //getters/setters
    public String getCode() { return code; }
    public int getPercent() { return percent; }

    public void setCode(String code) { this.code = code; }
    public void setPercent(int percent) { this.percent = percent; }

    //TODO a to string probably idk ill come back i promise

}
