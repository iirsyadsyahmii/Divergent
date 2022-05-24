package com.example.login;

public class Rewards {
    private String rewardName;
    private String desc;
    private Integer rewardImage;

    public Rewards(String rewardName, String desc, Integer rewardImage) {
        this.rewardName = rewardName;
        this.desc = desc;
        this.rewardImage = rewardImage;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getRewardImage() {
        return rewardImage;
    }

    public void setRewardImage(Integer rewardImage) {
        this.rewardImage = rewardImage;
    }
}
