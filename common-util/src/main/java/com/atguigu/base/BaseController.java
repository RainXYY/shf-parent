package com.atguigu.base;

import org.springframework.ui.Model;

/**
 * 包名:com.atguigu.base
 *
 * @author Leevi
 * 日期2022-05-18  13:58
 */
public class BaseController {
    private static final String PAGE_SUCCESS = "common/successPage";

    public String successPage(Model model,String successMessage){
        //往请求域中存储成功页面的提示信息
        model.addAttribute("messagePage",successMessage);
        return PAGE_SUCCESS;
    }
}
