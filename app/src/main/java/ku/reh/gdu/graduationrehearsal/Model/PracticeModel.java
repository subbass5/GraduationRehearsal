package ku.reh.gdu.graduationrehearsal.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PracticeModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("idcard")
    @Expose
    private String idcard;
    @SerializedName("score1")
    @Expose
    private String score1;
    @SerializedName("score2")
    @Expose
    private String score2;
    @SerializedName("score3")
    @Expose
    private String score3;
    @SerializedName("score4")
    @Expose
    private String score4;
    @SerializedName("score5")
    @Expose
    private String score5;

    @SerializedName("score6")
    @Expose
    private String score6;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public String getScore4() {
        return score4;
    }

    public void setScore4(String score4) {
        this.score4 = score4;
    }

    public String getScore5() {
        return score5;
    }

    public void setScore5(String score5) {
        this.score5 = score5;
    }

    public String getScore6() {
        return score6;
    }

    public void setScore6(String score6) {
        this.score6 = score6;
    }


}
