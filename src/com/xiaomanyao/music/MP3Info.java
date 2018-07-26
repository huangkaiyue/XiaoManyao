package com.xiaomanyao.music;

public class MP3Info {
	/**
     * 歌曲名字
     */
    private String songName;

    /**
     * 歌手名字
     */
    private String singer;

    /**
     * 时长（秒）
     */
    private Integer duration;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Integer getDuration() {
        return duration;
    }

    /**
     * 返回hh:MM:ss形式
     * @return
     */
    public String getDurationFormat() {
        if(duration/60 >= 60) {
            return String.format("%02d",duration/3600) + String.format("%02d",(duration%3600)/60) + ":" + String.format("%02d",duration%60);
        } else {
            return "00:" + String.format("%02d",duration/60) + ":" + String.format("%02d",duration%60);
        }
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
