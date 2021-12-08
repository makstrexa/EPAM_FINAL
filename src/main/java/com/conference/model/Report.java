package com.conference.model;

import java.util.Objects;

public class Report {

    private int reportId;
    private String title;
    private String theme;
    private String summary;
    private User speaker;
    private Event event;
    private ReportStatus status;

    public Report(int reportId, String title, String theme, String summary, User speaker, Event event, ReportStatus status) {
        this.reportId = reportId;
        this.title = title;
        this.theme = theme;
        this.summary = summary;
        this.speaker = speaker;
        this.event = event;
        this.status = status;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public User getSpeaker() {
        return speaker;
    }

    public void setSpeaker( User speaker) {
        this.speaker = speaker;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return reportId == report.reportId && title.equals(report.title) && theme.equals(report.theme) && summary.equals(report.summary) && Objects.equals(speaker, report.speaker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, title, theme, summary, speaker);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", title='" + title + '\'' +
                ", theme='" + theme + '\'' +
                ", summary='" + summary + '\'' +
                ", speaker=" + speaker +
                ", event=" + event +
                '}';
    }
}
