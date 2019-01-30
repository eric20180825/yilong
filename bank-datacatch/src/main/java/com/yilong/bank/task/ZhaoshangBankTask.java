package com.yilong.bank.task;


import com.alibaba.fastjson.JSONObject;
import com.yilong.utils.FileUtil;
import com.yilong.utils.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.util.List;

@Component
public class ZhaoshangBankTask {
    @Value("${project.bank.url.zhaoshang}")
    private String url;
    @Value("${project.bank.params.zhaoshang}")
    private String params;

    int i=0;

    @Scheduled(cron = "*/5 * * * * ?")
    public void run() throws Exception {
        i++;
        System.out.println("===========================招商银行第"+i+"次数据抓取任务启动=================================");
        String clientNo = "";
        String accountUID="";
        String subAccountNo = "";

        List<String> list = FileUtil.ReadLineTxtFile("D:\\zhaoshangConfig.txt");
        for (String str : list) {
            if (!StringUtils.isEmpty(str)) {
                String[] result=str.split(":");
                if ("CLIENTNO".equalsIgnoreCase(result[0])) {
                    clientNo = result[1].trim();
                } else if ("ACCOUNTUID".equalsIgnoreCase(result[0])) {
                    accountUID = result[1].trim();
                } else if ("SUBACCOUNTNO".equalsIgnoreCase(result[0])) {
                    subAccountNo = result[1].trim();
                }
            }
        }

        params=params.replace("CLIENTNO",clientNo).replace("ACCOUNTUID",accountUID).replace("SUBACCOUNTNO",subAccountNo);
        String result = HttpClientUtil.postParameters(url, params);
        String urlDecode= URLDecoder.decode(result,"utf-8");

        JSONObject jsonObject = JSONObject.parseObject(urlDecode);
        String html = jsonObject.getString("content");
        Document doc = Jsoup.parse(html);

        System.out.println(urlDecode);
        System.out.println("===========================招商银行数第"+i+"次据抓取任务结束=================================");
    }

    public static void main(String[] args) throws Exception {
        String url="https://cbsz.ebank.cmbchina.com/CmbBank_DebitCard_AccountManager/UI/DebitCard/AccountQuery/Pro8/am_QueryTrans.aspx";
        String parames="RequestType=AjaxRequest&ClientNo=D3C87659779261D5CD61FDB39A5624B2133775706037652800036828&AccountUID=J3shJkUb81rFiHtrnGP80ohgly6KSY6-&SubAccountNo=128328910420002&TransTypeID=-&TransType=%5B+%E5%85%A8%E9%83%A8+%5D&BeginDate=20190124&EndDate=20190130&IncomeFrom=&IncomeTo=&PayFrom=&PayTo=&Memo=&SortName=DateTime&SortDirection=desc";

        url = url+"?"+parames;
        String result=HttpClientUtil.get(url);

        String urlDecode= URLDecoder.decode(result,"utf-8");
        System.out.println(urlDecode);
    }

}
