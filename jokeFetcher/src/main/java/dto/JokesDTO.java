/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author gamma
 */
public class JokesDTO {

    String joke1;
    String joke1Reference;
    String joke2;
    String joke2Reference;

    public JokesDTO(ChuckDTO chuck, DadDTO dad, String joke1Ref, String joke2Ref) {
        if (chuck != null && dad != null && joke1Ref != null && joke2Ref != null) {
            joke1 = chuck.value;
            joke2 = dad.joke;
            this.joke1Reference = joke1Ref;
            this.joke2Reference = joke2Ref;
        } else {
            joke1 = "Error";
            joke2 = "Error";
            this.joke1Reference = "Error";
            this.joke2Reference = "Error";
        }
        System.out.println("FROM JOKESDTO " + joke2);
    }

    public String getJoke1() {
        return joke1;
    }

    public void setJoke1(String joke1) {
        this.joke1 = joke1;
    }

    public String getJoke2() {
        return joke2;
    }

    public void setJoke2(String joke2) {
        this.joke2 = joke2;
    }

    public String getJoke1Reference() {
        return joke1Reference;
    }

    public void setJoke1Reference(String joke1Reference) {
        this.joke1Reference = joke1Reference;
    }

    public String getJoke2Reference() {
        return joke2Reference;
    }

    public void setJoke2Reference(String joke2Reference) {
        this.joke2Reference = joke2Reference;
    }

}
