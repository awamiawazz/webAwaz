package com.Beans;

/**
 * Created by Hp on 11/22/2018.
 */
public class AccessRights {

    private  int a_id;
    private boolean can_report;
    private boolean view_report;
    private boolean delete_report;
    private boolean update_report;
    private boolean block_users;
    private boolean delete_users;
    private boolean view_users;
    private boolean generate_visual;
    private boolean generate_data_reports;
    private boolean add_comment;
    private boolean approved;
    private boolean add_role;
    private boolean delete_role;
    private boolean add_report;

    public AccessRights() {
        can_report=false;
        view_report=false;
        update_report=false;
        delete_report=false;
        block_users=false;
        delete_users=false;
        view_users=false;
        generate_visual=false;
        generate_data_reports=false;
        add_comment=false;
        approved=false;
        add_role=false;
        delete_role=false;
        add_report=false;
    }

    public AccessRights(int a_id, boolean can_report, boolean view_report, boolean delete_report, boolean update_report, boolean block_users, boolean delete_users, boolean view_users, boolean generate_visual, boolean generate_data_reports, boolean add_comment, boolean approved) {
        this.a_id = a_id;
        this.can_report = can_report;
        this.view_report = view_report;
        this.delete_report = delete_report;
        this.update_report = update_report;
        this.block_users = block_users;
        this.delete_users = delete_users;
        this.view_users = view_users;
        this.generate_visual = generate_visual;
        this.generate_data_reports = generate_data_reports;
        this.add_comment = add_comment;
        this.approved = approved;
    }

    public AccessRights(int a_id, boolean can_report, boolean view_report, boolean delete_report, boolean update_report, boolean block_users, boolean delete_users, boolean view_users, boolean generate_visual, boolean generate_data_reports, boolean add_comment) {
        this.a_id = a_id;
        this.can_report = can_report;
        this.view_report = view_report;
        this.delete_report = delete_report;
        this.update_report = update_report;
        this.block_users = block_users;
        this.delete_users = delete_users;
        this.view_users = view_users;
        this.generate_visual = generate_visual;
        this.generate_data_reports = generate_data_reports;
        this.add_comment = add_comment;
    }

    public boolean isAdd_report() {
        return add_report;
    }

    public void setAdd_report(boolean add_report) {
        this.add_report = add_report;
    }

    public boolean isDelete_role() {
        return delete_role;
    }

    public void setDelete_role(boolean delete_role) {
        this.delete_role = delete_role;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isAdd_role() {
        return add_role;
    }

    public void setAdd_role(boolean add_role) {
        this.add_role = add_role;
    }

    public boolean isAdd_comment() {
        return add_comment;
    }

    public void setAdd_comment(boolean add_comment) {
        this.add_comment = add_comment;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public boolean isCan_report() {
        return can_report;
    }

    public void setCan_report(boolean can_report) {
        this.can_report = can_report;
    }

    public boolean isView_report() {
        return view_report;
    }

    public void setView_report(boolean view_report) {
        this.view_report = view_report;
    }

    public boolean isDelete_report() {
        return delete_report;
    }

    public void setDelete_report(boolean delete_report) {
        this.delete_report = delete_report;
    }

    public boolean isUpdate_report() {
        return update_report;
    }

    public void setUpdate_report(boolean update_report) {
        this.update_report = update_report;
    }

    public boolean isBlock_users() {
        return block_users;
    }

    public void setBlock_users(boolean block_users) {
        this.block_users = block_users;
    }

    public boolean isDelete_users() {
        return delete_users;
    }

    public void setDelete_users(boolean delete_users) {
        this.delete_users = delete_users;
    }

    public boolean isView_users() {
        return view_users;
    }

    public void setView_users(boolean view_users) {
        this.view_users = view_users;
    }

    public boolean isGenerate_visual() {
        return generate_visual;
    }

    public void setGenerate_visual(boolean generate_visual) {
        this.generate_visual = generate_visual;
    }

    public boolean isGenerate_data_reports() {
        return generate_data_reports;
    }

    public void setGenerate_data_reports(boolean generate_data_reports) {
        this.generate_data_reports = generate_data_reports;
    }
}
