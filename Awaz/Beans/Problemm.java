package com.Beans;

/**
 * Created by Asad on 5/8/2019.
 */
public class Problemm {
    private int p_id,u_id;
    private String problem_name;

    public Problemm( ) {
    }

    public Problemm(int p_id, int u_id, String problem_name) {
        this.p_id = p_id;
        this.u_id = u_id;
        this.problem_name = problem_name;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }
}
