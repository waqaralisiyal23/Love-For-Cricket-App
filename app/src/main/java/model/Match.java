package model;

public class Match {
    private String id, team1, team2, matchType, matchStatus, date;

    public Match(){}

    public Match(String id, String team1, String team2, String matchType, String matchStatus, String date) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.matchType = matchType;
        this.matchStatus = matchStatus;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
