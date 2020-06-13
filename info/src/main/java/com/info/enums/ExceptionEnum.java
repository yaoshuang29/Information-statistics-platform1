package com.info.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  ExceptionEnum {

    TEMPLATE_NOT_FOUND(404,"模版信息未找到"),
    TEMPLATE_SAVE_ERROR(500,"保存模版信息错误"),
    TEMPLATE_DELETE_ERROR(500,"模版信息删除错误"),
    TEMPLATE_UPDATE_ERROR(500,"模版信息更新失败"),
    USER_NOT_FOUND(404,"用户没找到"),
    USER_SAVE_ERROR(500,"用户保存错误"),
    ROLE_SAVE_ERROR(500,"角色保存错误"),
    ROLE_NOT_FOUND(404,"角色信息未找到"),
    UPDATE_USER_ERROR(500,"更新用户失败"),
    DELETE_USER_ERROR(500,"删除用户失败"),
    CLASSES_SAVE_ERROR(500,"班级保存失败"),
    CLASSES_NOT_FOUND(404,"班级信息没找到"),
    MESSAGE_SAVE_ERROR(500,"提示信息保存失败"),
    MESSAGE_NOT_FOUND(404,"提示信息查询失败"),
    SUCCESS_INFO_SAVE_ERROR(500,"成功信息保存失败"),
    SUCCESS_INFO_NOT_FOUND(404,"成功信息查询失败"),
    SUCCESS_INFO_DELETE_ERROR(500,"成功信息删除失败"),

    ;
    private Integer status;
    private String msg;
}
