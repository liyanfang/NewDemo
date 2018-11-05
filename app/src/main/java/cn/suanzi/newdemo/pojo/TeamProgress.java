package cn.suanzi.newdemo.pojo;

/**
 *
 * @author liyanfang
 * @date 2018/11/2
 * 团队奖励进度条
 */

public class TeamProgress {

    /** 销售额*/
    private int sale;
    /** 奖励*/
    private int reward;

    public TeamProgress(int sale, int reward) {
        this.sale = sale;
        this.reward = reward;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
